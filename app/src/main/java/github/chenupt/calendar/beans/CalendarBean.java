package github.chenupt.calendar.beans;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

import github.chenupt.calendar.multiplemodel.ItemEntityCreator;
import github.chenupt.calendar.multiplemodel.ModelFactory;
import github.chenupt.calendar.multiplemodel.SimpleItemEntity;
import github.chenupt.calendar.view.item.DayItemView_;

/**
 * Created by chenupt@gmail.com on 2015/1/2.
 * Description TODO
 */
@EBean
public class CalendarBean {

    public ModelFactory getFactory(){
        ModelFactory modelFactory = new ModelFactory.Builder().addModel(DayItemView_.class).build();
        return modelFactory;
    }

    public List<SimpleItemEntity> getWrapperList(){
        List<SimpleItemEntity> resultList = new ArrayList<>();
        for (int i = 0; i < 21; i++) {
            ItemEntityCreator.create("").setModelView(DayItemView_.class).attach(resultList);
        }
        return resultList;
    }


}
