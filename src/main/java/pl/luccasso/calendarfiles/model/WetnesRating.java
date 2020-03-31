/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.calendarfiles.model;

/**
 *
 * @author piko
 */

public enum WetnesRating {
    DRY, WET, MIXED, NOT_SET;
    
    public static WetnesRating getFromString(String token){
        switch (token.strip()){
            case "S": return DRY;
            case "M": return WET;
            case "S/M": return MIXED;
            default: return NOT_SET;
        }
    }
    
}
