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
	void GRAINhasBankEnoughResourcesFalse() {
		Bank bank = new Bank();
		bank.removeBankResource(19, Resource.GRAIN);
		assertFalse(bank.hasBankEnoughResources(1, Resource.GRAIN));
	}
	
	@Test
	void GRAINhasBankEnoughResourcesTrue() {
		Bank bank = new Bank();
		assertTrue(bank.hasBankEnoughResources(1, Resource.GRAIN));
	}
	
	@Test
	void WOOLhasBankEnoughResourcesFalse() {
		Bank bank = new Bank();
		bank.removeBankResource(19, Resource.WOOL);
		assertFalse(bank.hasBankEnoughResources(1, Resource.WOOL));
	}
	
	@Test
	void WOOLhasBankEnoughResourcesTrue() {
		Bank bank = new Bank();
		assertTrue(bank.hasBankEnoughResources(1, Resource.WOOL));
	}
	
	@Test
	void WOODhasBankEnoughResourcesFalse() {
		Bank bank = new Bank();
		bank.removeBankResource(19, Resource.WOOD);
		assertFalse(bank.hasBankEnoughResources(1, Resource.WOOD));
	}
	
	@Test
	void WOODhasBankEnoughResourcesTrue() {
		Bank bank = new Bank();
		assertTrue(bank.hasBankEnoughResources(1, Resource.WOOD));
	}
	
	@Test
	void STONEhasBankEnoughResourcesFalse() {
		Bank bank = new Bank();
		bank.removeBankResource(19, Resource.STONE);
		assertFalse(bank.hasBankEnoughResources(1, Resource.STONE));
	}
	
	@Test
	void STONEhasBankEnoughResourcesTrue() {
		Bank bank = new Bank();
		assertTrue(bank.hasBankEnoughResources(1, Resource.STONE));
	}
	
	@Test
	void CLAYhasBankEnoughResourcesFalse() {
		Bank bank = new Bank();
		bank.removeBankResource(19, Resource.CLAY);
		assertFalse(bank.hasBankEnoughResources(1, Resource.CLAY));
	}
	
	@Test
	void CLAYhasBankEnoughResourcesTrue() {
		Bank bank = new Bank();
		assertTrue(bank.hasBankEnoughResources(1, Resource.CLAY));
	}
	
	@Test
	void overMaxHasBankEnoughResources() {
		Bank bank = new Bank();
		assertFalse(bank.hasBankEnoughResources(20, Resource.CLAY));
	}
	
}
