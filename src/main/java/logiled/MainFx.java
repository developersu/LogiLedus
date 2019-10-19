package logiled;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainFx extends Application {
    public static final String appVersion = "v0.4";

    private static boolean traySupport = true;

    private Stage stage;
    private ResourceBundle rb;

    @Override
    public void start(Stage primaryStage) throws Exception{
        //-----------------------Tray support---------------------
        this.stage = primaryStage;
        if (traySupport){
            Platform.setImplicitExit(false);
            SwingUtilities.invokeLater(this::addAppToTray);
        }
        //--------------------------------------------------------
        Mediator.getInstance().setInstance(getHostServices());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main.fxml"));

        Locale locale = new Locale(Locale.getDefault().getISO3Language());
        Locale.setDefault(locale);

        rb = ResourceBundle.getBundle("locale", locale);

        loader.setResources(rb);

        Parent root = loader.load();

        primaryStage.getIcons().addAll(
                new Image(getClass().getResourceAsStream("/ico/appIcon_32.png")),
                new Image(getClass().getResourceAsStream("/ico/appIcon_48.png")),
                new Image(getClass().getResourceAsStream("/ico/appIcon_64.png")),
                new Image(getClass().getResourceAsStream("/ico/appIcon_128.png"))
        );
        // NOTE: tray leftovers
        if (traySupport)
            primaryStage.setOnCloseRequest(windowEvent -> primaryStage.hide());

        primaryStage.setTitle("LogiLed "+appVersion);
        primaryStage.setMinWidth(1215);
        primaryStage.setMinHeight(550);
        Scene mainScene = new Scene(root, 1215, 525);
        mainScene.getStylesheets().add("/light.css");
        primaryStage.setScene(mainScene);
        primaryStage.show();

        primaryStage.setOnHidden(e->MessagesConsumer.getInstance().stop()); // Useless?
    }

    private void addAppToTray(){
        try {
            Toolkit.getDefaultToolkit();    // ???

            if (! SystemTray.isSupported()){
                System.out.println("No system tray support. Please try executing this application wit '--no-tray' key.");
                Platform.exit();
            }

            SystemTray tray = SystemTray.getSystemTray();

            TrayIcon trayIcon = new TrayIcon(ImageIO.read(getClass().getResourceAsStream("/ico/appIcon_24.png")));

            trayIcon.addActionListener(ActionEvent -> Platform.runLater(this::showStage));

            MenuItem closeItem = new MenuItem(rb.getString("tray_close"));
            closeItem.addActionListener(actionEvent -> {
                Platform.exit();
                tray.remove(trayIcon);
            });

            java.awt.Font defaultFont = java.awt.Font.decode(null);
            java.awt.Font boldFont = defaultFont.deriveFont(java.awt.Font.BOLD);
            closeItem.setFont(boldFont);

            final PopupMenu popupMenu = new PopupMenu();
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
        if (stage != null) {
            stage.show();
            stage.toFront();
        }
    }

    public static void main(String[] args) {
        if ((args.length > 0)) {
            if (args[0].equals("--no-tray")){
                traySupport = false;
                launch(args);
            }
            if (args[0].equals("-v") || args[0].equals("--version"))
                System.out.println("LogiLed " + appVersion);
            else
                System.out.println("Usage: LogiLed [KEY]\n" +
                        "  -v,  --version\tGet application version\n" +
                        "       --no-tray\tDisable tray support");
        }
        else
            launch(args);
    }
}
