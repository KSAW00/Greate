package electrolyte.greate.content.kinetics.arm;

import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.content.kinetics.belt.behaviour.BeltProcessingBehaviour;
import com.simibubi.create.content.kinetics.belt.behaviour.TransportedItemStackHandlerBehaviour;
import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;
import com.simibubi.create.content.logistics.depot.DepotBlockEntity;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;

import java.util.List;

public class EffectingBehaviour extends BeltProcessingBehaviour {
    public static final int CYCLE = 240;
    public static final int ENTITY_SCAN = 10;

    public EffectingBehaviour.EffectingBehaviourSpecifics specifics;
    public int prevRunningTicks;
    public int runningTicks;
    public boolean running;
    public boolean finished;
    public Mode mode;

    int entityScanCooldown;

    public interface EffectingBehaviourSpecifics {

        public boolean tryProcessOnBelt(TransportedItemStack input, List<ItemStack> outputList, boolean simulate);

        public boolean tryProcessOnDepot(boolean simulate);

        public boolean canProcessInBulk();

        public void onEffectingCompleted();

        public float getKineticSpeed();
    }

    public enum Mode {
        DEPOT, BELT;
    }

    public <T extends SmartBlockEntity & EffectingBehaviour.EffectingBehaviourSpecifics> EffectingBehaviour(T be) {
        super(be);
        this.specifics = be;
        entityScanCooldown = ENTITY_SCAN;
        whenItemEnters((s, i) -> TieredBeltEffectingCallbacks.onItemReceived(s, i, this));
        whileItemHeld((s, i) -> TieredBeltEffectingCallbacks.whenItemHeld(s, i, this));
    }

    @Override
    public void read(CompoundTag compound, boolean clientPacket) {
        running = compound.getBoolean("Running");
        finished = compound.getBoolean("Finished");
        prevRunningTicks = runningTicks = compound.getInt("Ticks");
        super.read(compound, clientPacket);
    }

    public int getRunningTickSpeed() {
        float speed = specifics.getKineticSpeed();
        if (speed == 0)
            return 0;
        return (int) Mth.lerp(Mth.clamp(Math.abs(speed) / 512f, 0, 1), 1, 60);
    }

    public void start(EffectingBehaviour.Mode mode) {
        this.mode = mode;
        running = true;
        prevRunningTicks = 0;
        runningTicks = 0;
        blockEntity.sendData();
    }

    public boolean onDepot() {
        return mode == Mode.DEPOT;
    }

    public boolean onBelt() {
        return mode == Mode.BELT;
    }

    public void tick() {
        super.tick();

        Level level = getWorld();
        BlockPos worldPosition = getPos();

        if (!running || level == null) {
            if (level != null && !level.isClientSide) {

                if (specifics.getKineticSpeed() == 0)
                    return;
                if (entityScanCooldown > 0)
                    entityScanCooldown--;
                if (entityScanCooldown <= 0) {
                    entityScanCooldown = ENTITY_SCAN;

                    if (BlockEntityBehaviour.get(level, worldPosition.below(2),
                            TransportedItemStackHandlerBehaviour.TYPE) != null)
                        return;

                    //Check if nearby block isDepot
                    for (Direction direction : new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST}) {
                        if (level.getBlockEntity(worldPosition.relative(direction)) instanceof DepotBlockEntity) {
                            return;
                        }
                    }
                }

            }
            return;
        }

        if (level.isClientSide && runningTicks == -CYCLE / 2) {
            prevRunningTicks = CYCLE / 2;
            return;
        }

        if (runningTicks == CYCLE / 2 && specifics.getKineticSpeed() != 0) {
            if (onDepot())
                applyOnDepot();

            if (level.getBlockState(worldPosition.below(2))
                    .getSoundType() == SoundType.WOOL)
                AllSoundEvents.MECHANICAL_PRESS_ACTIVATION_ON_BELT.playOnServer(level, worldPosition);
            else
                AllSoundEvents.MECHANICAL_PRESS_ACTIVATION.playOnServer(level, worldPosition, .5f,
                        .75f + (Math.abs(specifics.getKineticSpeed()) / 1024f));

            if (!level.isClientSide)
                blockEntity.sendData();
        }

        if (!level.isClientSide && runningTicks > CYCLE) {
            finished = true;
            running = false;
            specifics.onEffectingCompleted();
            blockEntity.sendData();
            return;
        }

        prevRunningTicks = runningTicks;
        runningTicks += getRunningTickSpeed();
        if (prevRunningTicks < CYCLE / 2 && runningTicks >= CYCLE / 2) {
            runningTicks = CYCLE / 2;
            // Pause the ticks until a packet is received
            if (level.isClientSide && !blockEntity.isVirtual())
                runningTicks = -(CYCLE / 2);
        }
    }

    protected void applyOnDepot() {
        Level level = getWorld();
        if (level.isClientSide)
            return;
        if (specifics.tryProcessOnDepot(false))
            blockEntity.sendData();
    }
}
