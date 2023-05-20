package com.example.bloodchaiyo.Util;

import com.google.gson.JsonObject;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.BasicResponseHandlerHC4;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


/**
 *
 */
public class FCMHelper {

    /**
     * Instance
     **/
    private static FCMHelper instance = null;

    /**
     * Google URL to use firebase cloud messenging
     */
    private static final String URL_SEND = "https://fcm.googleapis.com/fcm/send";

    /**
     * STATIC TYPES
     */

    public static final String TYPE_TO = "to";  // Use for single devices, device groups and topics
    public static final String TYPE_CONDITION = "condition"; // Use for Conditions

    /**
     * Your SECRET server key
     */
    private static final String FCM_SERVER_KEY = "AIzaSyDZmh69r2ifbKnm5EGM4zT6rPj3_DrXKbU";

    public static FCMHelper getInstance() {
        if (instance == null) instance = new FCMHelper();
        return instance;
    }

    private FCMHelper() {}

    /**
     * Send notification
     * @param type
     * @param typeParameter
     * @param notificationObject
     * @return
     * @throws IOException
     */
    public String sendNotification(String type, String typeParameter, JsonObject notificationObject) throws IOException {
        return sendNotifictaionAndData(type, typeParameter, notificationObject, null);
    }

    /**
     * Send data
     * @param type
     * @param typeParameter
     * @param dataObject
     * @return
     * @throws IOException
     */
    public String sendData(String type, String typeParameter, JsonObject dataObject) throws IOException {
        return sendNotifictaionAndData(type, typeParameter, null, dataObject);
    }

    /**
     * Send notification and data
     * @param type
     * @param typeParameter
     * @param notificationObject
     * @param dataObject
     * @return
     * @throws IOException
     */
    public String sendNotifictaionAndData(String type, String typeParameter, JsonObject notificationObject, JsonObject dataObject) throws IOException {
        String result = null;
        if (type.equals(TYPE_TO) || type.equals(TYPE_CONDITION)) {
            JsonObject sendObject = new JsonObject();
            sendObject.addProperty(type, typeParameter);
            result = sendFcmMessage(sendObject, notificationObject, dataObject);
        }
        return result;
    }

    /**
     * Send data to a topic
     * @param topic
     * @param dataObject
     * @return
     * @throws IOException
     */
    public String sendTopicData(String topic, JsonObject dataObject) throws IOException{
        return sendData(TYPE_TO, "/topics/" + topic, dataObject);
    }

    /**
     * Send notification to a topic
     * @param topic
     * @param notificationObject
     * @return
     * @throws IOException
     */
    public String sendTopicNotification(String topic, JsonObject notificationObject) throws IOException{
        return sendNotification(TYPE_TO, "/topics/" + topic, notificationObject);
    }

    /**
     * Send notification and data to a topic
     * @param topic
     * @param notificationObject
     * @param dataObject
     * @return
     * @throws IOException
     */

    public String sendTopicNotificationAndData(String topic, JsonObject notificationObject, JsonObject dataObject) throws IOException{
        return sendNotifictaionAndData(TYPE_TO, "/topics/" + topic, notificationObject, dataObject);
    }

    /**
     * Send a Firebase Cloud Message
     * @param sendObject - Contains to or condition
     * @param notificationObject - Notification Data
     * @param dataObject - Data
     * @return
     * @throws IOException
     */
    private String sendFcmMessage(JsonObject sendObject, JsonObject notificationObject, JsonObject dataObject) throws IOException {
        URL url = new URL(URL_SEND);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // Set headers
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "key=" + FCM_SERVER_KEY);
        conn.setDoOutput(true);

        // Add JSON payload
        if (notificationObject != null) sendObject.add("notification", notificationObject);
        if (dataObject != null) sendObject.add("data", dataObject);

        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(sendObject.toString());
        wr.flush();

        // Read response
        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
        String output;
        StringBuilder response = new StringBuilder();
        while ((output = br.readLine()) != null) {
            response.append(output);
        }

        conn.disconnect();

        return response.toString();
    }


}