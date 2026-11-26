package binnie.core.machines;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemMachine extends ItemBlock {

    private final IBlockMachine associatedBlock;

    public ItemMachine(Block block) {
        super(block);
        setMaxDamage(0);
        setHasSubtypes(true);
        associatedBlock = (IBlockMachine) block;
    }

    @Override
    public int getMetadata(int i) {
        return i;
    }

    @Override
    public String getItemStackDisplayName(ItemStack itemstack) {
        return associatedBlock.getMachineName(itemstack.getItemDamage());
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        final MachinePackage pkg = associatedBlock.getPackage(stack.getItemDamage());
        if (pkg != null) {
            pkg.addTooltip(tooltip);
        }
    }
}
