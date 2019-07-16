package nothing.impossible.com.nothing.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nothing.impossible.com.nothing.R;
import nothing.impossible.com.nothing.util.FontChecker;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteFragmentViewPager extends Fragment {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    Context context;
    public FavouriteFragmentViewPager(){

    }
    public FavouriteFragmentViewPager(Context context){
        this.context=context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_favourite_fragment_view_pager, container, false);
        viewPager = (ViewPager)view.findViewById(R.id.viewpager1);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs1);
        tabLayout.setupWithViewPager(viewPager);

        TextView tabOne=(TextView)LayoutInflater.from(context).inflate(R.layout.custom_tab,null);
        tabOne.setText(FontChecker.ChoosedFontText(getString(R.string.stories),context));
        TextView tabTwo=(TextView)LayoutInflater.from(context).inflate(R.layout.custom_tab,null);
        tabTwo.setText(FontChecker.ChoosedFontText(getString(R.string.quotes),context));

        tabLayout.getTabAt(0).setCustomView(tabOne);
        tabLayout.getTabAt(1).setCustomView(tabTwo);
        return  view;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
        adapter.addFragment(new FavouriteFragment(), "");
        adapter.addFragment(new FavouriteFragmentQuote(), "");



        viewPager.setAdapter(adapter);
    }
    class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
