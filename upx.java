#include <ESP32Servo.h>
#include <IRremote.h>

const int servoPin = 18;
const int receiverPin = 2;
const int rele = 12;

Servo servo; // Mantenha como Servo padrão
IRrecv irrecv(receiverPin);

void setup() {
    pinMode(rele, OUTPUT);
    Serial.begin(115200);
    irrecv.enableIRIn();
    Serial.println("Habilita o IR");
    servo.attach(servoPin, 500, 2400);
}

void loop() {
    if (irrecv.decode()) {
        Serial.println("Recebeu valor do botão");
        int value = irrecv.decodedIRData.command; // Obtém o valor do botão
        Serial.println(value, HEX);

        if (value == 0xA2) { // Verifique o valor do botão
            digitalWrite(rele, LOW);
            delay(9000);
            digitalWrite(rele, HIGH);
        }
        irrecv.resume();
    }

    for (int pos = 0; pos <= 180; pos++) {
        servo.write(pos);
        delay(15);
    }
    for (int pos = 180; pos >= 0; pos--) {
        servo.write(pos);
        delay(15);
    }
}
