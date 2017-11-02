package fp.grados.utiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

import fp.grados.tipos.Alumno;
import fp.grados.tipos.AlumnoImpl;
import fp.grados.tipos.AlumnoImpl2;
import fp.grados.tipos.Asignatura;
import fp.grados.tipos.AsignaturaImpl;
import fp.grados.tipos.Beca;
import fp.grados.tipos.BecaImpl;
import fp.grados.tipos.Categoria;
import fp.grados.tipos.Centro;
import fp.grados.tipos.CentroImpl;
import fp.grados.tipos.CentroImpl2;
import fp.grados.tipos.Departamento;
import fp.grados.tipos.DepartamentoImpl;
import fp.grados.tipos.DepartamentoImpl2;
import fp.grados.tipos.Despacho;
import fp.grados.tipos.DespachoImpl;
import fp.grados.tipos.Espacio;
import fp.grados.tipos.EspacioImpl;
import fp.grados.tipos.Grado;
import fp.grados.tipos.GradoImpl;
import fp.grados.tipos.GradoImpl2;
import fp.grados.tipos.Nota;
import fp.grados.tipos.Profesor;
import fp.grados.tipos.ProfesorImpl;
import fp.grados.tipos.ProfesorImpl2;
import fp.grados.tipos.TipoAsignatura;
import fp.grados.tipos.TipoBeca;
import fp.grados.tipos.TipoEspacio;
import fp.grados.tipos.Tutoria;

public class Grados {
	
	private static Map<String, Asignatura> asignaturasCreadas = new HashMap<String, Asignatura>();
	private static Set<Beca> becasCreadas = new HashSet<Beca>();
	private static Set<Departamento> departamentosCreados = new HashSet<Departamento>();
	private static Boolean usarImplementacionMapProfesor = false;
	private static Set<Profesor> profesoresCreados = new HashSet<Profesor>();
	private static Set<Alumno> alumnosCreados = new HashSet<Alumno>();
	private static Set<Centro> centrosCreados = new HashSet<Centro>();
	private static SortedSet<Espacio> espaciosCreados = new TreeSet<Espacio>();
	private static Set<Grado> gradosCreados = new HashSet<Grado>();
	private static Boolean usarJava8 = false;
	
	/**-----------------------------------------------------------------------------------------------------------**/
	public static <T> List<T> leeFichero(String nombreFichero, Function<String,T> funcion_deString_aT) {
		List<T> res = null;
		try {
			res = Files.lines(Paths.get(nombreFichero))
					 .map(funcion_deString_aT)
					 .collect(Collectors.toList());
		} catch (IOException e) {
			System.out.println("Error en lectura del fichero: "+nombreFichero);
		}
		
		return res;
	}
	/**-----------------------------------------------------------------------------------------------------------**/
	
	/**ALUMNO**/
	
	//Alumno con parametros
	public static Alumno createAlumno(String dni, String nombre, String apellidos, LocalDate fechaNacimiento, String email){
		Alumno a;
		if(usarJava8){
			a = new AlumnoImpl2(dni, nombre, apellidos, fechaNacimiento, email);
			actualizarPobsAlumno(a);
		} else {
			a = new AlumnoImpl(dni, nombre, apellidos, fechaNacimiento, email);
			actualizarPobsAlumno(a);
		}
		return a;
	}

	//Alumno con String
	public static Alumno createAlumno(String s){
		Alumno a;
		if(usarJava8){
			a = new AlumnoImpl2(s);
			actualizarPobsAlumno(a);
		} else {
			a = new AlumnoImpl(s);
			actualizarPobsAlumno(a);
		}
		return a;
	}
	
	//Alumno con copia
	public static Alumno createAlumno(Alumno original){
		Alumno a;
		a = createAlumno(original.getDNI(), original.getNombre(), original.getApellidos(), original.getFechaNacimiento(), original.getEmail());
		for(Asignatura asig: original.getAsignaturas()){
			a.matriculaAsignatura(asig);;
		}
		for(Nota nota: original.getExpediente().getNotas()){
			a.evaluaAlumno(nota.getAsignatura(), nota.getCursoAcademico(), nota.getConvocatoria(), nota.getValor());
		}
		return a;
	}
	
	//Alumno por fichero
	public static List<Alumno> createAlumnos(String file){
		List<Alumno> a;
		a = leeFichero(file, x->createAlumno(x));
		
		return a;
	}
	
	
	private static void actualizarPobsAlumno(Alumno a) {
		alumnosCreados.add(a);
	}
	
	//getNumAlumnosCreados, getAlumnosCreados
	
	public static Integer getNumAlumnosCreados(){
		return alumnosCreados.size();
	}
	
