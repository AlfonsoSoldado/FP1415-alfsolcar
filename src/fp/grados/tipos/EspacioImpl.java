package fp.grados.tipos;

import fp.grados.excepciones.ExcepcionEspacioNoValido;

public class EspacioImpl implements Espacio {

	private TipoEspacio tipo;
	private String nombre;
	private Integer capacidad;
	private Integer planta;
	
	// “A0.10,0,100,TEORIA”
	public EspacioImpl(String s){
		String [] trozos = s.split(",");
		if(trozos.length != 4){
			throw new IllegalArgumentException();
		}
		checkCapacidad(new Integer(trozos[2].trim()));
		this.nombre = trozos[0].trim();
		this.planta = new Integer(trozos[1].trim());
		this.capacidad = new Integer(trozos[2].trim());
		this.tipo = TipoEspacio.valueOf(trozos[3].trim());
	}
	
	public EspacioImpl(TipoEspacio tipo, String nombre, Integer capacidad, Integer planta){
		checkCapacidad(capacidad);
		this.tipo = tipo;
		this.nombre = nombre;
		this.capacidad = capacidad;
		this.planta = planta;
	}
	
	private static void checkCapacidad(Integer capacidad){
		if(capacidad <= 0){
			throw new ExcepcionEspacioNoValido("La capacidad debe ser mayor a 0");
		}
	}

	public TipoEspacio getTipo() {
		return tipo;
	}

	public String getNombre() {
		return nombre;
	}

	public Integer getCapacidad() {
		return capacidad;
	}

	public Integer getPlanta() {
		return planta;
	}

	public void setTipo(TipoEspacio tipo) {
		this.tipo = tipo;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setCapacidad(Integer capacidad) {
		checkCapacidad(capacidad);
		this.capacidad = capacidad;
	}
	
	public boolean equals(Object obj) {
		boolean res = false;
		if (obj instanceof Espacio) {
			Espacio esp = (Espacio) obj;
			res = getNombre().equals(esp.getNombre())
					&& getPlanta().equals(esp.getPlanta());
		}
		return res;
	}

	public int hashCode() {
		return getNombre().hashCode() + getPlanta().hashCode() * 31;	
	}

	public int compareTo(Espacio esp) {
		int res = getPlanta().compareTo(esp.getPlanta());
		if (res == 0) {
			res = getNombre().compareTo(esp.getNombre());
		}
		return res;
	}
	
	public String toString(){
		return getNombre()+" (Planta "+getPlanta()+")";
	}

}
