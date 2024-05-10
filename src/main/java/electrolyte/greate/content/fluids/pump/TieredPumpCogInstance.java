package electrolyte.greate.content.fluids.pump;

import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.api.instance.DynamicInstance;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;
import electrolyte.greate.content.kinetics.base.TieredSingleRotatingInstance;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import static electrolyte.greate.registry.GreatePartialModels.MECHANICAL_PUMP_COG_MODELS;

public class TieredPumpCogInstance extends TieredSingleRotatingInstance<TieredPumpBlockEntity> implements DynamicInstance {

	public TieredPumpCogInstance(MaterialManager materialManager, TieredPumpBlockEntity blockEntity) {
		super(materialManager, blockEntity);
	}

	@Override
	public void beginFrame() {}

	@Override
	protected Instancer<RotatingData> getModel() {
		BlockState referenceState = blockEntity.getBlockState();
		Direction facing = referenceState.getValue(BlockStateProperties.FACING);
		int tier = ((TieredPumpBlock) blockState.getBlock()).getTier();
		return getRotatingMaterial().getModel(MECHANICAL_PUMP_COG_MODELS[tier], referenceState, facing);
	}
}
