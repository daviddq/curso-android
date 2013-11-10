package es.daviddiaz.cursoandroid.tarea.dominio;

/**
 * Modela la información de una tienda. 
 * 
 * @author david
 *
 */
public class Tienda {
	private String nombre;
	private String direccion;
	private String telefono;
	private String[] horarios;
	private String website;
	private String email;
	private int icono;
	private int fotografia;
	
	public Tienda(String nombre, String direccion, String telefono,
			String[] horarios, String website, String email, int icono,
			int fotografia) {
		
		this.nombre = nombre;
		this.direccion = direccion;
		this.telefono = telefono;
		this.horarios = horarios;
		this.website = website;
		this.email = email;
		this.icono = icono;
		this.fotografia = fotografia;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String[] getHorarios() {
		return horarios;
	}
	public void setHorarios(String[] horarios) {
		this.horarios = horarios;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public int getIcono() {
		return icono;
	}

	public void setIcono(int icono) {
		this.icono = icono;
	}

	public int getFotografia() {
		return fotografia;
	}

	public void setFotografia(int fotografia) {
		this.fotografia = fotografia;
	}
}
