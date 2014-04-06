/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author Robert
 */
public class Quiz {

    private String description;
    private String name;
    private String tasksPath;
    private String sourcesPath;
    private ArrayList<Question> questionaire;
    private XMLHandler XMLHandler;

    public Quiz() {
        questionaire = new ArrayList<>();
        XMLHandler = new XMLHandler();
        XMLHandler.associateModel(this);
    }

    public Quiz(String fileName) {
        questionaire = new ArrayList<>();
        XMLHandler = new XMLHandler();
        XMLHandler.associateModel(this);

        File xmlFile = new File(fileName);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Quiz.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            @SuppressWarnings({"null", "ConstantConditions"})
            Document document = documentBuilder.parse(xmlFile);
            document.getDocumentElement().normalize();
            XMLHandler.handleQuiz(document);
        } catch (SAXException | IOException ex) {
            Logger.getLogger(Quiz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getSourcesPath() {
        return sourcesPath;
    }

    public void setSourcesPath(String sourcesPath) {
        this.sourcesPath = sourcesPath;
    }

    public void addQuestion(Question question) {
        if (questionaire.contains(question)) {
            System.err.println("Aveti deja obiectul in lista!");
        } else {
            questionaire.add(question);
        }
    }

    public void deleteQuestion(int index) {
        if (index > questionaire.size()) {
            System.err.println("Dimensiune prea mare!");
        } else {
            questionaire.remove(index);
        }
    }

    public void deleteQuestion(Question question) {
        if (!questionaire.contains(question)) {
            System.err.println("Obiect inexistent!");
        } else {
            questionaire.remove(question);
        }
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Question> getQuestionaire() {
        return questionaire;
    }

    public void save() {
        String numeFisier = name + ".xml";

        FileWriter fout = null;
        try {
            fout = new FileWriter(new File(numeFisier));

            fout.write("<?xml version=\"1.0\"?>" + System.lineSeparator());
            fout.write("<quiz>" + System.lineSeparator());
            fout.write("\t<quizTitle>");
            fout.write(name);
            fout.write("</quizTitle>" + System.lineSeparator());

            if (description != null && description.compareTo("") != 0) {
                fout.write("\t<quizDescription>");
                fout.write(description);
                fout.write("</quizDescription>" + System.lineSeparator());
            }

            for (Question it : questionaire) {
                it.saveSourceCodeToFile(new File(sourcesPath + it.getSourceCodePath()));
                it.saveTaskToFile(new File(tasksPath + it.getTaskPath()));

                int rightAnswersColectiveValue = 0;
                fout.write("\t<question>" + System.lineSeparator());

                fout.write("\t\t<task>");
                fout.write(it.getTaskPath());
                fout.write("</task>" + System.lineSeparator());

                if (it.getSourceCode() != null && it.getSourceCode().compareTo("") != 0) {
                    fout.write("\t\t<sourceCode>");
                    fout.write(it.getSourceCodePath());
                    fout.write("</sourceCode>" + System.lineSeparator());
                }

                fout.write("\t\t<options>" + System.lineSeparator());
                for (String stringIt : it.getOptions()) {
                    fout.write("\t\t\t<option>");
                    fout.write(stringIt);
                    fout.write("</option>" + System.lineSeparator());
                }
                fout.write("\t\t</options>" + System.lineSeparator());

                fout.write("\t\t<correctAnswers>");
                boolean hasRightAnswers = false;
                for (String stringIt : it.getOptions()) {
                    int rightAnswerIndex = it.getOptions().indexOf(stringIt);
                    if (it.getCorrectAnswers().get(rightAnswerIndex)) {
                        hasRightAnswers = true;
                    }
                }
                if (hasRightAnswers) {
                    for (String stringIt : it.getOptions()) {
                        int rightAnswerIndex = it.getOptions().indexOf(stringIt);
                        if (it.getCorrectAnswers().get(rightAnswerIndex)) {
                            rightAnswersColectiveValue += (1 << rightAnswerIndex);
                        }
                    }
                    String stringToWrite = new Integer(rightAnswersColectiveValue ^ 0x0011).toString();
                    fout.write(stringToWrite);
                }
                fout.write("</correctAnswers>" + System.lineSeparator());

                fout.write("\t\t<answers>" + System.lineSeparator());
                for (String stringIt : it.getOptions()) {
                    if (it.getAnswers().get(it.getOptions().indexOf(stringIt))) {
                        fout.write("\t\t\t<answer>");
                        fout.write('A' + it.getOptions().indexOf(stringIt));
                        fout.write("</answer>" + System.lineSeparator());
                    }
                }
                fout.write("\t\t</answers>" + System.lineSeparator());

                fout.write("\t\t<isCorrect>");
                if (it.isCorrect()) {
                    fout.write("true");
                } else {
                    fout.write("false");
                }
                fout.write("</isCorrect>" + System.lineSeparator());

                fout.write("\t\t<isCompilable>");
                if (it.isCompilable()) {
                    fout.write("true");
                } else {
                    fout.write("false");
                }
                fout.write("</isCompilable>" + System.lineSeparator());
                fout.write("\t</question>" + System.lineSeparator());
            }
            fout.write("</quiz>" + System.lineSeparator());
        } catch (IOException ex) {
            Logger.getLogger(Quiz.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (fout != null) {
                try {
                    fout.close();
                } catch (IOException ex) {
                    Logger.getLogger(Quiz.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuestionaire(ArrayList<Question> questionaire) {
        this.questionaire = questionaire;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getTasksPath() {
        return tasksPath;
    }

    public void setTasksPath(String tasksPath) {
        this.tasksPath = tasksPath;
    }
}
