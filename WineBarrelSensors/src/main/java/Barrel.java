import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Barrel implements Runnable{
    private List<Sensor> sensors;
    private MqttClient client;
    private String serverUri;
    private String clientId;
    private String room;
    private String type;
    private Thread thread;

    public Barrel(List<Sensor> sensors, String room, String cliendId, String type, String serverUri){
        this.room = room;
        this.type = type;
        this.sensors = new ArrayList<>(sensors);
        this.serverUri = serverUri;
        this.clientId= cliendId;
        this.type = type;
        if (thread == null) {
            thread = new Thread(this, this.clientId);
            thread.start ();
        }
    }
    public Barrel(){

    }

    public void setClientId(String clientId){
        this.clientId = clientId;
    }

    public void setRoom(String room){
        this.room = room;
    }

    public void setServerUri(String serverUri){
        this.serverUri = serverUri;
    }

    public void start(){
        if (thread == null) {
            thread = new Thread(this, this.clientId);
            thread.start ();
        }
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }
    public void setType(String type){
        this.type = type;
    }

    @Override
    public void run() {
        try{
            client = new MqttClient(this.serverUri, clientId);
            client.connect();
            for (int i = 0; i<10; i++) {
                client.publish(room+"/"+clientId+"/Type", new MqttMessage(type.getBytes(UTF_8)));

                for (Sensor sensor: sensors){
                    sensor.setSensorValue();
                    client.publish(room+"/"+clientId+"/"+sensor.getName(), sensor.getMqttMessage());

                }
                System.out.println(this);


                try {
                    Thread.sleep(new Random().nextInt(20)*1000);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }

            }
            client.disconnect();
            client.close();

        }
        catch (MqttException mqttException ){
            mqttException.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return  clientId + '\'' +
                "sensors=" + sensors;
    }
}
