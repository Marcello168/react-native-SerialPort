package com.reactlibrary.serialportlib;
import android.os.HandlerThread;
import android.serialport.SerialPort;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;


public class SerialPortManager {
    private SerialReadThread mReadThread;
    private OutputStream mOutputStream;
    private HandlerThread mWriteThread;
    private SerialPort mSerialPort;
    private String devicePath;
    private String baudrateString;
    private boolean isOpenSuccess;

    public SerialPortManager() {
    }

    public SerialPort open(Device device) {
        return this.open(device.getPath(), device.getBaudrate());
    }

    public SerialPort open(String devicePath, String baudrateString) {
        this.devicePath = devicePath;
        this.baudrateString = baudrateString;
        if (this.mSerialPort != null) {
            this.close();
        }

        try {
            File device = new File(devicePath);
            int baurate = Integer.parseInt(baudrateString);
            this.mSerialPort = new SerialPort(device, baurate, 0);
            this.mReadThread = new SerialReadThread(devicePath, baudrateString, this.mSerialPort.getInputStream());
            this.mReadThread.start();
            this.mOutputStream = this.mSerialPort.getOutputStream();
            this.mWriteThread = new HandlerThread("write-thread");
            this.mWriteThread.start();
            this.isOpenSuccess = true;
            return this.mSerialPort;
        } catch (Throwable var5) {
            this.isOpenSuccess = false;
            this.close();
            return null;
        }
    }

    public void setReceiveCallback(ReceiveCallback receiveCallback) {
        this.mReadThread.setReceiveCallback(receiveCallback);
    }

    public void removeReceiveCallback() {
        this.mReadThread.removeReceiveCallback();
    }

    public void close() {
        if (this.mReadThread != null) {
            this.mReadThread.close();
        }

        if (this.mOutputStream != null) {
            try {
                this.mOutputStream.close();
            } catch (IOException var2) {
                var2.printStackTrace();
            }
        }

        if (this.mWriteThread != null) {
            this.mWriteThread.quit();
        }

        if (this.mSerialPort != null) {
            this.mSerialPort.close();
            this.mSerialPort = null;
        }

    }

    public void sendData(byte[] datas) throws Exception {
        this.mOutputStream.write(datas);
    }

    public String getDevicePath() {
        return this.devicePath;
    }

    public String getBaudrateString() {
        return this.baudrateString;
    }

    public boolean isOpenSuccess() {
        return this.isOpenSuccess;
    }
}
