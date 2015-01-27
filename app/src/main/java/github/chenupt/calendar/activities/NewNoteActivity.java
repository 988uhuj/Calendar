package github.chenupt.calendar.activities;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.joda.time.DateTime;
import org.litepal.crud.DataSupport;

import github.chenupt.calendar.R;
import github.chenupt.calendar.persistance.Note;
import github.chenupt.calendar.utils.Constants;

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
    @ViewById(R.id.content_edit)
    EditText editText;

    @Extra
    DateTime dateTime;
    @Extra
    Note note;

    @AfterViews
    void afterViews() {
        initToolBar();
        initView();
    }

    private void initView() {
        infoTextView.setText(dateTime.toString("yyyy / MM / dd"));
        if (note != null) {
            editText.setText(note.getContent());
        }
    }

    private void initToolBar() {
        setSupportActionBar(toolbar);
//        toolbar.setElevation(20f);
        toolbar.setNavigationIcon(R.drawable.ic_cancel_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
//        getSupportActionBar().setTitle(dateTime.toString("yyyy / MM / dd"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            dataAction();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void collectData() {
        note.setContent(editText.getText().toString());
        note.setCreateTime(dateTime.getMillis());
    }

    private void dataAction() {
        saveData();
    }

    private void saveData() {
        if (note == null) {
            note = new Note();
            collectData();
            note.save();
        } else {
            collectData();
            note.update(note.getId());
        }


        resultBack();
    }

    private void deleteData() {
        if (note != null) {
            DataSupport.delete(Note.class, note.getId());
            resultBack();
        } else {
            finish();
        }

    }

    private void resultBack() {
        Toast.makeText(this, "has saved!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.putExtra(Constants.DEF_MAP_KEY.DATETIME, dateTime);
        intent.putExtra(Constants.DEF_MAP_KEY.NOTE, note);
        setResult(RESULT_OK, intent);
        finish();
    }


}
