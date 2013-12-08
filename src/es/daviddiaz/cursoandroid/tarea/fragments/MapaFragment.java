package es.daviddiaz.cursoandroid.tarea.fragments;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class MapaFragment 
extends 
  SupportMapFragment
{
  private GoogleMap map=null;
  private Bundle savedInstanceState;
  public static final LatLng ALICANTE= new LatLng(38.34600, -0.49069);
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.savedInstanceState = savedInstanceState;
  }
  
  @Override
  public void onResume() {
    super.onResume();
    crearMapa();
  }
  
  private void crearMapa() {
    if (null==map) {
      map = getMap();
      if (null!=map) {
        map.getUiSettings().setZoomControlsEnabled(false);
        if (null==savedInstanceState) {
          map.moveCamera(CameraUpdateFactory.newLatLngZoom(ALICANTE, 10));
          map.setMyLocationEnabled(true);
        }
      }
    }
  }
}
