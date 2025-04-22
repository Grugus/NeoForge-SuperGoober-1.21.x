package net.schnozz.supergoober.attachment;

import com.mojang.serialization.Codec;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.attachment.IAttachmentSerializer;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.schnozz.supergoober.GooberMod;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.UUID;
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


    public static final Supplier<AttachmentType<UUID>> UNDER_CONTROL = ATTACHMENT_TYPES.register(
            "under_control", () -> AttachmentType.builder(() -> UUID.randomUUID()).serialize(UUIDUtil.CODEC).build()
    );



    public static final Supplier<AttachmentType<List<UUID>>> COMMAND_LIST = ATTACHMENT_TYPES.register(
            "command_list", () -> AttachmentType.<List<UUID>>builder(() -> new ArrayList<UUID>())
                    .serialize(Codec.list(UUIDUtil.CODEC))
                    .copyOnDeath()
                    .build()
    );

    public static final Supplier<AttachmentType<Stack<CompoundTag>>> COMMAND_STACK = ATTACHMENT_TYPES.register(
            "command_stack", () -> AttachmentType.<Stack<CompoundTag>>builder(Stack::new)
                    .serialize(Codec.list(CompoundTag.CODEC).xmap(
                            tags -> {
                                Stack<CompoundTag> stack = new Stack<>();
                                stack.addAll(tags);
                                return stack;
                            },
                            stack -> new ArrayList<>(stack)
                    ))
                    .copyOnDeath()
                    .build()
    );



}
