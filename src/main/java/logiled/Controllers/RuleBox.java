package logiled.Controllers;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;

import java.util.*;

public class RuleBox extends HBox {   // todo: add class to selected toggle user data; provide interface to add buttons to selected toggle

    private final static ToggleGroup radioGroup = new ToggleGroup();

    /**
     * Get object (rule) that is currently selected
     * */
    public static RuleBox getSelected(){
        Toggle selectedToggle;
        if ((selectedToggle = radioGroup.getSelectedToggle()) == null)
            return null;
        return (RuleBox) selectedToggle.getUserData();
    }
    /**
     * Select rule
     * @param box : the rule that has to be selected.
     * */
    public static void select(RuleBox box){
        box.radBtn.setSelected(true);
    }

    private final FlowPane flowPaneBox;
    private final ColorPicker colorPicker;
    private final RadioButton radBtn;

    private static final Map<String, Byte> LoCodepage;

    static {
        Map<String, Byte> stMap = new HashMap<>();
        stMap.put("l_game", ((byte) 0x2));
        stMap.put("l_caps",((byte) 0x3));
        stMap.put("k_a",((byte) 0x4));
        stMap.put("k_b",((byte) 0x5));
        stMap.put("k_c",((byte) 0x6));
        stMap.put("k_d",((byte) 0x7));
        stMap.put("k_e",((byte) 0x8));
        stMap.put("k_f",((byte) 0x9));
        stMap.put("k_g",((byte) 0xA));
        stMap.put("k_h",((byte) 0xB));
        stMap.put("k_i",((byte) 0xC));
        stMap.put("k_j",((byte) 0xD));
        stMap.put("k_k",((byte) 0xE));
        stMap.put("k_l",((byte) 0xF));
        stMap.put("k_m",((byte) 0x10));
        stMap.put("k_n",((byte) 0x11));
        stMap.put("k_o",((byte) 0x12));
        stMap.put("k_p",((byte) 0x13));
        stMap.put("k_q",((byte) 0x14));
        stMap.put("k_r",((byte) 0x15));
        stMap.put("k_s",((byte) 0x16));
        stMap.put("k_t",((byte) 0x17));
        stMap.put("k_u",((byte) 0x18));
        stMap.put("k_v",((byte) 0x19));
        stMap.put("k_w",((byte) 0x1A));
        stMap.put("k_x",((byte) 0x1B));
        stMap.put("k_y",((byte) 0x1C));
        stMap.put("k_z",((byte) 0x1D));
        stMap.put("k_1",((byte) 0x1E));
        stMap.put("k_2",((byte) 0x1F));
        stMap.put("k_3",((byte) 0x20));
        stMap.put("k_4",((byte) 0x21));
        stMap.put("k_5",((byte) 0x22));
        stMap.put("k_6",((byte) 0x23));
        stMap.put("k_7",((byte) 0x24));
        stMap.put("k_8",((byte) 0x25));
        stMap.put("k_9",((byte) 0x26));
        stMap.put("k_0",((byte) 0x27));
        stMap.put("k_enter",((byte) 0x28));
        stMap.put("k_esc",((byte) 0x29));
        stMap.put("k_backspace",((byte) 0x2a));
        stMap.put("k_tab",((byte) 0x2b));
        stMap.put("k_space",((byte) 0x2c));
        stMap.put("k_dash",((byte) 0x2d));
        stMap.put("k_equal",((byte) 0x2e));
        stMap.put("k_bracket_open",((byte) 0x2f));
        stMap.put("k_bracket_close",((byte) 0x30));
        stMap.put("k_backslash",((byte) 0x32));
        stMap.put("k_semicolon",((byte) 0x33));
        stMap.put("k_quotation",((byte) 0x34));
        stMap.put("k_tilde",((byte) 0x35));
        stMap.put("k_comma",((byte) 0x36));
        stMap.put("k_dot",((byte) 0x37));
        stMap.put("k_shash",((byte) 0x38));
        stMap.put("k_caps",((byte) 0x39));
        stMap.put("k_f1",((byte) 0x3a));
        stMap.put("k_f2",((byte) 0x3b));
        stMap.put("k_f3",((byte) 0x3c));
        stMap.put("k_f4",((byte) 0x3d));
        stMap.put("k_f5",((byte) 0x3e));
        stMap.put("k_f6",((byte) 0x3f));
        stMap.put("k_f7",((byte) 0x40));
        stMap.put("k_f8",((byte) 0x41));
        stMap.put("k_f9",((byte) 0x42));
        stMap.put("k_f10",((byte) 0x43));
        stMap.put("k_f11",((byte) 0x44));
        stMap.put("k_f12",((byte) 0x45));
        stMap.put("k_prtscr",((byte) 0x46));
        stMap.put("k_scrl",((byte) 0x47));
        stMap.put("k_pause",((byte) 0x48));
        stMap.put("k_ins",((byte) 0x49));
        stMap.put("k_home",((byte) 0x4a));
        stMap.put("k_pg_up",((byte) 0x4b));
        stMap.put("k_del",((byte) 0x4c));
        stMap.put("k_end",((byte) 0x4d));
        stMap.put("k_pg_dn",((byte) 0x4e));
        stMap.put("k_arr_right",((byte) 0x4f));
        stMap.put("k_arr_left",((byte) 0x50));
        stMap.put("k_arr_down",((byte) 0x51));
        stMap.put("k_arr_up",((byte) 0x52));
        stMap.put("k_num",((byte) 0x53));
        stMap.put("k_num_slash",((byte) 0x54));
        stMap.put("k_num_asterisk",((byte) 0x55));
        stMap.put("k_num_minus",((byte) 0x56));
        stMap.put("k_num_plus",((byte) 0x57));
        stMap.put("k_num_enter",((byte) 0x58));
        stMap.put("k_num_1",((byte) 0x59));
        stMap.put("k_num_2",((byte) 0x5a));
        stMap.put("k_num_3",((byte) 0x5b));
        stMap.put("k_num_4",((byte) 0x5c));
        stMap.put("k_num_5",((byte) 0x5d));
        stMap.put("k_num_6",((byte) 0x5e));
        stMap.put("k_num_7",((byte) 0x5f));
        stMap.put("k_num_8",((byte) 0x60));
        stMap.put("k_num_9",((byte) 0x61));
        stMap.put("k_num_0",((byte) 0x62));
        stMap.put("k_num_period",((byte) 0x63));
        stMap.put("k_menu",((byte) 0x65));
        stMap.put("k_l_ctrl",((byte) 0xe0));
        stMap.put("k_l_shift",((byte) 0xe1));
        stMap.put("k_l_alt",((byte) 0xe2));
        stMap.put("k_win",((byte) 0xe3));
        stMap.put("k_r_ctrl",((byte) 0xe4));
        stMap.put("k_r_shift",((byte) 0xe5));
        stMap.put("k_r_alt",((byte) 0xe6));
        stMap.put("k_fn",((byte) 0xe7));

        LoCodepage = Collections.unmodifiableMap(stMap);
    }

