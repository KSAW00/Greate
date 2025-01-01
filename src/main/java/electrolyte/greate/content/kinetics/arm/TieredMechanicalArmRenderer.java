package electrolyte.greate.content.kinetics.arm;

import com.jozufozu.flywheel.backend.Backend;
import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmBlock;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmBlockEntity;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmBlockEntity.Phase;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import com.simibubi.create.foundation.utility.Color;
import com.simibubi.create.foundation.utility.Iterate;

import electrolyte.greate.content.kinetics.mixer.TieredMechanicalMixerBlock;
import electrolyte.greate.registry.GreatePartialModels;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.util.Mth;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class TieredMechanicalArmRenderer extends KineticBlockEntityRenderer<TieredMechanicalArmBlockEntity> {

    public TieredMechanicalArmRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(TieredMechanicalArmBlockEntity be, float pt, PoseStack ms, MultiBufferSource buffer, int light,
                              int overlay) {
        super.renderSafe(be, pt, ms, buffer, light, overlay);

        ItemStack item = be.heldItem;
        boolean hasItem = !item.isEmpty();
        boolean usingFlywheel = Backend.canUseInstancing(be.getLevel());

        if (usingFlywheel && !hasItem)
            return;

        ItemRenderer itemRenderer = Minecraft.getInstance()
                .getItemRenderer();

        BakedModel bakedModel = itemRenderer.getModel(item, be.getLevel(), null, 0);
        boolean isBlockItem = hasItem && (item.getItem() instanceof BlockItem) && bakedModel.isGui3d();

        VertexConsumer builder = buffer.getBuffer(be.goggles ? RenderType.cutout() : RenderType.solid());
        BlockState blockState = be.getBlockState();

        PoseStack msLocal = new PoseStack();
        TransformStack msr = TransformStack.cast(msLocal);

        float baseAngle;
        float lowerArmAngle;
        float upperArmAngle;
        float headAngle;
        int color;
        boolean inverted = blockState.getValue(ArmBlock.CEILING);

        TieredMechanicalArmBlock armBlock = (TieredMechanicalArmBlock) blockState.getBlock();
        int tier = armBlock.getTier();

        boolean rave = be.phase == TieredMechanicalArmBlockEntity.Phase.DANCING && be.getSpeed() != 0;
        if (rave) {
            float renderTick = AnimationTickHolder.getRenderTime(be.getLevel()) + (be.hashCode() % 64);
            baseAngle = (renderTick * 10) % 360;
            lowerArmAngle = Mth.lerp((Mth.sin(renderTick / 4) + 1) / 2, -45, 15);
            upperArmAngle = Mth.lerp((Mth.sin(renderTick / 8) + 1) / 4, -45, 95);
            headAngle = -lowerArmAngle;
            color = Color.rainbowColor(AnimationTickHolder.getTicks() * 100)
                    .getRGB();
        } else {
            baseAngle = be.baseAngle.getValue(pt);
            lowerArmAngle = be.lowerArmAngle.getValue(pt) - 135;
            upperArmAngle = be.upperArmAngle.getValue(pt) - 90;
            headAngle = be.headAngle.getValue(pt);
            color = 0xFFFFFF;
        }

        msr.centre();

        if (inverted)
            msr.rotateX(180);

        if (usingFlywheel)
            doItemTransforms(msr, baseAngle, lowerArmAngle, upperArmAngle, headAngle);
        else
            renderArm(builder, ms, msLocal, msr, blockState, color, baseAngle, lowerArmAngle, upperArmAngle, headAngle,
                    be.goggles, inverted && be.goggles, hasItem, isBlockItem, light, tier);

        if (hasItem) {
            ms.pushPose();
            float itemScale = isBlockItem ? .5f : .625f;
            msr.rotateX(90);
            msLocal.translate(0, isBlockItem ? -9 / 16f : -10 / 16f, 0);
            msLocal.scale(itemScale, itemScale, itemScale);

            ms.last()
                    .pose()
                    .mul(msLocal.last()
                            .pose());

            itemRenderer.render(item, ItemDisplayContext.FIXED, false, ms, buffer, light, overlay, bakedModel);
            ms.popPose();
        }

    }

    private void renderArm(VertexConsumer builder, PoseStack ms, PoseStack msLocal, TransformStack msr,
                           BlockState blockState, int color, float baseAngle, float lowerArmAngle, float upperArmAngle, float headAngle,
                           boolean goggles, boolean inverted, boolean hasItem, boolean isBlockItem, int light, int tier) {
        SuperByteBuffer base = CachedBufferer.partial(GreatePartialModels.ARM_BASE_MODELS[tier], blockState)
                .light(light);
        SuperByteBuffer lowerBody = CachedBufferer.partial(GreatePartialModels.ARM_LOWER_BODY_MODELS[tier], blockState)
                .light(light);
        SuperByteBuffer upperBody = CachedBufferer.partial(GreatePartialModels.ARM_UPPER_BODY_MODELS[tier], blockState)
                .light(light);
        SuperByteBuffer claw = CachedBufferer
                .partial(goggles ? GreatePartialModels.ARM_CLAW_BASE_GOGGLES_MODELS[tier] : GreatePartialModels.ARM_CLAW_BASE_MODELS[tier], blockState)
                .light(light);
        SuperByteBuffer upperClawGrip = CachedBufferer.partial(GreatePartialModels.ARM_CLAW_GRIP_UPPER_MODELS[tier],
                        blockState)
                .light(light);
        SuperByteBuffer lowerClawGrip = CachedBufferer.partial(GreatePartialModels.ARM_CLAW_GRIP_LOWER_MODELS[tier], blockState)
                .light(light);

        transformBase(msr, baseAngle);
        base.transform(msLocal)
                .renderInto(ms, builder);

        transformLowerArm(msr, lowerArmAngle);
        lowerBody.color(color)
                .transform(msLocal)
                .renderInto(ms, builder);

        transformUpperArm(msr, upperArmAngle);
        upperBody.color(color)
                .transform(msLocal)
                .renderInto(ms, builder);

        transformHead(msr, headAngle);

        if (inverted)
            msr.rotateZ(180);

        claw.transform(msLocal)
                .renderInto(ms, builder);

        if (inverted)
            msr.rotateZ(180);

        for (int flip : Iterate.positiveAndNegative) {
            msLocal.pushPose();
            transformClawHalf(msr, hasItem, isBlockItem, flip);
            (flip > 0 ? lowerClawGrip : upperClawGrip).transform(msLocal)
                    .renderInto(ms, builder);
            msLocal.popPose();
        }
    }

    private void doItemTransforms(TransformStack msr, float baseAngle, float lowerArmAngle, float upperArmAngle,
                                  float headAngle) {

        transformBase(msr, baseAngle);
        transformLowerArm(msr, lowerArmAngle);
        transformUpperArm(msr, upperArmAngle);
        transformHead(msr, headAngle);
    }

    public static void transformClawHalf(TransformStack msr, boolean hasItem, boolean isBlockItem, int flip) {
        msr.translate(0, -flip * (hasItem ? isBlockItem ? 3 / 16f : 5 / 64f : 1 / 16f), -6 / 16d);
    }

    public static void transformHead(TransformStack msr, float headAngle) {
        msr.translate(0, 0, -15 / 16d);
        msr.rotateX(headAngle - 45f);
    }

    public static void transformUpperArm(TransformStack msr, float upperArmAngle) {
        msr.translate(0, 0, -14 / 16d);
        msr.rotateX(upperArmAngle - 90);
    }

    public static void transformLowerArm(TransformStack msr, float lowerArmAngle) {
        msr.translate(0, 2 / 16d, 0);
        msr.rotateX(lowerArmAngle + 135);
    }

    public static void transformBase(TransformStack msr, float baseAngle) {
        msr.translate(0, 4 / 16d, 0);
        msr.rotateY(baseAngle);
    }

    @Override
    public boolean shouldRenderOffScreen(TieredMechanicalArmBlockEntity be) {
        return true;
    }

    @Override
    protected SuperByteBuffer getRotatedModel(TieredMechanicalArmBlockEntity be, BlockState state) {
        return CachedBufferer.partial(GreatePartialModels.ARM_COG_MODELS[((TieredMechanicalArmBlock) be.getBlockState().getBlock()).getTier()], state);
    }

}
