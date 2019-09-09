package com.reactlibrary.serialportlib;

import android.serialport.SerialPortFinder;

public class DLCSerialPortUtil {
    private static DLCSerialPortUtil instance;
    private String[] baudrates = new String[]{"110", "300", "600", "1200", "2400", "4800", "9600", "14400", "19200", "38400", "56000", "57600", "115200", "128000", "256000"};

    public DLCSerialPortUtil() {
    }

    public static DLCSerialPortUtil getInstance() {
        if (instance == null) {
            instance = new DLCSerialPortUtil();
        }
        return instance;
    }

    public String[] getAllDevicesPath() {
        SerialPortFinder serialPortFinder = new SerialPortFinder();
        return serialPortFinder.getAllDevicesPath();
    }

    public String[] getBaudrates() {
        return this.baudrates;
    }

    public SerialPortManager open(String devicePath, String baudrateString) {
        SerialPortManager portManager = new SerialPortManager();
        portManager.open(devicePath, baudrateString);
        return portManager;
    }
}
