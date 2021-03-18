package net.badlion.blcmodapivelocity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import net.badlion.blcmodapivelocity.listener.PlayerListener;
import org.slf4j.Logger;

import java.io.*;

@Plugin(id = "badlionclientmodapi", name = "BadlionClientModAPI", version = "1.0", authors = {"Badlion"})
public class BlcModApiVelocity {


    public static final Gson GSON_NON_PRETTY = new GsonBuilder()
            .enableComplexMapKeySerialization()
            .disableHtmlEscaping()
            .create();
    public static final Gson GSON_PRETTY = new GsonBuilder()
            .enableComplexMapKeySerialization()
            .disableHtmlEscaping()
            .setPrettyPrinting()
            .create();

    private final ProxyServer server;
    private final Logger logger;
    private final File dataDirectory;

    private Conf conf;

    @Inject
    public BlcModApiVelocity(ProxyServer server, Logger logger) {
        this.server = server;
        this.logger = logger;
        this.dataDirectory = new File("plugins/BadlionClientModAPI");
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        if (!(this.dataDirectory.exists()) && !(this.dataDirectory.mkdirs())) {
            logger.error("Failed to create plugin directory.");
        }

        try {
            conf = this.loadConf(new File(dataDirectory, "config.json"));
            this.server.getEventManager().register(this, new PlayerListener(this));

            logger.info("Successfully setup BadlionClientModAPI plugin.");
        } catch (IOException e) {
            logger.error("Error with config for BadlionClientModAPI plugin.");
            e.printStackTrace();
        }
    }

    public Conf getConf() {
        return conf;
    }

    private Conf loadConf(File file) throws IOException {
        try (Reader reader = new BufferedReader(new FileReader(file))) {
            return BlcModApiVelocity.GSON_NON_PRETTY.fromJson(reader, Conf.class);
        } catch (FileNotFoundException ex) {
            logger.info("No Config Found: Saving default... (" + file.toString() + ")");
            Conf conf = new Conf();
            this.saveConf(conf, new File(this.dataDirectory, "config.json"));
            return conf;
        }
    }

    private void saveConf(Conf conf, File file) {
        try (FileWriter writer = new FileWriter(file)) {
            BlcModApiVelocity.GSON_PRETTY.toJson(conf, writer);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
