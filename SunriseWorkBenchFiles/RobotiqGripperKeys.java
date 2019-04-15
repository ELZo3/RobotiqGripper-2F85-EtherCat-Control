package robotiq.gripper.twoFingersF85;

import javax.inject.Inject;  
import java.util.concurrent.TimeUnit;
import com.kuka.roboticsAPI.uiModel.userKeys.IUserKey; 
import com.kuka.roboticsAPI.controllerModel.Controller;  
import com.kuka.roboticsAPI.uiModel.userKeys.IUserKeyBar;
import com.kuka.roboticsAPI.uiModel.userKeys.UserKeyEvent;
import com.kuka.roboticsAPI.uiModel.userKeys.IUserKeyListener;
import com.kuka.roboticsAPI.uiModel.userKeys.UserKeyAlignment; 
import com.kuka.roboticsAPI.applicationModel.tasks.CycleBehavior;
import com.kuka.roboticsAPI.applicationModel.tasks.RoboticsAPICyclicBackgroundTask; 


public class RobotiqGripperKeys extends RoboticsAPICyclicBackgroundTask {
	@Inject
	private Controller controller; 
	private RobotiqGripper2F85 robotiqGripper ; 
	String displayStr1="N";
	String displayStr2="N";
	String str;
	IUserKey gripperDoubleKey;
	IUserKey acknowledgeDoubleKey ; 
	// Gripper variables
	private int positionRequestEcho = 4; 
	private int positionRequest = 4;  
	private int step = 10;
	private int speed = 128;
	private int force = 35;

	private  static final int maxVal = 227;
	private  static final int minVal = 4;
	// *********************************************************************************************************
	// *********************************************************************************************************	 
	@Override
	public void initialize() {
		// initialize your task here
		initializeCyclic(0, 5, TimeUnit.MILLISECONDS,CycleBehavior.BestEffort);
		controller = (Controller) getContext().getControllers().toArray()[0]; 
		robotiqGripper = new RobotiqGripper2F85(controller);   
		step =  getApplicationData().getProcessData("gripperStep").getValue();  
		speed = getApplicationData().getProcessData("gripperSpeed").getValue();  
		force = getApplicationData().getProcessData("gripperForce").getValue();   
		positionRequest = robotiqGripper.getPosition();
		positionRequestEcho = positionRequest;
		// ===================================================================================================
		// ==================== GripperBar configuration =======================================================
		// ===================================================================================================
		IUserKeyBar gripperKeyBar =  getApplicationUI().createUserKeyBar("RobotiqGripper");
		// ===================================================================================================
		IUserKeyListener gripperListener = new IUserKeyListener(){ 
			@Override 
			public void onKeyEvent(IUserKey key, UserKeyEvent event) 
			{	 
				// ==================================================================
				//  =======  First button to close Gripper ========================
				if(event == UserKeyEvent.FirstKeyDown){ 
					// ********
					// Value 0 For Open 255 for Fully Closed  

					getGripperProcessData(); 
					positionRequestEcho =  robotiqGripper.getPositionRequestEcho()- step; 

					if (positionRequestEcho<minVal) 
						positionRequest = minVal; 
					else   
						if (positionRequestEcho > maxVal)  
							positionRequest = maxVal;
						else  
							positionRequest = positionRequestEcho;  
					robotiqGripper.moveToHex(positionRequest,speed,force); 
					robotiqGripper.waitForMoveEnd();
					//robotiqGripper.waitForObjectDetected();  
					// ********

					displayStr1 = robotiqGripper.getPosition().toString(); 
					key.setText(UserKeyAlignment.Middle, displayStr1);



				}
				// ================================================================
				// =======  Second button to Open the Gripper ===================== 
				else if(event == UserKeyEvent.SecondKeyDown ) 
				{  
					// ********

					getGripperProcessData(); 
					positionRequestEcho =  robotiqGripper.getPositionRequestEcho()+ step; 

					if (positionRequestEcho<minVal) {
						positionRequest = minVal; 
					} 
					else  {
						if (positionRequestEcho > maxVal)  {
							positionRequest = maxVal;
						} 	
						else   {
							positionRequest = positionRequestEcho;  
							robotiqGripper.moveToHex(positionRequest,speed,force);
							robotiqGripper.waitForMoveEnd();
						} 
					}

					// ********

					displayStr1 = robotiqGripper.getPosition().toString(); 
					key.setText(UserKeyAlignment.Middle, displayStr1); 

				} 
			}
		};
		// ============  
		IUserKeyListener releaseListener = new IUserKeyListener(){
			@Override
			public void onKeyEvent(IUserKey key, UserKeyEvent event) 
			{ 

				if(event == UserKeyEvent.FirstKeyDown  ){  
					//robotiqGripper.activate() ;
					//robotiqGripper.waitForInitialization() ;
					robotiqGripper.fullyOpen() ;
					robotiqGripper.waitForfullyOpen();   
					displayStr2 = "Open"; 
					key.setText(UserKeyAlignment.Middle, displayStr2);  
				}
				else if(event == UserKeyEvent.SecondKeyDown  ){ 
					robotiqGripper.fullyClose() ;
					robotiqGripper.waitForfullyClosed(); 
					displayStr2 = "Closed"; 
					key.setText(UserKeyAlignment.Middle, displayStr2); 

				} 
				//value = "Released"; 

			}
		};

		// ===================================================================================================
		// Gripper Double Keys 
		gripperDoubleKey = gripperKeyBar.addDoubleUserKey(0,gripperListener, false); 
		gripperDoubleKey.setText(UserKeyAlignment.TopMiddle, "Open");
		gripperDoubleKey.setText(UserKeyAlignment.Middle,"val");
		gripperDoubleKey.setText(UserKeyAlignment.BottomMiddle, "Close");

		// Step Double Keys 
		acknowledgeDoubleKey = gripperKeyBar.addDoubleUserKey(2,releaseListener, false); 
		acknowledgeDoubleKey.setText(UserKeyAlignment.TopMiddle, "FullOpen");
		acknowledgeDoubleKey.setText(UserKeyAlignment.Middle,"--");
		acknowledgeDoubleKey.setText(UserKeyAlignment.BottomMiddle, "FullClose");


		// ===================================================================================================

		gripperKeyBar.publish(); 

	}

	// *********************************************************************************************************
	// *********************************************************************************************************
	// *********************************************************************************************************
	@Override
	public void runCyclic() {

		//acknowledgeDoubleKey.setText(UserKeyAlignment.Middle, String.valueOf(gripper).substring(1,5));
	}  

	// *********************************************************************************************************
	// *********************************************************************************************************
	// *********************************************************************************************************
	private void getGripperProcessData() { 
		// Update the Gripper process Data
		this.step  = getApplicationData().getProcessData("gripperStep").getValue();  
		this.speed = getApplicationData().getProcessData("gripperSpeed").getValue();  
		this.force = getApplicationData().getProcessData("gripperForce").getValue();  
	}  


	// ******************************************





	// ******************************************

}


