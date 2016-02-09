package com.slavafleer.sqlite;

/**
 * Data Class
 */
public class Skier {

    private long id;
    private String firstName;
    private String lastName;
    private double age;
    private String skiPassNumber;

    public Skier() {}

    public Skier(String firstName, String lastName, double age, String skiPassNumber) {

        setFirstName(firstName);
        setLastName(lastName);
        setAge(age);
        setSkiPassNumber(skiPassNumber);
    }

    public Skier(long id, String firstName, String lastName, double age, String skiPassNumber) {

        this(firstName, lastName, age, skiPassNumber);
        setId(id);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        if(id >= 0) {
            this.id = id;
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public double getAge() {
        return age;
    }

    public void setAge(double age) {
        if(age >= 0) {
            this.age = age;
        }
    }

    public String getSkiPassNumber() {
        return skiPassNumber;
    }

    public void setSkiPassNumber(String skiPassNumber) {
        this.skiPassNumber = skiPassNumber;
    }

    @Override
    public String toString() {

        return String.format("Name: %s %s, Age: ")

        return "Name: " + firstName + " " + lastName + ", Age: " + age +
                skiPassNumber != null ? ", Has Ski Pass" : ", Has not Ski Pass";
    }
}
