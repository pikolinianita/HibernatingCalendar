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
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import pl.luccasso.calendarfiles.gutils.GoogleLoader;
import pl.luccasso.calendarfiles.model.Lesson;

/**
 *
 * @author piko
 */
public class LessonRepository {

    private static final String FILE_PATH = "db.txt";

    private final SessionFactory sessionF;

    LessonRepository() {
        sessionF = new Configuration()
                .configure()
                .buildSessionFactory();
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

    LessonRepository saveListToHibernate(List<Lesson> list) {
        System.out.println(list.getClass());
        System.out.println(new LinkedList<Lesson>().getClass());
        try (var session = sessionF.openSession()) {
            session.beginTransaction();
            list.forEach((item) -> session.save(item));
            //TODO https://vladmihalcea.com/the-best-way-to-do-batch-processing-with-jpa-and-hibernate/
            session.getTransaction().commit();
        }
        return this;
    }

    public List<Lesson> findAll() {
        List<Lesson> list; // = new LinkedList<>();
        try (Session session = sessionF.openSession()) {
            session.beginTransaction();
            list = session.createQuery("FROM Lesson l", Lesson.class).getResultList();
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

            Query<String> query = session.createQuery("Select DISTINCT l.topic from Lesson l where l.school = :schoolNr", String.class);
            query.setParameter("schoolNr", nr);
            return query.getResultList();
        }

    }

}
