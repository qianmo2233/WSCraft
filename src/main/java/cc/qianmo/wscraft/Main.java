package cc.qianmo.wscraft;

import cc.qianmo.wscraft.WebSocket.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.java_websocket.WebSocketImpl;
import org.spigotmc.AsyncCatcher;

public class Main extends JavaPlugin {
    private static Main main;
    Thread asyncThread = new Thread(() -> {
        while(true) {
            try {
                Thread.sleep(5000L);
                if (AsyncCatcher.enabled) {
                    AsyncCatcher.enabled = false;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });
    @Override
    public void onLoad() {
        main = this;
        saveDefaultConfig();
        this.getLogger().info("WSCraft正在启动");
        serverStart();
    }
    @Override
    public void onEnable() {
        this.getLogger().info("正在启动异步线程");
        this.asyncThread.setDaemon(true);
        this.asyncThread.start();
        this.getLogger().info("WSCraft启动成功");
    }
    @Override
    public void onDisable() {
        this.getLogger().info("WSCraft已卸载");
    }
    public static Main getMain() {
        return main;
    }
    private void serverStart() {
        FileConfiguration config = getConfig();
        int port = config.getInt("Port");
        WebSocketImpl.DEBUG = config.getBoolean("DEBUG");
        Server s = new Server(port);
        s.start();
        this.getLogger().info("WebSocket服务监听端口：" + port);
    }

}
