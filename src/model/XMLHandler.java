/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.File;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Robert
 */
public class XMLHandler {

    private Quiz model;

    public void associateModel(Quiz model) {
        this.model = model;
    }

    public void handleAnswer(NodeList answer, Question question) {
        int answerNumber = answer.getLength();
        int answerCounter;

        for (answerCounter = 0; answerCounter < answerNumber; answerCounter++) {
            Node answerNode = answer.item(answerCounter);
            if (answerNode.getNodeType() == Node.ELEMENT_NODE) {
                Element answerElement = (Element) answerNode;

                for (Option it : Option.values()) {
                    if (it.toString().compareTo(
                            answerElement.getTextContent().trim()) == 0) {
                        question.setAnswer(it);
                    }
                }
            }
        }
    }

    public void handleAnswers(NodeList answers, Question question) {
        int answersNumber = answers.getLength();
        int answersCounter;

        for (answersCounter = 0; answersCounter < answersNumber; answersCounter++) {
            Node answersNode = answers.item(answersCounter);
            if (answersNode.getNodeType() == Node.ELEMENT_NODE) {
                Element answersElement = (Element) answersNode;

                NodeList answer = answersElement.getElementsByTagName("answer");
                handleAnswer(answer, question);
            }
        }
    }

    public void handleCorrectAnswers(NodeList correctAnswers, Question question) {
        int answersNumber = correctAnswers.getLength();
        int answersCounter;

        for (answersCounter = 0; answersCounter < answersNumber; answersCounter++) {
            Node answersNode = correctAnswers.item(answersCounter);
            if (answersNode.getNodeType() == Node.ELEMENT_NODE) {
                Element answersElement = (Element) answersNode;

                String numberString = answersElement.getTextContent();
                int number = 0;
                if (numberString != null && numberString.compareTo("") != 0) {
                    number = Integer.parseInt(numberString.toString());
                    number ^= 0x0011;
                }

                question.setRightAnswersByColectiveValue(number);
            }
        }
    }

    public void handleOption(NodeList option, Question question) {
        int optionNumber = option.getLength();
        int optionCounter;

        for (optionCounter = 0; optionCounter < optionNumber; optionCounter++) {
            Node optionNode = option.item(optionCounter);
            if (optionNode.getNodeType() == Node.ELEMENT_NODE) {
                Element optionElement = (Element) optionNode;
                question.getOptions().add(optionElement.getTextContent());
            }
        }
    }

    public void handleOptions(NodeList options, Question question) {
        int optionsSize = options.getLength();
        int optionsContor;

        for (optionsContor = 0; optionsContor < optionsSize; optionsContor++) {
            Node optionsNode = options.item(optionsContor);
            if (optionsNode.getNodeType() == Node.ELEMENT_NODE) {
                Element optionsElement = (Element) optionsNode;

                NodeList option = optionsElement.getElementsByTagName("option");
                handleOption(option, question);
            }
        }
    }

    public void handleQuestions(NodeList nodeList) {
        int questionsNumber = nodeList.getLength();
        int questionContor;

        for (questionContor = 0; questionContor < questionsNumber; questionContor++) {
            Node question = nodeList.item(questionContor);
            if (question.getNodeType() == Node.ELEMENT_NODE) {

                Question newQuestion = new Question();

                Element questionElement = (Element) question;

                NodeList taskList = questionElement
                        .getElementsByTagName("task");
                handleTask(taskList, newQuestion);

                NodeList sourceCodeList = questionElement
                        .getElementsByTagName("sourceCode");
                handleSourceCode(sourceCodeList, newQuestion);

                NodeList options = questionElement
                        .getElementsByTagName("options");
                handleOptions(options, newQuestion);

                NodeList correctAnswers = questionElement
                        .getElementsByTagName("correctAnswers");
                handleCorrectAnswers(correctAnswers, newQuestion);

                NodeList answers = questionElement
                        .getElementsByTagName("answers");
                handleAnswers(answers, newQuestion);

                boolean isCorect = false;
                if (questionElement.getElementsByTagName("isCorrect").item(0)
                        .getTextContent().compareTo("true") == 0) {
                    isCorect = true;
                }

                boolean isCompilable = false;
                if (questionElement.getElementsByTagName("isCompilable")
                        .item(0).getTextContent().compareTo("true") == 0) {
                    isCompilable = true;
                }

                newQuestion.setAnswered(isCorect);
                newQuestion.setCompilable(isCompilable);
                model.getQuestionaire().add(newQuestion);
            }
        }
    }

    public void handleQuiz(Document document) {

        NodeList quizTitle = document.getElementsByTagName("quizTitle");

        int quizTitleSize = quizTitle.getLength();
        int quizTitleContor;

        for (quizTitleContor = 0; quizTitleContor < quizTitleSize; quizTitleContor++) {
            Node quizTitleNode = quizTitle.item(quizTitleContor);
            if (quizTitleNode.getNodeType() == Node.ELEMENT_NODE) {
                Element quizTitleElement = (Element) quizTitleNode;
                model.setName(quizTitleElement.getTextContent());
                model.setTasksPath("tasks\\" + model.getName() + "\\");
                model.setSourcesPath("sources\\");
            }
        }

        NodeList quizDescription = document.getElementsByTagName("quizDescription");
        int quizDescriptionSize = quizDescription.getLength();
        int quizDescriptionContor;

        for (quizDescriptionContor = 0; quizDescriptionContor < quizDescriptionSize; quizDescriptionContor++) {
            Node quizDescriptionNode = quizDescription.item(quizDescriptionContor);
            if (quizDescriptionNode.getNodeType() == Node.ELEMENT_NODE) {
                Element quizDescriptionElement = (Element) quizDescriptionNode;
                model.setDescription(quizDescriptionElement.getTextContent());
            }
        }

        NodeList questionList = document.getElementsByTagName("question");
        handleQuestions(questionList);
    }

    public void handleSourceCode(NodeList sourceCodeList, Question question) {
        int sourceNumber = sourceCodeList.getLength();
        int sourceContor;

        for (sourceContor = 0; sourceContor < sourceNumber; sourceContor++) {
            Node sourceNode = sourceCodeList.item(sourceContor);
            if (sourceNode.getNodeType() == Node.ELEMENT_NODE) {
                Element sourceElement = (Element) sourceNode;
                question.setSourceCodePath(sourceElement.getTextContent().trim());
                if (question.getSourceCodePath() != null && question.getSourceCodePath().compareTo("") != 0) {
                    question.setSourceCodeByFile(new File(model.getSourcesPath() + question.getSourceCodePath()));
                }
            }
        }
    }

    public void handleTask(NodeList taskList, Question question) {
        int taskNumber = taskList.getLength();
        int taskContor;

        for (taskContor = 0; taskContor < taskNumber; taskContor++) {
            Node taskNode = taskList.item(taskContor);
            if (taskNode.getNodeType() == Node.ELEMENT_NODE) {
                Element taskElement = (Element) taskNode;
                question.setTaskPath(taskElement.getTextContent());
                question.setTaskByFile(new File(model.getTasksPath() + question.getTaskPath()));
            }
        }
    }
}
