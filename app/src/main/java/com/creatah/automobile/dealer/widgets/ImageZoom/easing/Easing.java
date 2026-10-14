package com.creatah.automobile.dealer.widgets.ImageZoom.easing;

/**
 * Created by admin on 2016-04-11.
 */
public interface Easing {

    double easeOut(double time, double start, double end, double duration);

    double easeIn(double time, double start, double end, double duration);

    double easeInOut(double time, double start, double end, double duration);
}
