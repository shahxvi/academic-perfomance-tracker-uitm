package com.apt.ui;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private final List<String> headers;
    private final List<List<String>> rows;

    public Table(String... headers) {
        this.headers = List.of(headers);
        this.rows = new ArrayList<>();
    }

    public void addRow(Object... values) {
        if (values.length != headers.size()) {
            throw new IllegalArgumentException("Expected " + headers.size() + " columns but got " + values.length);
        }

        List<String> row = new ArrayList<>();

        for (Object value : values) {
            row.add(String.valueOf(value));
        }

        rows.add(row);
    }

    public void print() {
        System.out.print(this);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        int[] widths = calculateWidths();

        appendRow(stringBuilder, headers, widths);
        appendSeparator(stringBuilder, widths);

        for (List<String> row : rows) {
            appendRow(stringBuilder, row, widths);
        }

        return stringBuilder.toString();
    }

    private int[] calculateWidths() {
        int[] widths = new int[headers.size()];

        for (int i = 0; i < headers.size(); i++) {
            widths[i] = headers.get(i).length();
        }

        for (List<String> row : rows) {
            for (int i = 0; i < row.size(); i++) {
                widths[i] = Math.max(widths[i], row.get(i).length());
            }
        }

        return widths;
    }

    private void appendSeparator(StringBuilder sb, int[] widths) {
        sb.append("|");

        for (int width : widths) {
            sb.append(" ").append("-".repeat(width)).append(" |");
        }

        sb.append("\n\r");
    }

    private void appendRow(StringBuilder sb, List<String> row, int[] widths) {
        sb.append("|");

        for (int i = 0; i < row.size(); i++) {
            sb.append(" ");
            sb.append(String.format("%-" + widths[i] + "s", row.get(i)));
            sb.append(" |");
        }

        sb.append("\n\r");
    }
}