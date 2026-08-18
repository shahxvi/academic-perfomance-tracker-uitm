package com.apt.ui;

import java.io.IOException;

public class Input {
    public static InputAction get() {
        try {
            int key = System.in.read(); // throws IOException

            // Arrow Key Escape Sequence (ESC -> '[' -> 'A'/'B')
            if (key == 27) {
                if (System.in.available() >= 2) {
                    int next1 = System.in.read();
                    int next2 = System.in.read();

                    if (next1 == 91) {
                        if (next2 == 65) return InputAction.UP;     // Up Arrow ('A')
                        if (next2 == 66) return InputAction.DOWN;   // Down Arrow ('B')
                        if (next2 == 67) return InputAction.SELECT; // Right Arrow ('C') -> Next / Forward
                        if (next2 == 68) return InputAction.BACK;   // Left Arrow ('D')  -> Back
                    }
                }
                return InputAction.NONE;
            } else if (key == 'h') {
                return InputAction.BACK;
            } else if (key == 'j') {
                return InputAction.DOWN;
            } else if (key == 'k') {
                return InputAction.UP;
            } else if (key == 'l' || key == '\n' || key == '\r') {
                return InputAction.SELECT;
            } else if (key == 'q') {
                return InputAction.QUIT;
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
            return InputAction.NONE;
        }

        return InputAction.NONE;
    }
}
