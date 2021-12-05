package SpaceManager;

import FileHierarchy.File;
import FileHierarchy.Folder;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Vector;

public abstract class IFreeSpaceManager {

    public FileWriter fileWriter = null;
    public PrintWriter printWriter = null;
    public FileReader fileReader = null;
    public BufferedReader bufferedReader = null;

    protected int StorageSpaceSize = 200;
    protected Boolean[] StorageSpace;

    protected IFreeSpaceManager() throws IOException {
        if(fileWriter == null)
            fileWriter = new FileWriter("text1.txt" , true);
        if(printWriter == null)
            printWriter = new PrintWriter(fileWriter);
        if(fileReader == null)
            fileReader = new FileReader("text1.txt");
        if(bufferedReader == null)
            bufferedReader = new BufferedReader(fileReader);
        StorageSpace = new Boolean[StorageSpaceSize];
        for(int i = 0 ; i < StorageSpaceSize ; i++)
            StorageSpace[i] = false;
    }

    public int allocatedSpace()
    {
        return StorageSpaceSize - availableSpace();
    }

    public int availableSpace() {
        int totalFreeSize = 0;
        for(int i = 0 ; i < StorageSpaceSize ; i++)
        {
            if(!StorageSpace[i])
                totalFreeSize++;
        }
        return totalFreeSize;
    }

    public abstract Boolean isValid(int size);

    public void printDiskStatus()
    {
        for(int i = 0 ; i < StorageSpaceSize ; i++)
        {
            if(!StorageSpace[i])
                System.out.print("_");
            else
                System.out.print("X");
            if(i % 30 == 0 && i != 0)
                System.out.println();
        }
        System.out.println("\n\n");
    }

    public abstract ArrayList<Integer> allocate(int size);
    public  void Deallocate(ArrayList<Integer> FileSpace){
        for(int i = 0 ; i < FileSpace.size() ; i++)
            StorageSpace[FileSpace.get(i)] = false;
    }
    public  void saveFile(){
        try {
            FileChannel.open(Paths.get("text1.txt"), StandardOpenOption.WRITE).truncate(0).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        printWriter.println(StorageSpaceSize);
        for(int i = 0 ; i < StorageSpace.length ;  i++)
            printWriter.println(StorageSpace[i]);
    }
    public  void loadFile(){
        try {
            String line;
            if ((line = bufferedReader.readLine()) != null) {
                StorageSpaceSize = Integer.valueOf(line);
            }
            int i = 0;
            while ((line = bufferedReader.readLine()) != null) {
                StorageSpace[i] = Boolean.valueOf(line);
                i++;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
