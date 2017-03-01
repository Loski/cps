package lift.test;

import lift.services.LiftService;

public class LiftTest3 extends AbstractLiftTest{
	@Override
	public void beforeTests() {
		LiftService lift = new liftimpl3.Lift3(); 
		LiftService liftCommand = new liftimpl3.Lift3(); 
		this.setLift(lift);
		lift.getCommands().init();
		liftCommand.getCommands().init();
		this.setCommands(liftCommand.getCommands());
	}

}
