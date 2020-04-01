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
import static org.assertj.core.api.Assertions.fail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author piko
 */
public class testLessonRepositoryQueriesTest {

    private static LessonRepository repo;

    @BeforeAll
    public static void SetUp() {

        try {
            repo = new LessonRepositoryBuilder().setTestConfig().create();
        } catch (IOException ex) {
            Logger.getLogger(testLessonRepositoryQueriesTest.class.getName()).log(Level.SEVERE, null, ex);
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

        var lList = repo.getTopicsFromSchool(26);
        assertThat(lList).as("Lista Temetow W wszkole 26").hasSize(8);
        lList.forEach(System.out::println);

    }

    public void testGetMissingTopicsFromSchool() {
        fail("todo");
    }

}
