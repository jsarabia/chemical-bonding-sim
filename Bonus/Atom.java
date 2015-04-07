//Joseph Sarabia
//COP 4600
//4/11/13


package bonding;

public class Atom implements Runnable {

	private BondingCreator bc;
	private int count;
	private String name;
	private int position;

	public Atom(BondingCreator bc, int count, String name, int position) {
		this.bc = bc;
		this.count = count;
		this.name = name;
		this.position = position;
	}

	@Override
	public void run() {
		System.out.println(name + " atom no: " + count + " created.");
		try {
			bc.waiton.acquire();
		} catch (InterruptedException e1) {
			//TODO Auto-generated catch block
		 	e1.printStackTrace();
		}
		bc.List.elementAt(position).add(this);
		bc.waiton.release();
		
		System.out.println(name +" atom no: " + count
				+ " waiting for bonding.");
		try {
			synchronized (bc.asem.elementAt(position)) {
				bc.asem.elementAt(position).wait();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(name +" atom no: " + count + " bonded, done.");
		bc.mol.release(1);
	}

}
