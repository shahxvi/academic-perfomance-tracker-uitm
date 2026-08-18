package com.apt.io;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.apt.academic.Assessment;
import com.apt.academic.Attendance;
import com.apt.academic.Course;

public class CourseFileHandler {
    private File file;

    public CourseFileHandler(String file) {
        this.file = new File(file);
    }

    public ArrayList<Course> parseRecords() {
        ArrayList<Course> courses = new ArrayList<>();

        try (Scanner inputFile = new Scanner(file)) {
            while (inputFile.hasNextLine()) {
                StringTokenizer tokens = new StringTokenizer(inputFile.nextLine(), ";");
                
                String courseCode = tokens.nextToken();
                String courseName = tokens.nextToken();
                int creditHours = Integer.parseInt(tokens.nextToken());

                int classTotal = Integer.parseInt(tokens.nextToken());
                int classConducted = Integer.parseInt(tokens.nextToken());
                int classAttended = Integer.parseInt(tokens.nextToken());
                Attendance attendance = new Attendance(classTotal, classConducted, classAttended);

                int numberOfAssessment = Integer.parseInt(tokens.nextToken());
                ArrayList<Assessment> assessments = new ArrayList<>();
                for (int i = 0; i < numberOfAssessment; i++) {
                    String assessmentName = tokens.nextToken();
                    double totalMarks = Double.parseDouble(tokens.nextToken());
                    double weight = Double.parseDouble(tokens.nextToken());
                    double marksObtained = Double.parseDouble(tokens.nextToken());
                    assessments.add(new Assessment(assessmentName, totalMarks, weight, marksObtained));
                }

                courses.add(new Course(courseCode, courseName, creditHours, attendance, assessments));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return courses;
    }

    public void saveRecords(ArrayList<Course> courses) {
        try (PrintWriter outputFile = new PrintWriter(file)) {
            for (int i = 0; i < courses.size(); i++) {
                outputFile.println(courses.get(i).toRecord());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean printReport(String filePath, ArrayList<Course> courses) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("> [!EXAMPLE] Table of Contents\n");
        for (Course c: courses) {
            stringBuilder.append(">> [[# " + c.getCode() +"]]\n");
        }
        stringBuilder.append("\n---\n\n");
        for (Course c: courses) {
            stringBuilder.append(c.toTable().replace("\r", ""));
        }

        try {
            Files.writeString(Path.of(filePath), stringBuilder);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return true;
    }
}