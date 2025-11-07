package entrega1;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.Before;
import fabricante.externo.tarjetas.TarjetaMonedero;


public class EcoRiderParkingTest {
	
	private EcoRiderParking parking;
    private TarjetaMonedero tarjeta;
    private Gps gps;
    private ArrayList<EcoRiderPlace> plazas;
    
    @Before
    public void setUp() {
        gps = new Gps(32.564, -18.70);
        parking = new EcoRiderParking("P001", "Parking Centro", "Calle Central 12", gps, 10, 2);
        tarjeta = new TarjetaMonedero("A156Bv09_1zXo894", 100.0);
    }

	//CONSTRUCTOR***************************************************
	@Test
	public void testConstructorEcoRiderParking() {
		assertEquals("P001", parking.getId());
        assertEquals("Parking Centro", parking.getNombre());
        assertEquals("Calle Central 12", parking.getDireccion());
        assertEquals(10, parking.getPlazasPorBloque());
        assertEquals(20, parking.getNumeroPlazas());
	}
	
	@Test(expected = IllegalArgumentException.class)
    public void testConstructorEcoRiderParkingPlazasPorBloqueFueraDeRango() {
        new EcoRiderParking("P001", "Parking", "Direccion", gps, 0, 2);
    }
	
	@Test(expected = IllegalArgumentException.class)
    public void testConstructorEcoRiderParkingIdNulo() {
        new EcoRiderParking(null, "Parking", "Direccion", gps, 10, 2);
    }
	
	@Test(expected = IllegalArgumentException.class)
    public void testConstructorEcoRiderParkingIdVacio() {
        new EcoRiderParking("", "Parking", "Direccion", gps, 10, 2);
    }
	
	@Test(expected = IllegalArgumentException.class)
    public void testConstructorEcoRiderParkingNombreNulo() {
        new EcoRiderParking("P001", null, "Direccion", gps, 10, 2);
    }
	
	@Test(expected = IllegalArgumentException.class)
    public void testConstructorEcoRiderParkingNombreVacio() {
        new EcoRiderParking("P001", "", "Direccion", gps, 10, 2);
    }
	
	@Test(expected = IllegalArgumentException.class)
    public void testConstructorEcoRiderParkingDireccionNula() {
        new EcoRiderParking("P001", "Parking", null, gps, 10, 2);
    }
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorEcoRiderParkingDireccionVacia() {
		new EcoRiderParking("P001", "Parking", "", gps, 10, 2);
	}
	
	@Test(expected = IllegalArgumentException.class)
    public void testConstructorEcoRiderParkingGpsNulo() {
        new EcoRiderParking("P001", "Parking", "Direccion", null, 10, 2);
    }
	//****************************************************************
	
	//CONSTRUCTORCOPIA**********************************************
	@Test
    public void testEcoRiderParkingConstructorCopia() {
        EcoRiderParking copia = new EcoRiderParking(parking);
        
        assertEquals(parking.getId(), copia.getId());
        assertEquals(parking.getNombre(), copia.getNombre());
        assertEquals(parking.getDireccion(), copia.getDireccion());
        assertEquals(parking.getPlazasPorBloque(), copia.getPlazasPorBloque());
        assertEquals(parking.getNumeroPlazas(), copia.getNumeroPlazas());
        assertNotSame(parking, copia);
    }
	
	@Test(expected = IllegalArgumentException.class)
    public void testEcoRiderParkingConstructorCopiaNulo() {
        new EcoRiderParking(null);
    }
	//*****************************************************************
	
	//GETTERS***********************************************************
	@Test
	public void testGetNumeroPlazas() {
		assertEquals(20, parking.getNumeroPlazas());
	}

	@Test
	public void testGetNumeroPlazasDisponibles() {
		assertEquals(0, parking.getNumeroPlazasDisponibles());
		
		parking.alquilarVehiculoEnPlaza(0, tarjeta, 10.0);
        parking.alquilarVehiculoEnPlaza(1, tarjeta, 10.0);
        
        assertEquals(2, parking.getNumeroPlazasDisponibles());
	}

	@Test
	public void testGetNumeroPlazasOcupadas() {
		assertEquals(20, parking.getNumeroPlazasOcupadas());
		
		parking.alquilarVehiculoEnPlaza(0, tarjeta, 10.0);
        parking.alquilarVehiculoEnPlaza(1, tarjeta, 10.0);
        
        assertEquals(18, parking.getNumeroPlazasOcupadas());
	}

	@Test
	public void testGetNumeroPlazasInoperativas() {
		assertEquals(0, parking.getNumeroPlazasDisponibles());
		
		parking.alquilarVehiculoEnPlaza(0, tarjeta, 10.0);
        parking.setPlazaInoperativa(0);
        
        parking.alquilarVehiculoEnPlaza(1, tarjeta, 10.0);
        parking.setPlazaInoperativa(1);
        
        assertEquals(2, parking.getNumeroPlazasInoperativas());
	}

