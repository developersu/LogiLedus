package logiled.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import logiled.Controllers.Helpers.LoCodepage;

import java.net.URL;
import java.util.*;

public class GameModeController implements Initializable {

    @FXML
    private Button resetBtn;

    private List<String> keySet;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        keySet = new ArrayList<>();
        resetBtn.setOnAction(ActionEvent -> {
            for (String keyID : keySet)
                ((ToggleButton) resetBtn.getScene().lookup("#"+keyID)).setSelected(false);
            keySet.clear();
        });
    }

    @FXML
    private void toggleBntAction(ActionEvent event) {
        ToggleButton btn = (ToggleButton) event.getSource();
        if (btn.isSelected())
            keySet.add(btn.getId());
        else
            keySet.remove(btn.getId());
    }

    public List<Byte> getKeys() {
        List<Byte> listCodes = new ArrayList<>();
        for (String keyId : keySet)
            listCodes.add(LoCodepage.valueOf(keyId).getValue());

        return listCodes;
    }
    /**
     * Get set of keys disabled in game mode. Would be stored in config file.
     * */
    public List<String> getInternalKeySet(){
        return keySet;
    }
    /**
     * Restore from config file.
     * */
    public void setConfig(List<String> keySet){
        resetBtn.fire();
        this.keySet = keySet;
        for (String keyID : keySet)
            ((ToggleButton) resetBtn.getScene().lookup("#"+keyID)).setSelected(true);
    }
}