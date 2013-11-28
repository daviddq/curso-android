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

  private Fragment[] fragments = {
      new ListadoTiendasFragment(),
      new MapaFragment()
  };
  
  int indexAnterior = 0;

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
    
    getActivity().getSupportFragmentManager()
      .beginTransaction()
      .add(R.id.mainContent, fragments[0])
      .add(R.id.mainContent, fragments[1])
      .commit();    
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
  public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
    // TODO Auto-generated method stub
  }

  @Override
  public void onTabSelected(Tab tab, FragmentTransaction transaction) {
    setContent(tab.getPosition());

  }

  public void setContent(int index) {
    Fragment toHide = null;
    Fragment toShow = null;
    
    index = Math.min(index, fragments.length-1);

    toHide = fragments[indexAnterior];
    toShow = fragments[index];

    getActivity().getSupportFragmentManager()
      .beginTransaction()
      .hide(toHide)
      .show(toShow)
      .commit();

    indexAnterior = index;
  }  
}
