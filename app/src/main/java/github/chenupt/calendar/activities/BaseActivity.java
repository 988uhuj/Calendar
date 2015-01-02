package github.chenupt.calendar.activities;

import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import github.chenupt.calendar.R;
import github.chenupt.calendar.util.LoadHandler;

/**
 * Created by chenupt@gmail.com on 2015/1/2.
 * Description TODO
 */
public class BaseActivity extends ActionBarActivity {

    protected ViewGroup baseContentLayout;
    protected ViewGroup baseProgressLayout;
    protected TextView baseEmptyTextView;
    protected TextView baseErrorTextView;

    LoadHandler loadHandler;

    private boolean showProgress = true;

    @Override
    public void setContentView(int layoutResID) {
        initLoadingView(layoutResID);
    }

    private void initLoadingView(int layoutResID) {
        if (showProgress) {
            super.setContentView(R.layout.layout_base);
            baseContentLayout = (ViewGroup) findViewById(R.id.base_content);
            baseProgressLayout = (ViewGroup) findViewById(R.id.base_progress);
            baseEmptyTextView = (TextView) findViewById(R.id.base_empty);
            baseErrorTextView = (TextView) findViewById(R.id.base_error);
            LayoutInflater.from(this).inflate(layoutResID, baseContentLayout, true);

            loadHandler = LoadHandler.from(this)
                    .setContentLayout(baseContentLayout)
                    .setEmptyTextView(baseEmptyTextView)
                    .setErrorTextView(baseErrorTextView)
                    .setProgressLayout(baseProgressLayout)
                    .build();
        } else {
            super.setContentView(layoutResID);
        }
    }

    protected void showProgress() {
        loadHandler.showProgress();
    }

    protected void showContent() {
        loadHandler.showContent();
    }

    protected void showEmpty() {
        loadHandler.showEmpty();
    }

    protected void showError() {
        loadHandler.showError();
    }

    protected void enableShowProgress(boolean enable){
        showProgress = enable;
    }

}
