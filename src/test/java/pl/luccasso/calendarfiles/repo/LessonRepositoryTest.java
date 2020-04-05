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
public class LessonRepositoryTest {

    public LessonRepositoryTest() {
    }

    @Test
    public void testHibernateLoad() throws IOException {

        var repo = new LessonRepositoryBuilder().setTestConfig().create();
        var list = repo.findAll();
        assertThat(list.size()).isBetween(400, 2000);
        System.out.println("To Jest z Hibernata");
        System.out.println(list.size());
    }

    @Test
    public void testFileLoad() throws IOException {
        var repo = new LessonRepositoryBuilder().setTestConfig().create();
        var list = repo.loadFromFile();
        assertThat(list.size()).isBetween(400, 2000);
        System.out.println("To Jest z pliku");
        System.out.println(list.size());
    }
}
