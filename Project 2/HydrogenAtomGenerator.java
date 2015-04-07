//Joseph Sarabia
//COP 4600
//4/11/13
import java.util.Random;

public class HydrogenAtomGenerator implements Runnable {

	private Random random = new Random();
	int count = 0;
	private ChemicalBondingCreator cbc;

	public HydrogenAtomGenerator(ChemicalBondingCreator cbc) {
		this.cbc = cbc;
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
			HydrogenAtom ha = new HydrogenAtom(cbc, count++);
			Thread thread = new Thread(ha);
			thread.start();
		}
	}

}
