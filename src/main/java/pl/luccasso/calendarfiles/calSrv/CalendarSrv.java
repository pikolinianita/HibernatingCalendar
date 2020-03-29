/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.calendarfiles.calSrv;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import org.h2.tools.Console;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import static pl.luccasso.calendarfiles.gutils.SheetsServiceUtil.getCalendarService;
import pl.luccasso.calendarfiles.gutils.SheetsServiceUtil;
import pl.luccasso.calendarfiles.model.Ewent;
import pl.luccasso.calendarfiles.model.Lesson;

/**
 *
 * @author piko
 */
public class CalendarSrv {

   

    public static void main(String[] args) throws IOException, GeneralSecurityException, SQLException {
        try {Console.main("-browser");} catch (Exception ex) {}
        
//        Calendar calendarSrv = getCalendarService();
//
//        @SuppressWarnings("deprecation")
//        var newYear = new DateTime(LocalDate.of(2019, 1, 1).toEpochSecond(LocalTime.of(0, 0), ZoneOffset.UTC));
//
//        System.out.println("kicha");
//
//        Events events = calendarSrv.events().list(KOLOROWO_ID)
//                .setMaxResults(2500)
//                .setSingleEvents(true)
//                .execute();
//
//        List<Lesson> list = new LinkedList();
//
//        List<Event> items = events.getItems();
//        if (items.isEmpty()) {
//            System.out.println("No upcoming events found.");
//        } else {
//            System.out.println("Upcoming events: " + items.size());
//            for (Event event : items) {
//                try {
//                    DateTime start = event.getStart().getDateTime();
//                    if (start == null) {
//                        start = event.getStart().getDate();
//                    }
//
//                    list.add(new Lesson(event));
//
//                    System.out.printf("%s (%s)\n", event.getSummary(), start);
//                } catch (Exception np) {
//                    System.out.println("Event: \n" + event);
//                    System.out.println(event.getSummary());
//                }
//            }
//        }

        SessionFactory sessionFactory = new Configuration().configure()
                .buildSessionFactory();

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            list.forEach((item) -> session.save(item));
            session.getTransaction().commit();
            }
        
        List<Lesson> retrieved = null;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
           retrieved = session.createQuery("FROM Lesson").list();
        } catch (Exception ex){
            System.out.println("no lesson");
        }
         System.out.println("retrieved");
        
        if (retrieved != null) {
            retrieved.forEach(System.out::println);
        }
        
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
           retrieved = session.createQuery("select a FROM Lesson where a.schoolNumber = 25").list();
        } catch (Exception ex){
            System.out.println("blad sql 2");
        }
        
        System.out.println("retrieved");
        
         if (retrieved != null) {
            retrieved.forEach(System.out::println);
        }
        
        var sc = new Scanner(System.in);
        sc.nextLine();

        calendarSrv.calendars().get("string").getClass();

        var x = calendarSrv.calendarList().list().execute();
        x.keySet().forEach(System.out::println);
        var y = x.get("items");
        System.out.println(x);
    }

}
