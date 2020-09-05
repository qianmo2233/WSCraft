package cc.qianmo.wscraft.Event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class onExceptionEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private String Id;
    private Exception e;
    public HandlerList getHandlers() {
        return handlers;
    }
    public onExceptionEvent(String ID, Exception exception) {
        Id = ID;
        e = exception;
    }
    public String getId() {
        return Id;
    }
    public Exception getException() {
        return e;
    }
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
