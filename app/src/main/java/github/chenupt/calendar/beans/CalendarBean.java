package github.chenupt.calendar.beans;

import org.androidannotations.annotations.EBean;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import github.chenupt.calendar.multiplemodel.ItemEntityCreator;
import github.chenupt.calendar.multiplemodel.ModelFactory;
import github.chenupt.calendar.multiplemodel.SimpleItemEntity;
import github.chenupt.calendar.view.item.DayItemView_;
import github.chenupt.calendar.view.item.MonthItemView_;

/**
 * Created by chenupt@gmail.com on 2015/1/2.
 * Description TODO
 */
@EBean
public class CalendarBean {

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
        if (lastEntity == null) {
            DateTime now = DateTime.now();
            dateTime = new DateTime(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth(), 0, 0);
        } else {
            DateTime lastDateTime = lastEntity.getContent();
            if (down) {
                dateTime = lastDateTime.plusMonths(1);
            } else {
                dateTime = lastDateTime.minusMonths(1);
            }
        }
        ItemEntityCreator.create(dateTime).setModelView(MonthItemView_.class).attach(resultList);
        int dayCount = dateTime.dayOfMonth().getMaximumValue();
        for (int i = 0; i < dayCount; i++) {
            ItemEntityCreator.create(dateTime.withDayOfMonth(i + 1)).setModelView(DayItemView_.class).attach(resultList);
        }
        return resultList;
    }


}
