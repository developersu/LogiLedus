package logiled;

import java.util.prefs.Preferences;

// Rule application settings
public class AppPreferences {

    private Preferences preferences;

    public AppPreferences(){
        preferences = Preferences.userRoot().node("LogiLed");
    }

    public void setUseTray(boolean value){ preferences.putBoolean("USE_TRAY", value); }
    public boolean getUseTray(){ return preferences.getBoolean("USE_TRAY", true); }

/*
    public void setPath(String path){
        preferences.put("PATH", path);
    }
    public String getPath(){
        return preferences.get("PATH", "/");
    }
*/
}
