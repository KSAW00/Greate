package electrolyte.greate.content.processing.Sequenced;

import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipeBuilder;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipe;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeBuilder;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public class TieredSequencedAssemblyRecipeBuilder extends SequencedAssemblyRecipeBuilder {

    private int recipeTier;
    private ProcessingOutput transitionalItem;

    public TieredSequencedAssemblyRecipeBuilder(ResourceLocation id) {
        super(id);
    }


    public <T extends TieredProcessingRecipe<?>> TieredSequencedAssemblyRecipeBuilder addTieredStep(
            TieredProcessingRecipeBuilder.TieredProcessingRecipeFactory<T> factory,
            UnaryOperator<TieredProcessingRecipeBuilder<T>> builder) {
        TieredProcessingRecipeBuilder<T> recipeBuilder = new TieredProcessingRecipeBuilder<>(factory, new ResourceLocation("dummy"));

        Item placeholder = transitionalItem.getStack().getItem(); // Use stored transitional item
        return this; // Call superclass addStep
    }

    @Override
    public SequencedAssemblyRecipeBuilder transitionTo(ItemLike item) {
        transitionalItem = new ProcessingOutput(new ItemStack(item), 1); // Store when transitionTo is called
        return super.transitionTo(item); // Still call super to keep original functionality
    }


    public void build(Consumer<FinishedRecipe> consumer, int recipeTier) { // Overload build method
        this.recipeTier = recipeTier;
        consumer.accept(new DataGenResult(build(), recipeConditions));
    }

    public int getRecipeTier() {
        return recipeTier;
    }

    public void setRecipeTier(int recipeTier) {
        this.recipeTier = recipeTier;
    }
}
