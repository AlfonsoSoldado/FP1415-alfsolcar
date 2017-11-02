package fp.grados.tipos;

import java.util.OptionalDouble;

public class ExpedienteImpl2 extends ExpedienteImpl {

	
	public Double getNotaMedia(){
		OptionalDouble res = getNotas().stream() //OptionalDouble no es lo mismo que Double
				.filter(n -> n.getValor() >= 5)
				.mapToDouble(n -> n.getValor())
				.average();
		
		if(res.isPresent()){
			return res.getAsDouble();
		} else {
			return 0.;
		}
	}
}
