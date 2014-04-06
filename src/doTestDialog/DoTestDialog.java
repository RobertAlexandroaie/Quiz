/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package doTestDialog;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import model.Question;
import model.Quiz;

/**
 *
 * @author Robert
 */
public class DoTestDialog
        extends JDialog
        implements ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private ArrayList<AbstractPanel> panels;
    private JPanel mainPanel;
    private JScrollPane mainPanelScroll;
    private JButton doneButton;

    public DoTestDialog(JFrame parent, boolean modal, Quiz quiz) {
        super(parent, modal);
        initDoTestDialog(quiz);
    }

    public final void initDoTestDialog(Quiz quiz) {
        initPanels(quiz);

        doneButton = new JButton("DONE");
        doneButton.addActionListener(this);

        setTitle(quiz.toString() + " Resolver");
        this.setResizable(true);
        setLocation(new Point(400, 100));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        mainPanel = new JPanel();

        GroupLayout layout = new GroupLayout(mainPanel);
        mainPanel.setLayout(layout);

        GroupLayout.ParallelGroup parGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
        GroupLayout.SequentialGroup seqGroup = layout.createSequentialGroup();
        for (AbstractPanel it : panels) {
            seqGroup.addComponent(it).addGap(10);
            GroupLayout.SequentialGroup newSeqGroup = layout.createSequentialGroup().addComponent(it);
            parGroup.addGroup(newSeqGroup);
        }

        layout.setHorizontalGroup(parGroup);

        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                .addGroup(seqGroup));

        mainPanelScroll = new JScrollPane();
        mainPanelScroll.setViewportView(mainPanel);

        GroupLayout mainLayout = new GroupLayout(getContentPane());
        getContentPane().setLayout(mainLayout);

        mainLayout.setHorizontalGroup(
                mainLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(mainLayout.createSequentialGroup()
                .addComponent(mainPanelScroll))
                .addGroup(mainLayout.createSequentialGroup()
                .addComponent(doneButton, 50, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        mainLayout.setVerticalGroup(
                mainLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(mainLayout.createSequentialGroup()
                .addComponent(mainPanelScroll, 200, 400, 600)
                .addComponent(doneButton, 30, GroupLayout.PREFERRED_SIZE, 50)));
        pack();
    }

    public void initPanels(Quiz quiz) {
        panels = new ArrayList<>();

        for (Question it : quiz.getQuestionaire()) {
            AbstractPanel panel = new FullPanel(it);
            panels.add(panel);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object sourceButton = e.getSource();

        if (sourceButton instanceof JButton) {
            JButton button = (JButton) sourceButton;
            StringBuilder message = new StringBuilder();
            if (button == doneButton) {
                for (AbstractPanel it : panels) {
                    it.doTest();
                    message.append("Question #").append(panels.indexOf(it) + 1).append(" is ");
                    if (it.question.isCorrect()) {
                        message.append("correct!");
                    } else {
                        message.append("incorrect!");
                    }
                    message.append(System.lineSeparator());
                }
                JOptionPane optionPane = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE);
                showMessageDialog(this, message);
                dispose();
            }

        }
    }
}
