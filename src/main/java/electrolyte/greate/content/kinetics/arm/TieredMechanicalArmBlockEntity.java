package electrolyte.greate.content.kinetics.arm;

import com.simibubi.create.content.kinetics.mechanicalArm.ArmBlockEntity;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPoint;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollOptionBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import com.simibubi.create.foundation.utility.Lang;
import com.simibubi.create.foundation.utility.VecHelper;
import com.simibubi.create.foundation.utility.animation.LerpedFloat;
import electrolyte.greate.Greate;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredKineticBlockEntity;
import electrolyte.greate.content.processing.Sequenced.TieredSequencedAssemblyRecipe;
import electrolyte.greate.foundation.data.recipe.TieredRecipeConditions;
import electrolyte.greate.registry.ModRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static electrolyte.greate.content.kinetics.arm.TieredArmAngleTarget.NO_TARGET;

public class TieredMechanicalArmBlockEntity extends ArmBlockEntity implements ITieredKineticBlockEntity {

    // Server
    List<ArmInteractionPoint> inputs;
    List<ArmInteractionPoint> outputs;
    ListTag interactionPointTag;

    // Both
    float chasedPointProgress;
    int chasedPointIndex;
    ItemStack heldItem;
    Phase phase;
    boolean goggles;

    // Client
    TieredArmAngleTarget previousTarget;
    LerpedFloat lowerArmAngle;
    LerpedFloat upperArmAngle;
    LerpedFloat baseAngle;
    LerpedFloat headAngle;
    LerpedFloat clawAngle;
    float previousBaseAngle;
    boolean updateInteractionPoints;
    int tooltipWarmup;

    //
    private int tier;
    private ScrollValueBehaviour targetCircuit;
    protected ScrollOptionBehaviour<SelectionMode> selectionMode;
    protected int lastInputIndex = -1;
    protected int lastOutputIndex = -1;
    protected boolean redstoneLocked;

    public enum Phase {
        SEARCH_INPUTS, MOVE_TO_INPUT, SEARCH_OUTPUTS, MOVE_TO_OUTPUT, DANCING
    }

    public TieredMechanicalArmBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
        inputs = new ArrayList<>();
        outputs = new ArrayList<>();
        interactionPointTag = null;
        heldItem = ItemStack.EMPTY;
        phase = TieredMechanicalArmBlockEntity.Phase.SEARCH_INPUTS;
        previousTarget = NO_TARGET;
        baseAngle = LerpedFloat.angular();
        baseAngle.startWithValue(previousTarget.baseAngle);
        lowerArmAngle = LerpedFloat.angular();
        lowerArmAngle.startWithValue(previousTarget.lowerArmAngle);
        upperArmAngle = LerpedFloat.angular();
        upperArmAngle.startWithValue(previousTarget.upperArmAngle);
        headAngle = LerpedFloat.angular();
        headAngle.startWithValue(previousTarget.headAngle);
        clawAngle = LerpedFloat.angular();
        previousBaseAngle = previousTarget.baseAngle;
        updateInteractionPoints = true;
        redstoneLocked = false;
        tooltipWarmup = 15;
        goggles = false;
        tier = ((TieredMechanicalArmBlock) state.getBlock()).getTier();
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);
        targetCircuit = new ScrollValueBehaviour(Lang.builder(Greate.MOD_ID).translate("tooltip.circuit_number").component(),
                this, new TieredMechanicalArmBlockEntity.CircuitValueBoxTransform());
        targetCircuit.between(0, 32);
        behaviours.add(targetCircuit);
    }

    public int getCircuitNumber() {
        return targetCircuit.getValue();
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        // Add tier information to the Goggle Tooltip
        super.addToGoggleTooltip(tooltip, isPlayerSneaking);
        return ITieredKineticBlockEntity.super.addToGoggleTooltip(tooltip, isPlayerSneaking, tier, capacity, stress);
    }

    public Optional<? extends Recipe<?>> getValidRecipe(ItemStack stack) {
        // Attempt to find a recipe matching the tier and item stack
        Optional<TieredEffectingRecipe> assemblyRecipe = TieredSequencedAssemblyRecipe.getRecipe(level, stack, ModRecipeTypes.EFFECTING.getType(), TieredEffectingRecipe.class)
            .filter(recipe -> recipe.getRecipeTier() <= tier);

        if(assemblyRecipe.isPresent()) {
            Predicate<Recipe<?>> predicate = TieredRecipeConditions.isEqualOrAboveTier(tier).and(TieredRecipeConditions.circuitMatches(targetCircuit.getValue()));
            if(predicate.test(assemblyRecipe.get())) {
                return assemblyRecipe;
            }
        }

        return assemblyRecipe.isPresent() ? assemblyRecipe : Optional.empty();
    }
//          Wot?

//    public boolean tryProcessItem(ItemEntity itemEntity, boolean simulate) {
//        ItemStack stack = itemEntity.getItem();
//        Optional<? extends Recipe<?>> recipe = getValidRecipe(stack);
//        if (recipe.isEmpty()) return false; // If no valid recipe, return early
//        if (simulate) return true; // Return success in simulation mode
//        applyRecipe(recipe.get(), itemEntity);
//
//        return true;
//    }

    private class CircuitValueBoxTransform extends ValueBoxTransform.Sided {
        @Override
        protected Vec3 getSouthLocation() {
            return VecHelper.voxelSpace(8, 9f, 15.5f);
        }

        @Override
        protected boolean isSideActive(BlockState state, Direction direction) {
            if(direction.getAxis().isVertical()) return false;
            return !((TieredMechanicalArmBlock) state.getBlock()).hasShaftTowards(level, getBlockPos(), state, direction);
        }
    }
}