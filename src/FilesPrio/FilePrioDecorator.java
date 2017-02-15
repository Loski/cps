package FilesPrio;

import java.util.Set;

public abstract class FilePrioDecorator<T> implements FilesPrio<T>{
	
	private FilesPrio<T> delegate;
	
	public FilePrioDecorator(FilesPrio<T> file){
		this.delegate = file;
	}

	@Override
	public int getSize() {
		return delegate.getSize();
	}

	@Override
	public boolean isEmpty() {
		return delegate.isEmpty();
	}

	@Override
	public Set<Integer> getActivePrios() {
		// TODO Auto-generated method stub
		return delegate.getActivePrios();
	}

	@Override
	public boolean isActive(int i) {
		// TODO Auto-generated method stub
		return delegate.isActive(i);
	}

	@Override
	public int getMaxPrio() {
		// TODO Auto-generated method stub
		return delegate.getMaxPrio();
	}

	@Override
	public int getSizePrio(int i) {
		// TODO Auto-generated method stub
		return delegate.getSizePrio(i);
	}

	@Override
	public T getPrio(int i) {
		// TODO Auto-generated method stub
		return delegate.getPrio(i);
	}

	@Override
	public T getElem() {
		// TODO Auto-generated method stub
		return delegate.getElem();
	}

	@Override
	public T getElemPrio(int i, int k) {
		// TODO Auto-generated method stub
		return delegate.getElemPrio(i, k);
	}

	@Override
	public void init() {
		delegate.init();
	}

	@Override
	public void putPrio(int i, T e) {
		delegate.putPrio(i, e);
	}

	@Override
	public void put(T e) {
		delegate.put(e);
		
	}

	@Override
	public void removePrio(int i) {
		delegate.removePrio(i);
		
	}

	@Override
	public void remove() {
		delegate.remove();
	}
}
