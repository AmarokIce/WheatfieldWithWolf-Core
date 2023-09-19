package club.someoneice.www.util;

import club.someoneice.pineapplepsychic.command.IPineappleConfig;
import club.someoneice.pineapplepsychic.config.ConfigBeanJson5;
import club.someoneice.www.WWWMain;

public class Config implements IPineappleConfig {
    public ConfigBeanJson5 bean = new ConfigBeanJson5(WWWMain.MODID, this);

    @Override
    public void init() {

    }
}
