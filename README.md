# RobotiqGripper2F85EtherCat
Controlling the Robotiq 2-Finger Gripper 2F85 with Kuka LBR IIWA Via Java Program OR the SmartPad userKeys.

Steps:<br/>
0. Follow the gripper manual to wire the gripper. Then, connect your robot's X65 interface to the gripper's controller via an ethernet cable <br/>
1 - Create an IOConfiguration.wvs file on sunrise then openit with WorkVisual.<br/>
2 - Add the robotiq Gripper's EtherCat description File "Robotiq_AG_ECS_20181109.xml" or download it from:<br/>
https://s3.amazonaws.com/com-robotiq-website-prod-assets/website-assets/support_documents/document/Robotiq_AG_ECS_20181109.xml <br/>
3- Add the gripper's controller  "NIC 50-RE/ECS" to the KUKA Extension BUS (SYS-44) <br/>
4- Follow the steps in the images in folder /WorkVisualConfiguration to MAP your Inputs and outputs. If you don't know how, refer to KUKA's Manual Chapter 11 "Bus configuration"  for Configuration and I/O mapping in WorkVisual. It's explained in detail.<br/>
5- Export your config to Sunrise Workbench and restart the robot.<br/>
6- Copy the files in folder "/SunriseWorkBenchFiles" to your sunrise project as follows:<br/>
  6.1. Don't need to copy "RobotiqGripperIOGroup" as it's automatically generated (use mine just to compare)<br/>
  6.2. Copy the Java Class file "RobotiqGripper2F85" that contains the gripper's manipulation functions.<br/>
  6.3. Copy GripperInit to use is as a simple program to test your gripper<br/>
  6.4. Copy GripperDemo to see en example of the essential functions to control the gripper<br/>
  6.5. Copy "RoboticsAPI.data.xml" content to the end of your original file "RoboticsAPI.data.xml". It contains variables that the SmartPad UserKey program uses.<br/>
  6.6. Copy "RobotiqGripperKeys.java", to use the smartPad directly to control the gripper. (See: Folder "/SmartPad userKey Screenshots")<br/>
