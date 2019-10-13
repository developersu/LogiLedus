package logiled;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class MainFx extends Application {
    public static final String appVersion = "v0.1";

    @Override
    public void start(Stage primaryStage) throws Exception{
        Mediator.getInstance().setInstance(getHostServices());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main.fxml"));

        Locale locale = new Locale(Locale.getDefault().getISO3Language());
        Locale.setDefault(locale);

        ResourceBundle rb = ResourceBundle.getBundle("locale", locale);

        loader.setResources(rb);

        Parent root = loader.load();

        primaryStage.getIcons().addAll(
                new Image(getClass().getResourceAsStream("/ico/appIcon_32.png")),
                new Image(getClass().getResourceAsStream("/ico/appIcon_48.png")),
                new Image(getClass().getResourceAsStream("/ico/appIcon_64.png")),
                new Image(getClass().getResourceAsStream("/ico/appIcon_128.png"))
        );

        primaryStage.setTitle("LogiLed "+appVersion);
        primaryStage.setMinWidth(1215);
        primaryStage.setMinHeight(550);
        Scene mainScene = new Scene(root, 1215, 525);
        mainScene.getStylesheets().add("/light.css");
        primaryStage.setScene(mainScene);
        primaryStage.show();

        primaryStage.setOnHidden(e->MessagesConsumer.getInstance().stop()); // Useless?
    }

    public static void main(String[] args) {
        if ((args.length == 1) && (args[0].equals("-v") || args[0].equals("--version")))
            System.out.println("LogiLed "+appVersion);
        else
            launch(args);
    }
}
