/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package editQuestionDialog;

import editTestDialog.EditTestDialog;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import mainFrame.XMLFilter;
import model.Option;
import static model.Option.fromInt;
import model.Question;

/**
 *
 * @author Robert
 */
public final class EditQuestionDialog extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;
    private ArrayList<JCheckBox> checkBoxes;
    private JLabel taskLabel = new JLabel("Task: ");
    private JLabel sourceCodeLabel = new JLabel("Source code: ");
    private JLabel optionsLabel = new JLabel("Options");
    private JButton setAnswers;
    private JButton addOptions;
    private JButton deleteOptions;
    private JButton compileButton;
    private JButton doneButton;
    private JButton setSourceCodeFileButton;
    private JTextArea task;
    private JTextArea sourceCode;
    private JPanel optionsPanel;
    private JPanel buttonsPanel;
    private JScrollPane sourceCodeScrollPane;
    private JScrollPane taskScrollPane;
    private JScrollPane optionsPanelScrollPane;
    private Question model;

    public EditQuestionDialog(EditTestDialog parent, boolean modal, Question selectedValue) {
        super(parent, modal);

        model = selectedValue;
        initEditQuestionDialog(selectedValue);
    }

    public void initEditQuestionDialog(Question selectedValue) {
        initCheckBoxes(selectedValue);
        initTask(selectedValue);
        initSourceCode(selectedValue);
        initButtons();

        setTitle(selectedValue.getTaskPath() + " Editor");
        this.setResizable(true);
        setLocation(new Point(400, 50));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING, true)
                .addGroup(layout.createSequentialGroup()
                .addComponent(taskLabel))
                .addGroup(layout.createSequentialGroup()
                .addComponent(taskScrollPane, 100, 200, Short.MAX_VALUE))
                .addGroup(layout.createSequentialGroup()
                .addComponent(sourceCodeLabel))
                .addGroup(layout.createSequentialGroup()
                .addComponent(sourceCodeScrollPane, 10, 400, Short.MAX_VALUE))
                .addGroup(layout.createSequentialGroup()
                .addComponent(optionsLabel))
                .addGroup(layout.createSequentialGroup()
                .addComponent(optionsPanelScrollPane, 10, 100, Short.MAX_VALUE))
                .addGroup(layout.createSequentialGroup()
                .addComponent(buttonsPanel, 50, 100, GroupLayout.DEFAULT_SIZE))
                .addGroup(layout.createSequentialGroup()
                .addComponent(setSourceCodeFileButton, 50, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE))
                .addGroup(layout.createSequentialGroup()
                .addComponent(doneButton, 50, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                .addComponent(taskLabel)
                .addComponent(taskScrollPane, 30, 100, 200)
                .addComponent(sourceCodeLabel)
                .addComponent(sourceCodeScrollPane, 100, 300, 400)
                .addComponent(optionsLabel)
                .addComponent(optionsPanelScrollPane, 50, 80, 100)
                .addComponent(buttonsPanel, 50, 80, 100)
                .addComponent(setSourceCodeFileButton)
                .addComponent(doneButton)));
        setMinimumSize(new Dimension(300, 300));
        setPreferredSize(new Dimension(300, 500));
        pack();
    }

    public void initCheckBoxes(Question selectedValue) {
        optionsPanel = new JPanel();
        checkBoxes = new ArrayList<>();
        optionsPanelScrollPane = new JScrollPane();

        for (String it : selectedValue.getOptions()) {
            JCheckBox checkBox = new JCheckBox("<html>" + it + "<html>");
            checkBox.setSelected(selectedValue.getCorrectAnswers().get(
                    selectedValue.getOptions().indexOf(it)));
            checkBoxes.add(checkBox);
            checkBox.setEnabled(false);
        }

        optionsPanel.setLayout(new GridLayout(checkBoxes.size() + 1, 1));
        for (JCheckBox it : checkBoxes) {
            optionsPanel.add(it);
        }
        optionsPanelScrollPane.setViewportView(optionsPanel);
    }

    public void initTask(Question selectedValue) {
        taskScrollPane = new JScrollPane();
        task = new JTextArea(selectedValue.getTask());
        //task.setText(model.taskFromAtt);

        taskScrollPane.setViewportView(task);
        taskScrollPane.setMaximumSize(new Dimension((int) EditQuestionDialog.this.getMaximumSize().getWidth(), 200));
    }

    public void initSourceCode(Question selectedValue) {
        sourceCodeScrollPane = new JScrollPane();
        sourceCode = new JTextArea(selectedValue.getSourceCode());

        sourceCodeScrollPane.setViewportView(sourceCode);
    }

    public void initButtons() {
        buttonsPanel = new JPanel();

        setAnswers = new JButton("Set answers");
        addOptions = new JButton("Add options");
        deleteOptions = new JButton("Delete options");
        compileButton = new JButton("Compile");
        doneButton = new JButton("Done");
        setSourceCodeFileButton = new JButton("Set java source");

        Dimension buttonMinDim = new Dimension(50, 30);
        Dimension buttonMaxDim = new Dimension(100, 60);

        setAnswers.setMinimumSize(buttonMinDim);
        addOptions.setMinimumSize(buttonMinDim);
        deleteOptions.setMinimumSize(buttonMinDim);
        compileButton.setMinimumSize(buttonMinDim);
        doneButton.setMinimumSize(buttonMinDim);
        setSourceCodeFileButton.setMinimumSize(buttonMinDim);

        setAnswers.setMaximumSize(buttonMaxDim);
        addOptions.setMaximumSize(buttonMaxDim);
        deleteOptions.setMaximumSize(buttonMaxDim);
        compileButton.setMaximumSize(buttonMaxDim);
        doneButton.setMaximumSize(buttonMaxDim);
        setSourceCodeFileButton.setMaximumSize(buttonMaxDim);


        setAnswers.addActionListener(this);
        addOptions.addActionListener(this);
        deleteOptions.addActionListener(this);
        compileButton.addActionListener(this);
        setSourceCodeFileButton.addActionListener(this);
        doneButton.addActionListener(this);

        buttonsPanel.setLayout(new GridLayout(2, 2));
        buttonsPanel.setMinimumSize(new Dimension(80, 40));
        buttonsPanel.add(compileButton);
        buttonsPanel.add(setAnswers);
        buttonsPanel.add(addOptions);
        buttonsPanel.add(deleteOptions);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object sourceButton = e.getSource();

        if (sourceButton instanceof JButton) {
            JButton button = (JButton) sourceButton;

            if (button == addOptions) {
                model.setTask(task.getText());
                model.setSourceCode(sourceCode.getText());
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        AddOptionDialog addOptionDialog = new AddOptionDialog(
                                EditQuestionDialog.this, true, model);
                        addOptionDialog.setVisible(true);

                        EditQuestionDialog.this.getContentPane().removeAll();
                        initEditQuestionDialog(model);
                    }
                });
            } else if (button == compileButton) {
                model.setTask(task.getText());
                model.setSourceCode(sourceCode.getText());
                model.tryToCompile();
                if (model.isCompilable()) {
                    JOptionPane.showMessageDialog(this, "Compilat cu succes!");
                } else {
                    JOptionPane.showMessageDialog(this, "Nu se compileaza!");
                    try {
                        Desktop.getDesktop().edit(new File("out.txt"));
                    } catch (IOException ex) {
                        Logger.getLogger(EditQuestionDialog.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                EditQuestionDialog.this.getContentPane().removeAll();
                initEditQuestionDialog(model);
            } else if (button == deleteOptions) {
                model.setTask(task.getText());
                model.setSourceCode(sourceCode.getText());
                switch (button.getText()) {
                    case "Delete options":
                        task.setEnabled(false);
                        sourceCode.setEditable(false);
                        compileButton.setEnabled(false);
                        addOptions.setEnabled(false);
                        doneButton.setEnabled(false);
                        setAnswers.setEnabled(false);

                        for (JCheckBox it : checkBoxes) {
                            it.setSelected(false);
                            it.setEnabled(true);
                        }

                        button.setText("Done");
                        button.revalidate();
                        button.repaint();
                        break;
                    case "Done":
                        task.setEnabled(true);
                        sourceCode.setEnabled(true);
                        compileButton.setEnabled(true);
                        addOptions.setEnabled(true);
                        doneButton.setEnabled(true);
                        setAnswers.setEnabled(true);

                        ArrayList<JCheckBox> newCheckBoxes = new ArrayList<>();

                        for (JCheckBox it : checkBoxes) {
                            if (!it.isSelected()) {
                                newCheckBoxes.add(it);
                            }
                        }

                        for (JCheckBox it : checkBoxes) {
                            if (it.isSelected()) {
                                String checkBoxTitle = it.getText().substring(6,
                                        it.getText().length() - 6);
                                model.removeOption(checkBoxTitle);
                            }
                        }

                        checkBoxes = newCheckBoxes;
                        button.setText("Delete options");
                        EditQuestionDialog.this.getContentPane().removeAll();
                        initEditQuestionDialog(model);
                        break;
                }
            } else if (button == setAnswers) {
                model.setTask(task.getText());
                model.setSourceCode(sourceCode.getText());
                switch (button.getText()) {
                    case "Set answers":
                        task.setEnabled(false);
                        sourceCode.setEditable(false);
                        compileButton.setEnabled(false);
                        addOptions.setEnabled(false);
                        doneButton.setEnabled(false);
                        deleteOptions.setEnabled(false);

                        for (JCheckBox it : checkBoxes) {
                            it.setEnabled(true);
                        }

                        button.setText("Done");
                        button.revalidate();
                        button.repaint();
                        break;
                    case "Done":
                        task.setEnabled(true);
                        sourceCode.setEnabled(true);
                        compileButton.setEnabled(true);
                        addOptions.setEnabled(true);
                        doneButton.setEnabled(true);
                        deleteOptions.setEnabled(true);

                        ArrayList<Boolean> newCorrectAnswers = new ArrayList<>();
                        for (Option iterator : Option.values()) {
                            newCorrectAnswers.add(iterator.toInt(), false);
                        }

                        model.setCorrectAnswers(newCorrectAnswers);

                        for (JCheckBox it : checkBoxes) {
                            if (it.isSelected()) {
                                model.setCorrectAnswer(fromInt(checkBoxes.indexOf(it)));
                            }
                        }

                        button.setText("Set answers");
                        button.revalidate();
                        button.repaint();
                        EditQuestionDialog.this.getContentPane().removeAll();
                        initEditQuestionDialog(model);

                        break;
                }
            } else if (button == doneButton) {
                model.setTask(task.getText());
                model.setSourceCode(sourceCode.getText());

                for (JCheckBox it : checkBoxes) {
                    if (it.isSelected()) {
                        model.getCorrectAnswers().set(checkBoxes.indexOf(it), true);
                    } else {
                        model.getCorrectAnswers().set(checkBoxes.indexOf(it), false);
                    }
                }
                dispose();
            } else if (button == setSourceCodeFileButton) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
                fileChooser.setFileFilter(new JavaFilter());
                int option = fileChooser.showOpenDialog(this);
                if (option == JFileChooser.APPROVE_OPTION) {
                    if (fileChooser.getSelectedFile() != null) {
                        File javaFile = new File(fileChooser.getSelectedFile().getAbsolutePath());

                        StringTokenizer tokenizer = new StringTokenizer(javaFile.getAbsolutePath(), "\\");
                        String relativePath = "";

                        while (tokenizer.hasMoreTokens()) {
                            relativePath = tokenizer.nextToken();
                        }

                        model.setSourceCodePath(relativePath);
                        model.setSourceCodeByFile(javaFile);
                        getContentPane().removeAll();
                        this.initEditQuestionDialog(model);
                    }
                }
                if (option == JFileChooser.CANCEL_OPTION) {
                    System.out.println("You canceled.");
                }
            }
        }
    }

    public ArrayList<JCheckBox> getCheckBoxes() {
        return checkBoxes;
    }

    public void setCheckBoxes(ArrayList<JCheckBox> checkBoxes) {
        this.checkBoxes = checkBoxes;
    }
}