	public static Set<Alumno> getAlumnosCreados(){
		return new HashSet<Alumno>(alumnosCreados);
	}
	
	/**ASIGNATURAS**/
	
	//Asignatura con parametros
	public static Asignatura createAsignatura(String nombre, String codigo, Double creditos, TipoAsignatura tipo, Integer curso, Departamento departamento){
		Asignatura a;
		a = new AsignaturaImpl(nombre, codigo, creditos, tipo, curso, departamento);
		actualizarPobsAsignatura(a);
		return a;
	}

	//Asignatura con String
	public static Asignatura createAsignatura(String s){
		Asignatura a;
		a = new AsignaturaImpl(s);
		actualizarPobsAsignatura(a);
		return a;
	}
	
	//Asignatura con fichero
	public static List<Asignatura> createAsignaturas(String file){
		List<Asignatura> res = leeFichero(file, x->createAsignatura(x)); 	//No hace falta usar actualizarPobsAsignatura() 
																		 	//porque ya va dentro del createAsignatura que hemos usado 
																			//dentro de la funcion
		return res;
	}
	
	private static void actualizarPobsAsignatura(Asignatura a) {
		asignaturasCreadas.put(a.getCodigo(), a);
		
	}
	
	//getNumAsignaturasCreadas, getAsignaturasCreadas, getCodigosAsignaturasCreadas, getAsignaturaCreada
	public static Set<Asignatura> getAsignaturasCreadas(){
		return new HashSet<Asignatura>(asignaturasCreadas.values());
	}
	
	public static Integer getNumAsignaturasCreadas(){
		return asignaturasCreadas.size();
	}
	
	public static Set<String> getCodigosAsignaturasCreadas(){
		return new HashSet<String>(asignaturasCreadas.keySet());
	}
	
	public static Asignatura getAsignaturaCreada(String codigo){
		Asignatura a = null;
		a = asignaturasCreadas.get(codigo);
		return a;
	}

	
	/**BECA**/
	
	//Beca con parametros1
	public static Beca createBeca(String codigo, Double cuantiaTotal, Integer duracion, TipoBeca tipo){
		Beca b;
		b = new BecaImpl(codigo, cuantiaTotal, duracion, tipo);
		actualizarPobsBeca(b);
		return b;
	}
	
	//Beca con parametros2
	public static Beca createBeca(String codigo, TipoBeca tipo){
		Beca b;
		b = new BecaImpl(codigo, tipo);
		actualizarPobsBeca(b);
		return b;
	}
	
	//Beca con String
	public static Beca createBeca(String s){
		Beca b;
		b = new BecaImpl(s);
		actualizarPobsBeca(b);
		return b;
	}
	
	//Beca con fichero
	public static List<Beca> createBecas(String file){
		List<Beca> res = leeFichero(file, x -> createBeca(x));
		return res;
	}
	
	//Beca a partir de copia
	public static Beca createBeca(Beca original){
		Beca b;
		b = createBeca(original.getCodigo(), original.getCuantiaTotal(), original.getDuracion(), original.getTipo());
		return b;
		
	}
	
	private static void actualizarPobsBeca(Beca b){
		becasCreadas.add(b);
	}
	
	//getNumBecasCreadas, getNumBecasTipo, getBecasCreadas
	
	public static Integer getNumBecasCreadas(){
		return becasCreadas.size();
	}
	
	public static Integer getNumBecasTipo(TipoBeca tipo){
		Integer acum = 0;
		for(Beca b: getBecasCreadas()) {
			if(b.getTipo() == tipo) {
				acum++;
			}
		}
		return acum;
	}
	
	public static Set<Beca> getBecasCreadas(){
		return new HashSet<Beca>(becasCreadas);
	}
	
	
	/**CENTRO**/
	
	//Centro con parametros
	public static Centro createCentro(String nombre, String direccion, Integer numeroPlantas, Integer numeroSotanos){
		Centro c;
		if(usarJava8){
			c = new CentroImpl2(nombre, direccion, numeroPlantas, numeroSotanos);
			actualizarPobsCentro(c);
		} else {
			c = new CentroImpl(nombre, direccion, numeroPlantas, numeroSotanos);
			actualizarPobsCentro(c);
		}
		return c;
	}

	//Centro con copia
	public static Centro createCentro(Centro original){
		Centro c;
		c = createCentro(original.getNombre(), original.getDireccion(), original.getNumeroPlantas(), original.getNumeroSotanos());
		for(Espacio e: original.getEspacios()){
			c.nuevoEspacio(e);
		}
		return c;
	}
	
	private static void actualizarPobsCentro(Centro c) {
		centrosCreados.add(c);
	}
	
	//getNumCentrosCreados, getCentrosCreados, getMaxPlantas, getMaxSotanos, getMediaPlantas, getMediaSotanos
	
