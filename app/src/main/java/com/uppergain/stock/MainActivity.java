package com.uppergain.stock;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final int PERMISSION_REQUEST = 200;
    public static final int REQUEST_LODE = 0;
    private int totalToday = 0;
    private ListView listView;
    private TextView today;
    private TextView storeName;
    private TextView tougituV;
    private TextView gekkan;
    private TextView kaiten;
    private TextView shire;
    private MysqlData my = new MysqlData();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //https://goo.gl/4b93sm

        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, PERMISSION_REQUEST);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                //Intent in = new Intent(getApplication(), SalesRegistrationActivity.class);
                //startActivity(in);
                Intent intent = new Intent(getApplication(), SalesRegistrationActivity.class);
                startActivityForResult(intent, REQUEST_LODE);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        today = (TextView) findViewById(R.id.today);
        today.setText(new Logic().toStringToDay());
        tougituV = (TextView) findViewById(R.id.tougituV);
        gekkan = (TextView) findViewById(R.id.gekkan);
        kaiten = (TextView) findViewById(R.id.kaiten);
        shire = (TextView) findViewById(R.id.shire);

        listView = (ListView) findViewById(R.id.listView);
        listView();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_shiire) {
        } else if (id == R.id.nav_shiire) {

        } else if (id == R.id.nav_touroku) {
            //Intent in = new Intent(getApplication(), SalesRegistrationActivity.class);
            Intent in = new Intent(getApplication(), ProductRegistrationActivity.class);
            startActivity(in);
        } else if (id == R.id.nav_staff) {

        } else if (id == R.id.nav_check) {
            Intent in = new Intent(getApplication(), StockActivity.class);
            startActivity(in);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void listView() {
        System.out.println("listView:ok");
        String url = "http://upper-gain.com/list.php";
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
                            MyAdapter myAdapter = new MyAdapter(MainActivity.this);

                            myAdapter.setMysqlData(list);
                            listView.setAdapter(myAdapter);

                            for (int i = 0; i < count; i++) {
                                jlist[i] = jArray.getJSONObject(i);
                                my = new MysqlData(jlist[i].getString("jan"), jlist[i].getString("product"), jlist[i].getString("price"));
                                totalToday += Integer.parseInt(my.getPrice());
                                System.out.println(my.getJan());
                                System.out.println(my.getProduct());
                                System.out.println(my.getPrice());

                                MysqlData m = new MysqlData();
                                m.setJan(my.getJan());
                                m.setProduct(my.getProduct());
                                m.setPrice(my.getPrice());
                                list.add(m);
                                myAdapter.notifyDataSetChanged();

                            }


                            System.out.println("合計：" + totalToday);
                            tougituV.setText("¥" + String.valueOf(totalToday));

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_LODE && resultCode == RESULT_OK) {
            reload();
            System.out.println("onRestart:ok");
        }
    }


    private void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

}
