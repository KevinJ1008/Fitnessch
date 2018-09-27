package com.kevinj1008.fitnessch.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kevinj1008.fitnessch.R;
import com.kevinj1008.fitnessch.api.beans.GetArticles;
import com.kevinj1008.fitnessch.main.MainContract;
import com.kevinj1008.fitnessch.objects.Article;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class MainAdapter extends RecyclerView.Adapter {

    private MainContract.Presenter mPresenter;
    private ArrayList<Article> mArticles;
    private int mNextPaging;

    public MainAdapter(GetArticles bean, MainContract.Presenter presenter) {
        mPresenter = presenter;
        this.mArticles = bean.getArticles();
        this.mNextPaging = bean.getPaging();
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
        return new MainItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        Picasso.get()
//                .load(mArticles.get(position).getAuthor().getImage())
//                .placeholder(R.drawable.all_placeholder_avatar)
//                .transform(new CropCircleTransformation())
//                .fit()
//                .into(((MainItemViewHolder) holder).mAuthorImage);

        //
        ((MainItemViewHolder) holder).mAuthorName.setText(mArticles.get(position).getName());
        //
        String date = new SimpleDateFormat("HH:mm")
                .format(new Date(mArticles.get(position).getCreatedTime() * 1000L));
        ((MainItemViewHolder) holder).mArticleCreatedTime.setText(date);
        ((MainItemViewHolder) holder).mArticleTitle.setText(mArticles.get(position).getTitle());
        ((MainItemViewHolder) holder).mArticleContent.setText(mArticles.get(position).getContent());
        if (mArticles.get(position).getTag().equals("課表")) {
            ((MainItemViewHolder) holder).mArticleTagIcon.setImageResource(R.drawable.icon_dumbbell);
        } else {
            ((MainItemViewHolder) holder).mArticleTagIcon.setImageResource(R.drawable.icon_meal);
        }
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    private class MainItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView mAuthorImage;
        private TextView mAuthorName;
        private TextView mArticleCreatedTime;
        private TextView mArticleTitle;
        private TextView mArticleContent;
//        private TextView mArticleTag;
        private ImageView mArticleTagIcon;

        public MainItemViewHolder(View itemView) {
            super(itemView);

            mAuthorImage = itemView.findViewById(R.id.main_author_image);
            mAuthorName = itemView.findViewById(R.id.main_author_name);
            mArticleCreatedTime = itemView.findViewById(R.id.main_create_time);
            mArticleTitle = itemView.findViewById(R.id.main_title);
            mArticleContent = itemView.findViewById(R.id.main_content);
//            mArticleTag = itemView.findViewById(R.id.article_tag);
            mArticleTagIcon = itemView.findViewById(R.id.article_tag_icon);
        }
    }

    public void updateData(Article bean) {
//        Log.d(Constants.TAG, "MainAdapter update data");
//        for (Article article : bean.getArticles()) {
            mArticles.add(bean);
//        }

//        setNextPaging(bean.getPaging());
        notifyDataSetChanged();
    }

    public void initData() {
        mArticles.clear();
    }
}
