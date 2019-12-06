package ch.zhaw.catan;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ch.zhaw.catan.Config.Resource;

/**
 * This class models a bank. The bank stores and manages resources.
 * 
 * @author Peter Blattmann
 *
 */
public class Bank {
	private List<Resource> resourceBank;
	
	/**
	 * The constructor creates the bank that stores and manages the 
	 * resources for the game. When the constructor is called, a list 
	 * is created which is filled with the required resources.
	 * 
	 */
	public Bank() {
		resourceBank = new ArrayList<>();
		fillBank();
	}
	
	/**
	 * This method fills the ArrayList with the resources needed for
	 * the game. The number of resources from {@link Config.Resource}.
	 * 
	 */
	private void fillBank() {
		for(Resource resource : Resource.values()) {
			addBankResources(Config.INITIAL_RESOURCE_CARDS_BANK.get(resource), resource);
		}
	}
	
	/**
	 * The method checks whether the bank has sufficient resources in stock.
	 * 
	 * @param number Number of resources to check
	 * @param resource The type of resource
	 * @return true, if if the bank has enough resources 
	 */
	public boolean hasBankEnoughResources(int number, Resource resource){
		int index = 0;
		Iterator<Resource> it = resourceBank.iterator();
		while(it.hasNext()) {
		Resource r1 = it.next();
			if(r1 == resource)
				index++;
		}
		if(number <= index) {
			return true;
		} 
		else {
			System.out.println("It seems like the bank doesn't has enough resources :(");
			return false;
		}
	}
	
	/**
	 * This method adds resources to the bank.
	 * 
	 * @param number Number of resources to add
	 * @param resource The type of resource
	 */
	public void addBankResources(int number, Resource resource) {
    	for(int i = 0; i < number; i++) {
    		resourceBank.add(resource);
    	}
	}
	
	/**
	 * This method removes the output resources from the bank. 
	 * 
	 * @param number Number of resources to be spent 
	 * @param resource The type of resource
	 */
	public void removeBankResource(int number, Resource resource) {
		boolean running = true;
		int index = 0;
    	Iterator<Resource> it = resourceBank.iterator();
    	while(running && it.hasNext()) {
    		Resource r1 = it.next();
    		if(r1 == resource) {
    			it.remove();
    			index++;
    		}
    		if(index == number) {
    			running = false;
    		}
    	}
	}
	
	/**
	 * This method returns the list of Resources.
	 * 
	 * @return resource ArrayList
	 */
	public List<Resource> getBankResources() {
		return resourceBank;
	}
}	
	

