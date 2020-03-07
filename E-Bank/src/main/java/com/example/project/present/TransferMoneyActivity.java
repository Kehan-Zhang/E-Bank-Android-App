package com.example.project.present;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.R;
import com.example.project.database.DBAdapter;
import com.example.project.database.DBHelper;

public class TransferMoneyActivity extends AppCompatActivity {
    private EditText aimedAccount,amount;
    private TextView balance;
    private Button next_btn;
    private ImageButton back_btn;
    private String aimedAccount_input,amount_input;
    private int number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_money);

        Intent intent=getIntent();
        final String account=intent.getStringExtra("Account");

        aimedAccount=this.findViewById(R.id.aimedAccount);
        balance=this.findViewById(R.id.balance);
        amount=this.findViewById(R.id.type);

        final DBHelper dbHelper=new DBHelper(TransferMoneyActivity.this);
        dbHelper.open();
        final DBAdapter dbAdapter=new DBAdapter(TransferMoneyActivity.this);
        dbAdapter.open();

        number=dbAdapter.getOneData(account)[0].Balance;
        balance.setText(String.valueOf(number));

        next_btn=this.findViewById(R.id.next_btn);
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aimedAccount_input=aimedAccount.getText().toString();
                amount_input=amount.getText().toString();
                if(aimedAccount_input.equals("")||amount_input.equals("")){
                    Toast.makeText(TransferMoneyActivity.this,"内容不能为空",Toast.LENGTH_SHORT).show();
                }
                else if(dbAdapter.getOneData(aimedAccount_input)==null){
                    Toast.makeText(TransferMoneyActivity.this,"用户不存在",Toast.LENGTH_SHORT).show();
                }
                else if(Integer.valueOf(amount_input)>dbAdapter.getOneData(account)[0].Balance){
                    Toast.makeText(TransferMoneyActivity.this,"余额不足",Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent=new Intent(TransferMoneyActivity.this, AffirmTransferActivity.class);
                    intent.putExtra("Account",account);
                    intent.putExtra("Amount",amount_input);
                    intent.putExtra("AimedAccount",aimedAccount_input);
                    startActivity(intent);
                }
            }
        });

        back_btn=this.findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TransferMoneyActivity.this, FunctionSelectActivity.class);
                intent.putExtra("Account",account);
                startActivity(intent);
            }
        });
    }
}
