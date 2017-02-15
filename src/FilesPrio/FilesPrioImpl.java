package FilesPrio;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

public class FilesPrioImpl<T> implements FilesPrio<T> {

	private HashMap<Integer, LinkedList<T>> map;
	
	@Override
	public int getSize() {
		return map.size();
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public Set<Integer> getActivePrios() {
		return map.keySet();
	}

	@Override
	public boolean isActive(int i) {
		return (i == 0); 
	}

	@Override
	public int getMaxPrio() {
		Set<Integer> key = getActivePrios();
		if(key.isEmpty())
			return -1;
		int max = Integer.MAX_VALUE;
		for(Integer i:key){
			if(i < max)
				max = i;
		}
		return max;
	}

	@Override
	public int getSizePrio(int i) {
		Integer key = new Integer(i);
		Set<Integer> keys = getActivePrios();
		if(keys.contains(key))
			return 0;
		return map.get(key).size();
	}

	@Override
	public int getPrio(int i) {
		return getMaxPrio()
	}

	@Override
	public T getElem() {
		return map.get(getMaxPrio()).removeFirst();
	}

	@Override
	public T getElemPrio(int i, int k) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init() {
		this.map = new HashMap<Integer, LinkedList<T>>();
	}

	@Override
	public void putPrio(int i, T e) throws FilePrioError {
		/*if(i == 0)
			throw new FilePrioError("Mauvaise valeur pour i");
		else if(e==null)
			throw new FilePrioError("Elément non défini");*/
		Integer key = new Integer(i);
		
	}

	@Override
	public void put(T e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removePrio(int i) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}

}
