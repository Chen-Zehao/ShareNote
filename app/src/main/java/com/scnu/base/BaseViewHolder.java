package com.scnu.base;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.ButterKnife;

/**
  *
  * @Package:        com.scnu.base
  * @ClassName:      BaseViewHolder
  * @Description:    基础适配器Holder
  * @Author:         ChenZehao
  * @CreateDate:     2020/1/17 0:03
  * @UpdateUser:     更新者：
  * @UpdateDate:     2020/1/17 0:03
  * @UpdateRemark:   更新说明：
  * @Version:        1.0
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {

    public Context context;
    public View view;

    public BaseViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        this.view = itemView;
    }

    public void setText(int id, String text) {
        TextView tv = (TextView) view.findViewById(id);
        tv.setText(text);
    }

    public void setImageResource(int id, int resouceId) {
        view.findViewById(id).setBackgroundResource(resouceId);
    }

    public void setColorResource(int id, int colorId) {
        view.findViewById(id).setBackgroundColor(colorId);
    }

    public void serVisiableity(int id, int visiableId){
        view.findViewById(id).setVisibility(visiableId);
    }
}