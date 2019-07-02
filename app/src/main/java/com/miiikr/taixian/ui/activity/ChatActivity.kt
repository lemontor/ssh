package com.miiikr.taixian.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.widget.SwipeRefreshLayout
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ListView
import android.widget.RelativeLayout
import android.widget.Toast
import com.hyphenate.EMCallBack
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMMessage
import com.hyphenate.easeui.EaseConstant
import com.hyphenate.easeui.domain.EaseEmojicon
import com.hyphenate.easeui.model.EaseAtMessageHelper
import com.hyphenate.easeui.widget.EaseChatExtendMenu
import com.hyphenate.easeui.widget.EaseChatInputMenu
import com.hyphenate.easeui.widget.EaseChatMessageList
import com.hyphenate.util.EMLog
import com.miiikr.taixian.BaseMvp.View.BaseMvpActivity
import com.miiikr.taixian.BaseMvp.presenter.PersonPresenter
import com.miiikr.taixian.R
import com.ssh.net.ssh.utils.ScreenUtils
import com.hyphenate.EMMessageListener
import com.hyphenate.chat.EMConversation
import com.hyphenate.easeui.EaseUI
import com.hyphenate.easeui.model.EaseCompat
import com.hyphenate.easeui.utils.EaseCommonUtils
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider
import com.hyphenate.util.PathUtil
import com.miiikr.taixian.service.NewsService
import com.miiikr.taixian.utils.AndroidWorkaround
import com.miiikr.taixian.utils.ToastUtils
import java.io.File
import java.io.FileOutputStream


class ChatActivity : BaseMvpActivity<PersonPresenter>(), EaseChatExtendMenu.EaseChatExtendMenuItemClickListener {

