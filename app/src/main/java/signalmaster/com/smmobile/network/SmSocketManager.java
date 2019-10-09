package signalmaster.com.smmobile.network;

import android.util.Log;

import java.io.Serializable;
import java.util.HashMap;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class SmSocketManager implements Serializable {

    private static volatile SmSocketManager sSoleInstance;

    //private constructor.
    private SmSocketManager() {

        //Prevent form the reflection api.
        if (sSoleInstance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static SmSocketManager getInstance() {
        if (sSoleInstance == null) { //if there is no instance available... create new one
            synchronized (SmSocketManager.class) {
                if (sSoleInstance == null) sSoleInstance = new SmSocketManager();
            }
        }

        return sSoleInstance;
    }

    //Make singleton from serialize and deserialize operation.
    protected SmSocketManager readResolve() {
        return getInstance();
    }

    private HashMap<String, WebSocket> socketMap = new HashMap<>();
    public WebSocket createWebSocket(String socketName) {
        if (socketMap.size() > 0)
            return null;

        WebSocket ws = null;
        WebSocketFactory factory = new WebSocketFactory().setConnectionTimeout(5000);

        try {
            ws = factory.createSocket("ws://drquant.iptime.org:9991");

            ws.addListener(new WebSocketAdapter() {
                @Override
                public void onTextMessage(WebSocket websocket, String message) throws Exception {
                    Log.d("TAG", "onTextMessage:  -> " + message);
                    SmProtocolManager ptMgr = SmProtocolManager.getInstance();
                    ptMgr.OnMessage(message);
                }
            });

            ws.connectAsynchronously();
        } catch (IOException e) {
            e.printStackTrace();
        }
        socketMap.put(socketName, ws);
        return ws;
    }

    public void closeAllSocket() {
        for (HashMap.Entry<String, WebSocket> entry : socketMap.entrySet()) {
            WebSocket ws = entry.getValue();
            if (ws.isOpen())
                ws.sendClose();
        }
    }

    public void sendMessage(String socketName, String message) {
        WebSocket ws = socketMap.get(socketName);
        if (ws == null)
            return;
        if (ws.isOpen()) {
            ws.sendText(message);
        }
    }
}
