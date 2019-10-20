package logiledus.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import logiledus.Controllers.Helpers.LoCodepage;

import java.net.URL;
import java.util.*;

public class GameModeController implements Initializable {

    @FXML
    private ToggleButton
            k_esc, k_f1, k_f2, k_f3, k_f4, k_f5, k_f6, k_f7, k_f8, k_f9, k_f10, k_f11, k_f12,
            k_1, k_2, k_3, k_4, k_5, k_6, k_7, k_8, k_9, k_0, k_dash, k_equal, k_backspace,
            k_tab,  k_q, k_w, k_e, k_r, k_t, k_y, k_u, k_i, k_o, k_p, k_bracket_open, k_bracket_close, k_backslash,
            k_caps, k_a, k_s, k_d, k_f, k_g, k_h, k_j, k_k, k_l, k_semicolon, k_quotation, k_enter,
            k_l_shift, k_z, k_x, k_c, k_v, k_b, k_n, k_m, k_comma, k_dot, k_shash, k_r_shift,
            k_l_ctrl, k_win, k_l_alt, k_space, k_r_alt, k_fn, k_menu, k_r_ctrl,
            k_prtscr, k_scrl, k_pause, k_tilde, k_ins, k_home, k_pg_up, k_del, k_pg_dn,
            k_arr_up, k_arr_down, k_arr_left, k_arr_right, k_end,
            k_num, k_num_slash, k_num_asterisk, k_num_minus, k_num_9, k_num_8, k_num_7, k_num_6, k_num_5, k_num_4, k_num_3, k_num_2, k_num_1, k_num_0, k_num_period, k_num_enter, k_num_plus;

    @FXML
    private Button resetBtn;

    private List<String> keySet;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        keySet = new ArrayList<>();

