//Joseph Sarabia
//COP 4600
//4/11/13
package bonding;

import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.Semaphore;

public class BondingCreator implements Runnable {

	public Semaphore waiton = new Semaphore(1);
	public Vector<Semaphore> asem = new Vector<>();
	
	public Semaphore bonding = new Semaphore(0);
	
	public Vector<ArrayList<Atom>> List = new Vector<>();
	private Vector<Integer> bonds;
	private int totalBonds = 0;
	public Semaphore mol = new Semaphore(0);
	private int molCount = 0;
	
	BondingCreator(Vector<Integer> bonds){
		this.bonds = bonds;
		ArrayList<Atom> a;
		Semaphore b;
		for(int i = 0; i < bonds.size(); i++){
			a = new ArrayList<>();
			b = new Semaphore(0);
			List.add(a);
			asem.add(b);	
			totalBonds += bonds.elementAt(i);
		}
		
		
	}
	
	@Override
	public void run() {

		while (true) {
			System.out.println("Chemical bonding creator, looking for bonding");
			
			//start by getting the lock
			try {
				waiton.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//check if the list has enough of each atom, otherwise spin
			for(int i = 0; i < bonds.size(); i++){
				if((i >= 0) && (List.elementAt(i).size() < bonds.elementAt(i))){
					i = -1;
					waiton.release();
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						waiton.acquire();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
					
			}
			waiton.release();

			//remove the atoms from their lists
			for(int i = 0; i < bonds.size(); i++){		
				//about to edit the list, so acquire the lock
				try {
					waiton.acquire();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//edit list
				for (int j = 0; j < bonds.elementAt(i); j++){
					List.elementAt(i).remove(0);
				}
				
				//release lock
				waiton.release();
				
				//notify for however many bonds per this current atom
				synchronized (asem.elementAt(i)) {
					for (int x = 0; x < bonds.elementAt(i); x++){
						asem.elementAt(i).notify();
					}
				}
				
			}
			try {
				mol.acquire(totalBonds);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				System.out.println("Chemical bonding creator: molecule " + molCount + " has been created");
				molCount++;
				mol.drainPermits();
			
		}
	}

}
