package com.sammy.malum.common.blockentity.obelisk;

import com.sammy.malum.common.block.ether.EtherBlock;
import com.sammy.malum.common.block.spirit_altar.IAltarAccelerator;
import com.sammy.malum.core.helper.DataHelper;
import com.sammy.malum.core.setup.ParticleRegistry;
import com.sammy.malum.core.setup.block.BlockEntityRegistry;
import com.sammy.malum.core.setup.block.BlockRegistry;
import com.sammy.malum.core.systems.multiblock.MultiBlockStructure;
import com.sammy.malum.core.systems.rendering.RenderUtilities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.awt.*;
import java.util.function.Supplier;

public class RunewoodObeliskBlockEntity extends ObeliskCoreBlockEntity implements IAltarAccelerator {
    public static final AcceleratorType OBELISK = new AcceleratorType(4, "obelisk");
    public static final Supplier<MultiBlockStructure> STRUCTURE = () -> (MultiBlockStructure.of(new MultiBlockStructure.StructurePiece(0, 1, 0, BlockRegistry.RUNEWOOD_OBELISK_COMPONENT.get().defaultBlockState())));

    public RunewoodObeliskBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.RUNEWOOD_OBELISK.get(), STRUCTURE.get(), pos, state);
    }

    @Override
    public IAltarAccelerator.AcceleratorType getAcceleratorType() {
        return OBELISK;
    }

    @Override
    public float getAcceleration() {
        return level.getBlockState(worldPosition.above(2)).getBlock() instanceof EtherBlock ? 1f : 0.75f;
    }

    @Override
    public void addParticles(Color color, float alpha, BlockPos altarPos, Vec3 altarItemPos) {
        Color endColor = new Color(color.getGreen(), color.getBlue(), color.getRed());
        Vec3 startPos = DataHelper.fromBlockPos(worldPosition).add(0.5f, 2.15f, 0.5f);
        RenderUtilities.create(ParticleRegistry.WISP_PARTICLE)
                .setAlpha(alpha, 0f)
                .setLifetime(35)
                .setScale(0.2f + level.random.nextFloat() * 0.1f, 0)
                .randomOffset(0.02f)
                .randomVelocity(0.01f, 0.01f)
                .setColor(color, endColor)
                .setSpin(0.1f + level.random.nextFloat() * 0.2f)
                .randomVelocity(0.0025f, 0.0025f)
                .enableNoClip()
                .repeat(level, startPos.x, startPos.y, startPos.z, 1);

        RenderUtilities.create(ParticleRegistry.SPARKLE_PARTICLE)
                .setAlpha(alpha, 0f)
                .setLifetime(25)
                .setScale(0.5f, 0)
                .randomOffset(0.1, 0.1)
                .randomVelocity(0.02f, 0.02f)
                .setColor(color, endColor)
                .randomVelocity(0.0025f, 0.0025f)
                .enableNoClip()
                .repeat(level, startPos.x, startPos.y, startPos.z, 2);

    }
}