package me.imflowow.tritium.utils.itemscroller;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.lwjgl.input.Mouse;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMerchant;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotMerchantResult;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import tritium.api.utils.event.api.EventTarget;
import tritium.api.utils.event.events.GuiScreenMouseInputEvent;

public class ItemScroller {
	private int lastPosX;
	private int lastPosY;
	private int slotNumberLast;
	private final Set<Integer> draggedSlots = new HashSet<Integer>();

	@EventTarget
	public void onMouseInputEvent(GuiScreenMouseInputEvent event) {
		
		int dWheel = Mouse.getEventDWheel();
		GuiScreen gui = event.gui;

		if (gui instanceof GuiContainer) {
			boolean cancel = false;

			if (dWheel != 0) {
				this.tryMoveItems((GuiContainer) gui, dWheel > 0);
			} else {
				if (this.canShiftPlaceItems((GuiContainer) gui)) {
					cancel = this.shiftPlaceItems((GuiContainer) gui);
				} else if (this.canShiftDropItems((GuiContainer) gui)) {
					this.shiftDropItems((GuiContainer) gui);
				} else {
					cancel = this.dragMoveItems((GuiContainer) gui);
				}
			}

			if (cancel) {
				event.setCancelled(true);
			}
		}
	}

	private boolean isValidSlot(Slot slot, GuiContainer gui, boolean requireItems) {
		if (gui.inventorySlots == null || gui.inventorySlots.inventorySlots == null) {
			return false;
		}

		return slot != null && gui.inventorySlots.inventorySlots.contains(slot) == true
				&& (requireItems == false || slot.getHasStack() == true);
	}

	/**
	 * Checks if there are slots belonging to another inventory on screen above the
	 * given slot
	 */
	private boolean inventoryExistsAbove(Slot slot, Container container) {
		for (Slot slotTmp : container.inventorySlots) {
			if (slotTmp.yDisplayPosition < slot.yDisplayPosition && areSlotsInSameInventory(slot, slotTmp) == false) {
				return true;
			}
		}

		return false;
	}

	private boolean canShiftPlaceItems(GuiContainer gui) {
		boolean eventKeyIsLeftButton = (Mouse.getEventButton() - 100) == gui.mc.gameSettings.keyBindAttack.getKeyCode();

		if (GuiScreen.isShiftKeyDown() == false || eventKeyIsLeftButton == false) {
			return false;
		}

		Slot slot = gui.getSlotUnderMouse();
		ItemStack stackCursor = gui.mc.thePlayer.inventory.getItemStack();

		// The target slot needs to be an empty, valid slot, and there needs to be items
		// in the cursor
		return slot != null && stackCursor != null && this.isValidSlot(slot, gui, false) && slot.getHasStack() == false
				&& slot.isItemValid(stackCursor);
	}

	private boolean shiftPlaceItems(GuiContainer gui) {
		Slot slot = gui.getSlotUnderMouse();

		// Left click to place the items from the cursor to the slot
		gui.mc.playerController.windowClick(gui.inventorySlots.windowId, slot.slotNumber, 0, 0, gui.mc.thePlayer);

		this.tryMoveStacks(slot, gui, true, false, false);

		return true;
	}

	private boolean canShiftDropItems(GuiContainer gui) {
		boolean eventKeyIsLeftButton = (Mouse.getEventButton() - 100) == gui.mc.gameSettings.keyBindAttack.getKeyCode();

		if (GuiScreen.isShiftKeyDown() == false || eventKeyIsLeftButton == false) {
			return false;
		}

		int left = 0;
		int top = 0;
		int xSize = 0;
		int ySize = 0;
		int mouseAbsX = 0;
		int mouseAbsY = 0;

		left = gui.guiLeft;
		top = gui.guiTop;
		xSize = gui.xSize;
		ySize = gui.ySize;
		mouseAbsX = Mouse.getEventX() * gui.width / gui.mc.displayWidth;
		mouseAbsY = gui.height - Mouse.getEventY() * gui.height / gui.mc.displayHeight - 1;

		boolean isOutsideGui = mouseAbsX < left || mouseAbsY < top || mouseAbsX >= left + xSize
				|| mouseAbsY >= top + ySize;

		return isOutsideGui && this.getSlotAtPosition(gui, mouseAbsX - left, mouseAbsY - top) == null
				&& gui.mc.thePlayer.inventory.getItemStack() != null;
	}

