package com.iot;

import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

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
                    execute();
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

    public void execute() {
        double temp = SensorUtils.convertToCelsius(sensor.read());
        System.out.println(temp + " Celsius");

        Gpio red = new Gpio(11);
        Gpio green = new Gpio(10);
        Gpio blue = new Gpio(9);

        red.write(0);
        green.write(0);
        blue.write(0);

        if (temp > 24) {
            red.write(200);
            green.write(0);
            blue.write(0);
        } else {
            red.write(0);
            green.write(200);
            blue.write(0);
        }
    }
}