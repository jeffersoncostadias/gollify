package com.example.gollify.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gollify.Adapter.TabbedDialogAdapter;
import com.example.gollify.Fragments.TabFragment1;
import com.example.gollify.Fragments.TabFragment2;
import com.example.gollify.R;
import com.example.gollify.DataBase.DataBaseHelper;


public class TabbedDialog extends AppCompatDialogFragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    TextView textView;
    DataBaseHelper myDatabase;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        myDatabase = new DataBaseHelper(getContext());
        setCancelable(false);
        View rootview = inflater.inflate(R.layout.activity_tabdialog, container, false);
        tabLayout = (TabLayout) rootview.findViewById(R.id.tabLayout);
        viewPager = (ViewPager) rootview.findViewById(R.id.masterViewPager);

        TabbedDialogAdapter adapter = new TabbedDialogAdapter(getChildFragmentManager());
        adapter.addFragment("Login", TabFragment1.createInstance(""));
        adapter.addFragment("Cadastro", TabFragment2.createInstance(""));

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


        return rootview;


    }


    @Override
    public void setCancelable(boolean cancelable) {
        super.setCancelable(cancelable);
    }





}