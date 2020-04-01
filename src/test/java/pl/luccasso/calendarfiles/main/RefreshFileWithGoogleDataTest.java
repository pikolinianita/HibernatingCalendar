/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.calendarfiles.main;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

/**
 *
 * @author piko
 */
public class RefreshFileWithGoogleDataTest {
    
    @Test
    public void testSomeMethod() throws IOException {
        
        var args = new String[] {"ala","ola"};
        RefreshFileWithGoogleData.main(args);
        assertThat(args).as("cheat").hasSize(2);
        
    }
    
}
