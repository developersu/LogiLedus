package logiled.USB;

import logiled.MessagesConsumer;
import org.usb4java.DeviceHandle;
import org.usb4java.LibUsb;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

abstract class LoThread {

    DeviceHandle handler;

    /**
     * Write to keyboard
     * @param message : what to sent
     * @return true in case of failure
     *         false for success
     * */
    boolean write(byte[] message){
        ByteBuffer writeBuffer = ByteBuffer.allocateDirect(message.length);   //writeBuffer.order() equals BIG_ENDIAN;
        writeBuffer.put(message);                                             // Don't do writeBuffer.rewind();
        int result;

        if (message.length > 20)
            result = LibUsb.controlTransfer(handler, (byte) 0x21, (byte) 0x09, (short) 0x212, (short) 1, writeBuffer, 2000);
        else
            result = LibUsb.controlTransfer(handler, (byte) 0x21, (byte) 0x09, (short) 0x211, (short) 1, writeBuffer, 2000);

        if (result < 0){
            MessagesConsumer.getInstance().inform("Data transfer failed: "+UsbErrorCodes.getErrCode(result));
            return true;
        }

        ByteBuffer readBuffer = ByteBuffer.allocateDirect(64);
        IntBuffer readBufTrans = IntBuffer.allocate(1);
        LibUsb.interruptTransfer(handler, (byte) 0x82, readBuffer, readBufTrans, 1000);
        /*
        readBuffer.rewind();
        byte[] arr = new byte[readBuffer.get()];
        readBuffer.get(arr);
        RainbowHexDump.hexDumpUTF8(arr);
        */
        return false;
    }
}
