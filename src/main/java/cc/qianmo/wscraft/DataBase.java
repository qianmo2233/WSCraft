package cc.qianmo.wscraft;

import cc.qianmo.wscraft.WebSocket.Server;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class DataBase {
    Connection conn;
    String Path;

    public DataBase(String path) throws Exception{
        Path = path;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:" + Path);
    }

    public void setup() throws Exception {
        conn.createStatement().execute("CREATE TABLE IF NOT EXISTS Player(Player varchar(20),Token varchar(20));");
        conn.close();
        Main.getMain().getLogger().info("数据库初始化成功");
    }

    public void crateToken(Player player) throws Exception {
        String Name = player.getName();
        ResultSet rs = conn.createStatement().executeQuery("SELECT count(*) FROM Player WHERE Player = " + "'" + Name + "';");
        int count = 0;
        if (rs.getInt(count) == 0) {
            String Token = Server.getRandomString();
            player.sendMessage(ChatColor.GOLD + "正在为您生成Token");
            conn.createStatement().execute("INSERT INTO Player (Player,Name) VALUES ('" + Name + "','" + Token + "');");
            player.sendMessage(ChatColor.GREEN + "Token生成成功，您的Token为：" + Token);
        }
        conn.close();
    }

    public String getToken(String Name) throws Exception {
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM Player WHERE Player = '" + Name + "'");
        String Token;
        Token = rs.getString("Token");
        return Token;
    }

    public String checkToken(String Token) throws Exception {
        ResultSet rs = conn.createStatement().executeQuery("SELECT count(*) FROM Player WHERE Token = '" + Token + "'");
        int count = 0;
        if (rs.getInt(count) == 0) {
            return null;
        } else {
            ResultSet resultSet = conn.createStatement().executeQuery("SELECT * FROM Player WHERE Token = '" + Token + "'");
            return resultSet.getString("Player");
        }
    }
}
