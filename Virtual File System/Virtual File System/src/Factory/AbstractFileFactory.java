package Factory;

import FileHierarchy.Ifile;

import java.io.IOException;

public abstract class AbstractFileFactory {

    public Ifile createIfile(String type, String name, String path , int size) throws IOException {
        return fileFactory(type, name, path , size);
    }

    protected abstract Ifile fileFactory(String type, String name, String path , int size) throws IOException;
}

