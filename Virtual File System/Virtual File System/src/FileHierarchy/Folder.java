package FileHierarchy;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class Folder implements Ifile {

    public static FileWriter fileWriter = null;
    public static PrintWriter printWriter = null;
    public static FileReader fileReader = null;
    public static BufferedReader bufferedReader = null;
    private String Name;
    private String Path;
    private boolean Deleted;
    ArrayList<Ifile> subIfiles;
    public Folder(String name , String path) throws IOException {
        if(fileWriter == null)
            fileWriter = new FileWriter("text.txt" , true);
        if(printWriter == null)
            printWriter = new PrintWriter(fileWriter);
        if(fileReader == null)
            fileReader = new FileReader("text.txt");
        if(bufferedReader == null)
            bufferedReader = new BufferedReader(fileReader);
        Name = name;
        Path = path;
        Deleted = false;
        subIfiles = new ArrayList<>();
    }


    @Override
    public void showDetails(int level) {

        for(int i = 0 ; i < level ; i++)
            System.out.print("|  ");
        System.out.println(Name);
        for(int i = 0 ; i < subIfiles.size(); i++)
        {
            if(!subIfiles.get(i).isDeleted())
                subIfiles.get(i).showDetails(level+1);
        }

    }

    @Override
    public boolean delete(String path) {
        String[] arr = path.split("/");
        if(arr.length == 1) {
            Deleted = true;
            for(int i = 0 ; i < subIfiles.size() ; i++)
                if(!subIfiles.get(i).isDeleted())
                    subIfiles.get(i).delete(path);
            return true;
        }
        else
        {
            for(int i = 0 ; i < subIfiles.size() ; i++)
            {
                if(subIfiles.get(i).getName().equals(arr[1]) && !subIfiles.get(i).isDeleted()) {
                    return subIfiles.get(i).delete(path.substring(path.indexOf("/") + 1));
                }
            }
        }
        return false;
    }

    @Override
    public boolean addSubFile(Ifile file , String path) {
        String[] arr = path.split("/");
        if(arr.length == 2) {
            for(int i = 0 ; i < subIfiles.size() ; i++)
                if(subIfiles.get(i).getName().equals(arr[1]) && !subIfiles.get(i).isDeleted())
                    return false;
            subIfiles.add(file);
            return true;
        }
        else
        {
            for(int i = 0 ; i < subIfiles.size() ; i++)
            {
                if(subIfiles.get(i).getName().equals(arr[1]) && !subIfiles.get(i).isDeleted()) {
                    return subIfiles.get(i).addSubFile(file, path.substring(path.indexOf("/") + 1));
                }
            }
        }
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
        try {
            FileChannel.open(Paths.get("text.txt"), StandardOpenOption.WRITE).truncate(0).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        printWriter.println("Folder");
        printWriter.println("{");
        printWriter.println(Name);
        printWriter.println(Path);
        printWriter.println(Deleted);
        for(int i = 0 ; i < subIfiles.size() ;  i++)
            subIfiles.get(i).saveToFile();
        printWriter.println("}");
    }

    @Override
    public Ifile loadToFile()  {

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
                if (line.equals("}")) break;
                if (line.equals("Folder"))
                    subIfiles.add(new Folder("", "").loadToFile());
                else if (line.equals("File"))
                    subIfiles.add(new File("", "", 0).loadToFile());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    public Boolean isValid(String path) {
        String[] arr = path.split("/");
        if(arr.length == 2) {
            for(int i = 0 ; i < subIfiles.size() ; i++)
                if(subIfiles.get(i).getName().equals(arr[1]) && !subIfiles.get(i).isDeleted())
                    return false;
            return true;
        }
        else
        {
            for(int i = 0 ; i < subIfiles.size() ; i++)
            {
                if(subIfiles.get(i).getName().equals(arr[1]) && !subIfiles.get(i).isDeleted()) {
                    return subIfiles.get(i).isValid(path.substring(path.indexOf("/") + 1));
                }
            }
        }
        return false;
    }

}
