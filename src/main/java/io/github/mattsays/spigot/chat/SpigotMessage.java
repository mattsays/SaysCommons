package io.github.mattsays.spigot.chat;

import de.themoep.minedown.adventure.MineDown;
import io.github.mattsays.commons.Message;
import net.kyori.adventure.audience.MessageType;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;

public class SpigotMessage extends Message {

    private Component chatComponent;
    private BukkitAudiences audiences;

    public SpigotMessage(String message, BukkitAudiences audiences) {
        super(message);
        this.chatComponent = new MineDown(this.message).toComponent();
        this.audiences = audiences;
    }

    public SpigotMessage(String configPath, Object config, BukkitAudiences audiences) {
        super(configPath, config);
        this.audiences = audiences;
    }

    @Override
    public Message resolvePlaceholder(String placeHolder, String replacement) {
        return new SpigotMessage(this.message.replace(placeHolder, replacement), audiences);
    }

    @Override
    public void loadFromConfig(Object config) {
        this.message = ((Configuration) config).getString(configPath, CANNOT_READ_DATA + configPath);
    }

    @Override
    public Message send(Object receiver) {
        audiences.sender((CommandSender) receiver).sendMessage(this.chatComponent, MessageType.SYSTEM);
        return this;
    }
}
