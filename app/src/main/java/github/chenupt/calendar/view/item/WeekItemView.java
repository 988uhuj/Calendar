package github.chenupt.calendar.view.item;

import android.content.Context;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.joda.time.DateTime;

import github.chenupt.calendar.R;

/**
 * Created by chenupt@gmail.com on 2015/1/3.
 * Description TODO
 */
@EViewGroup(R.layout.view_item_week)
public class WeekItemView extends BaseItemView<DateTime> {

    @ViewById(R.id.text_view)
    TextView textView;

    public WeekItemView(Context context) {
        super(context);
    }

    @Override
    public void bindView() {

    }
}
