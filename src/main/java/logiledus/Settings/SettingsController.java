package logiledus.Settings;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import logiledus.AppPreferences;
import logiledus.Mediator;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

    @FXML
    private Button cancelBtn, okBtn;

    @FXML
    private CheckBox trayCB, drkThemeCB, openRecentCB;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AppPreferences preferences = Mediator.getInstance().getPreferences();

        trayCB.setSelected(preferences.getUseTray());
        openRecentCB.setSelected(preferences.getOpenRecentPlaylistOnStart());
        drkThemeCB.setSelected(preferences.getTheme().equals("/dark.css"));

        cancelBtn.setOnAction(actionEvent -> ((Stage) cancelBtn.getScene().getWindow()).close());

        okBtn.setOnAction(actionEvent -> {
            preferences.setUseTray(trayCB.isSelected());
            preferences.setOpenRecentPlaylistOnStart(openRecentCB.isSelected());
            if (drkThemeCB.isSelected()) {
                preferences.setTheme("/dark.css");
                Mediator.getInstance().setTheme("/dark.css");
            }
            else {
                preferences.setTheme("/light.css");
                Mediator.getInstance().setTheme("/light.css");
            }
            ((Stage) cancelBtn.getScene().getWindow()).close();
        });

        //trayCB.setSelected();
    }
}