	private void shiftDropItems(GuiContainer gui) {
		ItemStack stackCursor = gui.mc.thePlayer.inventory.getItemStack();
		Container container = gui.inventorySlots;
		Minecraft mc = gui.mc;
		EntityPlayer player = mc.thePlayer;

		// First drop the existing stack from the cursor
		mc.playerController.windowClick(container.windowId, -999, 0, 0, player);

		for (Slot slot : container.inventorySlots) {
			if (areStacksEqual(slot.getStack(), stackCursor)) {
				// Pick up the items
				mc.playerController.windowClick(container.windowId, slot.slotNumber, 0, 0, player);
				// Drop the items
				mc.playerController.windowClick(container.windowId, -999, 0, 0, player);
			}
		}
	}

	private boolean dragMoveItems(GuiContainer gui) {
		if (gui.mc.thePlayer.inventory.getItemStack() != null) {
			return false;
		}

		boolean leftButtonDown = Mouse.isButtonDown(0);
		boolean rightButtonDown = Mouse.isButtonDown(1);
		boolean isShiftDown = GuiScreen.isShiftKeyDown();
		boolean isControlDown = GuiScreen.isCtrlKeyDown();
		boolean eitherMouseButtonDown = leftButtonDown || rightButtonDown;
		boolean eventKeyIsLeftButton = (Mouse.getEventButton() - 100) == gui.mc.gameSettings.keyBindAttack.getKeyCode();
		boolean eventKeyIsRightButton = (Mouse.getEventButton() - 100) == gui.mc.gameSettings.keyBindUseItem
				.getKeyCode();
		boolean eventButtonState = Mouse.getEventButtonState();

		boolean leaveOneItem = leftButtonDown == false;
		boolean moveOnlyOne = isShiftDown == false;
		int mouseX = Mouse.getEventX() * gui.width / gui.mc.displayWidth;
		int mouseY = gui.height - Mouse.getEventY() * gui.height / gui.mc.displayHeight - 1;
		boolean cancel = false;

		if (eventButtonState == true) {
			if (((eventKeyIsLeftButton || eventKeyIsRightButton) && isControlDown)
					|| (eventKeyIsRightButton && isShiftDown)) {
				// Reset this or the method call won't do anything...
				this.slotNumberLast = -1;
				cancel = this.dragMoveFromSlotAtPosition(gui, mouseX, mouseY, leaveOneItem, moveOnlyOne);
			}
		}

		// Check that either mouse button is down
		if (cancel == false && (isShiftDown == true || isControlDown == true) && eitherMouseButtonDown == true) {
			int distX = mouseX - this.lastPosX;
			int distY = mouseY - this.lastPosY;
			int absX = Math.abs(distX);
			int absY = Math.abs(distY);

			if (absX > absY) {
				int inc = distX > 0 ? 1 : -1;

				for (int x = this.lastPosX;; x += inc) {
					int y = absX != 0 ? this.lastPosY + ((x - this.lastPosX) * distY / absX) : mouseY;
					this.dragMoveFromSlotAtPosition(gui, x, y, leaveOneItem, moveOnlyOne);

					if (x == mouseX) {
						break;
					}
				}
			} else {
				int inc = distY > 0 ? 1 : -1;

				for (int y = this.lastPosY;; y += inc) {
					int x = absY != 0 ? this.lastPosX + ((y - this.lastPosY) * distX / absY) : mouseX;
					this.dragMoveFromSlotAtPosition(gui, x, y, leaveOneItem, moveOnlyOne);

					if (y == mouseY) {
						break;
					}
				}
			}
		}

		this.lastPosX = mouseX;
		this.lastPosY = mouseY;

		// Always update the slot under the mouse.
		// This should prevent a "double click/move" when shift + left clicking on slots
		// that have more
		// than one stack of items. (the regular slotClick() + a "drag move" from the
		// slot that is under the mouse
		// when the left mouse button is pressed down and this code runs).
		Slot slot = this.getSlotAtPosition(gui, mouseX, mouseY);
		this.slotNumberLast = slot != null ? slot.slotNumber : -1;

		if (eitherMouseButtonDown == false) {
			this.draggedSlots.clear();
		}

		return cancel;
	}

