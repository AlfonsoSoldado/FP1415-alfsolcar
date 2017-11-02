package fp.grados.tipos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import fp.grados.excepciones.ExcepcionExpedienteOperacionNoPermitida;

public class ExpedienteImpl implements Expediente {

	private List<Nota> notas;
	
	public ExpedienteImpl(){
		notas = new ArrayList<Nota>();
	}
	
	private void CheckConvocatorias(Nota n){
		Integer contador = 0;
		for(Nota n2: notas){
			if(n.getCursoAcademico().equals(n2.getCursoAcademico()) && n.getAsignatura().equals(n2.getAsignatura())){
				contador++;
			}
		}	
		if(contador > 2){
			throw new ExcepcionExpedienteOperacionNoPermitida();
		}
	}
	
	public List<Nota> getNotas() {
		return new ArrayList<Nota>(notas);
	}

	public void nuevaNota(Nota n) {
		Integer i = getNotas().indexOf(n);
		if(!(i.equals(-1))){
			notas.remove(getNotas().get(i));
			notas.add(n);
		} else {
			notas.add(n);
		}
		CheckConvocatorias(n);
	}

	public Double getNotaMedia() {
		List<Nota> notasMayCinco = new ArrayList<Nota>();
		Double res = 0.;
		Double acum = 0.;
		for(Nota n: notas){
			if(n.getValor()>=5){
				notasMayCinco.add(n);
			}
		}
		if(notasMayCinco.isEmpty()){
			res = 0.;
		} else {
			for(Nota n: notasMayCinco){
				acum = n.getValor();
				res = (acum + res);
			}
			res = res/notasMayCinco.size();
		}
		return res;
	}
	
	public List<Nota> getNotasOrdenadasPorAsignatura(){
		List<Nota> res = new ArrayList<Nota>();
		Comparator<Nota> cmp = Comparator.comparing(Nota::getAsignatura)
				.thenComparing(Comparator.naturalOrder());
		
		res.addAll(getNotas());
		res.sort(cmp);
		return res;
	}
	
	public Nota getMejorNota(){
		Comparator<Nota> cmp = Comparator.comparing(Nota::getMencionHonor)
				.thenComparing(Nota::getValor)
				.thenComparing(Comparator.comparing(Nota::getConvocatoria).reversed())
				.thenComparing(Comparator.comparing(Nota::getCursoAcademico).reversed());
		
		return getNotas().stream()
				.filter(x -> x != null)
				.max(cmp)
				.get();
	}
	
	public boolean equals(Object o){
		boolean res = false;
		if(o instanceof Expediente){
			Expediente expediente = (Expediente) o;
			res = getNotas().equals(expediente.getNotas());
		}
		return res;
	}
	
	public int hashCode(){
		return getNotas().hashCode();
	}
	
	public String toString(){
		return getNotas().toString();
	}	

}
