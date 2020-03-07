package com.example.project.present;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.project.R;

public class PurchaseProductActivity extends AppCompatActivity {
    private ImageButton back_btn;
    private Button product1_purchase_btn,product2_purchase_btn,product3_purchase_btn,product4_purchase_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_product);

        Intent intent=getIntent();
        final String account=intent.getStringExtra("Account");

        product1_purchase_btn=this.findViewById(R.id.purchase1_btn);
        product1_purchase_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PurchaseProductActivity.this, AffirmPurchaseActivity.class);
                intent.putExtra("Product",1);
                intent.putExtra("Account",account);
                startActivity(intent);
            }
        });

        product2_purchase_btn=this.findViewById(R.id.purchase2_btn);
        product2_purchase_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PurchaseProductActivity.this,AffirmPurchaseActivity.class);
                intent.putExtra("Product",2);
                intent.putExtra("Account",account);
                startActivity(intent);
            }
        });

        product3_purchase_btn=this.findViewById(R.id.purchase3_btn);
        product3_purchase_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PurchaseProductActivity.this,AffirmPurchaseActivity.class);
                intent.putExtra("Product",3);
                intent.putExtra("Account",account);
                startActivity(intent);
            }
        });

        product4_purchase_btn=this.findViewById(R.id.purchase4_btn);
        product4_purchase_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PurchaseProductActivity.this,AffirmPurchaseActivity.class);
                intent.putExtra("Product",4);
                intent.putExtra("Account",account);
                startActivity(intent);
            }
        });

        back_btn=this.findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PurchaseProductActivity.this,FunctionSelectActivity.class);
                intent.putExtra("Account",account);
                startActivity(intent);
            }
        });
    }
}
