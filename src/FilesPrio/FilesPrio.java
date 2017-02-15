package FilesPrio;

import java.util.Set;

public interface FilesPrio<T> {
	/*	observator	*/
	public int getSize();
	public boolean isEmpty();
	public Set<Integer> getActivePrios();
	public boolean isActive(int i);
	public int getMaxPrio();
	public int getSizePrio(int i);
	// \pre : getSizePrio(i) > 0
	public T getPrio(int i);
	
	// \pre : getSize() > 0
	public T getElem();
	
	// \pre: i \in getActivePrios() && k > 0 && k <= sizePrio(i)
	public T getElemPrio(int i, int k);
	
	
	/* invariant */
	// \inv : getSize() == \sum_ {i \in getActivePrios()} sizePrio(i)
	// \inv : isEmpty() == (getSize() == 0)
	// \inv : isActive(int i) == (i \in getActivePrios())
	// \inv :getMaxPrio() == max(getActivePrios()
	// \inv :getPrio(i) == getElemPrio(i,1)
	// \inv : getElem() == getPrio(getMaxPrio())
	// \inv : \forall i:int with i  \in getActivePrio() {getSizePrio(i) > 0}
	// \inv : \forall i:int with i  \notIn getActivePrio() {getSizePrio(i) == 0}
	/* \inv : \forall i:int with i  \notIn getActivePrio() {
			  	\forall k:int with k \In [1, getSizePrio(i)]{
			 		getElemprio(i,k) != null
			 	}
			 }i
	*/
	/* init */
	
	// \post: getSize() == 0 
	public void init();
	
	/* Operators */
	
	// \pre: i > 0 && e != null
	// \post: (not isActive(i) ) \_union (getActivePrios() == getActivePrios()@pre))
	// \post  (isActive(i) ) \_union (getActivePrios() == getActivePrios()@pre) \_union {i})
	// \post: (not getSizePrio()) \_union getSizePrio()@pre +1
	// \post: 
	// \post: \forall k \in [2,getSizePrio(i)+1] {getElem(putPrio(i,e),j,k) = elemPrio(j,k)}
	// post: \forall j \in activePrios()\{i} { \forall k \in [1,getSizePrio(j)] { getElemPrio(removePrio(i),j,k) = getElemPrio(j,k) } } 
	public void putPrio(int i, T e);
	
	// \pre i > 0 && e != null
	// \post put(e) == putPrio(e, getMaxPrio())
	public void put(T e);
	
	// \pre getSizePrio(i) > 0
	
	// \pre sizePrio(P,i)>1 =) activePrios(removePrio(P,i)) = activePrios(P)
			/*sizePrio(P,i)=1 =) activePrios(removePrio(P,i)) = activePrios(P) nfig
			sizePrio(removePrio(P,i),i) = sizePrio(P,i) - 1
			8j 2 activePrios(P)nfig, sizePrio(removePrio(P,i),j) = sizePrio(P,j)
			8k 2 [1::sizePrio(P,i)-1], elemPrio(removePrio(P,i),i,k) = elemPrio(P,i,k)
			8i 2 activePrios(P)nfig, 8k 2 [1::sizePrio(P,j)], elemPrio(removePrio(P,i),j,k) = elemPrio(P,j,k)
			*/
	// \pre getSizePrio(i) > 1 == getActivePrio(removePro(i)) = activePrios()		
	public void removePrio(int i);
	
	// \pre getSize(i) > 0
	// \pre remove(P) = removePrio(getMaxPrio())
	public void remove();
}
