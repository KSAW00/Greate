package electrolyte.greate.compat.jei.category.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import electrolyte.greate.content.kinetics.arm.TieredMechanicalArmBlock;
import net.minecraft.client.gui.GuiGraphics;

import static electrolyte.greate.registry.GreatePartialModels.ARM_COG_MODELS;
import static electrolyte.greate.registry.MechanicalArms.MECHANICAL_ARMS;


public class TieredAnimatedMechanicalArm extends AnimatedKinetics {

    private boolean depot;
    private final TieredMechanicalArmBlock block;

    public TieredAnimatedMechanicalArm(TieredMechanicalArmBlock block, boolean basin) {
        this.block = block;
        this.depot = basin;
    }

    @Override
    public void draw(GuiGraphics guiGraphics, int xOffset, int yOffset) {
        PoseStack matrixStack = guiGraphics.pose();
        matrixStack.pushPose();
        matrixStack.translate(xOffset, yOffset, 200);
        matrixStack.mulPose(Axis.XP.rotationDegrees(-15.5f));
        matrixStack.mulPose(Axis.YP.rotationDegrees(22.5f));
        int scale = depot ? 23 : 24;

        blockElement(block.defaultBlockState()).scale(scale).render(guiGraphics);
        blockElement(MECHANICAL_ARMS[block.getTier()].getDefaultState()).scale(scale).render(guiGraphics);

        // Render the animated cog
        blockElement(ARM_COG_MODELS[block.getTier()])
                .rotateBlock(0, getAnimatedCogOffset(), 0)
                .scale(scale)
                .render(guiGraphics);

        if (depot) {
            blockElement(AllBlocks.DEPOT.getDefaultState()).atLocal(0, 1.65, 0).scale(scale).render(guiGraphics);
        }

        matrixStack.popPose();
    }

    private float getAnimatedCogOffset() {
        return (AnimationTickHolder.getRenderTime() % 360);
    }
}