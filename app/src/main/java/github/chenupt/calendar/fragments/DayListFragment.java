package github.chenupt.calendar.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import github.chenupt.calendar.R;
import github.chenupt.calendar.beans.CalendarBean;
import github.chenupt.calendar.multiplemodel.SimpleItemEntity;
import github.chenupt.calendar.multiplemodel.SimpleModelAdapter;
import github.chenupt.calendar.view.AutoLoadListView;

/**
 * Created by chenupt@gmail.com on 2015/1/2.
 * Description TODO
 */
@EFragment
public class DayListFragment extends BaseFragment {

    @ViewById(R.id.list_view)
    AutoLoadListView listView;

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
        listView.setDirection(AutoLoadListView.Direction.ALL);
        listView.setOnLoadListener(new AutoLoadListView.SimpleOnLoadListener(){
            @Override
            public void onDownLoad() {
                super.onDownLoad();
                adapter.addList(calendarBean.getWrapperList(adapter.getLastItem(), true));
                adapter.notifyDataSetChanged();
                listView.setOnLoadComplete();
            }

            @Override
            public void onUpLoad() {
                super.onUpLoad();
                List<SimpleItemEntity> wrapperList = calendarBean.getWrapperList(adapter.getFirstItem(), false);
                // must call before add head data
                int index = listView.getCurrentPosition(wrapperList.size());
                int top = listView.getCurrentTop();

                adapter.addListToHead(wrapperList);
                adapter.notifyDataSetChanged();
                listView.setSelectionFromTop(index, top);

                listView.setOnLoadComplete();
            }
        });
        action();
    }

    private void action(){
        adapter.setList(calendarBean.getWrapperList(adapter.getLastItem(), true));
        adapter.notifyDataSetChanged();
//        show();
        showContent();
    }

    @UiThread(delay = 5000)
    void show(){
        showContent();

    }

}
