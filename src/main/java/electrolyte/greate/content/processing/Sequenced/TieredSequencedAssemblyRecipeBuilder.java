package electrolyte.greate.content.processing.Sequenced;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipe;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipeBuilder;
import com.simibubi.create.content.processing.sequenced.SequencedRecipe;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipe;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeBuilder;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public class TieredSequencedAssemblyRecipeBuilder extends SequencedAssemblyRecipeBuilder {

    private TieredSequencedAssemblyRecipe recipe;
    private int recipeTier;

    public TieredSequencedAssemblyRecipeBuilder(ResourceLocation id, int recipeTier) {
        super(id);
        this.recipeTier = recipeTier;
        this.recipe = new TieredSequencedAssemblyRecipe(id,recipeTier,AllRecipeTypes.SEQUENCED_ASSEMBLY.getSerializer());
    }

    public <R extends TieredProcessingRecipe<?>> TieredSequencedAssemblyRecipeBuilder addTieredStep(TieredProcessingRecipeBuilder.TieredProcessingRecipeFactory<R> factory,
                                                                                                    UnaryOperator<TieredProcessingRecipeBuilder<R>> builder) {
        TieredProcessingRecipeBuilder<R> recipeBuilder =
                new TieredProcessingRecipeBuilder<>(factory, new ResourceLocation("dummy"));
        Item placeHolder = recipe.transitionalItem.getStack().getItem();
        recipe.getSequence()
                .add(new SequencedRecipe<>(builder.apply(recipeBuilder.require(placeHolder)
                                .output(placeHolder))
                        .build()));
        return this;
    }

    @Override
    public TieredSequencedAssemblyRecipeBuilder transitionTo(ItemLike item) {
        recipe.transitionalItem = new ProcessingOutput(new ItemStack(item),1);
        super.transitionTo(item);
        return this;
    }

    @Override
    public TieredSequencedAssemblyRecipeBuilder require(ItemLike ingredient) {
        super.require(ingredient);
        return this;
    }

    @Override
    public TieredSequencedAssemblyRecipeBuilder require(TagKey<Item> tag) {
        super.require(tag);
        return this;
    }

    @Override
    public TieredSequencedAssemblyRecipeBuilder require(Ingredient ingredient) {
        super.require(ingredient);
        return this;
    }

    @Override
    public <T extends ProcessingRecipe<?>> TieredSequencedAssemblyRecipeBuilder addStep(
            ProcessingRecipeBuilder.ProcessingRecipeFactory<T> factory, UnaryOperator<ProcessingRecipeBuilder<T>> builder) {
        super.addStep(factory, builder);
        return this;
    }

    public void build(Consumer<FinishedRecipe> consumer, int recipeTier) {
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
