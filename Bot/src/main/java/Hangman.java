// Class to store all the game methods that the bot will interact with
public class Hangman {

    static String prompt;

    // Regex filter that is added to when guesses are made
    static String filter = " ,\\,,\\.,!,\\?";

    // Keeps track of if there's an active game
    private static boolean activeGame = false;

    // Sets active game to true and receives the prompt that was input
    public static void start(String inputPrompt){
            activeGame = true;
            prompt = inputPrompt;
    }

    // Checks the active game status
    public static boolean getActiveGameStatus(){
        return activeGame;
    }

    // Ends the game and resets the prompt and filter
    public static void end(){
        prompt = "";
        filter = " ,\\,,\\.,!,\\?";
        activeGame = false;
    }

    // Adds the letter guessed to the filter
    public static void addToFilter(String guess){
        String addedCase = guess.charAt(0) + ",";
        filter += addedCase;
    }

    // Returns the prompt with un-guessed letters hidden
    public static String returnHiddenPrompt(String prompt) {
        String hidden;

        // Sets up the regex filter
        String regex = "[^" + filter + "]";

        // Replaces all characters not in the filter with dashes then puts a space between the dashes
        hidden = prompt.replaceAll(regex, "-").replaceAll(".(?=.)", "$0 ");

        return hidden;
    }

    // Compares the guess to the prompt and returns true or false
    public static boolean solvePrompt(String solve) {
        System.out.println(prompt);
        System.out.println(solve.equals(prompt));

        return solve.equals(prompt);
    }

    // Returns the prompt
    public static String returnPrompt() {
        return prompt;
    }
}
