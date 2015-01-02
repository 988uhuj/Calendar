package github.chenupt.calendar.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import github.chenupt.calendar.R;
import github.chenupt.calendar.beans.CalendarBean;
import github.chenupt.calendar.multiplemodel.SimpleModelAdapter;

/**
 * Created by chenupt@gmail.com on 2015/1/2.
 * Description TODO
 */
@EFragment
public class DayListFragment extends BaseFragment {

    @ViewById(R.id.list_view)
    ListView listView;

    @Bean
    CalendarBean calendarBean;

    private SimpleModelAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setContentView(R.layout.fragment_day_list);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @AfterViews
    void afterViews(){
        showProgress();

        adapter = new SimpleModelAdapter(getActivity(), calendarBean.getFactory());
        listView.setAdapter(adapter);
        action();
    }

    private void action(){
        adapter.setList(calendarBean.getWrapperList());
        adapter.notifyDataSetChanged();
//        show();
        showContent();
    }

    @UiThread(delay = 5000)
    void show(){
        showContent();

    }

}
