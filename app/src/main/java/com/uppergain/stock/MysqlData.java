package com.uppergain.stock;

/**
 * Created by pcuser on 2017/11/24.
 */

public class MysqlData {
    private String jan;
    private String product;
    private String price;
    private String name;
    private String ex_num;
    private String purchase;
    private String today_Sum;
    private String usermail;
    private static String userpass;
    private static String store;
    private long id;
    private static int count;

    public MysqlData(){}

    public MysqlData(String jan,String product,String price){
        this.jan = jan;
        this.product = product;
        this.price = price;
    }

    public MysqlData(String store,String jan,String product,String price,String name){
        this.store = store;
        this.jan = jan;
        this.product = product;
        this.price = price;
        this.name = name;
    }

    public String getJan() {
        return jan;
    }

    public void setJan(String jan) {
        this.jan = jan;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }
    public String getEx_num() {
        return ex_num;
    }

    public void setEx_num(String ex_num) {
        this.ex_num = ex_num;
    }

    public String getPurchase() {
        return purchase;
    }

    public void setPurchase(String purchase) {
        this.purchase = purchase;
    }

    public String getToday_Sum() {
        return today_Sum;
    }

    public void setToday_Sum(String today_Sum) {
        this.today_Sum = today_Sum;
    }
    public static String getUserpass() {
        return userpass;
    }

    public static void setUserpass(String userpass) {
        MysqlData.userpass = userpass;
    }

    public String getUsermail() {
        return usermail;
    }

    public void setUsermail(String usermail) {
        this.usermail = usermail;
    }

    public long getId() {
        return id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
