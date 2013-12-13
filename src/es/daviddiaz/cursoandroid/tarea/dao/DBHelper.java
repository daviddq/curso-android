package es.daviddiaz.cursoandroid.tarea.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
  public final static String FIELD_ID = "id";
  public final static String FIELD_NOMBRE = "nombre";
  public final static String FIELD_DIRECCION = "direccion";
  public final static String FIELD_TELEFONO = "telefono";
  public final static String FIELD_EMAIL = "email";
  public final static String FIELD_WEBSITE = "website";
  public final static String FIELD_HORARIOS = "horarios";
  public final static String FIELD_FOTOGRAFIA = "fotografia";
  public final static String FIELD_ICONO = "icono";
  public final static String FIELD_LATITUD = "latitud";
  public final static String FIELD_LONGITUDE = "longitud";
  public final static String FIELD_TIENDA_ID = "tiendaId";
  public final static String FIELD_TEXTO = "texto";

  public final static String TIENDAS_TABLE = "tiendas";
  public final static String COMENTARIOS_TABLE = "comentarios";
  
  private final static String CREATE_TIENDAS = "CREATE TABLE " +
      TIENDAS_TABLE + "(" + 
      FIELD_ID + " integer primary key autoincrement, " +
      FIELD_NOMBRE + " text, " +
      FIELD_DIRECCION + " text, " +
      FIELD_TELEFONO + " text," +
      FIELD_EMAIL + " text, " + 
      FIELD_WEBSITE + " text, "+
      FIELD_HORARIOS + " text, "+
      FIELD_FOTOGRAFIA + " integer, "+
      FIELD_ICONO + " integer, "+
      FIELD_LATITUD + " real, "+
      FIELD_LONGITUDE + " real)";
  
  private final static String CREATE_COMENTARIOS = "CREATE TABLE " +
      COMENTARIOS_TABLE + "(" + 
      FIELD_ID + " integer primary key autoincrement, " +
      FIELD_TIENDA_ID + " integer, " +
      FIELD_TEXTO + " text)";

  public DBHelper(Context context, String name, CursorFactory factory, 
      int version) {
    super(context, name, factory, version);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    crearEsquema(db);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + TIENDAS_TABLE);
    db.execSQL("DROP TABLE IF EXISTS " + COMENTARIOS_TABLE);
    
    crearEsquema(db);
  }
  
  private void crearEsquema(SQLiteDatabase db) {
    db.execSQL(CREATE_TIENDAS);
    db.execSQL(CREATE_COMENTARIOS);
  }
}
