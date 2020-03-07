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
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.R;
import com.example.project.database.DBAdapter;
import com.example.project.database.DBHelper;
import com.example.project.entity.Record;
import com.example.project.entity.User;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AffirmTransferActivity extends AppCompatActivity {
    private EditText password;
    private TextView aimedAccount,amount;
    private ImageButton back_btn;
    private Button affirm_btn;
    private String password_input;
    private int residue,add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affirm_transfer);

        Intent intent=getIntent();
        final String account=intent.getStringExtra("Account");
        final String AimedAccount=intent.getStringExtra("AimedAccount");
        final String number=intent.getStringExtra("Amount");
        final int Amount=Integer.valueOf(intent.getStringExtra("Amount"));
        aimedAccount=this.findViewById(R.id.aimedAccount);
        aimedAccount.setText(AimedAccount);
        amount=this.findViewById(R.id.type);
        amount.setText(number);
        password=this.findViewById(R.id.password);


        final DBHelper dbHelper=new DBHelper(AffirmTransferActivity.this);
        dbHelper.open();
        final DBAdapter dbAdapter=new DBAdapter(AffirmTransferActivity.this);
        dbAdapter.open();

        affirm_btn=this.findViewById(R.id.affirm_btn);
        affirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password_input=password.getText().toString();
                if(password_input.equals("")){
                    Toast.makeText(AffirmTransferActivity.this,"内容不能为空",Toast.LENGTH_SHORT).show();
                }
                else if(!password_input.equals(dbAdapter.getOneData(account)[0].Pay)){
                    Toast.makeText(AffirmTransferActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
                }
                else{
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss ");
                    Date curDate = new Date(System.currentTimeMillis());
                    String time = formatter.format(curDate);

                    Record record=new Record();
                    record.Account=account;
                    record.Amount=Amount;
                    record.Detail="转账给用户"+AimedAccount;
                    record.Time=time;
                    dbHelper.insert(record);

                    residue=dbAdapter.getOneData(account)[0].Balance-Amount;
                    User user1=new User();
                    user1.Balance=residue;
                    dbAdapter.updateBalance(account,user1);
                    add=dbAdapter.getOneData(AimedAccount)[0].Balance+Amount;
                    User user2=new User();
                    user2.Balance=add;
                    dbAdapter.updateBalance(AimedAccount,user2);

                    AlertDialog.Builder builder = new AlertDialog.Builder(AffirmTransferActivity.this);
                    builder.setTitle("提示").setMessage("支付成功").setPositiveButton("确定", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(AffirmTransferActivity.this, FunctionSelectActivity.class);
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
                Intent intent=new Intent(AffirmTransferActivity.this,FunctionSelectActivity.class);
                intent.putExtra("Account",account);
                startActivity(intent);
            }
        });
    }
}
