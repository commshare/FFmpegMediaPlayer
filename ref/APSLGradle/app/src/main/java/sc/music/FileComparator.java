package sc.music;

import java.io.File;
import java.util.Comparator;

/**
 * Created by Administrator on 2015/12/10.
 */
public class FileComparator implements Comparator<File> {
    @Override
    public int compare(File f1, File f2) {
        if(f1.isDirectory() & f2.isFile())
            return -1;
        if(f1.isFile() & f2.isDirectory())
            return 1;

        return f1.getName().toLowerCase().compareTo(f2.getName().toLowerCase());
    }
}
