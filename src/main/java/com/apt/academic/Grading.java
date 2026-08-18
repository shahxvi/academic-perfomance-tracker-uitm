package com.apt.academic;

public class Grading {
    public static String calculateGrade(double score) {
        if (score >= 90) return "A+";
        if (score >= 80) return "A+";
        if (score >= 75) return "A-";
        if (score >= 70) return "B+";
        if (score >= 65) return "B";
        if (score >= 60) return "B-";
        if (score >= 55) return "C+";
        if (score >= 50) return "C";
        if (score >= 47) return "C-"; // Fail
        if (score >= 44) return "D+";
        if (score >= 40) return "D";
        if (score >= 30) return "E";
        return "F";
    }

    public static double calculateGradePoint(String grade) {
        switch (grade) {
            case "A+":
            case "A":
                return 4.00; 
            case "A-":
                return 3.67;
            case "B+":
                return 3.33;
            case "B":
                return 3.00;
            case "B-":
                return 2.67;
            case "C+":
                return 2.33;
            case "C":
                return 2.00;
            case "D+":
                return 1.33;
            case "D":
                return 1.00;
            case "E":
                return 0.67;
            default:
                return 0.00;
        }
    }

    public static double calculateGradePoint(double score) {
        if (score >= 80) return 4.00;
        if (score >= 75) return 3.67;
        if (score >= 70) return 3.33;
        if (score >= 65) return 3.00;
        if (score >= 60) return 2.67;
        if (score >= 55) return 2.33;
        if (score >= 50) return 2.00;
        if (score >= 47) return 1.67;
        if (score >= 44) return 1.33;
        if (score >= 40) return 1.00;
        if (score >= 30) return 0.67;
        return 0.00;
    }
}