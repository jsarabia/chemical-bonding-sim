//Joseph Sarabia
//COP 4600
//4/11/13


import java.util.Random;

public class CarbonAtomGenerator implements Runnable {

	private Random random = new Random();
	int count = 0;
	private ChemicalBondingCreator cbc;

	public CarbonAtomGenerator(ChemicalBondingCreator cbc) {
		this.cbc = cbc;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep((long) (5000.0 * random.nextDouble()));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			CarbonAtom ca = new CarbonAtom(cbc, count++);
			Thread thread = new Thread(ca);
			thread.start();
		
			try {
				cbc.waiton.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if((cbc.caList.size() > 0) && (cbc.haList.size() >= 4)){
				cbc.waiton.release();
				synchronized (cbc.bonding) {
					cbc.bonding.notify();
				}
			}
			else {
				cbc.waiton.release();
				
			}

		
		}
	}

}