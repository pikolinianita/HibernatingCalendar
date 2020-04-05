/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.calendarfiles.repo;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author piko
 */
public class TopicRepositoryQueriesTest {
    
    private static TopicRepository topicRepo;
    
    @BeforeAll
    public static void SetUp() {
          try {
             var repo = new LessonRepositoryBuilder().setTestConfig().create();
             topicRepo = TopicRepository.getInstance();
          } catch (IOException ex) {
              Logger.getLogger(TopicRepositoryQueriesTest.class.getName()).log(Level.SEVERE, null, ex);
          }
    }
    
    @Test
    public void testFindByName(){
        System.out.println("findByName");
        Logger.getLogger(TopicRepositoryQueriesTest.class.getName()).log(Level.INFO, "find by name");
        
        var topic = topicRepo.findByName("Metale");
        
        assertThat(topic).as("should be metale").hasFieldOrPropertyWithValue("getName","Metale");
    }
    
     @Test
    public void testNotTest(){
         System.out.println("kicha");
    }
    
}
