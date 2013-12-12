package es.daviddiaz.cursoandroid.tarea.fragments;

import java.util.HashMap;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import es.daviddiaz.cursoandroid.tarea.R;
import es.daviddiaz.cursoandroid.tarea.activities.DetalleTiendaActivity;
import es.daviddiaz.cursoandroid.tarea.dao.CentroComercialDao;
import es.daviddiaz.cursoandroid.tarea.dominio.Tienda;

public class MapaFragment 
extends 
  SupportMapFragment
implements 
  ConnectionCallbacks, 
  OnConnectionFailedListener,
  OnInfoWindowClickListener,
  InfoWindowAdapter

{
  private GoogleMap map=null;
  private Bundle savedInstanceState;

  public static final int MILLISECONDS_PER_SECOND = 1000;
  public static final long UPDATE_INTERVAL_IN_MILLISECONDS = MILLISECONDS_PER_SECOND * 5;
  public static final long FAST_INTERVAL_CEILING_IN_MILLISECONDS = MILLISECONDS_PER_SECOND * 1; 
  public final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
  LocationClient locationClient;
  HashMap<Marker, Tienda> markers_tiendas = new HashMap<Marker, Tienda>();

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.savedInstanceState = savedInstanceState;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) 
  {
    return super.onCreateView(inflater, container, savedInstanceState);
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
    ponerMarkers();
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
          map.setOnInfoWindowClickListener(this);
          map.setInfoWindowAdapter(this);
        }
      }
    }
  }

  private void ponerMarkers() {
    if (markers_tiendas.size()>0)
      return;

    for (Tienda t : CentroComercialDao.getTiendas()) {
      MarkerOptions options = new MarkerOptions()
      .position(t.getLocation())
      .title(t.getNombre())
      .snippet(t.getDireccion());

      Marker marker = map.addMarker(options);
      markers_tiendas.put(marker, t);
    }
  }

  private void actualizarPosicion(Location posicion) {
    if (posicion != null) {
      LatLng coordenadas = new LatLng(posicion.getLatitude(),
          posicion.getLongitude());
      map.moveCamera(CameraUpdateFactory.newLatLngZoom(coordenadas, 13));
    }
  }

  @Override
  public void onInfoWindowClick(Marker marker) {
    try {
      if (markers_tiendas.containsKey(marker)) {
        Tienda tienda = markers_tiendas.get(marker);
        int indice = CentroComercialDao.getTiendas().indexOf(tienda);

        Intent intent = new Intent();
        intent = new Intent(getActivity().getApplicationContext(),
            DetalleTiendaActivity.class);
        intent.putExtra(DetalleTiendaActivity.ID_TIENDA, indice);
        startActivity(intent);
      }
    } catch (Exception e) {}
  }

  @Override
  public View getInfoContents(Marker marker) {
    View window = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE))
        .inflate(R.layout.info_window, null);

    TextView txt_title = (TextView)window.findViewById(R.id.txt_title);
    TextView txt_snippet = (TextView)window.findViewById(R.id.txt_snippet);
    txt_title.setText(marker.getTitle());
    txt_snippet.setText(marker.getSnippet());
    return window;
  }

  @Override
  public View getInfoWindow(Marker marker) {
    return null;
  }  
}
