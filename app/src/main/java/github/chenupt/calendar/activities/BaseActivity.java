package github.chenupt.calendar.activities;

import android.animation.ObjectAnimator;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.androidannotations.annotations.EActivity;

import github.chenupt.calendar.R;

/**
 * Created by chenupt@gmail.com on 2015/1/2.
 * Description TODO
 */
@EActivity
public class BaseActivity extends ActionBarActivity {

    protected ViewGroup baseContentLayout;
    protected ViewGroup baseProgressLayout;
    protected TextView baseEmptyTextView;
    protected TextView baseErrorTextView;

    @Override
    public void setContentView(int layoutResID) {
        initLoadingView(layoutResID);
    }

    private void initLoadingView(int layoutResID){
        super.setContentView(R.layout.activity_base);
        baseContentLayout = (ViewGroup) findViewById(R.id.base_content);
        baseProgressLayout = (ViewGroup) findViewById(R.id.base_progress);
        baseEmptyTextView = (TextView) findViewById(R.id.base_empty);
        baseErrorTextView = (TextView) findViewById(R.id.base_error);
        LayoutInflater.from(this).inflate(layoutResID, baseContentLayout, true);
    }

    protected void showProgress(){
        baseProgressLayout.setVisibility(View.VISIBLE);
        baseContentLayout.setVisibility(View.GONE);
        baseEmptyTextView.setVisibility(View.GONE);
        baseErrorTextView.setVisibility(View.GONE);
    }

    protected void showContent(){
        baseContentLayout.setVisibility(View.VISIBLE);
        ObjectAnimator alphaOA = ObjectAnimator.ofFloat(baseContentLayout, "alpha", 0, 1.0f);
        alphaOA.setDuration(300);
        alphaOA.start();
        baseProgressLayout.setVisibility(View.GONE);
        baseEmptyTextView.setVisibility(View.GONE);
        baseErrorTextView.setVisibility(View.GONE);
    }

    protected void showEmpty(){
        baseEmptyTextView.setVisibility(View.VISIBLE);
        baseContentLayout.setVisibility(View.GONE);
        baseProgressLayout.setVisibility(View.GONE);
        baseErrorTextView.setVisibility(View.GONE);
    }

    protected void showError(){
        baseErrorTextView.setVisibility(View.VISIBLE);
        baseContentLayout.setVisibility(View.GONE);
        baseProgressLayout.setVisibility(View.GONE);
        baseEmptyTextView.setVisibility(View.GONE);
    }

}
