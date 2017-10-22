package dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.thaidh.lovecalendar.R;
import com.thaidh.lovecalendar.adapter.IconPickerAdapter;

/**
 * Created by thaidh on 10/22/17.
 */

public class IconPickerBottomSheet extends BottomSheetDialog {
    View sheetView;
    RecyclerView iconList;
    IconPickerAdapter adapter;
    LinearLayoutManager mLayoutManager;

    public IconPickerBottomSheet(@NonNull Context context) {
        super(context);
        sheetView = getLayoutInflater().inflate(R.layout.dialog_icon_picker_layout, null);

        setContentView(sheetView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        iconList = sheetView.findViewById(R.id.icon_list);
//        adapter = new IconPickerAdapter(getContext());
//        mLayoutManager = new LinearLayoutManager(getContext());
//        iconList.setLayoutManager(mLayoutManager);
//        iconList.setItemAnimator(null);
//        iconList.setAdapter(adapter);
    }


}
