package com.hyphenate.easeui.widget.chatrow;

import android.content.Context;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.R;

public class EaseChatRowGoodsInfo extends EaseChatRow {

    ImageView img;
    TextView title;
    TextView money;

    public EaseChatRowGoodsInfo(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ?
                R.layout.ease_row_goods : R.layout.ease_row_goods, this);
    }

    @Override
    protected void onFindViewById() {
        img = findViewById(R.id.iv_pic);
        title = findViewById(R.id.tv_title);
        money = findViewById(R.id.tv_money);

    }

    @Override
    protected void onViewUpdate(EMMessage msg) {
        if(msg != null){
            Log.e("tag_onViewUpdate",msg.toString());
        }else{
            Log.e("tag_onViewUpdate","msg is null");
        }

    }

    @Override
    protected void onSetUpView() {
        Log.e("tag_onSetUpView","onSetUpView");

    }
}
