package github.chenupt.calendar.beans;

import org.androidannotations.annotations.EBean;
import org.joda.time.DateTime;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import github.chenupt.calendar.persistance.Note;
import github.chenupt.calendar.utils.Constants;
import github.chenupt.calendar.view.item.DayItemView_;
import github.chenupt.calendar.view.item.MonthItemView_;
import github.chenupt.multiplemodel.ItemEntity;
import github.chenupt.multiplemodel.ItemEntityCreator;
import github.chenupt.multiplemodel.ModelManagerBuilder;
import github.chenupt.multiplemodel.aa.AAModelManager;

/**
 * Created by chenupt@gmail.com on 2015/1/2.
 * Description TODO
 */
@EBean
public class CalendarBean {

    private Map<Long, Note> map = new HashMap<>();

    public AAModelManager getFactory() {
        return ModelManagerBuilder
                .begin()
                .addModel(DayItemView_.class)
                .addModel(MonthItemView_.class)
                .build(AAModelManager.class);
    }

    public List<ItemEntity> getWrapperList(ItemEntity<DateTime> lastEntity, boolean down) {
        List<ItemEntity> resultList = new ArrayList<>();
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

        // month
        ItemEntityCreator.create(dateTime)
                .setModelView(MonthItemView_.class)
                .addAttr(Constants.DEF_MAP_KEY.BG, Constants.MONTH_BG_ARRAY[dateTime.getMonthOfYear()-1])
                .attach(resultList);
        // days
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


    public void updateList(List<ItemEntity> list, DateTime dateTime, Note note){
        for (ItemEntity<DateTime> entity : list) {
            if(entity.getModelView() == DayItemView_.class){
                if (entity.getContent().getMillis() == dateTime.getMillis()) {
                    entity.addAttr(Constants.DEF_MAP_KEY.NOTE, note);
                    return ;
                }
            }
        }
    }

    public ItemEntity<DateTime> getTodayItem(List<ItemEntity> list){
        DateTime today = DateTime.now().withTimeAtStartOfDay();
        for (ItemEntity<DateTime> entity : list) {
            if(entity.getModelView() == DayItemView_.class){
                if(entity.getContent().getMillis() == today.getMillis()){
                    return entity;
                }
            }
        }
        return null;
    }


}
