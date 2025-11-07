package entrega1;

import static org.junit.Assert.*;
import fabricante.externo.tarjetas.TarjetaMonedero;


import org.junit.Test;
import org.junit.Before;

public class EcoRiderPlaceTest {
	
	private EcoRiderPlace plaza;
	private TarjetaMonedero tarjeta;
	
	@Before
	public void setUp() {
		plaza = new EcoRiderPlace(5);
		tarjeta = new TarjetaMonedero("A156Bv09_1zXo894", 100.0);
	}

	//CONSTRURTOR*******************************************************
	@Test
	public void testConstructorEcoRiderPlaceConId() {
		assertEquals(5, plaza.getId());
		assertEquals(EcoRiderPlace.Estado.OCUPADA, plaza.getEstado());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorEcoRiderPlaceIdNegativo() {
		new EcoRiderPlace(-1);
	}
	//********************************************************************
	
	//CONSTRUSCTORCOPIA***************************************************
	@Test
	public void testEcoRiderPlaceConstructorCopia() {
		EcoRiderPlace original = new EcoRiderPlace(10);
		EcoRiderPlace copia = new EcoRiderPlace(original);
		
		assertEquals(original.getId(), copia.getId());
		assertEquals(original.getEstado(), copia.getEstado());
		assertNotSame(original, copia);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testEcoRiderPlaceConstructorCopiaNulo() {
		new EcoRiderPlace(null);
	}
	//**********************************************************************
	
	//GETTERS***************************************************************
	@Test
	public void testGetEstado() {
		assertEquals(EcoRiderPlace.Estado.OCUPADA, plaza.getEstado());
	}

	@Test
	public void testGetId() {
		assertEquals(5, plaza.getId());
	}
	//***********************************************************************
	
	//RESERVARPLAZA***********************************************************
	@Test
	public void testReservarPlazaDisponible() {
		plaza.llevarCoche(tarjeta, 10.0);
		plaza.reservarPlaza();
		assertEquals(EcoRiderPlace.Estado.RESERVADA, plaza.getEstado());
	}
	
	@Test(expected = IllegalStateException.class)
	public void testReservarPlazaOcupada() {
		plaza.reservarPlaza();
	}
	
	@Test(expected = IllegalStateException.class)
	public void testReservarPlazaInoperativa() {
		EcoRiderPlace plazaInop = new EcoRiderPlace(2);
		plazaInop.llevarCoche(tarjeta, 10.0);
		plazaInop.setPlazaInoperativa();
		plazaInop.reservarPlaza();
	}
	//**************************************************************************

	//DEJARCOCHE**************************************************************
	@Test
	public void testDejarCocheEnPlazaDisponible() {
		plaza.llevarCoche(tarjeta, 10.0);
		double saldoInicial = tarjeta.getSaldoActual();
		plaza.dejarCoche(tarjeta, 20.0);
		
		assertEquals(EcoRiderPlace.Estado.OCUPADA, plaza.getEstado());
		assertEquals(saldoInicial + 20.0, tarjeta.getSaldoActual(), 0.001);
	}
	
	@Test
	public void testDejarCocheEnPlazaReservada() {
		plaza.llevarCoche(tarjeta, 10.0);
		plaza.reservarPlaza();
		double saldoInicial = tarjeta.getSaldoActual();
		plaza.dejarCoche(tarjeta, 15.0);
		
		assertEquals(EcoRiderPlace.Estado.OCUPADA, plaza.getEstado());
		assertEquals(saldoInicial + 15.0, tarjeta.getSaldoActual(), 0.001);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testDejarCocheEnPlazaOcupada() {
		plaza.dejarCoche(tarjeta, 10.0);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testDejarCocheEnPlazaInoperativa() {
		EcoRiderPlace plazaInop = new EcoRiderPlace(3);
		plazaInop.llevarCoche(tarjeta, 10.0);
		plazaInop.setPlazaInoperativa();
		plazaInop.dejarCoche(tarjeta, 10.0);
	}
	//***********************************************************************
	
	//LLEVARCOCHE*************************************************************
	@Test
	public void testLlevarCoche() {
		double saldoInicial = tarjeta.getSaldoActual();
		plaza.llevarCoche(tarjeta, 10.0);
		
		assertEquals(EcoRiderPlace.Estado.DISPONIBLE, plaza.getEstado());
		assertEquals(saldoInicial - 10.0, tarjeta.getSaldoActual(), 0.001);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testLlevarCocheDePlazaDisponible() {
		plaza.llevarCoche(tarjeta, 10.0);
		plaza.llevarCoche(tarjeta, 10.0);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testLlevarCocheDePlazaReservada() {
		plaza.llevarCoche(tarjeta, 10.0);
		plaza.reservarPlaza();
		plaza.llevarCoche(tarjeta, 10.0);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testLlevarCocheDePlazaInoperativa() {
		EcoRiderPlace plazaInop = new EcoRiderPlace(4);
		plazaInop.llevarCoche(tarjeta, 10.0);
		plazaInop.setPlazaInoperativa();
		plazaInop.llevarCoche(tarjeta, 10.0);
	}
	//*************************************************************************
	
	//SETTERS***************************************************************
	@Test
	public void testSetPlazaInoperativa() {
		plaza.llevarCoche(tarjeta, 10.0);
		plaza.setPlazaInoperativa();
		assertEquals(EcoRiderPlace.Estado.INOPERATIVA, plaza.getEstado());
	}
	
	@Test(expected = IllegalStateException.class)
	public void testSetPlazaInoperativaDesdeOcupada() {
		plaza.setPlazaInoperativa();
	}
	
	@Test(expected = IllegalStateException.class)
	public void testSetPlazaInoperativaDesdeReservada() {
		plaza.llevarCoche(tarjeta, 10.0);
		plaza.reservarPlaza();
		plaza.setPlazaInoperativa();
	}
	
	@Test
	public void testSetPlazaOperativaDesdeInoperativa() {
		plaza.llevarCoche(tarjeta, 10.0);
		plaza.setPlazaInoperativa();
		plaza.setPlazaOperativa();
		assertEquals(EcoRiderPlace.Estado.DISPONIBLE, plaza.getEstado());
	}
	
	@Test(expected = IllegalStateException.class)
	public void testSetPlazaOperativaDesdeDisponible() {
		plaza.llevarCoche(tarjeta, 10.0);
		plaza.setPlazaOperativa();
	}
	
	@Test(expected = IllegalStateException.class)
	public void testSetPlazaOperativaDesdeOcupada() {
		plaza.setPlazaOperativa();
	}
	//***********************************************************************
	
	//TOSTRING**************************************************************
	@Test
	public void testToString() {
		String resultado = plaza.toString();
		assertTrue(resultado.contains("La plaza tiene el identificador 5"));
		assertTrue(resultado.contains("OCUPADA"));
	}
	//************************************************************************

	//EQUALS*****************************************************************
	@Test
	public void testEqualsObjectMismoObjeto() {
		assertTrue(plaza.equals(plaza));
	}
	
	@Test
	public void testEqualsObjectObjetosIguales() {
		EcoRiderPlace plaza2 = new EcoRiderPlace(5);
		assertTrue(plaza.equals(plaza2));
	}
	
	@Test
	public void testEqualsObjectDiferenteId() {
		EcoRiderPlace plaza2 = new EcoRiderPlace(2);
		assertFalse(plaza.equals(plaza2));
	}
	
	@Test
	public void testEqualsObjectDiferenteEstado() {
		EcoRiderPlace plaza2 = new EcoRiderPlace(1);
		plaza2.llevarCoche(tarjeta, 10.0);
		assertFalse(plaza.equals(plaza2));
	}
	
	@Test
	public void testEqualsObjectConNull() {
		assertFalse(plaza.equals(null));
	}
	
	@Test
	public void testEqualsObjectConOtroTipo() {
		String otro = "No soy una plaza";
		assertFalse(plaza.equals(otro));
	}
	//***********************************************************************
}