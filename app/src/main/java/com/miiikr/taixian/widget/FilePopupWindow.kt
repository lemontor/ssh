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
import com.miiikr.taixian.entity.ChoseEntity
import com.ssh.net.ssh.utils.ScreenUtils
import com.ssh.net.ssh.utils.SpannableUtils
import com.yo.lg.yocheck.widget.RecycleViewDivider

class FilePopupWindow(mContext: Context,type: Int,onClickItemListener: PopupClickListener,datas:ArrayList<ChoseEntity>) : BasePopupWindow2(mContext, type,onClickItemListener,datas) {

    lateinit var mIvDismiss: ImageView
    lateinit var mRvFiles: RecyclerView
//    lateinit var mDatas: SparseArray<FlagEntity>
    var adapter: FileAdapter? = null

    override fun initData() {

    }

    override fun initListener(context: Context) {

    }

    override fun initView(context: Context, view: View, type: Int,clickItemListener: PopupClickListener,datas:ArrayList<ChoseEntity>) {
        mIvDismiss = view.findViewById(R.id.iv_dismiss)
        mRvFiles = view.findViewById(R.id.rv_file)
        var gridLayoutManager = GridLayoutManager(context, 3)
        mRvFiles.layoutManager = gridLayoutManager
        mRvFiles.addItemDecoration(RecycleViewDivider(
                context, LinearLayoutManager.VERTICAL, ScreenUtils.dp2px(context, 26), context.resources.getColor(R.color.color_ffffff)))
        adapter = FileAdapter(context, datas, object : OnClickItemListener {
            override fun clickItem(position: Int) {
                datas[position].isCheck = !datas[position].isCheck
                clickItemListener.onClick(position,type,datas[position].flag)
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