
package com.reactlibrary;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import  com.reactlibrary*;

public class RNSerialPortModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;
  private static final String onSerialPortOpenStatus  = "onSerialPortOpenStatus";//判断串口是否打开成功通知
  private static final String onSerialPortRecevieData  = "onSerialPortRecevieData";//判断串口是否打开成功通知

  private DLCSerialPortUtil portutil;
  SerialPortManager PortManager;

  public RNSerialPortModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
    portutil = DLCSerialPortUtil.getInstance();

  }

  @Override
  public String getName() {
    return "RNSerialPort";
  }
}