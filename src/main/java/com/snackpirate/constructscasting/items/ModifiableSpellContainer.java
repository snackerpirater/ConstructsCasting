package com.snackpirate.constructscasting.items;

import com.snackpirate.constructscasting.ConstructsCasting;
import com.snackpirate.constructscasting.modifiers.SpellSlotsModifier;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.capabilities.magic.SpellContainer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

public class ModifiableSpellContainer extends SpellContainer {
	public ModifiableSpellContainer(int spells, boolean b, boolean b1) {
		super(spells, b, b1);
	}


	@Override
	public boolean addSpell(AbstractSpell spell, int level, boolean locked, ItemStack itemStack) {
		ToolStack tool = ToolStack.from(itemStack);
		tool.getPersistentData().putBoolean(ConstructsCasting.id("spell_slots_active"), true);
		return super.addSpell(spell, level, locked, itemStack);
	}

	@Override
	public boolean removeSpellAtIndex(int index, ItemStack itemStack) {
		ToolStack tool = ToolStack.from(itemStack);
		boolean remove = super.removeSpellAtIndex(index, itemStack);
		tool.getPersistentData().putBoolean(ConstructsCasting.id("spell_slots_active"), !this.isEmpty());
		return remove;
	}
}
