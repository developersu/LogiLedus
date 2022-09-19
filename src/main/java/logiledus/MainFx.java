package logiledus;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import logiledus.Controllers.MainController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainFx extends Application {
    public static final String appVersion = ResourceBundle.getBundle("app").getString("_version");;

    private static boolean traySupport = true;

    private Stage stage;
    private SystemTray tray;
    private TrayIcon trayIcon;

    private ResourceBundle rb;

    @Override
    public void start(Stage primaryStage) throws Exception{
        AppPreferences appPreferences = new AppPreferences();
        if (traySupport)                                // By default, it's enabled, but in case it disabled from CLI, don't touch.
            traySupport = appPreferences.getUseTray();  // Otherwise, check against preferences
        //-----------------------Tray support---------------------
        this.stage = primaryStage;
        if (traySupport){
            Platform.setImplicitExit(false);
            SwingUtilities.invokeLater(this::addAppToTray);
        }
        //--------------------------------------------------------
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main.fxml"));

        Locale locale = new Locale(Locale.getDefault().getISO3Language());
        Locale.setDefault(locale);

        rb = ResourceBundle.getBundle("locale", locale);

        loader.setResources(rb);

        Parent root = loader.load();

        primaryStage.getIcons().addAll(
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ico/appIcon_32.png"))),
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ico/appIcon_48.png"))),
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ico/appIcon_64.png"))),
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ico/appIcon_128.png")))
        );
        // NOTE: tray leftovers
        if (traySupport) {
            stage.setOnCloseRequest(windowEvent -> {
                Platform.exit();
                tray.remove(trayIcon);
            });

            stage.iconifiedProperty().addListener((observableValue, oldVal, iconified) -> {
                if (iconified)
                    Platform.runLater(this::hideStage);
                else
                    Platform.runLater(this::showStage);
            });
            //primaryStage.setOnCloseRequest(windowEvent -> primaryStage.hide());
        }

        primaryStage.setTitle("LogiLedus "+appVersion);
        primaryStage.setMinWidth(1215);
        primaryStage.setMinHeight(550);
        Scene mainScene = new Scene(root, 1215, 525);
        mainScene.getStylesheets().add(appPreferences.getTheme());

        Mediator.getInstance().setHostServices(getHostServices());
        Mediator.getInstance().setPreferences(appPreferences);
        Mediator.getInstance().setScene(mainScene);

        primaryStage.setScene(mainScene);
        primaryStage.show();

        MainController controller = loader.getController();
        primaryStage.setOnHidden(e->{
            MessagesConsumer.getInstance().stop();
            controller.exit();
        }); // Useless?
    }

    private void addAppToTray(){
        try {
            Toolkit.getDefaultToolkit();    // ???

            if (! SystemTray.isSupported()){
                System.out.println("No system tray support. Please try executing this application wit '--no-tray' key.");
                Platform.exit();
            }

            tray = SystemTray.getSystemTray();

            trayIcon = new TrayIcon(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ico/appIcon_24.png"))));

            trayIcon.addActionListener(ActionEvent -> Platform.runLater(this::showStage));

            MenuItem closeItem = new MenuItem(rb.getString("tray_close"));
            closeItem.addActionListener(actionEvent -> {
                Platform.exit();
                tray.remove(trayIcon);
            });

            java.awt.Font defaultFont = java.awt.Font.decode(null);
            java.awt.Font boldFont = defaultFont.deriveFont(java.awt.Font.BOLD);

            MenuItem openItem = new MenuItem(rb.getString("open"));
            openItem.addActionListener(actionEvent -> Platform.runLater(this::showStage));

            openItem.setFont(boldFont);
            closeItem.setFont(boldFont);

            final PopupMenu popupMenu = new PopupMenu();
            popupMenu.add(openItem);
            popupMenu.addSeparator();
            popupMenu.add(closeItem);
            trayIcon.setPopupMenu(popupMenu);
            tray.add(trayIcon);
        }
        catch (IOException | AWTException e){
            e.printStackTrace();
            System.out.println("Something wrong with tray support. Please try executing this application wit '--no-tray' key.");
        }
    }

    private void showStage() {
        if (stage == null)
            return;
        stage.show();
        stage.toFront();
    }

    private void hideStage(){
        if (stage == null)
            return;
        stage.hide();
    }

    public static void main(String[] args) {
        if ((args.length > 0) && args[0].startsWith("-")){
            switch (args[0]){
                case "--no-tray":
                    traySupport = false;
                    launch(args);
                    return;
                case "-v":
                case "--version":
                    System.out.println("LogiLedus " + appVersion);
                    return;
            }
            System.out.println("Usage: LogiLedus [KEY]\n" +
                    "  -v,  --version\tGet application version\n" +
                    "       --no-tray\tDisable tray support");
            return;
        }
        launch(args);
    }
}
