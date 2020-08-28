package cc.qianmo.wscraft;

import cc.qianmo.wscraft.WebSocket.EventListener;

public abstract class WSCraft implements EventListener {
    public abstract void onMsg(String ID, String Msg);
    public abstract void onConn(String ID);
    public abstract void onDisConn(String ID, String Reason);
    public abstract void onException(String ID, Exception e);

    public final void onMessage(String ID, String Message) {
         this.onMsg(ID, Message);
    }

    public final void onConnect(String ID) {
        this.onConn(ID);
    }

    public final void onDisconnect(String ID, String Reason) {
        this.onDisConn(ID, Reason);
    }

    public final void onExceptions(String ID, Exception e) {
        this.onException(ID, e);
    }
}
