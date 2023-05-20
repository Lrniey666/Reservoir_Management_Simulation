/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reservoir_management_simulation;
import java.util.Random;

/**
 *
 * @author admin
 */
public class Rock_Door_reservoir {

    /**
     * @param args the command line arguments
     */
    public static int[] max_volume() {
        int[] n={20000,2000};
       return n;}
    
    public static int make_power() { 
        Random rnd=new Random();
        int n=150+rnd.nextInt(100);
       return n;}
    
    public static int used_water(){
        Random rnd=new Random();
        int n=150+rnd.nextInt(100);
        return n;}
    
    public static int[] water_in(int p) {
        int[] n ={};
        switch(p){
            case 0:
             n=sun_water_in();
             break;
            case 1:
             n=cloudy_water_in();
             break;
            case 2:
             n=rain_water_in();
             break;
        }
       return n;}
      
    public static int[] sun_water_in() {
        int[] n={70,70};
       return n;}
    
    public static int[] cloudy_water_in() {
        int[] n={200,100};
       return n;}
    
    public static int[] rain_water_in() {
        int[] n={600,900};
       return n;}
    
    public static int water_out() {
       return 1500;}
    public static int water_out_helf() {
       return 750;}
    
    public static int evaporation(){
        return 10;}
    
    
    }



    
    
    

