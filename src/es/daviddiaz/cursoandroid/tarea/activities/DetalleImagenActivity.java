package es.daviddiaz.cursoandroid.tarea.activities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;
import es.daviddiaz.cursoandroid.tarea.R;
import es.daviddiaz.cursoandroid.tarea.dao.CentroComercialDao;
import es.daviddiaz.cursoandroid.tarea.dominio.Tienda;

public class DetalleImagenActivity 
extends FragmentActivity
implements TiendaProvider
{
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
      tienda = CentroComercialDao.getTiendas().get(indiceTienda);
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
    case R.id.action_share:
      Bitmap bitmap = BitmapFactory.decodeResource(getResources(), tienda.getFotografia());
      File  outputFile = saveBitmap(bitmap);
      if (outputFile==null) {
        Toast.makeText(this, "Error inesperado", Toast.LENGTH_SHORT).show();
      } else {
        Uri uriImage = null;
        uriImage = Uri.fromFile(outputFile);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/png");
        intent.putExtra(Intent.EXTRA_STREAM, uriImage);
        intent.putExtra("sms_body", "Te envío una imagen de la tienda");
        startActivity(Intent.createChooser(intent, getString(R.string.action_share)));
      }
      return true;    
    case R.id.action_favorite:
      Toast.makeText(this, "Marcado como favorito", Toast.LENGTH_SHORT).show();
      return true;
    default:
      return super.onOptionsItemSelected(item); 
    }    
  }

  @Override
  public Tienda getTienda() {
    return tienda;
  }

  private File saveBitmap(Bitmap bmp) {     
    String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
    File file = new File(extStorageDirectory, "imagen.png");
    try {
      if (file.exists()) {
        file.delete();
      }
      OutputStream outStream = new FileOutputStream(file);
      bmp.compress(Bitmap.CompressFormat.PNG, 100, outStream);
      outStream.flush();
      outStream.close();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
    return file;
  }
}
