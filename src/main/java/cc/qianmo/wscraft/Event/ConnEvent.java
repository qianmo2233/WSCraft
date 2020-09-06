package cc.qianmo.wscraft.Event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ConnEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private String Id;
    public HandlerList getHandlers() {
        return handlers;
    }
    public ConnEvent(String ID) {
        Id = ID;
    }
    public String getId() {
        return Id;
    }
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
