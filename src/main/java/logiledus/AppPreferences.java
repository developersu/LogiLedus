package logiledus;

import java.util.prefs.Preferences;

// Rule application settings
public class AppPreferences {

    private Preferences preferences;

    public AppPreferences(){
        preferences = Preferences.userRoot().node("LogiLedus");
    }

    public void setUseTray(boolean value){ preferences.putBoolean("USE_TRAY", value); }
    public boolean getUseTray(){ return preferences.getBoolean("USE_TRAY", true); }

    public void setTheme(String value){ preferences.put("THEME", value); }
    public String getTheme(){ return preferences.get("THEME", "/light.css"); }
}
