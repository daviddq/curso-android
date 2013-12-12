package es.daviddiaz.cursoandroid.tarea.dao;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import es.daviddiaz.cursoandroid.tarea.dominio.CentroComercial;
import es.daviddiaz.cursoandroid.tarea.dominio.Tienda;
import es.daviddiaz.cursoandroid.tarea.helpers.AssetsHelper;

public class CentroComercialDao {
  private static CentroComercial centroComercial = new CentroComercial();
  private static boolean inicializado = false;

  public static void Inicializar(Context context) {
    if (inicializado)
      return;
    
//    cargarDesdeParse(context);
    cargarDesdeFicheroLocal(context);

    inicializado = true;
  }
  
  public static List<Tienda> getTiendas() {
    return centroComercial.getTiendas();
  }
  
  private static void cargarDesdeParse(final Context context) {
    Parse.initialize(context, "DegLJPhYU4HZxoQLtvOmwb4kBw37ck4CnBG3IiPK", "oF2nL8zBtUzNev406WBv2XUnWz5LupElH7H4Gutt");
    ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("tienda");
    query.findInBackground(new FindCallback<ParseObject>() {
      @Override
      public void done(List<ParseObject> tiendas, ParseException ex) {
        if (null==ex) {
          for(ParseObject t : tiendas) {
            centroComercial.getTiendas().add(
              new Tienda(
                t.getString("nombre"),
                t.getString("direccion"),
                t.getString("telefono"),
                t.getString("horario"),
                t.getString("web"),
                t.getString("email"),
                context.getResources().getIdentifier(t.getString("icono"), "drawable", context.getPackageName()),
                context.getResources().getIdentifier(t.getString("imagen"), "drawable", context.getPackageName()),
                t.getNumber("latitud").doubleValue(),
                t.getNumber("longitud").doubleValue()));
          } 
        } else {
          // Si algo falla al cargar el fichero online lo cargo desde un fichero local
          cargarDesdeFicheroLocal(context);
        }
      }
    });
  }
  
  private static void cargarDesdeFicheroLocal(Context context) {
    String json = AssetsHelper.loadJSONFromAsset("tiendas.json", context);
    if (null==json)
      return;
    
    try {
      JSONArray jTiendas = new JSONArray(json);
      for(int i=0; i<jTiendas.length(); ++i) {
        try {
          JSONObject t = (JSONObject)jTiendas.get(i);
          centroComercial.getTiendas().add(
            new Tienda(
              t.getString("nombre"),
              t.getString("direccion"),
              t.getString("telefono"),
              t.getString("horario"),
              t.getString("web"),
              t.getString("email"),
              context.getResources().getIdentifier(t.getString("icono"), "drawable", context.getPackageName()),
              context.getResources().getIdentifier(t.getString("imagen"), "drawable", context.getPackageName()),
              t.getDouble("latitud"),
              t.getDouble("longitud")));
        } catch (Exception e) {
          Log.e("TAG", "Error parseando tienda", e);
        }
      }
    } catch (Exception e) {
      Log.e("TAG", "error inesperado", e);
    }
  }  
}
