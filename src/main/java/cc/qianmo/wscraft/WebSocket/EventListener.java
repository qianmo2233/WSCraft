package cc.qianmo.wscraft.WebSocket;

public interface EventListener {
    void onMessage (String ID, String Message);
    void onConnect (String ID);
    void onDisconnect (String ID, String Reason);
    void onExceptions (String ID, Exception e);
}