	private boolean dragMoveFromSlotAtPosition(GuiContainer gui, int x, int y, boolean leaveOneItem,
			boolean moveOnlyOne) {
		Slot slot = this.getSlotAtPosition(gui, x, y);

		if (slot != null && slot.slotNumber != this.slotNumberLast) {
			if (this.isValidSlot(slot, gui, true) == true) {
				if (this.draggedSlots.contains(slot.slotNumber) == false) {
					if (moveOnlyOne == true) {
						this.tryMoveSingleItemToOtherInventory(slot, gui);
					} else if (leaveOneItem == true) {
						this.tryMoveAllButOneItemToOtherInventory(slot, gui);
					} else {
						this.shiftClickSlot(gui.inventorySlots, gui.mc, slot.slotNumber);
					}

					this.draggedSlots.add(slot.slotNumber);
				}

				// Valid slot, cancel the event to prevent further processing (and thus
				// transferStackInSlot)
				return true;
			}
		}

		return false;
	}

	private Slot getSlotAtPosition(GuiContainer gui, int x, int y) {
		return gui.getSlotAtPosition(x, y);
	}

	private boolean tryMoveItemsVillager(GuiMerchant gui, Slot slot, boolean moveToOtherInventory,
			boolean isShiftDown) {
		if (isShiftDown == true) {
			// Try to fill the merchant's buy slots from the player inventory
			if (moveToOtherInventory == false) {
				this.tryMoveItemsToMerchantBuySlots(gui, true);
			}
			// Move items from sell slot to player inventory
			else if (slot.getHasStack() == true) {
				this.tryMoveStacks(slot, gui, true, true, true);
			}
			// Scrolling over an empty output slot, clear the buy slots
			else {
				this.tryMoveStacks(slot, gui, false, true, false);
			}
		} else {
			// Scrolling items from player inventory into merchant buy slots
			if (moveToOtherInventory == false) {
				this.tryMoveItemsToMerchantBuySlots(gui, false);
			}
			// Scrolling items from this slot/inventory into the other inventory
			else if (slot.getHasStack() == true) {
				this.tryMoveSingleItemToOtherInventory(slot, gui);
			}
		}

		return false;
	}

	private boolean tryMoveItems(GuiContainer gui, boolean scrollingUp) {
		Slot slot = gui.getSlotUnderMouse();
		boolean isCtrlDown = GuiContainer.isCtrlKeyDown();
		boolean isShiftDown = GuiContainer.isShiftKeyDown();
		// Villager handling only happens when scrolling over the trade output slot
		boolean villagerHandling = gui instanceof GuiMerchant && slot instanceof SlotMerchantResult;

		// Check that the cursor is empty, and the slot is valid (don't require items in
		// case of the villager output slot)
		if (gui.mc.thePlayer.inventory.getItemStack() != null
				|| this.isValidSlot(slot, gui, villagerHandling ? false : true) == false) {
			return false;
		}

		boolean moveToOtherInventory = scrollingUp;

		if (villagerHandling) {
			return this.tryMoveItemsVillager((GuiMerchant) gui, slot, moveToOtherInventory, isShiftDown);
		}

		if (isShiftDown == true) {
			// Ctrl + Shift + scroll: move everything
			if (isCtrlDown == true) {
				this.tryMoveStacks(slot, gui, false, moveToOtherInventory, false);
			}
			// Shift + scroll: move one matching stack
			else {
				this.tryMoveStacks(slot, gui, true, moveToOtherInventory, true);
			}
		}
		// Ctrl + scroll: Move all matching stacks
		else if (isCtrlDown == true) {
			this.tryMoveStacks(slot, gui, true, moveToOtherInventory, false);
		}
		// No Ctrl or Shift
		else {
			ItemStack stack = slot.getStack();

			// Scrolling items from this slot/inventory into the other inventory
			if (moveToOtherInventory == true) {
				this.tryMoveSingleItemToOtherInventory(slot, gui);
			}
			// Scrolling items from the other inventory into this slot/inventory
			else if (stack.stackSize < slot.getItemStackLimit(stack)) {
				this.tryMoveSingleItemToThisInventory(slot, gui);
			}
		}

		return false;
	}

