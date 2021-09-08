package me.ddozzi.ghostgrouper.util;

import java.util.Collection;

public class MathUtils {
    public static final MathUtils instance = new MathUtils();

    public double sum(Collection<?> c) {
        double s = 0;
        for(Object o : c) {
            s += Double.parseDouble("" + o);
        }
        return s;
    }

    public double average(Collection<?> c) {
        return sum(c) / (double) c.size();
    }

    public int getColorFromProgress(int progress)
    {
        int color1 = 0, color2 = 0, color = 0;
        float p = (float)progress;
        if(progress <= 10)
        {
            color1 = 0;
            color2 = 0xff0000;
            p = progress / 10.0f;
        }
        else if(progress <= 25)
        {
            color1 = 0xff0000;
            color2 = 0xffff00;
            p = (progress - 10) / 15.0f;
        }
        else if(progress <= 40)
        {
            color1 = 0xffff00;
            color2 = 0x00ff00;
            p = (progress - 25) / 15.0f;
        }
        else if(progress <= 55)
        {
            color1 = 0x00ff00;
            color2 = 0x00ffff;
            p = (progress - 40) / 15.0f;
        }
        else if(progress <= 70)
        {
            color1 = 0x00ffff;
            color2 = 0x0000ff;
            p = (progress - 55) / 15.0f;
        }
        else if(progress <= 90)
        {
            color1 = 0x0000ff;
            color2 = 0x00ff00;
            p = (progress - 70) / 20.0f;
        }
        else if(progress <= 98)
        {
            color1 = 0x00ff00;
            color2 = 0xff00ff;
            p = (progress - 90) / 8.0f;
        }
        else
        {
            color1 = 0xffffff;
            color2 = 0xffffff;
            p = 1.0f;
        }

        int r1 = (color1 >> 16) & 0xff;
        int r2 = (color2 >> 16) & 0xff;
        int g1 = (color1 >> 8) & 0xff;
        int g2 = (color2 >> 8) & 0xff;
        int b1 = (color1) & 0xff;
        int b2 = (color2) & 0xff;

        int r3 = (int) ((r2 * p) + (r1 * (1.0f-p)));
        int g3 = (int) (g2 * p + g1 * (1.0f-p));
        int b3 = (int) (b2 * p + b1 * (1.0f-p));

        color = r3 << 16 | g3 << 8 | b3;

        return color;
    }

}
