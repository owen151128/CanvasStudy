package com.hpcnt.canvasstudy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    SurfView vw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vw = new SurfView(this);
        setContentView(vw);
    }
}