package es.daviddiaz.cursoandroid.tarea.helpers;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;

public class AssetsHelper {
  public static String loadJSONFromAsset(String fileName, Context context) {
    String json = null;
    try {
      InputStream is = context.getAssets().open(fileName);
      int size = is.available();
      byte[] buffer = new byte[size];
      is.read(buffer);
      is.close();
      json = new String(buffer, "UTF-8");
    } catch (IOException ex) {
      ex.printStackTrace();
      return null;
    }
    return json;
  }
}
