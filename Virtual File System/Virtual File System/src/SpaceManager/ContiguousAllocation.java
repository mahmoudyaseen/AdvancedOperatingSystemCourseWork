package SpaceManager;

import java.io.IOException;
import java.util.ArrayList;

public class ContiguousAllocation extends IFreeSpaceManager {

    private static ContiguousAllocation allocator;
    private int bigSize,bigStart;

    private ContiguousAllocation() throws IOException {
        super();
    }

    @Override
    public Boolean isValid(int size) {
        contiguousAvailableSpace();
        if(size > bigSize)
            return false;
        return true;
    }

    public static synchronized ContiguousAllocation getInstance() throws IOException {
        if(allocator == null)
            allocator = new ContiguousAllocation();
        return  allocator;
    }

    private void contiguousAvailableSpace() {
        bigSize = 0;
        int currentSpace = 0;
        for(int i = 0 ; i < StorageSpaceSize ; i++)
        {
            if(!StorageSpace[i])
                currentSpace++;
            else
            {
                if(bigSize < currentSpace){ bigSize = currentSpace; bigStart = i - currentSpace; }
                currentSpace = 0;
            }
            if(bigSize < currentSpace && i == StorageSpaceSize-1)
            {
                bigSize = currentSpace; bigStart = i - currentSpace +1;
            }
        }
    }

    @Override
    public ArrayList<Integer> allocate(int size) {
        contiguousAvailableSpace();
        if(bigSize < size)
            return null;
        ArrayList<Integer> fileAllocate= new ArrayList<>();
        for(int i = 0 ; i < size ; i++)
        {
            StorageSpace[bigStart+i] = true;
            fileAllocate.add(bigStart+i);
        }
        return fileAllocate;
    }


}
