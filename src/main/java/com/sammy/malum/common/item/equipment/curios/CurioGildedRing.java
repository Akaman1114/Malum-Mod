package com.sammy.malum.common.item.equipment.curios;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sammy.malum.MalumMod;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

import net.minecraft.world.item.Item.Properties;

public class CurioGildedRing extends MalumCurioItem
{
    private static final UUID ATTRIBUTE_UUID = UUID.fromString("f792e379-4dce-4387-bd3a-099cd49b15f4");
    public CurioGildedRing(Properties builder)
    {
        super(builder);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(String identifier, ItemStack stack)
    {
        Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
        map.put(Attributes.ARMOR, new AttributeModifier(ATTRIBUTE_UUID, "Curio armor boost", 2f, AttributeModifier.Operation.ADDITION));
        return map;
    }

    @Override
    public boolean isGilded()
    {
        return true;
    }
}