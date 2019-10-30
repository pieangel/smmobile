package signalmaster.com.smmobile.network;


import android.os.AsyncTask;
import android.util.Log;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketExtension;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketFrame;

import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import signalmaster.com.smmobile.MainActivity;
import signalmaster.com.smmobile.global.SmGlobal;

public class SmSocketHandler
{
    private static volatile SmSocketHandler sSoleInstance;

    //private constructor.
    private SmSocketHandler() {

        //Prevent form the reflection api.
        if (sSoleInstance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static SmSocketHandler getInstance() {
        if (sSoleInstance == null) { //if there is no instance available... create new one
            synchronized (SmSocketHandler.class) {
                if (sSoleInstance == null) sSoleInstance = new SmSocketHandler();
            }
        }

        return sSoleInstance;
    }

    //Make singleton from serialize and deserialize operation.
    protected SmSocketHandler readResolve() {
        return getInstance();
    }

    private final static String LOGTAG = "SocketHandler";
    private int TIMEOUT = 5000;
    private MainActivity main;
    private SocketConnector socketConnector;

    /*
    public SmSocketHandler(MainActivity main) {
        this.main = main;
    }
    */
    public void connect() {
        if (socketConnector == null) {
            socketConnector = new SocketConnector();
            socketConnector.execute();
        }
    }

    public void disconnect() {
        socketConnector.ws.disconnect();
    }

    public void sendMessage(String message) {
        if (socketConnector.ws.isOpen()) {
            socketConnector.ws.sendText(message);
        }
    }

    private class SocketConnector extends AsyncTask<Void, Void, Void> {

        WebSocket ws;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                //SSLContext context = NaiveSSLContext.getInstance("TLS");
                WebSocketFactory wsf = new WebSocketFactory();
                wsf.setConnectionTimeout(TIMEOUT);
                ws = wsf.createSocket("ws://signalmaster.iptime.org:9991");
                //ws = wsf.createSocket("ws://angelpie.tplinkdns.com:9991");
                ws.addListener(new WSListener());
                ws.addExtension(WebSocketExtension.parse(WebSocketExtension.PERMESSAGE_DEFLATE));
                ws.connect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private class WSListener extends WebSocketAdapter {

        @Override
        public void onConnected(WebSocket websocket, Map<String, List<String>> headers) {
            try {
                super.onConnected ( websocket, headers );
                //bSend.setEnabled ( true );
                SmProtocolManager.getInstance().setAppState(SmGlobal.SmAppState.SocketConnected);
                Log.e ( LOGTAG, "onConnected" );
            }
            catch (Exception e) {
                Log.e ( LOGTAG, "onConnected" );
            }
        }

        @Override
        public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) {

            try {
                super.onDisconnected ( websocket, serverCloseFrame, clientCloseFrame, closedByServer );
                //bSend.setEnabled ( true );
                Log.e ( LOGTAG, "onDisconnected" );
            }
            catch (Exception e) {
                Log.e ( LOGTAG, "onDisconnected" );
            }
        }

        @Override
        public void onTextMessage(WebSocket websocket, String text) {

            try {
                super.onTextMessage ( websocket, text );
                //bSend.setEnabled ( true );
                SmProtocolManager ptMgr = SmProtocolManager.getInstance();
                ptMgr.OnMessage(text);
                //Log.e ( LOGTAG, "onTextMessage" );
            }
            catch (Exception e) {
                //Log.e ( LOGTAG, "onTextMessage" );
                String mess = e.getMessage();
            }
        }

        @Override
        public void onError(WebSocket websocket, WebSocketException cause) {
            try {
                super.onError ( websocket, cause );
                //bSend.setEnabled ( true );
                Log.e ( LOGTAG, "onError" );
            }
            catch (Exception e) {
                Log.e ( LOGTAG, "onError" );
            }

        }

        @Override
        public void onSendError(WebSocket websocket, WebSocketException cause, WebSocketFrame frame) {
            try {
                super.onSendError ( websocket, cause, frame );
                //bSend.setEnabled ( true );
                Log.e ( LOGTAG, "onSendError" );
            }
            catch (Exception e) {
                Log.e ( LOGTAG, "onSendError" );
            }
        }
    }
}
