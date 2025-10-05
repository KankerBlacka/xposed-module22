package com.example.xposedmodule;

import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class FloatingViewTouchListener implements View.OnTouchListener {
    
    private WindowManager windowManager;
    private View floatingView;
    private WindowManager.LayoutParams params;
    private int initialX, initialY;
    private float initialTouchX, initialTouchY;
    
    public FloatingViewTouchListener(WindowManager windowManager, View floatingView) {
        this.windowManager = windowManager;
        this.floatingView = floatingView;
        this.params = (WindowManager.LayoutParams) floatingView.getLayoutParams();
    }
    
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                initialX = params.x;
                initialY = params.y;
                initialTouchX = event.getRawX();
                initialTouchY = event.getRawY();
                return true;
                
            case MotionEvent.ACTION_MOVE:
                params.x = initialX + (int)(event.getRawX() - initialTouchX);
                params.y = initialY + (int)(event.getRawY() - initialTouchY);
                windowManager.updateViewLayout(floatingView, params);
                return true;
        }
        return false;
    }
}
