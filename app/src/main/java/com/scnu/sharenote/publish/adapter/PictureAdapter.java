package com.scnu.sharenote.publish.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.DateUtils;
import com.luck.picture.lib.tools.StringUtils;
import com.scnu.base.BaseRecycleAdapter;
import com.scnu.base.BaseViewHolder;
import com.scnu.sharenote.R;
import com.scnu.utils.LogUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ChenZehao
 * on 2020/2/22
 */
public class PictureAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    public static final int TYPE_CAMERA = 1;
    public static final int TYPE_PICTURE = 2;

    @BindView(R.id.iv_pic)
    AppCompatImageView ivPic;
    @BindView(R.id.iv_delete)
    AppCompatImageView ivDelete;

    private Context mContext;

    private int mLayoutId;

    private int selectMax = 9;

    private List<LocalMedia> list = new ArrayList<>();

    public PictureAdapter(Context context) {
        mContext = context;
        mLayoutId = R.layout.item_filter_image;
    }

    public void setList(List<LocalMedia> list) {
        this.list = list;
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(mLayoutId, viewGroup, false);
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
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        ButterKnife.bind(this,holder.itemView);
        if (getItemViewType(holder.getAdapterPosition()) == TYPE_CAMERA) {
            ivPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnAddPicClickListener.onAddPicClick();
                }
            });
            ivDelete.setVisibility(View.INVISIBLE);
        } else {
            ivDelete.setVisibility(View.VISIBLE);
            ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = holder.getAdapterPosition();
                    if (index != RecyclerView.NO_POSITION) {
                        list.remove(index);
                        notifyItemRemoved(index);
                        notifyItemRangeChanged(index, list.size());
                    }
                }
            });
            LocalMedia media = list.get(holder.getAdapterPosition());
            String path = "";
            if (media.isCut() && !media.isCompressed()) {
                // 裁剪过
                path = media.getCutPath();
            } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
                // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                path = media.getCompressPath();
            } else {
                // 原图
                path = media.getPath();
            }
            // 图片
            if (media.isCompressed()) {
                Log.i("compress image result:", new File(media.getCompressPath()).length() / 1024 + "k");
                Log.i("压缩地址::", media.getCompressPath());
            }

            Log.i("原图地址::", media.getPath());
            if (media.isCut()) {
                Log.i("裁剪地址::", media.getCutPath());
            }
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.color.color_f6)
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(holder.itemView.getContext())
                    .load(path)
                    .apply(options)
                    .into(ivPic);
        }
    }

    @Override
    public int getItemCount() {
        if (list.size() < selectMax) {
            return list.size() + 1;
        } else {
            return list.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowAddItem(position)) {
            return TYPE_CAMERA;
        } else {
            return TYPE_PICTURE;
        }
    }

    private boolean isShowAddItem(int position) {
        int size = list.size();
        return position == size;
    }


    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    private OnItemClickListener onItemClickListener;//单击事件

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    /**
     * 点击添加图片跳转
     */
    private OnAddPicClickListener mOnAddPicClickListener;

    public interface OnAddPicClickListener {
        void onAddPicClick();
    }

    public void setmOnAddPicClickListener(OnAddPicClickListener mOnAddPicClickListener) {
        this.mOnAddPicClickListener = mOnAddPicClickListener;
    }
}
