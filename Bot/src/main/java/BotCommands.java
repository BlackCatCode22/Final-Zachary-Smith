import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

// Class that holds the event listener for the bot commands
public class BotCommands extends ListenerAdapter {

    // Listens for a slash command
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        super.onSlashCommandInteraction(event);

        // Command "/hangman" starts the game
        if(event.getName().equals("hangman")){
            // Bot waits to reply after executing the rest of the commands
            event.deferReply().queue();
            // Gets the prompt input
            OptionMapping prompt = event.getOption("prompt");

            // Only execute if there is no active game
            if(!Hangman.getActiveGameStatus()) {
                // Start the game and pass in the prompt
                Hangman.start(prompt.getAsString());
                // Send a message that the game is started
                event.getHook().sendMessage("A game of hangman has started! Be the first one to guess correctly!").queue();
                // Send a message of the hidden prompt
                event.getHook().sendMessage(Hangman.returnHiddenPrompt(Hangman.prompt)).queue();
            } else {
                // Send if no active game
                event.getHook().sendMessage("Error! A game is already in progress!").queue();
            }
        }

        // Command "/guess" allows you to guess letters
        else if(event.getName().equals("guess")){
            event.deferReply().queue();

            // Get the user who entered the guess. Use their nickname if one is set, or their name if not
            String user = (event.getMember().getNickname() != null) ? event.getMember().getNickname() : event.getUser().getName();

            // Get the guess
            OptionMapping guess = event.getOption("guess");

            // Only execute if there's an active game
            if(Hangman.getActiveGameStatus()){
                // add the guessed letter to the filter
                Hangman.addToFilter(guess.getAsString());
                // Send a message with the user and the guess
                event.getHook().sendMessage(user + " has guessed the letter " + guess.getAsString()).queue();
                // Resend the updated hidden prompt
                event.getHook().sendMessage(Hangman.returnHiddenPrompt(Hangman.prompt)).queue();
            } else {
                event.getHook().sendMessage("There is not a current game. Please start a game to guess!").queue();
            }

        }

        // Command "/solve" allows the user to solve the prompt
        else if(event.getName().equals("solve")){
            event.deferReply().queue();

            String user = (event.getMember().getNickname() != null) ? event.getMember().getNickname() : event.getUser().getName();

            // Get solve input
            OptionMapping solve = event.getOption("solve");

            // Only executes on an active game
            if(Hangman.getActiveGameStatus()){
                // Compare the solve string to the prompt
                if(Hangman.solvePrompt(solve.getAsString())){
                    // Send win message
                    event.getHook().sendMessage(user + " has guessed correctly! " + user + " wins!").queue();
                    // Show the prompt once guessed
                    event.getHook().sendMessage("The prompt was: " + Hangman.returnPrompt()).queue();
                    // End the game
                    Hangman.end();
                }
                else {
                    // Incorrect guess message
                    event.getHook().sendMessage(user + " has guessed incorrectly!").queue();
                }

            } else {
                event.getHook().sendMessage("There is not a current game. Please start a game to guess!").queue();
            }
        }

        // Command "/end" ends the game and displays the un-guessed prompt
        else if(event.getName().equals("end")){
            event.deferReply().queue();

            if(Hangman.getActiveGameStatus()){
                event.getHook().sendMessage("The game of hangman has ended!").queue();
                event.getHook().sendMessage("Prompt not guessed: " + Hangman.returnPrompt()).queue();
                Hangman.end();
            } else {
                event.getHook().sendMessage("There is not a current game!").queue();
            }

        }

    }
}
