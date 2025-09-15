package com.example;

import com.example.api.ElpriserAPI;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ElpriserAPI elpriserAPI = new ElpriserAPI();

        // Getting data for today:

        LocalDate today = LocalDate.now();

        String myZone = "SE2"; // terminal
        ElpriserAPI.Prisklass zone = ElpriserAPI.Prisklass.valueOf(myZone);

        List<ElpriserAPI.Elpris> todaysList = elpriserAPI.getPriser(today, zone);

        System.out.println("prices list for today: " + todaysList);


        // Checking if tomorrow's data are available and get them:


        List<ElpriserAPI.Elpris> tommorowsList;

        LocalDate tomorrow = today.plusDays(1);

        LocalTime now = LocalTime.now();

        if(now.getHour() >= 13){
            tommorowsList = elpriserAPI.getPriser(tomorrow, zone);
            System.out.println("prices list for tomorrow= " + tommorowsList);

        }
        else {
            System.out.println("tomorrow's data are not available!");
        }


        // checking functions:


        findMeanPrice(todaysList);
        findMinPrice(todaysList);
        findMaxPrice(todaysList);

        sort(todaysList);

        optimalWindow(todaysList, 8);



    }



    //  Function for calculating and displaying the mean price:

    public static void findMeanPrice(List<ElpriserAPI.Elpris> pricesList){
        double sum=0;
        for (int i=0; i<pricesList.size(); i++){
            sum+=pricesList.get(i).sekPerKWh();
        }
        double mean = sum / pricesList.size();
        System.out.println("mean price = " + mean);

    }

    //  Function for calculating and displaying the minimum price:


    public static void findMinPrice(List<ElpriserAPI.Elpris> pricesList){
        ElpriserAPI.Elpris min = pricesList.get(0);

        for (int i=0; i<pricesList.size(); i++) {
            if(pricesList.get(i).sekPerKWh() < min.sekPerKWh()){
                min = pricesList.get(i);
            }
        }
        System.out.println("cheapest price is = " + min.sekPerKWh()  + " at " + min.timeStart().getHour() + " oclock.");

    }


    //  Function for calculating and displaying the maximum price:

    public static void findMaxPrice(List<ElpriserAPI.Elpris> pricesList){
        ElpriserAPI.Elpris max = pricesList.get(0);

        for (int i=0; i<pricesList.size(); i++) {
            if(pricesList.get(i).sekPerKWh() > max.sekPerKWh()){
                max = pricesList.get(i);
            }
        }
        System.out.println("most expensive price is = " + max.sekPerKWh()  + " at " + max.timeStart().getHour() + " oclock.");


    }



    // function for displaying prices in descending order:

    public static void sort(List<ElpriserAPI.Elpris> pricesList){

        List<ElpriserAPI.Elpris> sortedCopy = new ArrayList<>(pricesList);

        sortedCopy.sort((a, b) -> Double.compare(b.sekPerKWh(), a.sekPerKWh()));

        System.out.println("list after sorting: " + sortedCopy);
    }



    // Function for Determining optimal charging windows:

    public static void optimalWindow(List<ElpriserAPI.Elpris> prices, int duration) {
        if(duration == 2 || duration == 4 || duration == 8){
            // continue
        }
        else {
            System.out.println("The duration must be 2, 4, or 8 hour window!");
            return;
        }

        double minPriceSum = Double.MAX_VALUE;
        int starting = 0;
        double sum = 0;

        for (int i = 0; i < duration; i++) {
            sum += prices.get(i).sekPerKWh();
        }
        minPriceSum = sum;

        for (int i = duration; i < prices.size(); i++) {
            sum = sum - prices.get(i - duration).sekPerKWh() + prices.get(i).sekPerKWh();

            if (sum < minPriceSum) {
                minPriceSum = sum;
                starting = i - duration + 1;
            }
        }

        int ending = starting + duration - 1;

        int[] optimalArr = {starting, ending};


        System.out.println("optimal hours:");
        for (int i = optimalArr[0]; i <= optimalArr[1]; i++) {
            ElpriserAPI.Elpris p = prices.get(i);
            System.out.println(p.timeStart().getHour());
        }

    }





}
