package es.daviddiaz.cursoandroid.tarea.dominio;

import java.util.ArrayList;

/**
 * Modela la información de un centro comercial.
 * 
 * @author david
 *
 */
public class CentroComercial {
	private ArrayList<Tienda> tiendas = new ArrayList<Tienda>();

	public ArrayList<Tienda> getTiendas() {
		return tiendas;
	}
}
