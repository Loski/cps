package FilesPrio;

import java.util.LinkedList;
import java.util.Set;

public class FilePrioContract<T> extends FilePrioDecorator<T>{

	public FilePrioContract(FilesPrio<T> file) {
		super(file);
	}
	
	public void checkInvariant(){
		Set<Integer> keys = getActivePrios();
	}
	
	
	public void checkSize(Set<Integer> keys) throws Exception{
		int size = 0;
		for(Integer i : keys){
			size+= getSizePrio(i.intValue());
		}
		if(size != getSize())
			throw new Exception("Erreur de taille !");
	}
	
	public void checkEmpty() throws FilePrioError{
		boolean check = this.isEmpty();
		boolean check2 = this.getSize() == 0;
		if(check != check2)
			throw new FilePrioError("IsEmpty => getSize différents");
	}
	
	public void checkActive(Set<Integer> keys) throws Exception{
		for(Integer i: keys){
			if(isActive(i.intValue())){
				if(!getActivePrios().contains(i))
					throw new Exception("Problème de priorité");
			}
		}
	}
	
	public void maxPrio(){
		int i = getMaxPrio();
		
	}
}
