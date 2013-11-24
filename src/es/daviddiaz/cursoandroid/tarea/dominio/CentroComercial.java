package es.daviddiaz.cursoandroid.tarea.dominio;

import java.util.ArrayList;
import java.util.List;

/**
 * Modela la información de un centro comercial.
 * 
 * @author david
 *
 */
public class CentroComercial {
	private List<Tienda> tiendas = new ArrayList<Tienda>();

	public List<Tienda> getTiendas() {
		return tiendas;
	}
}
