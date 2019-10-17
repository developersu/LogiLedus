package logiled.USB;

import javafx.concurrent.Task;
import logiled.MessagesConsumer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class KeyLedThread extends LoThread {
    // Keys and indicators individual settings
    private static final byte[] commit = {
            0x11, (byte) 0xff, 0x0c, 0x5a, 0x00, 0x00, 0x00, 0x00,    0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00,        0x00, 0x00, 0x00
    };

    private static final byte[] indicators = {  // we have 2 leds on G513
            //                                                         KEY   RED   GRN   BLU   -//-
            0x12, (byte) 0xff, 0x0c, 0x3a, 0x00, 0x40, 0x00, 0x05,     0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00,        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,     0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00,        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,     0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00,        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,     0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
    };
    private static final byte[] keys = {        // Can store 14 rules
            //                                                         LED   RED   GRN   BLU   -//-
            0x12, (byte) 0xff, 0x0c, 0x3a, 0x00, 0x01, 0x00, 0x0e,     0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00,        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,     0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00,        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,     0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00,        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,     0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
    };


    // What would be sent to keyboard
    private List<byte[]> keyLedCommands;

    /**
     * Used to set keys & leds
     * @param keyLedSet : set of rules, always not empty and not null
     * */
    public KeyLedThread(HashMap<String, List<byte[][]>> keyLedSet){
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
        // Add commit command to the end
        keyLedCommands.add(commit);
    }

    @Override
    protected Void call() throws Exception {
        // If no commands in the query, then nothing to do
        if (keyLedCommands.size() == 0)
            return null;

        UsbConnect usbConnect = new UsbConnect();

        if (!usbConnect.isConnected())
            return null;

        handler = usbConnect.getHandlerKbrd();

        boolean notFailed = true;

        for (byte[] cmd : keyLedCommands)
            if (write(cmd)){
                notFailed = false;
                break;
            }

        if (notFailed)
            MessagesConsumer.getInstance().inform("Complete!");

        usbConnect.close();
        return null;
    }
}