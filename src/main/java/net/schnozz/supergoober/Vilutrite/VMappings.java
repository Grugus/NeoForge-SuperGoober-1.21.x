package net.schnozz.supergoober.Vilutrite;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.util.Lazy;
import net.schnozz.supergoober.GooberMod;
import org.lwjgl.glfw.GLFW;


@EventBusSubscriber(modid = GooberMod.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class VMappings {
    // Key mapping is lazily initialized so it doesn't exist until it is registered
    public static final Lazy<KeyMapping> GRAB_MAPPING = Lazy.of(() -> new KeyMapping(
            "key.goobermod.grab", // Will be localized using this translation key
            InputConstants.Type.KEYSYM, // Default mapping is on the keyboard
            GLFW.GLFW_KEY_G, // Default key is P
            "key.categories.misc" // Mapping will be in the misc category
    ) /*...*/);

    // Event is on the mod event bus only on the physical client
    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        event.register(GRAB_MAPPING.get());
    }
}
