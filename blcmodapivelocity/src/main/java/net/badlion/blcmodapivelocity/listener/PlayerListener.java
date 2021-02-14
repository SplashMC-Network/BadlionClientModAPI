package net.badlion.blcmodapivelocity.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import net.badlion.blcmodapivelocity.BlcModApiVelocity;

public class PlayerListener {

    private final BlcModApiVelocity plugin;

    public PlayerListener(BlcModApiVelocity plugin) {
        this.plugin = plugin;
    }

    @Subscribe
    public void onLogin(PostLoginEvent event) {
        final Player player = event.getPlayer();

        player.sendPluginMessage(
                MinecraftChannelIdentifier.create("badlion", "mods"),
                BlcModApiVelocity.GSON_NON_PRETTY.toJson(this.plugin.getConf().getModsDisallowed()).getBytes()
        );
    }

}
