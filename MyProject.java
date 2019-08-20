/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MRuser
 */
public class MyProject {

    int hours = 0, minutes = 0;
    String errorMessage;
    int month, day, year;
    int DaysInMonth;
    boolean Validmonth, Validday, Validyear;
    boolean leapyear;
    String name, line, time;

    public MyProject(String n) {
        name = n;
    }

    public void validTime(String time) {

        int colonPos = time.indexOf(":");

        // check for one colon between the digits
        if (colonPos >= 0) {
            // Get the substring of the hour
            String hoursString = time.substring(0, colonPos);

            // Convert the hoursString to an int
            hours = Integer.parseInt(hoursString);

            // Get the minutes string
            String minutesString = time.substring(colonPos + 1, time.length());

            // Convert the minutesString to an int
            minutes = Integer.parseInt(minutesString);

            // Check hours to ensure is between 1 and 23
            if (hours < 1 || hours > 23) {
                errorMessage = "Invalid hour entered: " + hours;
                System.out.println("Please try again!");
                accept();
            }

            // Check minutes to ensure is between 0 and 59
            if (minutes < 1 || minutes > 59) {
                errorMessage = "Invalid minute entered: " + minutes;
                System.out.println("Please try again!");
                accept();

            }

        }
        System.out.println("Time Saved Successfully!");
        book();
    }

    public void book() {

        try {
            FileWriter fw = new FileWriter("dates.txt");
            fw.append("\n" + line);
            fw.close();
            fw = new FileWriter("time.txt");
            fw.append("\n" + time);
            fw.close();
            fw = new FileWriter("name.txt");
            fw.append("\n" + name);
            fw.close();
            System.out.println("Appointment Booked of " + name + " on " + line + " at " + time);
        } catch (Exception e) {
            System.out.println("Unable to save your appointment, due to");
            System.out.println(e);
        }

    }

    public void validDate(String line) {

// split the Date in to day,month and year using split()
        String[] items = line.split("/");

        // first argument is Month, second is day and last one is Year
        month = Integer.parseInt(items[0]);

        day = Integer.parseInt(items[1]);

        year = Integer.parseInt(items[2]);

// checking if Entered month is VALID or Not
        Validmonth = (month >= 1 && month <= 12);

// checking if Entered Year is Valid or Not ( i Have taken Year from 1590 to 2400 )
        Validyear = (year >= 1590 && year <= 2400);

// checking if the Year is Leap Year or Not
        leapyear = ((year % 4) == 0 && ((year % 100) != 0) || (year % 400) == 0);

// Assinging the Number of days for Equivalent Month
// for 1,3,5,7,8,10 and 12 Months Have 31 DAYS
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            DaysInMonth = 31;
        }

// 4,6,9 and 11 Months have 30 DAYS
        if (month == 4 || month == 6 || month == 9 || month == 11);
        DaysInMonth = 30;

// for 2nd Month we have 2 Different DAYS based on Year
        if (month == 2) {
// if Year is Leap year then 2nd Month have 29 Days, othewise it have 28 Days

            if (leapyear) {
                DaysInMonth = 29;
            } else {
                DaysInMonth = 28;
            }
        }

// checking if the Entered Date is with in the Valid Range or Not i.e 1 <= Entered Days <= DaysInMonth
        Validday = (1 <= day);

        Validday = (day <= DaysInMonth);

// if date,month and Year is Correct then Display "date is valid"
// othewise Specify which is INCORRECT.
// check the if-else CONDITION
        if (Validday == true) {
            if (Validmonth == true) {
                if (Validyear == true) {
                    System.out.println("\nDate Saved Successfully!");

                } else {
                    System.out.println("\nYear is invalid");
                    System.out.println("Please Try again!");
                    accept();
                }
            } else {
                System.out.println("\nMonth is invalid");
                System.out.println("Please Try again!");
                accept();
            }
        } else {
            System.out.println("\nDate is invalid");
            System.out.println("Please Try again!");
            accept();
        }
    }

    public void accept() {

        Scanner sc = new Scanner(System.in);

        System.out.println("Please enter your date of appointment in mm/dd/yyyy format: ");
        line = sc.nextLine();
        validDate(line);
        System.out.println("Please enter your time of appointment in 24 hr:min format: ");
        time = sc.nextLine();
        validTime(time);

    }

    public void choice() {
        int ch;
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome " + name);
        System.out.println("What would you like to do today?");
        System.out.println("Press 1 for add a appointment, Press 2 for viewing existing appointments, Press 3 to search, Press 4 to exit");
        ch = sc.nextInt();

        switch (ch) {
            case 1:
                accept();
                break;
            case 2:
                view();
                break;
            case 3:
                search();
                break;
            case 4:
                System.out.println("Thank you!");
                System.exit(1);
                break;
            default:
                System.out.println("Invalid Option selected! Please try again");
 choice();
        }
    }

    public void view() {
        System.out.println("Appointments scheduled : ");
        System.out.println("______________________________");
        
        try {

            FileReader fr = new FileReader("dates.txt");
            int i;
            while ((i = fr.read()) != -1) {
                System.out.print((char) i);
            }
            fr.close();
        
            fr = new FileReader("time.txt");
            i = 0;
            while ((i = fr.read()) != -1) {
                System.out.print((char) i);
            }
            fr.close();
            System.out.print(" \t \t ");
            fr = new FileReader("name.txt");
            i = 0;
            while ((i = fr.read()) != -1) {
                System.out.print((char) i);
            }
            fr.close();
            System.out.println("");

        } catch (Exception e) {
            System.out.println("Unable to fetch appointment details due to : " + e);
        }
 choice();
    }

    public void search() {
        boolean found = true;
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter your date of appointment in mm/dd/yyyy format: ");
        line = sc.nextLine();
        validDate(line);
        try {
            FileReader fr = new FileReader("dates.txt");
            int i;
            StringBuilder sb = new StringBuilder();
            while ((i = fr.read()) != -1) {
                sb.append((char) i);
            }
            if (sb.equals(line)) {
                found = false;
            }

            fr.close();
            if (found) {
                System.out.println("Record found!");
               fr = new FileReader("time.txt");
            i = 0;
                System.out.print("Time is ");
            while ((i = fr.read()) != -1) {
                System.out.print((char) i);
            }
            fr.close();
                System.out.println("");
                System.out.print("Name is ");
            fr = new FileReader("name.txt");
            i = 0;
            while ((i = fr.read()) != -1) {
                System.out.print((char) i);
            }
            fr.close();
               choice();

            }
        } catch (Exception e) {
            System.out.println("Unable to fetch appointment details due to : " + e);
             choice();
        }

    }

    public static void main(String[] args) {
        String n;
        int ch;
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter your name : ");
        n = sc.nextLine();
        MyProject m = new MyProject(n);
        m.choice();

    }

}
