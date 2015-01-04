package github.chenupt.calendar.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.joda.time.DateTime;

import java.util.List;

import github.chenupt.calendar.R;
import github.chenupt.calendar.beans.CalendarBean;
import github.chenupt.calendar.multiplemodel.SimpleItemEntity;
import github.chenupt.calendar.multiplemodel.SimpleModelAdapter;
import github.chenupt.calendar.persistance.Note;
import github.chenupt.calendar.util.Constants;
import github.chenupt.calendar.util.DebugLog;
import github.chenupt.calendar.util.ListScrollHelper;
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
                DebugLog.d(adapter.getList().size() + "");
                adapter.addList(calendarBean.getWrapperList(adapter.getLastItem(), true));
                loadFinish();
                listView.setOnLoadComplete();
            }

            @Override
            public void onUpLoad() {
                super.onUpLoad();
                DebugLog.d(adapter.getList().size() + "");
                List<SimpleItemEntity> wrapperList = calendarBean.getWrapperList(adapter.getFirstItem(), false);
                // must call before add head data
                int index = listView.getCurrentPosition(wrapperList.size());
                int top = listView.getCurrentTop();

                adapter.addListToHead(wrapperList);
                loadFinish();
                listView.setSelectionFromTop(index, top);
                listView.setOnLoadComplete();
            }
        });
        action();
    }

    private void action(){
        showContent();
        listView.setEnableLoadMore(false);
        List<SimpleItemEntity> currentMonth = calendarBean.getWrapperList(adapter.getLastItem(), true);
        adapter.addList(currentMonth);
        List<SimpleItemEntity> lastMonth = calendarBean.getWrapperList(adapter.getFirstItem(), false);
        adapter.addListToHead(lastMonth);
        adapter.notifyDataSetChanged();
        DebugLog.d("lastMonth" + lastMonth.size());
        setSection(lastMonth.size());
        scrollToPosition();
    }

    @UiThread
    void setSection(int index){
        listView.setSelection(index);
    }

    @UiThread(delay = 1000)
    void scrollToPosition(){
        SimpleItemEntity entity = calendarBean.getTodayItem(adapter.getList());
        int index = adapter.getList().indexOf(entity);
        DebugLog.d("index" + index);
        ListScrollHelper.smoothScrollToPositionFromTop(listView, index);
        listView.setEnableLoadMore(true);
    }

    @UiThread(delay = 5000)
    void show(){
        showContent();
    }

    private void loadFinish(){
        adapter.notifyDataSetChanged();
    }

    @OnActivityResult(0)
    void newNoteResult(int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            DateTime dateTime = (DateTime) data.getSerializableExtra(Constants.DEF_MAP_KEY.DATETIME);
            Note note = (Note) data.getSerializableExtra(Constants.DEF_MAP_KEY.NOTE);
            calendarBean.updateList(adapter.getList(), dateTime, note);
            adapter.notifyDataSetChanged();
        }
    }

}
