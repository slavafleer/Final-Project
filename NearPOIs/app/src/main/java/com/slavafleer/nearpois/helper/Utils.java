package com.slavafleer.nearpois.helper;

/**
 * Utils Class
 */
public class Utils {

    // Format decimal rating to one digit rating.
    public static int formatRating(double rating) {

        if (rating * 10 % 10 > 5) {
            return (int) rating + 1;
        } else {
            return (int) rating;
        }

    }
}
