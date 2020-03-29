/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.calendarfiles.model;

import com.google.api.services.calendar.model.Event;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.regex.Pattern;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author piko
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
@Table(name = "Lessons")
public class Lesson implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "googleID")
    String googleID;

    @Column(name = "title")
    String topic;

    @Column(name = "schoolNumber")
    int school;

    @Column(name = "tx")
    String week;

    @Column(name = "StartDate")
    LocalDate startDate;

    private static Pattern spacePattern = Pattern.compile(" ");

    public Lesson() {
    }

    public Long getId() {
        return id;
    }

    void setId(Long newValue) {
        id = newValue;
    }

    public Lesson(Event eventGoogle) {

        splitTitle(eventGoogle.getSummary());
        startDate = LocalDate.parse(eventGoogle.getStart().getDate().toStringRfc3339());
        googleID = eventGoogle.getId();
    }

    private void splitTitle(String eventTitle) {
        //TODO better pattern in If, better pattern in topic if
        if (eventTitle.startsWith("SP") || eventTitle.startsWith("sp")) {
            var sc = new Scanner(eventTitle).useDelimiter(spacePattern);
            school = Integer.parseInt(sc.next().substring(2));
            week = sc.next();
            sc.reset();
            if (sc.hasNext()) {
                topic = sc.nextLine();
            }
        }
    }
}
