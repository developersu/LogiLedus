package logiled.USB;

import logiled.Controllers.LoEffects;
import logiled.MessagesConsumer;

import java.util.HashMap;

public class EffectsThread extends LoThread {

    private byte[] command;

    /**
     * Used to set effects
     * @param effectData : set of rules, always not empty and not null
     * */
    public EffectsThread(HashMap<String, Byte> effectData){
        switch (LoEffects.values()[effectData.get("EFFECT")]){
            case DISABLE:
                command = effectDisable();
                break;
            case CONSTANT_COLOR:
                command = effectConstantColor(effectData);
                break;
            case BREATH:
                command = effectBreath(effectData);
                break;
            case CIRCLES_ON_PRESS:
                command = effectCirlesOnPress(effectData);
                break;
            case CYCLE:
                command = effectCycle(effectData);
                break;
            case WAVE_HORIZONTAL_FRW:
                command = effectWaveHorizontalFrw(effectData);
                break;
            case WAVE_VERTICAL_FRW:
                command = effectWaveVerticalFrw(effectData);
                break;
            case WAVE_CENTER_TO_EDGE:
                command = effectWaveCenterToEdge(effectData);
                break;
            case WAVE_HORIZONTAL_BKW:
                command = effectWaveHorizontalBkw(effectData);
                break;
            case WAVE_VERTICAL_BKW:
                command = effectWaveVerticalBkw(effectData);
                break;
            case WAVE_EDGE_TO_CENTER:
                command = effectWaveEdgeToCenter(effectData);
        }
    }
    @Override
    protected Void call() throws Exception {
        if (command == null)
            return null;

        UsbConnect usbConnect = new UsbConnect();

        if (!usbConnect.isConnected())
            return null;

        handler = usbConnect.getHandlerKbrd();

        if (! write(command))
            MessagesConsumer.getInstance().inform("Complete!");

        usbConnect.close();
        return null;
    }

    private byte[] effectDisable(){
        return new byte[] {
            0x11, (byte) 0xff, 0x0d, 0x3c, 0x00, 0x00, 0x00, 0x00,     0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00,        0x00, 0x00, 0x00
        };
    }

    private byte[] effectConstantColor(HashMap<String, Byte> effectData){
        return new byte[] {
            0x11, (byte) 0xff, 0x0d, 0x3c, 0x00, 0x01, effectData.get("RED"), effectData.get("GREEN"),     effectData.get("BLUE"), 0x02, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00,        0x00, 0x00, 0x00
        };
    }

    private byte[] effectBreath(HashMap<String, Byte> effectData){
        return new byte[] {
            0x11, (byte) 0xff, 0x0d, 0x3c, 0x00, 0x02, effectData.get("RED"), effectData.get("GREEN"),     effectData.get("BLUE"), effectData.get("TIME_HIGH"), effectData.get("TIME_LOW"), 0x00, 0x64, 0x00, 0x00, 0x00,
            0x00,        0x00, 0x00, 0x00
        };
    }

    private byte[] effectCirlesOnPress(HashMap<String, Byte> effectData){
        return new byte[] {
            0x11, (byte) 0xff, 0x0d, 0x3c, 0x00, 0x06, effectData.get("RED"), effectData.get("GREEN"),     effectData.get("BLUE"), 0x00, 0x00, effectData.get("TIME_LOW"), 0x00, 0x00, 0x00, 0x00,
            0x00,        0x00, 0x00, 0x00
        };
    }

    private byte[] effectCycle(HashMap<String, Byte> effectData){
        return new byte[] {
            0x11, (byte) 0xff, 0x0d, 0x3c, 0x00, 0x03, 0x00, 0x00,     0x00, 0x00, 0x00, effectData.get("TIME_HIGH"), effectData.get("TIME_LOW"), 0x64, 0x00, 0x00,
            0x00,        0x00, 0x00, 0x00
        };
    }

    private byte[] effectWaveHorizontalFrw(HashMap<String, Byte> effectData){
        return new byte[] {
            0x11, (byte) 0xff, 0x0d, 0x3c, 0x00, 0x04, 0x00, 0x00,      0x00, 0x00, 0x00, 0x00, effectData.get("TIME_LOW"), 0x01, 0x64, effectData.get("TIME_HIGH"),
            0x00,        0x00, 0x00, 0x00
        };
    }

    private byte[] effectWaveVerticalFrw(HashMap<String, Byte> effectData){
        return new byte[] {
            0x11, (byte) 0xff, 0x0d, 0x3c, 0x00, 0x04, 0x00, 0x00,      0x00, 0x00, 0x00, 0x00, effectData.get("TIME_LOW"), 0x02, 0x64, effectData.get("TIME_HIGH"),
            0x00,        0x00, 0x00, 0x00
        };
    }

    private byte[] effectWaveCenterToEdge(HashMap<String, Byte> effectData){
        return new byte[] {
            0x11, (byte) 0xff, 0x0d, 0x3c, 0x00, 0x04, 0x00, 0x00,      0x00, 0x00, 0x00, 0x00, effectData.get("TIME_LOW"), 0x03, 0x64, effectData.get("TIME_HIGH"),
            0x00,        0x00, 0x00, 0x00
        };
    }

    private byte[] effectWaveHorizontalBkw(HashMap<String, Byte> effectData){
        return new byte[] {
            0x11, (byte) 0xff, 0x0d, 0x3c, 0x00, 0x04, 0x00, 0x00,      0x00, 0x00, 0x00, 0x00, effectData.get("TIME_LOW"), 0x06, 0x64, effectData.get("TIME_HIGH"),
            0x00,        0x00, 0x00, 0x00
        };
    }

    private byte[] effectWaveVerticalBkw(HashMap<String, Byte> effectData){
        return new byte[] {
            0x11, (byte) 0xff, 0x0d, 0x3c, 0x00, 0x04, 0x00, 0x00,      0x00, 0x00, 0x00, 0x00, effectData.get("TIME_LOW"), 0x07, 0x64, effectData.get("TIME_HIGH"),
            0x00,        0x00, 0x00, 0x00
        };
    }

    private byte[] effectWaveEdgeToCenter(HashMap<String, Byte> effectData){
        return new byte[] {
            0x11, (byte) 0xff, 0x0d, 0x3c, 0x00, 0x04, 0x00, 0x00,      0x00, 0x00, 0x00, 0x00, effectData.get("TIME_LOW"), 0x08, 0x64, effectData.get("TIME_HIGH"),
            0x00,        0x00, 0x00, 0x00
        };
    }
}
