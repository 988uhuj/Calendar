package github.chenupt.calendar.view.item;

import android.content.Context;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.joda.time.DateTime;

import github.chenupt.calendar.R;

/**
 * Created by chenupt@gmail.com on 2015/1/2.
 * Description TODO
 */
@EViewGroup(R.layout.view_item_day)
public class DayItemView extends BaseItemView {

    @ViewById(R.id.day)
    TextView dayTextView;

    public DayItemView(Context context) {
        super(context);
    }

    @Override
    public void bindView() {
        dayTextView.setText(DateTime.now().toString("yyyy-MM-dd") + " " + viewPosition);
    }
}
