package com.hpcnt.canvasstudy;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

/**
 * Created by 0wen151128 on 2017. 8. 4..
 */

class Ball {
    int x, y;
    int rad;
    int dx, dy;
    int color;
    int count;

    static Ball Create(int x, int y, int Rad) {
        Random Rnd = new Random();
        Ball NewBall = new Ball();
        NewBall.x = x;
        NewBall.y = y;
        NewBall.rad = Rad;
        do {
            NewBall.dx = Rnd.nextInt(11) - 5;
            NewBall.dy = Rnd.nextInt(11) - 5;
        } while (NewBall.dx == 0 || NewBall.dy == 0);
        NewBall.count = 0;
        NewBall.color = Color.rgb(Rnd.nextInt(256), Rnd.nextInt(256), Rnd.nextInt(256));
        return NewBall;
    }

    void Move(int Width, int Height) {
        x += dx * 8;
        y += dy * 8;
        if (x < rad || x > Width - rad) {
            dx *= -1;
            count++;
        }
        if (y < rad || y > Height - rad) {
            dy *= -1;
            count++;
        }
    }

    void Draw(Canvas canvas) {
        Paint pnt = new Paint();
        pnt.setAntiAlias(true);
        int r;
        int alpha;
        for (r = rad, alpha = 1; r > 4; r--, alpha += 5) {
            pnt.setColor(Color.argb(alpha, Color.red(color),
                    Color.green(color), Color.blue(color)));
            canvas.drawCircle(x, y, r, pnt);
        }
    }
}
