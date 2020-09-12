package cc.qianmo.wscraft;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Action implements Listener, CommandExecutor {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws Exception {
        Player player = event.getPlayer();
        DataBase dataBase = new DataBase(Main.getMain().getDataFolder().getPath() + "/Player.db");
        dataBase.crateToken(player);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings[0].equalsIgnoreCase("wsc")) {
            if (!(commandSender instanceof Player)) {
                Main.getMain().getLogger().info("WSCraft命令只能玩家执行！");
                return true;
            } else if (strings[1].equalsIgnoreCase("view")) {
                Player player = (Player) commandSender;
                try {
                    DataBase dataBase = new DataBase(Main.getMain().getDataFolder().getPath() + "/Player.db");
                    player.sendMessage(ChatColor.GOLD + "您的Token为：" + dataBase.getToken(player.getName()));
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}
