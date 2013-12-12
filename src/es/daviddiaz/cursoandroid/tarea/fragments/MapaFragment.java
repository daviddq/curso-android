package es.daviddiaz.cursoandroid.tarea.fragments;

import android.app.Dialog;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import es.daviddiaz.cursoandroid.tarea.R;

public class MapaFragment 
extends 
  SupportMapFragment
implements 
  ConnectionCallbacks,
  OnConnectionFailedListener
{
  private GoogleMap map=null;
  private Bundle savedInstanceState;
  
  public static final int MILLISECONDS_PER_SECOND = 1000;
  public static final long UPDATE_INTERVAL_IN_MILLISECONDS = MILLISECONDS_PER_SECOND * 5;
  public static final long FAST_INTERVAL_CEILING_IN_MILLISECONDS = MILLISECONDS_PER_SECOND * 1; 
  public final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
  LocationClient locationClient;
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.savedInstanceState = savedInstanceState;
}
  
  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.menu_mapa, menu);
    super.onCreateOptionsMenu(menu, inflater);
  }
  
  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    
    locationClient = new LocationClient(getActivity(), this, this);
  }
  
  @Override
  public void onResume() {
    super.onResume();
    crearMapa();
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
  
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (null==map) {
      return false;
    }
    
    switch (item.getItemId()) {
      case R.id.mapaNormal:
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        break;
      case R.id.mapaSatelital:
        map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        break;
      case R.id.mapaHibrido:
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        break;
      case R.id.mapaTerreno:
        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        break;
    }
    return super.onOptionsItemSelected(item);
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
      locationClient.disconnect();
    }
  }

  @Override
  public void onConnected(Bundle connectionHint) {
    actualizarPosicion(locationClient.getLastLocation());
  }

  @Override
  public void onDisconnected() {
  }
  
  private void crearMapa() {
    if (null==map) {
      map = getMap();
      if (null!=map) {
        map.getUiSettings().setZoomControlsEnabled(false);
        if (null==savedInstanceState) {
          map.setMyLocationEnabled(true);
        }
      }
    }
  }

  private void actualizarPosicion(Location posicion) {
    if (posicion != null) {
      LatLng coordenadas = new LatLng(posicion.getLatitude(),
        posicion.getLongitude());
      map.moveCamera(CameraUpdateFactory.newLatLngZoom(coordenadas, 10));
    }
  }
}
