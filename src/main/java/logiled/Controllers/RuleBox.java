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

class RuleBox extends HBox {   // todo: add class to selected toggle user data; provide interface to add buttons to selected toggle

    private final static ToggleGroup radioGroup = new ToggleGroup();

    /**
     * Get object (rule) that is currently selected
     * */
    static RuleBox getSelected(){
        Toggle selectedToggle;
        if ((selectedToggle = radioGroup.getSelectedToggle()) == null)
            return null;
        return (RuleBox) selectedToggle.getUserData();
    }
    /**
     * Select rule
     * @param box : the rule that has to be selected.
     * */
    static void select(RuleBox box){
        box.radBtn.setSelected(true);
    }

    private final FlowPane flowPaneBox;
    private final ColorPicker colorPicker;
    private final RadioButton radBtn;

    RuleBox(){
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
    void wipe(){
        for (Node node : flowPaneBox.getChildren()) {
            Button key = (Button) ((Object[]) node.getUserData())[0];
            key.setStyle("");
            key.setDisable(false);
        }
    }
    /**
     * Add button to UI (appears for current rule)
     * */
    void addKey(Button keyBtn){
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
    byte[][] getKeyCodes(){
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
            keyInfo[0] = LoCodepage.valueOf(id).getValue();
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
    byte[][] getLedCodes(){
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
            keyInfo[0] = LoCodepage.valueOf(id).getValue();
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
