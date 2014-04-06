/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package doTestDialog;

import javax.swing.JPanel;
import model.Question;

/**
 *
 * @author Robert
 */
public abstract class AbstractPanel
        extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    protected int position;
    protected Question question;

    public abstract void doTest();
}
