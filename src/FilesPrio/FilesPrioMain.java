package FilesPrio;

import java.util.ArrayList;

public class FilesPrioMain {

	public static void main(String[] args)
	{
		ArrayList<FilesPrio<Integer>> array = new ArrayList<>();
		FilesPrioImpl<Integer> file = new FilesPrioImpl<Integer>();
		FilesPrioImplBug<Integer> fileBug = new FilesPrioImplBug<Integer>();
		array.add(file);
		array.add(fileBug);
		
		for(FilesPrio<Integer> F : array)
		{
			FilePrioContract<Integer> contract = new FilePrioContract<Integer>(F);
			
			contract.init();
			contract.put(Integer.valueOf(0));
			contract.put(Integer.valueOf(0));
			for(int i=0;i<5;i++)
				contract.putPrio(Integer.valueOf(0),i);
			
			System.out.println("AVANT REMOVE :"+contract.getSizePrio(contract.getMaxPrio()));
			contract.remove();
			System.out.println("APRES REMOVE :"+contract.getSizePrio(contract.getMaxPrio()));
		}
	}
}
