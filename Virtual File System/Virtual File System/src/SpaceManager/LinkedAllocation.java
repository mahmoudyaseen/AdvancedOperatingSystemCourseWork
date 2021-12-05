package SpaceManager;

import java.io.IOException;
import java.util.ArrayList;

public class LinkedAllocation extends IFreeSpaceManager {


    private LinkedAllocation() throws IOException {
        super();
    }

    private static LinkedAllocation allocator;

    public static synchronized LinkedAllocation getInstance() throws IOException {
        if(allocator == null)
            allocator = new LinkedAllocation();
        return  allocator;
    }

    @Override
    public Boolean isValid(int size) {

        if(size > availableSpace())
            return false;
        return true;

    }


    @Override
    public ArrayList<Integer> allocate(int size) {
        int totalFreeSize = availableSpace();
        if(totalFreeSize < size)
            return null;
        ArrayList<Integer> fileAllocate= new ArrayList<>();
        int currsize = 0;
        for(int i = 0 ; /*i < StorageSpaceSize &&*/ currsize < size ; i++)
        {
            if(!StorageSpace[i]) {
                StorageSpace[i] = true;
                fileAllocate.add(i);
                currsize++;
            }
        }
        return fileAllocate;
    }
}
