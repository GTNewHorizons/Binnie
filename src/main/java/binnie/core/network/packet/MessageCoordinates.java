package binnie.core.network.packet;

import java.io.IOException;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import io.netty.buffer.ByteBuf;

public class MessageCoordinates extends MessageBase {

    public int posX;
    public int posY;
    public int posZ;

    public MessageCoordinates(MessageBinnie message) {
        super(message);
    }

    public MessageCoordinates(int id, int posX, int posY, int posZ) {
        super(id);
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    @Override
    public void writeData(ByteBuf data) throws IOException {
        data.writeInt(posX);
        data.writeInt(posY);
        data.writeInt(posZ);
    }

    @Override
    public void readData(ByteBuf data) throws IOException {
        posX = data.readInt();
        posY = data.readInt();
        posZ = data.readInt();
    }

    public TileEntity getTileEntity(World world) {
        return world.getTileEntity(posX, posY, posZ);
    }
}
