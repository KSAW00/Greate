package electrolyte.greate.content.kinetics.crusher;

import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.kinetics.belt.behaviour.DirectBeltInputBehaviour;
import com.simibubi.create.content.kinetics.crusher.CrushingWheelControllerBlock;
import com.simibubi.create.content.kinetics.crusher.CrushingWheelControllerBlockEntity;
import com.simibubi.create.content.processing.recipe.ProcessingInventory;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.damageTypes.CreateDamageSources;
import com.simibubi.create.foundation.item.ItemHelper;
import com.simibubi.create.foundation.recipe.RecipeConditions;
import com.simibubi.create.foundation.utility.VecHelper;
import com.simibubi.create.infrastructure.config.AllConfigs;
import electrolyte.greate.foundation.data.recipe.TieredRecipeConditions;
import electrolyte.greate.foundation.recipe.TieredRecipeFinder;
import electrolyte.greate.foundation.recipe.TieredRecipeHelper;
import electrolyte.greate.mixin.MixinCrushingWheelControllerBlockEntityAccessor;
import electrolyte.greate.registry.ModRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TieredCrushingWheelControllerBlockEntity extends CrushingWheelControllerBlockEntity {

    private final RecipeWrapper wrapper;
    private static final Object CRUSHING_RECIPES_CACHE_KEY = new Object();
    private int tier;

    public TieredCrushingWheelControllerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        inventory = new ProcessingInventory(this::itemInserted) {
            @Override
            public boolean isItemValid(int slot, ItemStack stack) {
                return super.isItemValid(slot, stack) && processingEntity == null;
            }
        };
        wrapper = new RecipeWrapper(inventory);
        tier = ((TieredCrushingWheelControllerBlock) state.getBlock()).getTier();
    }

    private Optional<Recipe<?>> findValidRecipe() {
        return TieredRecipeFinder.findRecipe(CRUSHING_RECIPES_CACHE_KEY, level, wrapper,
                RecipeConditions.isOfType(GTRecipeTypes.MACERATOR_RECIPES, ModRecipeTypes.CRUSHING.getType(),
                        ModRecipeTypes.MILLING.getType(), AllRecipeTypes.MILLING.getType()).and(TieredRecipeConditions.firstIngredientMatches(wrapper.getItem(0))),
                TieredRecipeConditions.isEqualOrAboveTier(tier));
    }

    @Override
    public void tick() {
        super.tick();
        if(searchForEntity) {
            searchForEntity = false;
            List<Entity> search = level.getEntities((Entity) null, new AABB(getBlockPos()), e -> ((MixinCrushingWheelControllerBlockEntityAccessor) this).getEntityUUID().equals(e.getUUID()));
            if(search.isEmpty()) clear();
            else processingEntity = search.get(0);
        }

        if(!isOccupied()) return;
        if(crushingspeed == 0) return;
        if(level.isClientSide) {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> this::tickAudio);
        }

        float speed = crushingspeed * 4;
        Vec3 centerPos = VecHelper.getCenterOf(worldPosition);
        Direction facing = getBlockState().getValue(CrushingWheelControllerBlock.FACING);
        int offset = facing.getAxisDirection().getStep();
        Vec3 outSpeed = new Vec3((facing.getAxis() == Axis.X ? 0.25D : 0.0D) * offset,
                offset == 1 ? (facing.getAxis() == Axis.Y ? 0.5D : 0.0D) : 0.0D,
                        (facing.getAxis() == Axis.Z  ? 0.25D : 0.0D) * offset);
        Vec3 outPos = centerPos.add((facing.getAxis() == Axis.X ? 0.55F * offset : 0f),
                (facing.getAxis() == Axis.Y ? 0.55f * offset : 0f), (facing.getAxis() == Axis.Z ? 0.55f * offset : 0f));
        if(!hasEntity()) {
            float processingSpeed = Mth.clamp((speed) / (!inventory.appliedRecipe ? Mth.log2(inventory.getStackInSlot(0).getCount()) : 1), 0.25f, 20);
            inventory.remainingTime -= processingSpeed;
            spawnParticles(inventory.getStackInSlot(0));

            if(level.isClientSide) return;
            if(inventory.remainingTime < 20 && !inventory.appliedRecipe) {
                applyRecipe();
                inventory.appliedRecipe = true;
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2 | 16);
                return;
            }

            if(inventory.remainingTime > 0) {
                return;
            }

            inventory.remainingTime = 0;

            if(facing != Direction.UP) {
                BlockPos nextPos = worldPosition.offset(facing.getAxis() == Axis.X ? offset : 0, -1, facing.getAxis() == Axis.Z ? offset : 0);
                DirectBeltInputBehaviour behaviour = BlockEntityBehaviour.get(level, nextPos, DirectBeltInputBehaviour.TYPE);
                if(behaviour != null) {
                    boolean changed = false;
                    if(!behaviour.canInsertFromSide(facing)) return;
                    for(int slot = 0; slot < inventory.getSlots(); slot++) {
                        ItemStack stack = inventory.getStackInSlot(slot);
                        if(stack.isEmpty()) continue;
                        ItemStack remainder = behaviour.handleInsertion(stack, facing, false);
                        if(remainder.equals(stack, false)) continue;
                        inventory.setStackInSlot(slot, remainder);
                        changed = true;
                    }

                    if(changed) {
                        setChanged();
                        sendData();
                    }
                    return;
                }
            }

            for(int slot = 0; slot < inventory.getSlots(); slot++) {
                ItemStack stack = inventory.getStackInSlot(slot);
                if(stack.isEmpty()) continue;
                ItemEntity entity = new ItemEntity(level, outPos.x, outPos.y, outPos.z, stack);
                entity.setDeltaMovement(outSpeed);
                entity.getPersistentData().put("BypassCrushingWheel", NbtUtils.writeBlockPos(worldPosition));
                level.addFreshEntity(entity);
            }
            inventory.clear();
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2 | 16);
            return;
        }

        if(!processingEntity.isAlive() || !processingEntity.getBoundingBox().intersects(new AABB(worldPosition).inflate(0.5f))) {
            clear();
            return;
        }

        double xMotion = ((worldPosition.getX() + 0.5f) - processingEntity.getX()) / 2f;
        double zMotion = ((worldPosition.getZ() + 0.5f) - processingEntity.getZ()) / 2f;
        if(processingEntity.isShiftKeyDown()) {
            xMotion = zMotion = 0;
        }

        double movement = Math.max(-speed / 4f, -0.5f) * -offset;
        processingEntity.setDeltaMovement(new Vec3(
                facing.getAxis() == Axis.X ? movement : xMotion,
                facing.getAxis() == Axis.Y ? movement : 0f,
                facing.getAxis() == Axis.Z ? movement : zMotion));

        if(level.isClientSide) return;
        if(!(processingEntity instanceof ItemEntity ie)) {
            Vec3 entityOutPos = outPos.add(
                    facing.getAxis() == Axis.X ? 0.5f * offset : 0f,
                    facing.getAxis() == Axis.Y ? 0.5f * offset : 0f,
                    facing.getAxis() == Axis.Z ? 0.5f * offset : 0f);
            int crusherDamage = AllConfigs.server().kinetics.crushingDamage.get();
            if(processingEntity instanceof LivingEntity le) {
                if((le.getHealth() - crusherDamage <= 0) && le.hurtTime <= 0 ){
                    processingEntity.setPos(entityOutPos.x, entityOutPos.y, entityOutPos.z);
                }
            }

            processingEntity.hurt(CreateDamageSources.crush(level), crusherDamage);
            if(!processingEntity.isAlive()) {
                processingEntity.setPos(entityOutPos.x, entityOutPos.y, entityOutPos.z);
            }
            return;
        }

        ie.setPickUpDelay(20);
        if(facing.getAxis() == Axis.Y) {
            if(processingEntity.getY() * -offset < (centerPos.y - 0.25f) * -offset) {
                intakeItem(ie);
            }
        } else if(facing.getAxis() == Axis.Z) {
            if(processingEntity.getZ() * -offset < (centerPos.z - 0.25f) * -offset) {
                intakeItem(ie);
            }
        } else {
            if(processingEntity.getX() * - offset < (centerPos.x - 0.25f) * - offset) {
                intakeItem(ie);
            }
        }
    }

    private void applyRecipe() {
        Optional<Recipe<?>> recipe = findValidRecipe();
        List<ItemStack> list = new ArrayList<>();
        if(recipe.isPresent()) {
            int rolls = inventory.getStackInSlot(0).getCount();
            inventory.clear();
            for(int roll = 0; roll < rolls; roll++) {
                List<ItemStack> rolledResults = TieredRecipeHelper.INSTANCE.getItemResults(recipe.get(), tier);
                for(ItemStack stack : rolledResults) {
                    ItemHelper.addToList(stack, list);
                }
            }
            for(int slot = 0; slot < list.size() && slot + 1 < inventory.getSlots(); slot++) {
                inventory.setStackInSlot(slot + 1, list.get(slot));
            }
        } else {
            inventory.clear();
        }
    }

    private void itemInserted(ItemStack stack) {
        Optional<Recipe<?>> recipe = findValidRecipe();
        int remainingTime = 100;
        if(recipe.isPresent()) {
            if(recipe.get() instanceof ProcessingRecipe<?> pr) {
                remainingTime = pr.getProcessingDuration();
            } else if(recipe.get() instanceof GTRecipe gtr) {
                remainingTime = gtr.duration;
            }
        }
        inventory.remainingTime = remainingTime;
        inventory.appliedRecipe = false;
    }

    private void intakeItem(ItemEntity itemEntity) {
        inventory.clear();
        inventory.setStackInSlot(0, itemEntity.getItem().copy());
        itemInserted(inventory.getStackInSlot(0));
        itemEntity.discard();
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2 | 16);
    }
}
