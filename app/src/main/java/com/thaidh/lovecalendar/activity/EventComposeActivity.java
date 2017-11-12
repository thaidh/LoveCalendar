package com.thaidh.lovecalendar.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.thaidh.lovecalendar.R;
import com.thaidh.lovecalendar.calendar.helper.Formatter;
import com.thaidh.lovecalendar.calendar.helper.GlobalData;
import com.thaidh.lovecalendar.calendar.model.Event;
import com.thaidh.lovecalendar.database.EventRepository;
import com.thaidh.lovecalendar.dialog.IconPickerBottomSheet;

import org.joda.time.DateTime;

public class EventComposeActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = EventComposeActivity.class.getSimpleName();

    public static final String EXTRA_DAY_CODE = "EXTRA_DAY_CODE";


    private Toolbar toolbar;
    private int eventType = Event.TYPE_BLEEDING;
    ImageView previewIcon;
    DateTime currDateTime;
    String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_compose);
        previewIcon = findViewById(R.id.preview_icon);
        previewIcon.setOnClickListener(this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupToolbar();

        currDateTime = new DateTime();
        Bundle extras = getIntent().getExtras();
        if (extras.containsKey(EXTRA_DAY_CODE)) {
            code = extras.getString(EXTRA_DAY_CODE);
            currDateTime = Formatter.getDateTimeFromCode(code);
            Log.i(TAG, "dayCode :" +  code);
        }

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
                submitEvent();
                break;
        }
        return true;
    }

    void submitEvent() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Event event = new Event(eventType, currDateTime.getMillis(), currDateTime.getMillis());
                EventRepository.mEventQuery.getRef().push().setValue(event, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError error, DatabaseReference reference) {
                        if (error != null) {
                            Log.e(TAG, "Failed to write message", error.toException());
                        } else {
                            Log.e(TAG, "Success write message: "  + eventType + " at :" + code);
                            finish();
                        }
                    }
                });
            }
        }).start();
    }

    void showIconPickerDialog() {
        IconPickerBottomSheet mBottomSheetDialog = new IconPickerBottomSheet(this);
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
        previewIcon.setImageResource(Event.getImageResource(eventType));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.preview_icon:
                showIconPickerDialog();
                break;
        }
    }
}
