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
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.R;
import com.example.project.database.DBAdapter;
import com.example.project.database.DBTooler;
import com.example.project.entity.Card;

public class ApplyCreditCardActivity extends AppCompatActivity {
    private EditText name,idNumber,credit;
    private TextView limit;
    private ImageButton back_btn;
    private Button affirm_btn;
    private Spinner bankName,type;
    private int cardNumber=62304913,cardLimit,count;
    private String input1,input2,input3,cardBank,cardType;
    private static final String[] selectBank = { "请选择银行","中国MG银行", "中国工商银行", "中国农业银行", "中国建设银行", "中国交通银行" };
    private static final String[] selectType = { "请选择信用卡类型","UnionPay", "Visa", "MasterCard", "JCB", "DISCOVER" };
    private ArrayAdapter<String> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_credit_card);

        Intent intent=getIntent();
        final String account=intent.getStringExtra("Account");
        final DBTooler dbTooler=new DBTooler(ApplyCreditCardActivity.this);
        dbTooler.open();
        final DBAdapter dbAdapter=new DBAdapter(ApplyCreditCardActivity.this);
        dbAdapter.open();

        name=this.findViewById(R.id.name);
        idNumber=this.findViewById(R.id.idNumber);
        credit=this.findViewById(R.id.credit);
        limit=this.findViewById(R.id.limit);
        bankName=this.findViewById(R.id.bankName);
        type=this.findViewById(R.id.type);

        cardLimit=Integer.valueOf(dbAdapter.getOneData(account)[0].Balance)*2;
        limit.setText(String.valueOf(cardLimit));
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, selectBank);
        //将adapter添加到m_Spinner中
        bankName.setAdapter(adapter);
        bankName.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                cardBank = selectBank[arg2];
                arg0.setVisibility(View.VISIBLE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, selectType);
        //将adapter添加到m_Spinner中
        type.setAdapter(adapter);
        type.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                cardType = selectType[arg2];
                arg0.setVisibility(View.VISIBLE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        affirm_btn=this.findViewById(R.id.affirm_btn);
        affirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input1=name .getText().toString();
                input2=idNumber.getText().toString();
                input3=credit.getText().toString();
                if(dbTooler.getOneData(account)==null){
                    count=0;
                }
                else{
                    count=dbTooler.getOneData(account).length;
                }
                if(input1.equals("")||input2.equals("")||input3.equals("")){
                    Toast.makeText(ApplyCreditCardActivity.this,"内容不能为空",Toast.LENGTH_SHORT).show();
                }
                else if(cardBank.equals("请选择银行")||cardType.equals("请选择信用卡类型")){
                    Toast.makeText(ApplyCreditCardActivity.this,"银行或信用卡类型尚未选择",Toast.LENGTH_SHORT).show();
                }
                else if(count==2){
                    Toast.makeText(ApplyCreditCardActivity.this,"信用卡数量过多",Toast.LENGTH_SHORT).show();
                }
                else if(Integer.valueOf(input3)>cardLimit){
                    Toast.makeText(ApplyCreditCardActivity.this,"申请额度超出上限",Toast.LENGTH_SHORT).show();
                }
                else{
                    Card card=new Card();
                    card.OwnerName=input1;
                    card.IDNumber=input2;
                    card.BankName=cardBank;
                    card.Type=cardType;
                    card.Account=account;
                    card.Credit=Integer.valueOf(input3);
                    card.CardNumber=String.valueOf(cardNumber+count);
                    dbTooler.insert(card);
                    AlertDialog.Builder builder = new AlertDialog.Builder(ApplyCreditCardActivity.this);
                    builder.setTitle("提示").setMessage("信用卡申请成功").setPositiveButton("确定", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(ApplyCreditCardActivity.this, FunctionSelectActivity.class);
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
                Intent intent=new Intent(ApplyCreditCardActivity.this,FunctionSelectActivity.class);
                intent.putExtra("Account",account);
                startActivity(intent);
            }
        });
    }
}
