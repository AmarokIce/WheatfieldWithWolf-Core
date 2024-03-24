package club.someoneice.www.network;

import club.someoneice.www.WWWMain;
import club.someoneice.www.network.message.AbstractMessageKey;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

import java.util.List;
import java.util.Map;

public class RawPackageHandle implements IMessage {
    static final List<Class<? extends AbstractMessageKey<?>>> mapping = Lists.newArrayList();

    AbstractMessageKey<?> messageKey;

    public RawPackageHandle(AbstractMessageKey<?> messagePackage) {
        this.messageKey = messagePackage;
    }

    public static void registerPackage(Class<? extends AbstractMessageKey<?>> clazz) {
        mapping.add(clazz);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        if (!mapping.contains(this.messageKey.getClass())) return;

        int id = mapping.indexOf(this.messageKey.getClass());
        buf.writeInt(id);
        this.messageKey.writeTo(buf);

    }

    @Override
    public void toBytes(ByteBuf buf) {
        int id = buf.readInt();
        if (id < 0 || id > mapping.size()) return;
        Class<? extends AbstractMessageKey<?>> clazz = mapping.get(id);
        try {
            this.messageKey = clazz.newInstance();
            this.messageKey.readFrom(buf);
        } catch (InstantiationException | IllegalAccessException e) {
            WWWMain.LOG.error(e);
        }
    }
}
