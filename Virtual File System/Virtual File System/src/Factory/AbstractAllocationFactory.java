package Factory;

import SpaceManager.IFreeSpaceManager;

import java.io.IOException;

public abstract class AbstractAllocationFactory {

    public IFreeSpaceManager createAllocator(String type) throws IOException {
        return allocatorFactory(type);
    }
    protected abstract IFreeSpaceManager allocatorFactory(String type) throws IOException;
}
