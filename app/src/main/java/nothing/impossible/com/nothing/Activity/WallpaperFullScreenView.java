package nothing.impossible.com.nothing.Activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nothing.impossible.com.nothing.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class WallpaperFullScreenView extends Fragment {


    public WallpaperFullScreenView() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wallpaper_full_screen_view, container, false);
    }

}
