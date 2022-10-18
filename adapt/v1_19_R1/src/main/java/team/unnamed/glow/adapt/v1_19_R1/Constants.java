package team.unnamed.glow.adapt.v1_19_R1;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;

public interface Constants {

    int BYTE_ENTITY_ACCESSOR_ID = 0;
    EntityDataAccessor<Byte> DATA_SHARED_FLAGS_ID = EntityDataSerializers.BYTE.createAccessor(BYTE_ENTITY_ACCESSOR_ID);


}
