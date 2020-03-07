package com.example.project.present;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.project.R;
import com.example.project.database.DBAdapter;
import com.example.project.entity.User;

public class RegisterActivity extends AppCompatActivity {

    private EditText phoneNumber,certification,password;
    private RadioButton agree;
    private Button obtainCertification_btn,affirmRegister_btn;
    private String phoneNumber_input,certification_input,password_input;
    private TextView message;
    private int balance=5000;
    private String pay="1111";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        phoneNumber=this.findViewById(R.id.phoneNumber);
        certification=this.findViewById(R.id.certification);
        password=this.findViewById(R.id.password);
        message=this.findViewById(R.id.message);
        agree=this.findViewById(R.id.agree);

        obtainCertification_btn=this.findViewById(R.id.obtainCertification);
        obtainCertification_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBAdapter dbAdapter=new DBAdapter(RegisterActivity.this);
                dbAdapter.open();
                phoneNumber_input=phoneNumber.getText().toString();
                if(phoneNumber_input.equals("")){
                    message.setText("手机号不能为空");
                }
                else if(dbAdapter.getOneData(phoneNumber_input)!=null){
                    message.setText("该手机号已注册");
                }
                else{
                    message.setText("验证码为7968");
                }
            }
        });

        affirmRegister_btn=this.findViewById(R.id.affirmRegister);
        affirmRegister_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBAdapter dbAdapter=new DBAdapter(RegisterActivity.this);
                dbAdapter.open();
                certification_input=certification.getText().toString();
                password_input=password.getText().toString();

                if(certification_input.equals("")){
                    message.setText("验证码不能为空");
                }
                else if(!certification_input.equals("7968")){
                    message.setText("验证码错误");
                }
                else if(password_input.equals("")) {
                    message.setText("密码不能为空");
                }
                else if(!agree.isChecked()){
                    message.setText("请阅读《电子银行个人用户服务协议》");
                }
                else{
                    User user1=new User();
                    user1.Account=phoneNumber_input;
                    user1.Password=password_input;
                    user1.Balance=balance;
                    user1.Pay=pay;
                    dbAdapter.insert(user1);

                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setTitle("提示").setMessage("注册成功").setPositiveButton("确定", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    }).show();
                }
            }
        });
    }
}
