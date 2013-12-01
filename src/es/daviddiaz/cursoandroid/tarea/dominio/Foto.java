package es.daviddiaz.cursoandroid.tarea.dominio;

import android.graphics.Bitmap;

public class Foto {
  String url;
  Bitmap bitmap;
  String descripcion;
  int favoritos;
  
  public Foto(
    String url,
    Bitmap bitmap,
    String descripcion,
    int favoritos) {
    
    setUrl(url);
    setBitmap(bitmap);
    setDescripcion(descripcion);
    setFavoritos(favoritos);
  }
  
  public Bitmap getBitmap() {
    return bitmap;
  }

  public void setBitmap(Bitmap bitmap) {
    this.bitmap = bitmap;
  }

  public String getUrl() {
    return url;
  }
  public void setUrl(String url) {
    this.url = url;
  }
  public String getDescripcion() {
    return descripcion;
  }
  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }
  public int getFavoritos() {
    return favoritos;
  }
  public void setFavoritos(int favoritos) {
    this.favoritos = favoritos;
  }
}