	@Test
	public void testGetNumeroPlazasReservadas() {
		assertEquals(0, parking.getNumeroPlazasReservadas());
		
		parking.alquilarVehiculoEnPlaza(0, tarjeta, 10.0);
        parking.reservarPlaza(0);
        
        parking.alquilarVehiculoEnPlaza(1, tarjeta, 10.0);
        parking.reservarPlaza(1);
        
        assertEquals(2, parking.getNumeroPlazasReservadas());
	}

	@Test
	public void testGetGps() {
		Gps gpsObtenido = parking.getGps();
        assertEquals(gps.getLatitudEnGD(), gpsObtenido.getLatitudEnGD(), 0.0001);
        assertEquals(gps.getLongitudEnGD(), gpsObtenido.getLongitudEnGD(), 0.0001);
        assertNotSame(gps, gpsObtenido);
	}

	@Test
	public void testGetId() {
		assertEquals("P001", parking.getId());
	}

	@Test
	public void testGetNombre() {
		assertEquals("Parking Centro", parking.getNombre());
	}

	@Test
	public void testGetDireccion() {
		assertEquals("Calle Central 12", parking.getDireccion());
	}

	@Test
	public void testGetPlazasPorBloque() {
		assertEquals(10, parking.getPlazasPorBloque());
	}
	
	public void testGetPlazas() {
		assertNotNull(plazas);
	    assertEquals(20, plazas.size());
	    
	    for (int i = 0; i < plazas.size(); i++) {
	        assertEquals(i, plazas.get(i).getId());
	        assertEquals(EcoRiderPlace.Estado.OCUPADA, plazas.get(i).getEstado());
	    }
	}

	@Test
	public void testGetEstadoPlaza() {
		assertEquals(EcoRiderPlace.Estado.OCUPADA, parking.getEstadoPlaza(0));
		
		parking.alquilarVehiculoEnPlaza(0, tarjeta, 10.0);
        
		assertEquals(EcoRiderPlace.Estado.DISPONIBLE, parking.getEstadoPlaza(0));
	}
	
	@Test(expected = IllegalArgumentException.class)
    public void testGetEstadoPlazaInvalidaNegativa() {
        parking.getEstadoPlaza(-1);
    }
	
	@Test(expected = IllegalArgumentException.class)
    public void testGetEstadoPlazaInvalidaFueraDeRangoSuperior() {
        parking.getEstadoPlaza(20);
    }

	@Test
	public void testGetDistancia() {
		Gps gps2 = new Gps(12.694, 40.79);
        EcoRiderParking parking2 = new EcoRiderParking("P002", "Parking", "Calle", gps2, 10, 2);
        
        double distancia = parking.getDistancia(parking2);
        assertTrue(distancia > 6800 && distancia < 7000);
	}

	@Test
	public void testGetDistanciaGps() {
		Gps otroGps = new Gps(32.5641, -18.70);
        double distancia = parking.getDistancia(otroGps);
        
        assertTrue(distancia < 1.0);
	}
	//******************************************************************

	//AlquilarVehiculoEnPlaza**********************************************
	@Test
	public void testAlquilarVehiculoEnPlaza() {
		double saldoInicial = tarjeta.getSaldoActual();
        parking.alquilarVehiculoEnPlaza(0, tarjeta, 15.0);
        
        assertEquals(EcoRiderPlace.Estado.DISPONIBLE, parking.getEstadoPlaza(0));
        assertEquals(saldoInicial - 15.0, tarjeta.getSaldoActual(), 0.001);
	}
	
	@Test(expected = IllegalArgumentException.class)
    public void testAlquilarVehiculoEnPlazaInvalida() {
        parking.alquilarVehiculoEnPlaza(25, tarjeta, 10.0);
    }
	//*****************************************************************

	//DevolverVehiculoEnPlaza*********************************************
	@Test
	public void testDevolverVehiculoEnPlaza() {
		parking.alquilarVehiculoEnPlaza(0, tarjeta, 10.0);
        
        double saldoInicial = tarjeta.getSaldoActual();
        parking.devolverVehiculoEnPlaza(0, tarjeta, 20.0);
        
        assertEquals(EcoRiderPlace.Estado.OCUPADA, parking.getEstadoPlaza(0));
        assertEquals(saldoInicial + 20.0, tarjeta.getSaldoActual(), 0.001);
	}
	
	@Test(expected = IllegalArgumentException.class)
    public void testDevolverVehiculoEnPlazaInvalida() {
        parking.devolverVehiculoEnPlaza(25, tarjeta, 10.0);
    }
	//*****************************************************************

