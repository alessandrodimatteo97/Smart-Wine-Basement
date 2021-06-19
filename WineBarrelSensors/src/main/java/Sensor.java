import org.eclipse.paho.client.mqttv3.MqttMessage;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class Sensor {
    private  String name;
    private float value;
    private MqttMessage mqttMessageSensor;
    private  float min;
    private  float max;
    private float plus;
    private float minus;

    public Sensor(String name, float min, float max, float plus, float minus){
        this.name = name;
        this.value = 0;
        this.max = max;
        this.min = min;
        this.plus = plus;
        this.minus = minus;
        mqttMessageSensor = new MqttMessage();
    }


    public Sensor(){
        mqttMessageSensor = new MqttMessage();
    }

    public void setName(String name){
        this.name = name;
    }

    public void setMax(float max){
        this.max = max;
    }

    public void setMin(float min){
        this.min = min;
    }



    public void setPlus(float plus){
        this.plus = plus;
    }

    public void setMinus(float minus){
        this.minus = minus;
    }



    public void setSensorValue() {

        if (this.value == 0.0) this.value = min + new Random().nextFloat() * (max - min);
        else {
            this.value += minus + new Random().nextFloat() * (plus - (minus));

        }
        if (this.value > max) this.value = max;
        if (this.value < min) this.value = min;


    }

    public float getSensorValue() {
        return value;
    }

    public MqttMessage getMqttMessage() {
        mqttMessageSensor.setPayload(Float.toString(this.value).getBytes(StandardCharsets.UTF_8));
        return mqttMessageSensor;
    }

    public String getName(){
        return name;
    }

    @Override
    public String toString() {
        return
                name + ": " + value ;

    }
}
