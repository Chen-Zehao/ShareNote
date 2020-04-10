package com.scnu.sharenote.detail.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.scnu.base.ui.activity.BaseMvpActivity;
import com.scnu.base.ui.view.titlebar.BaseTitleBar;
import com.scnu.custom.CircleImageView;
import com.scnu.eventbusmodel.AddCommentEvent;
import com.scnu.eventbusmodel.DeleteArticleEvent;
import com.scnu.model.ArticleModel;
import com.scnu.model.CommentModel;
import com.scnu.model.Macro;
import com.scnu.model.UserModel;
import com.scnu.sharenote.R;
import com.scnu.sharenote.detail.adapter.CommentAdapter;
import com.scnu.sharenote.detail.adapter.HeaderImgAdapter;
import com.scnu.sharenote.detail.presenter.DetailPresenter;
import com.scnu.sharenote.detail.ui.dialog.DeleteArticleDialog;
import com.scnu.sharenote.main.fragment.mine.ui.activity.OtherUserActivity;
import com.scnu.utils.DateUtils;
import com.scnu.utils.GlideImageLoader;
import com.scnu.utils.KeyBoardUtils;
import com.scnu.utils.LogUtils;
import com.scnu.utils.MyApplication;
import com.scnu.utils.ServerConfig;
import com.scnu.utils.SoftKeyBoardListener;
import com.scnu.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ChenZehao
 * on 2020/1/18
 */
public class DetailActivity extends BaseMvpActivity<IDetailView, DetailPresenter> implements IDetailView {

