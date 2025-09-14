package com.example;

import com.example.api.ElpriserAPI;

import java.time.LocalDate;
import java.time.LocalTime;
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



        calculateMeanMaxMinPrice(todaysList);



    }



    //  Function for calculate and display the mean, max, and min price for the current 24-hour period:


    public static void calculateMeanMaxMinPrice(List<ElpriserAPI.Elpris> pricesList){
        double sum=0;
        double cheapestPrice = pricesList.get(0).sekPerKWh();
        double expensivePrice = pricesList.get(0).sekPerKWh();

        int cheapestHour = pricesList.get(0).timeStart().getHour();
        int expensiveHour = pricesList.get(0).timeStart().getHour();

        for (int i=0; i<pricesList.size(); i++){
            sum+=pricesList.get(i).sekPerKWh();
            if(pricesList.get(i).sekPerKWh() > expensivePrice){
                expensivePrice = pricesList.get(i).sekPerKWh();
                expensiveHour = pricesList.get(i).timeStart().getHour();
            }
            if(pricesList.get(i).sekPerKWh() < cheapestPrice){
                cheapestPrice = pricesList.get(i).sekPerKWh();
                cheapestHour = pricesList.get(i).timeStart().getHour();
            }
        }

        double mean = sum / pricesList.size();
        System.out.println("mean price = " + mean);
        System.out.println("cheapest: " + cheapestHour);
        System.out.println("expensivePrice: " + expensiveHour);

    }



}
