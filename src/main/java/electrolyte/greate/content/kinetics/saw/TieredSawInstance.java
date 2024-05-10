package electrolyte.greate.content.kinetics.saw;

import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.MaterialManager;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;
import electrolyte.greate.content.kinetics.base.TieredShaftInstance;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import static electrolyte.greate.registry.GreatePartialModels.SHAFT_HALF_MODELS;

public class TieredSawInstance extends TieredShaftInstance<TieredSawBlockEntity> {

    public TieredSawInstance(MaterialManager materialManager, TieredSawBlockEntity blockEntity) {
        super(materialManager, blockEntity);
    }

    @Override
    protected Instancer<RotatingData> getModel() {
        if(blockState.getValue(BlockStateProperties.FACING).getAxis().isHorizontal()) {
            BlockState refState = blockState.rotate(blockEntity.getLevel(), blockEntity.getBlockPos(), Rotation.CLOCKWISE_180);
            Direction dir = refState.getValue(BlockStateProperties.FACING);
            int tier = ((TieredSawBlock) blockState.getBlock()).getTier();
            return getRotatingMaterial().getModel(SHAFT_HALF_MODELS[tier], refState, dir);
        } else {
            return getRotatingMaterial().getModel(shaft());
        }
    }
}
