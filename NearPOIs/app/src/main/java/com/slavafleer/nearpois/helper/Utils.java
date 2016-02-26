package com.slavafleer.nearpois.helper;

/**
 * Utils Class
 */
public class Utils {

    // Format decimal rating to one digit rating.
    // 1.5 = 1
    // 4.6 = 5
    public static int formatRating(double rating) {

        if (rating * 10 % 10 > 5) {
            return (int) rating + 1;
        } else {
            return (int) rating;
        }

    }
}
