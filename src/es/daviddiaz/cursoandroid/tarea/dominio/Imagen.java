package es.daviddiaz.cursoandroid.tarea.dominio;

public class Imagen {
  int resourceId;
  
  public Imagen(int resourceId) {
    setResourceId(resourceId);
  }
  
  public int getResourceId() {
    return resourceId;
  }

  private void setResourceId(int resourceId) {
    this.resourceId = resourceId;
  }
}
