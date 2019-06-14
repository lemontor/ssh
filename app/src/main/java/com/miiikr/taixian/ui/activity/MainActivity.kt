package com.miiikr.taixian.ui.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.util.Log
import android.view.View
import android.widget.*
import com.miiikr.taixian.BaseMvp.IView.MainView
import java.util.ArrayList
import com.miiikr.taixian.BaseMvp.View.BaseMvpActivity
import com.miiikr.taixian.BaseMvp.presenter.MainPresenter
import com.miiikr.taixian.R
import com.miiikr.taixian.adapter.NtAdapter2
import com.miiikr.taixian.app.SSHApplication
import com.miiikr.taixian.entity.Bean
import com.miiikr.taixian.entity.CommonEntity
import com.miiikr.taixian.entity.MainEntity
import com.miiikr.taixian.entity.MessageEvent
import com.miiikr.taixian.ui.fragment.MainFragmentLeft
import com.miiikr.taixian.ui.fragment.MainFragmentRight
import com.miiikr.taixian.ui.fragment.SetNameFragment
import com.miiikr.taixian.ui.fragment.SettingFragment
import com.miiikr.taixian.utils.RequestInterface
import com.miiikr.taixian.utils.SharedPreferenceUtils
import com.miiikr.taixian.widget.MainViewPager
import com.miiikr.taixian.widget.SSHProgressHUD
import com.ssh.net.ssh.utils.AppConfig
import com.tencent.mm.opensdk.constants.ConstantsAPI
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class MainActivity : BaseMvpActivity<MainPresenter>(), MainView {

    override fun <T : Any> onSuccess(responseId: Int, response: T) {
        when (responseId) {
            RequestInterface.REQUEST_MAIN_ID -> {
                val result = response as? MainEntity
                if (result != null) {
                    if (result.states == 1) {

                    } else {

                    }
                }
            }
            RequestInterface.REQUEST_MAIN_SEX -> {
                val result = response as? CommonEntity
                if (result != null) {
                    if (result.state == 1) {
                        Log.e("tag_chose", "chose")
                        getSharedPreferences()!!.setChose(SharedPreferenceUtils.PREFERENCE_U_C, true)
                    }
                }
            }
        }
    }

    override fun onFailue(responseId: Int, msg: String) {
    }

    lateinit var mainRecyclerView: MainViewPager
    lateinit var mDrawerLayout: DrawerLayout
    lateinit var mIvMore: ImageView
    lateinit var mIvPreson: ImageView
    lateinit var mFrameLayoutForLeft: FrameLayout
    lateinit var mFrameLayoutForRight: FrameLayout
    internal var list: MutableList<Bean> = ArrayList<Bean>()

    var mCurLeftFragment: Fragment
    var mCurRightFragment: Fragment

    var mFragmentManager: FragmentManager

    var mFragmentMore: MainFragmentLeft
    var mFragmentPerson: MainFragmentRight

    var mMyAdapter: NtAdapter2? = null
    var mFragmentSet: SettingFragment? = null
    var mFragmentNickName: SetNameFragment? = null
    lateinit var mSSHProgressHUD: SSHProgressHUD
    var share: SharedPreferenceUtils? = null


    /*
    0:显示主页
    1:显示左侧
    2:显示右侧
     */
    var mShowFrag = 0

    init {
        mCurLeftFragment = Fragment()
        mCurRightFragment = Fragment()
        mFragmentManager = supportFragmentManager
        mFragmentMore = MainFragmentLeft()
        mFragmentPerson = MainFragmentRight()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mPresenter = MainPresenter()
        mPresenter.mView = this
        EventBus.getDefault().register(this)
        initUI()
        initData()
        initListener()
    }

    private fun initListener() {
        showLeftFragment(mFragmentMore)
        showRightFragment(mFragmentPerson)
        mIvMore.setOnClickListener {
            mDrawerLayout.openDrawer(mFrameLayoutForLeft)
        }
        mIvPreson.setOnClickListener {
            mDrawerLayout.openDrawer(mFrameLayoutForRight)
        }
        mDrawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerStateChanged(p0: Int) {
            }

            override fun onDrawerSlide(p0: View, p1: Float) {
            }

            override fun onDrawerClosed(p0: View) {
                when (p0) {
                    mFrameLayoutForLeft -> mShowFrag = 0
                    mFrameLayoutForRight -> mShowFrag = 0
                }
            }

            override fun onDrawerOpened(p0: View) {
                when (p0) {
                    mFrameLayoutForLeft -> mShowFrag = 1
                    mFrameLayoutForRight -> mShowFrag = 2
                }
            }
        })

        mMyAdapter = NtAdapter2(supportFragmentManager, list)
        mainRecyclerView.adapter = mMyAdapter
        mainRecyclerView.offscreenPageLimit = 2

        val mOffset = -this.resources.getDimension(R.dimen.dp_180)

        mainRecyclerView.setPageTransformer(false, ViewPager.PageTransformer { view, position ->
            val pageHeight = view.height
            val iv_picture = view.findViewById<ImageView>(R.id.iv_picture)
            val tv_sign = view.findViewById<View>(R.id.tv_sign) as TextView
            view.translationY = mOffset * position
            if (position < 1) {
                //移动文字
                if (position >= 0) {
                    val tv_title2 = view.findViewById<View>(R.id.tv_title2) as TextView
                    val layout_live_content = view.findViewById<View>(R.id.layout_live_content) as RelativeLayout

                    tv_title2.alpha = 1 - position
                    iv_picture.scaleX = ((1 - position) * 0.2 + 1).toFloat()
                    iv_picture.scaleY = ((1 - position) * 0.2 + 1).toFloat()
                    val live_content_offset: Float
                    if (tv_sign.text.toString().trim { it <= ' ' } == (list.size - 1).toString()) {
                        live_content_offset = pageHeight - this@MainActivity.resources.getDimension(R.dimen.dp_200)
                    } else {
                        live_content_offset = pageHeight - this@MainActivity.resources.getDimension(R.dimen.dp_300)
                    }
                    layout_live_content.translationY = live_content_offset * (1 - position)
                }
            } else {
                view.translationY = -pageHeight * (position - 1) + (mOffset + (position - 1) * (-mOffset / 2))
            }
        })
        registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                SSHApplication.instance.api!!.registerApp(SSHApplication.appId)
            }
        }, IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP))
    }

    fun getSharedPreferences(): SharedPreferenceUtils? {
        if (share == null) {
            share = SharedPreferenceUtils(this)
        }
        return share
    }


    private fun initUI() {
        mainRecyclerView = findViewById(R.id.vp_main)
        mDrawerLayout = findViewById(R.id.drawer_layout)
        mFrameLayoutForLeft = findViewById(R.id.layout_left)
        mFrameLayoutForRight = findViewById(R.id.layout_right)
        mIvMore = findViewById(R.id.iv_more)
        mIvPreson = findViewById(R.id.iv_person)
        mSSHProgressHUD = SSHProgressHUD.getInstance(this@MainActivity)
        mSSHProgressHUD.setMessage("获取数据中")
        mSSHProgressHUD.setCancelable(true)
    }

    private fun initData() {
        list.add(Bean("已上市系列", "2018春夏", "http://pic38.nipic.com/20140226/2457331_112835004364_2.jpg"))
        list.add(Bean("发布会", "2018/19秋冬", "http://pic21.nipic.com/20120428/9821123_210731511131_2.jpg"))
        list.add(Bean("已上市系列", "2018春夏预告系列", "http://pic31.nipic.com/20130706/12970162_195127182100_2.jpg"))
        list.add(Bean("已上市系列", "眼睛系列广告大片", "http://pic40.nipic.com/20140423/11693248_221326322000_2.jpg"))
        list.add(Bean("HAUTE COUTURE", "2018春夏", "http://pic26.nipic.com/20130116/1773545_152734135000_2.jpg"))
        list.add(Bean("发布会", "METIERE D'ART\nPARIS-HAMBURG\n2017/18", "http://pic19.nipic.com/20120305/9440725_185052574000_2.jpg"))
        mPresenter.getMainData(RequestInterface.REQUEST_MAIN_ID)
    }


    fun showLeftFragment(fragment: Fragment) {
        val transaction = mFragmentManager.beginTransaction()
        transaction.addToBackStack(null)
        transaction.replace(R.id.layout_left, fragment)
        transaction.commit()
    }

    fun showRightFragment(fragment: Fragment) {
        val transaction = mFragmentManager.beginTransaction()
        transaction.addToBackStack(null)
        transaction.replace(R.id.layout_right, fragment)
        transaction.commit()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        if (event.openId == AppConfig.FRAGMENT_RIGHT_OPEN_SET_ID) {
            showRightFragment(getSettingFragment()!!)
        } else if (event.openId == AppConfig.FRAGMENT_RIGHT_OPEN_NICK_NAME) {
            showRightFragment(getNameFragment(0)!!)
        } else if (event.openId == AppConfig.FRAGMENT_RIGHT_OPEN_NICK_PHONE) {
            showRightFragment(getNameFragment(1)!!)
        } else if (event.openId == AppConfig.FRAGMENT_RIGHT_OPEN_CLOSE) {
            mDrawerLayout.closeDrawer(mFrameLayoutForRight)
        } else if (event.openId == AppConfig.FRAGMENT_LEFT_OPEN_CLOSE) {
            mDrawerLayout.closeDrawer(mFrameLayoutForLeft)
        } else if (event.openId == AppConfig.MAIN_CHANGE_SEX_WOMEN) {
            mPresenter.setSexInfo(RequestInterface.REQUEST_MAIN_SEX, share!!.getValue(SharedPreferenceUtils.PREFERENCE_U_I)!!, "2")
        } else if (event.openId == AppConfig.MAIN_CHANGE_SEX_MAN) {
            mPresenter.setSexInfo(RequestInterface.REQUEST_MAIN_SEX, share!!.getValue(SharedPreferenceUtils.PREFERENCE_U_I)!!, "1")
        }
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    fun getSettingFragment(): SettingFragment? {
        if (mFragmentSet === null) {
            mFragmentSet = SettingFragment()
        }
        return mFragmentSet
    }

    fun getNameFragment(from: Int): SetNameFragment? {
        mFragmentNickName = SetNameFragment()
        var bundle = Bundle()
        bundle.putInt("from", from)
        mFragmentNickName!!.arguments = bundle
        return mFragmentNickName
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount <= 2) {
            when (mShowFrag) {
                1 -> {
                    mDrawerLayout.closeDrawer(mFrameLayoutForLeft)
                }
                2 -> {
                    mDrawerLayout.closeDrawer(mFrameLayoutForRight)
                }
                else -> {
                    finish()
                    Toast.makeText(this, "再次点击回退将推出", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            supportFragmentManager.popBackStack()
        }
    }


    override fun showLoading() {
//        super.showLoading()
        mSSHProgressHUD.show()
    }

    override fun hideLoading() {
//        super.hideLoading()
        mSSHProgressHUD.dismiss()
    }


}
