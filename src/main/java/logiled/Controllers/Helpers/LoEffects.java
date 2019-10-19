package logiled.Controllers.Helpers;

public enum LoEffects {
    DISABLE             ((byte) 0),
    CONSTANT_COLOR      ((byte) 1),
    BREATH              ((byte) 2),
    CIRCLES_ON_PRESS    ((byte) 3),
    CYCLE               ((byte) 4),
    WAVE_HORIZONTAL_FRW ((byte) 5),
    WAVE_VERTICAL_FRW   ((byte) 6),
    WAVE_CENTER_TO_EDGE ((byte) 7),
    WAVE_HORIZONTAL_BKW ((byte) 8),
    WAVE_VERTICAL_BKW   ((byte) 9),
    WAVE_EDGE_TO_CENTER ((byte) 10);

    private final byte value;

    LoEffects(byte value){
        this.value = value;
    }

    public byte getValue() { return value; }
}
