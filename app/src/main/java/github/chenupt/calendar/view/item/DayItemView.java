package github.chenupt.calendar.view.item;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.joda.time.DateTime;

import github.chenupt.calendar.R;
import github.chenupt.calendar.activities.NewNoteActivity_;
import github.chenupt.calendar.persistance.Note;
import github.chenupt.calendar.util.Constants;

/**
 * Created by chenupt@gmail.com on 2015/1/2.
 * Description TODO
 */
@EViewGroup(R.layout.view_item_day)
public class DayItemView extends BaseItemView<DateTime> {

    @ViewById(R.id.day)
    TextView dayTextView;
    @ViewById(R.id.content)
    TextView contentTextView;
    @ViewById(R.id.new_week)
    TextView newWeekTextView;

    public DayItemView(Context context) {
        super(context);
    }

    @Override
    public void bindView() {
        if (model.getContent().getDayOfWeek() == 1) {
            newWeekTextView.setVisibility(View.VISIBLE);
        }else{
            newWeekTextView.setVisibility(View.GONE);
        }
        if(model.hasAttr(Constants.DEF_MAP_KEY.NOTE)){
            contentTextView.setText(model.getAttr(Constants.DEF_MAP_KEY.NOTE, Note.class).getContent());
        }else{
            contentTextView.setText("empty");
        }
        dayTextView.setText(model.getContent().toString("EE d" + "日"));
    }

    @Click(R.id.container)
    void containerClick(){
        NewNoteActivity_.intent(getContext()).note(model.getAttr(Constants.DEF_MAP_KEY.NOTE, Note.class)).dateTime(model.getContent()).startForResult(0);
    }
}
