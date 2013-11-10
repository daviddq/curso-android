package es.daviddiaz.cursoandroid.tarea;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import es.daviddiaz.cursoandroid.tarea.dao.CentroComercialDAO;
import es.daviddiaz.cursoandroid.tarea.dominio.Tienda;

public class DetalleTiendaActivity extends Activity {
	public final static String ID_TIENDA = "idTienda";

	Tienda tienda = null;
	int indiceTienda = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalle_tienda);

		Intent intent = getIntent();
		indiceTienda = intent.getIntExtra(ID_TIENDA, -1);
		if (indiceTienda != -1) {
			tienda = CentroComercialDAO.getTiendas().get(indiceTienda);
			if (null != tienda) {
				ImageView icono = (ImageView) findViewById(R.id.iconoTienda);
				if (null != icono)
					icono.setImageResource(tienda.getIcono());

				TextView nombre = (TextView) findViewById(R.id.nombreTienda);
				if (null != nombre)
					nombre.setText(tienda.getNombre());

				TextView direccion = (TextView) findViewById(R.id.direccionTienda);
				if (null != direccion)
					direccion.setText(tienda.getDireccion());

				TextView telefono = (TextView) findViewById(R.id.telefonoTienda);
				if (null != telefono) {
					telefono.setText(tienda.getTelefono());
					Linkify.addLinks(telefono, Linkify.ALL);
				}

				TextView email = (TextView) findViewById(R.id.emailTienda);
				if (null != email) {
					email.setText(tienda.getEmail());
					Linkify.addLinks(email, Linkify.ALL);
				}

				TextView site = (TextView) findViewById(R.id.siteTienda);
				if (null != site) {
					site.setText(tienda.getWebsite());
					Linkify.addLinks(site, Linkify.ALL);
				}

				TextView horario = (TextView) findViewById(R.id.horarioTienda);
				if (null != horario) {
					StringBuffer sb = new StringBuffer();
					for (String h : tienda.getHorarios()) {
						sb.append(h + "\r\n");
					}
					horario.setText(sb.toString());
				}

				Button botonLlamada = (Button) findViewById(R.id.btnLlamar);
				if (null != botonLlamada) {
					botonLlamada.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							if (null != tienda) {
								String uri = "tel:" + tienda.getTelefono();
								Intent intent = new Intent(Intent.ACTION_DIAL);
								intent.setData(Uri.parse(uri));
								startActivity(intent);
							} else {
								Toast.makeText(
										getApplicationContext(),
										"Error: no se puede realizar la llamada",
										Toast.LENGTH_SHORT).show();
							}
						}
					});
				}

				Button botonImagen = (Button) findViewById(R.id.btnImagen);
				if (null != botonImagen) {
					botonImagen.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {

							if (indiceTienda != -1) {
								Intent intent = new Intent();
								intent = new Intent(getApplicationContext(),
										DetalleImagenActivity.class);
								intent.putExtra(
										DetalleImagenActivity.ID_TIENDA,
										indiceTienda);
								startActivity(intent);
							}
						}
					});
				}
			}
		} else {
			tienda = null;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.detalle_tienda, menu);
		return true;
	}
}
