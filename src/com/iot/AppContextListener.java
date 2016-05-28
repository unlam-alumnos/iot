package com.iot;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.codehaus.jackson.map.ObjectMapper;

import com.iot.dto.AlarmStatus;
import com.iot.dto.TemperatureLimits;
import com.iot.service.AlarmService;
import com.iot.service.TemperatureService;
import com.iot.util.SensorUtils;

import mraa.Aio;
import mraa.Gpio;

public class AppContextListener implements ServletContextListener {
    private Timer timer;
    private Aio sensor;

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("AppContextListener initialized.");

        sensor = new Aio(5);

        this.timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    AlarmStatus alarmStatus = mapper.readValue(new File(AlarmService.FILE_NAME),
                            AlarmStatus.class);
                    if (alarmStatus != null && alarmStatus.isOn()) {
                        execute();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
        this.timer.schedule(timerTask, 500, 500);
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("AppContextListener destroyed.");

        this.timer.cancel();
    }

    public void execute() throws IOException {
        double temp = SensorUtils.convertToCelsius(sensor.read());

        ObjectMapper mapper = new ObjectMapper();
        TemperatureLimits limits = mapper.readValue(new File(TemperatureService.FILE_NAME),
                TemperatureLimits.class);

        Gpio red = new Gpio(11);
        Gpio green = new Gpio(10);
        Gpio blue = new Gpio(9);

        red.write(0);
        green.write(0);
        blue.write(0);

        if (temp < limits.getMin()) {
            red.write(0);
            green.write(0);
            blue.write(200);
            System.out.println(temp + " - LOW");
        } else if (temp >= limits.getMin() && temp <= limits.getMax()) {
            red.write(0);
            green.write(200);
            blue.write(0);
            System.out.println(temp + " - MID");
        } else {
            red.write(200);
            green.write(0);
            blue.write(0);
            System.out.println(temp + " - HIGH");
        }
    }
}