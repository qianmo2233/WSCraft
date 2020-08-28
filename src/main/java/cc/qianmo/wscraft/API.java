package cc.qianmo.wscraft;

import cc.qianmo.wscraft.WebSocket.connPool;
import org.java_websocket.WebSocket;

import java.util.Collection;

public class API {
    public static void sendMsgToOne(WebSocket conn, String msg) {
        connPool.sendMessageToUser(conn, msg);
    }
    public static void sendMsgToAll(String msg) {
        connPool.sendMessageToAll(msg);
    }
    public static Collection<String> getAllOnline() {
        return connPool.getOnlineUser();
    }
}
