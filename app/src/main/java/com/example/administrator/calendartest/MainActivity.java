package com.example.administrator.calendartest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private String selectedDate="2016-5-19";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomDateDialog.Builder builder = new BottomDateDialog.Builder(MainActivity.this, selectedDate);
                final BottomDialog dialog = builder.create();
                dialog.show();
                builder.setOnDialogClick(new BottomDateDialog.Builder.OnDialogClick() {
                    @Override
                    public void onClick(String date) {
                        selectedDate = date;
                    }
                });
            }
        });
    }
}
