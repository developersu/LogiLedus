package logiled.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
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
    }

    @FXML
    private void selectEffect(ActionEvent event){
        final RadioMenuItem item = (RadioMenuItem) event.getSource();
        effectsMenu.setText(item.getText());
        switch (item.getId()){
            case "disRMI":
                mainClrPkr.setVisible(false);
                mainSlide.setVisible(false);
                mainSlideInfoLbl.setVisible(false);
                break;
            case "constRMI":
            case "breathRMI":
                mainClrPkr.setVisible(true);
                mainSlide.setVisible(false);
                mainSlideInfoLbl.setVisible(false);
                break;
            case "cycleRMI":
                mainClrPkr.setVisible(false);
                mainSlide.setVisible(true);
                mainSlideInfoLbl.setVisible(true);
                mainSlide.setMin(20.0);
                mainSlide.setMax(200.0);
                mainSlide.setMajorTickUnit(10.0);
                mainSlide.setBlockIncrement(10.0);
                mainSlide.setValue(20.0);
                break;
            case "circlesOnPressRMI":
                mainClrPkr.setVisible(true);
                mainSlide.setVisible(true);
                mainSlideInfoLbl.setVisible(true);
                mainSlide.setMin(1000.0);
                mainSlide.setMax(5000.0);
                mainSlide.setMajorTickUnit(1000.0);
                mainSlide.setBlockIncrement(100.0);
                mainSlide.setValue(1000.0);
                break;
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

    public String getEffect(){
        final RadioMenuItem item = (RadioMenuItem) effectsToggleGrp.getSelectedToggle();
        /*
        HashMap<LoEffects, byte[]> effectsSet = new HashMap<>();
        byte[] meta;

        switch (item.getId()){
            case "disRMI":
                effectsSet.put(LoEffects.DISABLE, null);
                break;
            case "constRMI":
                effectsSet.put(LoEffects.CONSTANT_COLOR, );
                break;
            case "breathRMI":
                effectsSet.put(LoEffects.BREATH, );
                break;
            case "cycleRMI":
                effectsSet.put(LoEffects.CYCLE, );
                break;
            case "circlesOnPressRMI":
                effectsSet.put(LoEffects.CIRCLES_ON_PRESS, );
                break;
            case "hWaveFrwRMI":
                effectsSet.put(LoEffects.WAVE_HORIZONTAL_FRW, );
                break;
            case "vWaveFrwRMI":
                effectsSet.put(LoEffects.WAVE_VERTICAL_FRW, );
                break;
            case "cntrToEdgWaveRMI":
                effectsSet.put(LoEffects.WAVE_CENTER_TO_EDGE, );
                break;
            case "hWaveBkwRMI":
                effectsSet.put(LoEffects.WAVE_HORIZONTAL_BKW, );
                break;
            case "vWaveBkwRMI":
                effectsSet.put(LoEffects.WAVE_VERTICAL_BKW, );
                break;
            case "edgToCntrWaveRMI":
                effectsSet.put(LoEffects.WAVE_EDGE_TO_CENTER, );
                break;
        }
        */
        //System.out.println(LoEffects.DISABLE.getValue());
        return item.getId();
    }
}