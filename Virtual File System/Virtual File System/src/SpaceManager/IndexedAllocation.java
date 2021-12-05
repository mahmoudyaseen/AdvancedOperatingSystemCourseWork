package SpaceManager;

import java.io.IOException;
import java.util.ArrayList;

public class IndexedAllocation extends IFreeSpaceManager {


    private IndexedAllocation() throws IOException {
        super();
    }

    @Override
    public Boolean isValid(int size) {

        if(size+1 > availableSpace())
            return false;
        return true;

    }

    private static IndexedAllocation allocator;

    public static synchronized IndexedAllocation getInstance() throws IOException {
        if(allocator == null)
            allocator = new IndexedAllocation();
        return  allocator;
    }


    @Override
    public ArrayList<Integer> allocate(int size) {
        int totalFreeSize = availableSpace();
        if(totalFreeSize < size + 1)
            return null;
        ArrayList<Integer> fileAllocate= new ArrayList<>();
        int currsize = 0;
        for(int i = 0 ; /*i < StorageSpaceSize &&*/ currsize < size + 1 ; i++)
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
