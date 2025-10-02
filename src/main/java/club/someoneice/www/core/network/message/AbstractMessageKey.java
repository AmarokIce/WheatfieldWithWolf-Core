package club.someoneice.www.core.network.message;

import club.someoneice.www.core.network.RawPackageHandle;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public abstract class AbstractMessageKey<T extends AbstractMessageKey<?>> {
  abstract public IMessage onMessage(RawPackageHandle message, MessageContext ctx);

  abstract public void writeTo(ByteBuf buf);

  abstract public void readFrom(ByteBuf buf);
}
