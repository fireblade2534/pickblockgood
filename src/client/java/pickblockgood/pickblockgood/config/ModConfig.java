package pickblockgood.pickblockgood.config;


import java.util.Arrays;
import java.util.List;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;

@Environment(EnvType.CLIENT)
@Config(name="pickblockgood")
public class ModConfig implements ConfigData{
    @ConfigEntry.Gui.Tooltip()
    private String doNotReplaceItemTags="swords, tools";

    public boolean preventReplaceInCreative=false;

    public List<String> doNotReplaceItemTagsList(){
        List<String> splitText=Arrays.asList(doNotReplaceItemTags.split("\\s*,\\s*"));
        return splitText;
    }
}
