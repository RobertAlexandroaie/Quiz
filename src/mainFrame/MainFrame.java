/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mainFrame;

import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Logger;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import model.Quiz;
import doTestDialog.DoTestDialog;
import editTestDialog.EditTestDialog;

/**
 *
 * @author Robert
 */
public final class MainFrame extends JFrame implements ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Quiz model;
    private JButton browseButton;
    private JButton doButton;
    private JButton editButton;
    private JButton saveButton;
    private JButton addButton;
    private JPanel buttonsPanel;
    private JLabel quizTitleLabel;
    private JLabel quizDescriptionLabel;
    private JTextField quizTitleTextField;
    private JTextArea quizDescriptionTextArea;
    private JScrollPane quizDescriptionScrollPane;
    private JPanel modelPanel;

    public MainFrame() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(this.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        SwingUtilities.updateComponentTreeUI(this.getContentPane());
        initMainFrame(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object sourceButton = e.getSource();
        final MainFrame current = this;

        if (sourceButton instanceof JButton) {
            JButton button = (JButton) sourceButton;

            if (button == doButton) {
                if (model != null) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            DoTestDialog doTestDialog = new DoTestDialog(current,
                                    true, model);
                            doTestDialog.setVisible(true);
                        }
                    });
                } else {
                    JOptionPane optionPane = new JOptionPane();
                    showMessageDialog(this, "There is no quiz to resolve", "ERROR!", JOptionPane.ERROR_MESSAGE);
                }
            } else if (button == editButton) {
                if (model != null) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            EditTestDialog editTestDialog = new EditTestDialog(
                                    current, true, model);
                            editTestDialog.setVisible(true);
                        }
                    });
                } else {
                    JOptionPane optionPane = new JOptionPane();
                    showMessageDialog(this, "There is no quiz to edit", "ERROR!", JOptionPane.ERROR_MESSAGE);
                }

            } else if (button == browseButton) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
                fileChooser.setFileFilter(new XMLFilter());
                int option = fileChooser.showOpenDialog(this);
                if (option == JFileChooser.APPROVE_OPTION) {
                    if (fileChooser.getSelectedFile() != null) {
                        File xmlFile = new File(fileChooser.getSelectedFile().getAbsolutePath());
                        getContentPane().removeAll();
                        initMainFrame(xmlFile.getAbsolutePath());
                    }
                }
                if (option == JFileChooser.CANCEL_OPTION) {
                    System.out.println("You canceled.");
                }
            } else if (button == saveButton) {

                if (model != null) {
                    model.setName(quizTitleTextField.getText());
                    model.setDescription(quizDescriptionTextArea.getText());
                    model.save();
                } else {
                    JOptionPane optionPane = new JOptionPane();
                    showMessageDialog(this, "There is no quiz to save!", "ERROR!", JOptionPane.ERROR_MESSAGE);
                }

            } else if (button == addButton) {
                getContentPane().removeAll();
                initMainFrame(null);
            }
        }
    }

    public void initButtons() {
        GridBagConstraints gridBagConstraints;
        buttonsPanel = new JPanel();
        buttonsPanel.setBackground(Color.WHITE);
        buttonsPanel.setLayout(new GridBagLayout());

        Dimension buttonDimensionMin = new Dimension(100, 30);
        Dimension buttonDimensionMax = new Dimension(300, 100);

        doButton = new JButton("RESOLVE");
        doButton.setMinimumSize(buttonDimensionMin);
        doButton.setPreferredSize(buttonDimensionMin);
        doButton.setMaximumSize(buttonDimensionMax);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        buttonsPanel.add(doButton, gridBagConstraints);

        editButton = new JButton("EDIT");
        editButton.setMinimumSize(buttonDimensionMin);
        editButton.setPreferredSize(buttonDimensionMin);
        editButton.setMaximumSize(buttonDimensionMax);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        buttonsPanel.add(editButton, gridBagConstraints);

        saveButton = new JButton("SAVE");
        saveButton.setMinimumSize(buttonDimensionMin);
        saveButton.setPreferredSize(buttonDimensionMin);
        saveButton.setMaximumSize(buttonDimensionMax);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        buttonsPanel.add(saveButton, gridBagConstraints);

        browseButton = new JButton("BROWSE");
        gridBagConstraints = new GridBagConstraints();
        browseButton.setMinimumSize(buttonDimensionMin);
        browseButton.setPreferredSize(buttonDimensionMin);
        browseButton.setMaximumSize(buttonDimensionMax);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        buttonsPanel.add(browseButton, gridBagConstraints);

        addButton = new JButton("ADD");
        gridBagConstraints = new GridBagConstraints();
        addButton.setMinimumSize(buttonDimensionMin);
        addButton.setMaximumSize(buttonDimensionMax);
        addButton.setPreferredSize(new Dimension(200, 30));
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        buttonsPanel.add(addButton, gridBagConstraints);

        doButton.addActionListener(this);
        editButton.addActionListener(this);
        browseButton.addActionListener(this);
        saveButton.addActionListener(this);
        addButton.addActionListener(this);
    }

    public void initMainFrame(String xmlFilePath) {
        initModel(xmlFilePath);
        initButtons();

        setTitle("QuizApp");
        this.setResizable(true);
        setLocation(new Point(400, 100));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        GroupLayout mainLayout = new GroupLayout(getContentPane());
        getContentPane().setLayout(mainLayout);

        mainLayout.setHorizontalGroup(mainLayout.createParallelGroup()
                .addGroup(mainLayout.createSequentialGroup()
                .addContainerGap(0, 50)
                .addComponent(modelPanel)
                .addContainerGap(0, 50))
                .addGroup(mainLayout.createSequentialGroup()
                .addComponent(buttonsPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)));
        mainLayout.setVerticalGroup(mainLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addGroup(mainLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(modelPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, 600)
                .addGap(10, 10, 10)
                .addComponent(buttonsPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap()));
        setMinimumSize(new Dimension(240, 190));
        pack();
    }

    public void initModel(String filePath) {
        quizTitleLabel = new JLabel("Quiz title:");
        quizDescriptionLabel = new JLabel("Quiz description:");

        if (filePath != null) {
            model = new Quiz(filePath);
            quizTitleTextField = new JTextField(model.getName());
            quizDescriptionTextArea = new JTextArea(model.getDescription());

            quizDescriptionScrollPane = new JScrollPane();
            quizDescriptionScrollPane.setViewportView(quizDescriptionTextArea);

            modelPanel = new JPanel();


        } else {
            model = null;
            quizTitleTextField = new JTextField();
            quizDescriptionTextArea = new JTextArea();

            quizDescriptionScrollPane = new JScrollPane();
            quizDescriptionScrollPane.setViewportView(quizDescriptionTextArea);

            modelPanel = new JPanel();
        }
        GroupLayout modelPanelLayout = new GroupLayout(modelPanel);
        modelPanel.setLayout(modelPanelLayout);

        modelPanelLayout.setHorizontalGroup(modelPanelLayout.createParallelGroup()
                .addGroup(modelPanelLayout.createSequentialGroup()
                .addComponent(quizTitleLabel))
                .addGroup(modelPanelLayout.createSequentialGroup()
                .addComponent(quizTitleTextField))
                .addGroup(modelPanelLayout.createSequentialGroup()
                .addComponent(quizDescriptionLabel))
                .addGroup(modelPanelLayout.createSequentialGroup()
                .addComponent(quizDescriptionScrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)));

        modelPanelLayout.setVerticalGroup(modelPanelLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addGroup(modelPanelLayout.createSequentialGroup()
                .addComponent(quizTitleLabel)
                .addComponent(quizTitleTextField, 20, 20, 30)
                .addComponent(quizDescriptionLabel)
                .addComponent(quizDescriptionScrollPane)));
    }

    public void getTitleForModel() {
        JOptionPane optionPane = new JOptionPane("Set quiz title!", JOptionPane.WARNING_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
        model.setName(JOptionPane.showInputDialog("Set quiz title!"));
    }
}
