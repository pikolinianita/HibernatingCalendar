/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.calendarfiles.main;

import com.google.api.services.calendar.model.Event;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import pl.luccasso.calendarfiles.model.Ewent;

/**
 *
 * @author piko
 */
public class MainRunner {

    public static void main(String[] args) throws IOException, GeneralSecurityException {

        SessionFactory sessionFactory = new Configuration().configure()
                .buildSessionFactory();

        try {
            persist(sessionFactory);
            var sc = new Scanner(System.in);
            sc.nextLine();
            load(sessionFactory);
        } finally {
            sessionFactory.close();
        }

        /*System.out.println("start");
        var sheets = SheetsServiceUtil.getSheetsService();
        var calendar = SheetsServiceUtil.getCalendarService();
        System.out.println("Authorized");
        var cl = calendar.calendarList();
        var events = calendar.events()
                .list("hjimc4788te0h4222kdnjtdce4@group.calendar.google.com")
                .setSingleEvents(true)
                .setMaxResults(2500)
                .execute();
        System.out.println("Conncted");
        var list = events.getItems();
        list.forEach(e -> {
            try {
                System.out.println(e.getDescription() + " " + e.getSummary() + " " + e.getStart().toPrettyString());
            } catch (IOException ex) {
                Logger.getLogger(MainRunner.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        System.out.println(list.size());*/
        var sc = new Scanner(System.in);
        sc.nextLine();
    }

    private static void load(SessionFactory sessionFactory) {
        System.out.println("-- loading persons --");
        Session session = sessionFactory.openSession();
        @SuppressWarnings("unchecked")
        List<Ewent> events = session.createQuery("FROM Ewent").list();
        events.forEach((x) -> System.out.printf("- %s%n", x));
        session.close();
    }

    private static void persist(SessionFactory sessionFactory) {
        Ewent p1 = new Ewent("John", 35);
        Ewent p2 = new Ewent("Tina", 30);
        System.out.println("-- persisting persons --");
        System.out.printf("- %s%n- %s%n", p1, p2);

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(p1);
        session.save(p2);
        session.getTransaction().commit();
    }

}
