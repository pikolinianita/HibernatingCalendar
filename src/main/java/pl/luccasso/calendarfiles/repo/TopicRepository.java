/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.calendarfiles.repo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import org.hibernate.SessionFactory;
import pl.luccasso.calendarfiles.model.Topic;
import pl.luccasso.calendarfiles.model.WetnesRating;

/**
 *
 * @author piko
 */
public class TopicRepository {

    private static final String PATH = "db_topics.txt";

    private final SessionFactory sessionF;

    public TopicRepository() {
        sessionF = HibernateUtil.getFactory();
    }

    public List<Topic> loadFromFile() throws FileNotFoundException, IOException {
        return loadFromFile(PATH);
    }

    public List<Topic> loadFromFile(String PATH) throws FileNotFoundException, IOException {
        List<Topic> topicList = new LinkedList<>();
        try (var sc = new Scanner(new BufferedReader(new FileReader(PATH)))) {
            sc.nextLine();
            sc.useDelimiter("\t");
            while (sc.hasNext()) {
                var topic = new Topic();
                topic.setName(sc.next());
                topic.setWetnes(WetnesRating.getFromString(sc.next()));
                topic.setHelpingHand(sc.nextInt());
                topic.setYear(sc.nextInt());
                topic.setRemarks(sc.nextLine());
                topicList.add(topic);
            }

        }
        return topicList;
    }

    public TopicRepository saveListToHibernate(List<Topic> list) {

        try (var session = sessionF.openSession()) {
            session.beginTransaction();

            list.forEach(session::save);

            session.getTransaction().commit();
        }
        return this;
    }

    public List<Topic> findAll() {
        try (var session = sessionF.openSession()) {
            session.beginTransaction();
            List<Topic> list;
            list = session.createQuery("FROM Topic t", Topic.class).getResultList();
            return list;
        }
    }
}
