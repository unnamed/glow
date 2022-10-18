package team.unnamed.glow.adapt.v1_19_R1;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.Color;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import team.unnamed.glow.GlowHandler;
import team.unnamed.glow.GlowManager;

import java.util.Collection;
import java.util.List;

import static team.unnamed.glow.adapt.v1_19_R1.Constants.DATA_SHARED_FLAGS_ID;

public class GlowHandlerImpl implements GlowHandler {

    private final GlowManager glowManager;

    public GlowHandlerImpl(GlowManager glowManager) {
        this.glowManager = glowManager;
    }

    @Override
    public void sendGlowing(Player player, Entity entity, @Nullable Color color) {
        Collection<Object> packets = createGlowingPackets(entity, color);

        if (packets == null) {
            return;
        }

        ServerPlayer serverPlayer = ((CraftPlayer) player).getHandle();

        for (Object packet : packets) {
            glowManager.addGlowingEntity(entity.getEntityId(), serverPlayer.getId(), color);
            serverPlayer.connection.send((Packet<?>) packet);
        }
    }

    @Override
    public @Nullable Collection<Object> createGlowingPackets(Entity entity, @Nullable Color color) {
        net.minecraft.world.entity.Entity nmsEntity = ((CraftEntity) entity).getHandle();
        int entityId = nmsEntity.getId();
        SynchedEntityData entityData = nmsEntity.getEntityData();
        boolean glowing = color != null;

        byte lastValue = entityData.get(DATA_SHARED_FLAGS_ID);
        byte value = (byte) (glowing ? (lastValue | 1 << 6) : (lastValue & ~(1 << 6)));

        SynchedEntityData.DataItem<Byte> fakeDataItem =
                new SynchedEntityData.DataItem<>(DATA_SHARED_FLAGS_ID, value);

        ClientboundSetEntityDataPacket dataPacket = new ClientboundSetEntityDataPacket(
                /*
                  negative id to indicate that this is a fake packet
                  its handled by the {@link PacketInjectorHandlerImpl)
                 */
                -entityId,
                entityData,
                true
        );

        List<SynchedEntityData.DataItem<?>> fakeItems = dataPacket.getUnpackedData();

        if (fakeItems == null) {
            return null;
        }

        fakeItems.clear();
        fakeItems.add(fakeDataItem);

        return List.of(dataPacket);
    }
}
