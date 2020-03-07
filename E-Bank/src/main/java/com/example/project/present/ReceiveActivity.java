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

public class ReceiveActivity extends AppCompatActivity {
    private TextView setting;
    private Button setting_btn;
    private ImageButton back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);

        Intent intent=getIntent();
        final String account=intent.getStringExtra("Account");
        setting=this.findViewById(R.id.setting);
        setting_btn=this.findViewById(R.id.setting_btn);
        setting_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText=new EditText(ReceiveActivity.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(ReceiveActivity.this);
                builder.setTitle("请设置金额").setView(editText).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(editText.getText().toString().equals("")){
                            Toast.makeText(ReceiveActivity.this,"内容不能为空",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            setting.setText(editText.getText().toString());
                        }
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).show();
            }
        });

        back_btn=this.findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ReceiveActivity.this,FunctionSelectActivity.class);
                intent.putExtra("Account",account);
                startActivity(intent);
            }
        });
    }
}
