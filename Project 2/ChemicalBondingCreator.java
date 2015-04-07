//Joseph Sarabia
//COP 4600
//4/11/13
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class ChemicalBondingCreator implements Runnable {

	public Semaphore waiton = new Semaphore(1);
	public Semaphore csem = new Semaphore(0);
	public Semaphore hsem = new Semaphore(0);
	public Semaphore bonding = new Semaphore(0);
	public Semaphore mol = new Semaphore(0);
	
	public List<HydrogenAtom> haList = new ArrayList<>();
	public List<CarbonAtom> caList = new ArrayList<>();
	private int molCount = 0;

	@Override
	public void run() {
		while (true) {
			System.out.println("Chemical bonding creator, looking for bonding");

			try {
				synchronized (bonding) {
					bonding.wait();
				}
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
			if (haList.size() >= 4 && caList.size() >= 1) {
				System.out.println("Chemical bonding creator: enough atoms to create a methane molecule");
					
				for (int j = 0; j < 4; j++){
					haList.remove(0);
				}
				caList.remove(0);
					
				waiton.release();
					
				synchronized (hsem) {
					for (int x = 0; x < 4; x++){
						hsem.notify();
					}
				}
				synchronized (csem){
					csem.notify();
				}
				
				try {
					mol.acquire(5);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Chemical bonding creator: methane molecule " + molCount + " has been created");
				molCount++;
				mol.drainPermits();
				continue;
			}
			if (haList.size() >= 4) {
				System.out
						.println("Chemical bonding creator: enough H atoms, but no C atom");
				waiton.release();
				continue;
			}
		}
	}

}
