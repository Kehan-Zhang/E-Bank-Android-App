package com.example.project.present;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.project.R;
import com.example.project.database.DBHelper;

public class RecordActivity extends AppCompatActivity {
    private TableLayout tableLayout;
    private ImageButton back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        Intent intent=getIntent();
        final String account=intent.getStringExtra("Account");

        DBHelper dbHelper=new DBHelper(RecordActivity.this);
        dbHelper.open();

        tableLayout=this.findViewById(R.id.tableLayout);
        int length = dbHelper.getOneData(account).length;
        for (int i = 0; i < length;i++){
                View layout = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_three_text_views,null);
                TextView time = (TextView) layout.findViewById(R.id.cardNumber);
                TextView detail = (TextView) layout.findViewById(R.id.name);
                TextView amount = (TextView) layout.findViewById(R.id.type);
                time.setText(dbHelper.getOneData(account)[i].Time);
                detail.setText(dbHelper.getOneData(account)[i].Detail);
                String number=String.valueOf(dbHelper.getOneData(account)[i].Amount);
                amount.setText(number);
                tableLayout.addView(layout);
        }
        back_btn=this.findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RecordActivity.this, FunctionSelectActivity.class);
                intent.putExtra("Account",account);
                startActivity(intent);
            }
        });
    }
}

