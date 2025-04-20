package net.schnozz.supergoober.attachment;

import com.mojang.serialization.Codec;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.schnozz.supergoober.GooberMod;

import java.util.function.Supplier;

public class ModDataAttachment {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, GooberMod.MODID);

    // Serialization via codec
    public static final Supplier<AttachmentType<Integer>> HEALTH_NEEDED = ATTACHMENT_TYPES.register(
            "needed_health", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).copyOnDeath().build()
    );

    public static final Supplier<AttachmentType<Integer>> POWER_LEVEL = ATTACHMENT_TYPES.register(
            "power_level", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).copyOnDeath().build()
    );



}
