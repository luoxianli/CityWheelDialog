package com.wheelview.library.wheelview.bean;

import android.content.Context;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/12/23 0023.
 */

public class AddressSaveAllEntity {

    private static AddressSaveAllEntity instance = null;

    private AddressSaveAllEntity(Context context) {
//        context=getApplication   传入为getApplication
    }

    public static AddressSaveAllEntity newInstance(Context context) {
        if (instance == null) {
            instance = new AddressSaveAllEntity(context);
        }
        return instance;
    }

    private boolean isSave = false;   //判断是否存有值
    private String[] area;
    private String[] areacode;
    private HashMap<Integer, String[]> area_city = new HashMap<>();
    private HashMap<Integer, String[]> area_citycode = new HashMap<>();
    private HashMap<Integer, HashMap<Integer, String[]>> area_country = new HashMap<>();
    private HashMap<Integer, HashMap<Integer, String[]>> area_countrycode = new HashMap<>();

    public boolean isSave() {
        return isSave;
    }

    public void setSave(boolean save) {
        isSave = save;
    }

    public String[] getArea() {
        return area;
    }

    public void setArea(String[] area) {
        this.area = area;
    }

    public String[] getAreacode() {
        return areacode;
    }

    public void setAreacode(String[] areacode) {
        this.areacode = areacode;
    }

    public HashMap<Integer, String[]> getArea_city() {
        return area_city;
    }

    public void setArea_city(HashMap<Integer, String[]> area_city) {
        this.area_city = area_city;
    }

    public HashMap<Integer, String[]> getArea_citycode() {
        return area_citycode;
    }

    public void setArea_citycode(HashMap<Integer, String[]> area_citycode) {
        this.area_citycode = area_citycode;
    }

    public HashMap<Integer, HashMap<Integer, String[]>> getArea_country() {
        return area_country;
    }

    public void setArea_country(HashMap<Integer, HashMap<Integer, String[]>> area_country) {
        this.area_country = area_country;
    }

    public HashMap<Integer, HashMap<Integer, String[]>> getArea_countrycode() {
        return area_countrycode;
    }

    public void setArea_countrycode(HashMap<Integer, HashMap<Integer, String[]>> area_countrycode) {
        this.area_countrycode = area_countrycode;
    }
}
