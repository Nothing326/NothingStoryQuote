package nothing.impossible.com.nothing.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import nothing.impossible.com.nothing.R;

/**
 * Created by User on 7/15/18.
 */
public class CardsDataAdapter extends ArrayAdapter<String> {
    public Context context;
    public CardsDataAdapter(Context context) {

        super(context, R.layout.card_content);
    }
    Typeface typeface;
    @Override
    public View getView(int position, final View contentView, ViewGroup parent){
        TextView v = (TextView)(contentView.findViewById(R.id.content));

        typeface= Typeface.createFromAsset(getContext().getAssets(), "Myanmar3.ttf");

        v.setText(getItem(position));
        v.setTypeface(typeface);
        return contentView;
    }
}
