package logiled.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import logiled.About.AboutWindow;
import logiled.MessagesConsumer;
import logiled.USB.Communications;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private KeysLedsController KeysLedsController;

    @FXML
    private EffectsController EffectsController;

    @FXML
    private TabPane MainTabPane;
    /*
    @FXML
    private Tab KeyLedTab, EffectsTab;
    */
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
            if (MainTabPane.getSelectionModel().getSelectedItem().getId().equals("KeyLedTab")) {
                HashMap<String, List<byte[][]>> rules = KeysLedsController.getRules();
                if (rules == null)
                    return;
                Communications communications = new Communications(rules);
                Thread commThread = new Thread(communications);
                commThread.setDaemon(true);
                commThread.start();
            }
            //else if (MainTabPane.getSelectionModel().getSelectedItem().getId().equals("EffectsTab")) { // todo }
        });
    }
}
