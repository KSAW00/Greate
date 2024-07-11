package electrolyte.greate.registry;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.PropertyKey;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.ToolProperty;
import electrolyte.greate.Greate;

import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.*;
import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet.*;
import static com.gregtechceu.gtceu.api.item.tool.GTToolType.BUZZSAW;
import static com.gregtechceu.gtceu.common.data.GTElements.Ma;
import static com.gregtechceu.gtceu.common.data.GTElements.Sp;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;

public class GreateMaterials {

	public static Material AndesiteAlloy;
	public static Material RoseQuartz;
	public static Material ChromaticCompound;
	public static Material RefinedRadiance;
	public static Material ShadowSteel;

	public static void init() {
		AndesiteAlloy = Builder("andesite_alloy")
				.ingot().fluid()
				.appendFlags(STD_METAL, GENERATE_BOLT_SCREW)
				.color(0xC7C8B8).iconSet(DULL)
				.toolStats(ToolProperty.Builder.of(1, 1, 64, 0, BUZZSAW).build())
				.components(Andesite, 9, Iron, 1)
				.buildAndRegister();
		RoseQuartz = Builder("rose_quartz")
				.gem()
				.color(0xF44471).secondaryColor(0xFCE8CF).iconSet(QUARTZ)
				.flags(NO_SMELTING, CRYSTALLIZABLE, DISABLE_DECOMPOSITION)
				.components(NetherQuartz, 1, Redstone, 8)
				.buildAndRegister();
		ChromaticCompound = Builder("chromatic_compound")
				.ingot().fluid()
				.color(0x744B71).iconSet(DULL)
				.components(Glowstone, 3, Obsidian, 3, RoseQuartz, 1)
				.buildAndRegister();
		RefinedRadiance = Builder("refined_radiance")
				.ingot().fluid()
				.color(0xffffff).secondaryColor(0xffffff).iconSet(METALLIC)
				.appendFlags(EXT2_METAL)
				.buildAndRegister()
				.setFormula(ChromaticCompound.getChemicalFormula() + Ma.symbol());
		ShadowSteel = Builder("shadow_steel")
				.ingot().fluid()
				.color(0x35333c).iconSet(METALLIC)
				.appendFlags(EXT2_METAL)
				.buildAndRegister()
				.setFormula(ChromaticCompound.getChemicalFormula() + Sp.symbol());

		WroughtIron.addFlags(GENERATE_ROTOR);
		Darmstadtium.setProperty(PropertyKey.TOOL, ToolProperty.Builder.of(50.0F, 15.0F, 5120, 5, BUZZSAW).build());
		RhodiumPlatedPalladium.setProperty(PropertyKey.TOOL, ToolProperty.Builder.of(35.0F, 10.0F, 2560, 4, BUZZSAW).build());
	}

	public static Material.Builder Builder(String id) {
		return new Material.Builder(Greate.id(id));
	}
}
