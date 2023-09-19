package club.someoneice.www.network;

import club.someoneice.www.network.message.AbstractMessageKey;
import com.google.common.collect.Maps;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

import java.util.Map;

public class RawPackageHandle implements IMessage {
    private static int id = 0;
    static final Map<Integer, Class<? extends AbstractMessageKey<?>>> IdMapping = Maps.newHashMap();
    static final Map<Class<? extends AbstractMessageKey<?>>, Integer> PackageMapping = Maps.newHashMap();

    AbstractMessageKey<?> messageKey;

    public RawPackageHandle(AbstractMessageKey<?> messagePackage) {
        this.messageKey = messagePackage;
    }

    public static void registerPackage(Class<? extends AbstractMessageKey<?>> clazz) {
        IdMapping.put(id, clazz);
        PackageMapping.put(clazz, id++);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        if (PackageMapping.containsKey(this.messageKey.getClass())) {
            int id = PackageMapping.get(this.messageKey.getClass());
            buf.writeInt(id);
            this.messageKey.writeTo(buf);
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        int id = buf.readInt();
        if (IdMapping.containsKey(id)) {
            Class<? extends AbstractMessageKey<?>> clazz = IdMapping.get(id);
            try {
                this.messageKey = clazz.newInstance();
                this.messageKey.readFrom(buf);
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
