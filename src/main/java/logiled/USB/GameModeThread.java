package logiled.USB;

import logiled.MessagesConsumer;
import logiled.RainbowHexDump;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameModeThread extends LoThread{

    private static final byte[] game_key_prepare = {
            0x11, (byte) 0xff, 0x03, 0x3c, 0x00, 0x00, 0x00, 0x00,     0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00,        0x00, 0x00, 0x00
    };
    private static final byte[] game_key_list = {
            0x11, (byte) 0xff, 0x03, 0x1c, 0x00, 0x00, 0x00, 0x00,     0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00,        0x00, 0x00, 0x00
    };

    private List<byte[]> gameModeCommands;

    /**
     * Used to set keys & leds
     * @param keysToDisable : list of keys that has to be disables in 'Game Mode', always not empty and not null
     * */
    public GameModeThread(List<Byte> keysToDisable){
        gameModeCommands = new ArrayList<>();
        // Prepare to send commands
        gameModeCommands.add(game_key_prepare);
        // Set keys to disable
        byte[] command = Arrays.copyOfRange(game_key_list, 0, game_key_list.length);

        int pointer = 4;

        for (byte keyCode: keysToDisable) {
            if (pointer == 19){
                gameModeCommands.add(command);
                command = Arrays.copyOfRange(game_key_list, 0, game_key_list.length);
                pointer = 4;
            }
            command[pointer++] = keyCode;
        }
        // Add command that is not fulfilled to 100%    OR      In case nothing in the list
        if (pointer <= 19)
            gameModeCommands.add(command);
    }

    @Override
    protected Void call() throws Exception {
        UsbConnect usbConnect = new UsbConnect();

        if (!usbConnect.isConnected())
            return null;

        handler = usbConnect.getHandlerKbrd();

        boolean notFailed = true;

        for (byte[] cmd : gameModeCommands)
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
