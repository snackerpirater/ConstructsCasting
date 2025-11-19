package com.snackpirate.constructscasting.items.book;

import com.snackpirate.constructscasting.ConstructsCasting;
import com.snackpirate.constructscasting.materials.*;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.client.book.content.AbstractMaterialContent;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.library.utils.Util;

public class MagicMaterialContent extends AbstractMaterialContent {
	public static final ResourceLocation ID = ConstructsCasting.id("magic_material");
	public MagicMaterialContent(MaterialVariantId materialVariant, boolean detailed) {
		super(materialVariant, detailed);
	}

	@Override
	public ResourceLocation getId() {
		return ID;
	}

	@Nullable
	@Override
	protected MaterialStatsId getStatType(int index) {
		return switch (index) {
			case 0 -> MagicBaseMaterialStats.ID;
			case 1 -> MagicClothMaterialStats.ID;
			case 2 -> CCMaterialStats.Statless.ADORNMENT.getIdentifier();
			default -> null;
		};
	}

	@Override
	protected String getTextKey(MaterialId material) {
		if (detailed) {
			String primaryKey = String.format("material.%s.%s.magic", material.getNamespace(), material.getPath());
			if (Util.canTranslate(primaryKey)) {
				return primaryKey;
			}
			return String.format("material.%s.%s.encyclopedia", material.getNamespace(), material.getPath());
		}
		return String.format("material.%s.%s.flavor", material.getNamespace(), material.getPath());
	}


	@Override
	protected boolean supportsStatType(MaterialStatsId statsId) {
		return (statsId == MagicBaseMaterialStats.ID || statsId == MagicClothMaterialStats.ID || statsId == CCMaterialStats.Statless.ADORNMENT.getIdentifier());
	}
}
