package cc.qianmo.wscraft.Event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class onDisConnEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private String Id;
    private String reason;
    public HandlerList getHandlers() {
        return handlers;
    }
    public onDisConnEvent(String ID, String Reason) {
        Id = ID;
        reason = Reason;
    }
    public String getId() {
        return Id;
    }
    public String getReason() {
        return reason;
    }
}
