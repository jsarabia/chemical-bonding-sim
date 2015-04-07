//Joseph Sarabia
//COP 4600
//4/11/13
public class HydrogenAtom implements Runnable {

	private ChemicalBondingCreator cbc;
	private int count;

	public HydrogenAtom(ChemicalBondingCreator cbc, int count) {
		this.cbc = cbc;
		this.count = count;
	}

	@Override
	public void run() {
		System.out.println("Hydrogen atom no: " + count + " created.");
		try {
			cbc.waiton.acquire();
		} catch (InterruptedException e1) {
			//TODO Auto-generated catch block
		 	e1.printStackTrace();
		}
		cbc.haList.add(this);
		cbc.waiton.release();
		
		System.out.println("Hydrogen atom no: " + count
				+ " waiting for bonding.");
		try {
			synchronized (cbc.hsem) {
				cbc.hsem.wait();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Hydrogen atom no: " + count + " bonded, done.");
		cbc.mol.release(1);
	}

}