	private boolean tryMoveSingleItemToOtherInventory(Slot slot, GuiContainer gui) {
		ItemStack stackOrig = slot.getStack();
		Container container = gui.inventorySlots;

		if (gui.mc.thePlayer.inventory.getItemStack() != null || slot.canTakeStack(gui.mc.thePlayer) == false
				|| (stackOrig.stackSize > 1 && slot.isItemValid(stackOrig) == false)) {
			return false;
		}

		// Can take all the items to the cursor at once, use a shift-click method to
		// move one item from the slot
		if (stackOrig.stackSize <= stackOrig.getMaxStackSize()) {
			return this.clickSlotsToMoveSingleItemByShiftClick(container, gui.mc, slot.slotNumber);
		}

		ItemStack stack = stackOrig.copy();
		stack.stackSize = 1;

		ItemStack[] originalStacks = this.getOriginalStacks(container);

		// Try to move the temporary single-item stack via the shift-click handler
		// method
		slot.putStack(stack);
		container.transferStackInSlot(gui.mc.thePlayer, slot.slotNumber);

		// Successfully moved the item somewhere, now we want to check where it went
		if (slot.getHasStack() == false) {
			int targetSlot = this.getTargetSlot(container, originalStacks);

			// Found where the item went
			if (targetSlot >= 0) {
				// Remove the dummy item from the target slot (on the client side)
				container.inventorySlots.get(targetSlot).decrStackSize(1);

				// Restore the original stack to the slot under the cursor (on the client side)
				this.restoreOriginalStacks(container, originalStacks);

				// Do the slot clicks to actually move the items (on the server side)
				return this.clickSlotsToMoveSingleItem(container, gui.mc, slot.slotNumber, targetSlot);
			}
		}

		// Restore the original stack to the slot under the cursor (on the client side)
		slot.putStack(stackOrig);

		return false;
	}

