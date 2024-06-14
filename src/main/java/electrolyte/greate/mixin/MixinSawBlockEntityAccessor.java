package electrolyte.greate.mixin;

import com.simibubi.create.content.kinetics.saw.SawBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.filtering.FilteringBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SawBlockEntity.class)
public interface MixinSawBlockEntityAccessor {

    @Accessor("recipeIndex") int getRecipeIndex();
    @Accessor("recipeIndex") void setRecipeIndex(int recipeIndex);
    @Accessor("filtering") FilteringBehaviour getFilteringBehaviour();
    @Accessor("cuttingRecipesKey") Object getCuttingRecipesKey();
}
