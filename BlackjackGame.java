import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class BlackjackGame {
    private static final int MAX_SCORE = 21;
    private static final int DEALER_STAND_SCORE = 17;
    private static final int INITIAL_CARDS = 2;
    private static final List<String> CARD_SUITS = Arrays.asList("Hearts", "Spades", "Clubs", "Diamonds");
    private static final List<String> CARD_VALUES = Arrays.asList("2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace");

    private Random random;
    private Scanner scanner;

    public BlackjackGame() {
        random = new Random();
        scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        BlackjackGame game = new BlackjackGame();
        game.start();
    }

    public void start() {
        System.out.print("Enter the number of players: ");
        int numPlayers = scanner.nextInt();

        System.out.println("Starting game with " + numPlayers + " players.");
        List<Player> players = createPlayers(numPlayers);
        Deck deck = createDeck();
        deck.shuffle();

        dealInitialCards(players, deck);

        playGame(players, deck);

        printResults(players);
    }

    private List<Player> createPlayers(int numPlayers) {
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < numPlayers; i++) {
            players.add(new Player(i + 1));
        }
        return players;
    }

    private Deck createDeck() {
        Deck deck = new Deck();
        for (String suit : CARD_SUITS) {
            for (String value : CARD_VALUES) {
                deck.addCard(new Card(value, suit));
            }
        }
        return deck;
    }

    private void dealInitialCards(List<Player> players, Deck deck) {
        for (Player player : players) {
            for (int i = 0; i < INITIAL_CARDS; i++) {
                Card card = deck.dealCard();
                player.addCard(card);
            }
            System.out.println("Dealing to player " + player.getId() + ", cards: " + player.getHandAsString());
        }

        Card dealerCard = deck.dealCard();
        players.get(players.size() - 1).addCard(dealerCard);
        System.out.println("Dealing to computer, card:  face down");
    }

    private void playGame(List<Player> players, Deck deck) {
        for (Player player : players) {
            if (player.isDealer())
                continue;

            while (true) {
                System.out.print("Dealing to player " + player.getId() + ", cards: " + player.getHandAsString() + ".  Hit or Stand? > ");
                String decision = scanner.next();

                if (decision.equalsIgnoreCase("hit")) {
                    Card card = deck.dealCard();
                    player.addCard(card);
                } else if (decision.equalsIgnoreCase("stand")) {
                    break;
                }
            }
        }

        Player dealer = players.get(players.size() - 1);
        System.out.println("Dealing to computer, cards: " + dealer.getHandAsString() + ".  Dealer hits.");

        while (dealer.getScore() < DEALER_STAND_SCORE) {
            Card card = deck.dealCard();
            dealer.addCard(card);
        }
        System.out.println("Dealing to computer, cards: " + dealer.getHandAsString() + ".  Dealer stands.");
    }

    private void printResults(List<Player> players) {
        Player dealer = players.get(players.size() - 1);
        for (Player player : players) {
            if (player.isDealer())
                continue;

            int playerScore = player.getScore();
            int dealerScore = dealer.getScore();

            System.out.print("Scoring player " + player.getId() + " ");
            if (playerScore > MAX_SCORE) {
                System.out.println("busted.  Dealer wins.");
            } else if (dealerScore > MAX_SCORE || playerScore > dealerScore) {
                System.out.println("has " + playerScore + ", dealer has " + dealerScore + ".  Player " + player.getId() + " wins.");
            } else if (playerScore < dealerScore) {
                System.out.println("has " + playerScore + ", dealer has " + dealerScore + ".  Dealer wins.");
            } else {
                System.out.println("has " + playerScore + ", dealer has " + dealerScore + ".  It's a tie.");
            }
        }
    }
}

