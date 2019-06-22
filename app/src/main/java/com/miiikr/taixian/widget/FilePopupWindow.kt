package com.ssh.net.ssh.widget

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.View
import android.widget.ImageView
import ccom.miiikr.taixian.`interface`.OnClickItemListener
import ccom.miiikr.taixian.entity.FlagEntity
import com.miiikr.taixian.R
import com.miiikr.taixian.`interface`.PopupClickListener
import com.miiikr.taixian.adapter.FileAdapter
import com.ssh.net.ssh.utils.ScreenUtils
import com.ssh.net.ssh.utils.SpannableUtils
import com.yo.lg.yocheck.widget.RecycleViewDivider

class FilePopupWindow(mContext: Context,type: Int,onClickItemListener: PopupClickListener) : BasePopupWindow(mContext, type,onClickItemListener) {

    lateinit var mIvDismiss: ImageView
    lateinit var mRvFiles: RecyclerView
    lateinit var mDatas: SparseArray<FlagEntity>
    var adapter: FileAdapter? = null

    override fun initData() {

    }

    override fun initListener(context: Context) {

    }

    override fun initView(context: Context, view: View, type: Int,clickItemListener: PopupClickListener) {
        mIvDismiss = view.findViewById(R.id.iv_dismiss)
        mRvFiles = view.findViewById(R.id.rv_file)

        mDatas = SparseArray()
        when(type){
            1->{
                mDatas.put(0, FlagEntity(context.resources.getString(R.string.chose_goods_files_one), false))
                mDatas.put(1, FlagEntity(context.resources.getString(R.string.chose_goods_files_two), false))
                mDatas.put(2, FlagEntity(context.resources.getString(R.string.chose_goods_files_three), false))
                mDatas.put(3, FlagEntity(context.resources.getString(R.string.chose_goods_files_four), false))
                mDatas.put(4, FlagEntity(context.resources.getString(R.string.chose_goods_files_five), false))
            }
            2->{
                mDatas.put(0, FlagEntity(context.resources.getString(R.string.chose_goods_files_one), false))
                mDatas.put(1, FlagEntity(context.resources.getString(R.string.chose_goods_notify_six), false))
                mDatas.put(2, FlagEntity(context.resources.getString(R.string.chose_goods_files_three), false))
                mDatas.put(3, FlagEntity(context.resources.getString(R.string.chose_goods_files_four), false))
                mDatas.put(4, FlagEntity(context.resources.getString(R.string.chose_goods_notify_seven), false))
            }
            3->{
                mDatas.put(0, FlagEntity(context.resources.getString(R.string.chose_goods_notify_a), false))
                mDatas.put(1, FlagEntity(context.resources.getString(R.string.chose_goods_notify_b), false))
                mDatas.put(2, FlagEntity(context.resources.getString(R.string.chose_goods_notify_c), false))
                mDatas.put(3, FlagEntity(context.resources.getString(R.string.chose_goods_notify_d), false))
                mDatas.put(4, FlagEntity(context.resources.getString(R.string.chose_goods_notify_e), false))
            }
        }
        var gridLayoutManager = GridLayoutManager(context, 3)
        mRvFiles.layoutManager = gridLayoutManager
        mRvFiles.addItemDecoration(RecycleViewDivider(
                context, LinearLayoutManager.VERTICAL, ScreenUtils.dp2px(context, 26), context.resources.getColor(R.color.color_ffffff)))
        adapter = FileAdapter(context, mDatas, object : OnClickItemListener {
            override fun clickItem(position: Int) {
                mDatas.get(position).click = !mDatas.get(position).click
                clickItemListener.onClick(position,type,mDatas[position].flag)
                adapter!!.notifyItemChanged(position)
            }
        })
        mRvFiles.adapter = adapter
        mIvDismiss.setOnClickListener {
            if(isShowing){
                dismiss()
            }
        }
    }

    override fun getContentViews(): Int {
        return R.layout.layout_popupwindow_files
    }
}