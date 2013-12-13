package es.daviddiaz.cursoandroid.tarea.dao;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import es.daviddiaz.cursoandroid.tarea.dominio.Tienda;

public class DBAdapter {
  private DBHelper dbHelper;
  private static final String DATABASE_NAME = "centrocomercial.db";
  private static final int DATABASE_VERSION = 1;

  public DBAdapter (Context context){
    dbHelper = new DBHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  public void insert(Tienda teinda){
    ContentValues values = buildContentValuesFromPlace(teinda);
    SQLiteDatabase db = dbHelper.getWritableDatabase();
    try {
      db.insertWithOnConflict(DBHelper.TIENDAS_TABLE, null, values, 
          SQLiteDatabase.CONFLICT_IGNORE);
    } finally {
      db.close();
    }
  }

  public ArrayList<Tienda> getTiendas(){
    ArrayList<Tienda> tiendas= new ArrayList<Tienda>();
    Cursor cursor = null;
    try {
      SQLiteDatabase db = dbHelper.getReadableDatabase();
      cursor = db.query(DBHelper.TIENDAS_TABLE, null, null, null, null, null,
          null);

      while (cursor.moveToNext()) {
        Tienda tienda = new Tienda();
        tienda.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.FIELD_ID)));
        tienda.setNombre(cursor.getString(cursor.getColumnIndex(DBHelper.FIELD_NOMBRE)));
        tienda.setDireccion(cursor.getString(cursor.getColumnIndex(DBHelper.FIELD_DIRECCION)));
        tienda.setTelefono(cursor.getString(cursor.getColumnIndex(DBHelper.FIELD_TELEFONO)));
        tienda.setEmail(cursor.getString(cursor.getColumnIndex(DBHelper.FIELD_EMAIL)));
        tienda.setWebsite(cursor.getString(cursor.getColumnIndex(DBHelper.FIELD_WEBSITE)));
        tienda.setHorarios(cursor.getString(cursor.getColumnIndex(DBHelper.FIELD_HORARIOS)));
        tienda.setFotografia(cursor.getInt(cursor.getColumnIndex(DBHelper.FIELD_FOTOGRAFIA)));
        tienda.setIcono(cursor.getInt(cursor.getColumnIndex(DBHelper.FIELD_ICONO)));
        tienda.setLocation(
            cursor.getDouble(cursor.getColumnIndex(DBHelper.FIELD_LATITUD)),
            cursor.getDouble(cursor.getColumnIndex(DBHelper.FIELD_LONGITUDE)));
        tiendas.add(tienda);
      }
    } finally {
      if (null!=cursor) {
        cursor.close();
      }
    }
    return tiendas;
  }

  public int countTiendas() {
    int total = 0;
    Cursor cursor=null;
    try {
      SQLiteDatabase db = dbHelper.getReadableDatabase();
      cursor = db.query(DBHelper.TIENDAS_TABLE, null, null, null, null,
          null, null);
      total = cursor.getCount();
    } finally {
      if (null!=cursor)
        cursor.close();
    }
    return total;
  }

  public ContentValues buildContentValuesFromPlace (Tienda tienda) {
    ContentValues values = new ContentValues();
    values.put(DBHelper.FIELD_NOMBRE, tienda.getNombre());
    values.put(DBHelper.FIELD_DIRECCION, tienda.getDireccion());
    values.put(DBHelper.FIELD_TELEFONO, tienda.getTelefono());
    values.put(DBHelper.FIELD_EMAIL, tienda.getEmail());
    values.put(DBHelper.FIELD_WEBSITE, tienda.getWebsite());
    values.put(DBHelper.FIELD_HORARIOS, tienda.getHorarios());
    values.put(DBHelper.FIELD_FOTOGRAFIA, tienda.getFotografia());
    values.put(DBHelper.FIELD_ICONO, tienda.getIcono());
    values.put(DBHelper.FIELD_LATITUD, tienda.getLocation().latitude);
    values.put(DBHelper.FIELD_LONGITUDE, tienda.getLocation().longitude);
    return values;
  }
}
