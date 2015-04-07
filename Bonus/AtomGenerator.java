//Joseph Sarabia
//COP 4600
//4/11/13
package bonding;

import java.util.Random;

public class AtomGenerator implements Runnable {

	private Random random = new Random();
	int count = 0;
	private BondingCreator bc;
	private String aname;
	private int position;

	public AtomGenerator(BondingCreator bc, String aname, int position) {
		this.bc = bc;
		this.aname = aname;
		this.position = position;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep((long) (3000.0 * random.nextDouble()));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Atom ha = new Atom(bc, count++, aname, position);
			Thread thread = new Thread(ha);
			thread.start();
		}
	}

}