    public RuleBox(){
        super();
        Insets insets = new Insets(3.0, 3.0, 3.0, 3.0);
        // Radio button for selecting rule
        radBtn = new RadioButton();
        radBtn.setToggleGroup(radioGroup);      // Add to our declared group
        radBtn.setSelected(true);
        // FlowPane for buttons selected for this rule
        flowPaneBox = new FlowPane();
        flowPaneBox.setVgap(5.0);
        flowPaneBox.setHgap(5.0);
        HBox.setHgrow(flowPaneBox, Priority.ALWAYS);
        // Set FlowPane where elements would be added
        radBtn.setUserData(this);
        // Set Color Picker
        colorPicker = new ColorPicker();
        colorPicker.setValue(Color.CYAN);   // (^-^ )
        colorPicker.setOnAction(ActionEvent-> {
            for (Node node : flowPaneBox.getChildren())
                ((Button) ((Object[]) node.getUserData())[0]).setStyle(
                        String.format(".button:disabled {-fx-background-color: #bdf7ff; -fx-opacity: 1.0; -fx-text-fill: #%02x%02x%02x; -fx-border-color: #%02x%02x%02x;}",
                                (int) (colorPicker.getValue().getRed()*255),
                                (int) (colorPicker.getValue().getGreen()*255),
                                (int) (colorPicker.getValue().getBlue()*255),
                                (int) (colorPicker.getValue().getRed()*255),
                                (int) (colorPicker.getValue().getGreen()*255),
                                (int) (colorPicker.getValue().getBlue()*255))
                );
        });
        // Node that contains elements
        this.setPadding(insets);
        this.setSpacing(5.0);
        this.getChildren().addAll(radBtn, colorPicker, new Separator(Orientation.VERTICAL), flowPaneBox);
    }
    /**
     * Prepare this object to be deleted: release all buttons used etc.
     * */
    public void wipe(){
        for (Node node : flowPaneBox.getChildren()) {
            Button key = (Button) ((Object[]) node.getUserData())[0];
            key.setStyle("");
            key.setDisable(false);
        }
    }
    /**
     * Add button to UI (appears for current rule)
     * */
    public void addKey(Button keyBtn){
        keyBtn.setDisable(true);

        keyBtn.setStyle(
                String.format(".button:disabled {-fx-background-color: #bdf7ff; -fx-opacity: 1.0; -fx-text-fill: #%02x%02x%02x; -fx-border-color: #%02x%02x%02x;}",
                        (int) (colorPicker.getValue().getRed()*255),
                        (int) (colorPicker.getValue().getGreen()*255),
                        (int) (colorPicker.getValue().getBlue()*255),
                        (int) (colorPicker.getValue().getRed()*255),
                        (int) (colorPicker.getValue().getGreen()*255),
                        (int) (colorPicker.getValue().getBlue()*255))
        );
        final Button key = new Button(keyBtn.getText());    // Set same name
        key.setMnemonicParsing(false);  // don't wipe underscore from UI
        key.setUserData(new Object[] {keyBtn, keyBtn.getId()}); // Store button-'patent' and 'FX ID'
        key.setOnAction(ActionEvent->{
            keyBtn.setStyle("");
            flowPaneBox.getChildren().remove(key);
            keyBtn.setDisable(false);
        });
        flowPaneBox.getChildren().add(key);
    }
    /**
     * Get information about this rule (keys)
     * */
    public byte[][] getKeyCodes(){
        final List<byte[]> keysList = new ArrayList<>();

        final byte red   = (byte) (colorPicker.getValue().getRed()*255);
        final byte green = (byte) (colorPicker.getValue().getGreen()*255);
        final byte blue  = (byte) (colorPicker.getValue().getBlue()*255);

        for (int i = 0; i < flowPaneBox.getChildren().size(); i++) {
            Object[] pair = (Object[]) flowPaneBox.getChildren().get(i).getUserData();
            String id = (String) pair[1];
            if (id.startsWith("l_"))
                continue;
            byte[] keyInfo = new byte[4];    // Where 0 - special key/indicator code; 1 - red; 2 - green; 3 - blue
            keyInfo[0] = LoCodepage.get(id);
            keyInfo[1] = red;
            keyInfo[2] = green;
            keyInfo[3] = blue;
            keysList.add(keyInfo);
        }

        if (keysList.isEmpty())
            return null;

        return keysList.toArray(new byte[0][]);
    }
    /**
     * Get information about this rule (Leds)
     * */
    public byte[][] getLedCodes(){
        final List<byte[]> ledList = new ArrayList<>();

        final byte red   = (byte) (colorPicker.getValue().getRed()*255);
        final byte green = (byte) (colorPicker.getValue().getGreen()*255);
        final byte blue  = (byte) (colorPicker.getValue().getBlue()*255);

        for (int i = 0; i < flowPaneBox.getChildren().size(); i++) {
            Object[] pair = (Object[]) flowPaneBox.getChildren().get(i).getUserData();
            String id = (String) pair[1];
            if (id.startsWith("k_"))
                continue;
            byte[] keyInfo = new byte[4];    // Where 0 - special key/indicator code; 1 - red; 2 - green; 3 - blue
            keyInfo[0] = LoCodepage.get(id);
            keyInfo[1] = red;
            keyInfo[2] = green;
            keyInfo[3] = blue;
            ledList.add(keyInfo);
        }

        if (ledList.isEmpty())
            return null;

        return ledList.toArray(new byte[0][]);
    }
}
