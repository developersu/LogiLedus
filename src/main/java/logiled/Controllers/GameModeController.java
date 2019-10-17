package logiled.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;

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
            for (String keyID : keySet) {
                ((ToggleButton) resetBtn.getScene().lookup("#"+keyID)).setSelected(false);
            }
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
}