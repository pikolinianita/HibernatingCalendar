/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.calendarfiles.repo;

import java.io.IOException;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;


/**
 *
 * @author piko
 */
public class TopicRepositoryTest {
    
  
    @Test
    public void testLoadFromFile() throws IOException {
      
        var listFromFile = new TopicRepository().loadFromFile();
        assertThat(listFromFile).as("topic list from file").hasSize(67);
        
    }
    
    @Test
    public void testHibernate() throws IOException{
        
        var repo = new TopicRepository();
        var listFromFile = repo.loadFromFile();
        var receivedList = repo.saveListToHibernate(listFromFile).findAll();
        
        assertThat(receivedList).as("topic list from hibernate").hasSize(67);
        
        
    }
    
}
