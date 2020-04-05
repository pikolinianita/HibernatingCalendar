/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.calendarfiles.main;

import java.io.IOException;

import pl.luccasso.calendarfiles.repo.LessonRepositoryBuilder;
import pl.luccasso.calendarfiles.repo.TopicRepository;

/**
 *
 * @author piko
 */
public class RefreshFileWithGoogleData {

    public static void main(String[] args) throws IOException {
        
       var topicRepo = TopicRepository.getInstance();
       topicRepo.saveListToHibernate(topicRepo.loadFromFile());
       var lessRepo = new LessonRepositoryBuilder().setProductionConfig().setSaveToFile(true).create();
     
    }
}
