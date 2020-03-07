package com.example.project.present;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.project.R;

public class FunctionSelectActivity extends AppCompatActivity {
    private TextView accountName;
    private ImageButton personalInformation_btn,transfer_btn,deposit_btn,purchase_btn,apply_btn,record_btn;
    private Button pay_btn,receive_btn,card_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function_select);

        Intent intent=getIntent();
        final String account=intent.getStringExtra("Account");
        accountName=this.findViewById(R.id.user);
        accountName.setText("欢迎，用户"+account);

        personalInformation_btn=this.findViewById(R.id.personalInformation_btn);
        personalInformation_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FunctionSelectActivity.this,PersonalInformationActivity.class);
                intent.putExtra("Account",account);
                startActivity(intent);
            }
        });

        pay_btn=this.findViewById(R.id.pay_btn);
        pay_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FunctionSelectActivity.this,PayActivity.class);
                intent.putExtra("Account",account);
                startActivity(intent);
            }
        });

        receive_btn=this.findViewById(R.id.receive_btn);
        receive_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FunctionSelectActivity.this,ReceiveActivity.class);
                intent.putExtra("Account",account);
                startActivity(intent);
            }
        });

        card_btn=this.findViewById(R.id.card_btn);
        card_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FunctionSelectActivity.this, CardPocketActivity.class);
                intent.putExtra("Account",account);
                startActivity(intent);
            }
        });

        transfer_btn=this.findViewById(R.id.transfer_btn);
        transfer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FunctionSelectActivity.this, TransferMoneyActivity.class);
                intent.putExtra("Account",account);
                startActivity(intent);
            }
        });

        deposit_btn=this.findViewById(R.id.deposite_btn);
        deposit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FunctionSelectActivity.this, DepositActivity.class);
                intent.putExtra("Account",account);
                startActivity(intent);
            }
        });

        purchase_btn=this.findViewById(R.id.purchase_btn);
        purchase_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FunctionSelectActivity.this,PurchaseProductActivity.class);
                intent.putExtra("Account",account);
                startActivity(intent);
            }
        });

        apply_btn=this.findViewById(R.id.apply_btn);
        apply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FunctionSelectActivity.this, ApplyCreditCardActivity.class);
                intent.putExtra("Account",account);
                startActivity(intent);
            }
        });

        record_btn=this.findViewById(R.id.record_btn);
        record_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FunctionSelectActivity.this, RecordActivity.class);
                intent.putExtra("Account",account);
                startActivity(intent);
            }
        });
    }
}
