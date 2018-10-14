package com.kevinj1008.fitnessch.detail;

import com.kevinj1008.fitnessch.BasePresenter;
import com.kevinj1008.fitnessch.BaseView;
import com.kevinj1008.fitnessch.objects.Article;
import com.kevinj1008.fitnessch.objects.Meal;
import com.kevinj1008.fitnessch.objects.Schedule;

import java.util.List;

public interface DetailContract {

    interface View extends BaseView<Presenter> {

        void setToolbarVisibility(boolean visible);

        void showArticle(Article article);

        void showSchedule(List<Schedule> schedules);

        void showMeal(List<Meal> meals);

//        void setLoadMoreButtonVisibility(boolean visible);
//
//        void showEditOptionDialog(String commentId, String comment, int position);
//
//        void showEditCommentDialog(String commentId, String comment, int position);

//        void updateCommentItem(int position, Comment comment);
//
//        void deleteCommentItem(int position);

        void refreshUi();

//        void moveToCommentPosition(int position);

//        void showFriendUi(Article article);

    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void loadSchedule();

        void loadMeal();

//        void loadComments();

//        void updateInterestedIn(Article article, boolean isInterestedIn);

        void showToolbar();

        void hideToolbar();

        void refreshDetailUi();

//        void clickItemOptions(String commentId, String comment, int position);
//
//        void clickEditComment(String commentId, String comment, int position);
//
//        void clickDeleteComment(String commentId, int position);

//        void sendComment(String comment);

//        void sendEditComment(String commentId, String comment, int position);

//        void loadCommentsAndMoveToEnd();

//        void openFriend(Article article);
    }
}
