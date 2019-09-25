/*
 * @Author: gongyonghui
 * @Date: 2019-09-09 09:28:51
 * @LastEditors: gongyonghui
 * @LastEditTime: 2019-09-09 13:39:29
 * @Description: file content
 */


import React, { Component } from 'react';
import { Platform, StyleSheet, Text, View, NativeModules } from 'react-native';
import RNSerialPortManager, { SerialPortManager } from './RNSerialPortManager';



export default class App extends Component {

  render() {
    console.log('NativeModules :', NativeModules);
    return (
      <View style={styles.container}>
        <Text style={styles.welcome} onPress={this.getSerialPortDevicePath}>获取串口列表</Text>
        <Text style={styles.instructions}>发送数据</Text>
      </View>
    );
  }

  //获取串口列表
  getSerialPortDevicePath = async () => {
    let devicePathList = await SerialPortManager.getDeviceLsit()
    console.log('devicePathList :', devicePathList);
  }

  sendSerialPortData() {
    SerialPortManager.writeByteData('/dev/ttyS7', [0x00, 0x00, 0x00, 0x01])
  }

}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 35,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    fontSize: 35,
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});
