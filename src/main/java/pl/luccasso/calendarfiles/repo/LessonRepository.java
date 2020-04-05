/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.calendarfiles.repo;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import pl.luccasso.calendarfiles.gutils.GoogleLoader;
import pl.luccasso.calendarfiles.model.Lesson;
import pl.luccasso.calendarfiles.model.Topic;
import pl.luccasso.calendarfiles.model.UpdateStatus;

/**
 *
 * @author piko
 */
public class LessonRepository {

    private static final String FILE_PATH = "db.txt";

    private final SessionFactory sessionF;

    private static LessonRepository instance;
    
    private LessonRepository() {
        sessionF = HibernateUtil.getFactory();
        }

    public static LessonRepository getInstance(){
        if (instance == null){
            instance = new LessonRepository();
        }
        return instance;
    }
    
    
    
    public List<Lesson> initFromGoogle() {
        List<Lesson> lessonList = null;
        System.out.println("connecting to Google:");

        try {
            lessonList = GoogleLoader.getLessonsFromGoogle();
        } catch (IOException | GeneralSecurityException ex) {
            System.out.println("Somrthing wrong with Google loader");
        }
        return lessonList;

//        System.out.println(" trying to save");
//        
//        try{
//        persistToFile(FILE_PATH, lessonList);
//        } catch (IOException ex){
//            System.out.println("Somrthing wrong with file save");
//        }
//        
//        System.out.println("trying to persist");
//        
//        saveListToHibernate(lessonList);
//        
//        System.out.println(" all done");
//        
//        return this;
    }

    public LessonRepository persistToFile(String fileName, List<Lesson> list) throws IOException {
        var gson = new GsonBuilder().setPrettyPrinting().create();
        try (var fw = new BufferedWriter(new FileWriter(fileName))) {
            fw.write(gson.toJson(list));
        }
        return this;
    }

    LessonRepository persistToFile(List<Lesson> lList) throws IOException {
        return persistToFile(FILE_PATH, lList);
    }

    public List<Lesson> loadFromFile(String fileName) throws FileNotFoundException, IOException {
        var gson = new GsonBuilder().create();
        LinkedList<Lesson> list = new LinkedList<>();
        try (var reader = new BufferedReader(new FileReader(fileName))) {
            var token = new TypeToken<LinkedList<Lesson>>() {
            }.getType();
            list = gson.fromJson(reader, token);
        }
        return list;
    }

    public List<Lesson> loadFromFile() throws IOException {
        return loadFromFile(FILE_PATH);
    }
    
    LessonRepository deleteOriginalEntries(){
        try (Session session = sessionF.openSession()) {
            session.beginTransaction();
            var query = session.createQuery("DELETE FROM Lesson l WHERE l.status = :status")
                    .setParameter("status", UpdateStatus.ORIGINAL)
                    .executeUpdate();
        return this;
    }
    }

    LessonRepository saveListToHibernate(List<Lesson> list) {
        System.out.println(list.getClass());
        System.out.println(new LinkedList<Lesson>().getClass());
        try (var session = sessionF.openSession()) {
            session.beginTransaction();
            list.forEach((item) -> {
                System.out.println(item.getTopic() + " " + Integer.toString(item.getSchool()) + " " + item.getGoogleID());
                session.save(item);
                session.getTransaction().commit();
                session.beginTransaction();
                    });
            //TODO https://vladmihalcea.com/the-best-way-to-do-batch-processing-with-jpa-and-hibernate/
            session.getTransaction().commit();
        }
        return this;
    }

    public List<Lesson> findAll() {
        List<Lesson> list; 
        try (Session session = sessionF.openSession()) {
            session.beginTransaction();
            list = session.createQuery("Select l FROM Lesson l JOIN FETCH l.topic", Lesson.class).getResultList();
        }
        return list;
    }

    public List<Lesson> getLessonsFromSchool(int nr) {
        try (Session session = sessionF.openSession()) {
            session.beginTransaction();
            Query<Lesson> query = session.createQuery("Select l From Lesson l where l.school = :schoolNr", Lesson.class);
            query.setParameter("schoolNr", nr);
            return query.getResultList();
                    
        }
    }

    public List<String> getTopicsFromSchool(int nr) {
        try (Session session = sessionF.openSession()) {
            session.beginTransaction();

            Query<Topic> query = session.createQuery("Select DISTINCT l.topic from Lesson l where l.school = :schoolNr", Topic.class);
            query.setParameter("schoolNr", nr);
            return query.getResultList().stream()
                    .map(topic -> topic.getName())
                    .collect(Collectors.toList());
             
        }
    }

    public void uglyHack(){
       try (Session session = sessionF.openSession()) {
            session.beginTransaction();
            
            var nQueryDrop = session.createNativeQuery("DROP TABLE LESSONS");
            nQueryDrop.executeUpdate();
            
            var nQueryDropT = session.createNativeQuery("DROP TABLE TOPICS");
            nQueryDropT.executeUpdate();
            
            var nQueryT = session.createNativeQuery("CREATE TABLE TOPICS AS SELECT * FROM CSVREAD('e:/topics.csv');");
            nQueryT.executeUpdate();
            
            var nQuery = session.createNativeQuery("CREATE TABLE LESSONS AS SELECT * FROM CSVREAD('e:/lessons.csv');");
            nQuery.executeUpdate();
    }}

    List<String> getMissnigTopicsFromSchool(int nr) {
       try (Session session = sessionF.openSession()) {
            session.beginTransaction();

            final String QUERY_TEXT =  
            "Select t.name "
            + "FROM Topic  t "
            + "WHERE t.id NOT IN ( "
                + "Select DISTINCT t.id "
                + "from Lesson l "
                + "join Topic t on l.topic = t.id  "
                + "where l.school  = :SchoolNr "
                + ") "
            + "AND t.year = :year ";
            Query<String> query = session.createQuery("Select t.name FROM Topic  t WHERE t.id NOT IN ( Select DISTINCT t.id from Lesson l join Topic t on l.topic = t.id  where l.school  = :schoolNr ) AND t.year = :year ", String.class)
            .setParameter("schoolNr", nr)
            .setParameter("year", 2);
            
            
            
            //Select t.name
            //FROM TOPICS  t
            //WHERE t.ID NOT IN (
                //Select DISTINCT t.id
                //from Lessons l
                //join Topics t on l.NAME = t.ID  
                //where l.SCHOOLNUMBER  = :SchoolNr
                //) 
            //AND t.TOPIC_YEAR = 2
            
             return query.getResultList();
       }
    }
    
}
