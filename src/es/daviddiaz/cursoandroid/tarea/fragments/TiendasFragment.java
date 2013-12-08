package es.daviddiaz.cursoandroid.tarea.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import es.daviddiaz.cursoandroid.tarea.R;
import es.daviddiaz.cursoandroid.tarea.activities.MainActivity;

public class TiendasFragment
extends 
  Fragment
implements 
  TabListener
{
  private Fragment[] fragments = {
    new ListadoTiendasFragment(),
    new MapaFragment()
  };

  static final int LISTADO = 0;
  static final int MAPA = 1;

  int indexAnterior = 0;

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    ActionBar bar = ((MainActivity)getActivity()).getSupportActionBar();

    bar.addTab(
        bar.newTab()
        .setText("LISTADO")
        .setTabListener(this));

    bar.addTab(
        bar.newTab()
        .setText("MAPA")
        .setTabListener(this));

    getActivity().getSupportFragmentManager()
    .beginTransaction()
    .add(R.id.mainContent, fragments[LISTADO])
    .add(R.id.mainContent, fragments[MAPA])
    .commit();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_tiendas, container, false);
  }

  @Override
  public void onResume() {
    super.onResume();
    establecerVisibilidad();
  }

  @Override
  public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
  }

  @Override
  public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
  }

  @Override
  public void onTabSelected(Tab tab, FragmentTransaction transaction) {
    setContent(tab.getPosition());
  }

  private void setContent(int index) {
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

  private void establecerVisibilidad() {
    FragmentManager manager = getActivity().getSupportFragmentManager();

    if (googlePlayDisponible()) {
      manager.beginTransaction().show(fragments[MAPA]).commit();
    } else {
      manager.beginTransaction().hide(fragments[MAPA]).commit();
    }
    
  }
  
  private boolean googlePlayDisponible() {
    int resultCode =
        GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());

    if (ConnectionResult.SUCCESS == resultCode) {
      return true;
    } else {
      Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode, 
          getActivity(), 0);

      if (dialog != null) {
        ErrorDialogFragment errorFragment = new ErrorDialogFragment();
        errorFragment.setDialog(dialog);
        errorFragment.show(getActivity().getSupportFragmentManager(), "errorDialog");
      }
      return false;
    }
  }
}
