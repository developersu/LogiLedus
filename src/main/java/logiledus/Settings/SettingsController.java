package logiledus.Settings;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import logiledus.Mediator;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

    @FXML
    private Button cancelBtn, okBtn;

    @FXML
    private CheckBox trayCB, drkThemeCB;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        trayCB.setSelected(Mediator.getInstance().getPreferences().getUseTray());
        if (Mediator.getInstance().getPreferences().getTheme().equals("/dark.css"))
            drkThemeCB.setSelected(true);

        cancelBtn.setOnAction(actionEvent -> ((Stage) cancelBtn.getScene().getWindow()).close());

        okBtn.setOnAction(actionEvent -> {
            Mediator.getInstance().getPreferences().setUseTray(trayCB.isSelected());
            if (drkThemeCB.isSelected()) {
                Mediator.getInstance().getPreferences().setTheme("/dark.css");
                Mediator.getInstance().setTheme("/dark.css");
            }
            else {
                Mediator.getInstance().getPreferences().setTheme("/light.css");
                Mediator.getInstance().setTheme("/light.css");
            }
            ((Stage) cancelBtn.getScene().getWindow()).close();
        });

        //trayCB.setSelected();
    }
}
