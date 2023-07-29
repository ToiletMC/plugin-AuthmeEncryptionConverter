package net.toiletmc.aec;

import fr.xephi.authme.api.v3.AuthMeApi;
import fr.xephi.authme.events.PasswordEncryptionEvent;
import fr.xephi.authme.security.PasswordSecurity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class AEC extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(this, this);
        AuthMeApi authMeApi = AuthMeApi.getInstance();
        try {
            Class<?> clazz = authMeApi.getClass();
            Field privateField = clazz.getDeclaredField("passwordSecurity");
            privateField.setAccessible(true);
            PasswordSecurity fieldValue = (PasswordSecurity) privateField.get(authMeApi);
            Method publicReloadMethod = fieldValue.getClass().getMethod("reload");
            publicReloadMethod.invoke(fieldValue);
            getLogger().info("Successfully inject authme!");
        } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
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
