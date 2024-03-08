package efs.task.syntax;
import java.lang.IllegalArgumentException;
import java.util.Scanner;

public class GuessNumberGame {
    int m, l, numberToGuess;

    //Do not modify main method
    public static void main(String[] args) {
        try {
            GuessNumberGame game = new GuessNumberGame(args.length > 0 ? args[0] : "");
            game.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public GuessNumberGame(String argument) {
        try {
            m = Integer.parseInt(argument);
        } catch (NumberFormatException e) {
            onArgumentError(argument, " - nie można przekonwertować na typ int");
        }
        if(m > UsefulConstants.MAX_UPPER_BOUND || m < 1) onArgumentError(argument, " - jest mnijesza od 1 lub większa niż " + UsefulConstants.MAX_UPPER_BOUND);
        l = (int)(Math.log(m) / Math.log(2)) + 1;
        numberToGuess = (int)(Math.random() * m + 1);
    }

    private void onArgumentError(String argument, String additionalText) {
        System.out.println("Wartość " + argument + " jest niepoprawna: " + UsefulConstants.WRONG_ARGUMENT + additionalText);
        throw new IllegalArgumentException();
    }

    public void play() {
        boolean success = false;
        int guess = -1;

        Scanner input = new Scanner(System.in);

        System.out.println("Zgadnij liczbę z zakresu: <1,"+m+">");


        for (int attempt = 1; attempt <= l; attempt++){
            drawProgressBar(attempt);

            System.out.println(UsefulConstants.GIVE_ME);
            String guessText = input.next();

            try {
                guess = Integer.parseInt(guessText);
            } catch (NumberFormatException e) {
                System.out.println(UsefulConstants.NOT_A_NUMBER);
                continue;
            }

            if (guess == numberToGuess){
                System.out.println(UsefulConstants.YES);
                // zrobiłbym to tak, żeby nie robić niepotrzebnego breaka i flagi, ale patrząc na opis rozwiązanie z
                // breakiem i flagą bardziej odpowiada
//                System.out.println(UsefulConstants.CONGRATULATIONS);
//                return;
                success = true;
                break;
            }

            if ( guess < numberToGuess){
                System.out.println(UsefulConstants.TO_LESS);
            } else {
                System.out.println(UsefulConstants.TO_MUCH);
            }
        }

        if (success){
            System.out.println(UsefulConstants.CONGRATULATIONS);
        } else {
            System.out.println(UsefulConstants.UNFORTUNATELY);
        }
    }

    private void drawProgressBar(int attempt) {
        System.out.print('[');
        for (int i = 0; i < attempt; i++){
            System.out.print('*');
        }
        for (int i = attempt; i < l; i++){
            System.out.print('.');
        }
        System.out.println(']');
    }
}
