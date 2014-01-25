package com.pokemon.pokedex;

public class Pokemon {
   String number;
   String name;
   String type;
   String weight;
   String height;
   String description;
   String descriptionVoice;
   String call;
   String picturePath;
   
   public Pokemon(String index){
      this.number = "25";
      this.name = "Pikachu";
      this.type = "Electric";
      this.weight = "13.2";
      this.height = "1.04";
      this.description = "The electric mouse pokemon. It raises its tail to check its surroundings. The tail is sometimes struck by lightning in this pose.";
      this.descriptionVoice = "peeka chew The electric mouse pokee mawn. It raises its tail to check its surroundings. The tail is sometimes struck by lightning in this pose.";
      this.call = "pikachuVoice";
      this.picturePath = "pikachu_pic";
   }
   
   public String[] getStats(){
      String[] temp = new String[8];
      temp[0] = number;
      temp[1] = name;
      temp[2] = type;
      temp[3] = String.valueOf(weight) + "lbs";

      double tempDouble = Double.parseDouble(height);
      int tempInt = (int)tempDouble;
      temp[4] = String.valueOf(tempInt) + "\'";
      tempDouble-=tempInt;
      tempDouble*=100;
      tempInt = (int)tempDouble;
      temp[4] = temp[4] + String.valueOf(tempInt) + "\"";
      
      temp[5] = description;
      temp[6] = call;
      temp[7] = picturePath;
      return temp;
   }
   
   public String getVoice(){
      return descriptionVoice;
   }

}
