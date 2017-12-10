package com.uppergain.stock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.HashMap;
import java.util.Map;

public class ProductRegistrationActivity extends AppCompatActivity {
    public static final int REQUEST_PRODUCT = 200;
    private TextView JanText;
    private EditText product;
    private EditText ex_num;
    private EditText price;
    private EditText purchase;
    private Button insertBtn;
    private MysqlData my = new MysqlData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent in = new Intent(getApplication(), ScanActivity.class);
        startActivityForResult(in, REQUEST_PRODUCT);
        setContentView(R.layout.activity_product_registration);


        JanText = (TextView)findViewById(R.id.scanJan);
        product = (EditText)findViewById(R.id.product);
        ex_num = (EditText)findViewById(R.id.price);
        price = (EditText)findViewById(R.id.price);
        purchase = (EditText)findViewById(R.id.purchase);
        insertBtn = (Button)findViewById(R.id.insetBtn);

        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                my.setProduct(product.getText().toString());
                my.setEx_num(ex_num.getText().toString());
                my.setPrice(price.getText().toString());
                my.setPurchase(purchase.getText().toString());
                newProductInsertDB();
                finish();
            }
        });

    }

    private void newProductInsertDB() {
        String url = "http://upper-gain.com/shinki.php";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(ProductRegistrationActivity.this, "新しい商品を登録しました。", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ProductRegistrationActivity.this, "登録できませんでした。", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("JanText", my.getJan());
                params.put("product", my.getProduct());
                params.put("ex_num", my.getEx_num());
                params.put("price", my.getPrice());
                params.put("purchase", my.getPurchase());
                System.out.println(new Logic().toStringToDay() + ":" + my.getJan() + ":" + my.getProduct() + ":" + my.getPrice());
                return params;
            }
        };
        queue.add(request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_PRODUCT && resultCode == RESULT_OK){
            if(data != null){
                final Barcode barcode = data.getParcelableExtra("barcode");
                JanText.post(new Runnable() {
                    @Override
                    public void run() {
                        JanText.setText(barcode.displayValue);
                        my.setJan(barcode.displayValue);
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                    }
                });
            }
        }
    }
}
