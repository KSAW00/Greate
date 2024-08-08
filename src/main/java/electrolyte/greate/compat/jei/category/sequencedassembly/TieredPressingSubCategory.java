package electrolyte.greate.compat.jei.category.sequencedassembly;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.compat.jei.category.sequencedAssembly.SequencedAssemblySubCategory;
import com.simibubi.create.content.processing.sequenced.SequencedRecipe;
import electrolyte.greate.compat.jei.category.GreateRecipeCategory;
import electrolyte.greate.compat.jei.category.animations.TieredAnimatedMechanicalPress;
import electrolyte.greate.content.kinetics.press.TieredPressingRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;

import static com.simibubi.create.compat.jei.category.CreateRecipeCategory.getRenderedSlot;
import static electrolyte.greate.registry.MechanicalPresses.MECHANICAL_PRESSES;

public class TieredPressingSubCategory extends SequencedAssemblySubCategory {

    public TieredPressingSubCategory() {
        super(25);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, SequencedRecipe<?> recipe, IFocusGroup focuses, int x) {
        ItemStack circuitStack = GreateRecipeCategory.getCircuitStack((TieredPressingRecipe) recipe.getRecipe());
        if(!circuitStack.isEmpty()) {
            builder.addSlot(RecipeIngredientRole.RENDER_ONLY, x + 4, 11)
                    .setBackground(getRenderedSlot(), -1, -1)
                    .addItemStack(circuitStack);
        }
    }

    @Override
    public void draw(SequencedRecipe<?> recipe, GuiGraphics graphics, double mouseX, double mouseY, int index) {
        TieredAnimatedMechanicalPress press = new TieredAnimatedMechanicalPress(MECHANICAL_PRESSES[((TieredPressingRecipe) recipe.getRecipe()).getRecipeTier()].get(), false);
        PoseStack poseStack = graphics.pose();
        press.offset = index;
        poseStack.pushPose();
        poseStack.translate(-5, 50, 0);
        poseStack.scale(0.6f, 0.6f, 0.6f);
        press.draw(graphics, getWidth() / 2, 0);
        poseStack.popPose();
    }
}
