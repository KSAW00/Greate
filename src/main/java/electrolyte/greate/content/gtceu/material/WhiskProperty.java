package electrolyte.greate.content.gtceu.material;

import com.gregtechceu.gtceu.api.data.chemical.material.properties.IMaterialProperty;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.MaterialProperties;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.PropertyKey;

public class WhiskProperty implements IMaterialProperty<WhiskProperty> {

	@Override
	public void verifyProperty(MaterialProperties properties) {
		properties.ensureSet(PropertyKey.INGOT, true);
	}
}
