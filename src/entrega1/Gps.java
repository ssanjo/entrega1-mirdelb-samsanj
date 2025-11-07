package entrega1;

/**
 * Representa una localizacion GPS con su longitud y latitud.
 * 
 * @author Miryam Del Bosque Fernandez
 * @author Samuel San Jose Faulin
 *
 */
public class Gps {
	private double longitudEnGD;
	private double latitudEnGD;
	private static final double RADIO_TIERRA_KM = 6371.0;

	/**
	 * Representa los posibles sentidos de la longitud.
	 */
	public enum SentidoLongitud {
		/** Este */
		E,
		/** Oeste */
		O
	}

	/**
	 * Representa los posibles sentidos de la latitud.
	 */
	public enum SentidoLatitud {
		/** Norte */
		N,
		/** Sur */
		S
	}

	/**
	 * Constructor de la clase Gps.
	 * @param longitudEnGD La longitud en grados decimales.
	 * @param latitudEnGD La latitud en grados decimales.
	 * @throws IllegalArgumentException Si la longitud no esta en el intervalo (-180,180]
	 * o si la latitud no esta en el intervalo (-90,90].
	 */
	public Gps(double longitudEnGD, double latitudEnGD) {
		if (longitudEnGD <= -180 || longitudEnGD > 180 ) {
			throw new IllegalArgumentException("La longitud tiene que estar en el intervalo (-180,180]");
		}
		if (latitudEnGD <= -90 || latitudEnGD > 90) {
			throw new IllegalArgumentException("La latitud tiene que estar en el intervalo (-90,90]");
		}
		this.longitudEnGD = longitudEnGD;
		this.latitudEnGD = latitudEnGD;
	}

	/**
	 * Constructor de copia de la clase Gps.
	 * @param gps La localizacion GPS a copiar.
	 * @throws IllegalArgumentException Si la localizacion GPS es nula.
	 */
	public Gps(Gps gps) {
		if(gps == null) {
			throw new IllegalArgumentException("La localizacion no puede ser nula.");
		}
		this.longitudEnGD = gps.getLongitudEnGD();
		this.latitudEnGD = gps.getLatitudEnGD();
	}

	/**
	 * Constructor de la clase Gps.
	 * @param longitudGrados Los grados de la longitud.
	 * @param longitudMinutos Los minutos de la longitud.
	 * @param longitudSegundos Los segundos de la longitud.
	 * @param longitudSentido El sentido de la longitud.
	 * @param latitudGrados Los grados de la latitud.
	 * @param latitudMinutos Los minutos de la latitud.
	 * @param latitudSegundos Los segundos de la latitud.
	 * @param latitudSentido El sentido de la latitud.
	 * @throws IllegalArgumentException Si los grados, minutos o segundos no estan en los intervalos adecuados,
	 * o si la longitud o latitud resultantes no estan en los intervalos adecuados.
	 */
	public Gps(int longitudGrados, int longitudMinutos, int longitudSegundos, SentidoLongitud longitudSentido, int latitudGrados, int latitudMinutos, int latitudSegundos, SentidoLatitud latitudSentido) {

		if(longitudGrados < 0 || longitudGrados > 180) {
			throw new IllegalArgumentException("Los grados de longitud tienen que estar en el intervalo [0,180]");
		}
		if(longitudMinutos < 0 || longitudMinutos >= 60) {
			throw new IllegalArgumentException("Los minutos de longitud tienen que estar en el intervalo [0,60)");
		}
		if(longitudSegundos < 0 || longitudSegundos >= 60) {
			throw new IllegalArgumentException("Los segundos de longitud tienen que estar en el intervalo [0,60)");
		}

		if(latitudGrados < 0 || latitudGrados > 90) {
			throw new IllegalArgumentException("Los grados de latitud tienen que estar en el intervalo [0,90]");
		}
		if(latitudMinutos < 0 || latitudMinutos >= 60) {
			throw new IllegalArgumentException("Los minutos de latitud tienen que estar en el intervalo [0,60)");
		}
		if(latitudSegundos < 0 || latitudSegundos >= 60) {	
			throw new IllegalArgumentException("Los segundos de latitud tienen que estar en el intervalo [0,60)");
		}

		double longitudEnGD = longitudGrados + (longitudMinutos / 60.0) + (longitudSegundos / 3600.0);

		double latitudEnGD = latitudGrados + (latitudMinutos / 60.0) + (latitudSegundos / 3600.0);

		if(longitudSentido == SentidoLongitud.O) {
			longitudEnGD = -longitudEnGD;
		}
		if(latitudSentido == SentidoLatitud.S) {
			latitudEnGD = -latitudEnGD;
		}


		if (longitudEnGD <= -180 || longitudEnGD > 180 ) {
			throw new IllegalArgumentException("La longitud tiene que estar entre 179º 59' 59'' O y 180º 0' 0'' E");
		}
		if (latitudEnGD <= -90 || latitudEnGD > 90) {
			throw new IllegalArgumentException("La latitud tiene que estar entre 89º 59' 59'' S y 90º 0' 0'' N");
		}

		this.longitudEnGD = longitudEnGD;
		this.latitudEnGD = latitudEnGD;
		
	}

