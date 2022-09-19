package logiledus.About;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import logiledus.Mediator;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class AboutWindow {

    public AboutWindow(){
        Stage stageAbout = new Stage();

        stageAbout.setMinWidth(500);
        stageAbout.setMinHeight(500);

        FXMLLoader loaderAbout = new FXMLLoader(getClass().getResource("/AboutLayout.fxml"));
        ResourceBundle resourceBundle;

        Locale userLocale = new Locale(Locale.getDefault().getISO3Language());      // NOTE: user locale based on ISO3 Language codes
        resourceBundle = ResourceBundle.getBundle("locale", userLocale);
        loaderAbout.setResources(resourceBundle);

        try {
            Parent parentAbout = loaderAbout.load();

            stageAbout.setTitle(resourceBundle.getString("menu_item_about"));
            stageAbout.getIcons().addAll(
                    new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ico/appIcon_32.png"))),
                    new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ico/appIcon_48.png"))),
                    new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ico/appIcon_64.png"))),
                    new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ico/appIcon_128.png")))
            );
            Scene scene = new Scene(parentAbout, 500, 500);
            scene.getStylesheets().add(Mediator.getInstance().getPreferences().getTheme());
            stageAbout.setScene(scene);
            stageAbout.show();

        } catch (IOException ignored){}
    }
}
