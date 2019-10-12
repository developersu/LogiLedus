package logiled.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import logiled.About.AboutWindow;
import logiled.MessagesConsumer;
import logiled.USB.Communications;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private KbrdController KbrdPaneController;

    @FXML
    private Button applyBtn;

    @FXML
    private Label infoLbl;

    @FXML
    private MenuItem aboutMenuItem;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        aboutMenuItem.setOnAction(actionEvent -> new AboutWindow());
        MessagesConsumer.getInstance().setInstance(infoLbl);
        MessagesConsumer.getInstance().start();

        applyBtn.setOnAction(actionEvent -> {
            Communications communications = new Communications(KbrdPaneController.getRules());
            Thread commThread = new Thread(communications);
            commThread.setDaemon(true);
            commThread.start();
        });
    }
}
