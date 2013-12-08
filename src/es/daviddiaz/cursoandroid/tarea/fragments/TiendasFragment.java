package es.daviddiaz.cursoandroid.tarea.fragments;

import android.app.Dialog;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

import es.daviddiaz.cursoandroid.tarea.R;
import es.daviddiaz.cursoandroid.tarea.activities.MainActivity;

public class TiendasFragment
extends 
  Fragment
implements 
  TabListener,
  ConnectionCallbacks,
  OnConnectionFailedListener,
  LocationListener
{
  private Fragment[] fragments = {
    new ListadoTiendasFragment(),
    new MapaFragment()
  };

  static final int LISTADO = 0;
  static final int MAPA = 1;

  public static final int MILLISECONDS_PER_SECOND = 1000;
  public static final long UPDATE_INTERVAL_IN_MILLISECONDS = MILLISECONDS_PER_SECOND * 5;
  public static final long FAST_INTERVAL_CEILING_IN_MILLISECONDS = MILLISECONDS_PER_SECOND * 1; 
  public final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
  LocationClient locationClient;
  LocationRequest locationRequest;

  int indexAnterior = 0;

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
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
    .add(R.id.mainContent, fragments[LISTADO])
    .add(R.id.mainContent, fragments[MAPA])
    .commit();

    locationClient = new LocationClient(getActivity(), this, this);
    locationRequest = LocationRequest.create();
    
    locationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
    locationRequest.setFastestInterval(FAST_INTERVAL_CEILING_IN_MILLISECONDS);
    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
  }

  @Override
  public void onResume() {
    super.onResume();

    FragmentManager manager = getActivity().getSupportFragmentManager();

    if (serviciosConectados()) {
      manager.beginTransaction().show(fragments[MAPA]).commit();
    } else {
      manager.beginTransaction().hide(fragments[MAPA]).commit();
    }
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

  @Override
  public void onConnectionFailed(ConnectionResult connectionResult) {
    if (connectionResult.hasResolution()) {
      try {
        connectionResult.startResolutionForResult(
            getActivity(),
            CONNECTION_FAILURE_RESOLUTION_REQUEST);
      } catch (IntentSender.SendIntentException e) {
        Log.e("ERROR",Log.getStackTraceString(e));
      }
    } else {
      Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
          connectionResult.getErrorCode(),
          getActivity(),
          CONNECTION_FAILURE_RESOLUTION_REQUEST);

      if (errorDialog != null) {
        ErrorDialogFragment errorFragment = new ErrorDialogFragment();
        errorFragment.setDialog(errorDialog);
        errorFragment.show(getActivity().getSupportFragmentManager(), "dialog");
      }
    }
  }

  private boolean serviciosConectados() {
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

  @Override
  public void onStart() {
    super.onStart();
    locationClient.connect();
  }

  @Override
  public void onStop() {
    super.onStop();
    if (locationClient.isConnected()) {
      locationClient.removeLocationUpdates(this);
      locationClient.disconnect();
    }
  }

  @Override
  public void onConnected(Bundle connectionHint) {
    actualizarPosicion(locationClient.getLastLocation());
    locationClient.requestLocationUpdates(locationRequest, this);
  }

  @Override
  public void onDisconnected() {
  }
  
  @Override
  public void onLocationChanged(Location location) {
    actualizarPosicion(location);
  }

  public void actualizarPosicion(Location currentLocation ) {
    String latLng = "No disponible";
    if (currentLocation != null) {
      latLng = "Lat:" + currentLocation.getLatitude() 
        + " lon:"+ currentLocation.getLongitude();
    }
    Log.d("GPS", latLng);
  }
}
