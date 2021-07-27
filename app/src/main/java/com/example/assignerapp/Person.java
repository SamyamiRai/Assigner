package com.example.assignerapp;

public class Person {

    int Image;
    String Name;
    String Department;


    public Person(int image,String name,String dept){
        Image=image;
        Name=name;
        Department=dept;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }
}
