package bridge.contracts;

import bridge.services.BridgeService;

public class BridgeContract extends LimitedRoadContract implements BridgeService {

	public BridgeContract(BridgeService delegate) {
		super(delegate);
	}

	@Override
	protected BridgeService getDelegate() {
		return (BridgeService) super.getDelegate();
	}
	
	@Override
	public int getNbIn() {
		return getDelegate().getNbIn();
	}

	@Override
	public int getNbOut() {
		return getDelegate().getNbOut();
	}
	
	public void checkInvariant() {
		super.checkInvariant();
		if(getDelegate().getNbIn() < 0)
			Contractor.defaultContractor().invariantError("BridgeService", "Nombre de voitures In incorrect (<0)");
		if(getDelegate().getNbOut() < 0)
			Contractor.defaultContractor().invariantError("BridgeService", "Nombre de voitures Out incorrect (<0)");
		if(!(getNbCars() == (getNbIn() + getNbOut()))){
				Contractor.defaultContractor().invariantError("BridgeService", "Nombre de voitures incorrect");
		}
		
	//	if(!(getNbIn() <= getNbOut()) ||  )
	}
	

	
	@Override
	public void init() {
		
		checkInvariant();
		getDelegate().init();
		checkInvariant();
	}

	@Override
	public void init(int lim) {
		
		checkInvariant();
		getDelegate().init(lim);
		checkInvariant();
		if(getNbIn()!=0)
			Contractor.defaultContractor().postconditionError("BridgeService", "init" ,"Nombre de voitures In incorrect");
		if(getNbIn()!=0)
			Contractor.defaultContractor().postconditionError("BridgeService", "init" ,"Nombre de voitures Out incorrect");

	}

	@Override
	public void enterIn() {
		if(isFull())
			Contractor.defaultContractor().preconditionError("BridgeService", "enterIn" ,"Bridge is full");	
		checkInvariant();
		
		int nb_car_pre = getNbCars();
		int nb_in_pre = getNbIn();
		int nb_out_pre = getNbOut();
		
		getDelegate().enterIn();
		
		checkInvariant();
		
		if(getNbCars() != nb_car_pre + 1){
			Contractor.defaultContractor().postconditionError("BridgeService", "enterIn" ,"Nombre de voitures incorrect via getnbCar");
		}
		if(getNbIn() != nb_in_pre + 1){
			Contractor.defaultContractor().postconditionError("BridgeService", "enterIn" ,"Nombre de voitures incorrect via getNbIn");
		}
		if(getNbOut() != nb_out_pre){
			Contractor.defaultContractor().postconditionError("BridgeService", "enterIn" ,"Change of value in nbOut in EnterIn function");
		}
		
		
		
	}

	@Override
	public void leaveIn() {
		if(!(getNbIn() > 0))
			Contractor.defaultContractor().preconditionError("BridgeService", "LeaveIn" ,"No cars available to go out island");
		checkInvariant();
		
		int nb_car_pre = getNbCars();
		int nb_in_pre = getNbIn();
		int nb_out_pre = getNbOut();

		
		getDelegate().leaveIn();
		
		checkInvariant();
		
		if(getNbCars() != nb_car_pre - 1){
			Contractor.defaultContractor().postconditionError("BridgeService", "enterIn" ,"Nombre de voitures incorrect via getnbCar");
		}
		if(getNbIn() != nb_in_pre - 1){
			Contractor.defaultContractor().postconditionError("BridgeService", "enterIn" ,"Nombre de voitures incorrect via getNbIn");
		}
		if(getNbOut() != nb_out_pre){
			Contractor.defaultContractor().postconditionError("BridgeService", "enterIn" ,"Change of value in nbOut in EnterIn function");
		}
	}

	@Override
	public void enterOut() {
		if(isFull())
			Contractor.defaultContractor().preconditionError("BridgeService", "enterOut" ,"Bridge is full");
		int nb_car_pre = getNbCars();
		int nb_in_pre = getNbIn();
		int nb_out_pre = getNbOut();

		checkInvariant();
		getDelegate().enterOut();
		checkInvariant();
		
		if(getNbCars() != nb_car_pre + 1){
			Contractor.defaultContractor().postconditionError("BridgeService", "enterOut" ,"Nombre de voitures incorrect");
		}
		if(getNbIn() != nb_in_pre)
			Contractor.defaultContractor().postconditionError("BridgeService", "enterOut" ,"Nombre de voitures In incorrect" + getNbIn() + nb_in_pre);
		if(getNbOut() != nb_out_pre +1){
			Contractor.defaultContractor().postconditionError("BridgeService", "enterOut" ,"No change of nbout in Enterout");
		}
	}

	@Override
	public void leaveOut() {
		if(!(getNbOut() > 0))
			Contractor.defaultContractor().preconditionError("BridgeService", "LeaveOut" ,"No cars available to go out");
		checkInvariant();
		
		int nb_car_pre = getNbCars();
		int nb_in_pre = getNbIn();
		int nb_out_pre = getNbOut();
		
		getDelegate().leaveOut();
		
		checkInvariant();
		
		if(getNbCars() != nb_car_pre - 1){
			Contractor.defaultContractor().postconditionError("BridgeService", "leaveOut" ,"Nombre de voitures incorrect ; p");
		}
		if(getNbIn() != nb_in_pre)
			Contractor.defaultContractor().postconditionError("BridgeService", "leaveOut" ,"Nombre de voitures In incorrect");
		if(getNbOut() != nb_out_pre  - 1){
			Contractor.defaultContractor().postconditionError("BridgeService", "leaveOut" ,"No change of nbout in leaveOut");
		}
	}
	
}
