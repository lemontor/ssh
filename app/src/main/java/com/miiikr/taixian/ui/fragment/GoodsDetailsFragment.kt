package com.miiikr.taixian.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.miiikr.taixian.BaseMvp.View.BaseMvpFragment
import com.miiikr.taixian.BaseMvp.presenter.DetailsPresenter
import com.miiikr.taixian.R

class GoodsDetailsFragment:BaseMvpFragment<DetailsPresenter>() {

    companion object{
        fun newInstance(imgUrl:String):GoodsDetailsFragment{
            val bundle = Bundle()
            bundle.putString("img",imgUrl)
            val fragment = GoodsDetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    lateinit var mIvGoods:ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contentView = inflater.inflate(R.layout.fragment_goods_details,container,false)
        mIvGoods = contentView.findViewById(R.id.iv_goods)
        return contentView
    }

}