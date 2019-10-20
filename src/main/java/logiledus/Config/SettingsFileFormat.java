package logiledus.Config;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import logiledus.Controllers.Model.LoRule;

import java.util.HashMap;
import java.util.List;

// Move to main controller; implement getters and use annotation?

@JsonPropertyOrder({"Model", "Led & Keys", "Effect", "Game mode keys"})
public class SettingsFileFormat {

    @JsonProperty("Model")
    private static final String kbrdModel = "G513";

    @JsonProperty("Effect")
    @JsonSerialize(contentUsing = HexSerializer.class)
    @JsonDeserialize(contentUsing = HexDeserializer.class)
    private HashMap<String, Byte> effectHumanReadable;

    @JsonProperty("Game mode keys")
    private List<String> gameModeKeyCodes;

    @JsonProperty("Led & Keys")
    @JsonSerialize(contentUsing = LoRuleSerializer.class)
    @JsonDeserialize(contentUsing = LoRuleDeSerializer.class)
    private List<LoRule> keyLedRule;

    public void setGameModeKeyCodes(List<String> gameModeKeyCodes) {
        this.gameModeKeyCodes = gameModeKeyCodes;
    }

    public void setEffectHumanReadable(HashMap<String, Byte> map) {
        this.effectHumanReadable = map;
    }

    public void setKeyLedRule(List<LoRule> keyLedRule) {
        this.keyLedRule = keyLedRule;
    }

    public HashMap<String, Byte> getEffectHumanReadable() { return effectHumanReadable; }

    public List<String> getGameModeKeyCodes() { return gameModeKeyCodes; }

    public List<LoRule> getKeyLedRule() { return keyLedRule; }
}
