package com.apt.academic;

import java.util.ArrayList;

import com.apt.ui.Table;

public class Course {
    private String code;
    private String name;
    private int creditHours;
    private Attendance attendance;
    private ArrayList<Assessment> assessments;

    public Course() {
        this.code = null;
        this.name = null;
        this.creditHours = 0;
        this.attendance = null;
        this.assessments = null;
    }

    public Course(String code, String name, int creditHours, Attendance attendance, ArrayList<Assessment> assessments) {
        this.code = code;
        this.name = name;
        this.creditHours = creditHours;
        this.attendance = attendance;
        this.assessments = assessments;
    }

    /* Setters */
    public void setCode(String code) { this.code = code; }
    public void setName(String name) { this.name = name; }
    public void setCreditHours(int creditHours) { this.creditHours = creditHours; }
    public void setAttendance(Attendance attendance) { this.attendance = attendance; }
    public void setAssessments(ArrayList<Assessment> assessments) { this.assessments = assessments; }
    /* Setters */

    /* Getters */
    public String getCode() { return code; }
    public String getName() { return name; }
    public int getCreditHours() { return creditHours; }
    public Attendance getAttendance() { return attendance; }
    public ArrayList<Assessment> getAssessments() { return assessments; }
    /* Getters */

    /* Processors */
    public boolean isFinished() {
        for (Assessment a : assessments) {
            if (!a.isTaken()) {
                return false;
            }
        }
        return true;
    }

    public boolean hasFinalExam() {
        for (Assessment a : assessments) {
            if (a.isFinalAssessment()) {
                return true;
            }
        }
        return false;
    }

    boolean isExamEligable() {
        if (!hasFinalExam()) {
            return true;
        }
        return hasFinalExam() && attendance.isMinimum() && hasPassedContinuousAssessment();
    }

    public boolean hasPassedContinuousAssessment() {
        double totalCAweight = getTotalCarryMark();
        return calculateCarryMark() >= (totalCAweight * 0.5);
    }

    boolean hasPassedFinalAssessment() {
        for (Assessment a : assessments) {
            if (a.isFinalAssessment()) {
                return a.getMarksObtained() >= a.getMinimumMarksToPass();
            }
        }

        return true;
    }

    public boolean hasPassedOverallAssessment() {
        return hasPassedContinuousAssessment() && hasPassedFinalAssessment();
    }

    public String getCourseStatus() {
        if (!isFinished()) {
            return "IP";
        }

        boolean ca = hasPassedContinuousAssessment();
        boolean fa = hasPassedFinalAssessment() && isExamEligable();
        boolean oa = hasPassedOverallAssessment();

        
        if (ca && fa && oa) { return "LU"; } // Lulus
        if (ca && !fa && oa) { return "GF"; } // Gagal Final
        if (!ca && fa && oa) { return "GC"; } // Gagal Continous Assessment 
        
        return "GA"; // Gagal Overall
    }

    public double calculateCarryMark() {
        double carryMark = 0.00;
        for (Assessment a : assessments) {
            if (!a.isFinalAssessment()) {
                carryMark += a.getWeightedMark();
            }
        }
        return carryMark;
    }

    public double getPassingCarryMark() {
        double carryMark = 0.00;
        for (Assessment a : assessments) {
            if (!a.isFinalAssessment()) {
                carryMark += a.getMinimumWeightageToPass();
            }
        }
        return carryMark;
    }

    public double getTotalCarryMark() {
        double carryMark = 0.00;
        for (Assessment a : assessments) {
            if (!a.isFinalAssessment()) {
                carryMark += a.getWeightage();
            }
        }
        return carryMark;
    }

    public double predictMaximumCarryMarkPotential() {
        double maxScore = 0.00;
        for (Assessment a : assessments) {
            if (a.isTaken()) {
                maxScore += a.getWeightedMark();
            } else {
                maxScore += a.getWeightage();
            }
        }
        return maxScore;
    }

    public String calculateGrade() {
        return Grading.calculateGrade(calculateCarryMark());
    }

    public double calculateGradePoint() {
        return Grading.calculateGradePoint(calculateGrade());
    }

    public String predictMaximumGradePotential() {
        double maxScore = 0.00;
        for (Assessment a : assessments) {
            if (a.isTaken()) {
                maxScore += a.getWeightedMark();
            } else {
                maxScore += a.getWeightage();
            }
        }
        return Grading.calculateGrade(maxScore);
    }

    public double predictMaximumGradePointPotential() {
        return Grading.calculateGradePoint(predictMaximumGradePotential());
    }

