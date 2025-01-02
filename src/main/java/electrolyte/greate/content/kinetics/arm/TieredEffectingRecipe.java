package electrolyte.greate.content.kinetics.arm;

import com.gregtechceu.gtceu.api.GTValues;

import com.gregtechceu.gtceu.api.capability.recipe.EURecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.simibubi.create.compat.jei.category.sequencedAssembly.SequencedAssemblySubCategory;

import com.simibubi.create.content.processing.sequenced.IAssemblyRecipe;

import com.simibubi.create.foundation.utility.LangBuilder;
import electrolyte.greate.Greate;

import electrolyte.greate.GreateValues;
import electrolyte.greate.compat.jei.category.sequencedassembly.TieredEffectingSubCategory;
import electrolyte.greate.content.kinetics.press.TieredPressingRecipe;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipe;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeBuilder;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipeBuilder.TieredProcessingRecipeParams;
import electrolyte.greate.registry.MechanicalArms;
import electrolyte.greate.registry.ModRecipeTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import static com.gregtechceu.gtceu.api.GTValues.ULV;

@ParametersAreNonnullByDefault
public class TieredEffectingRecipe extends TieredProcessingRecipe<RecipeWrapper> implements IAssemblyRecipe{
    public TieredEffectingRecipe(TieredProcessingRecipeParams params) {
        super(ModRecipeTypes.EFFECTING, params);
    }

    @Override
    protected int getMaxInputCount() {
        return 1;
    }

    @Override
    protected int getMaxOutputCount() {
        return 2;
    }

    @Override
    public Component getDescriptionForAssembly() {
        return new LangBuilder(Greate.MOD_ID).translate("recipe.assembly.effecting", GTValues.VN[recipeTier]).component();
    }

    @Override
    public void addRequiredMachines(Set<ItemLike> list) {
        list.add(MechanicalArms.ALUMINIUM_MECHANICAL_ARM.get());
    }

    @Override
    public void addAssemblyIngredients(List<Ingredient> list) {}

    @Override
    public boolean matches(RecipeWrapper pContainer, Level pLevel) {
        if(pContainer.isEmpty()) return false;
        return ingredients.get(0).test(pContainer.getItem(0));
    }

    public boolean matches(RecipeWrapper container, Level level, int machineTier) {
        boolean containerMatches = matches(container, level);
        return containerMatches && this.getRecipeTier() <= machineTier;
    }

    @Override
    public Supplier<Supplier<SequencedAssemblySubCategory>> getJEISubCategory() {
        return () -> TieredEffectingSubCategory::new;
    }

    public static TieredEffectingRecipe convertGT(GTRecipe recipe) {
        List<Content> inputContents = recipe.getInputContents(ItemRecipeCapability.CAP);
        int recipeTier = GreateValues.convertGTEUToTier(recipe.getTickInputContents(EURecipeCapability.CAP));
        return new TieredProcessingRecipeBuilder<>(TieredEffectingRecipe::new, recipe.getId())
                .withItemIngredientsGT(inputContents)
                .withItemOutputsGT(recipe.getOutputContents(ItemRecipeCapability.CAP), recipeTier, ULV)
                .recipeTier(recipeTier)
                .recipeCircuit(getCircuitFromGTRecipe(inputContents))
                .build();
    }
}
