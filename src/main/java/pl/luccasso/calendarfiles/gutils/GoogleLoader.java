/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.calendarfiles.gutils;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.LinkedList;
import java.util.List;
import static pl.luccasso.calendarfiles.gutils.SheetsServiceUtil.getCalendarService;
import pl.luccasso.calendarfiles.model.Lesson;

/**
 *
 * @author piko
 */
public class GoogleLoader {

    public static final String KOLOROWO_ID = "hjimc4788te0h4222kdnjtdce4@group.calendar.google.com";

    public static List<Lesson> getLessonsFromGoogle() throws IOException, GeneralSecurityException {
        Calendar calendarSrv = getCalendarService();

        var newYear = new DateTime(LocalDate.of(2019, 1, 1)
                .toEpochSecond(LocalTime.of(0, 0), ZoneOffset.UTC));

        System.out.println("kicha");

        Events events = calendarSrv.events().list(KOLOROWO_ID)
                .setMaxResults(2500)
                .setSingleEvents(true)
                .execute();

        List<Lesson> list = new LinkedList();

        List<Event> items = events.getItems();
        if (items.isEmpty()) {
            System.out.println("No upcoming events found.");
        } else {
            System.out.println("Upcoming events: " + items.size());
            for (Event event : items) {
                try {
                    
                    list.add(new Lesson(event));

                } catch (Exception ex) {
                    System.out.println("Exception thrown - Event: \n" + event);
                    System.out.println(event.getSummary());
                    ex.printStackTrace();
                }
            }
        }
        return list;
    }
}
