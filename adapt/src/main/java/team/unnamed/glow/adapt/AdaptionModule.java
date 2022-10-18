package team.unnamed.glow.adapt;

import org.jetbrains.annotations.NotNull;
import team.unnamed.glow.GlowHandler;
import team.unnamed.glow.GlowManager;
import team.unnamed.glow.packet.PacketInjectorHandler;

public interface AdaptionModule {

    @NotNull PacketInjectorHandler createPacketInjector(GlowManager glowManager);

    @NotNull GlowHandler createGlowHandler(GlowManager glowManager);

}
