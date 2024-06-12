package com.kokai.Services;

import org.apache.commons.lang3.StringUtils;

public class StringFormatter {

    public static String centerTextWithLineBreaks(String text, int totalWidth) {
        StringBuilder centeredText = new StringBuilder();
        String[] lines = text.split("\n");

        for (String line : lines) {
            int spacesToAdd = Math.max(0, (totalWidth - line.length()) / 2);
            centeredText.append(StringUtils.repeat(' ', spacesToAdd)).append(line).append("\n");
        }

        return centeredText.toString();
    }

    public static void typeWrite(String text, int interval) throws InterruptedException {
        for (char ch : text.toCharArray()) {
            System.out.print(ch);
            Thread.sleep(interval);
        }
    }

}
