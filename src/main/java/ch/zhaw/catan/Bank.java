package ch.zhaw.catan;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ch.zhaw.catan.Config.Resource;

/**
 * @author Peter Blattmann
 *
 */
public class Bank {
	private List<Resource> resourceBank;
	
	public Bank() {
		resourceBank = new ArrayList<>();
		fillBank();
	}
	
	/**
	 * 
	 */
	private void fillBank() {
		for(Resource resource : Resource.values()) {
			addBankResources(Config.INITIAL_RESOURCE_CARDS_BANK.get(resource), resource);
		}
	}
	
	/**
	 * 
	 * @param number
	 * @param resource
	 * @return
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
	 * 
	 * 
	 * @param number
	 * @param resource
	 */
	public void addBankResources(int number, Resource resource) {
    	for(int i = 0; i < number; i++) {
    		resourceBank.add(resource);
    	}
	}
	
	/**
	 * 
	 * 
	 * @param number
	 * @param resource
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
}	
	

