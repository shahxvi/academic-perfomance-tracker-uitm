package com.apt.ui;

public class UI {
    private int selectedIndex = 0;
    private String[] options;

    public UI(String[] options) {
        this.options = options;
    }

    public int select() {
        while (true) {
            render();
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

    public void render() {
        Terminal.clearScreen();
        Terminal.moveCursorToHome();

        System.out.print("\033[95mUniversiti Tekonologi MARA: Academic Performance Tracker\033[0m\n\n\r");

        for (int i = 0; i < options.length; i++) {
            if (i == selectedIndex) {
                System.out.print("\033[36m> " + options[i] + "\033[0m\r\n");
            } else {
                System.out.print("  " + options[i] + "\r\n");
            }
        }
    }
}