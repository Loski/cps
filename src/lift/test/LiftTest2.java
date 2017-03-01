package lift.test;
import lift.services.LiftService;
import liftimpl1.*;
public class LiftTest2 extends AbstractLiftTest{

	@Override
	public void beforeTests() {
		LiftService lift = new liftimpl2.Lift2(); 
		LiftService liftCommand = new liftimpl2.Lift2(); 
		this.setLift(lift);
		lift.getCommands().init();
		liftCommand.getCommands().init();
		this.setCommands(liftCommand.getCommands());
	}

}
