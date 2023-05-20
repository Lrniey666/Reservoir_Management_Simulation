/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reservoir_management_simulation;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import java.util.Random;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

public class FXMLDocumentController implements Initializable {
    
    private Label label;
    @FXML
    private ImageView back_lack;
    @FXML
    private ImageView back_sky;
    @FXML
    private TextArea record;
    @FXML
    private TextArea water;
    @FXML
    private RadioButton open_gate;
    @FXML
    private ToggleGroup gate;
    @FXML
    private RadioButton closs_gate;
    @FXML
    private RadioButton make_power;
    @FXML
    private ToggleGroup power;
    @FXML
    private RadioButton no_power;
    @FXML
    private TextField day;
    @FXML
    private RadioButton helf_open_gate1;
    @FXML
    private RadioButton alarm;  
    
    private Random rnd=new Random();
    private Rock_Door_reservoir rdr=new Rock_Door_reservoir();
    private int max_water,now_water,days,water_in,beform_power,weather_number,water_level_number,no_power_day,no_water_day,water_change,complain,usedwater,cd_rain;
    private String weather,water_level,alarm_str;
    private String[] weathers={"晴天","陰天","雨天"};private String[] many_water={"滿水","中水位","缺水"};private String[] alarm_list={"潰堤危機","無","乾涸危機"};
    private boolean game_over,do_make_rain=false,mr=false;
        Image sun = new Image(getClass().getResourceAsStream("晴天.png"));
        Image cloudy = new Image(getClass().getResourceAsStream("陰天.png"));
        Image rainly = new Image(getClass().getResourceAsStream("雨天.png"));
        Image full = new Image(getClass().getResourceAsStream("滿.png"));
        Image middle = new Image(getClass().getResourceAsStream("沒滿.png"));
        Image dry = new Image(getClass().getResourceAsStream("乾涸.png"));
        Image con_ok = new Image(getClass().getResourceAsStream("操作台.png"));
        Image con_wr = new Image(getClass().getResourceAsStream("操作台_危.png"));
        Image close = new Image(getClass().getResourceAsStream("未排水.png"));
        Image hrlf_open = new Image(getClass().getResourceAsStream("半排水.png"));
        Image open = new Image(getClass().getResourceAsStream("全排水.png"));
        Image sun2 = new Image(getClass().getResourceAsStream("晴2.png"));
        Image cloudy2 = new Image(getClass().getResourceAsStream("陰2.png"));
        Image rainly2 = new Image(getClass().getResourceAsStream("雨2.png"));
        Image byby = new Image(getClass().getResourceAsStream("潰堤.png"));
    @FXML
    private ImageView console;
    @FXML
    private Button next_day_button;
    @FXML
    private RadioButton give_water;
    @FXML
    private ToggleGroup gw;
    @FXML
    private RadioButton no_give_water;
    @FXML
    private ImageView tvd;
    @FXML
    private ImageView tvu;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     again();
    } 
    
    @FXML
    private void next_day(ActionEvent event) {
       one_day();
    }
    
    private void one_day(){
       if(game_over==false){      
        water_change=now_water;      
        now_water+=water_in;
        
        if(give_water.isSelected()){
            if(now_water>=max_water/15){
              record.appendText(String.format("第%d天：正常供水\n", days));
              usedwater=rdr.used_water();
              no_water_day=0;
              now_water-=usedwater;             
            }else{
                no_water_day++;
                usedwater=0;
             record.appendText(String.format("第%d天：水位不足以供水\n", days));
             record.appendText(String.format("第%d天：連續停水%d天\n", days,no_water_day));
            }
        }else{
           no_water_day++;
                usedwater=0; 
         record.appendText(String.format("第%d天：未供水\n", days));   
         record.appendText(String.format("第%d天：連續停水%d天\n", days,no_water_day)); 
        }
        
        
        if(make_power.isSelected() && now_water>=max_water/10){       
         now_water-=rdr.make_power();
         no_power_day=0;
         beform_power=80+rnd.nextInt(40);
         record.appendText(String.format("第%d天：正常供電\n", days));
        }else{
            if(make_power.isSelected()){
             record.appendText(String.format("第%d天：水位不足以發電\n", days));   
            }
            no_power_day++;
            record.appendText(String.format("第%d天：連續停電%d天\n", days,no_power_day));
            beform_power=0;
            }  
        
        if(open_gate.isSelected()){
            tvd.setImage(open);
           now_water-=rdr.water_out();
           record.appendText(String.format("第%d天：水壩閘門全開洩洪\n", days));
                if(alarm.isSelected()){
                    record.appendText(String.format("第%d天：洩洪警報發布\n", days));
                }else{
                    complain++;
                 record.appendText(String.format("第%d天：第%d次民怨:洩洪未發警報\n", days,complain));   
                }
        }else if(helf_open_gate1.isSelected()){
            tvd.setImage(hrlf_open);
         now_water-=rdr.water_out_helf();
         record.appendText(String.format("第%d天：水壩閘門半開洩洪\n", days));
                if(alarm.isSelected()){ 
                    record.appendText(String.format("第%d天：洩洪警報發布\n", days));
                  }else{
                complain++;
                record.appendText(String.format("第%d天：第%d次民怨:洩洪未發警報\n", days,complain));}
               }else if(alarm.isSelected()){
                   tvd.setImage(close);
                record.appendText(String.format("第%d天：洩洪警報發布\n", days));
                complain++;
                record.appendText(String.format("第%d天：第%d次民怨:亂發警報\n", days,complain));
               }
       
        String over_reason="你被調職了\n";
        if(no_power_day>=7){
          over_reason+="因為民眾沒電可用\n" ; 
          game_over=true;
        }
         if(no_water_day>=3){
          over_reason+="因為民眾沒水可用\n" ; 
          game_over=true;
        }
         if(now_water>max_water){
          over_reason+="因為水壩潰堤了\n" ;
          tvd.setImage(byby);
          game_over=true;
        }
         if(complain>=3){
          over_reason+="因為民怨四起\n" ; 
          game_over=true;
        }
         
         if(game_over==false){
            if(now_water<0){
                now_water=0;
            } 
           water_change=now_water-water_change;
           if(mr==false){
           weather_number=rnd.nextInt(3);}else{
             weather_number=2;  
           mr=false;}
           water_in=decide_water_in(weather_number);  
           alarm_str=decide_alarm(now_water);
           back_sky_picter(weather_number);
           back_lack_picter(water_level_number);
           water_level_number=decide_water_level(now_water);
            back_lack_picter(water_level_number);
                days++;
                if(do_make_rain==false){
                cd_rain+=1;
                if(cd_rain==30){
                 do_make_rain=true;
                 record.appendText(String.format("第%d天：可製造人工雨\n", days));
                }
            }
        
                 water.setText(String.format("最高蓄水量：%d萬立方公尺\n現在蓄水量：%d萬立方公尺\n儲水等級：%s\n水位變動：%d\n昨日供水量：%d萬立方公尺\n今日天氣：%s\n今日預計進水量：%d萬立方公尺\n昨日發電量：%d萬瓦\n警報：%s\n"
                ,max_water,now_water,water_level,water_change,usedwater,weather,water_in,beform_power,alarm_str));
         day.setText(String.format("天數：第%d天", days));  
         }
         if(game_over==true){
          record.appendText(over_reason);
          water.appendText(over_reason);
         next_day_button.setText("再試一次");
         } 
}else{
        again();    
        }  
    }
    
    private void again(){
     int[] n=rdr.max_volume();
     game_over=false;
     next_day_button.setText("度過一天");
     record.setText("");
        max_water=n[0]+rnd.nextInt(n[1]);
        now_water=1000+rnd.nextInt(n[0]-2000);         
        weather_number=rnd.nextInt(3);       
        beform_power=80+rnd.nextInt(40);
        back_sky_picter(weather_number);
        days=1;
        water_level_number=decide_water_level(now_water);
        back_lack_picter(water_level_number);
        water_in=decide_water_in(weather_number);  
        alarm_str=decide_alarm(now_water);
        closs_gate.setSelected(true);alarm.setSelected(false);give_water.setSelected(true);make_power.setSelected(true);
        tvd.setImage(close);
        no_power_day=0;no_water_day=0;water_change=0;complain=0;cd_rain=0;do_make_rain=false;
        water.setText(String.format("最高蓄水量：%d萬立方公尺\n現在蓄水量：%d萬立方公尺\n儲水等級：%s\n水位變動：%d\n昨日供水量：%d萬立方公尺\n今日天氣：%s\n今日預計進水量：%d萬立方公尺\n昨日發電量：%d萬瓦\n警報：%s\n"
                ,max_water,now_water,water_level,water_change,usedwater,weather,water_in,beform_power,alarm_str));
        day.setText(String.format("天數：第%d天", days));   
    }
    
    
    private void back_sky_picter(int p){
        weather=weathers[p];
        switch(p){
            case 0:
             back_sky.setImage(sun); 
             tvu.setImage(sun2); 
             break;
            case 1:
             back_sky.setImage(cloudy);
             tvu.setImage(cloudy2); 
             break;
            case 2:
             back_sky.setImage(rainly);
             tvu.setImage(rainly2); 
             break;             
        }
    }
    
    private void back_lack_picter(int p){
        water_level=many_water[p];
        switch(p){            
            case 0:
             back_lack.setImage(full);          
             break;
            case 1:
             back_lack.setImage(middle);
             break;
            case 2:
             back_lack.setImage(dry);
             break;             
        }
    }
    
    private int decide_water_level(int p){
     if(p>max_water/3*2){
         return 0;
     }else if(p>max_water/3){
        return 1;
    }else{
        return 2;    
            } }
    
    private int decide_water_in(int p){
        int[] w=rdr.water_in(p);
        int wi=w[0]+rnd.nextInt(w[1]);   
        return wi;
    }
    
     private String decide_alarm(int w){
        String n="";
        if(w<max_water/15){
            n=alarm_list[2];
        console.setImage(con_ok);}
        else if(w>max_water/15*14){
            n=alarm_list[0];
        console.setImage(con_wr);}
        else{
         n=alarm_list[1];
         console.setImage(con_ok);
        }
        return n;}

    @FXML
    private void gate_control(ActionEvent event) {
         if(open_gate.isSelected()){
          tvd.setImage(open);              
        }else if(helf_open_gate1.isSelected()){
         tvd.setImage(hrlf_open);  
          }else if(closs_gate.isSelected()){
          tvd.setImage(close);    }  
}

    @FXML
    private void one_month(ActionEvent event) {
        
        for(int i=0;i<30;i++){
            if(game_over==false){
         one_day();   }
        }
    }

    @FXML
    private void man_make_rain(ActionEvent event) {
        if(do_make_rain==true){
          mr=true; do_make_rain=false;
          cd_rain=0; 
          record.appendText(String.format("第%d天：製造人工雨\n", days));
        }else{
          record.appendText(String.format("第%d天：還要%d天才能製造人工雨\n", days,30-cd_rain));  }}}