/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fernandezluis.pizzanotifier.timer;

import com.fernandezluis.pizzanotifier.core.Calendar;
import com.fernandezluis.pizzanotifier.core.CalendarClient;
import com.fernandezluis.pizzanotifier.core.CalendarMUMClient;
import com.fernandezluis.pizzanotifier.core.Food;
import com.fernandezluis.pizzanotifier.core.FoodDetector;
import com.fernandezluis.pizzanotifier.core.NotificationType;
import com.fernandezluis.pizzanotifier.core.Notifier;
import com.fernandezluis.pizzanotifier.core.PizzaDetector;
import com.fernandezluis.pizzanotifier.core.PizzaMomentNotifier;
import com.fernandezluis.pizzanotifier.core.PizzaMomentNotifier1;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.ScheduleExpression;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;

/**
 *
 * @author 984201
 */
@Stateless
public class PizzaTimer {

    @Resource
    TimerService timerService;
    @EJB
    PizzaMomentNotifier1 pizzaNotifier;

    @Schedule(minute = "00", second = "00", dayOfMonth = "*", month = "*", year = "*", hour = "8", dayOfWeek = "Mon-Sun", persistent = false)
    public void myTimer() {
        sendMail();
    }

    @Asynchronous
    public void sendMail() {
        //put a sleep here to demonstrate async
        CalendarClient<Calendar> c = new CalendarMUMClient();
        FoodDetector foodDetect = new PizzaDetector(c.getCalendar().getItems());
        List<Food> pizzaDays = foodDetect.desiredFood();
        java.util.Calendar todayDate = java.util.Calendar.getInstance();
        for (Food pizzaDay : pizzaDays) {
            java.util.Calendar pizzaDate = java.util.Calendar.getInstance();
            pizzaDate.setTime(pizzaDay.getStart().getDateTime());
            if (pizzaDate.get(java.util.Calendar.MONTH) == todayDate.get(java.util.Calendar.MONTH)) {
                if (pizzaDate.get(java.util.Calendar.DAY_OF_MONTH) == (todayDate.get(java.util.Calendar.DAY_OF_MONTH) + 1)) {
                    pizzaNotifier.notifySubscribers(pizzaDay, NotificationType.DAILY);
                    ScheduleExpression schedule = new ScheduleExpression();
                    schedule.dayOfMonth(pizzaDate.get(java.util.Calendar.DAY_OF_MONTH))
                            .month(pizzaDate.get(java.util.Calendar.MONTH))
                            .year(pizzaDate.get(java.util.Calendar.YEAR));
                    switch (pizzaDay.getSummary()) {
                        case "LUNCH":
                            schedule.hour("12");
                            break;
                        case "DINNER":
                            schedule.hour("18").minute("30");
                            break;
                    }
                    timerService.createCalendarTimer(schedule, new TimerConfig(pizzaDay, true));

                }
            }

        }
    }

    @Timeout
    public void notifySubscribers(Timer timer) {
       // Notifier pizzaNotifier = new PizzaMomentNotifier();
        Food food = (Food) timer.getInfo();
        pizzaNotifier.notifySubscribers(food, NotificationType.MOMENT);
    }
}
