package github.chenupt.calendar.multiplemodel.viewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenupt@gmail.com on 2014/8/9.
 * Description TODO
 */
public class PagerModelFactory {

    public final static String DATA = "data";

    private List<CharSequence> titleList;
    private List<Fragment> fragmentList;

    public PagerModelFactory() {
        titleList = new ArrayList<CharSequence>();
        fragmentList = new ArrayList<Fragment>();
    }

    public Fragment getItem(int position){
        return fragmentList.get(position);
    }

    public int getFragmentCount(){
        return fragmentList.size();
    }

    public boolean hasTitles(){
        return titleList.size() != 0;
    }

    public CharSequence getTitle(int position){
        return titleList.get(position);
    }

    public PagerModelFactory addFragment(Fragment fragment, CharSequence title){
        titleList.add(title);
        addFragment(fragment);
        return this;
    }

    public PagerModelFactory addFragment(Fragment fragment){
        fragmentList.add(fragment);
        return this;
    }

    public PagerModelFactory addCommonFragment(Class<?> c, List<? extends Serializable> list, List<String> titleList){
        this.titleList.addAll(titleList);
        addCommonFragment(c, list);
        return this;
    }

    public PagerModelFactory addCommonFragment(Class<?> c, List<? extends Serializable> list){
        try {
            for(int i = 0; i < list.size(); i ++){
                Fragment fragment = (Fragment) c.newInstance();
                Bundle bundle = new Bundle();
                bundle.putSerializable(DATA, list.get(i));
                fragment.setArguments(bundle);
                fragmentList.add(fragment);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return this;
    }

    public PagerModelFactory addCommonFragment(List<? extends Fragment> list){
        fragmentList.addAll(list);
        return this;
    }

    public PagerModelFactory addCommonFragment(List<? extends Fragment> list, List<String> titleList){
        this.titleList.addAll(titleList);
        addCommonFragment(list);
        return this;
    }




}
