# NodeMCU-ESP8266 led controller

Implementation of an Android application to control rgb leds connected to an NodeMCU-ESP8266 board


### Scheme:


![alt text](https://mechatronicsblog.com/wp-content/uploads/2019/03/LED_RGB_sketch.png)


### Libraries: 

- In the arduino ide, go to preferences
- Insert as aditional board manager urls: http://arduino.esp8266.com/stable/package_esp8266com_index.json
- In the board manager, download esp8266 by esp8266 community
- Select as board nodemcu 1.0


### Usage: 

- In nodemcu_script/esp8266/esp8266.ino file, change ssid and password variables by your own wifi credentials
- Load esp8266.ino in your nodemcu card
- On the arduino ide monitor set to 115200 baud, you can see the status of the device. When it shows as "connected", then the IP address assigned to the board will be printed
- The next step consist in install the android app on your device or emulator and set the ip address previously geted
