package github.chenupt.calendar.view.item;

import android.content.Context;
import android.util.AttributeSet;

import github.chenupt.calendar.multiplemodel.BaseItemModel;

/**
 * Created by chenupt@gmail.com on 2015/1/2.
 * Description TODO
 */
public abstract class BaseItemView extends BaseItemModel {

    public BaseItemView(Context context) {
        super(context);
    }

    public BaseItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

}
