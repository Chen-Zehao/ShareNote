package com.scnu.sharenote.detail.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.scnu.base.BaseRecycleAdapter;
import com.scnu.base.BaseViewHolder;
import com.scnu.custom.CircleImageView;
import com.scnu.eventbusmodel.AddCommentEvent;
import com.scnu.model.CommentModel;
import com.scnu.model.Macro;
import com.scnu.model.SecondaryCommentModel;
import com.scnu.model.UserModel;
import com.scnu.sharenote.R;
import com.scnu.sharenote.detail.ui.dialog.DeleteCommentDialog;
import com.scnu.source.beans.BaseBean;
import com.scnu.source.http.CustomObserver;
import com.scnu.source.http.OnlineDataSource;
import com.scnu.utils.GlideImageLoader;
import com.scnu.utils.MyApplication;
import com.scnu.utils.ServerConfig;
import com.scnu.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ChenZehao
 * on 2020/4/9
 */
public class SecondaryCommentAdapter extends BaseRecycleAdapter<SecondaryCommentModel> {

    @BindView(R.id.iv_avatar)
    CircleImageView ivAvatar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_reply)
    TextView tvReply;
    @BindView(R.id.tv_delete)
    TextView tvDelete;

    private String pid;

    private DeleteCommentDialog deleteCommentDialog;


    public SecondaryCommentAdapter(Context context, List<SecondaryCommentModel> data,String pid) {
        super(context, R.layout.detail_item_secondary_comment, data);
        this.pid = pid;
    }

    @Override
    protected <T> void bindView(BaseViewHolder holder, T t) {
        ButterKnife.bind(this, holder.itemView);
        SecondaryCommentModel comment = (SecondaryCommentModel) t;
        UserModel user = (UserModel) MyApplication.getObject(Macro.KEY_USER);
        GlideImageLoader.getInstance().loadImage(mContext, ServerConfig.getInstance().getAppServerURL() + comment.getUserAvatar(), ivAvatar);
        tvName.setText(comment.getUserName());
        if(TextUtils.isEmpty(comment.getReplyUserName())){
            tvContent.setText(comment.getContent());
        }else{
            tvContent.setText("回复 "+comment.getReplyUserName()+":"+comment.getContent());
        }
        tvTime.setText(comment.getTime());
        if (comment.getUserId().equals(user.getUserId())) {
            tvDelete.setVisibility(View.VISIBLE);
            tvReply.setVisibility(View.GONE);
        } else {
            tvDelete.setVisibility(View.GONE);
            tvReply.setVisibility(View.VISIBLE);
        }
        tvReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new AddCommentEvent(pid, comment.getUserName(), comment.getUserId()));
            }
        });
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCommentDialog = new DeleteCommentDialog();
                deleteCommentDialog.setStyle(DialogFragment.STYLE_NORMAL,R.style.centerDialog);
                deleteCommentDialog.setDeleteCommentListener(new DeleteCommentDialog.DeleteCommentListener() {
                    @Override
                    public void delete() {
                        OnlineDataSource.getInstance().deleteComment(comment.getCommentId(), new CustomObserver<BaseBean>(mContext, ((AppCompatActivity) mContext).getSupportFragmentManager()) {
                            @Override
                            public void onSuccess(BaseBean result) {
                                ToastUtils.showToast(mContext, "评论删除成功");
                                mData.remove(holder.getAdapterPosition());
                                notifyDataSetChanged();
                            }

                            @Override
                            public void onFail(Throwable exception) {
                                super.onFail(exception);
                                ToastUtils.showToast(mContext, exception.getMessage());
                            }
                        });
                    }
                });
                deleteCommentDialog.show(((AppCompatActivity)mContext).getSupportFragmentManager(),null);
            }
        });
    }
}
