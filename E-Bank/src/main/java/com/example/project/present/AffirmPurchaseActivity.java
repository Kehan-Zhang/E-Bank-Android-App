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

public class AffirmPurchaseActivity extends AppCompatActivity {
    private ImageButton back_btn;
    private TextView name,ratio,level,bank,leastAmount,duration,productId;
    private Button affirm_btn;
    private EditText password;
    private String input;
    private int cost,residue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affirm_purchase);

        name=this.findViewById(R.id.name);
        ratio=this.findViewById(R.id.ratio);
        level=this.findViewById(R.id.level);
        bank=this.findViewById(R.id.bank);
        leastAmount=this.findViewById(R.id.leastAmount);
        duration=this.findViewById(R.id.duration);
        productId=this.findViewById(R.id.productId);
        password=this.findViewById(R.id.password);
        input=password.getText().toString();
        Intent intent=getIntent();
        final String account=intent.getStringExtra("Account");
        final int product=intent.getIntExtra("Product",0);
        switch (product){
            case 1:name.setText("“稳得利”中国MG银行养老理财");ratio.setText("3.071%");level.setText("中等");bank.setText("中国MG银行");leastAmount.setText("1000");duration.setText("180天");productId.setText("W1018");break;
            case 2:name.setText("“稳健级”中国MG银行全权委托财产管理");ratio.setText("5.150%");level.setText("偏高");bank.setText("中国MG银行");leastAmount.setText("200");duration.setText("30天");productId.setText("W2030");break;
            case 3:name.setText("“月月赢”中国MG银行挂钩黄金AU9999");ratio.setText("2.125%");level.setText("偏低");bank.setText("中国MG银行");leastAmount.setText("10");duration.setText("2年");productId.setText("Y1002");break;
            case 4:name.setText("“净值型”中国MG银行个人增享365");ratio.setText("3.467%");level.setText("中等");bank.setText("中国MG银行");leastAmount.setText("10000");duration.setText("365天");productId.setText("J1036");break;
            default:break;
        }

        final DBHelper dbHelper=new DBHelper(AffirmPurchaseActivity.this);
        dbHelper.open();
        final DBAdapter dbAdapter=new DBAdapter(AffirmPurchaseActivity.this);
        dbAdapter.open();

        affirm_btn=this.findViewById(R.id.affirm_btn);
        affirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input=password.getText().toString();
                if(input.equals("")){
                    Toast.makeText(AffirmPurchaseActivity.this,"内容不能为空",Toast.LENGTH_SHORT).show();
                }
                else if(!input.equals(dbAdapter.getOneData(account)[0].Pay)){
                    Toast.makeText(AffirmPurchaseActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
                }
                else if(Integer.valueOf(leastAmount.getText().toString())>dbAdapter.getOneData(account)[0].Balance){
                    Toast.makeText(AffirmPurchaseActivity.this,"余额不足",Toast.LENGTH_SHORT).show();
                }
                else{
                    cost=Integer.valueOf(leastAmount.getText().toString());
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss ");
                    Date curDate = new Date(System.currentTimeMillis());
                    String time = formatter.format(curDate);

                    Record record=new Record();
                    record.Account=account;
                    record.Amount=cost;
                    record.Detail="购买理财产品"+productId.getText().toString();
                    record.Time=time;
                    dbHelper.insert(record);

                    residue=dbAdapter.getOneData(account)[0].Balance-cost;
                    User user=new User();
                    user.Balance=residue;
                    dbAdapter.updateBalance(account,user);

                    AlertDialog.Builder builder = new AlertDialog.Builder(AffirmPurchaseActivity.this);
                    builder.setTitle("提示").setMessage("支付成功").setPositiveButton("确定", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(AffirmPurchaseActivity.this, PurchaseProductActivity.class);
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
                Intent intent=new Intent(AffirmPurchaseActivity.this,PurchaseProductActivity.class);
                intent.putExtra("Account",account);
                startActivity(intent);
            }
        });
    }
}
