package electrolyte.greate.content.kinetics.millstone;

import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.kinetics.belt.behaviour.DirectBeltInputBehaviour;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.item.ItemHelper;
import com.simibubi.create.foundation.sound.SoundScapes;
import com.simibubi.create.foundation.utility.VecHelper;
import electrolyte.greate.GreateEnums;
import electrolyte.greate.content.kinetics.simpleRelays.ITieredKineticBlockEntity;
import electrolyte.greate.content.kinetics.simpleRelays.TieredKineticBlockEntity;
import electrolyte.greate.content.processing.recipe.TieredProcessingRecipe;
import electrolyte.greate.registry.ModRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class TieredMillstoneBlockEntity extends TieredKineticBlockEntity implements ITieredKineticBlockEntity {

    public ItemStackHandler inputInv;
    public ItemStackHandler outputInv;
    public LazyOptional<IItemHandler> capability;
    public int timer;
    private ProcessingRecipe<RecipeWrapper> lastRecipe;
    private int maxItemsPerRecipe;

    public TieredMillstoneBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
        inputInv = new ItemStackHandler(1);
        outputInv = new ItemStackHandler(1);
        capability = LazyOptional.of(TieredMillstoneInventoryHandler::new);
        maxItemsPerRecipe = GreateEnums.TIER.getTierMultiplier(this.getTier(), 2);
    }

    @Override
    public double getMaxCapacity() {
        return this.getTier().getStressCapacity();
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        behaviours.add(new DirectBeltInputBehaviour(this));
        super.addBehaviours(behaviours);
        registerAwardables(behaviours, AllAdvancements.MILLSTONE);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void tickAudio() {
        super.tickAudio();
        if(getSpeed() == 0) return;
        if(inputInv.getStackInSlot(0).isEmpty()) return;
        float pitch = Mth.clamp((Math.abs(getSpeed()) / 256f) + .45f, .85f, 1f);
        SoundScapes.play(SoundScapes.AmbienceGroup.MILLING, worldPosition, pitch);
    }

    @Override
    public void tick() {
        super.tick();

        if(getSpeed() == 0) return;

        for(int i = 0; i < outputInv.getSlots(); i++) {
            if(outputInv.getStackInSlot(i).getCount() == outputInv.getSlotLimit(i)) return;
            if(outputInv.getStackInSlot(i).getCount() + maxItemsPerRecipe > outputInv.getSlotLimit(i)) return;
        }

        RecipeWrapper inventoryIn = new RecipeWrapper(inputInv);

        if(timer > 0 && lastRecipe != null && lastRecipe.matches(inventoryIn, level)) {
            timer -= getProcessingSpeed();

            if(level.isClientSide) {
                spawnParticles();
                return;
            }

            if(timer <= 0) {
                process();
            }
            return;
        }

        if(inputInv.getStackInSlot(0).isEmpty()) return;

        if(lastRecipe == null || !lastRecipe.matches(inventoryIn, level)) {
            Optional<ProcessingRecipe<RecipeWrapper>> recipe = findRecipe(inventoryIn);
            if(recipe.isEmpty()) {
                timer = 100;
                sendData();
            } else {
                lastRecipe = recipe.get();
                timer = lastRecipe.getProcessingDuration();
                sendData();
            }
            return;
        }

        timer = lastRecipe.getProcessingDuration();
        sendData();
    }
    
    public Optional<ProcessingRecipe<RecipeWrapper>> findRecipe(RecipeWrapper wrapper) {
        Optional<ProcessingRecipe<RecipeWrapper>> millingRecipe = ModRecipeTypes.MILLING.find(wrapper, level, this.getTier());
        if(millingRecipe.isEmpty()) {
            millingRecipe = AllRecipeTypes.MILLING.find(wrapper, level);
        }
        if (millingRecipe.isEmpty()) {
            Optional<GTRecipe> test = level.getRecipeManager().getAllRecipesFor(GTRecipeTypes.MACERATOR_RECIPES).stream().filter(r ->
                    ((Ingredient) r.getInputContents(ItemRecipeCapability.CAP).get(0).getContent()).test(wrapper.getItem(0))).findFirst();
            if(test.isPresent()) {
                TieredProcessingRecipe<RecipeWrapper> convertedRecipe = TieredMillingRecipe.convertGT(test.get());
                if(convertedRecipe.getRecipeTier().compareTo(this.getTier()) <= 0) {
                    return Optional.of(convertedRecipe);
                }
            }
        }
        return millingRecipe;
    }

    @Override
    public void invalidate() {
        super.invalidate();
        capability.invalidate();
    }

    @Override
    public void destroy() {
        super.destroy();
        ItemHelper.dropContents(level, worldPosition, inputInv);
        ItemHelper.dropContents(level, worldPosition, outputInv);
    }

    private void process() {
        RecipeWrapper inventoryIn = new RecipeWrapper(inputInv);

        if(lastRecipe == null || !lastRecipe.matches(inventoryIn, level)) {
            Optional<ProcessingRecipe<RecipeWrapper>> recipe = findRecipe(inventoryIn);

            if(recipe.isEmpty()) return;
            lastRecipe = recipe.get();
        }

        ItemStack stackInSlot = inputInv.getStackInSlot(0);
        int amountInSlot = stackInSlot.getCount();
        stackInSlot.shrink(maxItemsPerRecipe);
        inputInv.setStackInSlot(0, stackInSlot);
        for(int i = 0; i < Math.min(amountInSlot, maxItemsPerRecipe); i++) {
            lastRecipe.rollResults().forEach(stack -> ItemHandlerHelper.insertItemStacked(outputInv, stack, false));
        }
        award(AllAdvancements.MILLSTONE);

        sendData();
        setChanged();
    }

    public void spawnParticles() {
        ItemStack stackInSlot = inputInv.getStackInSlot(0);
        if(stackInSlot.isEmpty()) return;

        ItemParticleOption data = new ItemParticleOption(ParticleTypes.ITEM, stackInSlot);
        float angle = level.random.nextFloat() * 360;
        Vec3 offset = new Vec3(0,0, 0.5f);
        offset = VecHelper.rotate(offset, angle, Direction.Axis.Y);
        Vec3 target = VecHelper.rotate(offset, getSpeed() > 0 ? 25 : -25, Direction.Axis.Y);

        Vec3 center = offset.add(VecHelper.getCenterOf(worldPosition));
        target = VecHelper.offsetRandomly(target.subtract(offset), level.random, 1 / 128f);
        level.addParticle(data, center.x, center.y, center.z, target.x, target.y, target.z);
    }

    @Override
    protected void write(CompoundTag compound, boolean clientPacket) {
        compound.putInt("Timer", timer);
        compound.put("InputInventory", inputInv.serializeNBT());
        compound.put("OutputInventory", outputInv.serializeNBT());
        super.write(compound, clientPacket);
    }

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        timer = compound.getInt("Timer");
        inputInv.deserializeNBT(compound.getCompound("InputInventory"));
        outputInv.deserializeNBT(compound.getCompound("OutputInventory"));
        super.read(compound, clientPacket);
    }

    public int getProcessingSpeed() {
        return Mth.clamp((int) Math.abs(getSpeed() / 16f), 1, 512);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(isItemHandlerCap(cap)) return capability.cast();
        return super.getCapability(cap, side);
    }

    private boolean canProcess(ItemStack stack) {
        ItemStackHandler tester = new ItemStackHandler(1);
        tester.setStackInSlot(0, stack);
        RecipeWrapper inventoryIn = new RecipeWrapper(tester);

        if(lastRecipe != null && lastRecipe.matches(inventoryIn, level)) return true;
        return inputInv.getStackInSlot(0).isEmpty() && outputInv.getStackInSlot(0).isEmpty();
    }

    private class TieredMillstoneInventoryHandler extends CombinedInvWrapper {

        public TieredMillstoneInventoryHandler() {
            super(inputInv, outputInv);
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            if(outputInv == getHandlerFromIndex(getIndexForSlot(slot))) return false;
            return canProcess(stack) && super.isItemValid(slot, stack);
        }

        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
            if(outputInv == getHandlerFromIndex(getIndexForSlot(slot))) return stack;
            if(!isItemValid(slot, stack)) return stack;
            return super.insertItem(slot, stack, simulate);
        }

        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            if(inputInv == getHandlerFromIndex(getIndexForSlot(slot))) return ItemStack.EMPTY;
            return super.extractItem(slot, amount, simulate);
        }
    }
}
