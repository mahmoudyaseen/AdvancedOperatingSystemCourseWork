package Factory;

import SpaceManager.ContiguousAllocation;
import SpaceManager.IFreeSpaceManager;
import SpaceManager.IndexedAllocation;
import SpaceManager.LinkedAllocation;

import java.io.IOException;

public class ConcreteAllocatorFactory extends AbstractAllocationFactory{
    @Override
    protected IFreeSpaceManager allocatorFactory(String type) throws IOException {
        if(type.toLowerCase().equals("contiguous"))
            return ContiguousAllocation.getInstance();
        else if(type.toLowerCase().equals("linked"))
            return LinkedAllocation.getInstance();
        else if(type.toLowerCase().equals("indexed"))
            return IndexedAllocation.getInstance();
        else
            return null;
    }
}
