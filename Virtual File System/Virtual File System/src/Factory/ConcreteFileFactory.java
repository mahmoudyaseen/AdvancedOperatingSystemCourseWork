package Factory;

import FileHierarchy.File;
import FileHierarchy.Folder;
import FileHierarchy.Ifile;

import java.io.IOException;

public class ConcreteFileFactory extends AbstractFileFactory {

    @Override
    protected Ifile fileFactory(String type , String name , String path , int size) throws IOException {
        if(type.toLowerCase().equals("file"))
            return new File(name , path , size);
        else if(type.toLowerCase().equals("folder"))
            return new Folder(name , path);
        else
            return null;
    }
}