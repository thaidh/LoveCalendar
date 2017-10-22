package com.thaidh.lovecalendar.activity;

import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.thaidh.lovecalendar.R;

import dialog.IconPickerBottomSheet;

public class EventComposeActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_compose);
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

        mBottomSheetDialog.show();
    }
}
