package club.someoneice.www.network;

import club.someoneice.www.WWWMain;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class SimpleNetWorkHandler {
    public static final SimpleNetworkWrapper INSTANCE;

    public static void init() {
        INSTANCE.registerMessage(Handler.class, RawPackageHandle.class, 0, Side.CLIENT);
        INSTANCE.registerMessage(Handler.class, RawPackageHandle.class, 0, Side.SERVER);
    }

    static {
        INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(WWWMain.MODID);
    }

    public static final class Handler implements IMessageHandler<RawPackageHandle, IMessage> {
        @Override
        public IMessage onMessage(RawPackageHandle message, final MessageContext ctx) {
            return message.messageKey.onMessage(message, ctx);
        }
    }
}
