package electrolyte.greate.content.processing;

import com.simibubi.create.content.kinetics.belt.BeltBlock;
import com.simibubi.create.content.kinetics.belt.BeltSlope;
import com.simibubi.create.content.processing.AssemblyOperatorBlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class TieredAssemblyOperatorBlockItem extends AssemblyOperatorBlockItem {
    public TieredAssemblyOperatorBlockItem(Block block, Properties builder) {
        super(block, builder);
    }

    @Override
    protected boolean operatesOn(BlockState placedOnState) {
        if(placedOnState.getBlock() instanceof BeltBlock) return placedOnState.getValue(BeltBlock.SLOPE) == BeltSlope.HORIZONTAL;
        return super.operatesOn(placedOnState);
    }
}
