package cc.qianmo.wscraft.WebSocket;

import cc.qianmo.wscraft.Main;
import cc.qianmo.wscraft.WSCraft;
import net.sf.json.JSONObject;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.Random;

public class Server extends WebSocketServer {
    WSCraft wsCraft = new WSCraft();
    public Server(int port) {
        super(new InetSocketAddress(port));
    }
    public Server(InetSocketAddress address) {
        super(address);
    }

    @Override
    public final void onOpen(WebSocket conn, ClientHandshake handshake) {
        String ID = getRandomString();
        userJoin(conn, ID);
        Main.getMain().getLogger().info("有新连接加入,ID:" + ID);
        wsCraft.onConnect(ID);
    }

    @Override
    public final void onClose(WebSocket conn, int code, String reason, boolean remote) {
        String ID = connPool.getUserByWs(conn);
        userLeave(conn);
        Main.getMain().getLogger().info("ID : " + ID + "连接断开，原因" + reason);
        wsCraft.onDisconnect(ID, reason);
    }

    @Override
    public final void onMessage(WebSocket conn, String message) {
        try {
            JSONObject jsonObject = JSONObject.fromObject(message);
            String ID = connPool.getUserByWs(conn);
            wsCraft.onMessage(ID, message);
        } catch (Exception e) {
            Main.getMain().getLogger().warning("数据错误，本插件仅支持传输JSON字符串");
        }
    }

    @Override
    public final void onError(WebSocket conn, Exception e) {
        String ID = connPool.getUserByWs(conn);
        Main.getMain().getLogger().warning("WebSocket服务出错！" + e);
        wsCraft.onExceptions(ID, e);
        e.printStackTrace();
    }

    private void userLeave(WebSocket conn) {
        connPool.removeUser(conn);
    }

    private void userJoin(WebSocket conn,String ID) {
        connPool.addUser(ID, conn);
    }

    public static String getRandomString(){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<=10;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
