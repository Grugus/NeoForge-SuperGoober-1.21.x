package net.schnozz.supergoober.lifesteal.lifeevents;


import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.schnozz.supergoober.GooberMod;
import org.checkerframework.checker.units.qual.A;

import static net.schnozz.supergoober.attachment.ModDataAttachment.HEALTH_NEEDED;

@EventBusSubscriber(modid = GooberMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public class LifeEvents {
    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event)
    {
        if(event.getEntity() instanceof Player player && event.getSource().getDirectEntity() instanceof Player p && p.getTags().contains("lifestealer"))
        {
            p.getData(HEALTH_NEEDED);
            p.getAttributes().getInstance(Attributes.MAX_HEALTH).setBaseValue(20 + Math.min((p.getData(HEALTH_NEEDED) + 4), 20));
            p.setData(HEALTH_NEEDED, Math.min((p.getData(HEALTH_NEEDED) + 4), 20)); //sets the tag on the player to the new health addition value
            p.setHealth(50);
        }

        if(event.getEntity() instanceof Player p && event.getSource().getDirectEntity() instanceof Player player && p.getTags().contains("lifestealer"))
        {
            p.getData(HEALTH_NEEDED);
            p.setData(HEALTH_NEEDED, Math.max((p.getData(HEALTH_NEEDED) - 2), -10 ) ); //sets the tag on the player to the new health addition value
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event)
    {
        if(event.getEntity().getTags().contains("lifestealer") && event.getEntity().hasData(HEALTH_NEEDED))
        {
            Player p = event.getEntity();
            p.getData(HEALTH_NEEDED);
            p.getAttributes().getInstance(Attributes.MAX_HEALTH).setBaseValue(20 + p.getData(HEALTH_NEEDED));
            p.setHealth(50);
        }
    }
}
