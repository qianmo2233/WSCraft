package cc.qianmo.wscraft;

import cc.qianmo.wscraft.WebSocket.Server;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.java_websocket.WebSocketImpl;
import org.spigotmc.AsyncCatcher;

public class Main extends JavaPlugin {
    private static Main main;
    @Override
    public void onLoad() {
        main = this;
        saveDefaultConfig();
        this.getLogger().info("WSCraft正在启动");
        WebSocketServer(true);
    }
    @Override
    public void onEnable() {
        this.getLogger().info("正在启用异步线程");
        this.asyncThread.setDaemon(true);
        this.asyncThread.start();
        this.getLogger().info("正在初始化数据库");
        try {
            setup();
        } catch (Exception e) {
            this.getLogger().warning("初始化数据库出错" + e);
        }

        Bukkit.getPluginManager().registerEvents(new Action(), this);
        Bukkit.getPluginCommand("wsc");
        this.getLogger().info("WSCraft启动成功");
    }
    @Override
    public void onDisable() {
        WebSocketServer(false);
        this.getLogger().info("WSCraft已卸载");
    }


    /**
     * 获取插件主类
     * @return 插件主类
     */
    public static Main getMain() {
        return main;
    }

    /**
     * WebSocketServer操作方法
     * @param action 开关WebSocket服务器
     * <pre>{@code false} 停止服务器</pre>
     * <pre>{@code ture} 启动服务器</pre>
     */
    private void WebSocketServer(Boolean action) {
        FileConfiguration config = getConfig();
        int port = config.getInt("Port");
        WebSocketImpl.DEBUG = config.getBoolean("DEBUG");
        Server s = new Server(port);
        if (action) {
            s.start();
            this.getLogger().info("正在启动WebSocket服务器");
            this.getLogger().info("WebSocket服务器监听端口：" + port);
        } else {
            try {
                this.getLogger().info("正在关闭WebSocket服务器");
                s.stop();
            } catch (Exception e) {
                this.getLogger().warning("关闭WebSocket服务器出错" + e);
            }
        }
    }

    /**
     * 启用异步线程
     * @throws Exception
     */
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

    private void setup() throws Exception {
        DataBase dataBase = new DataBase(getDataFolder().getPath() + "/Player.db");
        dataBase.setup();
    }
}
