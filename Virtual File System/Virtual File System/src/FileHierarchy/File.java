package FileHierarchy;

import Delegate.CommandProcessor;

import java.io.*;
import java.util.ArrayList;
public class File implements Ifile {

    public static FileWriter fileWriter = null;
    public static PrintWriter printWriter = null;
    public static FileReader fileReader = null;
    public static BufferedReader bufferedReader = null;

    private String Name;
    private String Path;
    private boolean Deleted;
    private ArrayList<Integer>Blocks;

    public File(String name , String path , int size) throws IOException {
        if(fileWriter == null)
            fileWriter = Folder.fileWriter;
        if(printWriter == null)
            printWriter = Folder.printWriter;
        if(fileReader == null)
            fileReader = Folder.fileReader;
        if(bufferedReader == null)
            bufferedReader = Folder.bufferedReader;
        Name = name;
        Path = path;
        Deleted = false;
        Blocks = CommandProcessor.allocator.allocate(size);
    }

    @Override
    public void showDetails(int level) {
        for(int i = 0 ; i < level ; i++)
            System.out.print("|  ");
        System.out.println(Name);
    }

    @Override
    public boolean delete(String path) {
        Deleted = true;
        CommandProcessor.allocator.Deallocate(Blocks);
        return true;
    }

    @Override
    public boolean addSubFile(Ifile file , String path) {
        return false;
    }

    @Override
    public boolean isDeleted() {
        return Deleted;
    }

    @Override
    public String getName() {
        return Name;
    }

    @Override
    public void saveToFile() {

        printWriter.println("File");
        printWriter.println("{");
        printWriter.println(Name);
        printWriter.println(Path);
        printWriter.println(Deleted);
        for(int i = 0 ; i < Blocks.size() ;  i++)
            printWriter.println(Blocks.get(i));
        printWriter.println("}");

    }

    @Override
    public Ifile loadToFile() {

        try {
            String line;
            if ((line = bufferedReader.readLine()) != null) {
                if(!line.equals("{"))
                    return null;
            }
            if ((line = bufferedReader.readLine()) != null) {
                Name = line;
            }
            if ((line = bufferedReader.readLine()) != null) {
                Path = line;
            }
            if ((line = bufferedReader.readLine()) != null) {
                Deleted = Boolean.valueOf(line);
            }
            while ((line = bufferedReader.readLine()) != null) {
                if(line.equals("}")) break;
                Blocks.add(Integer.valueOf(line));
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    public Boolean isValid(String path) {
        return false;
    }
}
