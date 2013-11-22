package es.daviddiaz.cursoandroid.tarea.dao;

import java.util.ArrayList;

import es.daviddiaz.cursoandroid.tarea.R;
import es.daviddiaz.cursoandroid.tarea.dominio.Imagen;
import es.daviddiaz.cursoandroid.tarea.dominio.RepositorioImagenes;
import es.daviddiaz.cursoandroid.tarea.dominio.Tienda;

public class RepositorioImagenesDAO {
	private static RepositorioImagenes imagenes = new RepositorioImagenes();
	private static boolean inicializado = false;
	
	public static void Inicializar() {
		if (inicializado)
			return;
		
		imagenes.getImagenes().add(
		    new Imagen(R.drawable.mall1));
		
		imagenes.getImagenes().add(
		    new Imagen(R.drawable.mall2));
		
		imagenes.getImagenes().add(
		    new Imagen(R.drawable.mall3));
		
		imagenes.getImagenes().add(
		    new Imagen(R.drawable.mall4));
		
		imagenes.getImagenes().add(
		    new Imagen(R.drawable.mall5));
		
		inicializado = true;
	}

	public static ArrayList<Imagen> getImagenes() {
		return imagenes.getImagenes();
	}
}
