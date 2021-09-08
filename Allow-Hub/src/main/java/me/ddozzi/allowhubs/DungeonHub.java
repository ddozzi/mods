package me.ddozzi.allowhubs;

import club.sk1er.mods.core.ModCoreInstaller;
import me.ddozzi.allowhubs.commands.DungeonHubCommand;
import me.ddozzi.allowhubs.config.Config;
import me.ddozzi.allowhubs.config.ConfigCommand;
import me.ddozzi.allowhubs.listener.ChatListener;
import me.ddozzi.allowhubs.proxy.CommonProxy;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

@Mod(modid = "ahub")

public class DungeonHub {
	

    public static DungeonHub instance;
    public static Config config = new Config();
    
    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public static void preInit(FMLPostInitializationEvent e) {
    }
    
    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
    	ModCoreInstaller.initializeModCore(Minecraft.getMinecraft().mcDataDir);
        config.preload();
       
    	 ClientCommandHandler.instance.registerCommand(new DungeonHubCommand());
    	 ClientCommandHandler.instance.registerCommand(new ConfigCommand());
    	 MinecraftForge.EVENT_BUS.register(new ChatListener());

    }

    @EventHandler
    public static void postInit(FMLPostInitializationEvent e) {
    	
    	
    	}

    }