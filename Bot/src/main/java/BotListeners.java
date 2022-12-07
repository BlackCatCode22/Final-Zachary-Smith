import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.channel.ChannelDeleteEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class BotListeners extends ListenerAdapter {
    // Listens for messages sent in the discord
    // Deletes the user's inputted hangman command so that the prompt is hidden
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        super.onMessageReceived(event);

        if (!event.getAuthor().isBot()) {

            if (event.getMessage().getContentDisplay().contains("hangman")) {
                event.getMessage().delete();
            }
        }

    }
}
