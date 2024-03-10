package me.imflowow.tritium.core.modules;

import me.imflowow.tritium.core.modules.components.armordisplay.ItemEntity;
import net.minecraft.item.ItemStack;
import tritium.api.module.Module;
import tritium.api.module.value.impl.BooleanValue;
import tritium.api.module.value.impl.PositionValue;
import tritium.api.module.value.utils.Position;
import tritium.api.module.value.utils.Position.Direction;

public class ArmorDisplay extends Module {

	public BooleanValue damageValue = new BooleanValue("Damage", false);

	public PositionValue helmet = new PositionValue("Healmet", new Position(10, 10, Direction.Left, Direction.Top));
	public PositionValue breastplate = new PositionValue("Breastplate",
			new Position(10, 10, Direction.Left, Direction.Top));
	public PositionValue trousers = new PositionValue("Trousers", new Position(10, 10, Direction.Left, Direction.Top));
	public PositionValue shoes = new PositionValue("Shoes", new Position(10, 10, Direction.Left, Direction.Top));

	public PositionValue held = new PositionValue("Held", new Position(10, 10, Direction.Left, Direction.Top));

	public ArmorDisplay() {
		super("ArmorDisplay", "Display armors on your screen.");
		super.addValues(damageValue);
		super.addGuiEntities(new ItemEntity(helmet) {

			@Override
			public ItemStack getItem() {
				return mc.thePlayer.inventory.armorInventory[3];
			}
		}, new ItemEntity(breastplate) {

			@Override
			public ItemStack getItem() {
				return mc.thePlayer.inventory.armorInventory[2];
			}
		}, new ItemEntity(trousers) {

			@Override
			public ItemStack getItem() {
				return mc.thePlayer.inventory.armorInventory[1];
			}
		}, new ItemEntity(shoes) {

			@Override
			public ItemStack getItem() {
				return mc.thePlayer.inventory.armorInventory[0];
			}
		}, new ItemEntity(held) {

			@Override
			public ItemStack getItem() {
				return mc.thePlayer.getHeldItem();
			}
		});
	}

}
