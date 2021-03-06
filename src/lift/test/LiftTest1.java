package lift.test;
import lift.services.LiftService;
import liftimpl1.*;
public class LiftTest1 extends AbstractLiftTest{

	@Override
	public void beforeTests() {
		LiftService lift = new liftimpl1.Lift1(); 
		LiftService liftCommand = new liftimpl1.Lift1(); 
		this.setLift(lift);
		lift.getCommands().init();
		liftCommand.getCommands().init();
		this.setCommands(liftCommand.getCommands());
	}

}
