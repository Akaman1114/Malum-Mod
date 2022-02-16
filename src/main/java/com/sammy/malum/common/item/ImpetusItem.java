package com.sammy.malum.common.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class ImpetusItem extends Item {
    public Supplier<Item> cracked;

    public ImpetusItem(Properties properties) {
        super(properties);
    }

    public ImpetusItem setCrackedVariant(Supplier<Item> cracked) {
        this.cracked = cracked;
        return this;
    }

    @Override
    public boolean isEnchantable(ItemStack p_41456_) {
        return false;
    }
}