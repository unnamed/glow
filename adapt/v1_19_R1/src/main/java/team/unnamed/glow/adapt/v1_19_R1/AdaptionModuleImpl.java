package team.unnamed.glow.adapt.v1_19_R1;

import org.jetbrains.annotations.NotNull;
import team.unnamed.glow.GlowHandler;
import team.unnamed.glow.GlowManager;
import team.unnamed.glow.adapt.AdaptionModule;
import team.unnamed.glow.packet.PacketInjectorHandler;

public class AdaptionModuleImpl implements AdaptionModule {
    @Override
    public @NotNull PacketInjectorHandler createPacketInjector(GlowManager glowManager) {
        return new PacketInjectorHandlerImpl(glowManager);
    }

    @Override
    public @NotNull GlowHandler createGlowHandler(GlowManager glowManager) {
        return new GlowHandlerImpl(glowManager);
    }
}
