package com.apt.academic;

public class Assessment {
    private String name;
    private double totalMarks;
    private double weightage;
    private double marksObtained;

    public Assessment(String name, double totalMarks, double weightage, double marksObtained) {
        this.name = name;
        this.totalMarks = totalMarks;
        this.weightage = weightage;
        this.marksObtained = marksObtained;
    }

    /* Setters */
    public void setName(String name) { this.name = name; }
    public void setTotalMarks(double totalMarks) { this.totalMarks = totalMarks; }
    public void setWeightage(double weightage) { this.weightage = weightage; }
    public void setMarksObtained(double marksObtained) { this.marksObtained = marksObtained; }
    /* Setters */

    /* Getters */
    public String getName() { return name; }
    public double getTotalMarks() { return totalMarks; }
    public double getWeightage() { return weightage; }

    public double getMarksObtained() {
        if (marksObtained == -1) { return 0; }
        return marksObtained;
    }
    /* Getters */

    /* Processors */
    public boolean isFinalAssessment() {
        if (name.contains("Final")) return true;
        return false;
    }

    public boolean isTaken() {
        if (marksObtained != -1) return true;
        return false;
    }

    public double getWeightedMark() {
        if (!isTaken()) return 0;
        return (marksObtained / totalMarks) * weightage; 
    }

    public double getMinimumMarksToPass() {
        if (isFinalAssessment()) return getTotalMarks() * 0.20;
        return getTotalMarks() * 0.50;
    }

    public double getMinimumWeightageToPass() {
        if (isFinalAssessment()) return weightage * 0.20;
        return weightage * 0.50;
    }

    public boolean isPassed() {
        if (getWeightedMark() < getMinimumWeightageToPass()) return false;
        return true;
    }
    /* Processors */

    /* Printers */
    public String toRecord() {
        return name + ";" + totalMarks + ";" +  weightage + ";" +  marksObtained;
    }
    /* Printers */
}
