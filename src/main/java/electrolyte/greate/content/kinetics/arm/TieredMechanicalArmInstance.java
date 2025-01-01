package electrolyte.greate.content.kinetics.arm;

import com.google.common.collect.Lists;
import com.jozufozu.flywheel.api.InstanceData;
import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.Material;
import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.api.instance.DynamicInstance;
import com.jozufozu.flywheel.core.materials.model.ModelData;
import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.kinetics.base.SingleRotatingInstance;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmBlock;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmBlockEntity;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmRenderer;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import com.simibubi.create.foundation.utility.Color;
import com.simibubi.create.foundation.utility.Iterate;
import electrolyte.greate.registry.GreatePartialModels;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;

public class TieredMechanicalArmInstance extends SingleRotatingInstance<TieredMechanicalArmBlockEntity> implements DynamicInstance {

	final ModelData base;
	final ModelData lowerBody;
	final ModelData upperBody;
	ModelData claw;

	private final ArrayList<ModelData> clawGrips;

	private final ArrayList<ModelData> models;
	private final Boolean ceiling;

	private boolean firstRender = true;

	private float baseAngle = Float.NaN;
	private float lowerArmAngle = Float.NaN;
	private float upperArmAngle = Float.NaN;
	private float headAngle = Float.NaN;
	private int tier;
	public TieredMechanicalArmInstance(MaterialManager materialManager, TieredMechanicalArmBlockEntity blockEntity) {
		super(materialManager, blockEntity);
		tier = ((TieredMechanicalArmBlock) this.blockEntity.getBlockState().getBlock()).getTier();
		Material<ModelData> mat = getTransformMaterial();

		base = mat.getModel(GreatePartialModels.ARM_BASE_MODELS[tier], blockState)
			.createInstance();
		lowerBody = mat.getModel(GreatePartialModels.ARM_LOWER_BODY_MODELS[tier], blockState)
			.createInstance();
		upperBody = mat.getModel(GreatePartialModels.ARM_UPPER_BODY_MODELS[tier], blockState)
			.createInstance();
		claw = mat
			.getModel(blockEntity.goggles ? GreatePartialModels.ARM_CLAW_BASE_GOGGLES_MODELS[tier] : GreatePartialModels.ARM_CLAW_BASE_MODELS[tier],
				blockState)
			.createInstance();
		ModelData clawGrip1 = mat.getModel(GreatePartialModels.ARM_CLAW_GRIP_UPPER_MODELS[tier], blockState)
			.createInstance();
		ModelData clawGrip2 = mat.getModel(GreatePartialModels.ARM_CLAW_GRIP_LOWER_MODELS[tier], blockState)
			.createInstance();

		clawGrips = Lists.newArrayList(clawGrip1, clawGrip2);
		models = Lists.newArrayList(base, lowerBody, upperBody, claw, clawGrip1, clawGrip2);
		ceiling = blockState.getValue(ArmBlock.CEILING);

		animateArm(false);
	}

	@Override
	public void beginFrame() {
		if (blockEntity.phase == TieredMechanicalArmBlockEntity.Phase.DANCING && blockEntity.getSpeed() != 0) {
			animateArm(true);
			firstRender = true;
			return;
		}

		float pt = AnimationTickHolder.getPartialTicks();

		float baseAngleNow = blockEntity.baseAngle.getValue(pt);
		float lowerArmAngleNow = blockEntity.lowerArmAngle.getValue(pt);
		float upperArmAngleNow = blockEntity.upperArmAngle.getValue(pt);
		float headAngleNow = blockEntity.headAngle.getValue(pt);

		boolean settled = Mth.equal(baseAngle, baseAngleNow) && Mth.equal(lowerArmAngle, lowerArmAngleNow)
			&& Mth.equal(upperArmAngle, upperArmAngleNow) && Mth.equal(headAngle, headAngleNow);

		this.baseAngle = baseAngleNow;
		this.lowerArmAngle = lowerArmAngleNow;
		this.upperArmAngle = upperArmAngleNow;
		this.headAngle = headAngleNow;

		if (!settled || firstRender)
			animateArm(false);

		if (firstRender)
			firstRender = false;
	}

	private void animateArm(boolean rave) {
		float baseAngle;
		float lowerArmAngle;
		float upperArmAngle;
		float headAngle;
		int color;

		if (rave) {
			float renderTick =
				AnimationTickHolder.getRenderTime(blockEntity.getLevel()) + (blockEntity.hashCode() % 64);
			baseAngle = (renderTick * 10) % 360;
			lowerArmAngle = Mth.lerp((Mth.sin(renderTick / 4) + 1) / 2, -45, 15);
			upperArmAngle = Mth.lerp((Mth.sin(renderTick / 8) + 1) / 4, -45, 95);
			headAngle = -lowerArmAngle;
			color = Color.rainbowColor(AnimationTickHolder.getTicks() * 100)
				.getRGB();
		} else {
			baseAngle = this.baseAngle;
			lowerArmAngle = this.lowerArmAngle - 135;
			upperArmAngle = this.upperArmAngle - 90;
			headAngle = this.headAngle;
			color = 0xFFFFFF;
		}

		PoseStack msLocal = new PoseStack();
		TransformStack msr = TransformStack.cast(msLocal);
		msr.translate(getInstancePosition());
		msr.centre();

		if (ceiling)
			msr.rotateX(180);

		ArmRenderer.transformBase(msr, baseAngle);
		base.setTransform(msLocal);

		ArmRenderer.transformLowerArm(msr, lowerArmAngle);
		lowerBody.setTransform(msLocal)
			.setColor(color);

		ArmRenderer.transformUpperArm(msr, upperArmAngle);
		upperBody.setTransform(msLocal)
			.setColor(color);

		ArmRenderer.transformHead(msr, headAngle);
		
		if (ceiling && blockEntity.goggles)
			msr.rotateZ(180);
		
		claw.setTransform(msLocal);
		
		if (ceiling && blockEntity.goggles)
			msr.rotateZ(180);

		ItemStack item = blockEntity.heldItem;
		ItemRenderer itemRenderer = Minecraft.getInstance()
			.getItemRenderer();
		boolean hasItem = !item.isEmpty();
		boolean isBlockItem = hasItem && (item.getItem() instanceof BlockItem)
			&& itemRenderer.getModel(item, Minecraft.getInstance().level, null, 0)
				.isGui3d();

		for (int index : Iterate.zeroAndOne) {
			msLocal.pushPose();
			int flip = index * 2 - 1;
			ArmRenderer.transformClawHalf(msr, hasItem, isBlockItem, flip);
			clawGrips.get(index)
				.setTransform(msLocal);
			msLocal.popPose();
		}
	}

	@Override
	public void update() {
		super.update();
		models.remove(claw);
		claw.delete();
		claw = getTransformMaterial()
			.getModel(blockEntity.goggles ? GreatePartialModels.ARM_CLAW_BASE_GOGGLES_MODELS[tier] : GreatePartialModels.ARM_CLAW_BASE_MODELS[tier],
				blockState)
			.createInstance();
		models.add(claw);
		updateLight();
		animateArm(false);
	}

	@Override
	public void updateLight() {
		super.updateLight();

		relight(pos, models.stream());
	}

	@Override
	protected Instancer<RotatingData> getModel() {
		return getRotatingMaterial().getModel(GreatePartialModels.ARM_COG_MODELS[tier], blockEntity.getBlockState());
	}

	@Override
	public void remove() {
		super.remove();
		models.forEach(InstanceData::delete);
	}

}
