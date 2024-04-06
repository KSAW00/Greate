package electrolyte.greate.mixin;

import com.simibubi.create.content.kinetics.crusher.CrushingWheelControllerBlockEntity;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import electrolyte.greate.content.kinetics.crusher.TieredCrushingWheelControllerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CrushingWheelControllerBlockEntity.class)
public abstract class MixinCrushingWheelControllerBlockEntity extends SmartBlockEntity {

    public MixinCrushingWheelControllerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/foundation/blockEntity/SmartBlockEntity;tick()V", shift = Shift.AFTER), remap = false, cancellable = true)
    private void tick(CallbackInfo ci) {
        if(this.getBlockState().getBlock() instanceof TieredCrushingWheelControllerBlock) ci.cancel();
    }
}
