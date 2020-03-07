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
import com.example.project.database.DBTooler;

public class CardPocketActivity extends AppCompatActivity {
    private ImageButton back_btn,addCard_btn;
    private TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_pocket);

        Intent intent=getIntent();
        final String account=intent.getStringExtra("Account");
        DBTooler dbTooler=new DBTooler(CardPocketActivity.this);
        dbTooler.open();
        tableLayout=this.findViewById(R.id.tableLayout);
        int length = dbTooler.getOneData(account).length;
        for (int i = 0; i < length;i++){
            View layout = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_card_view,null);
            TextView cardNumber = (TextView) layout.findViewById(R.id.cardNumber);
            TextView name = (TextView) layout.findViewById(R.id.name);
            TextView type = (TextView) layout.findViewById(R.id.type);
            cardNumber.setText(dbTooler.getOneData(account)[i].CardNumber);
            name.setText(dbTooler.getOneData(account)[i].OwnerName);
            type.setText(dbTooler.getOneData(account)[i].Type);
            tableLayout.addView(layout);
        }

        addCard_btn=this.findViewById(R.id.addCard_btn);
        addCard_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CardPocketActivity.this,ApplyCreditCardActivity.class);
                intent.putExtra("Account",account);
                startActivity(intent);
            }
        });

        back_btn=this.findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CardPocketActivity.this, FunctionSelectActivity.class);
                intent.putExtra("Account",account);
                startActivity(intent);
            }
        });
    }
}
