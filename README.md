# RobotiqGripper2F85EtherCat
Controlling the Robotiq 2-Finger Gripper 2F85 with Kuka LBR IIWA Via Java Program OR the SmartPad userKeys.

Steps:
1 - Create an IOConfiguration.wvs file on sunrise then openit with WorkVisual.
2 - Add the robotiq Gripper's EtherCat description File "Robotiq_AG_ECS_20181109.xml" or download it from:
https://s3.amazonaws.com/com-robotiq-website-prod-assets/website-assets/support_documents/document/Robotiq_AG_ECS_20181109.xml
3- Add the gripper's controller  "NIC 50-RE/ECS" to the KUKA Extension BUS (SYS-44) 
4- Follow the steps in the images in folder /WorkVisualConfiguration to MAP your Inputs and outputs. If you don't know how, refer to KUKA's Manual Chapter 11 "Bus configuration"  for Configuration and I/O mapping in WorkVisual. It's explained in detail.
5- Export your config to Sunrise Workbench and restart the robot.
6- Copy the files in folder "/SunriseWorkBenchFiles" to your sunrise project as follows:
  6.1. Don't need to cpopy "RobotiqGripperIOGroup" as it's automatically generated (use mine just to compare)
  6.2. Copy the Java Class file "RobotiqGripper2F85" that contains the gripper's manipulation functions.
  6.3. Copy GripperInit to use is as a simple program to test your gripper
  6.4. Copy GripperDemo to see en example of the essential functions to control the gripper
  6.5. Copy "RoboticsAPI.data.xml" content to the end of your original file "RoboticsAPI.data.xml". It contains variables that the SmartPad UserKey program uses.
  6.6. Copy "RobotiqGripperKeys.java", to use the smartPad directly to control the gripper. (See: Folder "/SmartPad userKey Screenshots")
 

