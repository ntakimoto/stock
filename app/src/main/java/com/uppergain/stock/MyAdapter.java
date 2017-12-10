package com.uppergain.stock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by pcuser on 2017/12/09.
 */

public class MyAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater = null;
    ArrayList<MysqlData> mysqlData;

    public MyAdapter(Context context) {
        this.context = context;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setMysqlData(ArrayList<MysqlData> mysqlData) {
        this.mysqlData = mysqlData;
    }

    @Override
    public int getCount() {
        return mysqlData.size();
    }

    @Override
    public Object getItem(int position) {
        return mysqlData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mysqlData.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.adapter,parent,false);

        ((TextView)convertView.findViewById(R.id.jan)).setText(String.valueOf(mysqlData.get(position).getJan()));
        ((TextView)convertView.findViewById(R.id.name)).setText(mysqlData.get(position).getProduct());
        ((TextView)convertView.findViewById(R.id.price)).setText(String.valueOf(mysqlData.get(position).getPrice())+"円");
        //((TextView)convertView.findViewById(R.id.price)).setText(String.valueOf(mysqlData.get(position).getEx_num())+"個");


        return convertView;
    }
}