	private void tryMoveAllButOneItemToOtherInventory(Slot slot, GuiContainer gui) {
		ItemStack stackOrig = slot.getStack().copy();

		if (stackOrig.stackSize == 1 || slot.canTakeStack(gui.mc.thePlayer) == false
				|| slot.isItemValid(stackOrig) == false) {
			return;
		}

		Container container = gui.inventorySlots;

		// Take half of the items in the original slot to cursor
		gui.mc.playerController.windowClick(container.windowId, slot.slotNumber, 1, 0, gui.mc.thePlayer);

		ItemStack stackInCursor = gui.mc.thePlayer.inventory.getItemStack();
		if (stackInCursor == null) {
			return;
		}

		int stackInCursorSizeOrig = stackInCursor.stackSize;
		int tempSlotNum = -1;

		// Find some other slot where to store one of the items temporarily
		for (Slot slotTmp : container.inventorySlots) {
			if (slotTmp.slotNumber != slot.slotNumber && slotTmp.isItemValid(stackInCursor) == true) {
				ItemStack stackInSlot = slotTmp.getStack();

				if (stackInSlot == null || areStacksEqual(stackInSlot, stackInCursor)) {
					// Try to put one item into the temporary slot
					gui.mc.playerController.windowClick(container.windowId, slotTmp.slotNumber, 1, 0, gui.mc.thePlayer);

					stackInCursor = gui.mc.thePlayer.inventory.getItemStack();

					// Successfully stored one item
					if (stackInCursor == null || stackInCursor.stackSize < stackInCursorSizeOrig) {
						tempSlotNum = slotTmp.slotNumber;
						break;
					}
				}
			}
		}

		// Return the rest of the items into the original slot
		gui.mc.playerController.windowClick(container.windowId, slot.slotNumber, 0, 0, gui.mc.thePlayer);

		// Successfully stored one item in a temporary slot
		if (tempSlotNum != -1) {
			// Shift click the stack from the original slot
			this.shiftClickSlot(container, gui.mc, slot.slotNumber);

			// Take half a stack from the temporary slot
			gui.mc.playerController.windowClick(container.windowId, tempSlotNum, 1, 0, gui.mc.thePlayer);

			// Return one item into the original slot
			gui.mc.playerController.windowClick(container.windowId, slot.slotNumber, 1, 0, gui.mc.thePlayer);

			// Return the rest of the items to the temporary slot, if any
			if (gui.mc.thePlayer.inventory.getItemStack() != null) {
				gui.mc.playerController.windowClick(container.windowId, tempSlotNum, 0, 0, gui.mc.thePlayer);
			}
		}
	}

	private void tryMoveSingleItemToThisInventory(Slot slot, GuiContainer gui) {
		Container container = gui.inventorySlots;
		ItemStack stackOrig = slot.getStack();

		if (slot.isItemValid(stackOrig) == false) {
			return;
		}

		for (int slotNum = container.inventorySlots.size() - 1; slotNum >= 0; slotNum--) {
			Slot slotTmp = container.inventorySlots.get(slotNum);

			if (areSlotsInSameInventory(slotTmp, slot) == false && slotTmp.getHasStack() == true
					&& slotTmp.canTakeStack(gui.mc.thePlayer) == true
					&& (slotTmp.getStack().stackSize == 1 || slotTmp.isItemValid(slotTmp.getStack()) == true)) {
				ItemStack stackTmp = slotTmp.getStack();
				if (areStacksEqual(stackTmp, stackOrig) == true) {
					this.clickSlotsToMoveSingleItem(container, gui.mc, slotTmp.slotNumber, slot.slotNumber);
					return;
				}
			}
		}

		// If we weren't able to move any items from another inventory, then try to move
		// items
		// within the same inventory (mostly between the hotbar and the player
		// inventory)
		for (Slot slotTmp : container.inventorySlots) {
			if (slotTmp.slotNumber != slot.slotNumber && slotTmp.getHasStack() == true
					&& slotTmp.canTakeStack(gui.mc.thePlayer) == true
					&& (slotTmp.getStack().stackSize == 1 || slotTmp.isItemValid(slotTmp.getStack()) == true)) {
				ItemStack stackTmp = slotTmp.getStack();
				if (areStacksEqual(stackTmp, stackOrig) == true) {
					this.clickSlotsToMoveSingleItem(container, gui.mc, slotTmp.slotNumber, slot.slotNumber);
					return;
				}
			}
		}
	}

	private void tryMoveStacks(Slot slot, GuiContainer gui, boolean matchingOnly, boolean toOtherInventory,
			boolean firstOnly) {
		Container container = gui.inventorySlots;
		ItemStack stack = slot.getStack();

		for (Slot slotTmp : container.inventorySlots) {
			if (slotTmp.slotNumber != slot.slotNumber && areSlotsInSameInventory(slotTmp, slot) == toOtherInventory
					&& (matchingOnly == false || areStacksEqual(stack, slotTmp.getStack()) == true)) {
				this.shiftClickSlot(container, gui.mc, slotTmp.slotNumber);

				if (firstOnly == true) {
					return;
				}
			}
		}

		// If moving to the other inventory, then move the hovered slot's stack last
		if (toOtherInventory == true) {
			this.shiftClickSlot(container, gui.mc, slot.slotNumber);
		}
	}

