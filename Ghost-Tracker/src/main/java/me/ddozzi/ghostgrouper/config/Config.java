package me.ddozzi.ghostgrouper.config;

import club.sk1er.vigilance.Vigilant;
import club.sk1er.vigilance.data.Property;
import club.sk1er.vigilance.data.PropertyType;

import java.io.File;

public class Config extends Vigilant {

    @Property(
            type = PropertyType.SLIDER,
            name = "Radius of Ghost Search",
            category = "General",
            subcategory = "General",
            description = "Increases or decreases the range in which ghosts are searched for.",
            max = 30
    )
    public int GHOST_RADIUS = 10;


    @Property(
            type = PropertyType.SWITCH,
            name = "Cluster Health",
            category = "General",
            subcategory = "General",
            description = "Shows the health of a cluster of ghosts."
    )
    public boolean DISPLAY_HEALTH = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "Ghost count",
            category = "General",
            subcategory = "General",
            description = "Displays the amount of ghosts in a cluster."
    )
    public boolean COUNT_GHOST = false;

    @Property(
            type = PropertyType.SLIDER,
            name = "Hitbox Color",
            category = "General",
            subcategory = "General",
            description = "Changes the Hitbox's color",
            max = 100
    )
    public int HITBOX_COLOR = 5;

    @Property(
            type = PropertyType.SLIDER,
            name = "Text Color",
            category = "General",
            subcategory = "General",
            description = "Changes the color of the healthh display",
            max = 100
    )
    public int TEXT_COLOR = 5;


    @Property(
            type = PropertyType.SWITCH,
            name = "Disable Depth mask",
            category = "Developer Options",
            subcategory = "General",
            description = "Disables the depth mask for the hitboxes"
    )
    public boolean DISABLE_DEPTH_MASK = true;

    @Property(
            type = PropertyType.SWITCH,
            name = "Show Individual Hitboxes",
            category = "Developer Options",
            subcategory = "General",
            description = "Renders hitboxes on each ghost."
    )
    public boolean INDIV_HITBOXES = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "Show Your Hitbox",
            category = "Developer Options",
            subcategory = "General",
            description = "Renders your ghost detection box"
    )
    public boolean USER_HITBOX = false;

    @Property(
            type = PropertyType.SLIDER,
            name = "User Hitbox Color",
            category = "Developer Options",
            subcategory = "General",
            description = "Changes your hitbox color.",
            max = 100
    )
    public int USER_COLOR = 10;

    public Config() {
        super(new File("./config/ghostconfig.toml"));
        initialize();
    }
}