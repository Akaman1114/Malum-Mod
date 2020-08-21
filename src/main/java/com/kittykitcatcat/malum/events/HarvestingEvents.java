package com.kittykitcatcat.malum.events;

import com.kittykitcatcat.malum.MalumHelper;
import com.kittykitcatcat.malum.MalumMod;
import com.kittykitcatcat.malum.init.ModSounds;
import com.kittykitcatcat.malum.items.staves.BasicStave;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

import static com.kittykitcatcat.malum.MalumHelper.playLoopingSound;
import static com.kittykitcatcat.malum.SpiritDataHelper.*;
import static com.kittykitcatcat.malum.capabilities.CapabilityValueGetter.getCachedTarget;
import static com.kittykitcatcat.malum.capabilities.CapabilityValueGetter.setCachedTarget;
import static com.kittykitcatcat.malum.items.staves.BasicStave.findEntity;
import static net.minecraft.util.SoundCategory.PLAYERS;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber()
public class HarvestingEvents
{
    public static SimpleSound sound;
    public static boolean tryCancel(PlayerEntity playerEntity, ItemStack stack)
    {
        if (getCachedTarget(playerEntity) == null) //shut the fuck up
        {
            playerEntity.swingArm(playerEntity.getActiveHand());
            playerEntity.world.playSound(null, playerEntity.getPosition(), ModSounds.spirit_harvest_failure, PLAYERS, 1f, 1);
            MalumHelper.stopPlayingSound(sound);
            playerEntity.getCooldownTracker().setCooldown(stack.getItem(), 10);
            playerEntity.resetActiveHand();
            return true;
        }
        return false;
    }
    public static void success(PlayerEntity playerEntity, ItemStack stack, LivingEntity cachedTarget)
    {
        playerEntity.world.playSound(playerEntity,playerEntity.getPosition(), ModSounds.spirit_harvest_success, PLAYERS,1f,1);
        setHusk(cachedTarget, true);
        harvestSpirit(playerEntity, getSpirit(cachedTarget), 1);
        setCachedTarget(playerEntity, findEntity(playerEntity));
        playerEntity.addStat(Stats.ITEM_USED.get(stack.getItem()));
        playerEntity.getCooldownTracker().setCooldown(stack.getItem(), 40);
    }
    @SubscribeEvent
    public static void updateCachedTarget(PlayerEvent.LivingUpdateEvent event)
    {
        if (event.getEntityLiving() instanceof PlayerEntity)
        {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            if (!player.isSneaking()) //sneaking is reserved for stave effects
            {
                if (!player.isHandActive()) //we dont wanna update the target if the player is harvesting a spirit
                {
                    if (player.getHeldItemOffhand().getItem() instanceof BasicStave || player.getHeldItemMainhand().getItem() instanceof BasicStave) //only if player holds stave
                    {
                        if (player.world.getGameTime() % 4L == 0L) //dont do it too often
                        {
                            LivingEntity entity = findEntity(player);
                            if (entity != null && !entity.equals(getCachedTarget(player)))
                            {
                                setCachedTarget(player, findEntity(player));
                            }
                        }
                    }
                    else
                    {
                        if (getCachedTarget(player) != null) //shut the fuck up
                        {
                            setCachedTarget(player, null);
                        }
                    }
                }
            }
        }
    }
    @SubscribeEvent
    public static void previewCachedTarget(PlayerEvent.LivingUpdateEvent event)
    {
        if (event.getEntityLiving() instanceof PlayerEntity)
        {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            if (player.getHeldItem(Hand.MAIN_HAND).getItem() instanceof BasicStave || player.getHeldItem(Hand.OFF_HAND).getItem() instanceof BasicStave)
            {
                LivingEntity cachedTarget = getCachedTarget(player);
                if (cachedTarget != null) //shut the fuck up
                {
                    cachedTarget.addPotionEffect(new EffectInstance(Effects.GLOWING, 5, 1, true, false));
                }
            }
        }
    }
    
    @SubscribeEvent
    public static void harvestStart(LivingEntityUseItemEvent.Start event)
    {
        if (event.getEntityLiving() instanceof PlayerEntity)
        {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            if (player.getActiveItemStack().getItem() instanceof BasicStave)
            {
                if (tryCancel(player, player.getActiveItemStack()))
                {
                    event.setCanceled(true);
                }
                else
                {
                    sound = new SimpleSound(ModSounds.spirit_harvest_drain, SoundCategory.RECORDS, 1, 1, player.getPosition());
                    Minecraft.getInstance().getSoundHandler().play(sound);
                }
            }
        }
    }
    @SubscribeEvent
    public static void harvestTick(LivingEntityUseItemEvent.Tick event)
    {
        if (event.getEntityLiving() instanceof PlayerEntity)
        {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            if (player.getActiveItemStack().getItem() instanceof BasicStave)
            {
                ItemStack heldItem = player.getActiveItemStack();
                if (tryCancel(player, heldItem))
                {
                    event.setCanceled(true);
                }
                if ((heldItem.getUseDuration() - player.getItemInUseCount()) > 100)
                {
                    success(player, heldItem, getCachedTarget(player));
                    event.setCanceled(true);
                }
            }
        }
    }
    @SubscribeEvent
    public static void stopHarvest(LivingEntityUseItemEvent.Stop event)
    {
        if (event.getEntityLiving() instanceof PlayerEntity)
        {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            if (player.getActiveItemStack().getItem() instanceof BasicStave)
            {
                tryCancel(player,player.getActiveItemStack());
            }
        }
    }
}