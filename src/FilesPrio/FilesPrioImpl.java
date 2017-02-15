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
		int size = 0;
		for(LinkedList<T> list: map.values()){
			size+= list.size();
		}
		return size;
	}
	
	@Override
	public boolean isEmpty() {
		return this.getSize() == 0;
	}

	@Override
	public Set<Integer> getActivePrios() {
		return map.keySet();
	}

	@Override
	public boolean isActive(int i) {
		return getMaxPrio() == i;
	}

	@Override
	public int getMaxPrio() {
		Set<Integer> key = getActivePrios();
		if(key.isEmpty())
			return -1;
		int min = Integer.MIN_VALUE;
		for(Integer i:key){
			if(i > min)
				min = i;
		}
		return min;
	}

	@Override
	public int getSizePrio(int i) {
		Integer key = new Integer(i);
		Set<Integer> keys = getActivePrios();
		if(!keys.contains(key))
			return 0;
		return map.get(key).size();
	}

	@Override
	public T getPrio(int i) {
		return getElemPrio(i, 0);
	}

	@Override
	public T getElem() {
		return map.get(getMaxPrio()).getFirst();
	}

	@Override
	public T getElemPrio(int i, int k) {
		return map.get(i).get(k);
	}

	@Override
	public void init() {
		this.map = new HashMap<Integer, LinkedList<T>>();
	}

	@Override
    public void putPrio(int i, T e){

        
        if(map.get(Integer.valueOf(i))!=null)
        {
            map.get(Integer.valueOf(i)).add(e);
        }
        else
        {
            LinkedList<T> list = new LinkedList<T>();
            list.add(e);
            map.put(Integer.valueOf(i),list);
        }
    }

    @Override
    public void put(T e) {
        // TODO Auto-generated method stub
        /*if(e !=null)
        {*/
            this.putPrio(0,e);
        //}
    }

    @Override
    public void removePrio(int i) {
        // TODO Auto-generated method stub
        /*if(getSizePrio(i)>0)
        {*/
        if(map.get(Integer.valueOf(i))!=null)
            map.remove(Integer.valueOf(i));
        //}
    }

    @Override
    public void remove() {
        // TODO Auto-generated method stub
        for (Integer key : map.keySet()) {
            removePrio(key.intValue());
        }
    }
    
    

}
