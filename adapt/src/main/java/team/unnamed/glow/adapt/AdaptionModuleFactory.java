package team.unnamed.glow.adapt;

import org.bukkit.Bukkit;
import team.unnamed.glow.GlowHandler;

public class AdaptionModuleFactory {

    private AdaptionModuleFactory() {
        throw new UnsupportedOperationException();
    }

    public static AdaptionModule create() {
        String serverVersion = Bukkit.getServer()
                .getClass().getPackage()
                .getName().split("\\.")[3]
                .substring(1);

        String className = GlowHandler.class.getPackage().getName()
                + ".adapt.v" + serverVersion + ".AdaptionModuleImpl";

        try {
            Class<?> clazz = Class.forName(className);
            Object module = clazz.getDeclaredConstructor().newInstance();
            if (!(module instanceof AdaptionModule)) {
                throw new IllegalStateException("Invalid adaption module: '"
                        + className + "'. It doesn't implement " + AdaptionModule.class);
            }
            return (AdaptionModule) module;
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Your server version isn't supported to unnamed/glow");
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Failed to instantiate adaption module", e);
        }
    }
}
