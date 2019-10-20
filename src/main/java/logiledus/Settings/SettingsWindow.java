package logiledus.Settings;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class SettingsWindow {

    public SettingsWindow(){
        Stage stageAbout = new Stage();

        stageAbout.setMinWidth(500);
        stageAbout.setMinHeight(500);

        FXMLLoader loaderAbout = new FXMLLoader(getClass().getResource("/SettingsLayout.fxml"));
        ResourceBundle resourceBundle;

        Locale userLocale = new Locale(Locale.getDefault().getISO3Language());      // NOTE: user locale based on ISO3 Language codes
        resourceBundle = ResourceBundle.getBundle("locale", userLocale);
        loaderAbout.setResources(resourceBundle);

        try {
            Parent parentAbout = loaderAbout.load();

            stageAbout.setTitle(resourceBundle.getString("btn_settings"));
            stageAbout.getIcons().addAll(
                    new Image(getClass().getResourceAsStream("/ico/appIcon_32.png")),
                    new Image(getClass().getResourceAsStream("/ico/appIcon_48.png")),
                    new Image(getClass().getResourceAsStream("/ico/appIcon_64.png")),
                    new Image(getClass().getResourceAsStream("/ico/appIcon_128.png"))
            );
            stageAbout.setScene(new Scene(parentAbout, 500, 500));

            stageAbout.show();

        } catch (IOException ignored){}
    }
}