        HashMap<String, ToggleButton> kbrdMap = new HashMap<>();
        kbrdMap.put("k_esc", k_esc);
        kbrdMap.put("k_f1", k_f1);
        kbrdMap.put("k_f2", k_f2);
        kbrdMap.put("k_f3", k_f3);
        kbrdMap.put("k_f4", k_f4);
        kbrdMap.put("k_f5", k_f5);
        kbrdMap.put("k_f6", k_f6);
        kbrdMap.put("k_f7", k_f7);
        kbrdMap.put("k_f8", k_f8);
        kbrdMap.put("k_f9", k_f9);
        kbrdMap.put("k_f10", k_f10);
        kbrdMap.put("k_f11", k_f11);
        kbrdMap.put("k_f12", k_f12);
        kbrdMap.put("k_1", k_1);
        kbrdMap.put("k_2", k_2);
        kbrdMap.put("k_3", k_3);
        kbrdMap.put("k_4", k_4);
        kbrdMap.put("k_5", k_5);
        kbrdMap.put("k_6", k_6);
        kbrdMap.put("k_7", k_7);
        kbrdMap.put("k_8", k_8);
        kbrdMap.put("k_9", k_9);
        kbrdMap.put("k_0", k_0);
        kbrdMap.put("k_dash", k_dash);
        kbrdMap.put("k_equal", k_equal);
        kbrdMap.put("k_backspace", k_backspace);
        kbrdMap.put("k_tab", k_tab);
        kbrdMap.put("k_q", k_q);
        kbrdMap.put("k_w", k_w);
        kbrdMap.put("k_e", k_e);
        kbrdMap.put("k_r", k_r);
        kbrdMap.put("k_t", k_t);
        kbrdMap.put("k_y", k_y);
        kbrdMap.put("k_u", k_u);
        kbrdMap.put("k_i", k_i);
        kbrdMap.put("k_o", k_o);
        kbrdMap.put("k_p", k_p);
        kbrdMap.put("k_bracket_open", k_bracket_open);
        kbrdMap.put("k_bracket_close", k_bracket_close);
        kbrdMap.put("k_backslash", k_backslash);
        kbrdMap.put("k_caps", k_caps);
        kbrdMap.put("k_a", k_a);
        kbrdMap.put("k_s", k_s);
        kbrdMap.put("k_d", k_d);
        kbrdMap.put("k_f", k_f);
        kbrdMap.put("k_g", k_g);
        kbrdMap.put("k_h", k_h);
        kbrdMap.put("k_j", k_j);
        kbrdMap.put("k_k", k_k);
        kbrdMap.put("k_l", k_l);
        kbrdMap.put("k_semicolon", k_semicolon);
        kbrdMap.put("k_quotation", k_quotation);
        kbrdMap.put("k_enter", k_enter);
        kbrdMap.put("k_l_shift", k_l_shift);
        kbrdMap.put("k_z", k_z);
        kbrdMap.put("k_x", k_x);
        kbrdMap.put("k_c", k_c);
        kbrdMap.put("k_v", k_v);
        kbrdMap.put("k_b", k_b);
        kbrdMap.put("k_n", k_n);
        kbrdMap.put("k_m", k_m);
        kbrdMap.put("k_comma", k_comma);
        kbrdMap.put("k_dot", k_dot);
        kbrdMap.put("k_shash", k_shash);
        kbrdMap.put("k_r_shift", k_r_shift);
        kbrdMap.put("k_l_ctrl", k_l_ctrl);
        kbrdMap.put("k_win", k_win);
        kbrdMap.put("k_l_alt", k_l_alt);
        kbrdMap.put("k_space", k_space);
        kbrdMap.put("k_r_alt", k_r_alt);
        kbrdMap.put("k_fn", k_fn);
        kbrdMap.put("k_menu", k_menu);
        kbrdMap.put("k_r_ctrl", k_r_ctrl);
        kbrdMap.put("k_prtscr", k_prtscr);
        kbrdMap.put("k_scrl", k_scrl);
        kbrdMap.put("k_pause", k_pause);
        kbrdMap.put("k_tilde", k_tilde);
        kbrdMap.put("k_ins", k_ins);
        kbrdMap.put("k_home", k_home);
        kbrdMap.put("k_pg_up", k_pg_up);
        kbrdMap.put("k_del", k_del);
        kbrdMap.put("k_pg_dn", k_pg_dn);
        kbrdMap.put("k_arr_up", k_arr_up);
        kbrdMap.put("k_arr_down", k_arr_down);
        kbrdMap.put("k_arr_left", k_arr_left);
        kbrdMap.put("k_arr_right", k_arr_right);
        kbrdMap.put("k_end", k_end);
        kbrdMap.put("k_num", k_num);
        kbrdMap.put("k_num_slash", k_num_slash);
        kbrdMap.put("k_num_asterisk", k_num_asterisk);
        kbrdMap.put("k_num_minus", k_num_minus);
        kbrdMap.put("k_num_9", k_num_9);
        kbrdMap.put("k_num_8", k_num_8);
        kbrdMap.put("k_num_7", k_num_7);
        kbrdMap.put("k_num_6", k_num_6);
        kbrdMap.put("k_num_5", k_num_5);
        kbrdMap.put("k_num_4", k_num_4);
        kbrdMap.put("k_num_3", k_num_3);
        kbrdMap.put("k_num_2", k_num_2);
        kbrdMap.put("k_num_1", k_num_1);
        kbrdMap.put("k_num_0", k_num_0);
        kbrdMap.put("k_num_period", k_num_period);
        kbrdMap.put("k_num_enter", k_num_enter);
        kbrdMap.put("k_num_plus", k_num_plus);

        resetBtn.setOnAction(ActionEvent -> {
            for (String keyID : keySet)
                kbrdMap.get(keyID).setSelected(false);
            keySet.clear();
        });
    }

    @FXML
    private void toggleBntAction(ActionEvent event) {
        ToggleButton btn = (ToggleButton) event.getSource();
        if (btn.isSelected())
            keySet.add(btn.getId());
        else
            keySet.remove(btn.getId());
    }

    public List<Byte> getKeys() {
        List<Byte> listCodes = new ArrayList<>();
        for (String keyId : keySet)
            listCodes.add(LoCodepage.valueOf(keyId).getValue());

        return listCodes;
    }
    /**
     * Get set of keys disabled in game mode. Would be stored in config file.
     * */
    public List<String> getInternalKeySet(){
        return keySet;
    }
    /**
     * Restore from config file.
     * */
    public void setConfig(List<String> keySet){
        resetBtn.fire();
        this.keySet = keySet;
        for (String keyID : keySet)
            ((ToggleButton) resetBtn.getScene().lookup("#"+keyID)).setSelected(true);
    }
}