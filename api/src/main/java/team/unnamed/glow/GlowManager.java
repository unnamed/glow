package team.unnamed.glow;

import org.bukkit.Color;

import java.util.HashMap;
import java.util.Map;

public class GlowManager {

    private final Map<Integer, Map<Integer, Color>> glowingEntities = new HashMap<>();

    public void addGlowingEntity(int entityId, int receiverId, Color color) {
        glowingEntities.compute(entityId, (id, map) -> {
            if (map == null) {
                map = new HashMap<>();
            }
            map.put(receiverId, color);
            return map;
        });
    }

    public void removeGlowingEntity(int entityId, int receiverId) {
        Map<Integer, Color> map = glowingEntities.get(entityId);
        if (map == null) {
            return;
        }
        map.remove(receiverId);
        if (map.isEmpty()) {
            glowingEntities.remove(entityId);
        }
    }

    public boolean isGlowing(int entityId, int receiverId) {
        Map<Integer, Color> map = glowingEntities.get(entityId);
        return map != null && map.containsKey(receiverId);
    }

}
