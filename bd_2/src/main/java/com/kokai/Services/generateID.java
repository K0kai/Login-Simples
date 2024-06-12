package com.kokai.Services;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.RandomStringUtils;

public class generateID {

    public static String generateProceduralID(int totalLength, boolean user) {
        String[] registerTime = {String.valueOf(DateTimeFunctions.getLocalTime().getHour()), String.valueOf(DateTimeFunctions.getLocalTime().getMinute()), String.valueOf(DateTimeFunctions.getLocalTime().getSecond())};
        String unitedString = Stream.of(registerTime)
                .collect(Collectors.joining());
        if (user == true) {
            StringBuilder sb = new StringBuilder();
            sb.append("u_").append(RandomStringUtils.randomAlphanumeric((int) Math.floor(totalLength / 3))).append('-').append(RandomStringUtils.randomAlphanumeric((int) Math.floor(totalLength / 3))).append('-').append(RandomStringUtils.randomAlphanumeric((int) Math.floor(totalLength / 3))).append('_').append(unitedString);
            String finalText = String.valueOf(sb);
            return finalText;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("a_").append(RandomStringUtils.randomAlphanumeric((int) Math.floor(totalLength / 3))).append('-').append(RandomStringUtils.randomAlphanumeric((int) Math.floor(totalLength / 3))).append('-').append(RandomStringUtils.randomAlphanumeric((int) Math.floor(totalLength / 3))).append('_').append(unitedString);
            String finalText = String.valueOf(sb);
            return finalText;
        }
    }

}
