package electrolyte.greate.mixin;

import com.simibubi.create.content.kinetics.crusher.CrushingWheelControllerBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.UUID;

@Mixin(CrushingWheelControllerBlockEntity.class)
public interface MixinCrushingWheelControllerBlockEntityAccessor {

    @Accessor("entityUUID")
    UUID getEntityUUID();
}
