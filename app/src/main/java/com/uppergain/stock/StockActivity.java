package com.uppergain.stock;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StockActivity extends AppCompatActivity {
    private ListView listView;
    private MysqlData my = new MysqlData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.stockList);
        listView();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void listView() {
        System.out.println("listView:ok");
        String url = "http://upper-gain.com/stock.php";
        RequestQueue queue = Volley.newRequestQueue(this);


        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("onResponse:ok");
                        try {
                            JSONObject item = new JSONObject(response);
                            JSONArray jArray = item.getJSONArray("SQL_TEST");
                            int count = jArray.length();
                            JSONObject[] jlist = new JSONObject[count];
                            ArrayList<MysqlData> list = new ArrayList<>();
                            MyAdapter myAdapter = new MyAdapter(StockActivity.this);

                            myAdapter.setMysqlData(list);
                            listView.setAdapter(myAdapter);

                            for (int i = 0; i < count; i++) {
                                jlist[i] = jArray.getJSONObject(i);
                                my = new MysqlData(jlist[i].getString("jan"), jlist[i].getString("product"), jlist[i].getString("price"));
                                System.out.println(my.getJan());
                                System.out.println(my.getProduct());
                                System.out.println(my.getEx_num());

                                MysqlData m = new MysqlData();
                                m.setJan(my.getJan());
                                m.setProduct(my.getProduct());
                                m.setPrice(my.getEx_num());
                                m.setPrice(my.getPrice());
                                list.add(m);
                                myAdapter.notifyDataSetChanged();

                            }


                            System.out.println("count:" + count);

                        } catch (JSONException e) {
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("sellday", new Logic().toStringToDay());
                return params;
            }
        };
        queue.add(request);

    }

}
