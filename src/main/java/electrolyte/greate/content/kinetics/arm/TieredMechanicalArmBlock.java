package electrolyte.greate.content.kinetics.arm;

import com.simibubi.create.content.kinetics.mechanicalArm.ArmBlock;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmBlockEntity;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredBlock;
import electrolyte.greate.registry.ModBlockEntityTypes;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TieredMechanicalArmBlock extends ArmBlock implements ITieredBlock {
    private static final Logger log = LoggerFactory.getLogger(TieredMechanicalArmBlock.class);
    private int tier;
    public TieredMechanicalArmBlock(Properties properties) {
        super(properties);
    }

    public BlockEntityType<? extends ArmBlockEntity> getBlockEntityType(){
        return ModBlockEntityTypes.TIERED_MECHANICAL_ARM.get();
    }

    public int getTier() {
        return tier;
    }

    public void setTier(int tier) {
        this.tier = tier;
    }
}
