package electrolyte.greate.content.fluids.pump;

import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.world.level.block.state.BlockState;

import static electrolyte.greate.registry.GreatePartialModels.MECHANICAL_PUMP_COG_MODELS;

public class TieredPumpRenderer extends KineticBlockEntityRenderer<TieredPumpBlockEntity> {

	public TieredPumpRenderer(Context context) {
		super(context);
	}

	@Override
	protected SuperByteBuffer getRotatedModel(TieredPumpBlockEntity be, BlockState state) {
		int tier = ((TieredPumpBlock) state.getBlock()).getTier();
		return CachedBufferer.partialFacing(MECHANICAL_PUMP_COG_MODELS[tier], state);
	}
}
