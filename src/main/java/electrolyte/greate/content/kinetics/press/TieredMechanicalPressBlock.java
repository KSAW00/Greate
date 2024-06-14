package electrolyte.greate.content.kinetics.press;

import com.simibubi.create.content.kinetics.press.MechanicalPressBlock;
import com.simibubi.create.content.kinetics.press.MechanicalPressBlockEntity;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredBlock;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredShaftBlock;
import electrolyte.greate.registry.ModBlockEntityTypes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class TieredMechanicalPressBlock extends MechanicalPressBlock implements ITieredBlock, ITieredShaftBlock {

    private int tier;
    private Block shaft;

    public TieredMechanicalPressBlock(Properties properties, Block shaft) {
        super(properties);
        this.shaft = shaft;
    }

    @Override
    public BlockEntityType<? extends MechanicalPressBlockEntity> getBlockEntityType() {
        return ModBlockEntityTypes.TIERED_MECHANICAL_PRESS.get();
    }

    @Override
    public int getTier() {
        return tier;
    }

    @Override
    public void setTier(int tier) {
        this.tier = tier;
    }

    @Override
    public Block getShaft() {
        return this.shaft;
    }
}
