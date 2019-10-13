package logiled.About;

import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import logiled.MainFx;
import logiled.Mediator;

import java.net.URL;
import java.util.ResourceBundle;


public class AboutController implements Initializable {

    @FXML
    private Button buttonOk;

    @FXML
    private Label versionLbl;

    @FXML
    private Hyperlink iconsMaterialHLink,
            usb4JavaHLink,
            gitHubHLink,
            blogspotHLink;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        versionLbl.setText(MainFx.appVersion);

        HostServices hs = Mediator.getInstance().getHostServices();

        iconsMaterialHLink.setOnAction(ActionEvent-> {
            try {
                hs.showDocument("https://materialdesignicons.com/");
            } catch (Exception ignored){}   // No luck for linux =(
        });
        usb4JavaHLink.setOnAction(ActionEvent-> {
            try {
                hs.showDocument("http://usb4java.org/");
            } catch (Exception ignored){}   // No luck for linux =(
        });
        gitHubHLink.setOnAction(ActionEvent-> {
            try {
                hs.showDocument("https://github.com/developersu/LogiLed");
            } catch (Exception ignored){}   // No luck for linux =(
        });
        blogspotHLink.setOnAction(ActionEvent-> {
            try {
                hs.showDocument("https://developersu.blogspot.com/");
            } catch (Exception ignored){}   // No luck for linux =(
        });
    }
    /**
     * Set 'Exit' action when 'Ok' clicked
     * */
    @FXML
    private void buttonClickOk(){
        Stage thisStage = (Stage) buttonOk.getScene().getWindow();
        thisStage.close();
    }
}
