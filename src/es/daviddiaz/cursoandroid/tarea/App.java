package es.daviddiaz.cursoandroid.tarea;

import android.app.Application;
import es.daviddiaz.cursoandroid.tarea.dao.CentroComercialDao;
import es.daviddiaz.cursoandroid.tarea.dao.FotosDao;

public class App extends Application {
  @Override
  public void onCreate() {
    CentroComercialDao.Inicializar(this);
    FotosDao.Inicializar(this);
    super.onCreate();
  }
}
