package entrega1;
import java.lang.reflect.Array;
import java.util.ArrayList;
import fabricante.externo.tarjetas.TarjetaMonedero;


public class EcoRiderCity {
	private String nombre;
	private ArrayList<EcoRiderParking> parkings;
	private double fianza;

	public EcoRiderCity(String nombre, double fianza){
		if(nombre == null || nombre.isEmpty()) {
			throw new IllegalArgumentException("El nombre de la ciudad no puede ser nulo o vacio.");
		}
		if(fianza < 0) {
			throw new IllegalArgumentException("La fianza no puede ser negativa.");
		}
		this.nombre = nombre;
		this.parkings = new ArrayList<>();
		this.fianza = fianza;
	}

	public EcoRiderCity(EcoRiderCity city) {
		if(city == null) {
			throw new IllegalArgumentException("La ciudad no puede ser nula.");
		}
		this.nombre = city.getNombre();
		this.fianza = city.getFianza();
		this.parkings = new ArrayList<>();
		for(EcoRiderParking parking : city.parkings) {
			this.parkings.add(new EcoRiderParking(parking));
		}
	}

	public String getNombre() {
		return nombre;
	}

	public double getFianza() {
		return fianza;
	}

	public void setFianza(double fianza) {
		if(fianza < 0) {
			throw new IllegalArgumentException("La fianza no puede ser negativa.");
		}
		this.fianza = fianza;
	}

	public ArrayList<EcoRiderParking> getParkings() {
		ArrayList<EcoRiderParking> copiaParkings = new ArrayList<>();
		for(EcoRiderParking parking : parkings) {
			copiaParkings.add(new EcoRiderParking(parking));
		}
		return copiaParkings;
	}

	public void anadirParking(EcoRiderParking parking) {
		if(parking == null) {
			throw new IllegalArgumentException("El parking no puede ser nulo.");
		}
		for (EcoRiderParking p : parkings) {
			if(p.getId().equals(parking.getId())) {
				throw new IllegalArgumentException("Ya existe un parking con el mismo identificador.");
			}
		}
		parkings.add(parking);
	}

	public void eliminarParking(String idParking) {
		if(idParking == null || idParking.isEmpty()) {
			throw new IllegalArgumentException("El identificador del parking no puede ser nulo o vacio.");
		}
		boolean eliminado = false;
		for(int i = 0; i < parkings.size(); i++) {
			if(parkings.get(i).getId().equals(idParking)) {
				parkings.remove(i);
				eliminado = true;
				break;
			}
		}
		if(!eliminado) {
			throw new IllegalArgumentException("No existe ningun parking con el identificador proporcionado.");
		}
	}

	public ArrayList<EcoRiderParking> getParkingsDisponibles() {
		ArrayList<EcoRiderParking> parkingsDisponibles = new ArrayList<>();
		for(EcoRiderParking parking : parkings) {
			if(parking.getNumeroPlazasDisponibles() > 0) {
				parkingsDisponibles.add(parking);
			}
		}
		return parkingsDisponibles;
	}

	public ArrayList<EcoRiderParking> getParkingsOcupados() {
		ArrayList<EcoRiderParking> parkingsOcupados = new ArrayList<>();
		for(EcoRiderParking parking : parkings) {
			if(parking.getNumeroPlazasDisponibles() == 0) {
				parkingsOcupados.add(parking);
			}
		}
		return parkingsOcupados;
	}


	public ArrayList<EcoRiderParking> getParkingsConPlazaInoperativa() {
		ArrayList<EcoRiderParking> parkingsConPlazaInoperativa = new ArrayList<>();
		for(EcoRiderParking parking : parkings) {
			if (parking.getNumeroPlazasInoperativas() > 0) {
				parkingsConPlazaInoperativa.add(parking);
			}
		}
		return parkingsConPlazaInoperativa;
	}


	public ArrayList<EcoRiderParking> getParkingsCercanos(Gps gps, double distanciaMaximaMetros) {
		if(gps == null) {
			throw new IllegalArgumentException("La localizacion no puede ser nula.");
		}
		if(distanciaMaximaMetros < 0) {
			throw new IllegalArgumentException("La distancia maxima no puede ser negativa.");
		}
		double distanciaMaximaKm = distanciaMaximaMetros / 1000.0;
		ArrayList<EcoRiderParking> parkingsCercanos = new ArrayList<>();
		for(EcoRiderParking parking : parkings) {
			if(parking.getDistancia(gps) <= distanciaMaximaKm && parking.getNumeroPlazasDisponibles() > 0) {
				parkingsCercanos.add(parking);
			}
		}
		return parkingsCercanos;
	}

	private void comprobarIdParkingValido(String idParking) {
		if(idParking == null || idParking.isEmpty()) {
			throw new IllegalArgumentException("El identificador del parking no puede ser nulo o vacio.");
		}
		boolean encontrado = false;
		for(EcoRiderParking parking : parkings) {
			if(parking.getId().equals(idParking)) {
				encontrado = true;
				break;
			}
		}
		if(!encontrado) {
			throw new IllegalArgumentException("No existe ningun parking con el identificador proporcionado.");
		}
	}

	public void alquilarVehiculoEnParking(String idParking, int idPlaza, TarjetaMonedero tarjeta) {
		comprobarIdParkingValido(idParking);
		EcoRiderParking parking = null;
		for(EcoRiderParking p : parkings) {
			if(p.getId().equals(idParking)) {
				parking = p;
				break;
			}
		}
		parking.alquilarVehiculoEnPlaza(idPlaza, tarjeta, getFianza());
	}

	public void devolverVehiculoEnParking(String idParking, int idPlaza, TarjetaMonedero tarjeta) {
		comprobarIdParkingValido(idParking);
		EcoRiderParking parking = null;
		for(EcoRiderParking p : parkings) {
			if(p.getId().equals(idParking)) {
				parking = p;
				break;
			}
		}
		parking.devolverVehiculoEnPlaza(idPlaza, tarjeta, getFianza());
	}

	public void reservarPlazaEnParking(String idParking, int idPlaza) {
		comprobarIdParkingValido(idParking);
		EcoRiderParking parking = null;
		for(EcoRiderParking p : parkings) {
			if(p.getId().equals(idParking)) {
				parking = p;
				break;
			}
		}
		parking.reservarPlaza(idPlaza);
	}

	public void setPlazaInoperativaEnParking(String idParking, int idPlaza) {
		comprobarIdParkingValido(idParking);
		EcoRiderParking parking = null;
		for(EcoRiderParking p : parkings) {
			if(p.getId().equals(idParking)) {
				parking = p;
				break;
			}
		}
		parking.setPlazaInoperativa(idPlaza);
	}

	public void setPlazaOperativaEnParking(String idParking, int idPlaza) {
		comprobarIdParkingValido(idParking);
		EcoRiderParking parking = null;
		for(EcoRiderParking p : parkings) {
			if(p.getId().equals(idParking)) {
				parking = p;
				break;
			}
		}
		parking.setPlazaOperativa(idPlaza);
	}


	@Override
	public String toString() {
		return "EcoRiderCity [nombre=" + getNombre() + ", parkings="
				+ parkings + ", fianza=" + getFianza() + "]";
	}


	@Override
	public boolean equals(Object objeto) {
		if (this == objeto) {
			return true;
		}

		if (!(objeto instanceof EcoRiderCity)) {
			return false;
		}
		EcoRiderCity city = (EcoRiderCity) objeto;

		return (
			getNombre().equals(city.getNombre()) &&
			getFianza() == city.getFianza() &&
			parkings.equals(city.parkings)
		);

	}


}