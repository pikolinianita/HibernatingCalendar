/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.calendarfiles.main;

import java.io.IOException;

import pl.luccasso.calendarfiles.repo.LessonRepositoryBuilder;

/**
 *
 * @author piko
 */
public class RefreshFileWithGoogleData {

    public static void main(String[] args) throws IOException {

       var repo = new LessonRepositoryBuilder().setProductionConfig().setSaveToFile(true).create();
     
    }
}
