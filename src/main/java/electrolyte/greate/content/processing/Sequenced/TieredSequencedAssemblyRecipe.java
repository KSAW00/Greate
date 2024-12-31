package electrolyte.greate.content.processing.Sequenced;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipe;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipeSerializer;
import com.simibubi.create.content.processing.sequenced.SequencedRecipe;
import com.simibubi.create.foundation.utility.Lang;
import net.minecraft.ChatFormatting;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import java.util.List;

public class TieredSequencedAssemblyRecipe extends SequencedAssemblyRecipe {

    private int recipeTier;
    ProcessingOutput transitionalItem;

    public TieredSequencedAssemblyRecipe(ResourceLocation recipeId, int tier, SequencedAssemblyRecipeSerializer serializer) {
        super(recipeId, AllRecipeTypes.SEQUENCED_ASSEMBLY.getSerializer());
        this.recipeTier = tier;
    }

    public int getRecipeTier() {
        return recipeTier;
    }

    public void setRecipeTier(int recipeTier) {
        this.recipeTier = recipeTier;
    }

    @Override
    public ItemStack assemble(RecipeWrapper inv, RegistryAccess registryAccess) {
        // Modified recipe processing based on tiers
        if (recipeTier > 0) {
            return super.getTransitionalItem().copy();
        }
        return super.assemble(inv, registryAccess);
    }

//    @Override
//    public ItemStack advance(ItemStack input) {
//        int step = getStep(input);
//        // Adjust looping count and output based on tier levels
//        if ((step + 1) / sequence.size() >= loops * recipeTier) {
//            return rollTieredResult();
//        }
//
//        return super.advance(input); // Regular advancement logic
//    }

    private int getStep(ItemStack input) {
        if (!input.hasTag())
            return 0;
        CompoundTag tag = input.getTag();
        if (!tag.contains("SequencedAssembly"))
            return 0;
        int step = tag.getCompound("SequencedAssembly")
                .getInt("Step");
        return step;
    }

    @Override
    public void addAdditionalIngredientsAndMachines(List<Ingredient> list) {
        super.addAdditionalIngredientsAndMachines(list);
        // Add tiered-specific logic for additional ingredients or machines
    }

    @Override
    public boolean matches(RecipeWrapper inv, Level world) {
        if (recipeTier > 0) {
            return super.getIngredient().test(inv.getItem(0));
        }
        return super.matches(inv, world);
    }

    @Override
    public RecipeType<?> getType() {
        return AllRecipeTypes.SEQUENCED_ASSEMBLY.getType();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return AllRecipeTypes.SEQUENCED_ASSEMBLY.getSerializer();
    }
}