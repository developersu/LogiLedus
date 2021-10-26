package logiledus;

import java.util.prefs.Preferences;

// Rule application settings
public class AppPreferences {
    private static final Preferences preferences = Preferences.userRoot().node("LogiLedus");

    public void setUseTray(boolean value){ preferences.putBoolean("USE_TRAY", value); }
    public boolean getUseTray(){ return preferences.getBoolean("USE_TRAY", true); }

    public void setTheme(String value){ preferences.put("THEME", value); }
    public String getTheme(){ return preferences.get("THEME", "/light.css"); }

    public void setRecent(String value){ preferences.put("recent", value); }
    public String getRecent(){ return preferences.get("recent", ""); }

    public void setOpenRecentPlaylistOnStart(boolean value){ preferences.putBoolean("auto_open_recent", value); }
    public boolean getOpenRecentPlaylistOnStart(){ return preferences.getBoolean("auto_open_recent", true); }
}
