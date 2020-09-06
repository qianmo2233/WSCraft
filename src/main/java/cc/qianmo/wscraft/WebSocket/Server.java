package cc.qianmo.wscraft.WebSocket;

import cc.qianmo.wscraft.Event.ConnEvent;
import cc.qianmo.wscraft.Event.DisConnEvent;
import cc.qianmo.wscraft.Event.ExceptionEvent;
import cc.qianmo.wscraft.Event.MsgEvent;
import cc.qianmo.wscraft.Main;
import net.sf.json.JSONObject;
import org.bukkit.Bukkit;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.Random;

public class Server extends WebSocketServer {
    public Server(int port) {
        super(new InetSocketAddress(port));
    }

    @Override
    public final void onOpen(WebSocket conn, ClientHandshake handshake) {
        String ID = getRandomString();
        userJoin(conn, ID);
        Main.getMain().getLogger().info("有新连接加入,ID:" + ID);
        ConnEvent event = new ConnEvent(ID);
        Bukkit.getServer().getPluginManager().callEvent(event);
    }

    @Override
    public final void onClose(WebSocket conn, int code, String reason, boolean remote) {
        String ID = connPool.getUserByWs(conn);
        userLeave(conn);
        Main.getMain().getLogger().info("ID : " + ID + "连接断开，原因" + reason);
        DisConnEvent event = new DisConnEvent(ID, reason);
        Bukkit.getServer().getPluginManager().callEvent(event);
    }

    @Override
    public final void onMessage(WebSocket conn, String message) {
        try {
            JSONObject jsonObject = JSONObject.fromObject(message);
            String ID = connPool.getUserByWs(conn);
            MsgEvent event = new MsgEvent(ID, message);
            Bukkit.getServer().getPluginManager().callEvent(event);
        } catch (Exception e) {
            Main.getMain().getLogger().warning("数据错误，本插件仅支持传输JSON字符串");
        }
    }

    @Override
    public final void onError(WebSocket conn, Exception e) {
        String ID = connPool.getUserByWs(conn);
        Main.getMain().getLogger().warning("WebSocket服务出错！" + e);
        ExceptionEvent event = new ExceptionEvent(ID, e);
        Bukkit.getServer().getPluginManager().callEvent(event);
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