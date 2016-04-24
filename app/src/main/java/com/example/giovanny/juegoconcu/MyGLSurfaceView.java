package com.example.giovanny.juegoconcu;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by giovanny on 17/04/16.
 */
public class MyGLSurfaceView extends GLSurfaceView {

    MyGLRenderer mRenderer;
    private float Xo;
    private float Yo;

    int height ;
    int width ;

    public MyGLSurfaceView(Context context,int h, int w) {
        super(context);
        height = h;
        width = w;
        mRenderer = new MyGLRenderer(context);
        Xo=3*width/40;
        Yo=7*height/9;
        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(mRenderer);
    }

    // Handler for touch event
    @Override
    public boolean onTouchEvent(final MotionEvent evt) {
        float currentX = evt.getX();
        float currentY = evt.getY();
        switch (evt.getAction()) {
            case MotionEvent.ACTION_UP:
                mRenderer.Mx=0f;
                mRenderer.My=0f;
                break;

            case MotionEvent.ACTION_MOVE:
                if(jostinDentro(currentX,currentY)){
                    mRenderer.Mx=10*(currentX-Xo)/width;
                    mRenderer.My=-5*(currentY-Yo)/height;
                }
                else if(logoDentro(currentX,currentY)){
                    mRenderer.setActivado(!mRenderer.getActivado());
                }
                break;
        }

        return true;
    }

    private boolean jostinDentro(float x,float y){
        if(x< 3*width/20) {
            if (5*height/9 < y)
                return true;
        }
        return false;
    }
    private boolean logoDentro(float x,float y){
        if(x> 17*width/20) {
            if (5*height/9 < y)
                return true;
        }
        return false;
    }
}
