# Smart Wine Basement

The following project has been developed for Software Engineering for the Internet of Things exam.
The aim of this project is to create a system which monitor several properties of wine inside the barrels.

This repository is organized in the following way:
1) **WineBarrelSensors** which is a java based application that emulates the behaviour of sensors inside the barrells and send the values to the broker (Mosquitto).
2) **Jupyter ANN** is a notebook in which is possible to see the ANNs developed to predict the quality of the wine given the sensors' values;
3) **ANNFlaskService** which expose the two neural networks that make predictions about the quality of the wine (developed in python using the microweb framework Flask )
4) **Telegram flows-Nodered** are flows exported in json for nodered usefull to manage a telegram bot;
5) **dashboards-influxdb2** are dashbords usefull to see the wine properties exported in json;
6) **OpenHAB Instance**, IOT gateway to handle the whole system;
7) **Installation-Screenshoots** is a folder that contains usefull screnshoots for installing the system in any pc.
   
<br>
<br>


# Installation Guide of the whole environment:

<br>


## 1. Installation and configuration of InfluxDB:

 1. Install InfluxDB from the site: https://docs.influxdata.com/influxdb/v2.0/install/?t=Docker;
 2. Launch the instance of InfluxDB and go to the following url: http://127.0.0.1:8086
 3. Now because it should be the first time you are using influDB you have to create a user with username and password;
 4. Give a name to the organization (**Important**, OpenHAB will need it);
 5. Create a Bucket with name "sensor" (withouth quotes, even this is **important** for OpenHAB);
 6. now you are inside InfluxDB, so, go to boards (on the left), click  "Create Dashboard" and then click on "Import Dashboard".
 7. Now import the dasboards contained in the folder of this repository dahboards-influxdb2;
 8. Go to Data and then to Token, click on Generate and after that click on "All Access Token" (in this way you are able to connect openhab to influxdb by using only this token, otherwise you can also use the username and password of InfluxDB, **for more details see the screenshoot contained in the folder of this repository Installation_Screenshoots/InfluxDB/InfluxDB-Token.png)**
<br>


### Now we are ready to install OpenHAB and integrate it with InfluDB.


<br>

<br>

## 2. Installation of OpenHAB:

 1. download and unzip openhab-3.0.2.zip from this repository;
 2. By using the terminal go inside the folder  openhab-3.0.2 and run the command <code>./start.sh </code> if you are using MacOS or Ubuntu, otherwise you are using windows so run <code>start.bat</code>
 3. Now openHAB is running, so, go on the browser to the following url: http://127.0.0.1:8080;
 4. Click on the admin section on the bottom left and sign in with username: admin and password: admin. In this way you are logged as admnistrator.
<br>
<br>
### **for more details on OpenHAB go on: https://www.openhab.org/docs/** 
<br>

## 3. Connection of OpenHAB with InfluxDB:


 1. Go to OpenHAB on the page of settings and click on Persistance
 2. Now select  the checkbox "InfluDB persistance layer";
 3. go back to settings and click on "InfluxDB Persistence Service" contained in the section "Other Service";
 4. In this page you have to set the url of InfluxDB, the version 2 of InfluxDB, the token that you created before on influxDB otherwise the username and passeord of influxDB **(for more details see the screenshoot contained in the folder of this repository Installation_Screenshoots/InfluxDB/InfluxDB-Openhab.jpg)**;
   
<br>
By now you should have connected InfluxDB to OpenHAB.
Scrivere delle immagini
<br>
<br>

## 4. Installation of FlaskML:
 1. download the folder ANNFlaskService from this repo.
 2. go inside the directory by using the terminal;
 3. run the command <code>docker build -t flask-ml:latest .</code>
 4. After that run the command <code>docker run -d -p 5000:5000 --name flask-ml flask-ml</code>
<br>

**Important**: in case FlaskML is running on a different machine from that in which is running openHAB you'll have to set the different ip from all the scripts.
In order to do that go on openHAB and click on rules, for each rule you have to click on it, then go on code (on the top right) and now set the different ip in the code.  **(for more details see the screenshoot contained in the folder of this repository Installation_Screenshoots/OpenHAB/openHAB-FlaskML-IP.jpg)**

<br>
<br>


## 5. Mosquitto Broker Installation

1. Download the broker from this site: https://mosquitto.org/download/ and follow what it's written in order to run it;

 <br>

