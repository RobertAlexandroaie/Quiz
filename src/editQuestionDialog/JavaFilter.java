/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package editQuestionDialog;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Robert
 */
public class JavaFilter extends FileFilter {

    @Override
    public boolean accept(File file) {
        return file.isDirectory() || file.getName().toLowerCase().endsWith(".java");
    }

    @Override
    public String getDescription() {
        return "fisiere .java";
    }
}
