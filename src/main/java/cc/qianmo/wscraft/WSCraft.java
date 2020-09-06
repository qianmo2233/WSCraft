package cc.qianmo.wscraft;

import cc.qianmo.wscraft.Event.ConnEvent;
import cc.qianmo.wscraft.Event.DisConnEvent;
import cc.qianmo.wscraft.Event.ExceptionEvent;
import cc.qianmo.wscraft.Event.MsgEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public abstract class WSCraft implements Listener {
    public abstract void onMessage(String ID, String Msg);
    public abstract void onConnect(String ID);
    public abstract void onDisconnect(String ID, String Reason);
    public abstract void onException(String ID, Exception e);

    @EventHandler
    public final void onConnEvent(ConnEvent event) {
        String ID = event.getId();
        onConnect(ID);
    }

    @EventHandler
    public final void onDisConnEvent(DisConnEvent event) {
        String ID = event.getId();
        String Reason = event.getReason();
        onDisconnect(ID, Reason);
    }

    @EventHandler
    public final void onMsgEvent(MsgEvent event) {
        String ID = event.getId();
        String Msg = event.getMsg();
        onMessage(ID, Msg);
    }

    @EventHandler
    public final void onExceptionEvent(ExceptionEvent event) {
        String ID = event.getId();
        Exception e = event.getException();
        onException(ID, e);
    }
}
