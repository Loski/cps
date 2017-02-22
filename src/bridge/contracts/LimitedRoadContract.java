package bridge.contracts;

import bridge.decorators.LimitedRoadDecorator;
import bridge.services.LimitedRoadService;

public class LimitedRoadContract extends LimitedRoadDecorator {

	public LimitedRoadContract(LimitedRoadService delegate) {
		super(delegate);
	}

	public void checkInvariant() {
		if(!(isFull() == (getNbCars() == getLimit())))
			Contractor.defaultContractor().invariantError("LimitedRoadService", "isFull bug");
		if(!(getNbCars() <= getLimit()))
			Contractor.defaultContractor().invariantError("LimitedRoadService", "Limite de voiture dépassée");
	}
	
	@Override
	public void init(int lim) {
		if(!(lim > 0))
			Contractor.defaultContractor().preconditionError("LimitedRoadService", "init", "limit init error");
		super.init();
		if(!(getLimit() == getLimit()))
			Contractor.defaultContractor().postconditionError("LimitedRoadService", "init", "limit init error");
		if((getNbCars() != 0))
			Contractor.defaultContractor().postconditionError("LimitedRoadService", "init", "number cars init error");

	}
	
	public void enter(){
		if(isFull())
			Contractor.defaultContractor().preconditionError("LimitedRoadService", "enter", "Can't enter, max cars already in");
		super.enter();
	}
}
