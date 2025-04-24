package org.example.sis.util;


public class GradeCalculator {
    public static double calculateFinalScore(double score1, double score2) {
        return 0.3 * score1 + 0.7 * score2;
    }

    public static String calculateGrade(double finalScore) {
        if (finalScore >= 8.0 && finalScore <= 10.0) {
            return "A";
        } else if (finalScore >= 6.0 && finalScore <= 7.9) {
            return "B";
        } else if (finalScore >= 4.0 && finalScore <= 5.9) {
            return "D";
        } else {
            return "F";
        }
    }
}