    /* Printers */
    public String toSummary() {
        return "> [!INFO] Summary\n\r" +
               "> Status: " + getCourseStatus() + "\n\r" +
               "> Attendance: " + String.format("%.2f%%" ,getAttendance().getAttendancePercentage()) + " (" + getAttendance().getClassesAttended() + "/" + getAttendance().getClassConducted() + ") \n\r" +
               "> Carry Mark: " + String.format("%.2f / %.2f%%", calculateCarryMark(), getTotalCarryMark()) + "\n\r" +
               "> Current Grade Point: " + calculateGradePoint() + " (" + calculateGrade() + ")\n\r" +
               "> Credit Hours: " + getCreditHours() + "\n\r";
    }

    public String toTable() {
        String table;
        // Course Information
        table = "# " + getCode() + "\n\n\r";
        table += toSummary();

        // Attendance
        Attendance a = getAttendance();
        table += "\n## Attendance\n\r";
        Table attendanceTable = new Table(
            "Total Classes",
            "Classes Conducted",
            "Classes Attended",
            "Attendance (%)",
            "Remaining Skips"
        );
        attendanceTable.addRow(
            a.getClassTotal(),
            a.getClassConducted(),
            a.getClassesAttended(),
            String.format("%.2f", a.getAttendancePercentage()),
            a.getRemainingSkips()
        );
        table += attendanceTable.toString();


        // Assessments
        table += "\n## Assessments\n\r";
        Table assessmentTable = new Table(
            "Assessments",
            "Total Marks",
            "Minimum Marks to Pass",
            "Marks Obtained",
            "Weight (%)",
            "Minimum Weight to Pass (%)",
            "Weighted Marks (%)"
        );
        for (Assessment as : getAssessments()) {
            if (as.isTaken()) {
                assessmentTable.addRow(
                    as.getName(),
                    String.format("%.2f", as.getTotalMarks()),
                    String.format("%.2f", as.getMinimumMarksToPass()),
                    String.format("%.2f", as.getMarksObtained()),
                    String.format("%.2f", as.getWeightage()),
                    String.format("%.2f", as.getMinimumWeightageToPass()),
                    String.format("%.2f", as.getWeightedMark())
                );
            } else {
                assessmentTable.addRow(
                    as.getName(),
                    String.format("%.2f", as.getTotalMarks()),
                    String.format("%.2f", as.getMinimumMarksToPass()),
                    "-",
                    String.format("%.2f", as.getWeightage()),
                    String.format("%.2f", as.getMinimumWeightageToPass()),
                    "-"
                );
            }
        }
        table += assessmentTable.toString();

        // Carry Mark
        table += "\n### Carry Mark\n\r";
        Table carryMarkTable = new Table(
            "Current (%)",
            "Maximum Potential (%)",
            "Required to Pass (%)",
            "Total (%)"
        );
        carryMarkTable.addRow(
            String.format("%.2f", calculateCarryMark()),
            String.format("%.2f", predictMaximumCarryMarkPotential()),
            String.format("%.2f", getPassingCarryMark()),
            String.format("%.2f", getTotalCarryMark())
        );
        table += carryMarkTable.toString();

        // Assessment Statuses
        table += "\n### Assessments Status\n\r";
        Table assessmentStatusTable = new Table(
            "Continuous Assessment",
            "Final Exam Status",
            "Overall Assessment Status"
        );
        assessmentStatusTable.addRow(
            (isFinished() ? (hasPassedContinuousAssessment() ? "Passed" : "==Failed==") : "IP"),
            (isExamEligable() ? "Eligible" : "==Not Eligible=="),
            (isFinished() ? (hasPassedOverallAssessment() ? "Passed" : "==Failed==") : "IP")
        );
        table += assessmentStatusTable.toString();
        //assessmentStatusTable.print();

        // Grade Point
        table += "\n## Grade Point\n\r";
        Table gpaTable = new Table(
            "Status",
            "Grade Point",
            "Grade"
        );
        gpaTable.addRow(
            "Current",
            String.format("%.2f", calculateGradePoint()),
            calculateGrade()
        );
        gpaTable.addRow(
            "Maximum Potential",
            String.format("%.2f", predictMaximumGradePointPotential()),
            predictMaximumGradePotential()
        );
        table += gpaTable.toString();

        table += "\n---\n\r";

        return table;
    }

    public String toRecord() {
        String record = code + ";" + name  + ";" + creditHours + ";" + attendance.toRecord() + ";" + assessments.size();
        for (int i = 0; i < assessments.size(); i++) {
            record += ";";
            record += assessments.get(i).toRecord();
        }
        return record;
    }
    /* Printers */
}