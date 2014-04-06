/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package model;

import static model.Option.fromInt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Robert
 */
public class Question {

    private String task;
    private String taskPath;
    private String sourceCode;
    private String sourceCodePath;
    private ArrayList<Boolean> correctAnswers;
    private ArrayList<Boolean> answers;
    private ArrayList<String> options;
    private boolean answered = false;
    private boolean compilable = false;

    public Question() {
	correctAnswers = new ArrayList<>();
	answers = new ArrayList<>();
	options = new ArrayList<>();

	for (Option iterator : Option.values()) {
	    answers.add(iterator.toInt(), false);
	    correctAnswers.add(iterator.toInt(), false);
	}
    }

    public String getTask() {
	return task;
    }

    public void setTask(String task) {
	this.task = task;
    }

    public String getSourceCode() {
	return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
	this.sourceCode = sourceCode;
    }

    public ArrayList<Boolean> getAnswers() {
	return answers;
    }

    public void setAnswers(ArrayList<Boolean> answers) {
	this.answers = answers;
    }

    public ArrayList<String> getOptions() {
	return options;
    }

    public void setOptions(ArrayList<String> options) {
	this.options = options;
    }

    public void setAnswer(Option optiune) {
	if (optiune.toInt() >= 0) {
	    answers.set(optiune.toInt(), true);
	}
    }

    public void setCorrectAnswer(Option optiune) {
	if (optiune.toInt() > options.size() && optiune.toInt() < 0) {
	    System.err.println("Optiune invalida!");
	    Logger.getLogger(Question.class.getName()).log(Level.INFO, "message");
	} else {
	    if (optiune.toInt() >= 0) {
		correctAnswers.set(optiune.toInt(), true);
	    }
	}
    }

    public boolean getAnswered() {
	return answered;
    }

    public void setAnswered(boolean answered) {
	this.answered = answered;
    }

    public ArrayList<Boolean> getCorrectAnswers() {
	return correctAnswers;
    }

    public void setCorrectAnswers(ArrayList<Boolean> correctAnswers) {
	this.correctAnswers = correctAnswers;
    }

    public boolean isCorrect() {
	for (String it : options) {
	    if (correctAnswers.get(options.indexOf(it)) != answers.get(options.indexOf(it))) {
		return false;
	    }
	}
	return true;
    }

    public void removeOption(String optionTitle) {
	ArrayList<String> optionsBackup = new ArrayList<>(options);
	for (String it : optionsBackup) {
	    if (it.compareTo(optionTitle) == 0) {
		options.remove(optionsBackup.indexOf(it));
	    }
	}
    }

    @Override
    public String toString() {
	return task;
    }

    /**
     * @return the compilable
     */
    public boolean isCompilable() {
	return compilable;
    }

    /**
     * @param compilable
     *            the compilable to set
     */
    public void setCompilable(boolean compilable) {
	this.compilable = compilable;
    }

    public void setRightAnswersByColectiveValue(int value) {
	if (isPowOf2(value)) {
	    setCorrectAnswer(fromInt(getPozFromColectiveValue(value)));
	} else {
	    while (value >= 1) {
		int auxValue = getInfPowOf2(value);
		setCorrectAnswer(fromInt(getPozFromColectiveValue(auxValue)));
		value -= auxValue;
	    }
	}
    }

    public int getPozFromColectiveValue(int value) {
	int poz = 0;
	while (value > 1) {
	    poz++;
	    value /= 2;
	}
	return poz;
    }

    public boolean isPowOf2(int value) {
	if (value == 0) {
	    return false;
	}
	while (value > 1) {
	    if (value % 2 == 1) {
		return false;
	    }
	    value /= 2;
	}
	return true;
    }

    public int getInfPowOf2(int value) {
	while (!isPowOf2(value)) {
	    value--;
	}
	return value;
    }

    public void tryToCompile() {
	FileWriter fout = null;
	try {
	    fout = new FileWriter(new File("Option.java"));
	    fout.write(sourceCode);

	} catch (IOException ex) {
	    Logger.getLogger(Question.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
	    if (fout != null) {
		try {
		    fout.close();
		} catch (IOException ex) {
		    Logger.getLogger(Question.class.getName()).log(Level.SEVERE, null, ex);
		}
	    }
	}
	try (PrintWriter out = new PrintWriter(new FileWriter("out.txt"))) {
	    String[] sources = new String[1];
	    sources[0] = "Option.java";
	    int status = compile(sources, out);
	    if (status == 0) {
		setCompilable(true);
	    } else {
		setCompilable(false);
	    }
	} catch (IOException ex) {
	    Logger.getLogger(Question.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    public String getTaskPath() {
	return taskPath;
    }

    public void setTaskPath(String taskPath) {
	this.taskPath = taskPath;
    }

    public String getSourceCodePath() {
	return sourceCodePath;
    }

    public void setSourceCodePath(String sourceCodePath) {
	this.sourceCodePath = sourceCodePath;
    }

    public void setTaskByFile(File file) {
	Scanner scanner = null;
	StringBuilder readTask = new StringBuilder();
	try {
	    scanner = new Scanner(file);
	    while (scanner.hasNextLine()) {
		readTask.append(scanner.nextLine()).append(System.lineSeparator());
	    }
	} catch (FileNotFoundException ex) {
	    Logger.getLogger(Question.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
	    if (scanner != null) {
		scanner.close();
		task = readTask.toString();
	    }
	}
    }

    public void saveTaskToFile(File file) {
	FileWriter fout = null;
	try {
	    fout = new FileWriter(file);
	    fout.write(task);
	} catch (IOException ex) {
	    Logger.getLogger(Question.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
	    if (fout != null) {
		try {
		    fout.close();
		} catch (IOException ex) {
		    Logger.getLogger(Question.class.getName()).log(Level.SEVERE, null, ex);
		}
	    }
	}
    }

    public void setSourceCodeByFile(File file) {
	Scanner scanner = null;
	StringBuilder readSourceCode = new StringBuilder();
	try {
	    scanner = new Scanner(file);
	    while (scanner.hasNextLine()) {
		readSourceCode.append(scanner.nextLine()).append(System.lineSeparator());
	    }
	} catch (FileNotFoundException ex) {
	    Logger.getLogger(Question.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
	    if (scanner != null) {
		scanner.close();
		sourceCode = readSourceCode.toString();
	    }
	}
    }

    public void setHTMLSourceCodeByFile(File file) {
	Scanner scanner = null;
	StringBuilder readSourceCode = new StringBuilder();
	try {
	    scanner = new Scanner(file);
	    if (scanner.hasNextLine()) {
		scanner.nextLine();
	    }
	    while (scanner.hasNextLine()) {
		readSourceCode.append(scanner.nextLine()).append(System.lineSeparator());
	    }
	} catch (FileNotFoundException ex) {
	    Logger.getLogger(Question.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
	    if (scanner != null) {
		scanner.close();
		sourceCode = readSourceCode.toString();
	    }
	}
    }

    public void saveSourceCodeToFile(File file) {
	FileWriter fout = null;
	try {
	    fout = new FileWriter(file);
	    if (sourceCode != null && sourceCode.compareTo("") != 0) {
		fout.write(sourceCode);
	    }
	} catch (IOException ex) {
	    Logger.getLogger(Question.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
	    if (fout != null) {
		try {
		    fout.close();
		} catch (IOException ex) {
		    Logger.getLogger(Question.class.getName()).log(Level.SEVERE, null, ex);
		}
	    }
	}
    }
}
