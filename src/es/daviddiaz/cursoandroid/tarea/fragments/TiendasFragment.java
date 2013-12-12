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

  public static final int TAB_LISTADO = 0;
  public static final int TAB_MAPA = 1;
  
  int tabActual = TAB_LISTADO;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

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
    .add(R.id.mainContent, fragments[TAB_LISTADO])
    .add(R.id.mainContent, fragments[TAB_MAPA])
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

  private void setContent(int tabIndex) {
    tabIndex = Math.min(tabIndex, fragments.length-1);
    
    int tabAnterior = tabActual;
    setTabActual(tabIndex);
    
    Fragment toHide = null;
    Fragment toShow = null;

    toHide = fragments[tabAnterior];
    toShow = fragments[tabActual];
    
    switch (tabActual) {
      case TAB_MAPA:
        setMapMenuEnabled(true);
        break;
      default:
        setMapMenuEnabled(false);
        break;
    }

    getActivity().getSupportFragmentManager()
    .beginTransaction()
    .hide(toHide)
    .show(toShow)
    .commit();
  }
  
  public void setMapMenuEnabled(boolean enabled) {
    if (enabled && tabActual==TAB_MAPA) {
      fragments[TAB_MAPA].setHasOptionsMenu(true);
    } else {
      fragments[TAB_MAPA].setHasOptionsMenu(false);
    }
  }

  private void establecerVisibilidad() {
    FragmentManager manager = getActivity().getSupportFragmentManager();
    if (googlePlayDisponible()) {
      manager.beginTransaction().show(fragments[TAB_MAPA]).commit();
    } else {
      manager.beginTransaction().hide(fragments[TAB_MAPA]).commit();
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
  
  private void setTabActual(int tabActual) {
    this.tabActual = tabActual;
  }

  public int getTabActual() {
    return tabActual;
  }
}