    private var isMessageListInited: Boolean = false
    lateinit var inputMenu: EaseChatInputMenu
    lateinit var chatMessageList: EaseChatMessageList
    var itemStrings = intArrayOf(com.hyphenate.easeui.R.string.attach_take_pic, com.hyphenate.easeui.R.string.attach_picture, R.string.attach_video)
    var itemdrawables = intArrayOf(com.hyphenate.easeui.R.drawable.ease_chat_takepic_pressed, com.hyphenate.easeui.R.drawable.ease_chat_image_pressed, com.hyphenate.easeui.R.drawable.em_chat_video_pressed)
    var itemIds = intArrayOf(0, 1, 2)
    val chatType = 1
    var toChatUsername = "user1"
//    var toChatUsername = "aaa"
//        var toChatUsername = "user3"
    lateinit var inputManager: InputMethodManager
    lateinit var conversation: EMConversation
    var pagesize = 20
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var listView: ListView
    var isloading: Boolean = false
    var haveMoreData = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        val layout = findViewById<RelativeLayout>(R.id.root_layout)
        layout.setPadding(0, 0, 0, AndroidWorkaround.getNavigationBarHeight(this))
        initUI()
        initObj()
        EMLogin("user2", "123456")
        initListener()
    }

    private fun initObj() {
        inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        onMessageListInit()
    }

    private fun initListener() {
        conversation = EMClient.getInstance().chatManager().getConversation(toChatUsername)
        val msgs = conversation.allMessages
        val msgCount = msgs?.size ?: 0
        if (msgCount < conversation.allMsgCount && msgCount < pagesize) {
            var msgId: String? = null
            if (msgs != null && msgs.size > 0) {
                msgId = msgs[0].msgId
            }
            conversation.loadMoreMsgFromDB(msgId, pagesize - msgCount)
        }
        conversation.markAllMessagesAsRead()
        val msgListener = object : EMMessageListener {
            //接收消息事件
            override fun onMessageReceived(messages: List<EMMessage>) {//收到消息
                Log.e("tag_onMessageReceived", "onMessageReceived")
                for (mg in messages) {
//                    mg.setAttribute("records",true)
//                    val b = mg.getStringAttribute("records")
//                    Log.e("tag_message","$b")
                    var uName = ""
                    uName = if (mg.chatType == EMMessage.ChatType.GroupChat || mg.chatType == EMMessage.ChatType.ChatRoom) {
                        mg.to
                    } else {
                        mg.from
                    }
                    if (uName == toChatUsername || mg.to == toChatUsername || mg.conversationId() == toChatUsername) {
                        chatMessageList.refreshSelectLast()
                        conversation.markMessageAsRead(mg.msgId)
                    }
                    EaseUI.getInstance().notifier.vibrateAndPlayTone(mg)

                }

            }

            override fun onCmdMessageReceived(messages: List<EMMessage>) {
                //收到透传消息
            }

            override fun onMessageRead(messages: List<EMMessage>) {
                Log.e("tag_info", "onMessageRead")
                //收到已读回执
                if (isMessageListInited) {
                    chatMessageList.refresh()
                }
            }

            override fun onMessageDelivered(message: List<EMMessage>) {
                Log.e("tag_info", "onMessageDelivered")
                //收到已送达回执
                if (isMessageListInited) {
                    chatMessageList.refresh()
                }
            }

            override fun onMessageRecalled(messages: List<EMMessage>) {
                //消息被撤回
            }

            override fun onMessageChanged(message: EMMessage, change: Any) {
                Log.e("tag_info", "onMessageChanged")
                //消息状态变动
                if (isMessageListInited) {
                    chatMessageList.refresh()
                }
            }
        }
        EMClient.getInstance().chatManager().addMessageListener(msgListener)
        swipeRefreshLayout.setOnRefreshListener {
            loadMoreLocalMessage()
        }
    }

    fun EMLogin(userName: String, passWord: String) {
        EMClient.getInstance().login(userName, passWord, object : EMCallBack {
            //登录事件
            override fun onSuccess() {
                Log.e("tag_EMLogin", "onSuccess")
//                EMClient.getInstance().groupManager().loadAllGroups()
//                EMClient.getInstance().chatManager().loadAllConversations()
            }

            override fun onProgress(p0: Int, p1: String?) {

            }

            override fun onError(p0: Int, p1: String?) {
                Log.e("tag_onError", "${p0} + $p1")
            }
        })
    }


    private fun initUI() {
        chatMessageList = findViewById(R.id.message_list)
        chatMessageList.isShowUserNick = true
        swipeRefreshLayout = chatMessageList.swipeRefreshLayout
        listView = chatMessageList.listView
        inputMenu = findViewById(R.id.input_menu)
        inputMenu.init()
        registerExtendMenuItem()
        inputMenu.init(null)
        inputMenu.setChatInputMenuListener(object : EaseChatInputMenu.ChatInputMenuListener {

            override fun onTyping(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun onSendMessage(content: String) {
                sendTextMessage(content)

            }

            override fun onPressToSpeakBtnTouch(v: View, event: MotionEvent): Boolean {
                return false
            }

            override fun onBigExpressionClicked(emojicon: EaseEmojicon) {
            }
        })
    }

    override fun onClick(itemId: Int, view: View?) {
        when (itemId) {
            0 -> selectPicFromCamera()
            1 -> selectPicFromLocal()
            2 -> {
                val intent = Intent(this, VideoThumbActivity::class.java)
                startActivityForResult(intent, REQUEST_CODE_SELECT_VIDEO)
            }
        }
    }


    var cameraFile: File? = null
    val REQUEST_CODE_CAMERA = 2
    val REQUEST_CODE_LOCAL = 3
    val REQUEST_CODE_SELECT_VIDEO = 11


    /**
     * capture new image
     */
    protected fun selectPicFromCamera() {
        if (!EaseCommonUtils.isSdcardExist()) {
            Toast.makeText(this, com.hyphenate.easeui.R.string.sd_card_does_not_exist, Toast.LENGTH_SHORT).show()
            return
        }

        cameraFile = File(PathUtil.getInstance().imagePath, EMClient.getInstance().currentUser
                + System.currentTimeMillis() + ".jpg")

        cameraFile!!.parentFile.mkdirs()
        startActivityForResult(
                Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, EaseCompat.getUriForFile(this, cameraFile)),
                REQUEST_CODE_CAMERA)
    }


    /**
     * select local image
     */
    fun selectPicFromLocal() {
        val intent: Intent
        if (Build.VERSION.SDK_INT < 19) {
            intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
        } else {
            intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        }
        startActivityForResult(intent, REQUEST_CODE_LOCAL)
    }

    protected fun sendTextMessage(content: String) {
        if (EaseAtMessageHelper.get().containsAtUsername(content)) {
            sendAtMessage(content)
        } else {
            val message = EMMessage.createTxtSendMessage(content, toChatUsername)
//            message.setAttribute("records","aaa")
            sendMessage(message)
        }
    }

    private fun sendAtMessage(content: String) {
        if (chatType != EaseConstant.CHATTYPE_GROUP) {
            EMLog.e("tag", "only support group chat message")
            return
        }
        val message = EMMessage.createTxtSendMessage(content, toChatUsername)
        val group = EMClient.getInstance().groupManager().getGroup(toChatUsername)
        if (EMClient.getInstance().currentUser == group.owner && EaseAtMessageHelper.get().containsAtAll(content)) {
            message.setAttribute(EaseConstant.MESSAGE_ATTR_AT_MSG, EaseConstant.MESSAGE_ATTR_VALUE_AT_MSG_ALL)
        } else {
            message.setAttribute(EaseConstant.MESSAGE_ATTR_AT_MSG,
                    EaseAtMessageHelper.get().atListToJsonArray(EaseAtMessageHelper.get().getAtMessageUsernames(content)))
        }
        sendMessage(message)

    }


    protected fun sendMessage(message: EMMessage?) {
        if (message == null) {
            return
        }
//        if (chatFragmentHelper != null) {
//            //set extension
//            chatFragmentHelper.onSetMessageAttributes(message)
//        }
        if (chatType == EaseConstant.CHATTYPE_GROUP) {
            message.chatType = EMMessage.ChatType.GroupChat
        } else if (chatType == EaseConstant.CHATTYPE_CHATROOM) {
            message.chatType = EMMessage.ChatType.ChatRoom
        }

        message.setMessageStatusCallback(object : EMCallBack {
            //消息发送事件
            override fun onSuccess() {
                Log.e("tag_", "onSuccess")
                if (isMessageListInited) {
                    chatMessageList.refresh()
                }
            }

            override fun onProgress(p0: Int, p1: String?) {
                Log.e("tag_onProgress", "$p0 + $p1")
            }

            override fun onError(p0: Int, p1: String?) {
                Log.e("tag_onError", "$p0 + $p1")

            }
        })

        // Send message.
        EMClient.getInstance().chatManager().sendMessage(message)
        //refresh ui
//        if (isMessageListInited) {
//            messageList.refreshSelectLast()
//        }
    }


    fun registerExtendMenuItem() {
        for (i in itemStrings.indices) {
            inputMenu.registerExtendMenuItem(itemStrings[i], itemdrawables[i], itemIds[i], this)
        }
    }

    protected fun onMessageListInit() {
        chatMessageList.init(toChatUsername, chatType, if (chatFragmentHelper != null)
            chatFragmentHelper!!.onSetCustomChatRowProvider()
        else
            null)
//        setListItemClickListener()

        chatMessageList.getListView().setOnTouchListener(View.OnTouchListener { v, event ->
            hideKeyboard()
            inputMenu.hideExtendMenuContainer()
            false
        })

        isMessageListInited = true
    }


    fun hideKeyboard() {//软键盘收起
        if (window.attributes.softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (currentFocus != null)
                inputManager.hideSoftInputFromWindow(currentFocus!!.windowToken,
                        InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }


    var chatFragmentHelper: EaseChatFragmentHelper? = null

    fun setEaseChatFragmentHelper(chatHelper: EaseChatFragmentHelper) {
        chatFragmentHelper = chatHelper
    }


    interface EaseChatFragmentHelper {
        /**
         * set message attribute
         */
        fun onSetMessageAttributes(message: EMMessage)

        /**
         * enter to chat detail
         */
        fun onEnterToChatDetails()

        /**
         * on avatar clicked
         * @param username
         */
        fun onAvatarClick(username: String)

        /**
         * on avatar long pressed
         * @param username
         */
        fun onAvatarLongClick(username: String)

        /**
         * on message bubble clicked
         */
        fun onMessageBubbleClick(message: EMMessage): Boolean

        /**
         * on message bubble long pressed
         */
        fun onMessageBubbleLongClick(message: EMMessage)

        /**
         * on extend menu item clicked, return true if you want to override
         * @param view
         * @param itemId
         * @return
         */
        fun onExtendMenuItemClick(itemId: Int, view: View): Boolean

        /**
         * on set custom chat row provider
         * @return
         */
        fun onSetCustomChatRowProvider(): EaseCustomChatRowProvider
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_CAMERA) { // capture new image
                if (cameraFile != null && cameraFile!!.exists())
                    sendImageMessage(cameraFile!!.absolutePath)
            } else if (requestCode == REQUEST_CODE_LOCAL) { // send local image
                if (data != null) {
                    val selectedImage = data.data
                    if (selectedImage != null) {
                        sendPicByUri(selectedImage)
                    }
                }
            } else if (requestCode == REQUEST_CODE_SELECT_VIDEO) {
                if (data != null) {//send the video
                    val duration = data.getIntExtra("dur", 0)
                    val videoPath = data.getStringExtra("path")
                    val file = File(PathUtil.getInstance().imagePath, "thvideo" + System.currentTimeMillis())
                    try {
                        val fos = FileOutputStream(file)
                        val ThumbBitmap = ThumbnailUtils.createVideoThumbnail(videoPath, 3)
                        ThumbBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                        fos.close()
                        sendVideoMessage(videoPath, file.absolutePath, duration)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    private fun sendImageMessage(imagePath: String) {
        val message = EMMessage.createImageSendMessage(imagePath, false, toChatUsername)
        sendMessage(message)
    }

    private fun sendPicByUri(selectedImage: Uri) {
        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        var cursor = contentResolver.query(selectedImage, filePathColumn, null, null, null)
        if (cursor != null) {
            cursor!!.moveToFirst()
            val columnIndex = cursor!!.getColumnIndex(filePathColumn[0])
            val picturePath = cursor!!.getString(columnIndex)
            cursor!!.close()
            cursor = null
            if (picturePath == null || picturePath == "null") {
                val toast = Toast.makeText(this, com.hyphenate.easeui.R.string.cant_find_pictures, Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
                return
            }
            sendImageMessage(picturePath)
        } else {
            val file = File(selectedImage.path)
            if (!file.exists()) {
                val toast = Toast.makeText(this, com.hyphenate.easeui.R.string.cant_find_pictures, Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
                return

            }
            sendImageMessage(file.absolutePath)
        }
    }

    private fun sendVideoMessage(videoPath: String, thumbPath: String, videoLength: Int) {
        val message = EMMessage.createVideoSendMessage(videoPath, thumbPath, videoLength, toChatUsername)
        sendMessage(message)
    }

    private fun loadMoreLocalMessage() {
        if (listView.getFirstVisiblePosition() == 0 && !isloading && haveMoreData) {
            val messages: List<EMMessage>
            try {
                messages = conversation.loadMoreMsgFromDB(if (conversation.allMessages.size == 0) "" else conversation.allMessages[0].msgId,
                        pagesize)
            } catch (e1: Exception) {
                swipeRefreshLayout.isRefreshing = false
                return
            }
            if (messages.size > 0) {
                chatMessageList.refreshSeekTo(messages.size - 1)
                if (messages.size != pagesize) {
                    haveMoreData = false
                }
            } else {
                haveMoreData = false
            }

            isloading = false
        } else {
            ToastUtils.toastShow(this, resources.getString(com.hyphenate.easeui.R.string.no_more_messages))
        }
        swipeRefreshLayout.isRefreshing = false
    }


    override fun onDestroy() {
        var intent = Intent()
        intent.setClass(this,NewsService::class.java)
        intent.putExtra("userName",toChatUsername)
        startService(intent)
        super.onDestroy()
    }


}