package severgame.card;

import static org.junit.jupiter.api.Assertions.*;

import commun.card.Card;
import commun.card.CardType;
import commun.card.Deck;
import commun.effect.*;
import commun.material.ChoiceMaterial;
import commun.material.Material;
import commun.material.MaterialType;
import org.junit.jupiter.api.*;
import servergame.card.CardFactory;

public class CardFactoryTest
{
    private CardFactory cardFactory;

    @BeforeEach
    public void init ()
    {
        this.cardFactory = new CardFactory();
    }

    @Test
    public void testAgeOneCardsName ()
    {
        String[] expected = new String [] {
                "BAINS",
                "AUTEL",
                "THÉÂTRE",
                "CHANTIER",
                "CAVITÉ",
                "BASSIN ARGILEUX",
                "FILON",
                "FOSSE ARGILEUSE",
                "EXPLOITATION FORESTIÉRE",
                "VERRERIE",
                "MÉTIER À TISSER",
                "PRESSE",
                "COMPTOIR EST",
                "COMPTOIR OUEST",
                "MARCHÉ",
                "PALISSADE",
                "CASERNE",
                "TOUR DE GARDE",
                "OFFICINE",
                "ATELIER",
                "SCRIPTORIUM",
                "PRÊTEUR SUR GAGES",
                "CHANTIER",
                "FILON",
                "EXCAVATION",
                "TAVERNE",
                "TOUR DE GARDE",
                "SCRIPTORIUM",
                "AUTEL",
                "CAVITÉ",
                "BASSIN ARGILEUX",
                "GISEMENT",
                "TAVERNE",
                "CASERNE",
                "OFFICINE",
                "THÉÂTRE",
                "FRICHE",
                "MINE",
                "VERRERIE",
                "MÉTIER À TISSER",
                "PRESSE",
                "MARCHÉ",
                "PRÊTEUR SUR GAGES",
                "BAINS",
                "TAVERNE",
                "COMPTOIR EST",
                "COMPTOIR OUEST",
                "PALISSADE",
                "ATELIER"



        };

        Deck deckGot = this.cardFactory.AgeOneCards(7);

        for (int i = 0; i < deckGot.getLength(); i++)
        {
            assertEquals(deckGot.getCard(i).getName(), expected[i]);
        }
    }

//    @Test
//    public void testAgeOneCardsEffect ()
//    {
//        IEffect[] expected = new IEffect[] {
//                new VictoryPointEffect(3),
//                new VictoryPointEffect(3),
//                new VictoryPointEffect(3),
//                new VictoryPointEffect(3),
//                new VictoryPointEffect(2),
//                new VictoryPointEffect(2),
//                new VictoryPointEffect(2),
//                new VictoryPointEffect(2),
//
//                new AddingMaterialEffet(new Material(MaterialType.WOOD,1 )),
//                new AddingMaterialEffet(new Material(MaterialType.WOOD,1 )),
//                new AddingMaterialEffet(new Material(MaterialType.STONE,1 )),
//                new AddingMaterialEffet(new Material(MaterialType.STONE,1 )),
//                new AddingMaterialEffet(new Material(MaterialType.CLAY,1 )),
//                new AddingMaterialEffet(new Material(MaterialType.CLAY,1 )),
//                new AddingMaterialEffet(new Material(MaterialType.ORES,1 )),
//                new AddingMaterialEffet(new Material(MaterialType.ORES,1 )),
//
//                new AddindChoiceMaterialEffect(new ChoiceMaterial( new Material(MaterialType.WOOD,1) , new Material(MaterialType.CLAY,1))),
//                new AddindChoiceMaterialEffect(new ChoiceMaterial( new Material(MaterialType.STONE,1) , new Material(MaterialType.CLAY,1))),
//                new AddindChoiceMaterialEffect(new ChoiceMaterial( new Material(MaterialType.CLAY,1) , new Material(MaterialType.ORES,1))),
//                new AddindChoiceMaterialEffect(new ChoiceMaterial( new Material(MaterialType.STONE,1) , new Material(MaterialType.WOOD,1))),
//                new AddindChoiceMaterialEffect(new ChoiceMaterial( new Material(MaterialType.WOOD,1) , new Material(MaterialType.ORES,1))),
//                new AddindChoiceMaterialEffect(new ChoiceMaterial( new Material(MaterialType.STONE,1) , new Material(MaterialType.ORES,1))),
//
//                new AddingMaterialEffet(new Material(MaterialType.GLASS, 1)),
//                new AddingMaterialEffet(new Material(MaterialType.GLASS, 1)),
//                new AddingMaterialEffet(new Material(MaterialType.FABRIC,1)),
//                new AddingMaterialEffet(new Material(MaterialType.FABRIC,1)),
//                new AddingMaterialEffet(new Material(MaterialType.PAPYRUS, 1)),
//                new AddingMaterialEffet(new Material(MaterialType.PAPYRUS, 1)),
//
//                new CoinEffect(5),
//                new CoinEffect(5),
//                new CoinEffect(5),
//                new OneCoinNeighbor(0, new NeighborMaterials( new Material(MaterialType.WOOD,1),new Material(MaterialType.CLAY,1), new Material(MaterialType.STONE,1),new Material(MaterialType.ORES,1))),
//                new OneCoinNeighbor(0, new NeighborMaterials( new Material(MaterialType.WOOD,1),new Material(MaterialType.CLAY,1), new Material(MaterialType.STONE,1),new Material(MaterialType.ORES,1))),
//                new OneCoinNeighbor(1, new NeighborMaterials( new Material(MaterialType.WOOD,1),new Material(MaterialType.CLAY,1), new Material(MaterialType.STONE,1), new Material(MaterialType.ORES,1))),
//                new OneCoinNeighbor(1, new NeighborMaterials( new Material(MaterialType.WOOD,1),new Material(MaterialType.CLAY,1), new Material(MaterialType.STONE,1),new Material(MaterialType.ORES,1))),
//                new OneCoinNeighbor(2, new NeighborMaterials( new Material(MaterialType.GLASS,1), new Material(MaterialType.PAPYRUS,1), new Material(MaterialType.FABRIC,1))),
//                new OneCoinNeighbor(2, new NeighborMaterials( new Material(MaterialType.GLASS,1), new Material(MaterialType.PAPYRUS,1), new Material(MaterialType.FABRIC,1))),
//
//                new MilitaryEffect(1),
//                new MilitaryEffect(1),
//                new MilitaryEffect(1),
//                new MilitaryEffect(1),
//                new MilitaryEffect(1),
//                new MilitaryEffect(1),
//
//                new ScientificEffect(ScientificType.GEOMETRY),
//                new ScientificEffect(ScientificType.GEOMETRY),
//                new ScientificEffect(ScientificType.GEOGRAPHY),
//                new ScientificEffect(ScientificType.GEOGRAPHY),
//                new ScientificEffect(ScientificType.LITERATURE),
//                new ScientificEffect(ScientificType.LITERATURE),
//
//
//        };
//
//        Deck deckGot = this.cardFactory.AgeOneCards(7);
//
//        for (int i = 0; i < deckGot.getLength(); i++)
//        {
//            assertEquals(deckGot.getCard(i).getCardEffect().getScore(), expected[i].getScore());
//
//            if(deckGot.getCard(i).getCardEffect().getMaterial() != null){
//                assertEquals(deckGot.getCard(i).getCardEffect().getMaterial().getNumber(), expected[i].getMaterial().getNumber());
//                assertEquals(deckGot.getCard(i).getCardEffect().getMaterial().getType(), expected[i].getMaterial().getType());
//            }
//
//            if(deckGot.getCard(i).getCardEffect().getChoiceMaterial() != null){
//                assertEquals(deckGot.getCard(i).getCardEffect().getChoiceMaterial().getMaterial1().getType(), expected[i].getChoiceMaterial().getMaterial1().getType());
//                assertEquals(deckGot.getCard(i).getCardEffect().getChoiceMaterial().getMaterial1().getNumber(), expected[i].getChoiceMaterial().getMaterial1().getNumber());
//
//                assertEquals(deckGot.getCard(i).getCardEffect().getChoiceMaterial().getMaterial2().getType(), expected[i].getChoiceMaterial().getMaterial2().getType());
//                assertEquals(deckGot.getCard(i).getCardEffect().getChoiceMaterial().getMaterial2().getNumber(), expected[i].getChoiceMaterial().getMaterial2().getNumber());
//            }
//
//
//        }
//    }

//    @Test
//    public void testAgeOneCardsType ()
//    {
//        Deck deckGot = this.cardFactory.AgeOneCards(7);
//
//        for (int i = 0; i < deckGot.getLength(); i++)
//        {
//            if(i<8)
//                assertEquals(deckGot.getCard(i).getType(), CardType.CIVIL_BUILDING);
//            else if(i<22)
//                assertEquals(deckGot.getCard(i).getType(), CardType.RAW_MATERIALS);
//            else if(i<28){
//                assertEquals(deckGot.getCard(i).getType(), CardType.MANUFACTURED_PRODUCTS);
//            }
//        }
//    }

    @Test
    public void testAgeOneCardsAge ()
    {
        Deck deckGot = this.cardFactory.AgeOneCards(7);

        for (int i = 0; i < deckGot.getLength(); i++)
        {
            assertEquals(deckGot.getCard(i).getAge(), 1);//Age 1
        }
    }
}