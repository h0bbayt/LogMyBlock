package me.darksnakex.blocktracker;


import com.mojang.brigadier.CommandDispatcher;
import me.darksnakex.blocktracker.Commands.BlockInspectCommand;
import me.darksnakex.blocktracker.Commands.BlockLookupCommand;
import me.darksnakex.blocktracker.Events.BlockEventHandler;
import me.darksnakex.blocktracker.Events.InventoryEventHandler;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static me.darksnakex.blocktracker.BlockEventSaveData.loadBlockEvents;
import static me.darksnakex.blocktracker.BlockEventSaveData.saveBlockEvents;
import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



@Mod(Logmyblock.MODID)
public class Logmyblock {
    public static String prefix = "§e[§3Log§aMy§6Block§e] §f";
    public static final String MODID = "logmyblock";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public Logmyblock(){
        EVENT_BUS.register(new BlockEventHandler());
        EVENT_BUS.register(new InventoryEventHandler());
        EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onServerStarted(ServerStartedEvent event) {
        loadBlockEvents();
    }

    @SubscribeEvent
    public void onServerStopping(ServerStoppingEvent event) {
        saveBlockEvents();
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        new BlockInspectCommand(dispatcher);
        new BlockLookupCommand(dispatcher);
    }
}