	//ReservarVehiculoEnPlaza*********************************************
	@Test
	public void testReservarVehiculoEnPlaza() {
		parking.alquilarVehiculoEnPlaza(0, tarjeta, 10.0);
        parking.reservarPlaza(0);
        
        assertEquals(EcoRiderPlace.Estado.RESERVADA, parking.getEstadoPlaza(0));
	}
	
	@Test(expected = IllegalArgumentException.class)
    public void testReservarPlazaInvalida() {
        parking.reservarPlaza(25);
    }
	//****************************************************************

	//SETTERS**************************************************************
	@Test
	public void testSetPlazaInoperativa() {
		parking.alquilarVehiculoEnPlaza(0, tarjeta, 10.0);
        parking.setPlazaInoperativa(0);
        
        assertEquals(EcoRiderPlace.Estado.INOPERATIVA, parking.getEstadoPlaza(0));
	}
	
	@Test(expected = IllegalArgumentException.class)
    public void testSetPlazaInoperativaInvalida() {
        parking.setPlazaInoperativa(25);
    }
	
	@Test
    public void testSetPlazaOperativa() {
        parking.alquilarVehiculoEnPlaza(0, tarjeta, 10.0);
        parking.setPlazaInoperativa(0);
        parking.setPlazaOperativa(0);
        
        assertEquals(EcoRiderPlace.Estado.DISPONIBLE, parking.getEstadoPlaza(0));
    }
	
	@Test(expected = IllegalArgumentException.class)
    public void testSetPlazaOperativaInvalida() {
        parking.setPlazaOperativa(25);
    }
	//**************************************************************

	//AnadirBloque****************************************************
	@Test
	public void testAnadirBloque() {
		int plazasIniciales = parking.getNumeroPlazas();
		int plazasPorBloqueIniciales = parking.getPlazasPorBloque();
	    parking.anadirBloque();
	    
	    assertEquals(3, (plazasIniciales / plazasPorBloqueIniciales) + 1);
	}
	///*****************************************************************

	//ToString***********************************************************
	@Test
	public void testToString() {
		String resultado = parking.toString();
        assertTrue(resultado.contains("EcoRiderParking"));
        assertTrue(resultado.contains("P001"));
        assertTrue(resultado.contains("Parking Centro"));
        assertTrue(resultado.contains("Calle Central 12"));
	}
	//****************************************************************

	//EQUALS**************************************************************
	@Test
	public void testEquals() {
		assertTrue(parking.equals(parking));
	}
	
	@Test
    public void testEqualsObjetosIguales() {
        EcoRiderParking parking3 = new EcoRiderParking("P001", "Parking Centro", "Calle Central 12", gps, 10, 2);
        assertTrue(parking.equals(parking3));
	}
	
	@Test
    public void testEqualsDiferenteId() {
        EcoRiderParking parking3 = new EcoRiderParking("P003", "Parking Centro", "Calle Central 12", gps, 10, 2);
        assertFalse(parking.equals(parking3));
	}
	
	@Test
    public void testEqualsDiferenteNombre() {
        EcoRiderParking parking3 = new EcoRiderParking("P001", "Parking", "Calle Central 12", gps, 10, 2);
        assertFalse(parking.equals(parking3));
	}
	
	@Test
    public void testEqualsDiferenteDireccion() {
        EcoRiderParking parking3 = new EcoRiderParking("P001", "Parking Centro", "Calle", gps, 10, 2);
        assertFalse(parking.equals(parking3));
	}
	
	@Test
    public void testEqualsDiferenteGps() {
		Gps diferenteGps = new Gps(41.3851, 2.1734);
        EcoRiderParking parking3 = new EcoRiderParking("P001", "Parking Centro", "Calle Central 12", diferenteGps, 10, 2);
        assertFalse(parking.equals(parking3));
	}
	
	@Test
    public void testEqualsDiferentePlazasPorBloque() {
        EcoRiderParking parking3 = new EcoRiderParking("P001", "Parking Centro", "Calle Central 12", gps, 11, 2);
        assertFalse(parking.equals(parking3));
	}
	
	@Test
    public void testEqualsDiferenteNumeroDeBloques() {
		EcoRiderParking parking3 = new EcoRiderParking("P001", "Parking Centro", "Calle Central 12", gps, 10, 3);
        assertFalse(parking.equals(parking3));
    }
	
	@Test
    public void testEqualsConNull() {
        assertFalse(parking.equals(null));
    }
	
	@Test
    public void testEqualsObjectConOtroTipo() {
		String otro = "No soy un parking";
        assertFalse(parking.equals(otro));
    }
	//********************************************************************
}