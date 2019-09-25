
package com.reactlibrary;

import android.util.Log;
import android.widget.Toast;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.reactlibrary.serialportlib.DLCSerialPortUtil;
import com.reactlibrary.serialportlib.ReceiveCallback;
import com.reactlibrary.serialportlib.SerialPortManager;

import java.util.ArrayList;

public class RNSerialPortModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;
  private static final String onSerialPortOpenStatus  = "onSerialPortOpenStatus";//判断串口是否打开成功通知
  private static final String onSerialPortRecevieData  = "onSerialPortRecevieData";//判断串口是否打开成功通知

  private DLCSerialPortUtil portutil;
  private ArrayList<SerialPortManager> mPortManagers = new ArrayList<>();


  public RNSerialPortModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
    portutil = DLCSerialPortUtil.getInstance();

  }

  @Override
  public String getName() {
    return "RNSerialPort";
  }



  @ReactMethod
  public void show(String message, int duration) {
    Toast.makeText(getReactApplicationContext(), message, duration).show();
  }

  /*自定义原生和RN通讯方法*/
  /**
   * 获取串口列表 方式
   * rn调用Native,并获取返回值
   *
   * @param callback
   */
  @ReactMethod
  public void getAllDevicesPath(Callback callback) {
    String[] paths = DLCSerialPortUtil.getInstance().getAllDevicesPath();
    String[] arr1 = {"aasfdas","fsdfs","fsfdssss","sdfsfsfs","sdfsfdsfdsf"};
    // .回调RN,即将处理串口列表返回给RN
//    Toast.makeText(this.reactContext ,result,Toast.LENGTH_SHORT).show();
    WritableArray pathArray = Arguments.createArray();
    for (int i = 0; i <paths.length ; i++) {
      String path = paths[i];
      pathArray.pushString(path);

    }
    callback.invoke(pathArray);
  }

  /**
   * 打开串口
   * rn调用Native,并获取返回值
   * @param portStr 串口路径
   * @param Baudrates 波特率
   */
  @ReactMethod
  public  void openSerialPort(String portStr,String Baudrates ){
    for (SerialPortManager portManager : mPortManagers) {
      String devicePath = portManager.getDevicePath();
      if (devicePath != null && portStr.equals(devicePath)) {
        return;
      }
    }

    SerialPortManager manager = DLCSerialPortUtil.getInstance().open(portStr,Baudrates);
    if (manager == null){
      this.reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(onSerialPortOpenStatus,false);
      return;
    }
    boolean isSucess = manager.isOpenSuccess();
    if ( isSucess){
      //打开串口成功
      this.reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(onSerialPortOpenStatus,true);
      WritableArray testArray = Arguments.createArray();
      testArray.pushInt(23);
      testArray.pushInt(15);
      testArray.pushInt(48);
      reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(onSerialPortRecevieData,testArray);
      manager.setReceiveCallback(mPortManagerCallback);
      mPortManagers.add(manager);
    }else {
      //打开串口失败
      this.reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(onSerialPortOpenStatus,false);

    }

  }

  private ReceiveCallback mPortManagerCallback = new ReceiveCallback() {
    @Override
    public void onReceive(String devicePath, String baudrateString, byte[] received, int size) {
      WritableMap params = Arguments.createMap();
      WritableArray receiveArray = Arguments.createArray();
      for (int i = 0; i < size; i++) {
        int cmdSingle = received[i];
        receiveArray.pushInt(cmdSingle);
      }
      params.putString("linuxDevPath", devicePath);
      params.putArray("valueArray", receiveArray);
      reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(onSerialPortRecevieData, params);
    }
  };

  private SerialPortManager pickPortManager(String portStr) {
    for (SerialPortManager portManager : mPortManagers) {
      String devicePath = portManager.getDevicePath();
      if (devicePath != null && portStr.equals(devicePath)) {
        return portManager;
      }
    }
    return null;
  }

  /**
   * 发送byte 字节 方式
   * rn调用Native,
   * @param msg
   *
   */
  @ReactMethod
  public void sendByteData(String portStr, ReadableArray msg) throws Exception {
    Log.d("BBC", "BBC: " +     msg.toString());
    int length = msg.size();
    byte [] cmd = new byte [length];
    for (int i = 0; i < length; i++) {
      int number =  msg.getInt(i);
      cmd[i] =(byte)number ;
    }
    SerialPortManager pickedPortManager = pickPortManager(portStr);
    if (pickedPortManager != null) {
      pickedPortManager.removeReceiveCallback();
    }
  }

  /**
   * 根据业务逻辑，增加移除监听
   * rn调用Native,
   *
   *
   */
  @ReactMethod
  public void removeReceiveCallback(String portStr) {
    SerialPortManager pickedPortManager = pickPortManager(portStr);
    if (pickedPortManager != null) {
      pickedPortManager.removeReceiveCallback();
    }
  }


  /**
   * 关闭串口
   * rn调用Native,
   *
   *
   */
  @ReactMethod
  public void close(String portStr) {
    SerialPortManager pickedPortManager = pickPortManager(portStr);
    if (pickedPortManager != null) {
      pickedPortManager.removeReceiveCallback();
      pickedPortManager.close();
      mPortManagers.remove(pickedPortManager);
    }
  }

  @ReactMethod
  public void doDestroy() {
    for (SerialPortManager portManager : mPortManagers) {
      String devicePath = portManager.getDevicePath();
      if (devicePath != null) {
        portManager.removeReceiveCallback();
        portManager.close();
        mPortManagers.remove(portManager);
      }
    }
  }

  @Override
  public void onCatalystInstanceDestroy() {
    super.onCatalystInstanceDestroy();
    this.doDestroy();
  }
}
