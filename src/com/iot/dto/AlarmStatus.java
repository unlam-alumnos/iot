package com.iot.dto;

import java.io.Serializable;

/**
 * @author Cristian Miranda
 * @since 5/24/16 - 18:04
 */
public class AlarmStatus implements Serializable {

    private boolean on;

    public AlarmStatus() {
    }

    public AlarmStatus(boolean on) {
        this.on = on;
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }
}
