package com.sammy.malum.common.blockentity.altar;

import com.sammy.malum.core.systems.blockentity.SimpleBlockEntityInventory;
import net.minecraft.world.phys.Vec3;

public interface IAltarProvider
{
    public SimpleBlockEntityInventory getInventoryForAltar();
    public Vec3 getItemPosForAltar();
}
