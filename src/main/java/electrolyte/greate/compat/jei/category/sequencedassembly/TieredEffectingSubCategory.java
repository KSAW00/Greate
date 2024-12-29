package electrolyte.greate.compat.jei.category.sequencedassembly;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.compat.jei.ItemIcon;
import com.simibubi.create.compat.jei.category.sequencedAssembly.SequencedAssemblySubCategory;
import com.simibubi.create.content.processing.sequenced.SequencedRecipe;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;

public class TieredEffectingSubCategory extends SequencedAssemblySubCategory {
    public TieredEffectingSubCategory() {
        super(25);
    }

    @Override
    public void draw(SequencedRecipe<?> recipe, GuiGraphics graphics, double mouseX, double mouseY, int index) {
        int x = 50;
        int y = 20;

        // Create an ItemIcon for the Mechanical Arm ItemStack
        ItemIcon armIcon = new ItemIcon(() -> new ItemStack(AllBlocks.MECHANICAL_ARM.get()));
        armIcon.draw(graphics, x, y);
    }
}
