package electrolyte.greate.content.kinetics.millstone;

import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.MaterialManager;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;
import electrolyte.greate.content.kinetics.base.TieredSingleRotatingInstance;

import static electrolyte.greate.registry.GreatePartialModels.MILLSTONE_INNER_MODELS;

public class TieredMillstoneCogInstance extends TieredSingleRotatingInstance<TieredMillstoneBlockEntity> {

    public TieredMillstoneCogInstance(MaterialManager materialManager, TieredMillstoneBlockEntity blockEntity) {
        super(materialManager, blockEntity);
    }

    @Override
    protected Instancer<RotatingData> getModel() {
        int tier = ((TieredMillstoneBlock) blockEntity.getBlockState().getBlock()).getTier();
        return getRotatingMaterial().getModel(MILLSTONE_INNER_MODELS[tier], blockEntity.getBlockState());
    }
}
