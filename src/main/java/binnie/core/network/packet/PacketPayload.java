package binnie.core.network.packet;

import java.util.ArrayList;
import java.util.List;

public class PacketPayload {

    public List<Integer> intPayload;
    public List<Float> floatPayload;
    public List<String> stringPayload;

    public PacketPayload() {
        intPayload = new ArrayList<>();
        floatPayload = new ArrayList<>();
        stringPayload = new ArrayList<>();
    }

    public void addInteger(int a) {
        intPayload.add(a);
    }

    public void addFloat(float a) {
        floatPayload.add(a);
    }

    public void addString(String a) {
        stringPayload.add(a);
    }

    public int getInteger() {
        return intPayload.remove(0);
    }

    public float getFloat() {
        return floatPayload.remove(0);
    }

    public String getString() {
        return stringPayload.remove(0);
    }

    public boolean isEmpty() {
        return intPayload.isEmpty() && floatPayload.isEmpty() && stringPayload.isEmpty();
    }
}
