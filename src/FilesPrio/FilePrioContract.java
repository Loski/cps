package FilesPrio;

import java.util.LinkedList;
import java.util.Set;

public class FilePrioContract<T> extends FilePrioDecorator<T>{

	public FilePrioContract(FilesPrio<T> file) {
		super(file);
	}
	
	public void checkInvariant(){
		Set<Integer> keys = getActivePrios();
		try {
			checkSize(keys);
			checkEmpty();
			checkActive(keys);
			checkPrio(keys);
			checkSize(keys);
			checkElem();
			checkActivePrio(keys);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			throw new FilePrioError("IsEmpty => getSize diff�rents");
	}
	
	public void checkActive(Set<Integer> keys) throws Exception{
		for(Integer i: keys){
			if(isActive(i.intValue())){
				if(!getActivePrios().contains(i))
					throw new Exception("Probl�me de priorit�");
			}
		}
	}
	
	public void maxPrio(Set<Integer> keys) throws Exception{
		int max = getMaxPrio();
		for(Integer i: keys){
			if(max < i)
				throw new Exception("Max not real max");
		}
	}
	public void checkPrio(Set<Integer> keys) throws Exception{
		for(Integer i: keys){
			if(!getPrio(i.intValue()).equals(getElemPrio(i.intValue(), 0)))
				throw new Exception("Not equals");
		}
	}
	
	public void checkElem() throws Exception{
		T elem = getElem();
		if(!elem.equals(getPrio(getMaxPrio())))
			throw new Exception("Pas le même élément !");
	}
	
	public void checkActivePrio(Set<Integer> keys) throws Exception{
		for(Integer i: keys){
			if(getSizePrio(i.intValue()) <= 0 ){
				throw new Exception("Mauvaise activation");
			}
		}
		
		
		/* 
		 * activePrios(P), sizePrio(P,i)= 0
		 *		Pas besoin (hashmap)
		 */
		for(Integer i: keys){
			if(getSizePrio(i.intValue()) <= 0 ){
				throw new Exception("Mauvaise activation");
			}
		}
	}
	
	public void putPrio(int i, T e){
		try{
			if(i == 0)
	        	throw new FilePrioError("Mauvaise valeur pour i");
	    	else if(e==null)
	    		throw new FilePrioError("Elément non défini");
			checkInvariant();
			
			boolean is_active_pre = isActive(i);
			Set<Integer> keys_pre = getActivePrios();
			int taille_pre = getSizePrio(i);
			
			super.putPrio(i, e);
			
			if(is_active_pre || getActivePrios() != keys_pre)
				throw new Exception("Put failed");
			keys_pre.add(new Integer(i));
			if(!is_active_pre || getActivePrios() != keys_pre)
				throw new Exception("Put failed");
			if(getSizePrio(i) == taille_pre +1 )
				throw new Exception("Size failed");
		} catch (Exception exception) {
			// TODO Auto-generated catch block
			exception.printStackTrace();
		}	
	}
}
