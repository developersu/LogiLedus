package logiled.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import logiled.Controllers.Helpers.LoEffects;

import java.net.URL;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.ResourceBundle;

public class EffectsController implements Initializable {

    @FXML
    private MenuButton effectsMenu;

    @FXML
    private ToggleGroup effectsToggleGrp;

    @FXML
    private RadioMenuItem disRMI,
            constRMI,
            breathRMI,
            circlesOnPressRMI,
            cycleRMI,
            hWaveFrwRMI,
            vWaveFrwRMI,
            cntrToEdgWaveRMI,
            hWaveBkwRMI,
            vWaveBkwRMI,
            edgToCntrWaveRMI;

    @FXML
    private ColorPicker mainClrPkr;

    @FXML
    private Slider mainSlide;

    @FXML
    private Label mainSlideInfoLbl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainSlide.valueProperty().addListener((observableValue, oldValue, newValue) -> mainSlideInfoLbl.setText(Long.toString(newValue.longValue())));
        mainSlideInfoLbl.setText(String.format("%.0f", mainSlide.getValue()));

        effectsMenu.setText(((RadioMenuItem) effectsToggleGrp.getSelectedToggle()).getText());
        effectsToggleGrp.selectedToggleProperty().addListener((observableValue, oldToggle, newToggle) -> effectsMenu.setText(((MenuItem) newToggle).getText()));
    }

    @FXML
    private void selectEffect(ActionEvent event){
        final RadioMenuItem item = (RadioMenuItem) event.getSource();
        switch (item.getId()){
            case "disRMI":
                mainClrPkr.setVisible(false);
                mainSlide.setVisible(false);
                mainSlideInfoLbl.setVisible(false);
                break;
            case "constRMI":
                mainClrPkr.setVisible(true);
                mainSlide.setVisible(false);
                mainSlideInfoLbl.setVisible(false);
                break;
            case "circlesOnPressRMI":
                mainClrPkr.setVisible(true);
                mainSlide.setVisible(true);
                mainSlideInfoLbl.setVisible(true);
                mainSlide.setMin(20.0);
                mainSlide.setMax(200.0);
                mainSlide.setMajorTickUnit(10.0);
                mainSlide.setBlockIncrement(10.0);
                mainSlide.setValue(20.0);
                break;
            case "breathRMI":
                mainClrPkr.setVisible(true);
                mainSlide.setVisible(true);
                mainSlideInfoLbl.setVisible(true);
                mainSlide.setMin(1000.0);
                mainSlide.setMax(5000.0);
                mainSlide.setMajorTickUnit(1000.0);
                mainSlide.setBlockIncrement(100.0);
                mainSlide.setValue(1000.0);
                break;
            case "cycleRMI":
            case "hWaveFrwRMI":
            case "vWaveFrwRMI":
            case "cntrToEdgWaveRMI":
            case "hWaveBkwRMI":
            case "vWaveBkwRMI":
            case "edgToCntrWaveRMI":
                mainClrPkr.setVisible(false);
                mainSlide.setVisible(true);
                mainSlideInfoLbl.setVisible(true);
                mainSlide.setMin(1000.0);
                mainSlide.setMax(5000.0);
                mainSlide.setMajorTickUnit(1000.0);
                mainSlide.setBlockIncrement(100.0);
                mainSlide.setValue(1000.0);
                break;
        }
    }

    public HashMap<String, Byte> getEffect(){
        final RadioMenuItem item = (RadioMenuItem) effectsToggleGrp.getSelectedToggle();

        final HashMap<String, Byte> effectsSet = new HashMap<>();

        final byte red = (byte) (mainClrPkr.getValue().getRed()*255);
        final byte green = (byte) (mainClrPkr.getValue().getGreen()*255);
        final byte blue = (byte) (mainClrPkr.getValue().getBlue()*255);

        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong((long) mainSlide.getValue());
        byte[] a = buffer.array();
        byte valHigh = a[6];        // E.g. 1000 = 0x03 0xe8; high = 0x03, low = 0xe8
        byte valLow = a[7];

        switch (item.getId()){
            case "disRMI":
                effectsSet.put("EFFECT", LoEffects.DISABLE.getValue());
                break;
            case "constRMI":
                effectsSet.put("EFFECT", LoEffects.CONSTANT_COLOR.getValue());
                effectsSet.put("RED", red);
                effectsSet.put("GREEN", green);
                effectsSet.put("BLUE", blue);
                break;
            case "breathRMI":
                effectsSet.put("EFFECT", LoEffects.BREATH.getValue());
                effectsSet.put("RED", red);
                effectsSet.put("GREEN", green);
                effectsSet.put("BLUE", blue);
                effectsSet.put("TIME_HIGH", valHigh);
                effectsSet.put("TIME_LOW", valLow);
                break;
            case "circlesOnPressRMI":
                effectsSet.put("EFFECT", LoEffects.CIRCLES_ON_PRESS.getValue());
                effectsSet.put("RED", red);
                effectsSet.put("GREEN", green);
                effectsSet.put("BLUE", blue);
                effectsSet.put("TIME_LOW", valLow);
                break;
            case "cycleRMI":
                effectsSet.put("EFFECT", LoEffects.CYCLE.getValue());
                effectsSet.put("TIME_HIGH", valHigh);
                effectsSet.put("TIME_LOW", valLow);
                break;
            case "hWaveFrwRMI":
                effectsSet.put("EFFECT", LoEffects.WAVE_HORIZONTAL_FRW.getValue());
                effectsSet.put("TIME_HIGH", valHigh);
                effectsSet.put("TIME_LOW", valLow);
                break;
            case "vWaveFrwRMI":
                effectsSet.put("EFFECT", LoEffects.WAVE_VERTICAL_FRW.getValue());
                effectsSet.put("TIME_HIGH", valHigh);
                effectsSet.put("TIME_LOW", valLow);
                break;
            case "cntrToEdgWaveRMI":
                effectsSet.put("EFFECT", LoEffects.WAVE_CENTER_TO_EDGE.getValue());
                effectsSet.put("TIME_HIGH", valHigh);
                effectsSet.put("TIME_LOW", valLow);
                break;
            case "hWaveBkwRMI":
                effectsSet.put("EFFECT", LoEffects.WAVE_HORIZONTAL_BKW.getValue());
                effectsSet.put("TIME_HIGH", valHigh);
                effectsSet.put("TIME_LOW", valLow);
                break;
            case "vWaveBkwRMI":
                effectsSet.put("EFFECT", LoEffects.WAVE_VERTICAL_BKW.getValue());
                effectsSet.put("TIME_HIGH", valHigh);
                effectsSet.put("TIME_LOW", valLow);
                break;
            case "edgToCntrWaveRMI":
                effectsSet.put("EFFECT", LoEffects.WAVE_EDGE_TO_CENTER.getValue());
                effectsSet.put("TIME_HIGH", valHigh);
                effectsSet.put("TIME_LOW", valLow);
                break;
        }

        return effectsSet;
    }
    /**
     * Restore from config file.
     * */
    public void setConfig(HashMap<String, Byte> effectConfig){
        int sliderVal;
        switch (LoEffects.values()[effectConfig.get("EFFECT")]){
            case DISABLE:
                disRMI.fire();
                effectsToggleGrp.selectToggle(disRMI);
                break;
            case CONSTANT_COLOR:
                constRMI.fire();
                effectsToggleGrp.selectToggle(constRMI);
                mainClrPkr.setValue(Color.color(uByteToDoubleClr(effectConfig.get("RED")), uByteToDoubleClr(effectConfig.get("GREEN")), uByteToDoubleClr(effectConfig.get("BLUE"))));
                break;
            case BREATH:
                breathRMI.fire();
                effectsToggleGrp.selectToggle(breathRMI);
                mainClrPkr.setValue(Color.color(uByteToDoubleClr(effectConfig.get("RED")), uByteToDoubleClr(effectConfig.get("GREEN")), uByteToDoubleClr(effectConfig.get("BLUE"))));
                sliderVal = concatInt(effectConfig.get("TIME_HIGH"), effectConfig.get("TIME_LOW"));
                mainSlide.setValue(sliderVal);
                break;
            case CIRCLES_ON_PRESS:
                circlesOnPressRMI.fire();
                effectsToggleGrp.selectToggle(circlesOnPressRMI);
                mainClrPkr.setValue(Color.color(uByteToDoubleClr(effectConfig.get("RED")), uByteToDoubleClr(effectConfig.get("GREEN")), uByteToDoubleClr(effectConfig.get("BLUE"))));
                sliderVal = concatInt((byte) 0, effectConfig.get("TIME_LOW"));
                mainSlide.setValue(sliderVal);
                break;
            case CYCLE:
                cycleRMI.fire();
                effectsToggleGrp.selectToggle(cycleRMI);
                sliderVal = concatInt(effectConfig.get("TIME_HIGH"), effectConfig.get("TIME_LOW"));
                mainSlide.setValue(sliderVal);
                break;
            case WAVE_HORIZONTAL_FRW:
                hWaveFrwRMI.fire();
                effectsToggleGrp.selectToggle(hWaveFrwRMI);
                sliderVal = concatInt(effectConfig.get("TIME_HIGH"), effectConfig.get("TIME_LOW"));
                mainSlide.setValue(sliderVal);
                break;
            case WAVE_VERTICAL_FRW:
                vWaveFrwRMI.fire();
                effectsToggleGrp.selectToggle(vWaveFrwRMI);
                sliderVal = concatInt(effectConfig.get("TIME_HIGH"), effectConfig.get("TIME_LOW"));
                mainSlide.setValue(sliderVal);
                break;
            case WAVE_CENTER_TO_EDGE:
                cntrToEdgWaveRMI.fire();
                effectsToggleGrp.selectToggle(cntrToEdgWaveRMI);
                sliderVal = concatInt(effectConfig.get("TIME_HIGH"), effectConfig.get("TIME_LOW"));
                mainSlide.setValue(sliderVal);
                break;
            case WAVE_HORIZONTAL_BKW:
                hWaveBkwRMI.fire();
                effectsToggleGrp.selectToggle(hWaveBkwRMI);
                sliderVal = concatInt(effectConfig.get("TIME_HIGH"), effectConfig.get("TIME_LOW"));
                mainSlide.setValue(sliderVal);
                break;
            case WAVE_VERTICAL_BKW:
                vWaveBkwRMI.fire();
                effectsToggleGrp.selectToggle(vWaveBkwRMI);
                sliderVal = concatInt(effectConfig.get("TIME_HIGH"), effectConfig.get("TIME_LOW"));
                mainSlide.setValue(sliderVal);
                break;
            case WAVE_EDGE_TO_CENTER:
                edgToCntrWaveRMI.fire();
                effectsToggleGrp.selectToggle(edgToCntrWaveRMI);
                sliderVal = concatInt(effectConfig.get("TIME_HIGH"), effectConfig.get("TIME_LOW"));
                mainSlide.setValue(sliderVal);
                
        }
    }
    /**
     * Since we widely use byte as representation of the Red/Green/Blue, this will convert byte to double < 1.
     * @return double value of the color
     * */
    private double uByteToDoubleClr(byte b){
        return Byte.toUnsignedInt(b)/255.0;
    }
    /**
     * Assemble int value of the two bytes
     * */
    private int concatInt(byte hi, byte lo){
        return ByteBuffer.wrap(new byte[]{0x00, 0x00, hi, lo}).getInt();
    }
}