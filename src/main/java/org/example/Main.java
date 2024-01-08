package org.example;

public class Main {
        public static void main(String[] args) {
            StudentDAO createDatabaseTable = new StudentDAO();
            createDatabaseTable.insertStudent("nik", 14);
        }

}
