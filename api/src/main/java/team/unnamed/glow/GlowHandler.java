package team.unnamed.glow;

import org.bukkit.Color;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface GlowHandler {

    void sendGlowing(Player player, Entity entity, @Nullable Color color);

    @Nullable Collection<Object> createGlowingPackets(Entity entity, @Nullable Color color);

}
