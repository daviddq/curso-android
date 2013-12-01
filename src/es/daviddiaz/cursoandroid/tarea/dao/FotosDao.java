package es.daviddiaz.cursoandroid.tarea.dao;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import es.daviddiaz.cursoandroid.tarea.dominio.Foto;
import es.daviddiaz.cursoandroid.tarea.helpers.AssetsHelper;

public class FotosDao {
  private static List<Foto> fotos = new ArrayList<Foto>();
  private static boolean inicializado = false;

  public static void Inicializar(Context context) {
    if (inicializado)
      return;
    
    cargarDesdeParse(context);

    inicializado = true;
  }
  
  public static List<Foto> getFotos() {
    return fotos;
  }
  
  private static void cargarDesdeParse(final Context context) {
    Parse.initialize(context, "DegLJPhYU4HZxoQLtvOmwb4kBw37ck4CnBG3IiPK", "oF2nL8zBtUzNev406WBv2XUnWz5LupElH7H4Gutt");
    ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("foto");
    query.findInBackground(new FindCallback<ParseObject>() {
      @Override
      public void done(List<ParseObject> poFotos, ParseException ex) {
        if (null==ex) {
          for(ParseObject t : poFotos) {
            fotos.add(
              new Foto(
                t.getString("url"),
                null,
                t.getString("description"),
                t.getInt("favorites")));
          } 
        } else {
          // Si algo falla al cargar el fichero online lo cargo desde un fichero local
          cargarDesdeFicheroLocal(context);
        }
      }
    });
  }
  
  private static void cargarDesdeFicheroLocal(Context context) {
    String json = AssetsHelper.loadJSONFromAsset("fotos.json", context);
    if (null==json)
      return;
    
    try {
      JSONArray jFotos = new JSONArray(json);
      for(int i=0; i<jFotos.length(); ++i) {
        try {
          JSONObject t = (JSONObject)jFotos.get(i);
          fotos.add(
            new Foto(
              t.getString("url"),
              null,
              t.getString("descripcion"),
              t.getInt("favoritos")));
        } catch (Exception e) {
          Log.e("TAG", "Error parseando foto", e);
        }
      }
    } catch (Exception e) {
      Log.e("TAG", "error inesperado", e);
    }
  }  
}
