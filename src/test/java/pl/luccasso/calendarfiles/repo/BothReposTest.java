/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.calendarfiles.repo;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 *
 * @author piko
 */
@Disabled
public class BothReposTest {
    
    private static TopicRepository topicRepo;
    private static LessonRepository lessRepo;
            
    @BeforeAll
    public static void SetUp(){
       
        try {
            topicRepo = TopicRepository.getInstance();
            topicRepo.saveListToHibernate(topicRepo.loadFromFile());
            lessRepo = new LessonRepositoryBuilder().setProductionConfig().setSaveToFile(true).create();
        } catch (IOException ex) {
            Logger.getLogger(BothReposTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void dirtyTest(){
        System.out.println("kuku");
    }
    
}