	/**
	 * Devuelve la longitud en grados decimales.
	 * @return La longitud en grados decimales.
	 */
	public double getLongitudEnGD() {
		return longitudEnGD;
	}

	/**
	 * Devuelve la latitud en grados decimales.
	 * @return La latitud en grados decimales.
	 */
	public double getLatitudEnGD() {
		return latitudEnGD;
	}

	/**
	 * Devuelve los grados de la longitud.
	 * @return Los grados
	 */
	public int getLongitudGrados() {
		return (int) Math.floor(Math.abs(getLongitudEnGD()));
	}

	/**
	 * Devuelve los minutos de la longitud.
	 * @return Los minutos
	 */
	public int getLongitudMinutos() {
		double minutosTotales = (Math.abs(getLongitudEnGD()) - getLongitudGrados()) * 60;
		return (int) Math.floor(minutosTotales);
	}

	/**
	 * Devuelve los segundos de la longitud.
	 * @return Los segundos
	 */
	public int getLongitudSegundos() {
		double minutosTotales = (Math.abs(getLongitudEnGD()) - getLongitudGrados()) * 60;
		double segundosTotales = (minutosTotales - getLongitudMinutos()) * 60;
		return (int) Math.round(segundosTotales);
	}

	/**
	 * Devuelve el sentido de la longitud.
	 * @return El sentido de la longitud.
	 */
	public SentidoLongitud getLongitudSentido() {
		if (longitudEnGD >= 0) {
			return SentidoLongitud.E;
		} else {
			return SentidoLongitud.O;
		}
	}

	/**
	 * Devuelve los grados de la latitud.
	 * @return Los grados
	 */
	public int getLatitudGrados() {
		return (int) Math.floor(Math.abs(getLatitudEnGD()));
	}

	/**
	 * Devuelve los minutos de la latitud.
	 * @return Los minutos
	 */
	public int getLatitudMinutos() {
		double minutosTotales = (Math.abs(getLatitudEnGD()) - getLatitudGrados()) * 60;
		return (int) Math.floor(minutosTotales);
	}

	/**
	 * Devuelve los segundos de la latitud.
	 * @return Los segundos
	 */
	public int getLatitudSegundos() {
		double minutosTotales = (Math.abs(getLatitudEnGD()) - getLatitudGrados()) * 60;
		double segundosTotales = (minutosTotales - getLatitudMinutos()) * 60;
		return (int) Math.round(segundosTotales);
	}

	/**
	 * Devuelve el sentido de la latitud.
	 * @return El sentido de la latitud.
	 */
	public SentidoLatitud getLatitudSentido() {
		if (latitudEnGD >= 0) {
			return SentidoLatitud.N;
		} else {
			return SentidoLatitud.S;
		}
	}

	/**
	 * Devuelve una representacion en cadena de la localizacion GPS.
	 * @return La representacion en cadena de la localizacion GPS.
	 */
	@Override
	public String toString() {
		return String.format("Latitud: %dº %d' %d'' %s, Longitud: %dº %d' %d'' %s",
				getLatitudGrados(), getLatitudMinutos(), getLatitudSegundos(), getLatitudSentido(),
				getLongitudGrados(), getLongitudMinutos(), getLongitudSegundos(), getLongitudSentido());
	}

	/**
	 * Compara dos objetos Gps para determinar si son iguales.
	 * @param objeto El objeto a comparar.
	 * @return true si los objetos son iguales, false en caso contrario.
	 */
	@Override
	public boolean equals(Object objeto) {
		if (this == objeto) {
			return true;
		}
		if (!(objeto instanceof Gps)) {
			return false;
		}

		Gps gps = (Gps) objeto;

		return(
			getLongitudEnGD() == gps.getLongitudEnGD() && 
			getLatitudEnGD() == gps.getLatitudEnGD()
		);
	}

	/**
	 * Calcula la distancia en kilometros entre esta localizacion GPS y otra.
	 * @param gps La otra localizacion GPS.
	 * @return La distancia en kilometros entre las dos localizaciones GPS.
	 */
	public Double distanciaEnKm(Gps gps) {
		double lat1 = Math.toRadians(getLatitudEnGD());
		double lat2 = Math.toRadians(gps.getLatitudEnGD());
		double dLat = Math.toRadians(gps.getLatitudEnGD() - getLatitudEnGD());
		double dLon = Math.toRadians(gps.getLongitudEnGD() - getLongitudEnGD());

		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
				   Math.cos(lat1) * Math.cos(lat2) *
				   Math.sin(dLon / 2) * Math.sin(dLon / 2);

		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return RADIO_TIERRA_KM * c;
	}
}