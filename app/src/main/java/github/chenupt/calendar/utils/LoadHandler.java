package github.chenupt.calendar.utils;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by chenupt@gmail.com on 2015/1/2.
 * Description : Handle the state of views.
 */
public class LoadHandler {

    public static final int TIME_ANIM_DURING = 300;

    private Builder builder;
    private ObjectAnimator showAnim;
    private ObjectAnimator hideAnim;

    public LoadHandler(Builder builder) {
        this.builder = builder;
    }

    public void showProgress() {
        visibleLayout(builder.progressLayout);
    }

    public void showContent() {
        visibleLayout(builder.contentLayout);
    }

    public void showEmpty() {
        visibleLayout(builder.emptyTextView);
    }

    public void showError() {
        visibleLayout(builder.errorTextView);
    }

    private View getCurrentShow() {
        for (View view : builder.viewList) {
            if (view.getVisibility() == View.VISIBLE) {
                return view;
            }
        }
        return null;
    }

    private void visibleLayout(View targetView) {
        View currentShowView = getCurrentShow();
        for (View view : builder.viewList) {
            if (view == targetView) {
                animChanged(view, true);
            } else {
                if (view == currentShowView) {
                    animChanged(view, false);
                }
            }
        }
    }

    private void animChanged(final View v, boolean show) {
        if (show) {
            if (showAnim != null) {
                showAnim.cancel();
            }
            v.setVisibility(View.VISIBLE);
            showAnim = ObjectAnimator.ofFloat(v, "alpha", 0, 1.0f);
            showAnim.setDuration(TIME_ANIM_DURING);
            showAnim.start();
        } else {
            if (hideAnim != null) {
                hideAnim.cancel();
                hideAnim.removeAllListeners();
            }
            hideAnim = ObjectAnimator.ofFloat(v, "alpha", 1.0f, 0);
            hideAnim.setDuration(TIME_ANIM_DURING);
            hideAnim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if(getCurrentShow() != v){
                        v.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            hideAnim.start();
        }
    }

    public static Builder from(Context context) {
        return new Builder();
    }


    //===========Builder=================

    public static class Builder {
        protected ViewGroup contentLayout;
        protected ViewGroup progressLayout;
        protected TextView emptyTextView;
        protected TextView errorTextView;

        private List<View> viewList;

        public ViewGroup getContentLayout() {
            return contentLayout;
        }

        public Builder setContentLayout(ViewGroup contentLayout) {
            this.contentLayout = contentLayout;
            return this;
        }

        public ViewGroup getProgressLayout() {
            return progressLayout;
        }

        public Builder setProgressLayout(ViewGroup progressLayout) {
            this.progressLayout = progressLayout;
            return this;
        }

        public TextView getEmptyTextView() {
            return emptyTextView;
        }

        public Builder setEmptyTextView(TextView emptyTextView) {
            this.emptyTextView = emptyTextView;
            return this;
        }

        public TextView getErrorTextView() {
            return errorTextView;
        }

        public Builder setErrorTextView(TextView errorTextView) {
            this.errorTextView = errorTextView;
            return this;
        }

        public LoadHandler build() {
            viewList = new ArrayList<>();
            addViewToList(contentLayout);
            addViewToList(progressLayout);
            addViewToList(emptyTextView);
            addViewToList(errorTextView);
            return new LoadHandler(this);
        }

        private void addViewToList(View v) {
            if (v != null) {
                viewList.add(v);
            }
        }
    }


}
