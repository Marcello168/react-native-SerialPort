/**
 * 获取串口列表
 * @param callback 
 */
export function getAllDevicesPath(callback: (params: string[]) => void): void

/**
 * 打开串口
 * @param portStr 串口路径
 * @param Baudrates 波特率
 */
export function openSerialPort(portStr: string, Baudrates: string): void

/**
 * 发送byte 字节 方式
 * @param msg 
 */
export function sendByteData(msg: number[]): void

/**
 * 移除监听
 */
export function removeReceiveCallback(): void

/**
 * 关闭串口
 */
export function close(): void
