package nothing.impossible.com.nothing;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.TypefaceSpan;

/**
 * Created by User on 6/1/18.
 */
public class CustomTypefaceSpan extends TypefaceSpan {
    private  final Typeface newType;
    public CustomTypefaceSpan(String family, Typeface newType) {
        super(family);
        this.newType = newType;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
       applyCustomTypeface(ds,newType);
    }

    @Override
    public void updateMeasureState(TextPaint paint) {
        applyCustomTypeface(paint,newType);
    }
   private static void applyCustomTypeface(Paint paint,Typeface tf){
       int oldStyle;
       Typeface old=paint.getTypeface();
       if(old==null){
           oldStyle=0;
       }else{
           oldStyle=old.getStyle();
       }
       int fake=oldStyle&~tf.getStyle();
       if((fake&Typeface.BOLD)!=0){
           paint.setFakeBoldText(true);
       }
       paint.setTypeface(tf);
   }
}
