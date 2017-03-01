package lift.test;

import static org.junit.Assert.*;
import lift.contracts.CommandsContract;
import lift.impl.CommandsImpl;
import lift.services.CommandsService;
import lift.services.DoorStatus;
import lift.services.LiftService;
import lift.services.LiftStatus;

import org.junit.*;

public abstract class AbstractLiftTest {
	private LiftService lift;
	private CommandsService commands;
	
	protected AbstractLiftTest() {
		lift = null;
		commands = null;
	}
	
	protected final LiftService getLift() {
		return lift;
	}
	
	protected final CommandsService getCommands() {
		return commands;
	}
	
	protected final void setLift(LiftService lift) {
		this.lift = lift;
	}
	
	protected final void setCommands(CommandsService commands) {
		this.commands = commands;
	}
	
	@Before
	public abstract void beforeTests(); 

	@After
	public final void afterTests() {
		checkInvariant();
		lift = null;
		commands = null;
	}

	public void checkInvariant(){
		assertTrue("LiftService bien initialisé", lift.getMinLevel() <= lift.getLevel() && lift.getMaxLevel() >= lift.getLevel());
		LiftStatus status = lift.getLiftStatus();
		assertTrue(!(status == LiftStatus.MOVING_DOWN || status == LiftStatus.MOVING_UP) || DoorStatus.CLOSED == lift.getDoorStatus());
		assertTrue(!(status == LiftStatus.IDLE) || DoorStatus.OPENED == lift.getDoorStatus());
	}
	@Test
	public void testInit(){
		lift.init(2, 4);
		//lift.init(8, 4);
		//assertFalse("Problème d'initialisation", lift.getMinLevel() <= lift.getLevel() && lift.getMaxLevel() >= lift.getLevel());
		assertTrue(lift.getMinLevel().intValue() ==  2);
		assertEquals(4, lift.getMaxLevel().intValue());
		assertEquals( 2, lift.getLevel().intValue());
		assertEquals( LiftStatus.IDLE, lift.getLiftStatus());
		assertEquals(DoorStatus.OPENED, lift.getDoorStatus());
		commands.init();
		//assertEquals(commands.get, lift.getCommands());
	}
	
	@Test
	public void testBeginMoveUp(){
		lift.init(2,  10);
		lift.closeDoor();
		lift.selectLevel(3);
		lift.doorAck();
		lift.beginMoveUp();
		assertEquals(LiftStatus.MOVING_UP, lift.getLiftStatus());
	}
	
	
	
	@Test
	public void testBeginMoveDown(){
		lift.init(2,  10);
		
		/* On monte à l'étage 7*/
		lift.closeDoor();
		lift.selectLevel(7);
		lift.doorAck();
		lift.beginMoveUp();
		for(int i=lift.getLevel().intValue();i<7;i++)
			lift.stepMoveUp();
		lift.endMoveUp();
		lift.openDoor();
		lift.doorAck();
		/* Arrivé à l'étage 7 */
		
		
		lift.selectLevel(3);
		lift.closeDoor();
		lift.doorAck();
		lift.beginMoveDown();
		assertEquals(LiftStatus.MOVING_DOWN, lift.getLiftStatus());
	}
	
	
	@Test
	public void testStepMoveUp(){
		lift.init(2,  10);
		
		int level = lift.getLevel().intValue();
		
		lift.closeDoor();
		lift.selectLevel(7);
		lift.doorAck();
		lift.beginMoveUp();
		lift.stepMoveUp();
		lift.endMoveUp();
		
		assertEquals(lift.getLevel().intValue(), level + 1);
	}
	
	@Test
	public void endMoveUp(){
		lift.init(2,  10);
		
		int level = lift.getLevel().intValue();
		
		lift.closeDoor();
		lift.selectLevel(7);
		lift.doorAck();
		lift.beginMoveUp();
		lift.stepMoveUp();
		lift.endMoveUp();
		
		assertEquals(LiftStatus.STOP_UP,lift.getLiftStatus());
		/* ???????? */
		//assertEquals(lift.getCommands().endUpCommand(),lift.getLiftStatus());
	}
	
	@Test
	public void openDoor(){
		lift.init(2,  10);
		
		lift.closeDoor();
		lift.selectLevel(3);
		lift.doorAck();
		lift.beginMoveUp();
		lift.stepMoveUp();
		lift.endMoveUp();
		lift.openDoor();
		
		assertEquals(DoorStatus.OPENING,lift.getDoorStatus());
	}
	
	@Test
	public void closeDoor(){
		lift.init(2,  10);
		
		lift.selectLevel(3);
		lift.doorAck();
		lift.closeDoor();
		
		assertEquals(DoorStatus.CLOSING,lift.getDoorStatus());
	}
}
