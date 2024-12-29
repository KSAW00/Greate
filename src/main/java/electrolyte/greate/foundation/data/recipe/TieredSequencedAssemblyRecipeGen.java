package electrolyte.greate.foundation.data.recipe;

import com.simibubi.create.AllItems;
import com.simibubi.create.AllTags;
import com.simibubi.create.Create;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipeBuilder;
import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider;
import com.simibubi.create.content.fluids.transfer.FillingRecipe;
import com.simibubi.create.content.kinetics.press.PressingRecipe;
import electrolyte.greate.content.kinetics.arm.TieredEffectingRecipe;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.material.Fluids;

import java.util.function.UnaryOperator;

public class TieredSequencedAssemblyRecipeGen extends CreateRecipeProvider {

    GeneratedTieredRecipe REINFORCED_SHEET = create("sturdy_sheet", b -> b.require(AllTags.AllItemTags.OBSIDIAN_DUST.tag)
            .transitionTo(AllItems.INCOMPLETE_REINFORCED_SHEET.get())
            .addOutput(AllItems.STURDY_SHEET.get(), 1)
            .loops(1)
            .addStep(FillingRecipe::new, rb -> rb.require(Fluids.LAVA, 500))
            .addStep(PressingRecipe::new, rb -> rb)
            .addStep(TieredEffectingRecipe::new,rb->rb)
            .addStep(PressingRecipe::new, rb -> rb));

    public TieredSequencedAssemblyRecipeGen(PackOutput output) {
        super(output);
    }

    protected GeneratedTieredRecipe create(String name, UnaryOperator<SequencedAssemblyRecipeBuilder> transform) {
        GeneratedTieredRecipe generatedTieredRecipe =
                c -> transform.apply(new SequencedAssemblyRecipeBuilder(Create.asResource(name)))
                        .build(c);
        all.add(generatedTieredRecipe);
        return generatedTieredRecipe;
    }
    interface GeneratedTieredRecipe extends GeneratedRecipe {

    }
}
