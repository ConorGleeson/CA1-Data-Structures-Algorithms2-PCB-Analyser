package com.example.ca1;

import java.util.List;

public class Utilities {
    public static boolean max10Chars(String string) {
        return (string.length() <= 10);
    } // checks the length of a string to see if it's less than or equal to 10

    public static boolean validPPS(String text) {
        if (text.length() == 9) {
            return (text.substring(0, 7).matches("[0-9]+") && text.substring(7, 9).matches("[A-Za-z]+")); //Could use quantifiers? //https://www.vogella.com/tutorials/JavaRegularExpressions/article.html link for explination and regex

        } else
            return false; // first checks to see if the string is 9 in length then checks to see if the first 7 characters are numbers then checks that 7 to 9 letters

    }

    public static boolean max30Chars(String string) {
        return (string.length() <= 30);
    } //checks to see if the length of a string is 30 or less

    public static boolean validIntNonNegative(int number) {
        return (number >= 0);
    } // makes sure that an int is greater than or equal to zero

    public static boolean validDoubleNonNegative(double number) {
        return (number >= 0);
    } //makes sure that a double is greater than or equal to zero

    public static boolean validIntRange(int min, int max, int i) {
        return (i >= min && i <= max);
    } //makes sure that an int is within a specified range

    public static boolean validDoubleRange(double min, double max, double i) {
        return (i >= min && i <= max);
    } //makes sure that a double is within a specified range

    public static boolean validLecturerLevel(int i) {
        return (i >= 1 && i <= 3);
    } //makes sure that the lecturer level is between 1 and 3

    public static double getSalaryForLecturerLevel(int i) { // first makes sure that int is between 1 and 3 and then multiples by 100 to get salary. returns -1 if invalid int
        if (i >= 1 && i <= 3) {
            return (i * 1000);
        } else return -1;
    }

    public static boolean validAdminLevel(int i) {
        return (i >= 1 && i <= 4);
    } // makes sure that the admin level is between 1 and 4

    public static double getSalaryForAdminGrade(int i) {
        if (i >= 1 && i <= 4) {
            return (i * 700);
        } else return -1;
    }  //first ensures that the int i is between 1 and 4. Then multiplies by 700 to find the salary of the Admin Worker also is used for manager salary

    public static boolean validManagerLevel(int i) {
        return (i >= 1 && i <= 7);
    } //ensures that the grade of the manager is between 1 and 7

    public static boolean validIndex(int index, List List) {
        return index >= 0 && index < List.size();
    } // this ensures that the index of an arraylist is a valid one. it makes sure that is greater than zero but less than the maximum size of the arraylist
}
