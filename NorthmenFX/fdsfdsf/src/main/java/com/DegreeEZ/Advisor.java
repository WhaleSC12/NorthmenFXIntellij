package com.DegreeEZ;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Advisor extends User {
    ArrayList<Student> students;

    public Advisor(UUID uuid, String userName, String firstName, String lastName, String password, ArrayList<UUID> studentUUIDs) {
        super(uuid, userName, firstName, lastName, password);
        students = new ArrayList<>();
        for (UUID id : studentUUIDs) {
            students.add(StudentList.getStudentByUUID(id));
        }
    }

    public void viewStudents() {
        int ndx = 1;
        for (Student s : students) {
            System.out.printf("%d)\nFirst Name: %s\nLast Name: %s\nMajor: %s\n\n", ndx++, s.getFirstName(), s.getLastName(), s.getMajor());
        }
    }

    public void performDegreeAudit(Student student, Scanner scanner) {
        System.out.println("Degree Audit For Student " + student);
        System.out.println("Major: " + student.getMajor());
        System.out.println("Completed Courses: " + student.getCompletedCourses());
        System.out.println("Enrolled Courses: " + student.getCompletedCourses());
        System.out.println("Required Courses: " + student.getOutstandingRequirements());

        System.out.println("1) Change Major");
        System.out.println("2) Add course to completed courses");
        System.out.println("3) Add course to required courses");
        System.out.println("4) Add course to enrolled courses (bypass prerequisites)");
        System.out.println("5) Add a note or message for student");

        int option = scanner.nextInt();
        scanner.nextLine();
        switch(option) {
            case 1:
                System.out.println("Enter the name of the major to switch to");
                String majorName = scanner.nextLine();
                Major m = MajorList.getMajorByName(majorName);
                if (m == null) {
                    return;
                }
                student.setMajor(m);
                System.out.println("Set major to " + m);
                break;
            case 2:
                System.out.println("Enter the course code to add to completed (Ex: CSCE101)");
                String courseCode = scanner.nextLine();
                Course c = CourseList.getCourseByCode(courseCode);
                if (c == null) {
                    return;
                }
                System.out.println("Enter Grade");
                int grade = scanner.nextInt();
                student.getCompletedCourses().add(new CompletedCourse(c.getId(), grade));
                System.out.println("Added " + c);
            case 3:
                System.out.println("Enter the course code to add to required (Ex: CSCE101)");
                courseCode = scanner.nextLine();
                c = CourseList.getCourseByCode(courseCode);
                if (c == null) {
                    return;
                }
                student.getOutstandingRequirements().add(c);
                System.out.println("Added " + c);
            case 4:
                System.out.println("Enter the course code to add to enrolled (Ex: CSCE101)");
                courseCode = scanner.nextLine();
                c = CourseList.getCourseByCode(courseCode);
                if (c == null) {
                    return;
                }
                student.getEnrolledCourses().add(c);
                System.out.println("Added " + c);
            case 5:
                System.out.println("Enter a note or message to leave on student's profile");
                String noteToStudent = scanner.nextLine();
                Advisor.addAdvisorNoteToStudent(student, noteToStudent);
                System.out.println("Added a note to student");
                break;
        }
    }

    public void addCompletedCourse(Student student, CompletedCourse completedCourse) {
        // Adds a completed course to the specified student's degree progress
    }


    public void registerClass(Student student, Course course) {
        // Adds a 'currently enrolled' course to the specified student's degree progress
    }

    public void addRequiredCourse(Student student, Course course) {
        // Adds a 'required' course to the specified student's degree progress
    }

    public List<UUID> getStudentUuids() {
        List<UUID> studentUuids = new ArrayList<>();
        for (Student student : students) {
            studentUuids.add(student.getUUID()); // Collect each student's UUID
        }
        return studentUuids;
    }

    public static void addAdvisorNoteToStudent(Student student, String note) {
        student.setAdvisorNote(note);
    }

    public List<Student> getStudents() {
        return students;
    }
}
