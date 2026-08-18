package com.apt.ui;

public class UI {
    static int selectedIndex = 0;

    public static int select(String[] options) {
        while (true) {
            render(options, selectedIndex);
            InputAction input = Input.get();

            switch (input) {
                case UP:
                    selectedIndex =  Math.max(0, selectedIndex - 1);
                    break;
                case DOWN:
                    selectedIndex = Math.min(options.length - 1, selectedIndex + 1);
                    break;
                case SELECT:
                    return selectedIndex; // Returns chosen item index (0, 1, 2...)
                case BACK:
                    return -1; // Signals "go back to previous screen"
                case QUIT:
                    return -2; // Signals "terminate application"
                case NONE:
                default:
                    break; // Ignore unrecognized keys
            }
        }
    }

    public static void render(String[] options, int selected) {
        Terminal.clearScreen();
        Terminal.moveCursorToHome();

        System.out.print("Universiti Tekonologi MARA: Academic Performance Tracker\n\n\r");

        for (int i = 0; i < options.length; i++) {
            if (i == selected) {
                System.out.print("\033[36m> " + options[i] + "\033[0m\r\n");
            } else {
                System.out.print("  " + options[i] + "\r\n");
            }
        }
    }
}