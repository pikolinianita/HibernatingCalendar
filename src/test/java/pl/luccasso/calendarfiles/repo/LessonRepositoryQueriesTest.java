/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.calendarfiles.repo;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author piko
 */
public class LessonRepositoryQueriesTest {

    private static LessonRepository repo = LessonRepository.getInstance();

    @BeforeAll
    public static void SetUp() {

        try {
            repo = new LessonRepositoryBuilder().setTestConfig().create();
        } catch (IOException ex) {
            Logger.getLogger(LessonRepositoryQueriesTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Test
    public void testGetFromSchool() {

        var lList = repo.getLessonsFromSchool(26);
        assertThat(lList).as("Lista Ewentow Ze Szkoly 26").allMatch((lesson) -> lesson.getSchool() == 26);
        lList.forEach(System.out::println);
    }

    @Test
    public void testGetTopicsFromSchool() {

        List<String> lList = repo.getTopicsFromSchool(26);
        assertThat(lList).as("Lista Temetow W wszkole 26").hasSize(8);
        lList.forEach(System.out::println);

    }

    @Test
    public void testGetMissingTopicsFromSchool() {
        
        List<String> lList = repo.getMissnigTopicsFromSchool(26);
        assertThat(lList).as("Lista Temetow W wszkole 26").hasSize(8);
        lList.forEach(System.out::println);
    }
    
    @Test
    public void testDeleteOrigilals(){
        int originalNumber = repo.findAll().size();
        System.out.println("aaa");
        int sizeAfterDelete = repo.deleteOriginalEntries().findAll().size();
        System.out.println("bbb");
        assertThat(originalNumber).as("should Be something").isGreaterThan(1);
        assertThat(sizeAfterDelete).as("should Be empty").isLessThan(1);
        
        System.out.printf("there were %d; and now is %d /n" , originalNumber, sizeAfterDelete );
    }
    
     @Test
     public void testHack(){
        repo.uglyHack();
        var list = repo.findAll();
       
        list.forEach(System.out::println); 
        
        assertThat(list.size()).isBetween(400, 2000);
     }

}
