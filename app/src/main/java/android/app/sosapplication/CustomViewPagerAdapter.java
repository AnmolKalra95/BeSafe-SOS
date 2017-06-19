package android.app.sosapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by MySelf on 5/14/2017.
 */

public class CustomViewPagerAdapter extends FragmentPagerAdapter {
   ArrayList<Fragment> fragmentArrayList=new ArrayList<>();
    ArrayList<String> titlesArrayList=new ArrayList<>();

    public CustomViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragmentsTitle(Fragment fragment, String titles)
   {
       fragmentArrayList.add(fragment);
       titlesArrayList.add(titles);
   }

    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titlesArrayList.get(position);
    }
}
