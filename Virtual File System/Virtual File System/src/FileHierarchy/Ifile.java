package FileHierarchy;

public interface Ifile {

    public void showDetails(int level);
    public boolean delete(String path);
    public boolean addSubFile(Ifile file , String path);
    public boolean isDeleted();
    public String getName();
    public void saveToFile();
    public Ifile loadToFile();
    public Boolean isValid(String path);
}
