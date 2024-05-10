package electrolyte.greate.content.kinetics.fan;

import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.content.kinetics.fan.EncasedFanBlock;
import com.simibubi.create.content.kinetics.fan.EncasedFanBlockEntity;
import com.simibubi.create.foundation.block.IBE;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredBlock;
import electrolyte.greate.registry.ModBlockEntityTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.List;

public class TieredEncasedFanBlock extends EncasedFanBlock implements IBE<EncasedFanBlockEntity>, ITieredBlock {

    private int tier;
    private PartialModel[] models;

    public TieredEncasedFanBlock(Properties properties, PartialModel... models) {
        super(properties);
        this.models = models;
    }

    @Override
    public BlockEntityType<? extends EncasedFanBlockEntity> getBlockEntityType() {
        return ModBlockEntityTypes.TIERED_FAN.get();
    }

    @Override
    public int getTier() {
        return tier;
    }

    @Override
    public void setTier(int tier) {
        this.tier = tier;
    }

    public PartialModel[] getModels() {
        return models;
    }

    @Override
    public void appendHoverText(ItemStack stack, BlockGetter level, List<Component> tooltip, TooltipFlag flag) {
        ITieredBlock.super.appendHoverText(stack, level, tooltip, flag);
    }
}
