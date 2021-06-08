package com.abhi41.seatbookexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    ViewGroup layout;
    TextView txtSelected;

    String seats = //"_UUUUAAARR_/"
            //  + "_________________/"
            "____AAR/"
                    + "UU__UUA/"
                    + "AA__AAU/"
                    + "AA__ARU/"
                    + "UU__UAU/"
                    + "AA__ARR/"
                    + "AA__ARU/"
            /*+ "_________________/"*/;

    List<TextView> seatViewList = new ArrayList<>();

    int seatSize = 100;
    int seatGaping = 10;

    int STATUS_AVAILABLE = 1;
    int STATUS_BOOKED = 2;
    int STATUS_RESERVED = 3;
    String selectedIds = "";

    ArrayList<Integer> arrayListSelectedIds = new ArrayList<Integer>();
    ArrayList<String> arrayListSeatSelection = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        txtSelected = findViewById(R.id.txtSelected);

        layout = findViewById(R.id.layoutSeat);

        seats = "/" + seats;

        LinearLayout layoutSeat = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutSeat.setOrientation(LinearLayout.VERTICAL);
        layoutSeat.setLayoutParams(params);
        layoutSeat.setPadding(8 * seatGaping, 8 * seatGaping,
                8 * seatGaping, 8 * seatGaping);
        layout.addView(layoutSeat);

        LinearLayout layout = null;

        int count = 0;

        for (int i = 0; i < seats.length(); i++) {

            if (seats.charAt(i) == '/') {
                layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.HORIZONTAL);
                layoutSeat.addView(layout);
            } else if (seats.charAt(i) == 'U') {
                count++;
                TextView view = new TextView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                        (seatSize, seatSize);
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                view.setLayoutParams(layoutParams);
                view.setPadding(0, 0, 0, 2 * seatGaping);
                view.setId(count);
                view.setGravity(Gravity.CENTER);
                view.setBackgroundResource(R.drawable.ic_chair_booked);
                view.setTextColor(Color.WHITE);
                view.setTag(STATUS_BOOKED);

                view.setText(count + "");
                view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);
                layout.addView(view);
                seatViewList.add(view);
                view.setOnClickListener(this);
            } else if (seats.charAt(i) == 'A') {
                count++;
                TextView view = new TextView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                        (seatSize, seatSize);
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                view.setLayoutParams(layoutParams);
                view.setPadding(0, 0, 0, 2 * seatGaping);
                view.setId(count);
                view.setGravity(Gravity.CENTER);
                view.setBackgroundResource(R.drawable.ic_chair_available);
                view.setTextColor(Color.WHITE);
                view.setTag(STATUS_AVAILABLE);
                view.setText(count + "");
                view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);
                layout.addView(view);
                seatViewList.add(view);
                view.setOnClickListener(this);
            } else if (seats.charAt(i) == 'R') {
                count++;
                TextView view = new TextView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                view.setLayoutParams(layoutParams);
                view.setPadding(0, 0, 0, 2 * seatGaping);
                view.setId(count);
                view.setGravity(Gravity.CENTER);
                view.setBackgroundResource(R.drawable.ic_chair_reserved);
                view.setText(count + "");
                view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);
                view.setTextColor(Color.WHITE);
                view.setTag(STATUS_RESERVED);
                layout.addView(view);
                seatViewList.add(view);
                view.setOnClickListener(this);
            } else if (seats.charAt(i) == '_') {
                TextView view = new TextView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(seatSize, seatSize);
                layoutParams.setMargins(seatGaping, seatGaping, seatGaping, seatGaping);
                view.setLayoutParams(layoutParams);
                view.setBackgroundColor(Color.TRANSPARENT);
                view.setText("");
                layout.addView(view);
            }

        }

    }

    @Override
    public void onClick(View v) {


        if ((int) v.getTag() == STATUS_AVAILABLE) {

            if (arrayListSelectedIds.contains(v.getId())) {
                //selectedIds = selectedIds.replace(+v.getId() + ",", "");
                v.setBackgroundResource(R.drawable.ic_chair_available);
                Integer index = arrayListSelectedIds.indexOf(v.getId());
                arrayListSeatSelection.remove(arrayListSeatSelection.get(index));
                arrayListSelectedIds.remove(arrayListSelectedIds.get(index));

                String selectedSeat = new String();
                for (int i = 0; i < arrayListSeatSelection.size(); i++) {
                    selectedSeat = selectedSeat + arrayListSeatSelection.get(i)+",";
                }
                txtSelected.setText("Selected Seat: " + selectedSeat);
            } else {

                arrayListSelectedIds.add(v.getId());
                arrayListSeatSelection.add(String.valueOf(v.getId()));
                v.setBackgroundResource(R.drawable.ic_chair_selected);

                String selectedSeat = new String();
                for (int i = 0; i < arrayListSeatSelection.size(); i++) {
                    selectedSeat = selectedSeat +arrayListSeatSelection.get(i)+",";
                }
                txtSelected.setText("Selected Seat: " + selectedSeat);
            }

        } else if ((int) v.getTag() == STATUS_BOOKED) {
            Toast.makeText(this, "Seat " + v.getId() + " is Booked", Toast.LENGTH_SHORT).show();
        } else if ((int) v.getTag() == STATUS_RESERVED) {
            Toast.makeText(this, "Seat " + v.getId() + " is Reserved", Toast.LENGTH_SHORT).show();
        }

    }

}