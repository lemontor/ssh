package com.hyphenate.easeui;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.domain.EaseAvatarOptions;
import com.hyphenate.easeui.domain.EaseEmojicon;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.model.EaseAtMessageHelper;
import com.hyphenate.easeui.model.EaseNotifier;
import com.hyphenate.easeui.model.EaseDingMessageHelper;
import com.hyphenate.easeui.utils.EaseCommonUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.hyphenate.easeui.utils.EaseUserUtils.getUserInfo;

public final class EaseUI {
    private static final String TAG = EaseUI.class.getSimpleName();

    /**
     * the global EaseUI instance
     */
    private static EaseUI instance = null;
    
    /**
     * user profile provider
     */
    private EaseUserProfileProvider userProvider;
    
    private EaseSettingsProvider settingsProvider;

    private EaseAvatarOptions avatarOptions;
    
    /**
     * application context
     */
    private Context appContext = null;
    
    /**
     * init flag: test if the sdk has been inited before, we don't need to init again
     */
    private boolean sdkInited = false;
    
    /**
     * the notifier
     */
    private EaseNotifier notifier = null;
    
    /**
     * save foreground Activity which registered eventlistener
     */
    private List<Activity> activityList = new ArrayList<Activity>();
    
    public void pushActivity(Activity activity){
        if(!activityList.contains(activity)){
            activityList.add(0,activity); 
        }
    }
    
    public void popActivity(Activity activity){
        activityList.remove(activity);
    }

    public Activity getTopActivity(){
        return activityList.get(0);
    }
    
    private EaseUI(){}
    
    /**
     * get instance of EaseUI
     * @return
     */
    public synchronized static EaseUI getInstance(){
        if(instance == null){
            instance = new EaseUI();
        }
        return instance;
    }
    
    /**
     *this function will initialize the SDK and easeUI kit
     * 
     * @return boolean true if caller can continue to call SDK related APIs after calling onInit, otherwise false.
     * 
     * @param context
     * @param options use default if options is null
     * @return
     */
    public synchronized boolean init(Context context, EMOptions options){
        if(sdkInited){
            return true;
        }
        appContext = context;
        

        // if there is application has remote service, application:onCreate() maybe called twice
        // this check is to make sure SDK will initialized only once
        // return if process name is not application's name since the package name is the default process name
        if (!isMainProcess(appContext)) {
            Log.e(TAG, "enter the service process!");
            return false;
        }
        if(options == null){
            EMClient.getInstance().init(context, initChatOptions());
        }else{
            EMClient.getInstance().init(context, options);
        }
        
        initNotifier();
        registerMessageListener();
        
        if(settingsProvider == null){
            settingsProvider = new DefaultSettingsProvider();
        }
        
        sdkInited = true;
        return true;
    }


    protected EMOptions initChatOptions(){
        Log.d(TAG, "init HuanXin Options");

        EMOptions options = new EMOptions();
        // change to need confirm contact invitation
        options.setAcceptInvitationAlways(false);
        // set if need read ack
        options.setRequireAck(true);
        // set if need delivery ack
        options.setRequireDeliveryAck(false);
        
        return options;
    }
    
    void initNotifier(){
        notifier = new EaseNotifier(appContext);
//        setNotifier();
    }

//    void setNotifier(){
//        notifier.setNotificationInfoProvider(new EaseNotifier.EaseNotificationInfoProvider() {
//
//            @Override
//            public String getTitle(EMMessage message) {
//                //you can update title here
//                return null;
//            }
//
//            @Override
//            public int getSmallIcon(EMMessage message) {
//                //you can update icon here
//                return 0;
//            }
//
//            @Override
//            public String getDisplayedText(EMMessage message) {
//                // be used on notification bar, different text according the message type.
//                String ticker = EaseCommonUtils.getMessageDigest(message, appContext);
//                if(message.getType() == EMMessage.Type.TXT){
//                    ticker = ticker.replaceAll("\\[.{2,3}\\]", "[表情]");
//                }
//                EaseUser user = getUserInfo(message.getFrom());
//                if(user != null){
//                    if(EaseAtMessageHelper.get().isAtMeMsg(message)){
//                        return String.format("%s在群聊中@了你", user.getNickname());
//                    }
//                    return user.getNickname() + ": " + ticker;
//                }else{
//                    if(EaseAtMessageHelper.get().isAtMeMsg(message)){
//                        return String.format("%s在群聊中@了你", message.getFrom());
//                    }
//                    return message.getFrom() + ": " + ticker;
//                }
//            }
//
//            @Override
//            public String getLatestText(EMMessage message, int fromUsersNum, int messageNum) {
//                // here you can customize the text.
//                // return fromUsersNum + "contacts send " + messageNum + "messages to you";
//                return null;
//            }
//
//            @Override
//            public Intent getLaunchIntent(EMMessage message) {
//                // you can set what activity you want display when user click the notification
//                Intent intent = new Intent(appContext, ChatActivity.class);
//                // open calling activity if there is call
////                if(isVideoCalling){
////                    intent = new Intent(appContext, VideoCallActivity.class);
////                }else if(isVoiceCalling){
////                    intent = new Intent(appContext, VoiceCallActivity.class);
////                }else{
//                    EMMessage.ChatType chatType = message.getChatType();
//                    if (chatType == EMMessage.ChatType.Chat) { // single chat message
//                        intent.putExtra("userId", message.getFrom());
//                        intent.putExtra("chatType", 1);
//                    } else { // group chat message
//                        // message.getTo() is the group id
//                        intent.putExtra("userId", message.getTo());
//                        if(chatType == EMMessage.ChatType.GroupChat){
//                            intent.putExtra("chatType", 2);
//                        }else{
//                            intent.putExtra("chatType", 3);
//                        }
//
//                    }
////                }
//                return intent;
//            }
//        });
//    }

//    private EaseUser getUserInfo(String username){
//        // To get instance of EaseUser, here we get it from the user list in memory
//        // You'd better cache it if you get it from your server
//        EaseUser user = null;
//        if(username.equals(EMClient.getInstance().getCurrentUser()))
//            return getUserProfileManager().getCurrentUserInfo();
//        user = getContactList().get(username);
//        if(user == null && getRobotList() != null){
//            user = getRobotList().get(username);
//        }
//
//        // if user is not in your contacts, set inital letter for him/her
//        if(user == null){
//            user = new EaseUser(username);
//            EaseCommonUtils.setUserInitialLetter(user);
//        }
//        return user;
//    }
//
//    public UserProfileManager getUserProfileManager() {
//        if (userProManager == null) {
//            userProManager = new UserProfileManager();
//        }
//        return userProManager;
//    }
//    private UserProfileManager userProManager;


