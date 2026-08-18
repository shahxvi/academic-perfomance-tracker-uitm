package com.apt.ui;

public class Terminal {
    public static void setRawMode() {
        try {
            new ProcessBuilder("/bin/sh", "-c", "stty raw -echo </dev/tty").inheritIO().start().waitFor();
        } catch (Exception ignored) {
            // Non-POSIX fallback
        }
    }

    public static void setCookedMode() {
        try {
            new ProcessBuilder("/bin/sh", "-c", "stty cooked echo </dev/tty").inheritIO().start().waitFor();
        } catch (Exception ignored) {
            // Non-POSIX fallback
        }
    }

    public static void clearScreen() {
        System.out.print("\033[2J");
        System.out.flush();
    }

    public static void moveCursorToHome() {
        System.out.print("\033[H");
        System.out.flush();
    }

    public static void showCursor() {
        System.out.print("\033[?25h");
    }

    public static void hideCursor() {
        System.out.print("\033[?25l");
    }
}