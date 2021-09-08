package com.ddozzi.lastseen;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import com.ddozzi.lastseen.commands.LastseenCommand;
import com.ddozzi.lastseen.listener.ChatListener;
import com.ddozzi.lastseen.proxy.CommonProxy;

import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Mod(modid = "lastseenmod")

public class Lastseen {
	
    @Instance
    public static Lastseen instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public static void preInit(FMLPostInitializationEvent e) {
    }
    
    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
    	//MinecraftForge.EVENT_BUS.register(this);
    	 ClientCommandHandler.instance.registerCommand(new LastseenCommand());
    	 MinecraftForge.EVENT_BUS.register(new ChatListener());
    }

    @EventHandler
    public static void postInit(FMLPostInitializationEvent e) {
    	
    	
    	}

    }
    

