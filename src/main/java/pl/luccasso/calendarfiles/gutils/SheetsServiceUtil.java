package  pl.luccasso.calendarfiles.gutils;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.sheets.v4.Sheets;

import java.io.IOException;
import java.security.GeneralSecurityException;

class SheetsServiceUtil {

    private static final String APPLICATION_NAME = "Google Sheets Example";

    static Credential credential;
    
    private SheetsServiceUtil(){
        //blocker
    }
    
    public static Sheets getSheetsService() throws IOException, GeneralSecurityException {
        if (credential == null) {
         credential = GoogleAuthorizeUtil.authorize();
        }
        return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), credential).setApplicationName(APPLICATION_NAME).build();
    }

    public static Calendar getCalendarService() throws IOException, GeneralSecurityException {
        if (credential == null) {
         credential = GoogleAuthorizeUtil.authorize();
        }
         return new Calendar.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), credential).setApplicationName(APPLICATION_NAME).build();
    }
}
