package com.example.android.animation_assignment4;

import android.content.ClipData;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by CuiCui on 4/24/2016.
 */
public class ArrowTouchListner implements View.OnTouchListener{
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction()== MotionEvent.ACTION_DOWN){
            ClipData data=ClipData.newPlainText("","");
            View.DragShadowBuilder shadowBuilder=new View.DragShadowBuilder(v);
            v.startDrag(data, shadowBuilder,v,0);
            return true;
        }
        else return false;
    }
}
