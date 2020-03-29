/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.calendarfiles.repo;

import java.io.IOException;
import java.util.List;
import pl.luccasso.calendarfiles.model.Lesson;

/**
 *
 * @author piko
 */
public class LessonRepositoryBuilder {
    private boolean fromGoogleNotFromFile = true;
    private boolean saveToHibernate = true;
    private boolean saveToFile = false;
    
    private LessonRepository repo;
    
    public LessonRepository create() throws IOException{
        repo = new LessonRepository();
        List<Lesson> lList;
        if (fromGoogleNotFromFile){
            lList = repo.initFromGoogle();
        } else {
            lList = repo.loadFromFile();
        }
        if (saveToHibernate){
            repo.saveListToHibernate(lList);
        }
        if (saveToFile){
            repo.persistToFile(lList);
        }
        
        return repo;
       }
    
    public LessonRepositoryBuilder setTestConfig(){
        
    fromGoogleNotFromFile = false;
    saveToHibernate = true;
    saveToFile = false;
        
    return this;
    }
    
    
    
    public LessonRepositoryBuilder setProductionConfig(){
        
    fromGoogleNotFromFile = true;
    saveToHibernate = true;
    saveToFile = false;
        
    return this;
        
    }
    
     public LessonRepositoryBuilder setSaveToFile(boolean b){
         saveToFile = b;
         return this;
     }
    
}
