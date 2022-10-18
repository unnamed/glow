package team.unnamed.glow;

import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GlowCommand implements CommandExecutor {

    private final GlowHandler glowHandler;

    public GlowCommand(GlowHandler glowHandler) {
        this.glowHandler = glowHandler;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command only can be executed from a player!");
            return true;
        }

        Player player = (Player) sender;
        glowHandler.sendGlowing(player, player, Color.AQUA);
        return true;
    }
}
