package binnie.genetics.genetics;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import binnie.genetics.api.IGene;

public class SequencerItem extends GeneItem {

    public int sequenced;
    public boolean analysed;

    public SequencerItem(ItemStack stack) {
        super(stack);
    }

    public SequencerItem(IGene gene) {
        super(gene);
        sequenced = 0;
        analysed = false;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        sequenced = nbt.getByte("seq");
        analysed = nbt.getBoolean("ana");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setByte("seq", (byte) sequenced);
        nbt.setBoolean("ana", analysed);
    }
}