	public static Integer getNumCentrosCreados(){
		return centrosCreados.size();
	}
	
	public static Set<Centro> getCentrosCreados(){
		return new HashSet<Centro>(centrosCreados);
	}
	
	public static Integer getMaxPlantas(){
		Integer res = 0;
		if(centrosCreados.isEmpty()){
			res = null;
		} else {
			Integer acum = 0;
			for(Centro c: centrosCreados){
				acum = c.getNumeroPlantas();
				if(acum > res){
					res = acum;
				}
			}
		}
		return res;
	}
	
	public static Integer getMaxSotanos(){
		Integer res = 0;
		if(centrosCreados.isEmpty()){
			res = null;
		} else {
			Integer acum = 0;
			for(Centro c: centrosCreados){
				acum = c.getNumeroSotanos();
				if(acum > res){
					res = acum;
				}
			}
		}
		return res;
	}
	
	public static Double getMediaPlantas(){
		Double res = 0.;
		Double acum = 0.;
		Double cont = 0.;
		
		for(Centro c: centrosCreados){
			acum = acum + (c.getNumeroPlantas()*1.);
			cont++;
		}
		
		res = acum / cont;
		return res;
	}
	
	public static Double getMediaSotanos(){
		Double res = 0.;
		Double acum = 0.;
		Double cont = 0.;
		
		for(Centro c: centrosCreados){
			acum = acum + (c.getNumeroSotanos()*1.);
			cont++;
		}
		
		res = acum / cont;
		return res;
	}
	
	
	
	/**DEPARTAMENTO**/
	
	//Departamento con parametros
	public static Departamento createDepartamento(String nombre){
		Departamento d;
		if(usarJava8){
			d = new DepartamentoImpl2(nombre);
			actualizarPobsDepartamento(d);
		} else {
			d = new DepartamentoImpl(nombre);
			actualizarPobsDepartamento(d);
		}
		return d;
	}
	
	private static void actualizarPobsDepartamento(Departamento d) {
		departamentosCreados.add(d);
	}
	
	//getNumDepartamentosCreados, getDepartamentosCreados
	
	public static Integer getNumDepartamentosCreados(){
		return departamentosCreados.size();
	}
	
	public static Set<Departamento> getDepartamentosCreados(){
		return new HashSet<Departamento>(departamentosCreados);
	}
	
	
	/**DESPACHO**/

	//Despacho con parametros
	public static Despacho createDespacho(String nombre, Integer capacidad, Integer planta, Set<Profesor> profesores){
		Despacho d;
		d = new DespachoImpl(nombre, capacidad, planta, profesores);
		actualizarPobsEspacio(d);
		return d;
	}
	
	//Despacho con String
	public static Despacho createDespacho(String s){
		Despacho d;
		d = new DespachoImpl(s);
		actualizarPobsEspacio(d);
		return d;
	}
	
	//Despacho por copia
	public static Despacho createDespacho(Despacho original){
		Despacho d;
		d = createDespacho(original.getNombre(), original.getCapacidad(), original.getPlanta(), original.getProfesores());
		return d;
	}
	
	//Despacho por fichero
	public static List<Despacho> createDespachos(String file){
		List<Despacho> d = leeFichero(file, x->createDespacho(x));
		return d;
	}
	
	/**ESPACIO**/

	//Espacio con parametros
	public static Espacio createEspacio(TipoEspacio tipo, String nombre, Integer capacidad, Integer planta){
		Espacio e;
		e = new EspacioImpl(tipo, nombre, capacidad, planta);
		actualizarPobsEspacio(e);
		return e;
	}

	//Espacio con String
	public static Espacio createEspacio(String s){
		Espacio e;
		e = new EspacioImpl(s);
		actualizarPobsEspacio(e);
		return e;
	}
	
	//Espacio por copia
	public static Espacio createEspacio(Espacio original){
		Espacio e;
		e = createEspacio(original.getTipo(), original.getNombre(), original.getCapacidad(), original.getPlanta());
		return e;
	}
	
	//Espacio por fichero
	public static List<Espacio> createEspacios(String file){
		List<Espacio> e = leeFichero(file, x->createEspacio(x));
		return e;
	}
	
	private static void actualizarPobsEspacio(Espacio e) {
		espaciosCreados.add(e);
	}
	
	//getNumEspaciosCreados, getEspaciosCreados, getPlantaMayorEspacio, getPlantaMenorEspacio
	
	public static Integer getNumEspaciosCreados(){
		return espaciosCreados.size();
	}
	
	public static SortedSet<Espacio> getEspaciosCreados(){
		return new TreeSet<Espacio>(espaciosCreados);
	}
	
