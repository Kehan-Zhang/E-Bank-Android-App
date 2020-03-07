package com.example.project.present;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.project.R;
import com.example.project.database.DBAdapter;
import com.example.project.database.DBHelper;
import com.example.project.entity.Record;
import com.example.project.entity.User;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DepositActivity extends AppCompatActivity {
    private EditText amount,password;
    private Spinner duration;
    private Button affirm_btn;
    private ImageButton back_btn;
    private static final String[] selection = { "30天", "90天", "180天", "一年", "三年" };
    private ArrayAdapter<String> adapter;
    private String length,amount_input,password_input;
    private int amount_input_integer,residue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);

        Intent intent=getIntent();
        final String account=intent.getStringExtra("Account");
        amount=this.findViewById(R.id.type);
        password=this.findViewById(R.id.password);

        duration = this.findViewById(R.id.duration);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, selection);
        duration.setAdapter(adapter);
        duration.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                length = selection[arg2];
                arg0.setVisibility(View.VISIBLE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        final DBHelper dbHelper=new DBHelper(DepositActivity.this);
        dbHelper.open();
        final DBAdapter dbAdapter=new DBAdapter(DepositActivity.this);
        dbAdapter.open();

        affirm_btn=this.findViewById(R.id.affirm_btn);
        affirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount_input=amount.getText().toString();
                password_input=password.getText().toString();
                if(amount_input.equals("")||password_input.equals("")){
                    Toast.makeText(DepositActivity.this,"内容不能为空",Toast.LENGTH_SHORT).show();
                }
                else if(!password_input.equals(dbAdapter.getOneData(account)[0].Pay)){
                    Toast.makeText(DepositActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
                }
                else if(Integer.valueOf(amount_input)>dbAdapter.getOneData(account)[0].Balance){
                    Toast.makeText(DepositActivity.this,"余额不足",Toast.LENGTH_SHORT).show();
                }
                else{
                    amount_input_integer=Integer.valueOf(amount_input);
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss ");
                    Date curDate = new Date(System.currentTimeMillis());
                    String time = formatter.format(curDate);

                    Record record=new Record();
                    record.Account=account;
                    record.Amount=amount_input_integer;
                    record.Detail="存款"+length;
                    record.Time=time;
                    dbHelper.insert(record);

                    residue=dbAdapter.getOneData(account)[0].Balance-amount_input_integer;
                    User user=new User();
                    user.Balance=residue;
                    dbAdapter.updateBalance(account,user);

                    AlertDialog.Builder builder = new AlertDialog.Builder(DepositActivity.this);
                    builder.setTitle("提示").setMessage("存款成功").setPositiveButton("确定", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(DepositActivity.this, FunctionSelectActivity.class);
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
                Intent intent=new Intent(DepositActivity.this,FunctionSelectActivity.class);
                intent.putExtra("Account",account);
                startActivity(intent);
            }
        });
    }
}
