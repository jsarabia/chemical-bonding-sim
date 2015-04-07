//Joseph Sarabia
//COP 4600
//4/11/13
package bonding;

import java.util.Scanner;
import java.util.Vector;



public class Main {

	
	public static final void main(String args[]) {
		System.out.println("Project 2 Extra Credit");
		System.out.println("Please enter a Chemical Formula");
		Scanner input = new Scanner(System.in);
		String formula = input.nextLine();
		input.close();
		
		String[] parts = formula.split(" ", 0);
		Vector<String> atoms = new Vector<>();
		Vector<Integer> bonds = new Vector<>();


		for (int i = 0; i < parts.length; i+=2){
			atoms.add(parts[i]);
			if(i+1 < parts.length){
				bonds.add(Integer.parseInt(parts[i+1]));
			}
			else {
				System.out.println("Error, expected the number of Atoms following the Atom name");
				return;
			}
				
		}

		
	
		BondingCreator bc = new BondingCreator(bonds);
		Thread thread = new Thread(bc);
		thread.start();
		
		AtomGenerator ag;
		
		for (int i = 0; i < atoms.size(); i++){
			ag = new AtomGenerator(bc, atoms.elementAt(i), i);
			thread = new Thread(ag);
			thread.start();
		}
		
		
		
	}
	
	
}