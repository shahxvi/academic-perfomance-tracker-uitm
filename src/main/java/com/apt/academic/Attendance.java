package com.apt.academic;

import com.apt.ui.Table;

public class Attendance {
    private int classTotal;
    private int classConducted;
    private int classAttended;

    public Attendance(int classTotal, int classConducted, int classesAttended) {
        this.classTotal = classTotal;
        this.classConducted = classConducted;
        this.classAttended = classesAttended;
    }

    /* Setters */
    public void setClassTotal(int classTotal) { this.classTotal = classTotal; }
    public void setClassConducted(int classConducted) { this.classConducted = classConducted; }
    public void setAbsentClasses(int absentClasses) { this.classAttended = classAttended - absentClasses; }
    /* Setters */

    /* Getters */
    public int getClassTotal() { return classTotal; }
    public int getClassConducted() { return classConducted; }
    public int getClassesAttended() { return classAttended; }
    /* Getters */

    /* Processors */
    public double getAttendancePercentage() {
        return ((double) classAttended / (double) classTotal) * 100;
    }

    public int getRemainingSkips() {
        int allowedAbsences = (int) Math.floor(classTotal * 0.20);
        int absences = classConducted - classAttended;
        return allowedAbsences - absences;
    }

    public boolean isMinimum() {
        return getRemainingSkips() >= 0;
    }
    /* Processors */

    /* Printers */
    public void printTable() {
        System.out.println("\n## Attendance");
        Table attendanceTable = new Table(
            "Total Classes",
            "Classes Conducted",
            "Classes Attended",
            "Attendance (%)",
            "Remaining Skips"
        );
        attendanceTable.addRow(
            getClassTotal(),
            getClassConducted(),
            getClassesAttended(),
            String.format("%.2f", getAttendancePercentage()),
            getRemainingSkips()
        );
        attendanceTable.print();
    }

    public String toRecord() {
        return classTotal + ";" + classConducted + ";" + classAttended;
    }
    /* Printers */
}
