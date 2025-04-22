package net.schnozz.supergoober.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.schnozz.supergoober.GooberMod;
import net.schnozz.supergoober.item.custom.MobHolderItem;
import net.schnozz.supergoober.item.custom.NecromancyRodItem;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.createItems(GooberMod.MODID);

    public static final DeferredHolder<Item, NecromancyRodItem> NECROROD =  ITEMS.register("necrorod", () -> new NecromancyRodItem(new Item.Properties()));

    public static final DeferredHolder<Item, MobHolderItem> MOBHOLDER =  ITEMS.register("mobholder", () -> new MobHolderItem(new Item.Properties()));



    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }
}
