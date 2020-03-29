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
public class testLessonRepositoryQueries {

    private static LessonRepository repo;
    
    @BeforeAll
    public static void SetUp() {
         System.out.println("startSetUp" + repo);
        try {
            repo = new LessonRepositoryBuilder().setTestConfig().create();
        } catch (IOException ex) {
            Logger.getLogger(testLessonRepositoryQueries.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("endSetUp" +  repo);
    }
    
    @Test
    public void testGetFromSchool(){
        System.out.println(repo);
        var lList = repo.getLessonsFromSchool(26);
        assertThat(lList).as("ListaZeSzkoly26").allMatch((lesson)->lesson.getSchool()==26);
        System.out.println(lList);
    }
    
}
