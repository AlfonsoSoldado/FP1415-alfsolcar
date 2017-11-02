package fp.grados.tipos.test;

import fp.grados.tipos.Asignatura;
import fp.grados.tipos.AsignaturaImpl;
import fp.grados.tipos.Convocatoria;
import fp.grados.tipos.Departamento;
import fp.grados.tipos.DepartamentoImpl;
import fp.grados.tipos.Expediente;
import fp.grados.tipos.ExpedienteImpl;
import fp.grados.tipos.Nota;
import fp.grados.tipos.NotaImpl;
import fp.grados.tipos.TipoAsignatura;

public class TestExpediente {

	public static void main(String[] args) {
		Expediente exp = new ExpedienteImpl();
		Departamento dep = new DepartamentoImpl("LSI");
		Asignatura asig = new AsignaturaImpl("Fundamentos de Programación", "8992351", 12., TipoAsignatura.ANUAL, 1, dep);
		Nota n1 = new NotaImpl(asig, 1, Convocatoria.PRIMERA, 7.);
		Nota n2 = new NotaImpl(asig, 1, Convocatoria.PRIMERA, 8.);
		Nota n3 = new NotaImpl(asig, 1, Convocatoria.PRIMERA, 9.);
		Nota n4 = new NotaImpl(asig, 1, Convocatoria.PRIMERA, 5.);
		Nota n5 = new NotaImpl(asig, 1, Convocatoria.PRIMERA, 2.);
		
		exp.nuevaNota(n1);
		exp.nuevaNota(n2);
		exp.nuevaNota(n3);
		exp.nuevaNota(n4);
		exp.nuevaNota(n5);
		
		System.out.println(exp.getNotaMedia());

	}

}
