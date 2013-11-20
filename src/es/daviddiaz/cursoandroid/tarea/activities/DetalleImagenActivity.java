package es.daviddiaz.cursoandroid.tarea.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;
import es.daviddiaz.cursoandroid.tarea.R;
import es.daviddiaz.cursoandroid.tarea.dao.CentroComercialDAO;
import es.daviddiaz.cursoandroid.tarea.dominio.Tienda;

public class DetalleImagenActivity extends Activity {
  public final static String ID_TIENDA = "idTienda";

  Tienda tienda = null;
  int indiceTienda = -1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detalle_imagen);

    Intent intent = getIntent();
    indiceTienda = intent.getIntExtra(ID_TIENDA, -1);

    if (indiceTienda!=-1) {
      tienda = CentroComercialDAO.getTiendas().get(indiceTienda);
      if (null!=tienda) {
        ImageView imagen = (ImageView) findViewById(R.id.imagenTienda);
        if (null != imagen)
          imagen.setImageResource(tienda.getFotografia());
      }
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
    // TODO: cambiar
    //case R.id.action_share:
    case 1:
      if (null!=tienda) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/jpg");
        intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("android.resource://"
          + getPackageName() + "/" + tienda.getFotografia()));
        startActivity(Intent.createChooser(intent,
          getString(R.string.action_share)));
      } 
      return true;
    // TODO: cambiar
    //case R.id.action_favorite:
    case 2:
      Toast.makeText(this, "Marcado como favorito", Toast.LENGTH_SHORT).show();
      return true;
    default:
      return super.onOptionsItemSelected(item); 
    }    
  }
}
