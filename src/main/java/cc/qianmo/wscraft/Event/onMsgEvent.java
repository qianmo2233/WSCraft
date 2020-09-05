package cc.qianmo.wscraft.Event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class onMsgEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private String Id;
    private String Msg;
    public HandlerList getHandlers() {
        return handlers;
    }
    public onMsgEvent(String ID, String Message) {
        Msg = Message;
        Id = ID;
    }
    public String getId() {
        return Id;
    }
    public String getMsg() {
        return Msg;
    }
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
