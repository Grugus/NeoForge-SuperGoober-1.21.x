package net.schnozz.supergoober.item;

import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.schnozz.supergoober.GooberMod;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.createItems(GooberMod.MODID);

    public static final DeferredItem<Item> NECROROD = (DeferredItem<Item>) ITEMS.register("necrorod",
            () -> new Item(new Item.Properties()));




    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }
}
