// Zachary Smith
// CIT-63 Final Project
// Hangman Java Discord Bot
// built with JDA

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

public class DiscordBot extends ListenerAdapter {

    public static void main(String[] args) throws LoginException, InterruptedException {

        // Create the bot and initialize it with intent permissions and event listeners
        JDA bot = JDABuilder.createDefault(
                "BOT-TOKEN-GOES-HERE",
                        GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.MESSAGE_CONTENT,
                        GatewayIntent.GUILD_MEMBERS
                        )
                .addEventListeners(new BotListeners())
                .addEventListeners(new BotCommands())
                .build().awaitReady();

        // Initialize the hangman game class
        Hangman hangman = new Hangman();

        // Sets the guild (server) that the bot will operate in
        Guild guild = bot.getGuildById("SERVER-ID-GOES-HERE");

        // Add guild commands if the guild exists
        if(guild != null){
            // Hangman command, takes a string input
            guild.upsertCommand("hangman", "Play a game of hangman with friends")
                    .addOption(OptionType.STRING, "prompt", "Word or phrase to guess", true)
                    .queue();
            // Guess command, takes a string input
            guild.upsertCommand("guess", "Guess a letter").addOption(OptionType.STRING, "guess", "Letter to guess", true).queue();
            // Solve command, takes a string input
            guild.upsertCommand("solve", "Solve the word or phrase").addOption(OptionType.STRING, "solve", "Solve the word or phrase", true).queue();
            // End command, ends the game
            guild.upsertCommand("end", "End the game").queue();
        }

    }
}