    @BindView(R.id.iv_back)
    AppCompatImageView ivBack;
    @BindView(R.id.iv_avatar)
    CircleImageView ivAvatar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_follow)
    TextView tvFollow;
    @BindView(R.id.iv_share)
    AppCompatImageView ivShare;
    @BindView(R.id.vp_header_img)
    ViewPager vpHeaderImg;
    @BindView(R.id.tv_index)
    TextView tvIndex;
    @BindView(R.id.cl_title_bar)
    ConstraintLayout clTitleBar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.fl_title)
    FrameLayout flTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.fl_content)
    FrameLayout flContent;
    @BindView(R.id.iv_location)
    AppCompatImageView ivLocation;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.cl_location)
    ConstraintLayout clLocation;
    @BindView(R.id.cl_main)
    ConstraintLayout clMain;
    @BindView(R.id.tv_comment)
    TextView tvComment;
    @BindView(R.id.tv_comment_total_num)
    TextView tvCommentTotalNum;
    @BindView(R.id.cl_comment)
    ConstraintLayout clComment;
    @BindView(R.id.rv_comment)
    RecyclerView rvComment;
    @BindView(R.id.nsv_main)
    NestedScrollView nsvMain;
    @BindView(R.id.iv_edit)
    AppCompatImageView ivEdit;
    @BindView(R.id.cl_edit)
    ConstraintLayout clEdit;
    @BindView(R.id.iv_collection)
    AppCompatImageView ivCollection;
    @BindView(R.id.tv_collection_num)
    TextView tvCollectionNum;
    @BindView(R.id.iv_like)
    AppCompatImageView ivLike;
    @BindView(R.id.tv_like_num)
    TextView tvLikeNum;
    @BindView(R.id.iv_comment)
    AppCompatImageView ivComment;
    @BindView(R.id.tv_comment_num)
    TextView tvCommentNum;
    @BindView(R.id.cl_bottom)
    ConstraintLayout clBottom;
    @BindView(R.id.tv_delete)
    TextView tvDelete;
    @BindView(R.id.et_bottom)
    EditText etBottom;
    @BindView(R.id.v_mask)
    View vMask;
    @BindView(R.id.tv_send)
    TextView tvSend;
    @BindView(R.id.cl_comment_popup)
    ConstraintLayout clCommentPopup;

    private ArticleModel article;

    private List<CommentModel> commentList;

    private HeaderImgAdapter headerImgAdapter;

    private List<ImageView> imageViews = new ArrayList<>();

    private UserModel user;

    private DeleteArticleDialog deleteArticleDialog;

    private SoftKeyBoardListener softKeyBoardListener;

    private CommentAdapter commentAdapter;

    private String issueId;

    /**
     * 父评论id
     */
    private String pid="";
    /**
     * 该评论发布者的名称
     */
    private String commentUserName="";
    /**
     * 该评论发布者的id
     */
    private String commentReplyUserId="";

    @Override
    public int getLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    public void initView() {
        if(!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        user = (UserModel) MyApplication.getObject(Macro.KEY_USER);
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            issueId = getIntent().getStringExtra("issueId");
            presenter.getArticleDetail(issueId, user.getUserId());
        }
        commentList = new ArrayList<>();
        deleteArticleDialog = new DeleteArticleDialog();
        deleteArticleDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.centerDialog);
        setSoftKeyBoardListener();
        commentAdapter = new CommentAdapter(mContext,commentList);
        rvComment.setLayoutManager(new LinearLayoutManager(mContext,RecyclerView.VERTICAL,false));
        rvComment.setAdapter(commentAdapter);
    }


    @Override
    public void initData() {
    }

    /**
     * setSoftKeyBoard()
     * 设置软键盘显示/隐藏
     */
    private void setSoftKeyBoard() {
        //自动设置软键盘显示或隐藏
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * setSoftKeyBoardListener()
     * 监听软键盘显示/隐藏
     */
    private void setSoftKeyBoardListener() {
        //软键盘显示/隐藏监听
        softKeyBoardListener = new SoftKeyBoardListener(this);
        softKeyBoardListener.setListener(new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                //此处的height为软键盘的高度
                //软键盘显示，底部发送消息和灰色背景显示，EditText获取焦点
                clCommentPopup.setVisibility(View.VISIBLE);
                clBottom.setVisibility(View.GONE);
                vMask.setVisibility(View.VISIBLE);
                etBottom.setFocusable(true);
                etBottom.setFocusableInTouchMode(true);
                etBottom.setCursorVisible(true);
                etBottom.requestFocus();
                if(TextUtils.isEmpty(commentUserName)){
                    etBottom.setHint("");
                }else{
                    etBottom.setHint("回复 @"+commentUserName);
                }
            }

            @Override
            public void keyBoardHide(int height) {
                //软键盘隐藏，底部发送消息和灰色背景隐藏，EditText失去焦点
                clCommentPopup.setVisibility(View.GONE);
                clBottom.setVisibility(View.VISIBLE);
                vMask.setVisibility(View.GONE);
                etBottom.setFocusable(false);
                etBottom.setFocusableInTouchMode(false);
                etBottom.setCursorVisible(false);
                commentUserName = "";
                commentReplyUserId = "";
                pid = "";
            }
        });
    }


    @Override
    protected BaseTitleBar getTitleBar() {
        return null;
    }

    @Override
    public DetailPresenter initPresenter() {
        return new DetailPresenter();
    }



    /**
     * 显示Banner图
     */
    private void initBannerView() {
        if (null == article.getImageList() || article.getImageList().isEmpty()) {
            return;
        }else{
            vpHeaderImg.setVisibility(View.VISIBLE);
        }
        for (String item : article.getImageList()) {
            ImageView iv = new ImageView(mContext);
            GlideImageLoader.getInstance().loadImage(mContext, ServerConfig.getInstance().getAppServerURL() + item, iv);
            imageViews.add(iv);
        }
        headerImgAdapter = new HeaderImgAdapter(imageViews, vpHeaderImg);
        vpHeaderImg.setAdapter(headerImgAdapter);
        if (imageViews.size() != 0) {
            tvIndex.setText(1 + "/" + imageViews.size());
        } else {
            tvIndex.setVisibility(View.GONE);
        }
        vpHeaderImg.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                tvIndex.setText((position + 1) + "/" + imageViews.size());
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }


    /**
     * 显示内容信息
     */
    private void initInfoView() {
        GlideImageLoader.getInstance().loadImage(mContext, ServerConfig.getInstance().getAppServerURL() + article.getUserAvatar(), ivAvatar);
        String strTitle = article.getTitle();
        if (!TextUtils.isEmpty(article.getTheme())) {
            String strTheme = "#" + article.getTheme() + "#";
            ForegroundColorSpan themeSpan = new ForegroundColorSpan(mContext.getResources().getColor(R.color.text_blue));
            ForegroundColorSpan contentSpan = new ForegroundColorSpan(mContext.getResources().getColor(R.color.text_title));
            //使用SpannableString实现字体分颜色
            SpannableString str = new SpannableString(strTheme + "  " + strTitle);
            str.setSpan(themeSpan, 0, strTheme.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            str.setSpan(contentSpan, strTheme.length() + 2, strTheme.length() + strTitle.length() + 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            str.setSpan(new StyleSpan(Typeface.BOLD), strTheme.length() + 2, strTheme.length() + strTitle.length() + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvTitle.setText(str);
        } else {
            tvTitle.setText(strTitle);
        }
        tvName.setText(article.getUserName());
        tvTime.setText(article.getTime());
        tvContent.setText(article.getContent());
        if (null == article.getLocation() || TextUtils.isEmpty(article.getLocation().getCity())) {
            clLocation.setVisibility(View.GONE);
        } else {
            clLocation.setVisibility(View.VISIBLE);
            tvLocation.setText(article.getLocation().getName());
        }
        if (article.isLikeFlag()) {
            ivLike.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_common_like_selected));
        } else {
            ivLike.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_common_like));
        }
        if (article.isCollectionFlag()) {
            ivCollection.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_common_collection_selected));
        } else {
            ivCollection.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_common_collection));
        }
        tvLikeNum.setText(article.getLikeNum() + "");
        tvCollectionNum.setText(article.getCollectionNum() + "");
        tvCommentNum.setText(article.getCommentNum() + "");
        tvCommentTotalNum.setText("（"+article.getCommentNum()+"）");
        if (article.getUserId().equals(user.getUserId())) {
            tvFollow.setVisibility(View.GONE);
        } else {
            tvFollow.setVisibility(View.VISIBLE);
        }
        if (article.isAttentionFlag()) {
            tvFollow.setText("取消关注");
            tvFollow.setTextColor(mContext.getResources().getColor(R.color.text_gray));
            tvFollow.setBackground(mContext.getResources().getDrawable(R.drawable.mine_shape_has_followed));
        } else {
            tvFollow.setText("＋关注");
            tvFollow.setTextColor(mContext.getResources().getColor(R.color.text_orange));
            tvFollow.setBackground(mContext.getResources().getDrawable(R.drawable.mine_shape_follow));
        }
        if (article.getUserId().equals(user.getUserId())) {
            tvDelete.setVisibility(View.VISIBLE);
        } else {
            tvDelete.setVisibility(View.GONE);
        }
        deleteArticleDialog.setDeleteArticleListener(new DeleteArticleDialog.DeleteArticleListener() {
            @Override
            public void delete() {
                if (null != article) {
                    presenter.deleteArticle(article.getIssueId());
                }
            }
        });
    }

    @OnClick(R.id.iv_avatar)
    void ivAvatarClicked() {
        Intent intent = new Intent(mContext, OtherUserActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Macro.KEY_USER_ID, article.getUserId());
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    @OnClick({R.id.iv_like, R.id.tv_like_num})
    void likeClicked() {
        presenter.addLike(article.getIssueId(), user.getUserId());
    }

    @OnClick({R.id.iv_collection, R.id.tv_collection_num})
    void collectionClicked() {
        presenter.addCollection(article.getIssueId(), user.getUserId());
    }

    @OnClick(R.id.tv_follow)
    void followClicked() {
        presenter.addAttention(article.getUserId(), user.getUserId());
    }

    @OnClick(R.id.iv_back)
    void ivBackClicked() {
        finish();
    }

    @OnClick(R.id.tv_delete)
    void tvDeleteClicked() {
        deleteArticleDialog.show(getSupportFragmentManager(), null);
    }

    @OnClick(R.id.cl_edit)
    void clEditClicked() {
        /**
         * 弹出软键盘
         */
        setSoftKeyBoard();
    }

    @OnClick(R.id.v_mask)
    void vMaskClicked() {
        /**
         * 隐藏软键盘
         */
        setSoftKeyBoard();
    }

    @OnClick(R.id.tv_send)
    void tvSendClicked(){
        if(TextUtils.isEmpty(etBottom.getText().toString())){
            return;
        }
        presenter.addComment(article.getIssueId(), etBottom.getText().toString(),pid,commentReplyUserId, DateUtils.getCurTime(), user.getUserId());
        etBottom.setText("");
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCommentMessageEvent(AddCommentEvent event){
        pid = event.getPid();
        commentUserName = event.getUserName();
        commentReplyUserId = event.getReplyUserId();
        setSoftKeyBoard();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void getDetailSuccess(ArticleModel article) {
        this.article = article;
        initBannerView();
        initInfoView();
        presenter.findComment(article.getIssueId());
    }

    @Override
    public void addLikeSuccess() {
        article.setLikeFlag(!article.isLikeFlag());
        if (article.isLikeFlag()) {
            article.setLikeNum(article.getLikeNum() + 1);
        } else {
            article.setLikeNum(article.getLikeNum() - 1);
        }
        if (article.isLikeFlag()) {
            ivLike.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_common_like_selected));
        } else {
            ivLike.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_common_like));
        }
        tvLikeNum.setText(article.getLikeNum() + "");
    }

    @Override
    public void addCollectionSuccess() {
        article.setCollectionFlag(!article.isCollectionFlag());
        if (article.isCollectionFlag()) {
            article.setCollectionNum(article.getCollectionNum() + 1);
        } else {
            article.setCollectionNum(article.getCollectionNum() - 1);
        }
        if (article.isCollectionFlag()) {
            ivCollection.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_common_collection_selected));
        } else {
            ivCollection.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_common_collection));
        }
        tvCollectionNum.setText(article.getCollectionNum() + "");
    }

    @Override
    public void addAttentionSuccess() {
        article.setAttentionFlag(!article.isAttentionFlag());
        if (article.isAttentionFlag()) {
            tvFollow.setText("取消关注");
            tvFollow.setTextColor(mContext.getResources().getColor(R.color.text_gray));
            tvFollow.setBackground(mContext.getResources().getDrawable(R.drawable.mine_shape_has_followed));
        } else {
            tvFollow.setText("＋关注");
            tvFollow.setTextColor(mContext.getResources().getColor(R.color.text_orange));
            tvFollow.setBackground(mContext.getResources().getDrawable(R.drawable.mine_shape_follow));
        }
    }

    @Override
    public void deleteArticleSuccess() {
        ToastUtils.showToast(mContext, "删除文章成功");
        EventBus.getDefault().post(new DeleteArticleEvent(article.getIssueId()));
        finish();
    }

    @Override
    public void findCommentSuccess(List<CommentModel> commentList) {
        this.commentList.clear();
        this.commentList.addAll(commentList);
        commentAdapter.notifyDataSetChanged();
    }

    @Override
    public void addCommentSuccess() {
        ToastUtils.showToast(mContext, "评论成功");
        presenter.findComment(article.getIssueId());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
