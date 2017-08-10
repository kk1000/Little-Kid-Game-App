package com.example.android.animation_assignment4;

import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by CuiCui on 4/24/2016.
 */
public class BlankDragListener implements View.OnDragListener{


    @Override
    public boolean onDrag(View v, DragEvent event) {
        switch (event.getAction()){
            case DragEvent.ACTION_DRAG_STARTED:
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                break;
            case DragEvent.ACTION_DROP:

                ImageView view=(ImageView) event.getLocalState();//get dragged arrow
                ImageView dropTarget=(ImageView)v;
                ImageView dropped=view; //dragged arrow

                Object tag=dropTarget.getTag();
                if(tag!=null){
                    //target has arrow existed
                    int existingID=(Integer) tag;
                    Log.v("CCR: existingID: ",""+existingID);
                    view.setVisibility(View.VISIBLE);
                }
                else {
                    dropTarget.setBackground(dropped.getBackground());
                    dropTarget.setTag(dropped.getId());
                    Log.v("CCR dropTargetID:",""+dropTarget.getTag());
                }
                break;
            default:
                break;
        }

        return true;
    }
}
