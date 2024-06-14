package electrolyte.greate.foundation.data.recipe;

import com.google.common.collect.ImmutableList;
import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.UnificationEntry;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import electrolyte.greate.foundation.data.recipe.machine.*;
import electrolyte.greate.registry.*;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.function.Consumer;

public class GreateRecipes {
    public static void init(Consumer<FinishedRecipe> provider) {
        //TODO: remove once recipes are finalized
        conversionCycle(provider, ImmutableList.of(AllBlocks.SHAFT, Shafts.ANDESITE_SHAFT));
        conversionCycle(provider, ImmutableList.of(AllBlocks.COGWHEEL, Cogwheels.ANDESITE_COGWHEEL));
        conversionCycle(provider, ImmutableList.of(AllBlocks.LARGE_COGWHEEL, Cogwheels.LARGE_ANDESITE_COGWHEEL));
        conversionCycle(provider, ImmutableList.of(AllBlocks.CRUSHING_WHEEL, CrushingWheels.ANDESITE_CRUSHING_WHEEL));
        conversionCycle(provider, ImmutableList.of(AllItems.BELT_CONNECTOR, Belts.RUBBER_BELT_CONNECTOR));
        conversionCycle(provider, ImmutableList.of(AllBlocks.MECHANICAL_PRESS, MechanicalPresses.ANDESITE_MECHANICAL_PRESS));
        conversionCycle(provider, ImmutableList.of(AllBlocks.MECHANICAL_MIXER, MechanicalMixers.ANDESITE_MECHANICAL_MIXER));
        conversionCycle(provider, ImmutableList.of(AllBlocks.MECHANICAL_SAW, Saws.ANDESITE_SAW));

        GreateAlloySmelterRecipes.register(provider);
        GreateAssemblerRecipes.register(provider);
        GreateCraftingTableRecipes.register(provider);
        GreateCuttingMachineRecipes.register(provider);
        GreateDeployerRecipes.register(provider);
        GreateMaceratorRecipes.register(provider);
        GreateMillstoneRecipes.register(provider);
        GreateMechanicalCraftingRecipes.register(provider);
        GreateMechanicalMixingRecipes.register(provider);
        GreateSequencedAssemblyRecipes.register(provider);
    }

    public static void conversionCycle(Consumer<FinishedRecipe> provider, List<ItemProviderEntry<? extends ItemLike>> cycle) {
        for (int i = 0; i < cycle.size(); i++) {
            ItemProviderEntry<? extends ItemLike> currentEntry = cycle.get(i);
            ItemProviderEntry<? extends ItemLike> nextEntry = cycle.get((i + 1) % cycle.size());
            var builder = ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, nextEntry).requires(currentEntry).unlockedBy("has_cycle_origin", RegistrateRecipeProvider.has(currentEntry));
            builder.save(provider, RecipeBuilder.getDefaultRecipeId(builder.getResult()).withSuffix("_from_conversion"));
        }
    }

    public static Ingredient createIngFromTag(String namespace, String path) {
        return Ingredient.of(TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), new ResourceLocation(namespace, path)));
    }

    public static Ingredient createIngFromUnificationEntry(Object ingredient) {
        return Ingredient.of(ChemicalHelper.getTag(((UnificationEntry) ingredient).tagPrefix, ((UnificationEntry) ingredient).material));
    }
}