	private void tryMoveItemsToMerchantBuySlots(GuiMerchant gui, boolean fillStacks) {
		MerchantRecipeList list = gui.getMerchant().getRecipes(gui.mc.thePlayer);
		int index = 0;

		index = gui.selectedMerchantRecipe;

		if (list == null || list.size() <= index) {
			return;
		}

		MerchantRecipe recipe = list.get(index);
		if (recipe == null) {
			return;
		}

		ItemStack buy1 = recipe.getItemToBuy();
		ItemStack buy2 = recipe.getSecondItemToBuy();

		if (buy1 != null) {
			this.fillBuySlot(gui, 0, buy1, fillStacks);
		}

		if (buy2 != null) {
			this.fillBuySlot(gui, 1, buy2, fillStacks);
		}
	}

	private void fillBuySlot(GuiContainer gui, int slotNum, ItemStack buyStack, boolean fillStacks) {
		Slot slot = gui.inventorySlots.getSlot(slotNum);

		// If there are items not matching the merchant recipe, move them out first
		if (areStacksEqual(buyStack, slot.getStack()) == false) {
			this.shiftClickSlot(gui.inventorySlots, gui.mc, slotNum);
		}

		this.moveItemsFromInventory(gui, slotNum, gui.mc.thePlayer.inventory, buyStack, fillStacks);
	}

	private void moveItemsFromInventory(GuiContainer gui, int slotTo, IInventory invSrc, ItemStack stackTemplate,
			boolean fillStacks) {
		Container container = gui.inventorySlots;

		for (Slot slot : container.inventorySlots) {
			if (slot == null) {
				continue;
			}

			if (slot.inventory == invSrc && areStacksEqual(stackTemplate, slot.getStack()) == true) {
				if (fillStacks == true) {
					if (this.clickSlotsToMoveItems(container, gui.mc, slot.slotNumber, slotTo) == false) {
						break;
					}
				} else {
					this.clickSlotsToMoveSingleItem(container, gui.mc, slot.slotNumber, slotTo);
					break;
				}
			}
		}
	}

	private static boolean areStacksEqual(ItemStack stack1, ItemStack stack2) {
		return ItemStack.areItemsEqual(stack1, stack2) && ItemStack.areItemStackTagsEqual(stack1, stack2);
	}

	private static boolean areSlotsInSameInventory(Slot slot1, Slot slot2) {
		if ((slot1 instanceof SlotItemHandler) && (slot2 instanceof SlotItemHandler)) {
			return ((SlotItemHandler) slot1).itemHandler == ((SlotItemHandler) slot2).itemHandler;
		}

		return slot1.inventory == slot2.inventory;
	}

	private ItemStack[] getOriginalStacks(Container container) {
		ItemStack[] originalStacks = new ItemStack[container.inventorySlots.size()];

		for (int i = 0; i < originalStacks.length; i++) {
			originalStacks[i] = ItemStack.copyItemStack(container.inventorySlots.get(i).getStack());
		}

		return originalStacks;
	}

	private void restoreOriginalStacks(Container container, ItemStack[] originalStacks) {
		for (int i = 0; i < originalStacks.length; i++) {
			ItemStack stackSlot = container.getSlot(i).getStack();
			if (areStacksEqual(stackSlot, originalStacks[i]) == false || (stackSlot != null && originalStacks[i] != null
					&& stackSlot.stackSize != originalStacks[i].stackSize)) {
				container.putStackInSlot(i, originalStacks[i]);
			}
		}
	}

