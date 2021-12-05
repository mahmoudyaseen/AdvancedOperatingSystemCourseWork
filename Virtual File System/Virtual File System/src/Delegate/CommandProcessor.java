package Delegate;

import Factory.*;
import FileHierarchy.*;
import SpaceManager.*;

import java.io.IOException;
import java.util.Scanner;

public class CommandProcessor {
    private static AbstractFileFactory fileFactory;
    private static AbstractAllocationFactory allocationFactory ;
    public static IFreeSpaceManager allocator ;
    private Ifile root ;

    public CommandProcessor() throws IOException {
        fileFactory = new ConcreteFileFactory();
        allocationFactory = new ConcreteAllocatorFactory();
        allocator = allocationFactory.createAllocator("linked");
        root = fileFactory.createIfile("folder" , "root" , "" , 0);
        String line;
        if ((line = Folder.bufferedReader.readLine()) != null) {
            root = root.loadToFile();
        }
        allocator.loadFile();
    }
    public void commandProcess(String line) throws IOException {
        String []arr = line.split(" ");
        if(arr[0].toLowerCase().equals("createfile") && arr.length == 3)
        {
            String []path = arr[1].split("/");
            String name = path[path.length-1];
            if(!allocator.isValid(Integer.parseInt(arr[2]))) {
                System.out.println("not space");
                return;
            }
            if(!root.isValid(arr[1]))
            {
                System.out.println("Directory not found or there is file with the same name");
                return;
            }
            Ifile file = fileFactory.createIfile("file" , name , arr[1], Integer.parseInt(arr[2]));
            root.addSubFile(file , arr[1]);
        }
        else if(arr[0].toLowerCase().equals("createfolder") && arr.length == 2)
        {
            String []path = arr[1].split("/");
            String name = path[path.length-1];
            //el 0 meday2ny
            Ifile folder = fileFactory.createIfile("folder" , name , arr[1], 0);
            if(!root.addSubFile(folder , arr[1]))
            {
                System.out.println("Directory not found or there is file with the same name");
            }
        }
        else if((arr[0].toLowerCase().equals("deletefile") || arr[0].toLowerCase().equals("deletefolder"))  && arr.length == 2)
        {
            String []path = arr[1].split("/");
            if(!root.delete(arr[1]))
            {
                System.out.println("Directory not found or there is file with the same name");
            }
        }
        else if(arr[0].toLowerCase().equals("displaydiskstructure") && arr.length == 1)
        {
            root.showDetails(0);
        }
        else if(arr[0].toLowerCase().equals("displaydiskstatus") && arr.length == 1)
        {
            System.out.println("num of free blocks is " + String.valueOf(allocator.availableSpace()));
            System.out.println("num of allocated blocks is " + String.valueOf(allocator.allocatedSpace()));
            allocator.printDiskStatus();
        }
        else if(arr[0].toLowerCase().equals("exitandsave") && arr.length == 1)
        {
            root.saveToFile();
            Folder.printWriter.close();
            allocator.saveFile();
            allocator.printWriter.close();
        }
        else
        {
            System.out.println("You entered Wrong Command");
        }
    }
    public static void main(String[]args) throws IOException {
        CommandProcessor commandProcessor;
        commandProcessor = new CommandProcessor();
        Scanner in = new Scanner(System.in);
        while(true) {
            System.out.println("please enter your command");
            String line = in.nextLine();
            commandProcessor.commandProcess(line);
            if(line.equals("exitandsave")) return;
        }

        /*Ifile folder2 = fileFactory.createIfile("folder" , "folder2" , "root" , 0);
        Ifile folder3 = fileFactory.createIfile("folder" , "folder3" , "root", 0);
        Ifile folder4 = fileFactory.createIfile("folder" , "folder4" , "root/folder2", 0);
        Ifile folder5 = fileFactory.createIfile("folder" , "folder5" , "root/folder3", 0);
        Ifile folder6 = fileFactory.createIfile("folder" , "folder6" , "root/folder2/folder4", 0);
        Ifile file2 = fileFactory.createIfile("file" , "file2" , "root", 100);
        Ifile file3 = fileFactory.createIfile("file" , "file3" , "root/folder2" , 200);
        Ifile file4 = fileFactory.createIfile("file" , "file4" , "root/folder3" , 300);
        Ifile file5 = fileFactory.createIfile("file" , "file5" , "root/folder2/folder4" , 100);
        Ifile file6 = fileFactory.createIfile("file" , "file6" , "root/folder2/folder4/folder6", 50);
        root.addSubFile(folder2 , "root/folder2");
        root.addSubFile(folder3 , "root/folder3");
        root.addSubFile(folder4 , "root/folder2/folder4");
        root.addSubFile(folder5 , "root/folder3/folder5");
        root.addSubFile(folder6 , "root/folder2/folder4/folder6");
        root.addSubFile(file2 , "root/file2");
        root.addSubFile(file3 , "root/folder2/file3");
        root.addSubFile(file4 , "root/folder3/file4");
        root.addSubFile(file5 , "root/folder2/folder4/file5");
        root.addSubFile(file6 , "root/folder2/folder4/folder6/file6");
        //System.out.println(folder1.addSubFile(file6 , "root/folder2/folder4/folder6/file6"));
        root.showDetails(0);
        //folder2.showDetails(0);*/
    }

}
