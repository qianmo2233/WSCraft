package cc.qianmo.wscraft.WebSocket;

import org.java_websocket.WebSocket;

import java.util.*;

public class ConnPool {
    private static final Map<WebSocket, String> wsUserMap = new HashMap<WebSocket, String>();
    public static String getUserByWs(WebSocket conn) {
        return wsUserMap.get(conn);
    }

    public static WebSocket getWsByUser(String ID) {
        Set<WebSocket> keySet = wsUserMap.keySet();
        synchronized (keySet) {
            for (WebSocket conn : keySet) {
                String user = wsUserMap.get(conn);
                if (user.equals(ID)) {
                    return conn;
                }
            }
        }
        return null;
    }

    public static void addUser(String ID, WebSocket conn) {
        wsUserMap.put(conn, ID);
    }

    public static Collection<String> getOnlineUser() {
        List<String> setUsers = new ArrayList<String>();
        Collection<String> setUser = wsUserMap.values();
        for (String u : setUser) {
            setUsers.add(u);
        }
        return setUsers;
    }

    public static boolean removeUser(WebSocket conn) {
        if (wsUserMap.containsKey(conn)) {
            wsUserMap.remove(conn);
            return true;
        }
        return false;
    }

    public static void sendMessageToUser(WebSocket conn, String message) {
        if (null != conn && null != wsUserMap.get(conn)) {
            conn.send(message);
        }
    }

    public static void sendMessageToAll(String message) {
        Set<WebSocket> keySet = wsUserMap.keySet();
        synchronized (keySet) {
            for (WebSocket conn : keySet) {
                String user = wsUserMap.get(conn);
                if (user != null) {
                    conn.send(message);
                }
            }
        }
    }
}
