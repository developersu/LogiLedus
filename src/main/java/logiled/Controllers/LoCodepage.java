package logiled.Controllers;

class LoCodepage {
    static byte getCode(String id){
        switch (id){
            case "l_game":
                return 0x2;
            case "l_caps":
                return 0x3;
            case "k_a":
                return 0x4;
            case "k_b":
                return 0x5;
            case "k_c":
                return 0x6;
            case "k_d":
                return 0x7;
            case "k_e":
                return 0x8;
            case "k_f":
                return 0x9;
            case "k_g":
                return 0xA;
            case "k_h":
                return 0xB;
            case "k_i":
                return 0xC;
            case "k_j":
                return 0xD;
            case "k_k":
                return 0xE;
            case "k_l":
                return 0xF;
            case "k_m":
                return 0x10;
            case "k_n":
                return 0x11;
            case "k_o":
                return 0x12;
            case "k_p":
                return 0x13;
            case "k_q":
                return 0x14;
            case "k_r":
                return 0x15;
            case "k_s":
                return 0x16;
            case "k_t":
                return 0x17;
            case "k_u":
                return 0x18;
            case "k_v":
                return 0x19;
            case "k_w":
                return 0x1A;
            case "k_x":
                return 0x1B;
            case "k_y":
                return 0x1C;
            case "k_z":
                return 0x1D;
            case "k_1":
                return 0x1E;
            case "k_2":
                return 0x1F;
            case "k_3":
                return 0x20;
            case "k_4":
                return 0x21;
            case "k_5":
                return 0x22;
            case "k_6":
                return 0x23;
            case "k_7":
                return 0x24;
            case "k_8":
                return 0x25;
            case "k_9":
                return 0x26;
            case "k_0":
                return 0x27;
            case "k_enter":
                return 0x28;
            case "k_esc":
                return 0x29;
            case "k_backspace":
                return 0x2a;
            case "k_tab":
                return 0x2b;
            case "k_space":
                return 0x2c;
            case "k_dash":
                return 0x2d;
            case "k_equal":
                return 0x2e;
            case "k_bracket_open":
                return 0x2f;
            case "k_bracket_close":
                return 0x30;
            // nothing for 0x31
            case "k_backslash":
                return 0x32;        // (ISO version only?)
            case "k_semicolon":
                return 0x33;
            case "k_quotation":
                return 0x34;
            case "k_tilde":
                return 0x35;
            case "k_comma":
                return 0x36;
            case "k_dot":
                return 0x37;
            case "k_shash":
                return 0x38;
            case "k_caps":
                return 0x39;
            case "k_f1":
                return 0x3a;
            case "k_f2":
                return 0x3b;
            case "k_f3":
                return 0x3c;
            case "k_f4":
                return 0x3d;
            case "k_f5":
                return 0x3e;
            case "k_f6":
                return 0x3f;
            case "k_f7":
                return 0x40;
            case "k_f8":
                return 0x41;
            case "k_f9":
                return 0x42;
            case "k_f10":
                return 0x43;
            case "k_f11":
                return 0x44;
            case "k_f12":
                return 0x45;
            case "k_prtscr":
                return 0x46;
            case "k_scrl":
                return 0x47;
            case "k_pause":
                return 0x48;
            case "k_ins":
                return 0x49;
            case "k_home":
                return 0x4a;
            case "k_pg_up":
                return 0x4b;
            case "k_del":
                return 0x4c;
            case "k_end":
                return 0x4d;
            case "k_pg_dn":
                return 0x4e;
            case "k_arr_right":
                return 0x4f;
            case "k_arr_left":
                return 0x50;
            case "k_arr_down":
                return 0x51;
            case "k_arr_up":
                return 0x52;
            case "k_num":
                return 0x53;
            case "k_num_slash":
                return 0x54;
            case "k_num_asterisk":
                return 0x55;
            case "k_num_minus":
                return 0x56;
            case "k_num_plus":
                return 0x57;
                                                                        /* not confirmed */
            case "k_num_enter":
                return 0x58;
            case "k_num_1":
                return 0x59;
            case "k_num_2":
                return 0x5a;
            case "k_num_3":
                return 0x5b;
            case "k_num_4":
                return 0x5c;
            case "k_num_5":
                return 0x5d;
            case "k_num_6":
                return 0x5e;
            case "k_num_7":
                return 0x5f;
            case "k_num_8":
                return 0x60;
            case "k_num_9":
                return 0x61;
            case "k_num_0":
                return 0x62;
            case "k_num_period":
                return 0x63;
                                                                        /*---------------*/
            // nothing for 0x64 (ISO version)
            case "k_menu":
                return 0x65;

            case "k_l_ctrl":
                return (byte) 0xe0;
            case "k_l_shift":
                return (byte) 0xe1;
            case "k_l_alt":
                return (byte) 0xe2;
            case "k_win":
                return (byte) 0xe3;
            case "k_r_ctrl":
                return (byte) 0xe4;
            case "k_r_shift":
                return (byte) 0xe5;
            case "k_r_alt":
                return (byte) 0xe6;
            case "k_fn":
                return (byte) 0xe7;
            default:
                return 0x00;    // ???
        }
    }
}
