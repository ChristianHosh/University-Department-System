package defpack.project3ds;

public class Student {
    private String fullName;
    private String id;
    private double average;
    private char gender;

    public Student(String fullName, String id, double average, char gender) {
        this.fullName = fullName;
        this.id = id;
        this.average = average;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return String.format("Student | Full Name: %-20s | ID: %-10s | Average %-5.1f | Gender %c",fullName,id,average,gender);
    }

    public String toFileString(){
        return (this.fullName + "/" + this.id + "/" + this.average + "/" + this.gender);
    }

    //Getters & Setters
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

}
