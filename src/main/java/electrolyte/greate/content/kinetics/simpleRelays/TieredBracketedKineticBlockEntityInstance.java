package electrolyte.greate.content.kinetics.simpleRelays;

import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.simpleRelays.ICogWheel;
import electrolyte.greate.content.kinetics.base.TieredSingleRotatingInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction.AxisDirection;

import static electrolyte.greate.registry.GreatePartialModels.COGWHEEL_SHAFT_MODELS;
import static electrolyte.greate.registry.GreatePartialModels.LARGE_COGWHEEL_SHAFTLESS_MODELS;

public class TieredBracketedKineticBlockEntityInstance extends TieredSingleRotatingInstance<TieredBracketedKineticBlockEntity> {

    protected RotatingData additionalShaft;
    public TieredBracketedKineticBlockEntityInstance(MaterialManager materialManager, TieredBracketedKineticBlockEntity blockEntity) {
        super(materialManager, blockEntity);
    }

    @Override
    public void init() {
        super.init();
        if(!ICogWheel.isLargeCog(blockEntity.getBlockState())) return;

        int tier = ((TieredCogwheelBlock) blockEntity.getBlockState().getBlock()).getTier();
        float speed = blockEntity.getSpeed();
        Axis axis = KineticBlockEntityRenderer.getRotationAxisOf(blockEntity);
        BlockPos pos = blockEntity.getBlockPos();
        float offset = BracketedKineticBlockEntityRenderer.getShaftAngleOffset(axis, pos);
        Direction facing = Direction.fromAxisAndDirection(axis, AxisDirection.POSITIVE);
        Instancer<RotatingData> half = getRotatingMaterial().getModel(LARGE_COGWHEEL_SHAFTLESS_MODELS[tier], blockState, facing, () -> this.rotateToAxis(axis));

        additionalShaft = setup(half.createInstance(), speed);
        additionalShaft.setRotationOffset(offset);
    }

    @Override
    protected Instancer<RotatingData> getModel() {
        if(!ICogWheel.isLargeCog(blockEntity.getBlockState())) return super.getModel();

        int tier = ((TieredCogwheelBlock) blockEntity.getBlockState().getBlock()).getTier();
        Axis axis = KineticBlockEntityRenderer.getRotationAxisOf(blockEntity);
        Direction facing = Direction.fromAxisAndDirection(axis, AxisDirection.POSITIVE);
        return getRotatingMaterial().getModel(COGWHEEL_SHAFT_MODELS[tier], blockState, facing, () -> this.rotateToAxis(axis));
    }

    private PoseStack rotateToAxis(Axis axis) {
        Direction facing = Direction.fromAxisAndDirection(axis, AxisDirection.POSITIVE);
        PoseStack poseStack = new PoseStack();
        TransformStack.cast(poseStack).centre().rotateToFace(facing).multiply(com.mojang.math.Axis.XN.rotationDegrees(-90)).unCentre();
        return poseStack;
    }

    @Override
    public void update() {
        super.update();
        if(additionalShaft != null) {
            updateRotation(additionalShaft);
            additionalShaft.setRotationOffset(BracketedKineticBlockEntityRenderer.getShaftAngleOffset(axis, pos));
        }
    }

    @Override
    public void updateLight() {
        super.updateLight();
        if(additionalShaft != null) {
            relight(pos, additionalShaft);
        }
    }

    @Override
    public void remove() {
        super.remove();
        if(additionalShaft != null) {
            additionalShaft.delete();
        }
    }
}
