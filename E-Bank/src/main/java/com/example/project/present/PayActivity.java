package com.example.project.present;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.project.R;
import com.example.project.database.DBAdapter;
import com.example.project.database.DBHelper;
import com.example.project.entity.Record;
import com.example.project.entity.User;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PayActivity extends AppCompatActivity {
    private EditText companyNumber,price,password;
    private Button affirmPay_btn;
    private ImageButton back_btn;
    private String companyNumber_input,price_input,password_input;
    private int price_input_integer,residue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        Intent intent=getIntent();
        final String account=intent.getStringExtra("Account");
        companyNumber=this.findViewById(R.id.companyNumber);
        price=this.findViewById(R.id.price);
        password=this.findViewById(R.id.password);

        final DBHelper dbHelper=new DBHelper(PayActivity.this);
        dbHelper.open();
        final DBAdapter dbAdapter=new DBAdapter(PayActivity.this);
        dbAdapter.open();

        affirmPay_btn=this.findViewById(R.id.affirmPay_btn);
        affirmPay_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                companyNumber_input=companyNumber.getText().toString();
                price_input=price.getText().toString();
                password_input=password.getText().toString();
                if(companyNumber_input.equals("")||price_input.equals("")||password_input.equals("")){
                    Toast.makeText(PayActivity.this,"内容不能为空",Toast.LENGTH_SHORT).show();
                }
                else if(!password_input.equals(dbAdapter.getOneData(account)[0].Pay)){
                    Toast.makeText(PayActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
                }
                else if(Integer.valueOf(price_input)>dbAdapter.getOneData(account)[0].Balance){
                    Toast.makeText(PayActivity.this,"余额不足",Toast.LENGTH_SHORT).show();
                }
                else{
                    price_input_integer=Integer.valueOf(price_input);
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss ");
                    Date curDate = new Date(System.currentTimeMillis());
                    String time = formatter.format(curDate);

                    Record record=new Record();
                    record.Account=account;
                    record.Amount=price_input_integer;
                    record.Detail="付款给商家"+companyNumber_input;
                    record.Time=time;
                    dbHelper.insert(record);

                    residue=dbAdapter.getOneData(account)[0].Balance-price_input_integer;
                    User user=new User();
                    user.Balance=residue;
                    dbAdapter.updateBalance(account,user);

                    AlertDialog.Builder builder = new AlertDialog.Builder(PayActivity.this);
                    builder.setTitle("提示").setMessage("支付成功").setPositiveButton("确定", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(PayActivity.this,FunctionSelectActivity.class);
                            intent.putExtra("Account",account);
                            startActivity(intent);
                        }
                    }).show();
                }
            }
        });

        back_btn=this.findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PayActivity.this,FunctionSelectActivity.class);
                intent.putExtra("Account",account);
                startActivity(intent);
            }
        });
    }
}
