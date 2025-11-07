package entrega1;

import static org.junit.Assert.*;

import org.junit.Test;

import org.junit.Before;

public class GpsTest {

	private Gps gps1;
	private Gps gps2;

	@Before
	public void setUp() {
		gps1 = new Gps(139.6917, 35.6895); 
		gps2 = new Gps(34.0522, -29.4234); 
	}

	//PRIMER CONTRUCTOR*************************************************************
    @Test
    public void testConstructorEnGD() {
        Gps gps = new Gps(100.0, 45.0);
        assertEquals(100.0, gps.getLongitudEnGD(), 0.0001);
        assertEquals(45.0, gps.getLatitudEnGD(), 0.0001);
    }

    @Test
    public void testConstructorEnGDLimitePositivo() {
        Gps gps = new Gps(180.0, 90.0);
        assertEquals(180.0, gps.getLongitudEnGD(), 0.0001);
        assertEquals(90.0, gps.getLatitudEnGD(), 0.0001);
    }

    @Test
    public void testConstructorEnGDLimiteNegativo() {
        Gps gps = new Gps(-179.999999, -89.999999);
        assertEquals(-179.999999, gps.getLongitudEnGD(), 0.0001);
        assertEquals(-89.999999, gps.getLatitudEnGD(), 0.0001);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorEnGDLongitudFueraDeRangoPositivo() {
    	new Gps(180.001, 45.0);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorEnGDLongitudFueraDeRangoNegativo(){
    	new Gps(-180.0, 45.0);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorEnGDLatitudFueraDeRangoPositivo(){
    	new Gps(74.0, 90.001);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorEnGDLatitudFueraDeRangoNegativo(){
    	new Gps(74, -90.0);
    }
    //******************************************************************************

    //SEGUNDO CONSTRUCTOR***********************************************************
    @Test
    public void testConstructorCopia() {
    	Gps original = new Gps(100.0, 45.0);
    	Gps copia = new Gps(original);
    	assertEquals(original.getLongitudEnGD(), copia.getLongitudEnGD(), 0.0001);
    	assertEquals(original.getLatitudEnGD(), copia.getLatitudEnGD(), 0.0001);
    	assertNotSame(original, copia);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorCopiaNulo() {
    	new Gps(null);
    }
    //******************************************************************************
    
    //TERCER CONSTRUCTOR************************************************************
    @Test
    public void testConstructorEnDMS() {
        Gps gps = new Gps(74, 0, 21, Gps.SentidoLongitud.O, 30, 42, 51, Gps.SentidoLatitud.N);
        assertEquals(-74.0058333, gps.getLongitudEnGD(), 0.0001);
        assertEquals(30.7141667, gps.getLatitudEnGD(), 0.0001);
    }
    
    @Test(expected = IllegalArgumentException.class)
	public void testConstructorDMSLongitudGradosFueraDeRangoSuperior() {
		new Gps(181, 0, 0, Gps.SentidoLongitud.E, 40, 0, 0, Gps.SentidoLatitud.N);
	}
    
    @Test(expected = IllegalArgumentException.class)
	public void testConstructorDMSLongitudGradosFueraDeRangoInferior() {
		new Gps(-1, 0, 0, Gps.SentidoLongitud.E, 40, 0, 0, Gps.SentidoLatitud.N);
	}
    
    @Test(expected = IllegalArgumentException.class)
	public void testConstructorDMSLongitudMinutosFueraDeRangoSuperior() {
		new Gps(74, 60, 0, Gps.SentidoLongitud.E, 40, 0, 0, Gps.SentidoLatitud.N);
	}
    
    @Test(expected = IllegalArgumentException.class)
	public void testConstructorDMSLongitudMinutosFueraDeRangoInferior() {
		new Gps(74, -1, 0, Gps.SentidoLongitud.E, 40, 0, 0, Gps.SentidoLatitud.N);
	}
    
    @Test(expected = IllegalArgumentException.class)
	public void testConstructorDMSLongitudSegundosFueraDeRangoSuperior() {
		new Gps(74, 0, 60, Gps.SentidoLongitud.E, 40, 0, 0, Gps.SentidoLatitud.N);
	}
    
    @Test(expected = IllegalArgumentException.class)
	public void testConstructorDMSLongitudSegundosFueraDeRangoInferior() {
		new Gps(74, 0, -1, Gps.SentidoLongitud.E, 40, 0, 0, Gps.SentidoLatitud.N);
	}
   
    @Test(expected = IllegalArgumentException.class)
	public void testConstructorDMSLatitudGradosFuerDeRangoSuperior() {
		new Gps(74, 0, 0, Gps.SentidoLongitud.E, 91, 0, 0, Gps.SentidoLatitud.N);
	}
    
    @Test(expected = IllegalArgumentException.class)
	public void testConstructorDMSLatitudGradosFuerDeRangoInferior() {
		new Gps(74, 0, 0, Gps.SentidoLongitud.E, -1, 0, 0, Gps.SentidoLatitud.N);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorDMSLatitudMinutosFueraDeRangoSuperior() {
		new Gps(74, 0, 0, Gps.SentidoLongitud.E, 40, 60, 0, Gps.SentidoLatitud.N);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorDMSLatitudMinutosFueraDeRangoInferior() {
		new Gps(74, 0, 0, Gps.SentidoLongitud.E, 40, -1, 0, Gps.SentidoLatitud.N);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorDMSLatitudSegundosFueraDeRangoSuperior() {
		new Gps(74, 0, 0, Gps.SentidoLongitud.E, 40, 0, 60, Gps.SentidoLatitud.N);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorDMSLatitudSegundosFueraDeRangoInferior() {
		new Gps(74, 0, 0, Gps.SentidoLongitud.E, 40, 0, -1, Gps.SentidoLatitud.N);
	}
	
	@Test
    public void testLongitudOesteNegativa() {
        Gps gps = new Gps(74, 0, 0, Gps.SentidoLongitud.O, 40, 0, 0, Gps.SentidoLatitud.S);
        assertEquals(-74, gps.getLongitudEnGD(), 0.0001);
        assertEquals(-40, gps.getLatitudEnGD(), 0.0001);
    }
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorDMSLongitudFueraDeRangoEste() {
		new Gps(180, 0, 1, Gps.SentidoLongitud.E, 40, 0, 0, Gps.SentidoLatitud.N);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorDMSLongitudFueraDeRangoOeste() {
		new Gps(180, 0, 0, Gps.SentidoLongitud.O, 40, 0, 0, Gps.SentidoLatitud.N);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorDMSLatitudFueraDeRangoNorte() {
		new Gps(74, 0, 0, Gps.SentidoLongitud.O, 90, 0, 1, Gps.SentidoLatitud.N);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorDMSLatitudFueraDeRangoSur() {
		new Gps(74, 0, 0, Gps.SentidoLongitud.O, 90, 0, 0, Gps.SentidoLatitud.S);
	}
	//*******************************************************************************
	
	//GETTERS************************************************************************
	@Test
	public void testGetLongitudEnGD() {
		Gps gps = new Gps(132.521, 36.29);
		assertEquals(132.521, gps.getLongitudEnGD(), 0.0001);
	}
	
	@Test
	public void testGetLatitudEnGD() {
		Gps gps = new Gps(132.521, 36.29);
		assertEquals(36.29, gps.getLatitudEnGD(), 0.0001);
	}
	
	@Test
	public void testGetLongitudGradosPositivo() {
		Gps gps = new Gps(132.521, 36.29);
		assertEquals(132, gps.getLongitudGrados());
	}
	
	@Test
	public void testGetLongitudGradosNegativo() {
		Gps gps = new Gps(-132.521, 36.29);
		assertEquals(132, gps.getLongitudGrados());
	}
	
	@Test
	public void testGetLongitudMinutos() {
		Gps gps = new Gps(132.521, 36.29);
		assertEquals(31, gps.getLongitudMinutos());
	}
	
	@Test
	public void testGetLongitudSegundos() {
		Gps gps = new Gps(132.521, 36.29);
		assertEquals(16, gps.getLongitudSegundos());
	}
	
	@Test
	public void testGetLatitudGradosPositivo() {
		Gps gps = new Gps(132.521, 36.29);
		assertEquals(36, gps.getLatitudGrados());
	}
	
	@Test
	public void testGetLatitudGradosNegativo() {
		Gps gps = new Gps(132.521, -36.29);
		assertEquals(36, gps.getLatitudGrados());
	}
	
	@Test
	public void testGetLatitudMinutos() {
		Gps gps = new Gps(132.521, 36.29);
		assertEquals(17, gps.getLatitudMinutos());
	}
	
	@Test
	public void testGetLatitudSegundos() {
		Gps gps = new Gps(132.521, 36.29);
		assertEquals(24, gps.getLatitudSegundos());
	}
	
	@Test
	public void testGetLongitudSentidoEsteCasoCero() {
		Gps gps = new Gps(0, 36.29);
		assertEquals(Gps.SentidoLongitud.E, gps.getLongitudSentido());
	}
	
	@Test
	public void testGetLongitudSentidoEste() {
		Gps gps = new Gps(132.521, 36.29);
		assertEquals(Gps.SentidoLongitud.E, gps.getLongitudSentido());
	}
	
	@Test
	public void testGetLongitudSentidoOeste() {
		Gps gps = new Gps(-132.521, 36.29);
		assertEquals(Gps.SentidoLongitud.O, gps.getLongitudSentido());
	}
	
	@Test
	public void testGetLatiudSentidoNorteCasoCero() {
		Gps gps = new Gps(132.521, 0);
		assertEquals(Gps.SentidoLatitud.N, gps.getLatitudSentido());
	}
	
	@Test
	public void testGetLatiudSentidoNorte() {
		Gps gps = new Gps(132.521, 36.29);
		assertEquals(Gps.SentidoLatitud.N, gps.getLatitudSentido());
	}
	
	@Test
	public void testGetLatiudSentidoSur() {
		Gps gps = new Gps(132.521, -36.29);
		assertEquals(Gps.SentidoLatitud.S, gps.getLatitudSentido());
	}
	//*******************************************************************************
	
	//TOSTRING***********************************************************************
	@Test
	public void testToStringPositivo() {
		Gps gps = new Gps(74, 0, 21, Gps.SentidoLongitud.E, 40, 42, 51, Gps.SentidoLatitud.N);
		String expected = "Latitud: 40º 42' 51'' N, Longitud: 74º 0' 21'' E";
		assertEquals(expected, gps.toString());
	}
	
	@Test
	public void testToStringNegativo() {
		Gps gps = new Gps(74, 0, 21, Gps.SentidoLongitud.O, 40, 42, 51, Gps.SentidoLatitud.S);
		String expected = "Latitud: 40º 42' 51'' S, Longitud: 74º 0' 21'' O";
		assertEquals(expected, gps.toString());
	}
	//*******************************************************************************
	
	//EQUALS*************************************************************************
	@Test
	public void testEqualsMismoObjeto() {
		assertTrue(gps1.equals(gps1));
	}
	
	@Test
	public void testEqualsObjetosIguales() {
		Gps gps3 = new Gps(gps1.getLongitudEnGD(), gps1.getLatitudEnGD());
		assertTrue(gps1.equals(gps3));
	}
	
	@Test
	public void testEqualsDiferenteTipo() {
		String otro = "No soy un Gps";
		assertFalse(gps1.equals(otro));
	}
	
	@Test
	public void testEqualsNull() {
		assertFalse(gps1.equals(null));
	}
	
	@Test
	public void testEqualsDiferenteLongitud() {
		assertFalse(gps1.equals(gps2));
	}
	
	@Test
	public void testEqualsDiferenteLatitud() {
		assertFalse(gps1.equals(gps2));
	}
	//*******************************************************************************
	
	//DISTANCIAENKM******************************************************************
	@Test
	public void testDistanciaEnKmMismoPunto() {
		assertEquals(0.0, gps1.distanciaEnKm(gps1), 0.0001);
	}
	
	@Test
	public void testDistanciaEnKmPuntosConocidos() {
	    Gps origen = new Gps(-3.7038, 40.4168);
	    Gps destino = new Gps(2.1734, 41.3851);
	    Double distancia = origen.distanciaEnKm(destino);
	    assertEquals(505.0, distancia, 10.0);
	}
	//*******************************************************************************
}