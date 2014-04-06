/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package editQuestionDialog;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import model.Question;

/**
 *
 * @author Robert
 */
public class AddOptionDialog
        extends JDialog
        implements ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JLabel textLabel = new JLabel("Nume raspuns:");
    private JTextField text;
    private JButton okButton;
    private JButton cancelButton;
    private JPanel buttonPanel;
    private Question model;

    public AddOptionDialog(EditQuestionDialog parent, boolean modal, Question model) {
        super(parent, modal);

        this.model = model;
        initAddOptionDialog(parent);
    }

    public final void initAddOptionDialog(EditQuestionDialog parent) {
        initButtons();
        initText();

        setTitle("Add option");
        this.setResizable(true);
        setLocation(new Point(400, 100));

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));
        buttonPanel.setMinimumSize(new Dimension(80, 40));
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING, true)
                .addGroup(layout.createSequentialGroup()
                .addComponent(textLabel))
                .addGroup(layout.createSequentialGroup()
                .addComponent(text, 50, 300, GroupLayout.DEFAULT_SIZE))
                .addGroup(layout.createSequentialGroup()
                .addComponent(buttonPanel, 200, 250, GroupLayout.DEFAULT_SIZE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING, true)
                .addGroup(layout.createSequentialGroup()
                .addComponent(textLabel)
                .addComponent(text, 10, 20, 30)
                .addComponent(buttonPanel)));
        setMinimumSize(new Dimension(300, 150));
        pack();
    }

    public void initButtons() {
        okButton = new JButton("OK");
        cancelButton = new JButton("CANCEL");

        Dimension buttonMinDim = new Dimension(20, 10);
        Dimension buttonMaxDim = new Dimension(40, 20);

        okButton.setMinimumSize(buttonMinDim);
        cancelButton.setMinimumSize(buttonMinDim);

        okButton.setMaximumSize(buttonMaxDim);
        cancelButton.setMaximumSize(buttonMaxDim);

        okButton.addActionListener(this);
        cancelButton.addActionListener(this);
    }

    public void initText() {
        text = new JTextField();
        text.setToolTipText("Introduceti numele optiunii");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object sourceButton = e.getSource();

        if (sourceButton instanceof JButton) {
            JButton button = (JButton) sourceButton;

            if (button == okButton) {
                model.getOptions().add(text.getText());
                dispose();
            } else if (button == cancelButton) {
                dispose();
            }
        }
    }
}
