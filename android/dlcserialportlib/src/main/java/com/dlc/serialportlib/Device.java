package com.dlc.serialportlib;


public class Device {
    private String path;
    private String baudrate;

    public Device() {
    }

    public Device(String path, String baudrate) {
        this.path = path;
        this.baudrate = baudrate;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getBaudrate() {
        return this.baudrate;
    }

    public void setBaudrate(String baudrate) {
        this.baudrate = baudrate;
    }

    public String toString() {
        return "Device{path='" + this.path + '\'' + ", baudrate='" + this.baudrate + '\'' + '}';
    }
}
