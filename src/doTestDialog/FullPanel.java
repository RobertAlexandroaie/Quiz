/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package doTestDialog;

import com.java2html.BadOptionException;
import com.java2html.Java2HTML;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import model.Option;
import static model.Option.fromInt;
import model.Question;

/**
 *
 * @author Robert
 */
public class FullPanel
        extends AbstractPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private ArrayList<JCheckBox> checkBoxes;
    private JLabel taskLabel = new JLabel("Task: ");
    private JLabel sourceCodeLabel = new JLabel("Source code: ");
    private JLabel optionsLabel = new JLabel("Options");
    private JLabel task;
    private JLabel sourceCode;
    private JPanel optionsPanel;
    private JScrollPane sourceCodeScrollPane;
    private JScrollPane taskScrollPane;

    public FullPanel(Question question) {
        this.question = question;
        initFullPanel(question);
    }

    public final void initFullPanel(Question question) {
        initLabels(question);
        initCheckBoxes(question);

        optionsPanel = new JPanel();

        taskScrollPane = new JScrollPane();
        sourceCodeScrollPane = new JScrollPane();

        taskScrollPane.setViewportView(task);
        taskScrollPane.setMaximumSize(new Dimension((int) FullPanel.this.getMaximumSize().getWidth(), 200));

        sourceCodeScrollPane.setViewportView(sourceCode);
        sourceCodeScrollPane.setMaximumSize(new Dimension((int) FullPanel.this.getMaximumSize().getWidth(), 400));


        optionsPanel.setLayout(new GridLayout(checkBoxes.size() + 1, 1));
        optionsPanel.add(optionsLabel);

        for (JCheckBox it : checkBoxes) {
            optionsPanel.add(it);
        }

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING, true)
                .addGroup(layout.createSequentialGroup()
                .addComponent(taskLabel))
                .addGroup(layout.createSequentialGroup()
                .addComponent(taskScrollPane, 200, 400, Short.MAX_VALUE))
                .addGroup(layout.createSequentialGroup()
                .addComponent(sourceCodeLabel))
                .addGroup(layout.createSequentialGroup()
                .addComponent(sourceCodeScrollPane, 200, 400, Short.MAX_VALUE))
                .addGroup(layout.createSequentialGroup()
                .addComponent(optionsPanel)));
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING, true)
                .addGroup(layout.createSequentialGroup()
                .addComponent(taskLabel)
                .addComponent(taskScrollPane, 20, GroupLayout.PREFERRED_SIZE, 200)
                .addComponent(sourceCodeLabel)
                .addComponent(sourceCodeScrollPane, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                .addComponent(optionsPanel)));
    }

    public void initLabels(Question question) {
        task = new JLabel(question.getTask());
        String javaSourceCode = question.getSourceCode();
        Java2HTML java2HTML = new Java2HTML();

        if (question.getSourceCodePath() != null && question.getSourceCodePath().compareTo("") != 0) {
            try {
                java2HTML.setTitle("Demonstrates use of the Java2HTML API");
                java2HTML.setDestination("java2html");
                java2HTML.setMarginSize(4);
                java2HTML.setTabSize(4);
                java2HTML.setFooter(true);
                java2HTML.setFooter(false);

                // Specifies Java Sources
                String[] javaSources = new String[]{"sources"};
                java2HTML.setJavaDirectorySource(javaSources);

                // Generate Java2HTML output to D:\\Dropbox\\Developing\\Java\\[TAP]Quiz\\sources Directory
                java2HTML.buildJava2HTML();
            } catch (BadOptionException e) {
                System.err.println("Bad Option: " + e.getMessage());
            } catch (Exception ex) {
                Logger.getLogger(this.getName()).log(Level.SEVERE, null, ex);
            }
            String sourceFileName = question.getSourceCodePath() + ".html";
            question.setHTMLSourceCodeByFile(new File("java2html\\model\\" + sourceFileName));
            String htmlSourceCode = question.getSourceCode();
            question.setSourceCode(javaSourceCode);
            sourceCode = new JLabel(htmlSourceCode);
        } else {
            sourceCode = new JLabel();
        }
    }

    public void initCheckBoxes(Question question) {
        checkBoxes = new ArrayList<>();

        for (String it : question.getOptions()) {
            JCheckBox checkBox = new JCheckBox("<html>" + it + "<html>");
            checkBoxes.add(checkBox);
        }
    }

    @Override
    public void doTest() {
        for (JCheckBox it : checkBoxes) {
            if (it.isSelected()) {
                question.setAnswer(fromInt(checkBoxes.indexOf(it)));
            }
        }
    }
}
