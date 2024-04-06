package de.tuxbe.testtechnique.util;

import org.apache.commons.text.similarity.JaroWinklerDistance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



public class TuxUtility {
    public static double compareStrings(String str1, String str2) {
        return new JaroWinklerDistance().apply(str1, str2);
    }

}
