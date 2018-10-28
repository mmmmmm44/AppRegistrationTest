package com.appregistrationtest;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;

public class RegStd5Confirm extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_std5_confirm);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        //Get screen's width and height
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        //Set the window's width and height to 0.8*screen width and 0.7*screen height
        getWindow().setLayout((int)(width * 0.9), (int)(height * 0.9));

        //Set the attributes of the window (the position)
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);


    }
}
