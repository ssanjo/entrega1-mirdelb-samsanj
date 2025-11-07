package entrega1;
import java.lang.reflect.Array;
import java.util.ArrayList;
import fabricante.externo.tarjetas.TarjetaMonedero;



/**
 * Clase que representa una ciudad de EcoRider con sus parkings y fianza.
 * 
 * @author Miryam Del Bosque Fernandez
 * @author Samuel San Jose Faulin
 */
public class EcoRiderCity {

	private String nombre;
	private ArrayList<EcoRiderParking> parkings;
	private double fianza;


	/**
	 * Constructor de la clase EcoRiderCity.
	 * 
	 * @param nombre Nombre de la ciudad.
	 * @param fianza Fianza para alquilar vehiculos en la ciudad.
	 * @throws IllegalArgumentException Si el nombre es nulo o vacio, o si la fianza es negativa.
	 */
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

	/**
	 * Constructor de copia de la clase EcoRiderCity.
	 * 
	 * @param city Ciudad a copiar.
	 * @throws IllegalArgumentException Si la ciudad es nula.
	 */
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

	/**
	 * Devuelve el nombre de la ciudad.
	 * @return El nombre de la ciudad.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Devuelve la fianza para alquilar vehiculos en la ciudad.
	 * @return La fianza.
	 */
	public double getFianza() {
		return fianza;
	}


	/**
	 * Establece la fianza para alquilar vehiculos en la ciudad.
	 * @param fianza La fianza a establecer.
	 * @throws IllegalArgumentException Si la fianza es negativa.
	 */
	public void setFianza(double fianza) {
		if(fianza < 0) {
			throw new IllegalArgumentException("La fianza no puede ser negativa.");
		}
		this.fianza = fianza;
	}


	/**
	 * Devuelve una copia de la lista de parkings de la ciudad.
	 * @return La lista de parkings.
	 */
	public ArrayList<EcoRiderParking> getParkings() {
		ArrayList<EcoRiderParking> copiaParkings = new ArrayList<>();
		for(EcoRiderParking parking : parkings) {
			copiaParkings.add(new EcoRiderParking(parking));
		}
		return copiaParkings;
	}


	/**
	 * Anade un parking a la ciudad.
	 * @param parking El parking a anadir.
	 * @throws IllegalArgumentException Si el parking es nulo o si ya existe un parking
	 * con el mismo identificador.
	 */
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

	/**
	 * Elimina un parking de la ciudad.
	 * @param idParking El identificador del parking a eliminar.
	 * @throws IllegalArgumentException Si el identificador es nulo o vacio,
	 * o si no existe ningun parking con el identificador proporcionado.
	 */
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

	/**
	 * Devuelve una lista de los parkings con plazas disponibles.
	 * @return La lista de parkings con plazas disponibles.
	 */
	public ArrayList<EcoRiderParking> getParkingsDisponibles() {
		ArrayList<EcoRiderParking> parkingsDisponibles = new ArrayList<>();
		for(EcoRiderParking parking : parkings) {
			if(parking.getNumeroPlazasDisponibles() > 0) {
				parkingsDisponibles.add(parking);
			}
		}
		return parkingsDisponibles;
	}

	/**
	 * Devuelve una lista de los parkings ocupados (sin plazas disponibles).
	 * @return La lista de parkings ocupados.
	 */
	public ArrayList<EcoRiderParking> getParkingsOcupados() {
		ArrayList<EcoRiderParking> parkingsOcupados = new ArrayList<>();
		for(EcoRiderParking parking : parkings) {
			if(parking.getNumeroPlazasDisponibles() == 0) {
				parkingsOcupados.add(parking);
			}
		}
		return parkingsOcupados;
	}

