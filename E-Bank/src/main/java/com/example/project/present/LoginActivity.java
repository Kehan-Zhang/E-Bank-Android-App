package com.example.project.present;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.project.R;
import com.example.project.database.DBAdapter;

public class LoginActivity extends AppCompatActivity  {

    private EditText account,password;
    private String account_input,password_input;
    private Button login_btn,register_btn;
    private TextView information;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        account=this.findViewById(R.id.account);
        password=this.findViewById(R.id.password);
        information = this.findViewById(R.id.information);

        login_btn = this.findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                account_input= account.getText().toString();
                password_input= password.getText().toString();

                DBAdapter dbAdapter=new DBAdapter(LoginActivity.this);
                dbAdapter.open();
                if(account_input.equals("")){
                    information.setText("用户名不能为空");
                }
                else if(dbAdapter.getOneData(account_input)==null){
                    information.setText("用户名不存在");
                }
                else if(dbAdapter.getOneData(account_input)[0].Password.equals(password_input)){
                    information.setText("登录成功");
                    Intent intent = new Intent(LoginActivity.this, FunctionSelectActivity.class);
                    intent.putExtra("Account",account_input);
                    startActivity(intent);
              }
                else{
                    information.setText("密码错误");
                }
            }
        });

        register_btn = this.findViewById(R.id.register_btn);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        }

}
