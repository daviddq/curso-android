package es.daviddiaz.cursoandroid.tarea;

import es.daviddiaz.cursoandroid.tarea.dao.CentroComercialDAO;
import es.daviddiaz.cursoandroid.tarea.dominio.Tienda;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;

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
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.image_detail, menu);
        return true;
    }
    
}
