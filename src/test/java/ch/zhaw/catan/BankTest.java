package ch.zhaw.catan;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import ch.zhaw.catan.Config.Resource;

class BankTest {

	@Test
	void testFillBank() {
		Bank bank = new Bank();
		assertEquals(95, bank.getBankResources().size());
	}
	
	@Test
	void testRemoveBankRessource() {
		Bank bank = new Bank();
		bank.removeBankResource(19, Resource.GRAIN);
		bank.removeBankResource(19, Resource.WOOL);
		bank.removeBankResource(19, Resource.WOOD);
		bank.removeBankResource(19, Resource.STONE);
		bank.removeBankResource(19, Resource.CLAY);
		assertEquals(0, bank.getBankResources().size());
	}
	
	@Test
	void testAddBankResources() {
		Bank bank = new Bank();
		bank.removeBankResource(19, Resource.GRAIN);
		bank.removeBankResource(19, Resource.WOOL);
		bank.removeBankResource(19, Resource.WOOD);
		bank.removeBankResource(19, Resource.STONE);
		bank.removeBankResource(19, Resource.CLAY);
		bank.addBankResources(2, Resource.CLAY);
		assertTrue(bank.hasBankEnoughResources(2, Resource.CLAY));
	}
	
	@Test
	void GRAINhasBankEnoughtResourcesFalse() {
		Bank bank = new Bank();
		bank.removeBankResource(19, Resource.GRAIN);
		assertFalse(bank.hasBankEnoughResources(1, Resource.GRAIN));
	}
	
	@Test
	void GRAINhasBankEnoughtResourcesTrue() {
		Bank bank = new Bank();
		assertTrue(bank.hasBankEnoughResources(1, Resource.GRAIN));
	}
	
	@Test
	void WOOLhasBankEnoughtResourcesFalse() {
		Bank bank = new Bank();
		bank.removeBankResource(19, Resource.WOOL);
		assertFalse(bank.hasBankEnoughResources(1, Resource.WOOL));
	}
	
	@Test
	void WOOLhasBankEnoughtResourcesTrue() {
		Bank bank = new Bank();
		assertTrue(bank.hasBankEnoughResources(1, Resource.WOOL));
	}
	
	@Test
	void WOODhasBankEnoughtResourcesFalse() {
		Bank bank = new Bank();
		bank.removeBankResource(19, Resource.WOOD);
		assertFalse(bank.hasBankEnoughResources(1, Resource.WOOD));
	}
	
	@Test
	void WOODhasBankEnoughtResourcesTrue() {
		Bank bank = new Bank();
		assertTrue(bank.hasBankEnoughResources(1, Resource.WOOD));
	}
	
	@Test
	void STONEhasBankEnoughtResourcesFalse() {
		Bank bank = new Bank();
		bank.removeBankResource(19, Resource.STONE);
		assertFalse(bank.hasBankEnoughResources(1, Resource.STONE));
	}
	
	@Test
	void STONEhasBankEnoughtResourcesTrue() {
		Bank bank = new Bank();
		assertTrue(bank.hasBankEnoughResources(1, Resource.STONE));
	}
	
	@Test
	void CLAYhasBankEnoughtResourcesFalse() {
		Bank bank = new Bank();
		bank.removeBankResource(19, Resource.CLAY);
		assertFalse(bank.hasBankEnoughResources(1, Resource.CLAY));
	}
	
	@Test
	void CLAYhasBankEnoughtResourcesTrue() {
		Bank bank = new Bank();
		assertTrue(bank.hasBankEnoughResources(1, Resource.CLAY));
	}
	
	
}
