package logiled.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import logiled.About.AboutWindow;
import logiled.MessagesConsumer;
import logiled.USB.EffectsThread;
import logiled.USB.GameModeThread;
import logiled.USB.KeyLedThread;

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
    private GameModeController GameModeController;

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

    // TODO: add block & release-button function

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
                KeyLedThread keyLedThread = new KeyLedThread(rules);
                Thread commThread = new Thread(keyLedThread);
                commThread.setDaemon(true);
                commThread.start();
            }
            else if (MainTabPane.getSelectionModel().getSelectedItem().getId().equals("EffectsTab")) {
                EffectsThread effectsThread = new EffectsThread(EffectsController.getEffect());
                Thread commThread = new Thread(effectsThread);
                commThread.setDaemon(true);
                commThread.start();
            }
            else {  // Consider as GameMode; refactor in case more tabs added.
                List<Byte> disKeysList = GameModeController.getKeys();
                if (disKeysList.isEmpty())
                    return;
                GameModeThread gameModeThread = new GameModeThread(disKeysList);
                Thread commThread = new Thread(gameModeThread);
                commThread.setDaemon(true);
                commThread.start();
            }
        });
    }
}
