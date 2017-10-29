package com.thaidh.lovecalendar.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import com.thaidh.lovecalendar.R;
import com.thaidh.lovecalendar.calendar.model.Event;

import dialog.IconPickerBottomSheet;

public class EventComposeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private int eventType = Event.TYPE_BLEEDING;
    ImageView previewIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_compose);
        previewIcon = findViewById(R.id.preview_icon);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupToolbar();

    }

    void setupToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.compose_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_save:
//                Snackbar.make(toolbar, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                showIconPickerDialog();
                break;
        }
        return true;
    }

    void showIconPickerDialog() {
        IconPickerBottomSheet mBottomSheetDialog = new IconPickerBottomSheet(this);
//        View sheetView = getLayoutInflater().inflate(R.layout.dialog_icon_picker_layout, null);
//        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.setIconPickerListener(new IconPickerBottomSheet.IconPickerListener() {
            @Override
            public void onIconClick(int index) {
                eventType = index;
                updatePreviewIcon();
            }
        });
        mBottomSheetDialog.show();
    }

    private void updatePreviewIcon() {
        switch (eventType) {
            case Event.TYPE_BLEEDING:
                previewIcon.setImageResource(R.drawable.icon_bleeding);
                break;
            case Event.TYPE_SPOTTING:
                previewIcon.setImageResource(R.drawable.icon_spotting);
                break;
            case Event.TYPE_DRY_INFERTILE:
                previewIcon.setImageResource(R.drawable.icon_dry_infertile);
                break;
            case Event.TYPE_INFERTILE:
                previewIcon.setImageResource(R.drawable.icon_infertile);
                break;
            case Event.TYPE_POSSIBLY_FERTILE:
                previewIcon.setImageResource(R.drawable.icon_possibly_fertile);
                break;
            case Event.TYPE_PEAK:
                previewIcon.setImageResource(R.drawable.icon_peak);
                break;
            case Event.TYPE_PEAK_AFTER_1:
                previewIcon.setImageResource(R.drawable.icon_peak_after_1);
                break;
            case Event.TYPE_PEAK_AFTER_2:
                previewIcon.setImageResource(R.drawable.icon_peak_after_2);
                break;
            case Event.TYPE_PEAK_AFTER_3:
                previewIcon.setImageResource(R.drawable.icon_peak_after_3);
                break;
            case Event.TYPE_INFERCOURSE:
                previewIcon.setImageResource(R.drawable.icon_infercourse);
                break;
        }
    }
}
