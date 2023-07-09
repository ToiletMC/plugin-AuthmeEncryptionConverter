package net.toiletmc.aec;

import fr.xephi.authme.events.PasswordEncryptionEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class AEC extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onPwdEncryption(PasswordEncryptionEvent event) {
        event.setMethod(new SaltedSha512Twice());
    }
}
