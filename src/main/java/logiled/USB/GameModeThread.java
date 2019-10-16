package logiled.USB;

import java.util.List;

public class GameModeThread extends LoThread implements Runnable{

    // Game-key settings
    //private static final byte[] game_key_rule
    private static final byte[] game_key_set_default = {
            0x11, (byte) 0xff, 0x03, 0x3c, 0x00, 0x00, 0x00, 0x00,     0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00,        0x00, 0x00, 0x00
    };

    private byte[] command;

    /**
     * Used to set keys & leds
     * @param keysToDisable : list of keys that has to be disables in 'Game Mode', always not empty and not null
     * */
    public GameModeThread(List<Byte> keysToDisable){

    }

    @Override
    public void run() {
        if (command == null)
            return;

        UsbConnect usbConnect = new UsbConnect();

        if (!usbConnect.isConnected())
            return;

        handler = usbConnect.getHandlerKbrd();

        write(command);

        usbConnect.close();
    }

}
