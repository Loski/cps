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
	public void testOpenDoor(){
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
	public void testCloseDoor(){
		lift.init(2,  10);
		
		lift.selectLevel(3);
		lift.doorAck();
		lift.closeDoor();
		
		assertEquals(DoorStatus.CLOSING,lift.getDoorStatus());
	}
	
	@Test
	public void testSelectLevelUp(){
		lift.init(2,  10);
		CommandsImpl cmd = (CommandsImpl) lift.getCommands();
		int level = lift.getLevel();
		//test UP
		lift.selectLevel(4);
		test(level, cmd, 4);
	}
	
	@Test
	public void testSelectLevelEquals(){
		lift.init(2,  10);
		CommandsImpl cmd = (CommandsImpl) lift.getCommands();
		int level = lift.getLevel();
		//test Equals
		lift.selectLevel(2);
		test(level, cmd, 2);
	}
	
	
	@Test
	public void testSelectLevelDown(){
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
		//test DOWN
		int level = lift.getLevel();
		lift.selectLevel(4);
		test(level, commands, 4);
	}
	
	public void test(int level, CommandsService cmd, int expected_level){
		if(level == lift.getLevel().intValue()){
			assertEquals(cmd, lift.getCommands());
		}
		else if(level > lift.getLevel()){
			cmd.addUpCommand(expected_level);
			assertEquals(cmd, lift.getCommands());
		}
		else{
			System.out.println(cmd);
			cmd.addDownCommand(expected_level);
			assertEquals(cmd, lift.getCommands());
		}
	}

	@Test
	public void testDoorHackClosed()
	{
		lift.init(2,  10);
				
		lift.closeDoor();
		DoorStatus doorStatusPre = lift.getDoorStatus();
		lift.selectLevel(7);
		lift.doorAck();
		
		assertTrue(!(doorStatusPre == DoorStatus.CLOSING) || DoorStatus.CLOSED == lift.getDoorStatus());
	}
	
	@Test
	public void testDoorHackOpened()
	{
		lift.init(2,  10);
		
		lift.closeDoor();
		lift.selectLevel(3);
		lift.doorAck();
		lift.beginMoveUp();
		lift.stepMoveUp();
		lift.endMoveUp();
		lift.openDoor();
		
		DoorStatus doorStatusPre = lift.getDoorStatus();
		
		lift.doorAck();
		assertTrue(!(doorStatusPre == DoorStatus.OPENING) || DoorStatus.OPENED == lift.getDoorStatus());
	}
	
	@Test
	public void testDoorHackStandByUp()
	{
		lift.init(2,  10);
		
		lift.closeDoor();
		lift.selectLevel(3);
		lift.doorAck();
		
		LiftStatus LiftStatusPre = lift.getLiftStatus();
		
		lift.doorAck();
		
		assertTrue(!(LiftStatusPre == LiftStatus.IDLE && lift.getCommands().getNbUpCommands()>0) || LiftStatus.STANDBY_UP == lift.getLiftStatus());
	}
	
	@Test
	public void testDoorHackStandByDown()
	{
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
		
		LiftStatus LiftStatusPre = lift.getLiftStatus();
		
		lift.doorAck();
		
		
		assertTrue(!(LiftStatusPre == LiftStatus.IDLE && lift.getCommands().getNbDownCommands()>0) || LiftStatus.STANDBY_DOWN == lift.getLiftStatus());
	}
	
	@Test
	public void testDoorHackIdle()
	{
		lift.init(2,  10);
		
		lift.closeDoor();
		
		LiftStatus LiftStatusPre = lift.getLiftStatus();
		lift.doorAck();
		
		assertTrue(!(LiftStatusPre == LiftStatus.IDLE && lift.getCommands().getNbDownCommands()==0 && lift.getCommands().getNbUpCommands()==0) || LiftStatus.IDLE == lift.getLiftStatus());

		lift.openDoor();
		lift.doorAck();
	}
}
