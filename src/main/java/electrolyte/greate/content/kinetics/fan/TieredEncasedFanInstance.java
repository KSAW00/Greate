package electrolyte.greate.content.kinetics.fan;

import com.jozufozu.flywheel.api.MaterialManager;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityInstance;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;
import com.simibubi.create.content.kinetics.fan.EncasedFanBlock;
import com.simibubi.create.foundation.render.AllMaterialSpecs;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;

import static electrolyte.greate.registry.GreatePartialModels.FAN_INNER_MODELS;
import static electrolyte.greate.registry.GreatePartialModels.SHAFT_HALF_MODELS;

public class TieredEncasedFanInstance extends KineticBlockEntityInstance<TieredEncasedFanBlockEntity> {

    protected final RotatingData halfShaft;
    protected final RotatingData fanInner;
    private final Direction dir;
    private final Direction opposite;

    public TieredEncasedFanInstance(MaterialManager materialManager, TieredEncasedFanBlockEntity blockEntity) {
        super(materialManager, blockEntity);
        dir = blockState.getValue(EncasedFanBlock.FACING);
        opposite = dir.getOpposite();
        int tier = ((TieredEncasedFanBlock) blockState.getBlock()).getTier();
        halfShaft = getRotatingMaterial().getModel(SHAFT_HALF_MODELS[tier], blockState, opposite).createInstance();
        fanInner = materialManager.defaultCutout().material(AllMaterialSpecs.ROTATING).getModel(FAN_INNER_MODELS[tier], blockState, opposite).createInstance();

        setup(halfShaft);
        setup(fanInner, getFanSpeed());
    }

    private float getFanSpeed() {
        float speed = blockEntity.getSpeed() * 5;
        if(speed > 0) {
            speed = Mth.clamp(speed, 80, 64 * 20);
        }
        if (speed < 0) {
            speed = Mth.clamp(speed, -64 * 20, -80);
        }
        return speed;
    }

    @Override
    public void update() {
        updateRotation(halfShaft);
        updateRotation(fanInner, getFanSpeed());
    }

    @Override
    public void updateLight() {
        relight(pos.relative(opposite), halfShaft);
        relight(pos.relative(dir), fanInner);
    }

    @Override
    protected void remove() {
        halfShaft.delete();
        fanInner.delete();
    }
}
