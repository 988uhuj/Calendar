package github.chenupt.calendar.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;

import github.chenupt.calendar.R;

/**
 * Created by chenupt@gmail.com on 2015/1/2.
 * Description TODO
 */
@EFragment(R.layout.fragment_day_list)
public class DayListFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setContentView(R.layout.fragment_day_list);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @AfterViews
    void afterViews(){
        showProgress();
        show();
    }

    @UiThread(delay = 5000)
    void show(){
        showContent();
    }

}