	private int getTargetSlot(Container container, ItemStack[] originalStacks) {
		List<Slot> slots = container.inventorySlots;

		for (int i = 0; i < originalStacks.length; i++) {
			ItemStack stackOrig = originalStacks[i];
			ItemStack stackNew = slots.get(i).getStack();

			if ((stackOrig == null && stackNew != null)
					|| (stackOrig != null && stackNew != null && stackNew.stackSize == (stackOrig.stackSize + 1))) {
				return i;
			}
		}

		return -1;
	}

	private void shiftClickSlot(Container container, Minecraft mc, int slot) {
		mc.playerController.windowClick(container.windowId, slot, 0, 1, mc.thePlayer);
	}

	private boolean clickSlotsToMoveSingleItem(Container container, Minecraft mc, int slotFrom, int slotTo) {
		// System.out.println("clickSlotsToMoveSingleItem(from: " + slotFrom + ", to: "
		// + slotTo + ")");

		ItemStack stack = container.inventorySlots.get(slotFrom).getStack();
		if (stack == null) {
			return false;
		}

		EntityPlayer player = mc.thePlayer;

		// Click on the from-slot to take items to the cursor - if there is more than
		// one item in the from-slot,
		// right click on it, otherwise left click.
		mc.playerController.windowClick(container.windowId, slotFrom, stack.stackSize > 1 ? 1 : 0, 0, player);

		// Right click on the target slot to put one item to it
		mc.playerController.windowClick(container.windowId, slotTo, 1, 0, player);

		// If there are items left in the cursor, then return them back to the original
		// slot
		if (player.inventory.getItemStack() != null) {
			// Left click again on the from-slot to return the rest of the items to it
			mc.playerController.windowClick(container.windowId, slotFrom, 0, 0, player);
		}

		return true;
	}

	private boolean clickSlotsToMoveSingleItemByShiftClick(Container container, Minecraft mc, int slotFrom) {
		EntityPlayer player = mc.thePlayer;
		ItemStack stack = container.inventorySlots.get(slotFrom).getStack();

		if (stack.stackSize > 1) {
			// Left click on the from-slot to take all the items to the cursor
			mc.playerController.windowClick(container.windowId, slotFrom, 0, 0, player);

			// Still items left in the slot, put the stack back and abort
			if (container.inventorySlots.get(slotFrom).getHasStack()) {
				mc.playerController.windowClick(container.windowId, slotFrom, 0, 0, player);
				return false;
			} else {
				// Right click one item back to the slot
				mc.playerController.windowClick(container.windowId, slotFrom, 1, 0, player);
			}
		}

		// ... and then shift-click on the slot
		this.shiftClickSlot(container, mc, slotFrom);

		if (player.inventory.getItemStack() != null) {
			// ... and then return the rest of the items
			mc.playerController.windowClick(container.windowId, slotFrom, 0, 0, player);
		}

		return true;
	}

	/**
	 * Try move items from slotFrom to slotTo
	 * 
	 * @return true if at least some items were moved
	 */
	private boolean clickSlotsToMoveItems(Container container, Minecraft mc, int slotFrom, int slotTo) {
		EntityPlayer player = mc.thePlayer;
		// System.out.println("clickSlotsToMoveItems(from: " + slotFrom + ", to: " +
		// slotTo + ")");

		// Left click to take items
		mc.playerController.windowClick(container.windowId, slotFrom, 0, 0, player);

		// Couldn't take the items, bail out now
		if (player.inventory.getItemStack() == null) {
			return false;
		}

		boolean ret = true;
		int size = player.inventory.getItemStack().stackSize;

		// Left click on the target slot to put the items to it
		mc.playerController.windowClick(container.windowId, slotTo, 0, 0, player);

		// If there are items left in the cursor, then return them back to the original
		// slot
		if (player.inventory.getItemStack() != null) {
			ret = player.inventory.getItemStack().stackSize != size;

			// Left click again on the from-slot to return the rest of the items to it
			mc.playerController.windowClick(container.windowId, slotFrom, 0, 0, player);
		}

		return ret;
	}
}
