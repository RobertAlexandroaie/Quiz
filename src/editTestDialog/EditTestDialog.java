/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package editTestDialog;

import editQuestionDialog.EditQuestionDialog;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import model.Question;
import model.Quiz;

/**
 *
 * @author Robert
 */
public final class EditTestDialog
        extends JDialog
        implements ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JList<Question> questionaire;
    private JButton editButton;
    private JButton addButton;
    private JButton deleteButton;
    private JButton doneButton;
    private JScrollPane questionaireScrollPane;
    private JPanel buttonsPanel;
    private Quiz model;

    public EditTestDialog(JFrame parent, boolean modal, Quiz selectedValue) {
        super(parent, modal);

        model = selectedValue;
        initEditTestDialog(selectedValue);
    }

    public void initQuestionaire() {
        questionaireScrollPane = new JScrollPane();
        DefaultListModel<Question> defaultListModel = new DefaultListModel<>();

        for (Question it : model.getQuestionaire()) {
            defaultListModel.addElement(it);
        }

        questionaire = new JList<>();
        questionaire.setModel(defaultListModel);
        questionaireScrollPane.setViewportView(questionaire);
    }

    public void initButtons() {
        GridBagConstraints gridBagConstraints;
        buttonsPanel = new JPanel();
        buttonsPanel.setBackground(Color.WHITE);
        buttonsPanel.setLayout(new GridBagLayout());

        Dimension buttonDimensionMin = new Dimension(100, 30);
        Dimension buttonDimensionMax = new Dimension(300, 100);

        addButton = new JButton("ADD");
        addButton.setMinimumSize(buttonDimensionMin);
        addButton.setPreferredSize(buttonDimensionMin);
        addButton.setMaximumSize(buttonDimensionMax);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        buttonsPanel.add(addButton, gridBagConstraints);

        deleteButton = new JButton("DELETE");
        gridBagConstraints = new GridBagConstraints();
        deleteButton.setMinimumSize(buttonDimensionMin);
        deleteButton.setMaximumSize(buttonDimensionMax);
        deleteButton.setPreferredSize(buttonDimensionMin);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        buttonsPanel.add(deleteButton, gridBagConstraints);

        editButton = new JButton("EDIT");
        editButton.setMinimumSize(buttonDimensionMin);
        editButton.setPreferredSize(new Dimension(200, 30));
        editButton.setMaximumSize(buttonDimensionMax);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        buttonsPanel.add(editButton, gridBagConstraints);

        doneButton = new JButton("DONE");
        doneButton.setMinimumSize(buttonDimensionMin);
        doneButton.setPreferredSize(new Dimension(200, 30));
        doneButton.setMaximumSize(buttonDimensionMax);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        buttonsPanel.add(doneButton, gridBagConstraints);

        editButton.addActionListener(this);
        deleteButton.addActionListener(this);
        addButton.addActionListener(this);
        doneButton.addActionListener(this);
    }

    public void initEditTestDialog(Quiz selectedValue) {
        initQuestionaire();
        initButtons();

        setTitle(selectedValue.toString() + "Editor");
        this.setResizable(true);
        setLocation(new Point(400, 100));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        GroupLayout mainLayout = new GroupLayout(getContentPane());
        getContentPane().setLayout(mainLayout);

        mainLayout.setHorizontalGroup(mainLayout.createParallelGroup()
                .addGroup(mainLayout.createSequentialGroup()
                .addContainerGap(0, 50)
                .addComponent(questionaireScrollPane, GroupLayout.PREFERRED_SIZE, 188, Short.MAX_VALUE)
                .addContainerGap(0, 50))
                .addGroup(mainLayout.createSequentialGroup()
                .addComponent(buttonsPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)));
        mainLayout.setVerticalGroup(mainLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addGroup(mainLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(questionaireScrollPane, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, 600)
                .addGap(10, 10, 10)
                .addComponent(buttonsPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap()));
        setMinimumSize(new Dimension(240, 190));
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object sourceButton = e.getSource();
        final EditTestDialog current = this;

        if (sourceButton instanceof JButton) {
            JButton button = (JButton) sourceButton;

            if (button == editButton) {
                if (questionaire.getSelectedIndex() != -1) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            EditQuestionDialog editTestDialog = new EditQuestionDialog(current, true, questionaire
                                    .getSelectedValue());
                            editTestDialog.setVisible(true);
                        }
                    });
                }
            } else if (button == deleteButton) {
                if (questionaire.getSelectedIndex() != -1) {
                    DefaultListModel<Question> defaultListModel = (DefaultListModel<Question>) questionaire.getModel();
                    model.getQuestionaire().remove(questionaire.getSelectedIndex());
                    defaultListModel.remove(questionaire.getSelectedIndex());
                }
            } else if (button == addButton) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        Question newQuestion = new Question();
                        EditQuestionDialog editTestDialog = new EditQuestionDialog(current, true, newQuestion);
                        editTestDialog.setVisible(true);
                        EditTestDialog.this.model.addQuestion(newQuestion);

                        DefaultListModel<Question> defaultListModel = (DefaultListModel<Question>) questionaire.getModel();
                        defaultListModel.addElement(newQuestion);
                    }
                });
            } else if (button == doneButton) {
                dispose();
            }
        }
    }
}
