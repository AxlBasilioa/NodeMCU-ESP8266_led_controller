#include <ESP8266WiFi.h>


const char* ssid="";
const char* password = "";

WiFiServer server(80);

int greenLed = 13; //D6
int redLed = 14;   //D7
int blueLed = 15;  //D8

void setup() {
  
  pinMode(greenLed,OUTPUT);
  pinMode(blueLed, OUTPUT);
  pinMode(redLed, OUTPUT);
  
  digitalWrite(greenLed,LOW);
  digitalWrite(blueLed,LOW);
  digitalWrite(redLed,LOW);

  Serial.begin(115200);
  Serial.println();
  Serial.print("Wifi connecting to ");
  Serial.println( ssid );

  WiFi.begin(ssid,password);

  Serial.println();
  Serial.print("Connecting");

  while( WiFi.status() != WL_CONNECTED ){
      delay(500);
      Serial.print(".");        
  }

  digitalWrite(greenLed,HIGH);
  digitalWrite(blueLed,LOW);
  digitalWrite(redLed,LOW);
  
  Serial.println();

  Serial.println("Wifi Connected Success!");
  Serial.print("NodeMCU IP Address : ");
  Serial.println(WiFi.localIP() );

  server.begin();
  Serial.println("servidor inicializado");

}

void loop() {
  WiFiClient client = server.available();
  
  if(!client){
    return;
  }

  Serial.println("Nuevo cliente");
  while(!client.available()){
    delay(2);
  }

  String request = client.readStringUntil('\r');
  
  Serial.println(request);
  
  int redValue = (request.substring(request.indexOf('R')+2, request.indexOf('R')+5)).toInt();
  digitalWrite(redLed,redValue);
  int greenValue = (request.substring(request.indexOf('V')+2, request.indexOf('V')+5)).toInt();
  digitalWrite(greenLed,greenValue);
  int blueValue = (request.substring(request.indexOf('B')+2, request.indexOf('B')+5)).toInt();
  digitalWrite(blueLed,blueValue);
  delay(2);

  client.flush();

  
  if(request.indexOf('/LED=ON') != -1){
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
