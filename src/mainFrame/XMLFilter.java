package mainFrame;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class XMLFilter extends FileFilter {

    @Override
    public boolean accept(File file) {
        return file.isDirectory() || file.getName().toLowerCase().endsWith(".xml");
    }

    @Override
    public String getDescription() {
        return "fisiere .xml";
    }
}
