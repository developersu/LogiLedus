package logiledus;

import javafx.application.HostServices;
import javafx.scene.Scene;

public class Mediator{

    private HostServices hostServices;
    private AppPreferences preferences;
    private Scene scene;

    public static Mediator getInstance(){ return MediatorHolder.INSTANCE; }

    public void setHostServices(HostServices hostServices){ this.hostServices = hostServices; }
    public void setPreferences(AppPreferences preferences){ this.preferences = preferences; }
    public void setScene(Scene scene){ this.scene = scene; }

    private static class MediatorHolder{
        private static final Mediator INSTANCE = new Mediator();
    }

    public HostServices getHostServices() { return hostServices; }
    public AppPreferences getPreferences() { return preferences; }

    public void setTheme(String themeString){
        scene.getStylesheets().remove(0);
        scene.getStylesheets().add(themeString);
    }
}