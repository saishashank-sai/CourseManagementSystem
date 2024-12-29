import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        StudentManagementSystem sms = new StudentManagementSystem();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            displayMenu();
            System.out.print("Please select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addStudent(sms, scanner);
                    break;
                case 2:
                    removeStudent(sms, scanner);
                    break;
                case 3:
                    searchStudent(sms, scanner);
                    break;
                case 4:
                    sms.displayAllStudents();
                    break;
                case 5:
                    saveStudentsToFile(sms, "students.txt");
                    break;
                case 6:
                    loadStudentsFromFile(sms, "students.txt");
                    break;
                case 7:
                    exit = true;
                    System.out.println("Exiting the Student Management System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        
        scanner.close();
    }
    
    private static void displayMenu() {
        System.out.println("Student Management System");
        System.out.println("1. Add Student");
        System.out.println("2. Remove Student");
        System.out.println("3. Search Student");
        System.out.println("4. Display All Students");
        System.out.println("5. Save Students to File");
        System.out.println("6. Load Students from File");
        System.out.println("7. Exit");
    }
    
    private static void addStudent(StudentManagementSystem sms, Scanner scanner) {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter roll number: ");
        int rollNumber = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter grade: ");
        String grade = scanner.nextLine();

        Student student = new Student(name, rollNumber, grade);
        sms.addStudent(student);
        System.out.println("Student added successfully.");
    }
    
    private static void removeStudent (StudentManagementSystem sms, Scanner scanner) {
        System.out.print("Enter roll number of the student to remove: ");
        int rollNumber = scanner.nextInt();
        scanner.nextLine();
        
        sms.removeStudent(rollNumber);
        System.out.println("Student removed successfully.");
    }
    
    private static void searchStudent(StudentManagementSystem sms, Scanner scanner) {
        System.out.print("Enter roll number of the student to search: ");
        int rollNumber = scanner.nextInt();
        scanner.nextLine();
        
        Student student = sms.searchStudent(rollNumber);
        if (student != null) {
            System.out.println("Student found: " + student);
        } else {
            System.out.println("Student not found.");
        }
    }
    
    private static void saveStudentsToFile(StudentManagementSystem sms, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Student student : sms.getStudents()) { 
                writer.write(student.getName() + "," + student.getRollNumber() + "," + student.getGrade());
                writer.newLine();
            }
            System.out.println("Students saved to file successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while saving to file:"+e.getMessage());
        }
    }
   
    private static void loadStudentsFromFile(StudentManagementSystem sms, String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) !=null ) {
                String[] parts = line.split(".");
                if (parts.length == 3) {
                    String name = parts[0];
                    int rollNumber = Integer.parseInt(parts[1]);
                     String  grade = parts[2];
 
                     Student student = new Student(name, rollNumber, grade);
                     sms.addStudent(student);
                }
            }
            System.out.println("Students loaded from file successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while loading from file: " + e.getMessage());
        }
    }
}

class Student {
    private String name;
    private int rollNumber;
    private String grade;

    public Student(String name, int rollNumber, String grade) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
    }
    
    public String getName() { 
        return name;
    }
    
    public int getRollNumber() { 
        return rollNumber;
    }
    
    public String getGrade() { 
        return grade;
    }
    
    public void setName(String name) { 
        this.name = name;
    }
    
    public void setRollNumber(int rollNumber) { 
        this.rollNumber = rollNumber;
    }
    
    public void setGrade(String grade) {
        this.grade = grade;
    }
    
    @Override
    public String toString() {
        return "Student[" +
        "name='" + name + '\'' +
        ", rollNumber=" + rollNumber +
        ",grade='" + grade + '\'' + 
        '}';
    }
}

class StudentManagementSystem {
    private List<Student> students;

    public StudentManagementSystem() { 
        this.students = new ArrayList<>();
    }
    
    public void addStudent(Student student) {
        students.add(student);
    }
    
    public void removeStudent(int rollNumber) {
        students.removeIf (student -> student.getRollNumber() == rollNumber);
    }
    
    public Student searchStudent(int rollNumber) {
        for (Student student : students) {
            if (student.getRollNumber() == rollNumber) {
                return student;
            }
        }
        return null;
    }
    
    public void displayAllStudents() {
        for (Student student : students) {
              System.out.println(student);
        }
    }
    
    public List<Student> getStudents() {
        return students;
    }
}
