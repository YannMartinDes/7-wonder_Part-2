package action;

public class ActionTest
{
//    private Deck currentDeck;
//    private Deck discardingDeck;
//    private WonderBoard wonderBoard;
//    private String playerName;
//
//    private BuildAction buildAction;
//    private DiscardAction discardAction;
//
//    @BeforeEach
//    public void init ()
//    {
//        GameLogger.verbose = false;
//        this.currentDeck = Mockito.mock(Deck.class);
//        this.discardingDeck = Mockito.mock(Deck.class);
//        this.wonderBoard = Mockito.mock(WonderBoard.class);
//        this.playerName = "";
//    }
//
//    @Test
//    public void testPlayAction ()
//    {
//        // Discard Action
//        this.currentDeck = new Deck();
//        this.currentDeck.addCard(new Card("PRÊTEUR SUR GAGES", CardType.CIVIL_BUILDING, new VictoryPointEffect(3),1,null));
//        this.discardAction = new DiscardAction(0);
//
//        assertEquals(this.currentDeck.getLength(), 1);
//
//        this.discardAction.playAction(this.currentDeck, this.discardingDeck, this.wonderBoard, this.playerName);
//
//        assertEquals(this.currentDeck.getLength(), 0);
//
//        // Build Action
//            // playedCard.getCostCard()==null
//        this.currentDeck = new Deck();
//        this.currentDeck.addCard(new Card("PRÊTEUR SUR GAGES", CardType.CIVIL_BUILDING, new VictoryPointEffect(3),1,null));
//        this.wonderBoard = new WonderBoard("Le Colosse de Rhodes",new AddingMaterialEffet(new Material(MaterialType.ORES,1)));
//        this.discardingDeck = new Deck();
//        this.playerName = "Donald Duck";
//        this.buildAction = new BuildAction(0);
//
//        assertEquals(this.currentDeck.getLength(), 1);
//
//        this.discardAction.playAction(this.currentDeck, this.discardingDeck, this.wonderBoard, this.playerName);
//
//        assertEquals(this.currentDeck.getLength(), 0);
//
//            // playedCard.getCostCard().canBuyCard(wonderBoard.getAllEffects()) == true
//        this.currentDeck = new Deck();
//        this.currentDeck.addCard(new Card("PRÊTEUR SUR GAGES", CardType.CIVIL_BUILDING, new VictoryPointEffect(3),1,new MaterialCost(new Material(MaterialType.ORES, 1))));
//        this.wonderBoard = new WonderBoard("Le Colosse de Rhodes",new AddingMaterialEffet(new Material(MaterialType.ORES,1)));
//        this.discardingDeck = new Deck();
//        this.playerName = "Donald Duck";
//        this.buildAction = new BuildAction(0);
//
//        assertEquals(this.currentDeck.getLength(), 1);
//
//        this.discardAction.playAction(this.currentDeck, this.discardingDeck, this.wonderBoard, this.playerName);
//
//        assertEquals(this.currentDeck.getLength(), 0);
//
//            // else
//        this.currentDeck = new Deck();
//        this.currentDeck.addCard(new Card("PRÊTEUR SUR GAGES", CardType.CIVIL_BUILDING, new VictoryPointEffect(3),1,new CoinCost(999)));
//        this.wonderBoard = new WonderBoard("Le Colosse de Rhodes",new AddingMaterialEffet(new Material(MaterialType.ORES,1)));
//        this.discardingDeck = new Deck();
//        this.playerName = "Donald Duck";
//        this.buildAction = new BuildAction(0);
//
//        assertEquals(this.currentDeck.getLength(), 1);
//
//        this.discardAction.playAction(this.currentDeck, this.discardingDeck, this.wonderBoard, this.playerName);
//
//        assertEquals(this.currentDeck.getLength(), 0);
//    }
}
