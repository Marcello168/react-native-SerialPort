
# Android和硬件通信使用的串口库 react-native-serial-port

## 原生库 使用的是 志勇大神写的串口工具库 [项目地址]:[https://github.com/licheedev/Android-SerialPort-API]

## 开始使用
1. 在`package.json`里面的 `dependencies` 添加 `"react-native-serial-port": "github:Marcello168/react-native-SerialPort"`
2. 添加后 执行 `$ npm install ` 就会下载
3. 执行 如下命令 自动链接到原生库  `$ react-native link react-native-serial-port`
4. 在Android 目录下 的`build.gradle`文件里面的 `repositories`
   增加 `maven { url 'https://jitpack.io' }` 如下

   ```
    allprojects {
    repositories {
        mavenLocal()
        google()
        jcenter()
        maven {
            // All of React Native (JS, Obj-C sources, Android binaries) is installed from npm
            url "$rootDir/../node_modules/react-native/android"
            }
      maven { url 'https://jitpack.io' }
      }
	}
   ```

   5. 在AndraidMainifest.xml 文件中 将  ` android:allowBackup="false"` 改成`android:allowBackup="true"`



### 手动安装

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNSerialPortPackage;` to the imports at the top of the file
  - Add `new RNSerialPortPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-serial-port'
  	project(':react-native-serial-port').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-serial-port/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-serial-port')
  	```


## 使用方法

1. 获取设备的路径列表
```javascript
import RNSerialPort from 'react-native-serial-port';

// TODO: 获取设备的路径列表
 RNSerialPort.getAllDevicesPath((result) => {
                console.log(result); 
            });
```

2. 打开串口
```javascript
import RNSerialPort from 'react-native-serial-port';
// TODO: 打开串口
RNSerialPort.openSerialPort('/dev/ttySO', 9600);
```
3. 发送数据
```javascript
import RNSerialPort from 'react-native-serial-port';
let byteData = [0x00,0x01,0x02,0x03,0x05]
// TODO: 发送数据
RNSerialPort.sendByteData(byteData);
```

3. 监听串口的状态 和 监听串口回传数据
```javascript
import RNSerialPort from 'react-native-serial-port';
     DeviceEventEmitter.addListener('onSerialPortRecevieData', this.onSerialPortRecevieData, this)
    //监听接收串口开关的状态
	DeviceEventEmitter.addListener('onSerialPortOpenStatus'，this.onSerialPortOpenStatus, this)


	    //监听串口的状态
    onSerialPortOpenStatus(status) {
        alert(status)
        console.log("onSerialPortOpenStatus");
        //处理逻辑
	}
	
    // 监听串口回传数据
    onSerialPortRecevieData(receiveData) {
        console.log(receiveData);
        console.log("onSerialPortRecevieData");
        // 处理接收的数据
    }

```
### 具体使用可以参考 `example` 里面的 `RNSerialPortManager.js`文件