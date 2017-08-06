package com.hpcnt.canvasstudy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * Created by 0wen151128 on 2017. 8. 4..
 */

class SurfView extends SurfaceView implements SurfaceHolder.Callback {
    Bitmap mBack;
    ArrayList<Ball> arBall = new ArrayList<Ball>();
    final static int DELAY = 50;
    final static int RAD = 24;
    SurfaceHolder mHolder;
    DrawThread mThread;

    public SurfView(Context context) {
        super(context);
        mBack = BitmapFactory.decodeResource(context.getResources(), R.drawable.black);
        mHolder = getHolder();
        mHolder.addCallback(this);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        mThread = new DrawThread(mHolder);
        mThread.start();
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        mThread.bExit = true;
        for (; ; ) {
            try {
                mThread.join();
                break;
            } catch (Exception e) {
                ;
            }
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (mThread != null) {
            mThread.SizeChange(width, height);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            synchronized (mHolder) {
                Ball NewBall = Ball.Create((int) event.getX(), (int) event.getY(), RAD);
                arBall.add(NewBall);
            }
            return true;
        }
        return false;
    }

    class DrawThread extends Thread {
        boolean bExit;
        int mWidth, mHeight;
        SurfaceHolder mHolder;

        DrawThread(SurfaceHolder Holder) {
            mHolder = Holder;
            bExit = false;
        }

        public void SizeChange(int Width, int Height) {
            mWidth = Width;
            mHeight = Height;
        }

        public void run() {
            Canvas canvas;
            Ball B;
            while (bExit == false) {
                for (int idx = 0; idx < arBall.size(); idx++) {
                    B = arBall.get(idx);
                    B.Move(mWidth, mHeight);
                    if (B.count > 4) {
                        arBall.remove(idx);
                        idx--;
                    }
                }
                synchronized (mHolder) {
                    canvas = mHolder.lockCanvas();
                    if (canvas == null) break;
                    canvas.drawColor(Color.BLACK);
                    canvas.drawBitmap(mBack, 0, 0, null);
                    for (int idx = 0; idx < arBall.size(); idx++) {
                        arBall.get(idx).Draw(canvas);
                        if (bExit) break;
                    }
                    mHolder.unlockCanvasAndPost(canvas);
                }
                try {
                    Thread.sleep(SurfView.DELAY);
                } catch (Exception e) {
                    ;
                }
            }
        }
    }
}

