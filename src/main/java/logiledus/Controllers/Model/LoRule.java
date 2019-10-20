package logiledus.Controllers.Model;

public class LoRule {
    private byte red;
    private byte green;
    private byte blue;

    private String[] keyLedCode;

    public LoRule(byte red, byte green, byte blue, String[] keyLedCode){
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.keyLedCode = keyLedCode;
    }

    public byte getRed() {
        return red;
    }

    public byte getGreen() {
        return green;
    }

    public byte getBlue() {
        return blue;
    }

    public String[] getKeyLedCode() {
        return keyLedCode;
    }
}
