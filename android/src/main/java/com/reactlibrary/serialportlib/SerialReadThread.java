package com.reactlibrary.serialportlib;


import android.os.SystemClock;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SerialReadThread extends Thread {
    private static final String TAG = "SerialReadThread";
    private BufferedInputStream mInputStream;
    private String devicePath;
    private String baudrateString;
    private ReceiveCallback receiveCallback;

    public SerialReadThread(String devicePath, String baudrateString, InputStream is) {
        this.devicePath = devicePath;
        this.baudrateString = baudrateString;
        this.mInputStream = new BufferedInputStream(is);
    }

    public void setReceiveCallback(ReceiveCallback receiveCallback) {
        this.receiveCallback = receiveCallback;
    }

    public void removeReceiveCallback() {
        this.receiveCallback = null;
    }

    public void run() {
        byte[] received = new byte[1024];

        while(!Thread.currentThread().isInterrupted()) {
            try {
                int available = this.mInputStream.available();
                if (available > 0) {
                    int size = this.mInputStream.read(received);
                    if (size > 0) {
                        this.onDataReceive(received, size);
                    }
                } else {
                    SystemClock.sleep(1L);
                }
            } catch (IOException var4) {
                ;
            }
        }

    }

    private void onDataReceive(byte[] received, int size) {
        if (this.receiveCallback != null) {
            this.receiveCallback.onReceive(this.devicePath, this.baudrateString, received, size);
        }

    }

    public void close() {
        try {
            this.mInputStream.close();
        } catch (IOException var5) {
            ;
        } finally {
            super.interrupt();
        }

    }
}
