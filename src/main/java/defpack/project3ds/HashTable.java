package defpack.project3ds;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class HashTable {
    private static final int initialArraySize = 13;
    private Student[] hashArray;
    private int capacity;
    private int size;

    public HashTable(){
        this(initialArraySize);
    }

    private HashTable(int newSize){
        this.capacity = newSize;
        hashArray = new Student[capacity];
        size = 0;
    }

    public void insert(Student student){
        String key = student.getFullName();
        int hashVal = hash(key);
        for (int i = 0; i < capacity; i++) {
            hashVal = (hash(key) + i * hash(key)) % capacity;
            if (hashArray[hashVal] == null)
                break;
        }
        hashArray[hashVal] = student;
        size++;
        double sizeAsDouble = size;
        double capAsDouble = capacity;
        if (sizeAsDouble / capAsDouble > 0.65) {
            reHash();
        }
    }

    public void delete(String key){
        int hashVal;
        for (int i = 0; i < capacity; i++) {
            hashVal = (hash(key) + i * hash(key)) % capacity;
            if (hashArray[hashVal].getFullName().compareTo(key) == 0){
                hashArray[hashVal] = null;
                return;
            }
            if (hashArray[hashVal] == null)
                return;
        }
    }

    public Student find(String key){
        int hashVal;
        for (int i = 0; i < capacity; i++) {
            hashVal = (hash(key) + i * hash(key)) % capacity;
            if (hashArray[hashVal] == null)
                return null;
            if (hashArray[hashVal].getFullName().compareTo(key) == 0){
                return hashArray[hashVal];
            }
        }
        return null;
    }

    public String printTable(){
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < capacity; i++) {
            if (hashArray[i] == null)
                result.append(String.format("%-3d | \n", i));
            else
                result.append(String.format("%-3d | %s\n", i, hashArray[i].toString()));
        }
        return result.toString();
    }

    public void saveTableToFile(File file) throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter(file);
        for (int i = 0; i < capacity; i++) {
            if (hashArray[i] != null)
                printWriter.println(hashArray[i].toFileString());
        }
        printWriter.close();
    }

    private void reHash(){
        HashTable newHash = new HashTable(nextCapacity());
        for (Student student : hashArray) {
            if (student != null) {
                newHash.insert(student);
            }
        }
        this.capacity = newHash.capacity;
        this.hashArray = newHash.hashArray;
        this.size = newHash.size;
    }

    private int nextCapacity(){
        int nextCapacity = capacity * 2;
        for (int i = 2; i < nextCapacity; i++) {
            if(nextCapacity % i == 0) {
                nextCapacity++;
                i = 2;
            }
        }
        return nextCapacity;
    }

    public int hash(String key){
        int hashVal = 0;
        for( int i = 0; i < key.length(); i++) {
            hashVal +=  key.charAt(i);
            hashVal = hashVal << 5;
        }
        hashVal %= capacity;
        if( hashVal < 0 )
            hashVal += capacity;
        return hashVal;
    }

    public String hashUsed(String key){
        StringBuilder result = new StringBuilder();
        int hashVal;
        for (int i = 0; i < capacity; i++) {
            hashVal = (hash(key) + i * hash(key)) % capacity;
            result.append(" | ").append(hashVal);
            if (hashArray[hashVal] == null)
                break;
            if (hashArray[hashVal].getFullName().compareTo(key) == 0)
                break;
            result.append(" Collision!");
        }
        return result.toString();
    }

    public int getCapacity(){
        return capacity;
    }


}
