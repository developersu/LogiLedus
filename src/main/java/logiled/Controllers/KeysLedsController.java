package logiled.Controllers;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class KeysLedsController implements Initializable {

    @FXML
    private Button
            k_esc, k_f1, k_f2, k_f3, k_f4, k_f5, k_f6, k_f7, k_f8, k_f9, k_f10, k_f11, k_f12,
            k_1, k_2, k_3, k_4, k_5, k_6, k_7, k_8, k_9, k_0, k_dash, k_equal, k_backspace,
            k_tab,  k_q, k_w, k_e, k_r, k_t, k_y, k_u, k_i, k_o, k_p, k_bracket_open, k_bracket_close, k_backslash,
            k_caps, k_a, k_s, k_d, k_f, k_g, k_h, k_j, k_k, k_l, k_semicolon, k_quotation, k_enter,
            k_l_shift, k_z, k_x, k_c, k_v, k_b, k_n, k_m, k_comma, k_dot, k_shash, k_r_shift,
            k_l_ctrl, k_win, k_l_alt, k_space, k_r_alt, k_fn, k_menu, k_r_ctrl,
            k_prtscr, k_scrl, k_pause, k_tilde, k_ins, k_home, k_pg_up, k_del, k_pg_dn,
            k_arr_up, k_arr_down, k_arr_left, k_arr_right, k_end,
            k_num, k_num_slash, k_num_asterisk, k_num_minus, k_num_9, k_num_8, k_num_7, k_num_6, k_num_5, k_num_4, k_num_3, k_num_2, k_num_1, k_num_0, k_num_period, k_num_enter, k_num_plus,
            l_game, l_caps;
    @FXML
    private Button addRuleBtn, remRuleBtn;

    @FXML
    private VBox rulesVBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rulesVBox.getChildren().addListener((ListChangeListener<Node>) change -> {
            change.next();                              // Get changes
            if (change.wasAdded()){                     // If something added, turn on ability to remove such rule
                remRuleBtn.setDisable(false);
                return;
            }                                           //  Otherwise, check if we have anything inside the pane
            if (rulesVBox.getChildren().isEmpty())     // If so, select latest available 'box'
                remRuleBtn.setDisable(true);
            else                                        // If the pane is empty, disable 'Remove' button.
                RuleBox.select((RuleBox) rulesVBox.getChildren().get(rulesVBox.getChildren().size()-1));
        });

        addRuleBtn.setOnAction(ActionEvent -> rulesVBox.getChildren().add(new RuleBox()));
        remRuleBtn.setDisable(true);
        remRuleBtn.setOnAction(ActionEvent -> {
            if (rulesVBox.getChildren().isEmpty())
                return;
            RuleBox box;
            if ((box = RuleBox.getSelected()) != null)
                box.wipe();  // Reset buttons used for rule
            rulesVBox.getChildren().remove(RuleBox.getSelected());  // Remove from pane
        });
    }

    @FXML
    private void toggleBntAction(ActionEvent event){
        if (rulesVBox.getChildren().isEmpty())          // if we have rule, then we have selected rule (app architecture)
            return;
        Button btn = (Button) event.getSource();
        RuleBox box;
        if ((box = RuleBox.getSelected()) != null)
            box.addKey(btn);
    }

    public HashMap<String, List<byte[][]>> getRules(){
        final HashMap<String, List<byte[][]>> set = new HashMap<>();
        final List<byte[][]> keySet = new ArrayList<>();
        final List<byte[][]> ledSet = new ArrayList<>();

        byte[][] keySingleRuleSet;
        byte[][] ledSingleRuleSet;

        for (Node box : rulesVBox.getChildren()){
            keySingleRuleSet = ((RuleBox) box).getKeyCodes();
            if (keySingleRuleSet != null)
                keySet.add(keySingleRuleSet);
            ledSingleRuleSet = ((RuleBox) box).getLedCodes();
            if (ledSingleRuleSet != null)
                ledSet.add(ledSingleRuleSet);
        }
        if (keySet.size() == 0 && ledSet.size() == 0)
            return null;
        set.put("Key", keySet);
        set.put("Led", ledSet);
        return set;
    }
}
