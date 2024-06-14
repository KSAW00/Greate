package electrolyte.greate.mixin;

import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.kinetics.simpleRelays.ShaftBlock;
import com.simibubi.create.content.kinetics.steamEngine.PoweredShaftBlock;
import com.simibubi.create.content.kinetics.steamEngine.SteamEngineBlock;
import com.simibubi.create.content.kinetics.steamEngine.SteamEngineBlockEntity;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SteamEngineBlock.class)
public abstract class MixinSteamEngineBlock extends FaceAttachedHorizontalDirectionalBlock
        implements SimpleWaterloggedBlock, IWrenchable, IBE<SteamEngineBlockEntity>  {

    public MixinSteamEngineBlock(Properties pProperties) {
        super(pProperties);
    }

    @Inject(method = "isShaftValid", at = @At("HEAD"), remap = false, cancellable = true)
    private static void greate_isShaftValid(BlockState state, BlockState shaft, CallbackInfoReturnable<Boolean> cir) {
        if((shaft.getBlock() instanceof ShaftBlock || shaft.getBlock() instanceof PoweredShaftBlock) &&
                shaft.getValue(ShaftBlock.AXIS) != getConnectedDirection(state).getAxis()) {
            cir.setReturnValue(true);
        }
    }
}
