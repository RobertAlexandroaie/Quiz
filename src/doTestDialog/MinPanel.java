/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package doTestDialog;

import java.awt.Dimension;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import model.Question;

/**
 *
 * @author Robert
 */
public class MinPanel
        extends AbstractPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JLabel taskLabel;
    private JTextArea task;
    private JScrollPane taskScrollPane;

    public MinPanel(Question question) {
        this.question = question;
        initMinPanel();
    }

    public final void initMinPanel() {
        taskLabel = new JLabel((position + " Task:").toString());
        task = new JTextArea(question.getTask());
        task.setEditable(false);
        taskScrollPane = new JScrollPane();
        taskScrollPane.setViewportView(task);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING, true)
                .addGroup(layout.createSequentialGroup()
                .addComponent(taskLabel))
                .addGroup(layout.createSequentialGroup()
                .addComponent(taskScrollPane, 100, 200, GroupLayout.DEFAULT_SIZE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING, true)
                .addGroup(layout.createSequentialGroup()
                .addComponent(taskLabel)
                .addComponent(taskScrollPane, 50, 100, GroupLayout.PREFERRED_SIZE)));

        this.setMinimumSize(new Dimension(300, 200));
        this.setPreferredSize(new Dimension(350, 250));
    }

    @Override
    public void doTest() {
    }
}
