package com.wheelview.library.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.wheelview.library.R;
import com.wheelview.library.wheelview.CommonUntil;
import com.wheelview.library.wheelview.OnWheelChangedListener;
import com.wheelview.library.wheelview.WheelView;
import com.wheelview.library.wheelview.adapter.ArrayWheelAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.HashMap;

/**
 * @desc：
 * @author: yongzhi
 * @time: 2017/2/20 0020
 * @reviser_and_time:
 */

public class MyWheelDialog extends Dialog implements OnWheelChangedListener, View.OnClickListener {
    private final Context mContext;
    private final OnWheelClickLitener mWheelClickLitener;
    private String json = "";
    private View view;
    private final WheelView wArea;
    private final WheelView wArea_child;
    private final WheelView wArea_child2;
    private final TextView area_tv_ok;
    private final TextView area_tv_cancel;
    private String[] area;
    private String[] areacode;
    private HashMap<Integer, String[]> area_city = new HashMap<>();
    private HashMap<Integer, String[]> area_citycode = new HashMap<>();
    private HashMap<Integer, HashMap<Integer, String[]>> area_country = new HashMap<>();
    private HashMap<Integer, HashMap<Integer, String[]>> area_countrycode = new HashMap<>();
    public static final String TAG = "qqq";

    public MyWheelDialog(Context context, OnWheelClickLitener wheelClickLitener) {
        super(context, R.style.transparentFrameWindowStyle);
        mContext = context;
        mWheelClickLitener = wheelClickLitener;
        view = View.inflate(context, R.layout.dialog_select_area, null);
        setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                CommonUntil.getScreenHeight(context) / 3));
        Window window = getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.Dialog_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        wl.x = 0;
        wl.y = wm.getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 设置显示位置
        onWindowAttributesChanged(wl);
        // 设置点击外围解散
        setCanceledOnTouchOutside(true);
        wArea = (WheelView) view.findViewById(R.id.id_area);
        wArea_child = (WheelView) view.findViewById(R.id.id_area_child);
        wArea_child2 = (WheelView) view.findViewById(R.id.id_area_child2);
        area_tv_ok = (TextView) view.findViewById(R.id.tv_ok);
        area_tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        /**
         * 加载数据
         */
        json = readFromAsset(context);
        province(json);
        wArea.addChangingListener(this);
        wArea_child.addChangingListener(this);
        wArea.setVisibleItems(5);
        wArea.setViewAdapter(new ArrayWheelAdapter<String>(
                mContext, area));
        wArea_child.setViewAdapter(new ArrayWheelAdapter<String>(mContext, area_city.get(0)));
        wArea_child2.setViewAdapter(new ArrayWheelAdapter<String>(mContext, area_country.get(0).get(0)));
        area_tv_ok.setOnClickListener(this);
        area_tv_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_ok) {
            String provinceName = area[wArea.getCurrentItem()];
            String provinceID = areacode[wArea.getCurrentItem()];
            String cityName = area_city.get(wArea.getCurrentItem())[wArea_child.getCurrentItem()];
            String cityID = area_citycode.get(wArea.getCurrentItem())[wArea_child.getCurrentItem()];
            String countryName = "";
            String countryID = "";

            if (area_country.get(wArea.getCurrentItem()).get(wArea_child.getCurrentItem()).length > 0) {
                countryName = area_country.get(wArea.getCurrentItem()).get(wArea_child.getCurrentItem())[wArea_child2.getCurrentItem()];
                countryID = area_countrycode.get(wArea.getCurrentItem()).get(wArea_child.getCurrentItem())[wArea_child2.getCurrentItem()];
            } else {
                countryName = "";
                countryID = "";
            }
            mWheelClickLitener.onOKClick(provinceName, provinceID, cityName, cityID, countryName, countryID);
            dismiss();
        } else if (v.getId() == R.id.tv_cancel) {
            mWheelClickLitener.onCancelClick();
            cancel();
        }
//        switch (v.getId()) {
//            case R.id.tv_ok:
//
//                break;
//            case R.id.tv_cancel:
//
//                break;
//        }
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == wArea) {
            wArea_child.setViewAdapter(new ArrayWheelAdapter<String>(mContext, area_city.get(wArea.getCurrentItem())));
            wArea_child.setCurrentItem(0);
            wArea_child2.setViewAdapter(new ArrayWheelAdapter<String>(mContext, area_country.get(wArea.getCurrentItem()).get(wArea_child.getCurrentItem())));
            wArea_child2.setCurrentItem(0);
        }
        if (wheel == wArea_child) {
            wArea_child2.setViewAdapter(new ArrayWheelAdapter<String>(mContext, area_country.get(wArea.getCurrentItem()).get(wArea_child.getCurrentItem())));
            wArea_child2.setCurrentItem(0);
        }
    }

    private String readFromAsset(Context context) {
        String res = "";
        try {
            InputStream in = context.getClass().getClassLoader().getResourceAsStream("assets/city.txt");
            /**
             * 获取文件的字节数
             */
            int length = in.available();
            /**
             * 创建byte数组
             */
            byte[] buffer = new byte[length];
            /**
             * 将文件中的数据读到byte数组中
             */
            in.read(buffer);
            res = new String(buffer, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    private void province(String res) {
        if (res.length() != 0) {
            try {
                JSONObject jsonObject = new JSONObject(res);
                if (jsonObject.optString("code").equals("40000")) {
                    JSONObject list = jsonObject.optJSONObject("list");
                    JSONArray regions = list.optJSONArray("regions");
                    area = new String[regions.length()];
                    areacode = new String[regions.length()];
                    for (int i = 0; i < regions.length(); i++) {
                        JSONObject p = regions.optJSONObject(i);
                        String pname = p.optString("name");
                        String pcode = p.optString("regions_id");
                        area[i] = pname;//省
                        areacode[i] = pcode;//省id
                        JSONArray citylist = p.optJSONArray("child");
                        String[] city = new String[citylist.length()];
                        String[] citycode = new String[citylist.length()];
                        HashMap<Integer, String[]> district = new HashMap<>();//城区
                        HashMap<Integer, String[]> districtcode = new HashMap<>();//城区id
                        for (int j = 0; j < citylist.length(); j++) {
                            JSONObject c = citylist.optJSONObject(j);
                            String cname = c.optString("name");
                            String ccode = c.optString("regions_id");
                            city[j] = cname;
                            citycode[j] = ccode;
                            JSONArray countrylist = c.optJSONArray("child");
                            String[] country = new String[countrylist.length()];
                            String[] countrycode = new String[countrylist.length()];
                            for (int k = 0; k < countrylist.length(); k++) {
                                JSONObject countryObj = countrylist.optJSONObject(k);
                                String countryname = countryObj.optString("name");
                                String countrycode1 = countryObj.optString("regions_id");
                                country[k] = countryname;
                                countrycode[k] = countrycode1;
                            }
                            district.put(j, country);
                            districtcode.put(j, countrycode);
                        }
                        area_country.put(i, district);//城区
                        area_countrycode.put(i, districtcode);//城区id
                        area_city.put(i, city);//city
                        area_citycode.put(i, citycode);//city id
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    public interface OnWheelClickLitener {
        void onOKClick(String provinceName, String provinceID, String cityName, String cityID, String countryName, String countryID);

        void onCancelClick();
    }
}
