package defpack.project3ds;

import java.io.File;
import java.io.IOException;

public class Department {
    final static String MAIN_FILES_PATH = "C:\\Users\\mctib\\eclipse-workspace\\Project3DS\\depFiles\\";
    private String name;
    private final String path;
    private final File file;
    HashTable students;

    public Department(String name, String path) {
        this.name = name;
        this.path = path;
        file = new File(MAIN_FILES_PATH +path);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        }catch (IOException ignored){}
        students = new HashTable();
    }

    @Override
    public String toString() {
         return String.format("Department : %-20s | Path: %-20s",name,path);
    }

    public String toFileString(){
        return (this.name + "/" + this.path);
    }

    //Getters & Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getFile() {
        return file;
    }


}
