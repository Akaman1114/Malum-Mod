package com.sammy.malum.core.systems.totems.rites;

import com.sammy.malum.core.init.MalumEffects;
import com.sammy.malum.core.modcontent.MalumRunes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class RiteOfFortitude extends AffectEntitiesRite implements IPoppetBlessing
{
    public RiteOfFortitude(String identifier, boolean isInstant, MalumRunes.MalumRune... runes)
    {
        super(identifier, isInstant, runes);
    }
    
    @Override
    public void effect(LivingEntity entity)
    {
        if (entity instanceof PlayerEntity)
        {
            entity.addPotionEffect(new EffectInstance(MalumEffects.FORTITUDE.get(), 100, 1));
        }
    }
    
    @Override
    public void blessingEffect(PlayerEntity entity)
    {
        entity.addPotionEffect(new EffectInstance(MalumEffects.FORTITUDE.get(), 200, 0));
    }
}
