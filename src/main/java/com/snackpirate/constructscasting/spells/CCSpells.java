package com.snackpirate.constructscasting.spells;

import com.snackpirate.constructscasting.ConstructsCasting;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CCSpells {
	public static final DeferredRegister<AbstractSpell> SPELLS = DeferredRegister.create(SpellRegistry.SPELL_REGISTRY_KEY, ConstructsCasting.MOD_ID);

	public static void register(IEventBus eventBus) {
		SPELLS.register(eventBus);
	}

	public static RegistryObject<AbstractSpell> registerSpell(AbstractSpell spell) {
		return SPELLS.register(spell.getSpellName(), () -> spell);
	}
	public static RegistryObject<AbstractSpell> SLIMEBALL_SPELL = registerSpell(new SlimeballSpell());

}
