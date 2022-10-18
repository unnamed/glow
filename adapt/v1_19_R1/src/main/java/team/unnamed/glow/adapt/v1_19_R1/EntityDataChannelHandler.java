package team.unnamed.glow.adapt.v1_19_R1;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.syncher.SynchedEntityData;
import team.unnamed.glow.GlowManager;

import java.lang.reflect.Field;
import java.util.List;

public class EntityDataChannelHandler extends ChannelDuplexHandler {
    private static final Field ENTITY_ID_FIELD;

    static {
        Field entityIdField = null;

        for (Field field : ClientboundSetEntityDataPacket.class.getDeclaredFields()) {
            if (field.getType() == int.class) {
                field.setAccessible(true);
                entityIdField = field;
                break;
            }
        }

        if (entityIdField == null) {
            throw new ExceptionInInitializerError("Unable to find the entity id" +
                    " field in the " + ClientboundSetEntityDataPacket.class + " class");
        }

        ENTITY_ID_FIELD = entityIdField;
        ENTITY_ID_FIELD.setAccessible(true);
    }

    private final int playerEntityId;
    private final GlowManager glowManager;

    public EntityDataChannelHandler(int playerEntityId, GlowManager glowManager) {
        this.playerEntityId = playerEntityId;
        this.glowManager = glowManager;
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (!(msg instanceof ClientboundSetEntityDataPacket entityDataPacket)) {
            super.write(ctx, msg, promise);
            return;
        }

        int entityId = entityDataPacket.getId();

        if (entityId < 0) { // custom entity data packet
            ENTITY_ID_FIELD.set(entityDataPacket, -entityId); // normalize id
            super.write(ctx, msg, promise);
            return;
        }

        List<SynchedEntityData.DataItem<?>> items = entityDataPacket.getUnpackedData();

        if (items == null) {
            super.write(ctx, msg, promise);
            return;
        }

        SynchedEntityData.DataItem<?> byteDataItem = items.get(Constants.BYTE_ENTITY_ACCESSOR_ID);

        if (byteDataItem == null) {
            super.write(ctx, msg, promise);
            return;
        }

        // check if the metadata is the glow metadata
        if (byteDataItem.getAccessor().getId() != Constants.BYTE_ENTITY_ACCESSOR_ID) {
            super.write(ctx, msg, promise);
            return;
        }

        @SuppressWarnings("unchecked") // we know that the value is a byte
        SynchedEntityData.DataItem<Byte> castedByteDataItem = (SynchedEntityData.DataItem<Byte>) byteDataItem;

        byte currentValue = castedByteDataItem.getValue();
        boolean internalGlowFlagValue = (currentValue & (1 << 6)) == (1 << 6);
        boolean isGlowing = glowManager.isGlowing(entityId, playerEntityId);

        if (internalGlowFlagValue == isGlowing) { // no changes
            super.write(ctx, msg, promise);
            return;
        }

        byte newValue = (byte) (isGlowing ? currentValue | (1 << 6) : currentValue & ~(1 << 6));
        castedByteDataItem.setValue(newValue);
    }

}
