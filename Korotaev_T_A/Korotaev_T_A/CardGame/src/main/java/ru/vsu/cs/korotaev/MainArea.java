package ru.vsu.cs.korotaev;

import javafx.scene.image.Image;

import java.util.*;

public class MainArea {
    private static List<Card> deck = createDeck();
    private static Phase phase = Phase.FirstAttack;
    private static Color trumpCol;
    private static boolean isStartGame;
    private static int cardNum = 54;
    private static List<Card> curPage = new ArrayList<>();
    private static int pageNum=0;
    private static HashMap<Rank, Integer> massMap = new HashMap<Rank, Integer>();
    private static Card[] gameFieldCardAttack = new Card[6];
    private static Card[] gameFieldCardDefence = new Card[6];
    private static Card takenCard = new Card();
    private static boolean cardIsTaken = false;
    private static boolean[] isTaped = new boolean[6];

    public static void giveMassToMap(){
        massMap.put(Rank.Two, 200);
        massMap.put(Rank.Three, 300);
        massMap.put(Rank.Four, 400);
        massMap.put(Rank.Five, 500);
        massMap.put(Rank.Six, 600);
        massMap.put(Rank.Seven, 700);
        massMap.put(Rank.Eight, 800);
        massMap.put(Rank.Nine, 900);
        massMap.put(Rank.Ten, 1000);
        massMap.put(Rank.Jack, 1100);
        massMap.put(Rank.Queen, 1200);
        massMap.put(Rank.King, 1300);
        massMap.put(Rank.Ace, 1400);
        massMap.put(Rank.Joker, 1500);
    }

    private static<T> void shuffle(List<T> list)
    {
        Random random = new Random();
        for (int i = list.size() - 1; i >= 1; i--)
        {
            int j = random.nextInt(i + 1);
            T obj = list.get(i);
            list.set(i, list.get(j));
            list.set(j, obj);
        }
    }

    private static List<Card> createDeck(){
        List<Card> list = new ArrayList<>();
        for (Color allColor : Color.values()){
            if(allColor != Color.Black && allColor !=Color.Red) {
                for (Rank allRank : Rank.values()) {
                    if (allRank != Rank.Joker) {
                        Image image = new Image("file:Sprites/cards_" + allColor + "_" + allRank +".png.png");
                        list.add(new Card(allColor, allRank, false, image));
                    }
                }
            }
        }
        Image image1 = new Image("file:Sprites/cards_Red_Joker.png" );
        Image image2 = new Image("file:Sprites/cards_Black_Joker.png");
        list.add(new Card(Color.Black, Rank.Joker, false, image2));
        list.add(new Card(Color.Red, Rank.Joker, false, image1));
        return list;
    }

    public static void randomDeck(){
        shuffle(deck);
        trumpCol = deck.get(deck.size()-1).getColor();
        for(Card el : deck){
            if (el.getColor()==trumpCol){
                el.setTrump(true);
            }
            if (el.getColor()==Color.Red && (trumpCol == Color.Diamonds || trumpCol == Color.Hearts)){
                el.setTrump(true);
            }
            if (el.getColor()==Color.Black && (trumpCol == Color.Clubs || trumpCol == Color.Spades)){
                el.setTrump(true);
            }
        }
    }

    public static List<Card> distribution(List<Card> first) {
        if (first.size() < 6) {
            for (int i = first.size(); i < 6; i++) {
                for (Card card : deck) {
                    if (!card.isBroken()) {
                        first.add(card);
                        card.setBroken(true);
                        cardNum -=1;
                        break;
                    }
                    }
                }
        }
        return first;
    }



    public static List<Card> getDeck() {
        return deck;
    }

    public static void setDeck(List<Card> deck) {
        MainArea.deck = deck;
    }

    public static Phase getPhase() {
        return phase;
    }

    public static void setPhase(Phase phase) {
        MainArea.phase = phase;
    }

    public static Color getTrumpCol() {
        return trumpCol;
    }

    public static void setTrumpCol(Color trumpCol) {
        MainArea.trumpCol = trumpCol;
    }

    public static HashMap<Rank, Integer> getMassMap() {
        return massMap;
    }

    public static void setMassMap(HashMap<Rank, Integer> massMap) {
        MainArea.massMap = massMap;
    }

    public static List<Card> getCurPage() {
        return curPage;
    }

    public static void setCurPage(List<Card> curPage) {
        MainArea.curPage = curPage;
    }

    public static int getPageNum() {
        return pageNum;
    }

    public static void setPageNum(int pageNum) {
        MainArea.pageNum = pageNum;
    }

    public static Card[] getGameFieldCardAttack() {
        return gameFieldCardAttack;
    }

    public static void setGameFieldCardAttack(Card[] gameFieldCardAttack) {
        MainArea.gameFieldCardAttack = gameFieldCardAttack;
    }

    public static Card[] getGameFieldCardDefence() {
        return gameFieldCardDefence;
    }

    public static void setGameFieldCardDefence(Card[] gameFieldCardDefence) {
        MainArea.gameFieldCardDefence = gameFieldCardDefence;
    }

    public static Card getTakenCard() {
        return takenCard;
    }

    public static void setTakenCard(Card takenCard) {
        MainArea.takenCard = takenCard;
    }

    public static boolean isCardIsTaken() {
        return cardIsTaken;
    }

    public static void setCardIsTaken(boolean cardIsTaken) {
        MainArea.cardIsTaken = cardIsTaken;
    }

    public static boolean isIsStartGame() {
        return isStartGame;
    }

    public static void setIsStartGame(boolean isStartGame) {
        MainArea.isStartGame = isStartGame;
    }

    public static int getCardNum() {
        return cardNum;
    }

    public static void setCardNum(int cardNum) {
        MainArea.cardNum = cardNum;
    }

    public static boolean[] getIsTaped() {
        return isTaped;
    }

    public static void setIsTaped(boolean[] isTaped) {
        MainArea.isTaped = isTaped;
    }
}