	/**
	 * Devuelve una lista de los parkings con plazas inoperativas.
	 * @return La lista de parkings con plazas inoperativas.
	 */
	public ArrayList<EcoRiderParking> getParkingsConPlazaInoperativa() {
		ArrayList<EcoRiderParking> parkingsConPlazaInoperativa = new ArrayList<>();
		for(EcoRiderParking parking : parkings) {
			if (parking.getNumeroPlazasInoperativas() > 0) {
				parkingsConPlazaInoperativa.add(parking);
			}
		}
		return parkingsConPlazaInoperativa;
	}

	/**
	 * Devuelve una lista de los parkings cercanos a una localizacion dada
	 * dentro de una distancia maxima en metros.
	 * @param gps La localizacion desde la que buscar parkings cercanos.
	 * @param distanciaMaximaMetros La distancia maxima en metros.
	 * @return La lista de parkings cercanos.
	 * @throws IllegalArgumentException Si la localizacion es nula o
	 * si la distancia maxima es negativa.
	 */
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

	/**
	 * Comprueba si el identificador del parking es valido.
	 * @param idParking El identificador del parking.
	 * @throws IllegalArgumentException Si el identificador es nulo o vacio,
	 * o si no existe ningun parking con el identificador proporcionado.
	 */
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

	/**
	 * Alquila un vehiculo en una plaza de un parking.
	 * @param idParking El identificador del parking.
	 * @param idPlaza El identificador de la plaza.
	 * @param tarjeta La tarjeta monedero para realizar el pago.
	 * @throws IllegalArgumentException Si el identificador del parking es nulo o vacio,
	 * si no existe ningun parking con el identificador proporcionado,
	 * si la plaza no existe en el parking, si la plaza no esta disponible,
	 * o si la tarjeta es nula o no tiene saldo suficiente.
	 */
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

	/**
	 * Devuelve un vehiculo en una plaza de un parking.
	 * @param idParking El identificador del parking.
	 * @param idPlaza El identificador de la plaza.
	 * @param tarjeta La tarjeta monedero para devolver el pago.
	 * @throws IllegalArgumentException Si el identificador del parking es nulo o vacio,
	 * si no existe ningun parking con el identificador proporcionado,
	 * si la plaza no existe en el parking, si la plaza no esta ocupada,
	 * o si la tarjeta es nula o no tiene saldo suficiente.
	 */
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


	/**
	 * Reserva una plaza en un parking.
	 * @param idParking El identificador del parking.
	 * @param idPlaza El identificador de la plaza.
	 * @throws IllegalArgumentException Si el identificador del parking es nulo o vacio
	 * 	o si no existe ningun parking con el identificador proporcionado,
	 * 	si la plaza no existe en el parking, o si la plaza no esta
	 * disponible.
	 */
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

	/**
	 * Marca una plaza como inoperativa en un parking.
	 * @param idParking El identificador del parking.
	 * @param idPlaza El identificador de la plaza.
	 * @throws IllegalArgumentException Si el identificador del parking es nulo o vacio
	 * 	o si no existe ningun parking con el identificador proporcionado,
	 * 	si la plaza no existe en el parking, o si la plaza ya esta inoperativa.
	 */
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

	/**
	 * Marca una plaza como operativa en un parking.
	 * @param idParking El identificador del parking.
	 * @param idPlaza El identificador de la plaza.
	 * @throws IllegalArgumentException Si el identificador del parking es nulo o vacio
	 * 	o si no existe ningun parking con el identificador proporcionado,
	 * 	si la plaza no existe en el parking, o si la plaza ya esta operativa.
	 */
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


	/**
	 * Devuelve una representacion en cadena de la ciudad.
	 * @return La representacion en cadena de la ciudad.
	 */
	@Override
	public String toString() {
		return "EcoRiderCity [nombre=" + getNombre() + ", parkings="
				+ parkings + ", fianza=" + getFianza() + "]";
	}

	/**
	 * Compara dos objetos EcoRiderCity para determinar si son iguales.
	 * @param objeto El objeto a comparar.
	 * @return true si los objetos son iguales, false en caso contrario.
	 */
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