#include <ESP8266WiFi.h>

//replace with wifi credentials
const char* ssid="";
const char* password = "";

//declare Wifi server listening on 80 port
WiFiServer server(80);

//pin where led are connected
int greenLed = 13; //D6
int redLed = 14;   //D7
int blueLed = 15;  //D8

void setup() {

  //set pin output mode
  pinMode(greenLed,OUTPUT);
  pinMode(blueLed, OUTPUT);
  pinMode(redLed, OUTPUT);

  //at setup, all leds are off
  digitalWrite(greenLed,LOW);
  digitalWrite(blueLed,LOW);
  digitalWrite(redLed,LOW);

  //set monitor to log
  Serial.begin(115200);
  Serial.println();

  //print ssid
  Serial.print("Wifi connecting to ");
  Serial.println( ssid );

  //start connection
  WiFi.begin(ssid,password);

  Serial.println();
  Serial.print("Connecting");

  //waiting to enable connection
  while( WiFi.status() != WL_CONNECTED ){
      delay(500);
      Serial.print(".");        
  }

  //when card is connected to wifi, green led's on
  digitalWrite(greenLed,HIGH);
  digitalWrite(blueLed,LOW);
  digitalWrite(redLed,LOW);
  
  Serial.println();

  Serial.println("Wifi Connected Success!");
  Serial.print("NodeMCU IP Address : ");
  Serial.println(WiFi.localIP() );

  //start server to receive requests
  server.begin();
  Serial.println("servidor inicializado");

}

void loop() {
  WiFiClient client = server.available();
  
  if(!client){
    return;
  }

  Serial.println("Nuevo cliente");
  
  //waiting while client isn't ready
  while(!client.available()){
    delay(2);
  }

  //get request
  String request = client.readStringUntil('\r');
  
  Serial.println(request);

  client.flush();

  //get color's led values from the request
  int redValue = (request.substring(request.indexOf('R')+2, request.indexOf('R')+5)).toInt();
  digitalWrite(redLed,redValue);
  int greenValue = (request.substring(request.indexOf('V')+2, request.indexOf('V')+5)).toInt();
  digitalWrite(greenLed,greenValue);
  int blueValue = (request.substring(request.indexOf('B')+2, request.indexOf('B')+5)).toInt();
  digitalWrite(blueLed,blueValue);
  delay(2);

  //get status from request (ON / OFF)
  if(request.indexOf('/LED=ON') != -1){
    digitalWrite(greenLed , HIGH);
    digitalWrite(blueLed , HIGH);
    digitalWrite(redLed , HIGH);
    digitalWrite(greenLed , greenValue);
    digitalWrite(blueLed , blueValue);
    digitalWrite(redLed , redValue);
  }
  if(request.indexOf('/LED=OF') != -1){
    digitalWrite(greenLed , LOW);
    digitalWrite(blueLed , LOW);
    digitalWrite(redLed , LOW);
  }

  client.println("HTTP/1.1 200 OK");
  
}
