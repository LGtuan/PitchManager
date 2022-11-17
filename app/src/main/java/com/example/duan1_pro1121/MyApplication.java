package com.example.duan1_pro1121;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class MyApplication extends Application {

    public static final String ADMIN_CATEGORY = "Admin";

    public static final int ID_CATEGORY_PITCH_5 = 1;
    public static final int ID_CATEGORY_PITCH_7 = 2;
    public static final int ID_CATEGORY_PITCH_11 =3;

    public static final int DANG_STATUS = 1;
    public static final int DA_STATUS = 2;
    public static final int CHUA_STATUS = 0;

    public static final int HOATDONG_STATUS = 0;
    public static final int BAOTRI_STATUS = 1;

    public static String PHONE_REGEX = "0[35789]\\d{8}";
    public static String PASS_REGEX = "(\\W|\\w){6,10}";
    public static String ADDRESS_REGEX = "(\\w|\\s)+";
    public static String NAME_REGEX = "(\\w|\\s)+";

    public static int TYPE_ADMIN = 0;
    public static int TYPE_USER = 1;
    public static int CURRENT_TYPE = -1;

    public static String convertMoneyToString(int money){
        String s = "";
        while (true) {
            if (money / 100 >= 10) {
                s += ".000";
                money /= 1000;
            } else {
                s = money + s;
                break;
            }
        }
        return s;
    }

    public static Bitmap getBitMapFromByte(byte[] bytes){
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }

}
