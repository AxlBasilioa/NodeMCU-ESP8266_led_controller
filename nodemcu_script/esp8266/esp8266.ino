#include <ESP8266WiFi.h>


const char* ssid="abcd";
const char* password = "abcd1234";

WiFiServer server(80);

int ledPin = 13;

void setup() {
  
  pinMode(ledPin,OUTPUT);
  digitalWrite(ledPin,LOW);

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

  digitalWrite(ledPin , HIGH);
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
  
  //Serial.println(request);

  Serial.print("RED: ");
  Serial.println(request.substring(request.indexOf('R')+2, request.indexOf('R')+5));

  Serial.print("GREEN: ");
  Serial.println(request.substring(request.indexOf('V')+2, request.indexOf('V')+5));

  Serial.print("BLUE: ");
  Serial.println(request.substring(request.indexOf('B')+2, request.indexOf('B')+5));

  
  client.flush();

  
  if(request.indexOf('/LED=ON') != -1){
    digitalWrite(ledPin , HIGH);
  }
  if(request.indexOf('/LED=OF') != -1){
    digitalWrite(ledPin , LOW);
  }

  client.println("HTTP/1.1 200 OK");
  
}
