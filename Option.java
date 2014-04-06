/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
fgfg dsgdfg
 * @author Robert
 */
public enum Optin {

    A,
    B,
    C,
    D,
    E,
    F,
    G;

    public static Option fromInt(int valoare) {
        switch (valoare) {
            case 0:
                return A;
            case 1:
                return B;
            case 2:
                return C;
            case 3:
                return D;
            case 4:
                return E;
            case 5:
                return F;
            case 6:
                return G;
            default:
                return null;
        }
    }

    public int toInt() {
        switch (this) {
            case A:
                return 0;
            case B:
                return 1;
            case C:
                return 2;
            case D:
                return 3;
            case E:
                return 4;
            case F:
                return 5;
            case G:
                return 6;
            default:
                return -1;
        }
    }
}
