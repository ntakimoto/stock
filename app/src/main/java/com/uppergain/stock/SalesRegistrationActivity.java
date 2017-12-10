package com.uppergain.stock;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.vision.barcode.Barcode;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SalesRegistrationActivity extends AppCompatActivity {
    public static final int REQUEST_SALES = 100;
    private TextView scanJan;
    private TextView t_product;
    private TextView t_price;
    private Button regButton;
    private MysqlData my = new MysqlData();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent in = new Intent(getApplication(), ScanActivity.class);
        startActivityForResult(in, REQUEST_SALES);

        scanJan = (TextView)findViewById(R.id.scanJan);
        t_product = (TextView) findViewById(R.id.product);
        t_price = (TextView) findViewById(R.id.price);
        regButton = (Button) findViewById(R.id.regButton);
        regButton.setVisibility(View.INVISIBLE);


        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertDB();
            }
        });

    }
    //商品検索→検索結果表示→インスタンスへ保存
    private void postRequest() {
        String url = "http://upper-gain.com/test.php";
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject item = jsonObject.getJSONObject("SQL_TEST");
                            String jan = item.getString("jan");
                            my.setJan(jan);
                            String product = item.getString("product");
                            my.setProduct(product);
                            t_product.setText(my.getProduct());
                            String price = item.getString("price");
                            my.setPrice(price);
                            t_price.setText("¥" + my.getPrice());
                        } catch (JSONException e) {
                            // error
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SalesRegistrationActivity.this, "商品の登録がありませんよ。", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                //params.put("JanText", postJan);
                params.put("JanText", my.getJan());
                //System.out.println("post:" + postJan);
                System.out.println("post:" + my.getJan());
                System.out.println(new Logic().toStringToDay());
                return params;
            }
        };
        queue.add(request);

    }

    private void showToast() {
        if (false) {
            Toast.makeText(this, "商品登録がありません", Toast.LENGTH_SHORT).show();
        }

        Toast.makeText(this, "登録しました", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.putExtra("lode", 1);
        setResult(RESULT_OK, intent);
        finish();
    }

    //販売商品登録
    private void insertDB() {
        String url = "http://upper-gain.com/insert.php";
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        showToast();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SalesRegistrationActivity.this, "商品の登録がありません。", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("date", new Logic().toStringToDay());
                //params.put("store","清瀬支店");
                params.put("JanText", my.getJan());
                params.put("product", my.getProduct());
                params.put("price", my.getPrice());
                //params.put("name",my.getName());
                System.out.println(new Logic().toStringToDay() + ":" + my.getJan() + ":" + my.getProduct() + ":" + my.getPrice());
                return params;
            }
        };
        queue.add(request);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_SALES && resultCode == RESULT_OK){
            if(data != null){
                final Barcode barcode = data.getParcelableExtra("barcode");
                scanJan.post(new Runnable() {
                    @Override
                    public void run() {
                        scanJan.setText(barcode.displayValue);
                        my.setJan(barcode.displayValue);
                        postRequest();
                        //insertDB();
                        //if(my.getPrice() == null){
                            //newProductInsertDB();
                        //}
                        regButton.setVisibility(View.VISIBLE);
                    }
                });
            }
        }
    }


}
