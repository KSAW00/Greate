package electrolyte.greate.compat.jade.provider;

import electrolyte.greate.Greate;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredBlock;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

import static electrolyte.greate.registry.Belts.BELT_CONNECTORS;

public class BeltBlockComponentProvider implements IBlockComponentProvider {

    public static final BeltBlockComponentProvider INSTANCE = new BeltBlockComponentProvider();

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        if(blockAccessor.getBlock() instanceof ITieredBlock tieredBlock) {
            iTooltip.getElementHelper().item(BELT_CONNECTORS[tieredBlock.getTier() / 2].asStack());
        }
    }

    @Override
    public ResourceLocation getUid() {
        return Greate.id("belt_icon");
    }
}
