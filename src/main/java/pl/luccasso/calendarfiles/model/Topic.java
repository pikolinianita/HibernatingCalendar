/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.calendarfiles.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
@Entity
@Table(name = "Topics")
public class Topic implements Serializable {
    
    @Id
    @GeneratedValue
    Long id;
    
    @Column(name = "name")
    String name;
    
    @Column (name = "year")
    int year;
    
    @Column(name = "help")
    int helpingHand;
    
    @Column(name = "wetnes")
    WetnesRating wetnes;
    
    @Column(name = "remarks")
    String reamrks;
}
