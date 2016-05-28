package com.iot.util;

public class SensorUtils {
    public static double convertToCelsius(double raw) {
        double vo = raw * (3.3 / 4095);
        double temp = Math.log(10000 * (3.3 / vo - 1));
        temp = 1 / (0.001129148 + (0.000234125 + (0.0000000876741 * temp * temp)) * temp);
        temp = temp - 273.5;
        return temp * -1;
    }
}
