package es.daviddiaz.cursoandroid.tarea.dominio;

public class Comentario {
  int id;
  int tiendaId;
  String texto;
  
  public Comentario() {
  }
  
  public Comentario(int id, int tiendaId, String texto) {
    setId(id);
    setTiendaId(tiendaId);
    setTexto(texto);
  }
  
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public int getTiendaId() {
    return tiendaId;
  }
  public void setTiendaId(int id) {
    this.tiendaId = id;
  }
  public String getTexto() {
    return texto;
  }
  public void setTexto(String texto) {
    this.texto = texto;
  }
}
