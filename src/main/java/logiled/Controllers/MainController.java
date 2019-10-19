package logiled.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import logiled.About.AboutWindow;
import logiled.MessagesConsumer;
import logiled.Config.SettingsFileFormat;
import logiled.ServiceWindow;
import logiled.Settings.SettingsWindow;
import logiled.USB.EffectsThread;
import logiled.USB.GameModeThread;
import logiled.USB.KeyLedThread;

import java.io.*;
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
    private Button applyBtn, openBtn, saveBtn, saveAsBtn, settingsBtn, aboutBtn;

    @FXML
    private Label infoLbl;

    private ResourceBundle rb;

    private String recentPath;
    private File openedConfigFile;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.rb = resourceBundle;

        aboutBtn.setOnAction(actionEvent -> new AboutWindow());
        settingsBtn.setOnAction(actionEvent -> new SettingsWindow());
        MessagesConsumer.getInstance().setInstance(infoLbl);
        MessagesConsumer.getInstance().start();

        openBtn.setOnAction(actionEvent -> openConfig());

        saveBtn.setOnAction(ActionEvent -> saveConfig(false));
        saveAsBtn.setOnAction(ActionEvent -> saveConfig(true));

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

    /**
     * For 'Open' button
     * */
    private void openConfig(){
        File configFile = getOpenFileChooser();
        if (configFile == null)
            return;
        else
            recentPath = configFile.getParentFile().getAbsolutePath();
        ObjectMapper mapper = new ObjectMapper();
        SettingsFileFormat setup;
        try{
            setup = mapper.readerFor(SettingsFileFormat.class).readValue(new FileInputStream(configFile));

            KeysLedsController.setConfig(setup.getKeyLedRule());
            EffectsController.setConfig(setup.getEffectHumanReadable());
            GameModeController.setConfig(setup.getGameModeKeyCodes());

            openedConfigFile = configFile;
        }
        catch (IOException e){
            ServiceWindow.getErrorNotification(rb.getString("error_any_title"), rb.getString("error_any_body"));
            e.printStackTrace();
        }
    }

    /**
     * Show File Chooser for saving file
     * @return file that has to be written.
     * */
    private File getOpenFileChooser(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(rb.getString("btn_save_as"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("LogiLed config (*.lcfg)", "*.lcfg"));

        if (recentPath != null){
            File stat = new File(recentPath);
            if (! stat.exists())
                recentPath = System.getProperty("user.home");
        }
        else
            recentPath = System.getProperty("user.home");

        fileChooser.setInitialDirectory(new File(recentPath));

        return fileChooser.showOpenDialog(applyBtn.getScene().getWindow());
    }
    /**
     * For 'Save' and 'Save As' buttons
     * */
    private void saveConfig(boolean isSaveAs){
        SettingsFileFormat fileFormat = new SettingsFileFormat();
        fileFormat.setKeyLedRule(KeysLedsController.getInternalRules());
        fileFormat.setEffectHumanReadable(EffectsController.getEffect());
        fileFormat.setGameModeKeyCodes(GameModeController.getInternalKeySet());
        File tempFile;
        if (isSaveAs) {
            if ((tempFile = getSaveFileChooser()) == null)  // In case nothing user clicked 'Cancel button' just leave this
                return;
            else
                openedConfigFile = tempFile;    // Because: if we already have config loaded, we won't loose it with this hint.
        }
        else {
            // If nothing opened then show file chooser
            if (openedConfigFile == null){
                if ((openedConfigFile = getSaveFileChooser()) == null)  // In case nothing user clicked 'Cancel button' just leave this
                    return;
                else
                    recentPath = openedConfigFile.getParentFile().getAbsolutePath();
            }
        }
        // Create JSON magic
        ObjectMapper mapper = new ObjectMapper();

        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new FileOutputStream(openedConfigFile), fileFormat);
            infoLbl.setText(rb.getString("info_file_saved"));
        }
        catch (Exception e){
            ServiceWindow.getErrorNotification(rb.getString("error_any_title"), rb.getString("error_any_body"));
            infoLbl.setText(rb.getString("info_file_not_saved"));
            e.printStackTrace();
        }
/*
            try{

                // DEBUG
                String jsonResult = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(fileFormat);
                //System.out.println(jsonResult);

                ObjectMapper mapper = new ObjectMapper();
                SettingsFileFormat ff = mapper.readerFor(SettingsFileFormat.class).readValue(jsonResult);

                System.out.println("FF getKeyLedRule: ");
                for (LoRule r: ff.getKeyLedRule()){
                    System.out.println(r.getRed()+" "+r.getGreen()+" "+r.getBlue()+" ");
                    for (String s: r.getKeyLedCode())
                        System.out.print(s+" ");
                    System.out.println();
                }
                System.out.println("FF getEffectHumanReadable: ");
                for (Map.Entry<String, Byte> e : ff.getEffectHumanReadable().entrySet()){
                    System.out.println(e.getKey()+" "+e.getValue());
                }
                System.out.println("FF getGameModeKeyCodes: ");
                for (String s: ff.getGameModeKeyCodes())
                    System.out.println(s);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
 */
    }

    /**
     * Show File Chooser for saving file
     * @return file that has to be written.
     * */
    private File getSaveFileChooser(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(rb.getString("btn_save_as"));
        fileChooser.setInitialFileName("keyboard settings.lcfg");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("LogiLed config (*.lcfg)", "*.lcfg"));

        if (recentPath != null){
            File stat = new File(recentPath);
            if (! stat.exists())
                recentPath = System.getProperty("user.home");
        }

        return fileChooser.showSaveDialog(applyBtn.getScene().getWindow());
    }
}