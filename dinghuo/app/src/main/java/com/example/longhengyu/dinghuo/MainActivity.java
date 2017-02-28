package com.example.longhengyu.dinghuo;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.longhengyu.dinghuo.Home.HomeFragment;
import com.example.longhengyu.dinghuo.Person.PersonFragment;
import com.example.longhengyu.dinghuo.Shopping.ShoppingFragment;
import com.example.longhengyu.dinghuo.SuperMark.SuperMarkFragment;
import com.example.longhengyu.dinghuo.Tools.ActivityCollector;
import com.example.longhengyu.dinghuo.Tools.BaseActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private FragmentManager fragmentManager;
    private HomeFragment homeFragment;
    private SuperMarkFragment markFragment;
    private ShoppingFragment shoppingFragment;
    private PersonFragment personFragment;

    private TextView HomeText;
    private TextView MarkText;
    private TextView ShoppingText;
    private TextView PersonText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView(){

        HomeText = (TextView)findViewById(R.id.Main_Home);
        MarkText = (TextView)findViewById(R.id.Main_Mark);
        ShoppingText = (TextView)findViewById(R.id.Main_Shopping);
        PersonText = (TextView)findViewById(R.id.Main_Person);
        HomeText.setOnClickListener(this);
        MarkText.setOnClickListener(this);
        ShoppingText.setOnClickListener(this);
        PersonText.setOnClickListener(this);
        fragmentManager = getFragmentManager();
        shoppingFragment = new ShoppingFragment();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.Main_FrameLayout,shoppingFragment);
        ft.commit();
        ShoppingText.setSelected(true);

    }

    private void setAllTextViewSelect(){

        HomeText.setSelected(false);
        MarkText.setSelected(false);
        ShoppingText.setSelected(false);
        PersonText.setSelected(false);

    }

    private void hidenAllFragment(FragmentTransaction fragmentTransaction){

        if(homeFragment!=null){
            fragmentTransaction.hide(homeFragment);
        }
        if(shoppingFragment!=null){
            fragmentTransaction.hide(shoppingFragment);
        }
        if(markFragment!=null){
            fragmentTransaction.hide(markFragment);
        }
        if(personFragment!=null){
            fragmentTransaction.hide(personFragment);
        }

    }

    @Override
    public void onClick(View v) {

        FragmentTransaction ft = fragmentManager.beginTransaction();
        hidenAllFragment(ft);
        setAllTextViewSelect();
        switch (v.getId()){
            case R.id.Main_Home:
                HomeText.setSelected(true);
                if(homeFragment==null){
                    homeFragment = new HomeFragment();
                    ft.add(R.id.Main_FrameLayout,homeFragment);
                }else {
                    ft.show(homeFragment);
                }
                break;
            case R.id.Main_Mark:
                MarkText.setSelected(true);
                if(markFragment==null){
                    markFragment = new SuperMarkFragment();
                    ft.add(R.id.Main_FrameLayout,markFragment);
                }else {
                    ft.show(markFragment);
                }
                break;
            case R.id.Main_Shopping:
                ShoppingText.setSelected(true);
                if(shoppingFragment == null){
                    shoppingFragment = new ShoppingFragment();
                    ft.add(R.id.Main_FrameLayout,shoppingFragment);
                }else {
                    ft.show(shoppingFragment);
                }
                break;
            case R.id.Main_Person:
                PersonText.setSelected(true);
                if(personFragment == null){
                    personFragment = new PersonFragment();
                    ft.add(R.id.Main_FrameLayout,personFragment);
                }else {
                    ft.show(personFragment);
                }
                break;
        }
        ft.commit();
    }

}
