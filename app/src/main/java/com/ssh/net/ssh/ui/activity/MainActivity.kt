package com.ssh.net.ssh.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageView
import com.ssh.net.mainview.bean.ColorBean
import com.ssh.net.mainview.view.ChanelView
import com.ssh.net.ssh.R
import com.ssh.net.ssh.adapter.MyAdapterDemo
import com.ssh.net.ssh.ui.fragment.MainFragmentLeft
import com.ssh.net.ssh.ui.fragment.MainFragmentRight
import java.util.ArrayList
import android.widget.Toast
import com.ssh.net.ssh.BaseMvp.View.BaseMvpActivity
import com.ssh.net.ssh.BaseMvp.presenter.MainPresenter
import com.ssh.net.ssh.entity.MessageEvent
import com.ssh.net.ssh.ui.fragment.SetNameFragment
import com.ssh.net.ssh.ui.fragment.SettingFragment
import com.ssh.net.ssh.utils.AppConfig
import com.ssh.net.ssh.utils.IntentUtils
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class MainActivity : BaseMvpActivity<MainPresenter>(), MyAdapterDemo.OnItemClickListener {

    override fun onItemClick(v: View, position: Int) {
    }

    lateinit var mainRecyclerView: ChanelView
    lateinit var mDrawerLayout: DrawerLayout
    lateinit var mIvMore: ImageView
    lateinit var mIvPreson: ImageView
    lateinit var mFrameLayoutForLeft: FrameLayout
    lateinit var mFrameLayoutForRight: FrameLayout
    var list: MutableList<ColorBean> = ArrayList<ColorBean>()

    var mCurLeftFragment: Fragment
    var mCurRightFragment: Fragment

    var mFragmentManager: FragmentManager

    var mFragmentMore: MainFragmentLeft
    var mFragmentPerson: MainFragmentRight

    var myAdapter: MyAdapterDemo? = null

    var mFragmentSet: SettingFragment? = null
    var mFragmentNickName: SetNameFragment? = null


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
    }

    private fun initUI() {
        mainRecyclerView = findViewById(R.id.rv_main)
        mDrawerLayout = findViewById(R.id.drawer_layout)
        mFrameLayoutForLeft = findViewById(R.id.layout_left)
        mFrameLayoutForRight = findViewById(R.id.layout_right)
        mIvMore = findViewById(R.id.iv_more)
        mIvPreson = findViewById(R.id.iv_person)
        var linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mainRecyclerView.layoutManager = linearLayoutManager
    }

    private fun initData() {
        list.add(ColorBean(R.drawable.img_one, 1))
        list.add(ColorBean(R.drawable.img_two, 1))
        list.add(ColorBean(R.drawable.img_three, 1))
        list.add(ColorBean(R.drawable.img_four, 1))
        list.add(ColorBean(R.drawable.img_five, 1))

        myAdapter = MyAdapterDemo(mainRecyclerView!!, list, this)
        mainRecyclerView!!.adapter = myAdapter
        myAdapter!!.addOnItemClickListener(this)
        runLayoutAnimation(mainRecyclerView)
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

    fun runLayoutAnimation(recyclerView: RecyclerView) {
        val context = recyclerView.context
        val controller = AnimationUtils.loadLayoutAnimation(context, R.anim.recycler_animation)

        recyclerView.layoutAnimation = controller
        recyclerView.adapter!!.notifyDataSetChanged()
        recyclerView.scheduleLayoutAnimation()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        when {
            event.openId == AppConfig.FRAGMENT_RIGHT_OPEN_SET_ID -> showRightFragment(getSettingFragment()!!)
            event.openId == AppConfig.FRAGMENT_RIGHT_OPEN_NICK_NAME -> showRightFragment(getNameFragment(0)!!)
            event.openId == AppConfig.FRAGMENT_RIGHT_OPEN_NICK_PHONE -> showRightFragment(getNameFragment(1)!!)
            event.openId == AppConfig.FRAGMENT_RIGHT_OPEN_CLOSE -> mDrawerLayout.closeDrawer(mFrameLayoutForRight)
            event.openId == AppConfig.FRAGMENT_LEFT_OPEN_CLOSE -> mDrawerLayout.closeDrawer(mFrameLayoutForLeft)
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


}
