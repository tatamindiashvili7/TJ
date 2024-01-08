package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    // Task 3, Read Operation 1: Retrieve a list of all students from the database
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();

        // Try-with-resources to automatically close the Connection
        try (Connection connection = DriverManager.getConnection("jdbc:h2:./studentsDB;AUTO_SERVER=TRUE")) {
            String selectSQL = "SELECT * FROM STUDENTS";

            // Using a PreparedStatement to prevent SQL injection
            try (PreparedStatement ps = connection.prepareStatement(selectSQL)) {
                // Execute the query and process the ResultSet
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        int studentId = rs.getInt("id");
                        String name = rs.getString("name");
                        int age = rs.getInt("age");

                        // Create a Student object and add it to the list
                        Student student = new Student(studentId, name, age);
                        students.add(student);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }

    // Task 3, Read Operation 2: Retrieve a specific student by ID
    public Student getStudentById(int studentId) {
        Student student = null;

        try (Connection connection = DriverManager.getConnection("jdbc:h2:./studentsDB;AUTO_SERVER=TRUE")) {
            String selectSQL = "SELECT * FROM STUDENTS WHERE id = ?";

            // Using a PreparedStatement to prevent SQL injection
            try (PreparedStatement ps = connection.prepareStatement(selectSQL)) {
                ps.setInt(1, studentId);

                // Execute the query and process the ResultSet
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String name = rs.getString("name");
                        int age = rs.getInt("age");

                        // Create a Student object for the retrieved student
                        student = new Student(studentId, name, age);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return student;
    }

    // Task 3, Insert Operation: Insert a new student into the database
    public void insertStudent(String name, int age) {
        try (Connection connection = DriverManager.getConnection("jdbc:h2:./studentsDB;AUTO_SERVER=TRUE")) {
            String insertSQL = "INSERT INTO STUDENTS (name, age) VALUES (?, ?)";

            // Using a PreparedStatement to prevent SQL injection
            try (PreparedStatement ps = connection.prepareStatement(insertSQL)) {
                ps.setString(1, name);
                ps.setInt(2, age);

                // Execute the update and check if data was inserted successfully
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

    // Inner class representing a Student with id, name, and age
    private static class Student {
        private int id;
        private String name;
        private int age;

        // Constructor for creating a Student instance
        public Student(int id, String name, int age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }

        // Getter methods for retrieving Student attributes
        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }

    // Task 3, Main method for testing the StudentDAO operations
    public static void main(String[] args) {
        // Create an instance of StudentDAO for testing
        StudentDAO studentDAO = new StudentDAO();

        // Task 3, Read Operation 1: Get all students and print the result
        List<Student> allStudents = studentDAO.getAllStudents();
        System.out.println("All Students: " + allStudents);

        // Task 3, Read Operation 2: Retrieve a specific student by ID and print the result
        int studentIdToRetrieve = 1;
        Student retrievedStudent = studentDAO.getStudentById(studentIdToRetrieve);
        System.out.println("Retrieved Student: " + retrievedStudent);

        // Task 3, Insert Operation: Insert a new student and print the result
        studentDAO.insertStudent("John", 25);
    }
}
