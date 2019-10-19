package logiled;

import javafx.application.HostServices;

public class Mediator{

    private HostServices hostServices;
    private AppPreferences preferences;

    public static Mediator getInstance(){ return MediatorHolder.INSTANCE; }

    public void setHostServices(HostServices hostServices){ this.hostServices = hostServices; }
    public void setPreferences(AppPreferences preferences){ this.preferences = preferences; }

    private static class MediatorHolder{
        private static final Mediator INSTANCE = new Mediator();
    }

    public HostServices getHostServices() { return hostServices; }
    public AppPreferences getPreferences() { return preferences; }
}