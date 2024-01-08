package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*sql
create table if not exists students
(ID IDENTITY not null primary key,
name varchar(50),  -- VARCHAR(50) specification
age INT)*/

public class Student {
    private int id;
    private String name;
    private int age;

    // Constructor for creating a Student object with specified values
    public Student(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    // Getter and setter methods for id, name, and age

    // Constructor for inserting data into the database
    public Student(String name, int age) {
        try (Connection connection = DriverManager.getConnection("jdbc:h2:./studentsDB;AUTO_SERVER=TRUE")) {
            String insertSQL = "INSERT INTO STUDENTS (name, age) VALUES (?, ?)";

            // Set the VARCHAR length explicitly to 50
            try (PreparedStatement ps = connection.prepareStatement(insertSQL)) {
                ps.setString(1, name);
                ps.setInt(2, age);

                int i = ps.executeUpdate();
                if (i > 0) {
                    System.out.println("Data inserted successfully.");
                } else {
                    System.out.println("Failed to insert data.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Instantiate Student object and insert data into the database
        Student student = new Student("John Doe", 25);
    }
}
