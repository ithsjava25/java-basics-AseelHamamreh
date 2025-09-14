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



    }
}
