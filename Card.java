class Card {
    private String value;
    private String suit;

    public Card(String value, String suit) {
        this.value = value;
        this.suit = suit;
    }

    public int getValue() {
        if (value.equals("Ace")) {
            return 1;
        } else if (value.equals("King") || value.equals("Queen") || value.equals("Jack")) {
            return 10;
        } else {
            return Integer.parseInt(value);
        }
    }

    @Override
    public String toString() {
        return value + " " + suit;
    }
}
