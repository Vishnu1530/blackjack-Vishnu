class Player {
    private int id;
    private List<Card> hand;

    public Player(int id) {
        this.id = id;
        this.hand = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public String getHandAsString() {
        List<String> cardStrings = new ArrayList<>();
        for (Card card : hand) {
            cardStrings.add(card.toString());
        }
        return String.join(", ", cardStrings);
    }

    public int getScore() {
        int score = 0;
        int numAces = 0;

        for (Card card : hand) {
            int value = card.getValue();

            if (value == 1) {
                score += 11;
                numAces++;
            } else if (value >= 10) {
                score += 10;
            } else {
                score += value;
            }
        }

        while (score > BlackjackGame.MAX_SCORE && numAces > 0) {
            score -= 10;
            numAces--;
        }

        return score;
    }

    public boolean isDealer() {
        return id == 0;
    }
}

