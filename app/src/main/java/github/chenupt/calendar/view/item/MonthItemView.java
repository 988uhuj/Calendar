package github.chenupt.calendar.view.item;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.joda.time.DateTime;

import github.chenupt.calendar.R;

/**
 * Created by chenupt@gmail.com on 2015/1/3.
 * Description TODO
 */
@EViewGroup(R.layout.view_item_month)
public class MonthItemView extends BaseItemView<DateTime> {

    @ViewById(R.id.month_text_view)
    TextView monthTextView;
    @ViewById(R.id.image_view)
    ImageView bgImageView;

    public MonthItemView(Context context) {
        super(context);
    }

    @Override
    public void bindView() {
        monthTextView.setText(model.getContent().toString("yyyy-MM"));
    }
}
