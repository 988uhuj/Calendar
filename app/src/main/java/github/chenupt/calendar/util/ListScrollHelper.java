package github.chenupt.calendar.util;

import android.os.Handler;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;


/**
 * Created by chenupt@gmail.com on 2014/9/22.
 * Description TODO
 */
public class ListScrollHelper {

    public static void smoothScrollToPositionFromTop(final AbsListView view, final int position) {
        if (view == null || view.getChildCount() == 0) {
            return ;
        }

        View child = getChildAtPosition(view, position);
        // There's no need to scroll if child is already at top or view is already scrolled to its end
        if ((child != null)
                && ((child.getTop() == 0 + view.getPaddingTop()) || ((child.getTop() > 0 + view.getPaddingTop())
                && !ViewCompat.canScrollVertically(view, ViewCompat.LAYOUT_DIRECTION_RTL)))) {
            return;
        }

        view.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(final AbsListView view, final int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    view.setOnScrollListener(null);

                    // Fix for scrolling bug
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            view.setSelection(position);
                        }
                    });
                }
            }

            @Override
            public void onScroll(final AbsListView view, final int firstVisibleItem, final int visibleItemCount,
                                 final int totalItemCount) { }
        });

        // Perform scrolling to position
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                view.smoothScrollToPositionFromTop(position, 0, 500);
            }
        });
    }



    public static View getChildAtPosition(AdapterView view, int position) {
        final int numVisibleChildren = view.getChildCount();
        final int firstVisiblePosition = view.getFirstVisiblePosition();

        for (int i = 0; i < numVisibleChildren; i++) {
            int positionOfView = firstVisiblePosition + i;
            if (positionOfView == position) {
                return view.getChildAt(i);
            }
        }
        return null;
    }

}
