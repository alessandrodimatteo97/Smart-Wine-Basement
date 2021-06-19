import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args){
        try {
            InputStream inputBarrels;
            if(args.length!=0) {
                String path = "./"+args[0];
                inputBarrels = new FileInputStream(path);
            }
            else {
                inputBarrels = Main.class.getClassLoader().getResourceAsStream("config.properties");

            }

            Properties propsBarrel = new Properties();
            propsBarrel.load(inputBarrels);
            String ip = "tcp://" + propsBarrel.getProperty("broker.ip") + ":" + propsBarrel.getProperty("broker.port");

            System.out.println(ip);

            List<Sensor> redSensors = new ArrayList<>();
            List<Sensor> whiteSensors = new ArrayList<>();

            HashSet<String> sensorNames = new HashSet<>();

            for (Map.Entry<Object, Object> property : propsBarrel.entrySet()) {
                String sensorName = property.getKey().toString();
                sensorName = sensorName.substring(0, sensorName.indexOf("."));

                if(!sensorName.contains("room") && !sensorName.equals("broker") && sensorNames.add(sensorName) ) {
                   Sensor redSensor = new Sensor();
                   Sensor whiteSensor = new Sensor();
                   redSensor.setName(sensorName);
                   redSensor.setMin(Float.parseFloat(propsBarrel.getProperty(sensorName.concat(".redMin"))));
                   redSensor.setMax(Float.parseFloat(propsBarrel.getProperty(sensorName.concat(".redMax"))));
                   redSensor.setMinus(Float.parseFloat(propsBarrel.getProperty(sensorName.concat(".minus"))));
                   redSensor.setPlus(Float.parseFloat(propsBarrel.getProperty(sensorName.concat(".plus"))));

                   whiteSensor.setName(sensorName);
                   whiteSensor.setMin(Float.parseFloat(propsBarrel.getProperty(sensorName.concat(".whiteMin"))));
                   whiteSensor.setMax(Float.parseFloat(propsBarrel.getProperty(sensorName.concat(".whiteMax"))));
                   whiteSensor.setMinus(Float.parseFloat(propsBarrel.getProperty(sensorName.concat(".minus"))));
                   whiteSensor.setPlus(Float.parseFloat(propsBarrel.getProperty(sensorName.concat(".plus"))));

                   redSensors.add(redSensor);
                   whiteSensors.add(whiteSensor);

               }

            }





            for (Map.Entry<Object, Object> property : propsBarrel.entrySet()) {
                String barrel = property.getKey().toString();
                if(barrel.contains("room") ) {
                    String barrelRoom = barrel.substring(0, barrel.indexOf("."));
                    String barrelName = barrel.substring(barrel.indexOf(".")+1);
                    String type = property.getValue().toString();

                    Barrel barrel1 = new Barrel();
                    barrel1.setRoom(barrelRoom);
                    barrel1.setType(type);
                    if (type.equals("red")) barrel1.setSensors(redSensors);
                    else barrel1.setSensors(whiteSensors);
                    barrel1.setServerUri(ip);
                    barrel1.setClientId(barrelName);
                    barrel1.start();

                }

            }




        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}