package team.unnamed.glow;

import org.bukkit.plugin.java.JavaPlugin;
import team.unnamed.glow.adapt.AdaptionModule;
import team.unnamed.glow.adapt.AdaptionModuleFactory;
import team.unnamed.glow.listener.PlayerJoinListener;
import team.unnamed.glow.packet.PacketInjectorHandler;

public class GlowPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        AdaptionModule adaptionModule = AdaptionModuleFactory.create();
        GlowManager glowManager = new GlowManager();
        PacketInjectorHandler injectorHandler = adaptionModule.createPacketInjector(glowManager);
        GlowHandler glowHandler = adaptionModule.createGlowHandler(glowManager);

        getCommand("glow").setExecutor(new GlowCommand(glowHandler));
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(injectorHandler), this);
    }

}
