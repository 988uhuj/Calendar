package github.chenupt.calendar.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.androidannotations.annotations.EFragment;

import github.chenupt.calendar.R;
import github.chenupt.calendar.utils.LoadHandler;

/**
 * Created by chenupt@gmail.com on 2015/1/2.
 * Description TODO
 */
@EFragment
public class BaseFragment extends Fragment {

    protected ViewGroup baseContentLayout;
    protected ViewGroup baseProgressLayout;
    protected TextView baseEmptyTextView;
    protected TextView baseErrorTextView;

    LoadHandler loadHandler;

    private int layoutResID;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_base, container, false);
        baseContentLayout = (ViewGroup) view.findViewById(R.id.base_content);
        baseProgressLayout = (ViewGroup) view.findViewById(R.id.base_progress);
        baseEmptyTextView = (TextView) view.findViewById(R.id.base_empty);
        baseErrorTextView = (TextView) view.findViewById(R.id.base_error);
        if(layoutResID == 0){
            throw new RuntimeException("Layout is empty. Must call setContentView before onCreateView.");
        }
        LayoutInflater.from(getActivity()).inflate(layoutResID, baseContentLayout, true);

        loadHandler = LoadHandler.from(getActivity())
                .setContentLayout(baseContentLayout)
                .setProgressLayout(baseProgressLayout)
                .setErrorTextView(baseErrorTextView)
                .setEmptyTextView(baseEmptyTextView)
                .build();

        return view;
    }

    protected void showProgress(){
        loadHandler.showProgress();
    }

    protected void showContent(){
        loadHandler.showContent();
    }

    protected void showEmpty(){
        loadHandler.showEmpty();
    }

    protected void showError(){
        loadHandler.showError();
    }

    protected void setContentView(int layoutResID){
        this.layoutResID = layoutResID;
    }
}
