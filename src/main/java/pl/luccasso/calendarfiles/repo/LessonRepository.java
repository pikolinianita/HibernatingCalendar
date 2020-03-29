/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.calendarfiles.repo;

import com.google.gson.GsonBuilder;
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
import pl.luccasso.calendarfiles.gutils.GoogleLoader;
import pl.luccasso.calendarfiles.model.Lesson;

/**
 *
 * @author piko
 */
public class LessonRepository {
    
    private static final String FILE_PATH = "db.txt";

    private final SessionFactory sessionF;

    public LessonRepository() {
        sessionF = new Configuration()
                .configure()
                .buildSessionFactory();
    }

    public LessonRepository initFromGoogle(){
       List<Lesson> lessonList = null;
        System.out.println("connecting to Google:"); 
       
        try{
        lessonList = GoogleLoader.getLessonsFromGoogle();
        }
        catch (IOException | GeneralSecurityException ex){
            System.out.println("Somrthing wrong with Google loader");
        }
        
        System.out.println(" trying to save");
        
        try{
        persistToFile(FILE_PATH, lessonList);
        } catch (IOException ex){
            System.out.println("Somrthing wrong with file save");
        }
        
        System.out.println(" all done");
        
        return this;
    }

    private void persistToFile(String fileName, List<Lesson> list) throws IOException {
        var gson = new GsonBuilder().setPrettyPrinting().create();
        try(var fw = new BufferedWriter(new FileWriter(fileName))){
            fw.write(gson.toJson(list));
        };
    }
    
    List<Lesson> loadFromFile(String fileName) throws FileNotFoundException, IOException{
         var gson = new GsonBuilder().create();
         LinkedList<Lesson> list = new LinkedList<>();
         try( var reader = new BufferedReader(new FileReader(fileName))){         
         list = gson.fromJson(reader, list.getClass());
          }
        return list;
    }
    
}
