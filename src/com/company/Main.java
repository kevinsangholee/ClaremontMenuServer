package com.company;

import javax.management.Query;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Main {
    private final ScheduledExecutorService scheduler = Executors
            .newScheduledThreadPool(1);

    public void startScheduleTask() {
        /**
         * not using the taskHandle returned here, but it can be used to cancel
         * the task, or check if it's done (for recurring tasks, that's not
         * going to be very useful)
         */
        LocalDateTime localNow = LocalDateTime.now();
        ZoneId currentZone = ZoneId.of("America/Los_Angeles");
        ZonedDateTime zonedNow = ZonedDateTime.of(localNow, currentZone);
        ZonedDateTime zonedNext5 ;
        zonedNext5 = zonedNow.withHour(6).withMinute(0).withSecond(0);
        if(zonedNow.compareTo(zonedNext5) > 0)
            zonedNext5 = zonedNext5.plusDays(1);

        Duration duration = Duration.between(zonedNow, zonedNext5);
        long initalDelay = duration.getSeconds();

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(
                new Runnable() {
                    public void run() {
                        try {
                            dailyCheck();
                        }catch(Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                },
                initalDelay, 24*60*60, TimeUnit.SECONDS);
    }

    public static void main(String[] args) {
        dailyCheck();
        Main ste = new Main();
        ste.startScheduleTask();
    }

    public static void dailyCheck() {
        RequestHandler rh = new RequestHandler();
        // Adding new foods
        System.out.println("Hello! Here to do my daily run.");
        for(int i = 0; i < 7; i++) {
            System.out.println("Starting to add new foods for school id = " + i);
            HashMap<String, String> params = new HashMap<>();
            String JSON_DATA = rh.sendGetRequestAspcAll(DBConfig.ASPC_BASE_URL, i);
            params.put(DBConfig.KEY_JSON, JSON_DATA);
            params.put(DBConfig.KEY_FOOD_SCHOOL, String.valueOf(i));
            String result = rh.sendPostRequest(DBConfig.URL_ADD_ASPC_FOOD, params);
            System.out.println(result + " for school id = " + i);
        }
        // Adding today's images
        for(int i = 0; i < 7; i++) {
            System.out.println("Starting to put in images for school id = " + i);
            String ASPC_JSON = rh.sendGetRequestAspcToday(DBConfig.ASPC_BASE_URL, i);
            ArrayList<String> foodList = QueryUtils.extractASPCFoodList(ASPC_JSON);
            String DB_JSON = rh.sendGetRequestWithStringArray(DBConfig.URL_GET_FOOD, i, foodList);
            QueryUtils.postFoodImages(DB_JSON);
        }
        System.out.println("That's all! See you tomorrow!");
    }
}
