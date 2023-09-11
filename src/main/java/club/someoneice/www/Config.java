package club.someoneice.www;

import club.someoneice.pineapplepsychic.command.IPineappleConfig;
import club.someoneice.pineapplepsychic.config.ConfigBeanJson5;

public class Config implements IPineappleConfig {
    public ConfigBeanJson5 bean = new ConfigBeanJson5(WWWMain.MODID, this);

    @Override
    public void init() {

    }
}