	public static Integer getPlantaMayorEspacio(){
		Integer res = -10000;
		if(espaciosCreados.isEmpty()){
			res = null;
		} else {
			Integer acum = 0;
			for(Espacio e: espaciosCreados){
				acum = e.getPlanta();
				if(acum>res){
					res = acum;
				}
			}
		}
		return res;
	}
	
	public static Integer getPlantaMenorEspacio(){
		Integer res = 10000;
		if(espaciosCreados.isEmpty()){
			res = null;
		} else {
			Integer acum = 0;
			for(Espacio e: espaciosCreados){
				acum = e.getPlanta();
				if(acum<res){
					res = acum;
				}
			}
		}
		return res;	
	}
	
/**GRADO**/
	
	//Grado con parametros
	public static Grado createGrado(String nombre, Set<Asignatura> asignaturasObligatorias, Set<Asignatura> asignaturasOptativas, Double numeroMinimoCreditosOptativas){
		Grado g;
		if(usarJava8){
			g = new GradoImpl2(nombre, asignaturasObligatorias, asignaturasOptativas, numeroMinimoCreditosOptativas	);
			actualizarPobsGrado(g);
		} else {
			g = new GradoImpl(nombre, asignaturasObligatorias, asignaturasOptativas, numeroMinimoCreditosOptativas	);
			actualizarPobsGrado(g);
		}
		return g;
	}
	
	
	private static void actualizarPobsGrado(Grado g) {
		gradosCreados.add(g);
	}
	
	//getNumGradosCreados, getGradosCreados, getMediaAsignaturasGrados, getMediaAsignaturasObligatoriasGrados, getMediaAsignaturasOptativasGrados
	
	public static Integer getNumGradosCreados(){
		return gradosCreados.size();
	}
	
	public static Set<Grado> getGradosCreados(){
		return new HashSet<Grado>(gradosCreados);
	}
	
	public static Double getMediaAsignaturasGrados(){
		Double res = 0.;
		Double acum = 0.;
		for(Grado g: gradosCreados){
			acum = g.getAsignaturasObligatorias().size()*1 + g.getAsignaturasOptativas().size()*1.;
			res = res + acum;
		}
		return res/getNumGradosCreados();
	}
	
	public static Double getMediaAsignaturasObligatoriasGrados(){
		Double res = 0.;
		Double acum = 0.;
		for(Grado g: gradosCreados){
			acum = g.getAsignaturasObligatorias().size()*1.;
			res = res + acum;
		}
		return res/getNumGradosCreados();
	}
	
	public static Double getMediaAsignaturasOptativasGrados(){
		Double res = 0.;
		Double acum = 0.;
		for(Grado g: gradosCreados){
			acum = g.getAsignaturasOptativas().size()*1.;
			res = res + acum;
		}
		return res/getNumGradosCreados();
	}
	
	

	/**PROFESOR**/
	
	//Profesor con parametros
	public static Profesor createProfesor(String dni, String nombre, String apellidos, LocalDate fechaNacimiento, String email, Categoria categoria, Departamento departamento){
		Profesor p;
		
		if(usarImplementacionMapProfesor){
			p = new ProfesorImpl2(dni, nombre, apellidos, fechaNacimiento, email, categoria, departamento);
			actualizarPobsProfesor(p);
		} else {
			p = new ProfesorImpl(dni, nombre, apellidos, fechaNacimiento, email, categoria, departamento);
			actualizarPobsProfesor(p);
		}
		return p;
	}
	
	//Profesor por copia
	public static Profesor createProfesor(Profesor original){
		Profesor p;
		p = createProfesor(original.getDNI(), original.getNombre(), original.getApellidos(), original.getFechaNacimiento(), original.getEmail(), original.getCategoria(), original.getDepartamento());
		for(Tutoria t: original.getTutorias()){
			p.nuevaTutoria(t.getHoraComienzo(), t.getDuracion(), t.getDiaSemana());
		}
		for(Asignatura asig: original.getAsignaturas()){
			p.imparteAsignatura(asig, original.dedicacionAsignatura(asig));
		}
		return p;
	}
	
	private static void actualizarPobsProfesor(Profesor p){
		profesoresCreados.add(p);
	}
	
	//getNumProfesoresCreados, getProfesoresCreados, setUsarImplementacionMapProfesor
	public static void setUsarImplementacionMapProfesor(Boolean b){
		usarImplementacionMapProfesor = b;
	}
	
	public static Set<Profesor> getProfesoresCreados(){
		return new HashSet<Profesor>(profesoresCreados);
	}
	
	public static Integer getNumProfesoresCreados(){
		return profesoresCreados.size();
	}
	
	public static void setUsarJava8(Boolean b){
		usarJava8 = b;
	}
}
