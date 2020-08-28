package cc.qianmo.wscraft;

import cc.qianmo.wscraft.WebSocket.connPool;
import net.sf.json.JSONObject;
import org.java_websocket.WebSocket;

import java.util.Collection;

public class API {
    public static void sendMsgToOne(String ID, String msg) {
        try {
            JSONObject jsonObject = JSONObject.fromObject(msg);
            WebSocket conn = connPool.getWsByUser(ID);
            connPool.sendMessageToUser(conn, msg);
        } catch (Exception e) {
            Main.getMain().getLogger().warning("数据发送失败，仅支持发送JSON数据");
        }
    }
    public static void sendMsgToAll(String msg) {
        try {
            JSONObject jsonObject = JSONObject.fromObject(msg);
            connPool.sendMessageToAll(msg);
        } catch (Exception e) {
            Main.getMain().getLogger().warning("数据发送失败，仅支持发送JSON数据");
        }
    }
    public static Collection<String> getAllOnline() {
        return connPool.getOnlineUser();
    }
}
