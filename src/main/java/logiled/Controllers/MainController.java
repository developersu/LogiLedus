package logiled.Controllers;

import javafx.event.EventHandler;
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
                applyBtn.setDisable(true);
                KeyLedThread keyLedThread = new KeyLedThread(rules);
                keyLedThread.setOnSucceeded(EventHandler -> applyBtn.setDisable(false));       // <- I guess this shit never fails
                Thread commThread = new Thread(keyLedThread);
                commThread.setDaemon(true);
                commThread.start();
            }
            else if (MainTabPane.getSelectionModel().getSelectedItem().getId().equals("EffectsTab")) {
                applyBtn.setDisable(true);
                EffectsThread effectsThread = new EffectsThread(EffectsController.getEffect());
                effectsThread.setOnSucceeded(EventHandler -> applyBtn.setDisable(false));
                Thread commThread = new Thread(effectsThread);
                commThread.setDaemon(true);
                commThread.start();
            }
            else {  // Consider as GameMode; refactor in case more tabs added.
                applyBtn.setDisable(true);
                GameModeThread gameModeThread = new GameModeThread(GameModeController.getKeys());
                gameModeThread.setOnSucceeded(EventHandler -> applyBtn.setDisable(false));
                Thread commThread = new Thread(gameModeThread);
                commThread.setDaemon(true);
                commThread.start();
            }
        });
    }
}
