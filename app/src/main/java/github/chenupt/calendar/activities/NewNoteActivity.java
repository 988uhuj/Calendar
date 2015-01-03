package github.chenupt.calendar.activities;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.joda.time.DateTime;

import github.chenupt.calendar.R;

/**
 * Created by chenupt@gmail.com on 2015/1/3.
 * Description TODO
 */
@EActivity(R.layout.activity_new_note)
public class NewNoteActivity extends BaseActivity {

    @ViewById(R.id.tool_bar)
    Toolbar toolbar;
    @ViewById(R.id.info_text)
    TextView infoTextView;

    @Extra
    DateTime dateTime;

    @AfterViews
    void afterViews(){
        initToolBar();

        infoTextView.setText(dateTime.toString("yyyy / MM / dd"));
    }

    private void initToolBar(){
        setSupportActionBar(toolbar);
//        toolbar.setElevation(20f);
        toolbar.setNavigationIcon(R.drawable.ic_cancel_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        getSupportActionBar().setTitle("New Note");
    }


}
