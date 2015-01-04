package github.chenupt.calendar.beans;

import org.androidannotations.annotations.EBean;
import org.joda.time.DateTime;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import github.chenupt.calendar.multiplemodel.ItemEntityCreator;
import github.chenupt.calendar.multiplemodel.ModelFactory;
import github.chenupt.calendar.multiplemodel.SimpleItemEntity;
import github.chenupt.calendar.persistance.Note;
import github.chenupt.calendar.util.Constants;
import github.chenupt.calendar.view.item.DayItemView_;
import github.chenupt.calendar.view.item.MonthItemView_;

/**
 * Created by chenupt@gmail.com on 2015/1/2.
 * Description TODO
 */
@EBean
public class CalendarBean {

    private Map<Long, Note> map = new HashMap<>();

    public ModelFactory getFactory() {
        ModelFactory modelFactory = new ModelFactory.Builder()
                .addModel(DayItemView_.class)
                .addModel(MonthItemView_.class)
//                .addModel(WeekItemView_.class)
                .build();
        return modelFactory;
    }

    public List<SimpleItemEntity> getWrapperList(SimpleItemEntity<DateTime> lastEntity, boolean down) {
        List<SimpleItemEntity> resultList = new ArrayList<>();
        DateTime dateTime;
        DateTime now = DateTime.now();
        if (lastEntity == null) {
            dateTime = now.withTimeAtStartOfDay();
        } else {
            DateTime lastDateTime = lastEntity.getContent();
            if (down) {
                dateTime = lastDateTime.plusMonths(1);
            } else {
                dateTime = lastDateTime.minusMonths(1);
            }
        }

        int dayCount = dateTime.dayOfMonth().getMaximumValue();
        // TODO get data
        DateTime lastDateTime = now.withDayOfMonth(dayCount).plusMillis(1);

        List<Note> notes = DataSupport.where("createtime between " + dateTime.getMillis() + " and " + lastDateTime.getMillis()).order("createtime").find(Note.class);
        for (Note note : notes) {
            map.put(note.getCreateTime(), note);
        }

        ItemEntityCreator.create(dateTime).setModelView(MonthItemView_.class).attach(resultList);
        DateTime today = now.withTimeAtStartOfDay();
        for (int i = 0; i < dayCount; i++) {
            DateTime dateTimeTemp = dateTime.withDayOfMonth(i + 1);
            ItemEntityCreator.create(dateTimeTemp)
                    .setModelView(DayItemView_.class)
                    .setCheck(today.getMillis() == dateTimeTemp.getMillis())
                    .addAttr(Constants.DEF_MAP_KEY.NOTE, map.get(dateTimeTemp.getMillis()))
                    .attach(resultList);
        }
        return resultList;
    }


    public void updateList(List<SimpleItemEntity> list, DateTime dateTime, Note note){
        for (SimpleItemEntity<DateTime> entity : list) {
            if(entity.getModelView() == DayItemView_.class){
                if (entity.getContent().getMillis() == dateTime.getMillis()) {
                    entity.addAttr(Constants.DEF_MAP_KEY.NOTE, note);
                    return ;
                }
            }
        }
    }

    public SimpleItemEntity<DateTime> getTodayItem(List<SimpleItemEntity> list){
        DateTime today = DateTime.now().withTimeAtStartOfDay();
        for (SimpleItemEntity<DateTime> entity : list) {
            if(entity.getModelView() == DayItemView_.class){
                if(entity.getContent().getMillis() == today.getMillis()){
                    return entity;
                }
            }
        }
        return null;
    }


}
