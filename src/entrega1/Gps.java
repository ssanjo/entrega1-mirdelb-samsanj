package entrega1;

public class Gps {
	private double longitudEnGD;
	private double latitudEnGD;
	private static final double RADIO_TIERRA_KM = 6371.0;

	public enum SentidoLongitud {
		E, O
	}
	public enum SentidoLatitud {
		N, S
	}

	
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

	public Gps(Gps gps) {
		if(gps == null) {
			throw new IllegalArgumentException("La localizacion no puede ser nula.");
		}
		this.longitudEnGD = gps.getLongitudEnGD();
		this.latitudEnGD = gps.getLatitudEnGD();
	}

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

	public double getLongitudEnGD() {
		return longitudEnGD;
	}
	public double getLatitudEnGD() {
		return latitudEnGD;
	}

	public int getLongitudGrados() {
		return (int) Math.floor(Math.abs(getLongitudEnGD()));
	}
	public int getLongitudMinutos() {
		double minutosTotales = (Math.abs(getLongitudEnGD()) - getLongitudGrados()) * 60;
		return (int) Math.floor(minutosTotales);
	}
	public int getLongitudSegundos() {
		double minutosTotales = (Math.abs(getLongitudEnGD()) - getLongitudGrados()) * 60;
		double segundosTotales = (minutosTotales - getLongitudMinutos()) * 60;
		return (int) Math.round(segundosTotales);
	}
	public SentidoLongitud getLongitudSentido() {
		if (longitudEnGD >= 0) {
			return SentidoLongitud.E;
		} else {
			return SentidoLongitud.O;
		}
	}

	public int getLatitudGrados() {
		return (int) Math.floor(Math.abs(getLatitudEnGD()));
	}
	public int getLatitudMinutos() {
		double minutosTotales = (Math.abs(getLatitudEnGD()) - getLatitudGrados()) * 60;
		return (int) Math.floor(minutosTotales);
	}
	public int getLatitudSegundos() {
		double minutosTotales = (Math.abs(getLatitudEnGD()) - getLatitudGrados()) * 60;
		double segundosTotales = (minutosTotales - getLatitudMinutos()) * 60;
		return (int) Math.round(segundosTotales);
	}
	public SentidoLatitud getLatitudSentido() {
		if (latitudEnGD >= 0) {
			return SentidoLatitud.N;
		} else {
			return SentidoLatitud.S;
		}
	}

	@Override
	public String toString() {
		return String.format("Latitud: %dº %d' %d'' %s, Longitud: %dº %d' %d'' %s",
				getLatitudGrados(), getLatitudMinutos(), getLatitudSegundos(), getLatitudSentido(),
				getLongitudGrados(), getLongitudMinutos(), getLongitudSegundos(), getLongitudSentido());
	}

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