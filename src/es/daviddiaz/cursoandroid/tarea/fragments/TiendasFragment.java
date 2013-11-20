package es.daviddiaz.cursoandroid.tarea.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import es.daviddiaz.cursoandroid.tarea.R;
import es.daviddiaz.cursoandroid.tarea.activities.MainActivity;

public class TiendasFragment
extends Fragment
implements TabListener {
  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    // TODO Auto-generated method stub
    super.onActivityCreated(savedInstanceState);
    
    ActionBar bar = ((MainActivity)getActivity()).getSupportActionBar();

    bar.addTab(
        bar.newTab()
        //.setText(getString(R.string.title_fragment_list)) // TODO: textos
        .setText("LISTADO")
        .setTabListener(this));

    bar.addTab(
        bar.newTab()
        //.setText(getString(R.string.title_fragment_flags)) // TODO: textos
        .setText("MAPA")
        .setTabListener(this));
    
  }
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_tiendas, container, false);
  }
  
  @Override
  public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
    // TODO Auto-generated method stub
  }

  @Override
  public void onTabSelected(Tab tab, FragmentTransaction transaction) {
    // TODO Auto-generated method stub
  }

  @Override
  public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
    // TODO Auto-generated method stub
    
  }  
}
