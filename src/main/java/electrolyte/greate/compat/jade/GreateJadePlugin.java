package electrolyte.greate.compat.jade;

import electrolyte.greate.compat.jade.provider.BeltBlockComponentProvider;
import electrolyte.greate.content.kinetics.belt.TieredBeltBlock;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class GreateJadePlugin implements IWailaPlugin {

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockIcon(BeltBlockComponentProvider.INSTANCE, TieredBeltBlock.class);
    }
}
