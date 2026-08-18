package com.apt;

import java.util.ArrayList;
import java.util.Scanner;

import com.apt.academic.Course;
import com.apt.io.CourseFileHandler;
import com.apt.ui.*;

public class Main {
    static UI mainMenu = new UI(new String[] {"Manage Course", "Manage Students (TBA)"});
    static UI studentMenu = new UI(new String[] {"Feature coming soon!"});
    static UI coursesMenu;

    private static final int STATUS_BACK = -1;
    private static final int STATUS_QUIT = -2;

    public static CourseFileHandler courseFileHandler = new CourseFileHandler("data/courses.txt");
    public static ArrayList<Course> courses = courseFileHandler.parseRecords();
    public static Scanner keyboard = new Scanner(System.in);
    private static String savePath = "/home/shah/Obsidian/UiTM-CDCS110/semester-3/academic_report.md";

    public static void main(String[] args) {
        /*
            TODO: 1.0 Student login
            TODO: 2.0 Track each course for each student
            TODO: 3.0 Save user's default file path
            TODO:   3.1 Save's user's default file path
         */
        if (args.length > 0) {
            if (args[0].contains("-r")) {
                System.out.print("Please enter absolute save path and file name and extension (Press enter to save to default save path): ");
                String keyboardInput = keyboard.nextLine();
                if (!keyboardInput.isEmpty()) {
                    savePath = keyboardInput;
                }
                courseFileHandler.saveRecords(courses);
                courseFileHandler.printReport(savePath, courses);
                keyboard.close();
                return;
            }
        }

        Terminal.setRawMode();
        Terminal.hideCursor();

        try {
            mainMenu();
        } finally {
            Terminal.showCursor();
            Terminal.setCookedMode();
            keyboard.close();
            System.out.println("\nExiting system...\r");
            //Terminal.moveCursorToHome();
        }
    }

    private static void mainMenu() {
        while (true) {
            int selection = mainMenu.select();
            if (selection == STATUS_BACK || selection == STATUS_QUIT) {
                break;
            }

            switch (selection) {
                case 0:
                    if (courseMenu())
                        return;
                    break;
                case 1:
                    if (studentMenu())
                        return;
                    break;
            }
        }
    }

    private static boolean courseMenu() {
        String[] a = new String[courses.size()];
        for (int i = 0; i < courses.size(); i++) {
            a[i] = courses.get(i).getCode();
        }
        coursesMenu = new UI(a);

        while (true) {
            int selection = coursesMenu.select();

            if (selection == STATUS_BACK) return false;
            if (selection == STATUS_QUIT) return true;

            InputAction input = InputAction.SELECT;
            while (input != InputAction.BACK) {
                Terminal.clearScreen();
                Terminal.moveCursorToHome();
                System.out.print(courses.get(selection).toTable());
                input = Input.get();
                if (input == InputAction.QUIT) return true;
            }
        }
    }

    private static boolean studentMenu() {
        while (true) {
            int selection = studentMenu.select();
            if (selection == STATUS_BACK) return false;
            if (selection == STATUS_QUIT) return true;
        }
    }
}