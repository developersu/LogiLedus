package logiled;

import javafx.application.HostServices;

public class Mediator{

    private HostServices hostServices;

    public static Mediator getInstance(){ return MediatorHolder.INSTANCE; }

    public void setInstance(HostServices hostServices){ this.hostServices = hostServices; }

    private static class MediatorHolder{
        private static final Mediator INSTANCE = new Mediator();
    }

    public HostServices getHostServices() { return hostServices; }
}