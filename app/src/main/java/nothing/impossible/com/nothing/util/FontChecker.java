package nothing.impossible.com.nothing.util;

import android.content.Context;

import nothing.impossible.com.nothing.LocalData;

public class FontChecker {

    public  static String ChoosedFontText(String text, Context context){
        LocalData localData = new LocalData(context);

        if(!localData.isZawGyi()){
           return   Rabbit.zg2uni(text);

        }else{
            return  text;
        }
   }
  public static String UnicodeToZawGyi(String text ,Context context){
      LocalData localData = new LocalData(context);

      if(!localData.isZawGyi()){
          return Rabbit.uni2zg(text);
      }else{
          return text;
      }

  }

}
