/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.calendarfiles.model;

import java.time.LocalDate;
import javax.annotation.processing.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author piko
 */
@Entity
@Table(name = "Eventy")
public class Ewent {

@Id
@GeneratedValue
int id_1;

//event.getSummary
@Column(name ="tytul")
String title_1;

@Column(name = "datab")
String date_1;


    
public Ewent(String name, int day){
    title_1 = name;
    date_1 = LocalDate.ofYearDay(2020, day).toString();
}

public Ewent(){
    
}

    @Override
    public String toString() {
        return "Ewent{" + "id_1=" + id_1 + ", title_1=" + title_1 + ", date_1=" + date_1 + '}';
    }



}
