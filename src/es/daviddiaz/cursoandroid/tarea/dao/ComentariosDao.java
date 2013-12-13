package es.daviddiaz.cursoandroid.tarea.dao;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import es.daviddiaz.cursoandroid.tarea.dominio.Comentario;
import es.daviddiaz.cursoandroid.tarea.helpers.AssetsHelper;

public class ComentariosDao {
  private static List<Comentario> comentarios = new ArrayList<Comentario>();
  private static boolean inicializado = false;

  public static void Inicializar(Context context) {
    if (inicializado)
      return;
    
    if (!cargarDesdeBaseDeDatos(context)) {
      cargarDesdeFicheroLocal(context);
      crearBaseDeDatos(context);
    }
    inicializado = true;
  }
  
  private static void crearBaseDeDatos(Context context) {
    DBAdapter db = new DBAdapter(context);
    for (Comentario c : comentarios) {
      db.insert(c);
    }
  }

  private static boolean cargarDesdeBaseDeDatos(Context context) {
    try {
      DBAdapter db = new DBAdapter(context);
      List<Comentario> comentarios = db.getComentarios();
      if (comentarios.size()>0) {
        getComentarios().addAll(comentarios);
        return true;
      } else {
        return false;
      }
    } catch (Exception e) {
      return false;
    }
  }

  public static List<Comentario> getComentarios() {
    return comentarios;
  }
  
  private static void cargarDesdeFicheroLocal(Context context) {
    String json = AssetsHelper.loadJSONFromAsset("comentarios.json", context);
    if (null==json)
      return;
    
    try {
      JSONArray jComentarios = new JSONArray(json);
      for(int i=0; i<jComentarios.length(); ++i) {
        try {
          JSONObject t = (JSONObject)jComentarios.get(i);
          Comentario comentario = new Comentario();
          comentario.setTiendaId(t.getInt("tiendaId"));
          comentario.setTexto(t.getString("texto"));
          comentarios.add(comentario);
        } catch (Exception e) {
          Log.e("TAG", "Error parseando comentario", e);
        }
      }
    } catch (Exception e) {
      Log.e("TAG", "error inesperado", e);
    }
  }  
}
