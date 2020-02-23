package com.scnu.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


/**
  *
  * @Package:        com.scnu.base
  * @ClassName:      BaseRecycleAdapter
  * @Description:    基础适配器
  * @Author:         ChenZehao
  * @CreateDate:     2020/1/17 0:03
  * @UpdateUser:     更新者：
  * @UpdateDate:     2020/1/17 0:03
  * @UpdateRemark:   更新说明：
  * @Version:        1.0
 */
public abstract class BaseRecycleAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    protected Context mContext;
    private int mLayoutId;
    protected List<T> mData;

    /**
     * @param context //上下文
     * @param layoutId  //布局id
     * @param data  //数据源
     */
    public BaseRecycleAdapter(Context context, int layoutId, List<T> data) {
        this.mContext = context;
        this.mLayoutId = layoutId;
        this.mData = data;
        if (mData == null){
            mData = new ArrayList<>();
        }
    }

    public void notifyDataSetChanged(List<T> data){
        if (data == null){
            this.mData = new ArrayList<>();
        }else{
            this.mData = data;
        }
        notifyDataSetChanged();
    }

    public T getItemData(int position){
        return mData.get(position);
    }

    /**
     * viewholder
     * */

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(mLayoutId, parent, false);
        final BaseViewHolder holder = new BaseViewHolder(mContext,view);
        //单击事件回调
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null){
                    onItemClickListener.onItemClick(v,holder.getLayoutPosition());
                }
            }
        });
        //单击长按事件回调
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemLongClickListener != null) {
                    onItemLongClickListener.onItemLongClick(v, holder.getLayoutPosition());
                }
                return false;
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        bindView(holder,mData.get(position));
    }

    protected abstract <T> void bindView(BaseViewHolder holder,T t);

    @Override
    public int getItemCount() {
        return mData.size();
    }

    /**
     * 响应事件
     */

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View v, int position);
    }

    private OnItemClickListener onItemClickListener;//单击事件
    private OnItemLongClickListener onItemLongClickListener;//长按单击事件

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }
}
