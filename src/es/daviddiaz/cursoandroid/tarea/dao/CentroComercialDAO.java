package es.daviddiaz.cursoandroid.tarea.dao;

import java.util.ArrayList;
import java.util.List;

import es.daviddiaz.cursoandroid.tarea.R;
import es.daviddiaz.cursoandroid.tarea.dominio.CentroComercial;
import es.daviddiaz.cursoandroid.tarea.dominio.Tienda;

public class CentroComercialDAO {
	private static CentroComercial centroComercial = new CentroComercial();
	private static boolean inicializado = false;
	
	public static void Inicializar() {
		if (inicializado)
			return;
		
		List<Tienda> tiendas = centroComercial.getTiendas();
		
		tiendas.add(
				new Tienda(
						"Tienda de Lego",
						"Dirección de la tienda de Lego",
						"+34111222333",
						new String[] {
								"Lunes-Viernes: 08:00am - 21:00pm",
								"Sabados: 08:00am - 23:00pm",
								"Domingos: 08:00am - 14:00pm",},
						"tiendadelego.centrocomercial.com",
						"tiendadelego@centrocomercial.com",
						R.drawable.lego,
						R.drawable.legostore));

		tiendas.add(
				new Tienda(
						"Tienda de Libros",
						"Dirección de la tienda de Libros",
						"+34 222 333 444",
						new String[] {
								"Lunes-Viernes: 08:00am - 21:00pm",
								"Sabados: 08:00am - 23:00pm",
								"Domingos: 08:00am - 14:00pm",},
						"tiendadelibros.centrocomercial.com",
						"tiendadelibros@centrocomercial.com",
						R.drawable.libro,
						R.drawable.librosstore));

		tiendas.add(
				new Tienda(
						"Tienda de Zapatos",
						"Dirección de la tienda de Zapatos",
						"+34 333 444 555",
						new String[] {
								"Lunes-Viernes: 08:00am - 21:00pm",
								"Sabados: 08:00am - 23:00pm",
								"Domingos: 08:00am - 14:00pm",},
						"tiendadezapatos.centrocomercial.com",
						"tiendadezapatos@centrocomercial.com",
						R.drawable.zapatos,
						R.drawable.zapatossotre));
						

		tiendas.add(
				new Tienda(
						"Tienda de Ropa",
						"Dirección de la tienda de Ropa",
						"+34 444 555 666",
						new String[] {
								"Lunes-Viernes: 08:00am - 21:00pm",
								"Sabados: 08:00am - 23:00pm",
								"Domingos: 08:00am - 14:00pm",},
						"tiendaderopa.centrocomercial.com",
						"tiendaderopa@centrocomercial.com",
						R.drawable.ropa,
						R.drawable.ropastore));

		tiendas.add(
				new Tienda(
						"Tienda de Vinos",
						"Dirección de la tienda de Vinos",
						"+34 555 666 777",
						new String[] {
								"Lunes-Viernes: 08:00am - 21:00pm",
								"Sabados: 08:00am - 23:00pm",
								"Domingos: 08:00am - 14:00pm",},
						"tiendadevinos.centrocomercial.com",
						"tiendadevinos@centrocomercial.com",
						R.drawable.vino,
						R.drawable.vinosstore));
		
		inicializado = true;
	}

	public static ArrayList<Tienda> getTiendas() {
		return centroComercial.getTiendas();
	}
}
