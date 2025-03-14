package com.sammy.malum.common.block.item_storage;

import com.sammy.malum.common.blockentity.item_storage.PlinthCoreBlockEntity;
import com.sammy.malum.core.setup.block.BlockEntityRegistry;
import com.sammy.malum.core.systems.block.WaterLoggedBlock;
import com.sammy.malum.core.systems.multiblock.IMultiBlockCore;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PlinthCoreBlock extends WaterLoggedBlock<PlinthCoreBlockEntity> implements IMultiBlockCore {
    public static final VoxelShape SHAPE = makeShape();
    public PlinthCoreBlock(Properties properties) {
        super(properties);
        setTile(BlockEntityRegistry.PLINTH);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    public static VoxelShape makeShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.625, 0, 0.625, 1, 0.25, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.5625, 0.1875, 0.5625, 0.875, 1, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.5625, 0.1875, 0.125, 0.875, 1, 0.4375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.625, 0, 0, 1, 0.25, 0.375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0.1875, 0.125, 0.4375, 1, 0.4375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0.1875, 0.5625, 0.4375, 1, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0.625, 0.375, 0.25, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 0.375, 0.25, 0.375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.0625, 0.9375, 0.1875, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.25, 0.1875, 0.25, 0.75, 1, 0.75), BooleanOp.OR);

        return shape;
    }
}