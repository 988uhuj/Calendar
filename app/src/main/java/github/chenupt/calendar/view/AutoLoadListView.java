package github.chenupt.calendar.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import github.chenupt.calendar.utils.DebugLog;

/**
 * Created by chenupt@gmail.com on 2015/1/3.
 * Description : Auto load data when the listView attach top or bottom.
 */
public class AutoLoadListView extends ListView {

    private boolean isLoading;
    private boolean enableLoadMore;
    private int autoLoadOffset;
    /**
     * Delegating listener, can be null.
     */
    private OnScrollListener delegateOnScrollListener;
    private OnLoadListener onLoadListener;
    private Direction direction = Direction.DOWN;

    public enum Direction {
        UP, DOWN, ALL
    }

    public AutoLoadListView(Context context) {
        super(context);
        initView();
    }

    public AutoLoadListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public AutoLoadListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public interface OnLoadListener {
        void onDownLoad();

        void onUpLoad();
    }

    public static class SimpleOnLoadListener implements OnLoadListener {

        @Override
        public void onDownLoad() {

        }

        @Override
        public void onUpLoad() {

        }
    }

    private final OnScrollListener onScrollListener = new OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (delegateOnScrollListener != null) {
                delegateOnScrollListener.onScrollStateChanged(view, scrollState);
            }
            if(!enableLoadMore){
                return ;
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (delegateOnScrollListener != null) {
                delegateOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
            }
            if(!enableLoadMore){
                return ;
            }
            if (view != null && totalItemCount != 0 && !isLoading) {
                if (direction == Direction.DOWN) {
                    attachBottom(firstVisibleItem, visibleItemCount, totalItemCount);
                } else if (direction == Direction.UP) {
                    attachTop(firstVisibleItem, visibleItemCount, totalItemCount);
                } else if (direction == Direction.ALL) {
                    attachBottom(firstVisibleItem, visibleItemCount, totalItemCount);
                    attachTop(firstVisibleItem, visibleItemCount, totalItemCount);
                }

            }
        }
    };

    private void attachBottom(int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int lastVisibleItem = firstVisibleItem + visibleItemCount;
        if (lastVisibleItem >= totalItemCount - autoLoadOffset) {
            // TODO load more
            isLoading = true;
            DebugLog.d("action load more down");
            if (onLoadListener != null) {
                onLoadListener.onDownLoad();
            }
        }
    }

    private void attachTop(int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (firstVisibleItem <= autoLoadOffset) {
            isLoading = true;
            DebugLog.d("action load more up");
            if (onLoadListener != null) {
                onLoadListener.onUpLoad();
            }
        }
    }


    private void initView() {
        DebugLog.d("---");
        super.setOnScrollListener(onScrollListener);
    }

    public void setOnLoadComplete(){
        isLoading = false;
    }


    public int getCurrentPosition(int listSize){
        return this.getFirstVisiblePosition() + listSize + getHeaderViewsCount();   // add header
    }
    public int getCurrentTop(){
        if(getChildCount() > getHeaderViewsCount()){
            View v = getChildAt(getHeaderViewsCount());
            return (v == null) ? 0 : v.getTop();
        }
        return 0;
    }

    public OnScrollListener getOnScrollListener() {
        return delegateOnScrollListener;
    }

    public void setOnScrollListener(OnScrollListener delegateOnScrollListener) {
        this.delegateOnScrollListener = delegateOnScrollListener;
    }

    public boolean isEnableLoadMore() {
        return enableLoadMore;
    }

    public void setEnableLoadMore(boolean enableLoadMore) {
        this.enableLoadMore = enableLoadMore;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getAutoLoadOffset() {
        return autoLoadOffset;
    }

    public void setAutoLoadOffset(int autoLoadOffset) {
        this.autoLoadOffset = autoLoadOffset;
    }

    public OnLoadListener getOnLoadListener() {
        return onLoadListener;
    }

    public void setOnLoadListener(OnLoadListener onLoadListener) {
        this.onLoadListener = onLoadListener;
    }
}