**Important**: In case mosquitto is running on a different machine from openHAB you have to set the ip on the settings of the broker.  **(for more details see the screenshoot contained in the folder of this repository Installation_Screenshoots/OpenHAB/openHAB-Mosquitto-IP.jpg)**.

So go on settings, click on things, click on Mqtt Broker, now set the different ip.
In case the publishers or subscribers to the broker mosquito are running on other machine you need to run the broker with a configuration file and set in this file the var allow_anonymous to True. (more details on: https://mosquitto.org/man/mosquitto-conf-5.html);

<br>
<br>

## 6. OpenHAB Cloud configuration

1. go on OpenHAB, click on settings, then on Misc and then on the button + on the right bottom;
2. Add OpenHAB Cloud Connector;
3. Go on https://myopenhab.org/;
4. Sign up with email, password, uuid and secrets (vedere foto da mettere); 

<br>

**Important**:
**As you can see from the screnshoot contained in the folder Installation-Screenshoots/OpenHAB-CLoud, the uuid is contained as string in the file contained in the directory Openhab/userdata, whereas the string "secret" is contained in the folder OpenHAB.*/userdata/openHABCloud**;

By now you can connect to your instance of openHAB by going to https://myopenhab.org/ and logging in.
Moreover, now you can integrate the OpenHAB API iside the telegram service. it's going to be explained in the following paragraphs.

<br>
<br>

## 7. Running WineBarrelSensors
 1. download the directory WineBarrelSensors from this repo;
 2. By using the terminal go inside the folder WineBarrelSensors/target; 
 3. run the command; <code> java -jar wineBarrel-with-depency.jar </code> 

**Important**:
It's possible to run the jar with your own property file. In order to do that you need to put the file inside the same folder of the jar
and then run in the terminal the following command: <code> java -jar wineryBarrel-with-dependecy.jar filename </code>

<br>
<br>

## 8. Creating a telegram bot:

1. Downlaod telegram;
2. Start the chat with BotFather by typing on the search bar "BotFather";
3. Start the bot by clicking the command /start;
4. Create a bot by typing the command /newbot;
5. Give a name and username to the bot.


**Important**:
**There are several screenshots in the folder Installation_ Screenshoots/Telegram**
Once you have run the following commands BotFather will give us the **Token**.
The **Token** is very important because it will allow us to manage the bot.

<br>

### **More details on: https://core.telegram.org/bots**

#### The command just described are the most important to create a new bot, as you can see in the screenshoot there are other commands that you can run on botfather, but less important.

<br>
<br>

## 9. Installation of Nodered and integration with Telegram Bot

1. Download nodered (https://nodered.org/docs/getting-started/), in docker you can simply typing from the terminal the command:  <code> docker run -it -p 1880:1880 --name mynodered nodered/node-red </code>;
2. Connect to the nodered Service (http://localhost:1880/, if installed in localhost);
3. Download the packet "node-red-contrib-telegrambot"  (**more details in the screenshoot Nodered1.jpg contained in the folder Installation-Screenshoots/Nodered/**);
4. Import the floaws contained in the folder on this repo  ;
5. Once you have imported the flows, click on one of the telegram commands(**more details in the screenshoot Nodered2.jpg contained in the folder Installation-Screenshoots/Nodered/**);
6. Click on the icon close to the name of the bot (**more details in the screenshoot Nodered2.jpg contained in the folder Installation_screen/Nodered/**); 
7. Now you are in the settings of the bot in telegram , so insert the token that botfather gave you;
8. Insert the users' id who can use the bot (**more details in the screenshoot contained in the folder Nodered3.jpg Installation-Screenshoots/Nodered/**);
9. click on each command of http request and click on checkbox "basic authentication", now select "basic a    uthentication" as Type and enter the mail and password of OpenHAB Cloud (repet this work for each http request **more details in the screenshoot Nodered4.jpg contained in the folder Installation-Screenshoots/Nodered/**);

Now if everything is went well you will use the bot of telegram integrated with OpenHAB.

<br>

**More details on: https://flows.nodered.org/node/node-red-contrib-telegrambot**

<br>

## 10. OpenHAB App

1. download OpenHAB for IOS or Android;
2. In the app settings, set the different paramiters;

By using the app you'll see the sitemaps page created in OpenHAB.
**(for more details see the screenshoots contained in the this repo in the folder Installation-Screenshoots/OpenHAB-App)**.
<br>
<br><br>
<br>

### *For any kind of problem do not hesitate to ask me: alessandro.dimatteo1897@gmail.com*