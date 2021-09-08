package me.ddozzi.allowhubs.config;

import club.sk1er.vigilance.Vigilant;
import club.sk1er.vigilance.data.Property;
import club.sk1er.vigilance.data.PropertyType;

import java.io.File;

public class Config extends Vigilant {

    @Property(
        type = PropertyType.SLIDER,
        name = "Delay",
        category = "General",
        subcategory = "General",
        description = "Select the amount of delay you want between the warps (miliseconds) - this includes auto warp",
        max = 5000
    )
    public int delay = 0;
    
    @Property(
            type = PropertyType.SELECTOR,
            name = "Warp Choice",
            category = "General",
            subcategory = "General",
            description = "Select where you would like to warp to - this includes auto warp",
            options = {"Skyblock Hub", "Dungeon Hub", "Private Island"}
        )
        public int warpchoice = 0;
    
    @Property(
            type = PropertyType.SWITCH,
            name = "Auto Warp",
            category = "Auto Warp",
            subcategory = "Auto Warp",
            description = "Toggle Auto Warp (warps you to specified area automatically after dying)"
        )
        public boolean autowarp = false;
    
    @Property(
            type = PropertyType.SLIDER,
            name = "Auto Warp Delay",
            category = "Auto Warp",
            subcategory = "Auto Warp",
            description = "Select the amount of delay you want between the death and warp",
            max = 5000
        )
        public int autowarpdelay = 0;
    
    public Config() {
        super(new File("./config/allowhub.toml"));
        initialize();
    }
}