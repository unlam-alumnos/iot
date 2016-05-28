package com.iot.dto;

import java.io.Serializable;

/**
 * @author Cristian Miranda
 * @since 5/24/16 - 18:04
 */
public class TemperatureLimits implements Serializable {

    private double min;
    private double max;

    public TemperatureLimits() {
    }

    public TemperatureLimits(double min, double max) {
        this.min = min;
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }
}
