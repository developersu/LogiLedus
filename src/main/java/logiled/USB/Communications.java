package logiled.USB;

import logiled.MessagesConsumer;
import org.usb4java.DeviceHandle;
import org.usb4java.LibUsb;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Communications implements Runnable{

    private static final byte[] commit = {
            0x11, (byte) 0xff, 0x0c, 0x5a, 0x00, 0x00, 0x00, 0x00,    0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00,        0x00, 0x00, 0x00
    };

    private static final byte[] indicators = {  // we have 2 leds on G513
            0x12, (byte) 0xff, 0x0c, 0x3a, 0x00, 0x40, 0x00, 0x05,     0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00,        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,     0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00,        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,     0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00,        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,     0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
    };
    private static final byte[] keys = {        // Can store 14 rules
            0x12, (byte) 0xff, 0x0c, 0x3a, 0x00, 0x01, 0x00, 0x0e,     0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00,        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,     0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00,        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,     0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00,        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,     0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
    };

    private DeviceHandle handler;

    // What would be sent to keyboard
    private List<byte[]> keyLedCommands;

    public Communications(HashMap<String, List<byte[][]>> keyLedSet){
        keyLedCommands = new ArrayList<>();

        int appendTo = 8;

        byte[] command = Arrays.copyOfRange(indicators, 0, indicators.length);

        for (byte[][] singleRuleSet : keyLedSet.get("Led")){
            for (byte[] keyLedSetting: singleRuleSet) {
                System.arraycopy(keyLedSetting, 0, command, appendTo, 4);
                appendTo += 4;
            }
        }

        if (appendTo > 8) {
            keyLedCommands.add(Arrays.copyOfRange(command, 0, command.length));
            appendTo = 8;
        }

        command = Arrays.copyOfRange(keys, 0, keys.length);

        for (byte[][] singleRuleSet : keyLedSet.get("Key")) {
            for (byte[] keyLedSetting : singleRuleSet){
                if (appendTo == 64){
                    keyLedCommands.add(Arrays.copyOfRange(command, 0, command.length));
                    command = Arrays.copyOfRange(keys, 0, keys.length);
                    appendTo = 8;
                }
                System.arraycopy(keyLedSetting, 0, command, appendTo, 4);
                appendTo += 4;
            }
        }

        if (appendTo > 8)
            keyLedCommands.add(Arrays.copyOfRange(command, 0, command.length));
        // If any command has been added to chain, add commit command to the end
        if (keyLedCommands.size() > 0)
            keyLedCommands.add(commit);
    }

    @Override
    public void run() {
        // If no commands in the query, then nothing to do
        if (keyLedCommands.size() == 0)
            return;

        UsbConnect usbConnect = new UsbConnect();

        if (!usbConnect.isConnected())
            return;

        handler = usbConnect.getHandlerKbrd();

        for (byte[] cmd : keyLedCommands)
            if (write(cmd))
                break;

        usbConnect.close();
    }
    /**
     * Write to keyboard
     * @param message : what to sent
     * @return true in case of failure
     *         false for success
     * */
    private boolean write(byte[] message){
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
