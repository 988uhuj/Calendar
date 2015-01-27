package github.chenupt.calendar.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import github.chenupt.calendar.R;
import github.chenupt.calendar.fragments.DayListFragment;
import github.chenupt.calendar.fragments.DayListFragment_;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewById(R.id.tool_bar)
    Toolbar toolbar;
    @ViewById(R.id.drawer)
    DrawerLayout drawerLayout;
    @ViewById(R.id.toolbar_content)
    View toolbarContent;
    @ViewById(R.id.toolbar_text)
    TextView toolbarTextView;
    @ViewById(R.id.toolbar_arrow)
    ImageView toolbarArrow;

    private DayListFragment dayListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        enableShowProgress(false);
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    void afterViews(){
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
//        drawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.common_text));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(drawerLayout.isDrawerVisible(Gravity.START)){
                    drawerLayout.closeDrawer(Gravity.START);
                }else{
                    drawerLayout.openDrawer(Gravity.START);
                }
            }
        });
        dayListFragment = DayListFragment_.builder().build();
        getSupportFragmentManager().beginTransaction().replace(R.id.content, dayListFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        dayListFragment.onActivityResult(requestCode, resultCode, data);
    }

    @Click(R.id.toolbar_content)
    void toolbarContentClick(){
        dayListFragment.toggleTopView();
    }
}
