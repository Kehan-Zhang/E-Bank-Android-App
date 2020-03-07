package com.example.project.present;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.R;
import com.example.project.database.DBAdapter;
import com.example.project.entity.User;

public class PersonalInformationActivity extends AppCompatActivity {

    private TextView account,balance,phone,location,post,name,occupation,payPassword;
    private Button phoneModify_btn,locationModify_btn,postModify_btn,nameModify_btn,occupationModify_btn,pay_btn;
    private ImageButton returnMain_btn;
    private Switch showBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);

        account=this.findViewById(R.id.account);
        balance=this.findViewById(R.id.balance);
        phone=this.findViewById(R.id.phone);
        location=this.findViewById(R.id.location);
        post=this.findViewById(R.id.post);
        name=this.findViewById(R.id.name);
        occupation=this.findViewById(R.id.occupation);
        payPassword=this.findViewById(R.id.paypassword);

        final DBAdapter dbAdapter=new DBAdapter(PersonalInformationActivity.this);
        dbAdapter.open();
        Intent intent=getIntent();
        final String accountName=intent.getStringExtra("Account");
        account.setText(accountName);
        final String Balance=Integer.toString(dbAdapter.getOneData(accountName)[0].Balance);
        final String Pay=dbAdapter.getOneData(accountName)[0].Pay;
        payPassword.setText(Pay);

        showBalance=this.findViewById(R.id.showBalance);
        showBalance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    balance.setText(Balance);
                    showBalance.setTextOn("隐藏");
                }
                else{
                    balance.setText("*****");
                    showBalance.setTextOff("显示");
                }
            }
        });

        phoneModify_btn=this.findViewById(R.id.phoneModify_btn);
        phoneModify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText editText=new EditText(PersonalInformationActivity.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(PersonalInformationActivity.this);
                builder.setTitle("请输入电话号码").setView(editText).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(editText.getText().toString().equals("")){
                            Toast.makeText(PersonalInformationActivity.this,"内容不能为空",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            phone.setText(editText.getText().toString());
                        }
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).show();
            }
        });

        locationModify_btn=this.findViewById(R.id.locationModify_btn);
        locationModify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText=new EditText(PersonalInformationActivity.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(PersonalInformationActivity.this);
                builder.setTitle("请输入家庭地址").setView(editText).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(editText.getText().toString().equals("")){
                            Toast.makeText(PersonalInformationActivity.this,"内容不能为空",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            location.setText(editText.getText().toString());
                        }
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).show();
            }
        });

        postModify_btn=this.findViewById(R.id.postModify_btn);
        postModify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText=new EditText(PersonalInformationActivity.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(PersonalInformationActivity.this);
                builder.setTitle("请输入邮编").setView(editText).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(editText.getText().toString().equals("")){
                            Toast.makeText(PersonalInformationActivity.this,"内容不能为空",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            post.setText(editText.getText().toString());
                        }
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).show();
            }
        });

        nameModify_btn=this.findViewById(R.id.nameModify_btn);
        nameModify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText=new EditText(PersonalInformationActivity.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(PersonalInformationActivity.this);
                builder.setTitle("请输入姓名").setView(editText).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(editText.getText().toString().equals("")){
                            Toast.makeText(PersonalInformationActivity.this,"内容不能为空",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            name.setText(editText.getText().toString());
                        }
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).show();
            }
        });

        occupationModify_btn=this.findViewById(R.id.occupationModify_btn);
        occupationModify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText=new EditText(PersonalInformationActivity.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(PersonalInformationActivity.this);
                builder.setTitle("请输入职业").setView(editText).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(editText.getText().toString().equals("")){
                            Toast.makeText(PersonalInformationActivity.this,"内容不能为空",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            occupation.setText(editText.getText().toString());
                        }
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).show();
            }
        });

        returnMain_btn=this.findViewById(R.id.returnMain_btn);
        returnMain_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PersonalInformationActivity.this,FunctionSelectActivity.class);
                intent.putExtra("Account",accountName);
                startActivity(intent);
            }
        });

        pay_btn=this.findViewById(R.id.pay_btn);
        pay_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText1=new EditText(PersonalInformationActivity.this);
                //final EditText editText2=new EditText(PersonalInformationActivity.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(PersonalInformationActivity.this);
                builder.setTitle("请设置新密码").setView(editText1).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(editText1.getText().toString().equals("")){
                            Toast.makeText(PersonalInformationActivity.this,"内容不能为空",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            payPassword.setText(editText1.getText().toString());
                            User user1=new User();
                            user1.Pay=editText1.getText().toString();
                            dbAdapter.updatePay(accountName,user1);
                        }
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).show();
            }
        });
    }
}