    private void registerMessageListener() {
        EMClient.getInstance().chatManager().addMessageListener(new EMMessageListener() {
            
            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                EaseAtMessageHelper.get().parseMessages(messages);
            }
            @Override
            public void onMessageRead(List<EMMessage> messages) {
                
            }
            @Override
            public void onMessageDelivered(List<EMMessage> messages) {
            }

            @Override
            public void onMessageRecalled(List<EMMessage> messages) {

            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {
                
            }
            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                for (EMMessage message : messages) {
                    // To handle group-ack msg.
                    EaseDingMessageHelper.get().handleAckMessage(message);
                }
            }
        });
    }
    
    public EaseNotifier getNotifier(){
        return notifier;
    }
    
    public boolean hasForegroundActivies(){
        return activityList.size() != 0;
    }


    public void setAvatarOptions(EaseAvatarOptions avatarOptions) {
        this.avatarOptions = avatarOptions;
    }
    public EaseAvatarOptions getAvatarOptions(){
        return  avatarOptions;
    }

    
    /**
     * set user profile provider
     * @param provider
     */
    public void setUserProfileProvider(EaseUserProfileProvider userProvider){
        this.userProvider = userProvider;
    }
    
    /**
     * get user profile provider
     * @return
     */
    public EaseUserProfileProvider getUserProfileProvider(){
        return userProvider;
    }
    
    public void setSettingsProvider(EaseSettingsProvider settingsProvider){
        this.settingsProvider = settingsProvider;
    }
    
    public EaseSettingsProvider getSettingsProvider(){
        return settingsProvider;
    }

    public boolean isMainProcess(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return context.getApplicationInfo().packageName.equals(appProcess.processName);
            }
        }
        return false;
    }
    
    /**
     * User profile provider
     * @author wei
     *
     */
    public interface EaseUserProfileProvider {
        /**
         * return EaseUser for input username
         * @param username
         * @return
         */
        EaseUser getUser(String username);
    }
    
    /**
     * Emojicon provider
     *
     */
    public interface EaseEmojiconInfoProvider {
        /**
         * return EaseEmojicon for input emojiconIdentityCode
         * @param emojiconIdentityCode
         * @return
         */
        EaseEmojicon getEmojiconInfo(String emojiconIdentityCode);
        
        /**
         * get Emojicon map, key is the text of emoji, value is the resource id or local path of emoji icon(can't be URL on internet)
         * @return
         */
        Map<String, Object> getTextEmojiconMapping();
    }
    
    private EaseEmojiconInfoProvider emojiconInfoProvider;
    
    /**
     * Emojicon provider
     * @return
     */
    public EaseEmojiconInfoProvider getEmojiconInfoProvider(){
        return emojiconInfoProvider;
    }
    
    /**
     * set Emojicon provider
     * @param emojiconInfoProvider
     */
    public void setEmojiconInfoProvider(EaseEmojiconInfoProvider emojiconInfoProvider){
        this.emojiconInfoProvider = emojiconInfoProvider;
    }
    
    /**
     * new message options provider
     *
     */
    public interface EaseSettingsProvider {
        boolean isMsgNotifyAllowed(EMMessage message);
        boolean isMsgSoundAllowed(EMMessage message);
        boolean isMsgVibrateAllowed(EMMessage message);
        boolean isSpeakerOpened();
    }
    
    /**
     * default settings provider
     *
     */
    protected class DefaultSettingsProvider implements EaseSettingsProvider{

        @Override
        public boolean isMsgNotifyAllowed(EMMessage message) {
            // TODO Auto-generated method stub
            return true;
        }

        @Override
        public boolean isMsgSoundAllowed(EMMessage message) {
            return true;
        }

        @Override
        public boolean isMsgVibrateAllowed(EMMessage message) {
            return true;
        }

        @Override
        public boolean isSpeakerOpened() {
            return true;
        } 
    }
    
    public Context getContext(){
        return appContext;
    }
}
