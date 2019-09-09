
# react-native-serial-port

## Getting started

`$ npm install react-native-serial-port --save`

### Mostly automatic installation

`$ react-native link react-native-serial-port`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-serial-port` and add `RNSerialPort.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNSerialPort.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

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

#### Windows
[Read it! :D](https://github.com/ReactWindows/react-native)

1. In Visual Studio add the `RNSerialPort.sln` in `node_modules/react-native-serial-port/windows/RNSerialPort.sln` folder to their solution, reference from their app.
2. Open up your `MainPage.cs` app
  - Add `using Serial.Port.RNSerialPort;` to the usings at the top of the file
  - Add `new RNSerialPortPackage()` to the `List<IReactPackage>` returned by the `Packages` method


## Usage
```javascript
import RNSerialPort from 'react-native-serial-port';

// TODO: What to do with the module?
RNSerialPort;
```
  