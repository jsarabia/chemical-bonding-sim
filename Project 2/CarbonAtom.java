//Joseph Sarabia
//COP 4600
//4/11/13

public class CarbonAtom implements Runnable {

	private ChemicalBondingCreator cbc;
	private int count;

	public CarbonAtom(ChemicalBondingCreator cbc, int count) {
		this.cbc = cbc;
		this.count = count;
	}

	@Override
	public void run() {
		System.out.println("Carbon atom no: " + count + " created.");
		try {
			cbc.waiton.acquire();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

		cbc.caList.add(this);
		cbc.waiton.release();
		System.out.println("Carbon atom no: " + count
				+ " waiting for bonding.");
		try {
			synchronized (cbc.csem) {
				cbc.csem.wait();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		

		System.out.println("Carbon atom no: " + count + " bonded, done.");
		cbc.mol.release(1);
		
	}

}