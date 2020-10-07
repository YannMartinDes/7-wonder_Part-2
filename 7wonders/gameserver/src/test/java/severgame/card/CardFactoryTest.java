package severgame.card;

import static org.junit.jupiter.api.Assertions.*;

import commun.card.CardType;
import commun.card.Deck;
import commun.effect.*;
import commun.material.ChoiceMaterial;
import commun.material.Material;
import commun.material.MaterialType;
import org.junit.jupiter.api.*;
import servergame.card.CardFactory;

import java.io.CharArrayReader;

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

    @Test
    public void testAgeOneCardsEffect ()
    {
        IEffect[] expected = new IEffect[] {
                new VictoryPointEffect(3),
                new VictoryPointEffect(2),
                new VictoryPointEffect(2),

                new ChoiceMaterialEffect ( new ChoiceMaterial(new Material(MaterialType.WOOD,1 ))),
                new ChoiceMaterialEffect ( new ChoiceMaterial(new Material(MaterialType.STONE,1 ))),
                new ChoiceMaterialEffect ( new ChoiceMaterial(new Material(MaterialType.CLAY,1 ))),
                new ChoiceMaterialEffect ( new ChoiceMaterial(new Material(MaterialType.ORES,1 ))),
                new ChoiceMaterialEffect(new ChoiceMaterial( new Material(MaterialType.CLAY,1), new Material(MaterialType.ORES,1))),
                new ChoiceMaterialEffect(new ChoiceMaterial( new Material(MaterialType.STONE,1) , new Material(MaterialType.WOOD,1))),
                new ChoiceMaterialEffect ( new ChoiceMaterial(new Material(MaterialType.GLASS, 1))),
                new ChoiceMaterialEffect ( new ChoiceMaterial(new Material(MaterialType.FABRIC,1))),
                new ChoiceMaterialEffect ( new ChoiceMaterial(new Material(MaterialType.PAPYRUS, 1))),

                new OneCoinNeighborEffect(TargetType.RIGHT_NEIGHTBOUR,new Material(MaterialType.WOOD,1),new Material(MaterialType.CLAY,1), new Material(MaterialType.STONE,1), new Material(MaterialType.ORES,1)),
                new OneCoinNeighborEffect(TargetType.LEFT_NEIGHTBOUR,new Material(MaterialType.WOOD,1),new Material(MaterialType.CLAY,1), new Material(MaterialType.STONE,1), new Material(MaterialType.ORES,1)),
                new OneCoinNeighborEffect(TargetType.BOTH_NEIGHTBOUR,new Material(MaterialType.GLASS,1), new Material(MaterialType.PAPYRUS,1), new Material(MaterialType.FABRIC,1)),

                new MilitaryEffect(1),
                new MilitaryEffect(1),
                new MilitaryEffect(1),

                new ScientificEffect(ScientificType.GEOMETRY),
                new ScientificEffect(ScientificType.GEOGRAPHY),
                new ScientificEffect(ScientificType.LITERATURE),

                new VictoryPointEffect(3),

                new ChoiceMaterialEffect ( new ChoiceMaterial(new Material(MaterialType.WOOD,1 ))),
                new ChoiceMaterialEffect ( new ChoiceMaterial(new Material(MaterialType.ORES,1 ))),
                new ChoiceMaterialEffect(new ChoiceMaterial( new Material(MaterialType.STONE,1) , new Material(MaterialType.CLAY,1))),

                new CoinEffect(5),
                new MilitaryEffect(1),
                new ScientificEffect(ScientificType.LITERATURE),
                new VictoryPointEffect(2),

                new ChoiceMaterialEffect ( new ChoiceMaterial(new Material(MaterialType.STONE,1 ))),
                new ChoiceMaterialEffect ( new ChoiceMaterial(new Material(MaterialType.CLAY,1 ))),
                new ChoiceMaterialEffect(new ChoiceMaterial( new Material(MaterialType.WOOD,1) , new Material(MaterialType.ORES,1))),

                new CoinEffect(5),
                new MilitaryEffect(1),
                new ScientificEffect(ScientificType.GEOMETRY),
                new VictoryPointEffect(2),

                new ChoiceMaterialEffect(new ChoiceMaterial( new Material(MaterialType.WOOD,1) , new Material(MaterialType.CLAY,1))),
                new ChoiceMaterialEffect(new ChoiceMaterial( new Material(MaterialType.STONE,1) , new Material(MaterialType.ORES,1))),
                new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.GLASS, 1))),
                new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.FABRIC,1))),
                new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.PAPYRUS, 1))),

                new OneCoinNeighborEffect(TargetType.BOTH_NEIGHTBOUR,new Material(MaterialType.GLASS,1), new Material(MaterialType.PAPYRUS,1), new Material(MaterialType.FABRIC,1)),

                new VictoryPointEffect(3),
                new VictoryPointEffect(3),
                new CoinEffect(5),

                new OneCoinNeighborEffect(TargetType.RIGHT_NEIGHTBOUR,new Material(MaterialType.WOOD,1),new Material(MaterialType.CLAY,1), new Material(MaterialType.STONE,1), new Material(MaterialType.ORES,1)),
                new OneCoinNeighborEffect(TargetType.RIGHT_NEIGHTBOUR,new Material(MaterialType.WOOD,1),new Material(MaterialType.CLAY,1), new Material(MaterialType.STONE,1), new Material(MaterialType.ORES,1)),

                new MilitaryEffect(1),
                new ScientificEffect(ScientificType.GEOGRAPHY)
        };

        Deck deckGot = this.cardFactory.AgeOneCards(7);

        for (int i = 0; i < deckGot.getLength(); i++)
        {
            assertEquals(deckGot.getCard(i).getCardEffect().getScore(), expected[i].getScore());

            if (deckGot.getCard(i).getCardEffect().getMaterials().length > 0)
            {
                for (int k = 0; k < deckGot.getCard(i).getCardEffect().getMaterials().length; k++)
                {
                    assertEquals(deckGot.getCard(i).getCardEffect().getMaterials()[k].getNumber(), expected[i].getMaterials()[k].getNumber());
                    assertEquals(deckGot.getCard(i).getCardEffect().getMaterials()[k].getType(), expected[i].getMaterials()[k].getType());
                }
            }

            if(deckGot.getCard(i).getCardEffect().getMaterials() != null)
            {
                for (int k = 0; k < deckGot.getCard(i).getCardEffect().getMaterials().length; k++)
                {
                    assertEquals(deckGot.getCard(i).getCardEffect().getMaterials()[k].getType(), expected[i].getMaterials()[k].getType());
                    assertEquals(deckGot.getCard(i).getCardEffect().getMaterials()[k].getNumber(), expected[i].getMaterials()[k].getNumber());
                }
            }


        }
    }

    @Test
    public void testAgeOneCardsType ()
    {
        Deck deckGot = this.cardFactory.AgeOneCards(7);
        CardType [] types = new CardType[]
                {
                        CardType.CIVIL_BUILDING,
                        CardType.CIVIL_BUILDING,
                        CardType.CIVIL_BUILDING,
                        CardType.RAW_MATERIALS,
                        CardType.RAW_MATERIALS,
                        CardType.RAW_MATERIALS,
                        CardType.RAW_MATERIALS,
                        CardType.RAW_MATERIALS,
                        CardType.RAW_MATERIALS,
                        CardType.MANUFACTURED_PRODUCTS,
                        CardType.MANUFACTURED_PRODUCTS,
                        CardType.MANUFACTURED_PRODUCTS,
                        CardType.COMMERCIAL_BUILDINGS,
                        CardType.COMMERCIAL_BUILDINGS,
                        CardType.COMMERCIAL_BUILDINGS,
                        CardType.MILITARY_BUILDINGS,
                        CardType.MILITARY_BUILDINGS,
                        CardType.MILITARY_BUILDINGS,
                        CardType.SCIENTIFIC_BUILDINGS,
                        CardType.SCIENTIFIC_BUILDINGS,
                        CardType.SCIENTIFIC_BUILDINGS,
                        CardType.CIVIL_BUILDING,
                        CardType.RAW_MATERIALS,
                        CardType.RAW_MATERIALS,
                        CardType.RAW_MATERIALS,
                        CardType.COMMERCIAL_BUILDINGS,
                        CardType.MILITARY_BUILDINGS,
                        CardType.SCIENTIFIC_BUILDINGS,
                        CardType.CIVIL_BUILDING,
                        CardType.RAW_MATERIALS,
                        CardType.RAW_MATERIALS,
                        CardType.RAW_MATERIALS,
                        CardType.COMMERCIAL_BUILDINGS,
                        CardType.MILITARY_BUILDINGS,
                        CardType.SCIENTIFIC_BUILDINGS,
                        CardType.CIVIL_BUILDING,
                        CardType.RAW_MATERIALS,
                        CardType.RAW_MATERIALS,
                        CardType.MANUFACTURED_PRODUCTS,
                        CardType.MANUFACTURED_PRODUCTS,
                        CardType.MANUFACTURED_PRODUCTS,
                        CardType.COMMERCIAL_BUILDINGS,
                        CardType.CIVIL_BUILDING,
                        CardType.CIVIL_BUILDING,
                        CardType.COMMERCIAL_BUILDINGS,
                        CardType.COMMERCIAL_BUILDINGS,
                        CardType.COMMERCIAL_BUILDINGS,
                        CardType.MILITARY_BUILDINGS,
                        CardType.SCIENTIFIC_BUILDINGS
                };

        for (int i = 0; i < deckGot.getLength(); i++)
        {
            assertEquals(deckGot.getCard(i).getType(), types[i]);
        }
    }

    @Test
    public void testAgeOneCardsAge ()
    {
        Deck deckGot = this.cardFactory.AgeOneCards(7);

        for (int i = 0; i < deckGot.getLength(); i++)
        {
            assertEquals(deckGot.getCard(i).getAge(), 1);//Age 1
        }
    }

    @Test
    public void testAgeTwoCardsName ()
    {
        String[] expected = new String []
                {
                        "MURAILLE",
                        "ÉCURIES",
                        "CHAMPS DE TIR",
                        "DISPENSAIRE",
                        "LABORATOIRE",
                        "BIBLIOTHÈQUE",
                        "ÉCOLE",
                        "FORUM",
                        "CARAVANSÉRAIL",
                        "VIGNOBLE",
                        "SCIERIE",
                        "CARRIÈRE",
                        "BRIQUETERIE",
                        "FONDERIE",
                        "MÉTIER À TISSER",
                        "VERRERIE",
                        "PRESSE",
                        "AQUEDUC",
                        "TEMPLE",
                        "STATUE",
                        "TRIBUNAL",
                        "PLACE D'ARMES",
                        "DISPENSAIRE",
                        "BAZAR",
                        "SCIERIE",
                        "CARRIÈRE",
                        "BRIQUETERIE",
                        "FONDERIE",
                        "ÉCURIES",
                        "LABORATOIRE",
                        "CARAVANSÉRAIL",
                        "MÉTIER À TISSER",
                        "VERRERIE",
                        "PRESSE",
                        "TRIBUNAL",
                        "PLACE D'ARMES",
                        "CHAMPS DE TIRS",
                        "BIBLIOTHÈQUE",
                        "FORUM",
                        "CARAVANSÉRAIL",
                        "VIGNOBLE",
                        "TEMPLE",
                        "MURAILLE",
                        "PLACE D'ARMES",
                        "ÉCOLE",
                        "FORUM",
                        "BAZAR",
                        "AQUEDUC",
                        "STATUE",
        };

        Deck deckGot = this.cardFactory.AgeTwoCards(7);

        for (int i = 0; i < deckGot.getLength(); i++)
        {
            assertEquals(deckGot.getCard(i).getName(), expected[i]);
        }
    }

    @Test
    public void testAgeTwoCardsEffect ()
    {
        IEffect[] expected = new IEffect[] {
                new MilitaryEffect(2),
                new MilitaryEffect(2),
                new MilitaryEffect(2),

                new ScientificEffect(ScientificType.GEOMETRY),
                new ScientificEffect(ScientificType.GEOGRAPHY),
                new ScientificEffect(ScientificType.LITERATURE),
                new ScientificEffect(ScientificType.LITERATURE),

                new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.PAPYRUS,1),new Material(MaterialType.FABRIC,1), new Material(MaterialType.GLASS,1))) ,
                new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.WOOD,1),new Material(MaterialType.ORES,1),new Material(MaterialType.STONE,1),new Material(MaterialType.CLAY,1))) ,
                new EarnWithCardEffect(new EarnWithCard(1,0,TargetType.RIGHT_NEIGHTBOUR,CardType.CIVIL_BUILDING)) ,
                new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.WOOD,2))) ,
                new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.STONE,2))) ,
                new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.CLAY,2))) ,
                new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.ORES,2))) ,
                new ChoiceMaterialEffect ( new ChoiceMaterial(new Material(MaterialType.FABRIC,1))),
                new ChoiceMaterialEffect ( new ChoiceMaterial(new Material(MaterialType.GLASS,1))),
                new ChoiceMaterialEffect ( new ChoiceMaterial(new Material(MaterialType.PAPYRUS,1))),

                new VictoryPointEffect(5),
                new VictoryPointEffect(3),
                new VictoryPointEffect(4),
                new VictoryPointEffect(4),

                new MilitaryEffect(2),
                new ScientificEffect(ScientificType.GEOMETRY),
                new EarnWithCardEffect(new EarnWithCard(2,0,TargetType.RIGHT_NEIGHTBOUR,CardType.MANUFACTURED_PRODUCTS)) ,

                new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.WOOD,2))) ,
                new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.STONE,2))) ,
                new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.CLAY,2))) ,
                new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.ORES,2))) ,

                new MilitaryEffect(2),
                new ScientificEffect(ScientificType.GEOGRAPHY),

                new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.WOOD,1),new Material(MaterialType.ORES,1),new Material(MaterialType.STONE,1),new Material(MaterialType.CLAY,1))) ,
                new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.FABRIC,1))) ,
                new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.GLASS,1))) ,
                new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.PAPYRUS,1))) ,
                new VictoryPointEffect(4),

                new MilitaryEffect(2),
                new MilitaryEffect(2),

                new ScientificEffect(ScientificType.LITERATURE),

                new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.PAPYRUS,1),new Material(MaterialType.FABRIC,1), new Material(MaterialType.GLASS,1))) ,
                new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.WOOD,1),new Material(MaterialType.ORES,1),new Material(MaterialType.STONE,1),new Material(MaterialType.CLAY,1))) ,
                new EarnWithCardEffect(new EarnWithCard(1,0, TargetType.RIGHT_NEIGHTBOUR,CardType.RAW_MATERIALS)) ,

                new VictoryPointEffect(3),

                new MilitaryEffect(2),
                new MilitaryEffect(2),
                new ScientificEffect(ScientificType.LITERATURE),
                new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.PAPYRUS,1),new Material(MaterialType.FABRIC,1), new Material(MaterialType.GLASS,1))) ,
                new EarnWithCardEffect(new EarnWithCard(2,0,TargetType.RIGHT_NEIGHTBOUR,CardType.MANUFACTURED_PRODUCTS)) ,
                new VictoryPointEffect(5),
                new VictoryPointEffect(4)
        };

        Deck deckGot = this.cardFactory.AgeTwoCards(7);

        for (int i = 0; i < deckGot.getLength(); i++)
        {
            assertEquals(deckGot.getCard(i).getCardEffect().getScore(), expected[i].getScore());

            if (deckGot.getCard(i).getCardEffect().getMaterials().length > 0)
            {
                for (int k = 0; k < deckGot.getCard(i).getCardEffect().getMaterials().length; k++)
                {
                    assertEquals(deckGot.getCard(i).getCardEffect().getMaterials()[k].getNumber(), expected[i].getMaterials()[k].getNumber());
                    assertEquals(deckGot.getCard(i).getCardEffect().getMaterials()[k].getType(), expected[i].getMaterials()[k].getType());
                }
            }

            if(deckGot.getCard(i).getCardEffect().getMaterials() != null)
            {
                for (int k = 0; k < deckGot.getCard(i).getCardEffect().getMaterials().length; k++)
                {
                    assertEquals(deckGot.getCard(i).getCardEffect().getMaterials()[k].getType(), expected[i].getMaterials()[k].getType());
                    assertEquals(deckGot.getCard(i).getCardEffect().getMaterials()[k].getNumber(), expected[i].getMaterials()[k].getNumber());
                }
            }


        }
    }

    @Test
    public void testAgeTwoCardsType ()
    {
        Deck deckGot = this.cardFactory.AgeTwoCards(7);
        CardType [] types = new CardType[]
                {
                        CardType.MILITARY_BUILDINGS,
                        CardType.MILITARY_BUILDINGS,
                        CardType.MILITARY_BUILDINGS,
                        CardType.SCIENTIFIC_BUILDINGS,
                        CardType.SCIENTIFIC_BUILDINGS,
                        CardType.SCIENTIFIC_BUILDINGS,
                        CardType.SCIENTIFIC_BUILDINGS,
                        CardType.COMMERCIAL_BUILDINGS,
                        CardType.COMMERCIAL_BUILDINGS,
                        CardType.COMMERCIAL_BUILDINGS,
                        CardType.RAW_MATERIALS,
                        CardType.RAW_MATERIALS,
                        CardType.RAW_MATERIALS,
                        CardType.RAW_MATERIALS,
                        CardType.COMMERCIAL_BUILDINGS,
                        CardType.COMMERCIAL_BUILDINGS,
                        CardType.COMMERCIAL_BUILDINGS,
                        CardType.CIVIL_BUILDING,
                        CardType.CIVIL_BUILDING,
                        CardType.CIVIL_BUILDING,
                        CardType.CIVIL_BUILDING,
                        CardType.MILITARY_BUILDINGS,
                        CardType.SCIENTIFIC_BUILDINGS,
                        CardType.COMMERCIAL_BUILDINGS,
                        CardType.RAW_MATERIALS,
                        CardType.RAW_MATERIALS,
                        CardType.RAW_MATERIALS,
                        CardType.RAW_MATERIALS,
                        CardType.MILITARY_BUILDINGS,
                        CardType.SCIENTIFIC_BUILDINGS,
                        CardType.COMMERCIAL_BUILDINGS,
                        CardType.COMMERCIAL_BUILDINGS,
                        CardType.COMMERCIAL_BUILDINGS,
                        CardType.COMMERCIAL_BUILDINGS,
                        CardType.CIVIL_BUILDING,
                        CardType.MILITARY_BUILDINGS,
                        CardType.MILITARY_BUILDINGS,
                        CardType.SCIENTIFIC_BUILDINGS,
                        CardType.COMMERCIAL_BUILDINGS,
                        CardType.COMMERCIAL_BUILDINGS,
                        CardType.COMMERCIAL_BUILDINGS,
                        CardType.CIVIL_BUILDING,
                        CardType.MILITARY_BUILDINGS,
                        CardType.MILITARY_BUILDINGS,
                        CardType.SCIENTIFIC_BUILDINGS,
                        CardType.COMMERCIAL_BUILDINGS,
                        CardType.COMMERCIAL_BUILDINGS,
                        CardType.CIVIL_BUILDING,
                        CardType.CIVIL_BUILDING
                };

        for (int i = 0; i < deckGot.getLength(); i++)
        {
            assertEquals(deckGot.getCard(i).getType(), types[i]);
        }
    }

    @Test
    public void testAgeTwoCardsAge ()
    {
        Deck deckGot = this.cardFactory.AgeTwoCards(7);

        for (int i = 0; i < deckGot.getLength(); i++)
        {
            assertEquals(deckGot.getCard(i).getAge(), 2);//Age 2
        }
    }

    @Test
    public void testAgeThreeCardsName(){
        String[] expected = new String [] {
                //3+
                "PANTHÉON",
                "JARDINS",
                "HÔTEL DE VILLE",
                "PALACE",
                "PORT",
                "PHARE",
                "FORTIFICATIONS",
                "ARSENAL",
                "ATELIER DE SIEGE",
                "LOGE",
                "OBSERVATOIRE",
                "UNIVERSITE",
                "ACADEMIE",
                "ETUDE",
                "ARENE",
                "SENAT",
                //4+
                "JARDINS",
                "PORT",
                "CHAMBRE DE COMMERCE",
                "CIRQUE",
                "ARSENAL",
                "UNIVERSITE",
                //5+
                "HÔTEL DE VILLE",
                "CIRQUE",
                "ETUDE",
                "ARENE",
                "ATELIER DE SIEGE",
                //6+
                "PANTHÉON",
                "HÔTEL DE VILLE",
                "PHARE",
                "CHAMBRE DE COMMERCE",
                "CIRQUE",
                "LOGE",
                //7+
                "PALACE",
                "FORTIFICATIONS",
                "ARSENAL",
                "OBSERVATOIRE",
                "ACADEMIE",
                "ARENE",
        };

        Deck deckGot = this.cardFactory.AgeThreeCards(7);

        //sans les cartes Guildes (9 car 7joueurs)
        for (int i = 0; i < deckGot.getLength()-9; i++) {
            assertEquals(deckGot.getCard(i).getName(), expected[i]);
        }
    }
    public void testAgeThreeCardsType ()
    {
        Deck deckGot = this.cardFactory.AgeThreeCards(7);
        CardType [] types = new CardType[]
                {       //3+
                        CardType.CIVIL_BUILDING,
                        CardType.CIVIL_BUILDING,
                        CardType.CIVIL_BUILDING,
                        CardType.CIVIL_BUILDING,
                        CardType.COMMERCIAL_BUILDINGS,
                        CardType.COMMERCIAL_BUILDINGS,
                        CardType.MILITARY_BUILDINGS,
                        CardType.MILITARY_BUILDINGS,
                        CardType.MILITARY_BUILDINGS,
                        CardType.SCIENTIFIC_BUILDINGS,
                        CardType.SCIENTIFIC_BUILDINGS,
                        CardType.SCIENTIFIC_BUILDINGS,
                        CardType.SCIENTIFIC_BUILDINGS,
                        CardType.SCIENTIFIC_BUILDINGS,
                        CardType.COMMERCIAL_BUILDINGS,
                        CardType.CIVIL_BUILDING,
                        //4+
                        CardType.CIVIL_BUILDING,
                        CardType.COMMERCIAL_BUILDINGS,
                        CardType.COMMERCIAL_BUILDINGS,
                        CardType.MILITARY_BUILDINGS,
                        CardType.MILITARY_BUILDINGS,
                        CardType.SCIENTIFIC_BUILDINGS,
                        //5+
                        CardType.CIVIL_BUILDING,
                        CardType.MILITARY_BUILDINGS,
                        CardType.SCIENTIFIC_BUILDINGS,
                        CardType.COMMERCIAL_BUILDINGS,
                        CardType.MILITARY_BUILDINGS,
                        //6+
                        CardType.CIVIL_BUILDING,
                        CardType.CIVIL_BUILDING,
                        CardType.COMMERCIAL_BUILDINGS,
                        CardType.COMMERCIAL_BUILDINGS,
                        CardType.MILITARY_BUILDINGS,
                        CardType.SCIENTIFIC_BUILDINGS,
                        //7+
                        CardType.CIVIL_BUILDING,
                        CardType.MILITARY_BUILDINGS,
                        CardType.SCIENTIFIC_BUILDINGS,
                        CardType.SCIENTIFIC_BUILDINGS,
                        CardType.COMMERCIAL_BUILDINGS,

                };
        //sans les cartes Guildes (9 car 7joueurs)
        for (int i = 0; i < deckGot.getLength()-9; i++)
        {
            assertEquals(deckGot.getCard(i).getType(), types[i]);
        }
    }

    @Disabled
    public void testAgeThreeCardsEffect ()
    {
        IEffect[] expected = new IEffect[]
                {
                        new VictoryPointEffect(7),
                        new VictoryPointEffect(5),
                        new VictoryPointEffect(6),
                        new VictoryPointEffect(8),

                        new EarnWithCardEffect(new EarnWithCard(1,1,TargetType.ME,CardType.RAW_MATERIALS)),
                        new EarnWithCardEffect(new EarnWithCard(1,1,TargetType.ME,CardType.COMMERCIAL_BUILDINGS)),

                        new MilitaryEffect(3),
                        new MilitaryEffect(3),
                        new MilitaryEffect(3),

                        new ScientificEffect(ScientificType.GEOMETRY),
                        new ScientificEffect(ScientificType.GEOGRAPHY),
                        new ScientificEffect(ScientificType.LITERATURE),
                        new ScientificEffect(ScientificType.GEOMETRY),
                        new ScientificEffect(ScientificType.GEOGRAPHY),
                        null, //TODO new EarnWithCardEffect(); //arene
                        new VictoryPointEffect(6),

                        new VictoryPointEffect(5),
                        new EarnWithCardEffect(new EarnWithCard(1,1,TargetType.ME,CardType.RAW_MATERIALS)),
                        new EarnWithCardEffect(new EarnWithCard(2,2,TargetType.ME,CardType.MANUFACTURED_PRODUCTS)),
                        new MilitaryEffect(3),
                        new MilitaryEffect(3),
                        new ScientificEffect(ScientificType.LITERATURE),

                        new VictoryPointEffect(6),
                        new MilitaryEffect(3),
                        new ScientificEffect(ScientificType.GEOGRAPHY),
                        null, //TODO new EarnWithCardEffect(); //arene
                        new MilitaryEffect(3),

                        new VictoryPointEffect(7),
                        new VictoryPointEffect(6),
                        new EarnWithCardEffect(new EarnWithCard(1,1,TargetType.ME,CardType.COMMERCIAL_BUILDINGS)),
                        new EarnWithCardEffect(new EarnWithCard(2,2,TargetType.ME,CardType.MANUFACTURED_PRODUCTS)),
                        new MilitaryEffect(3),
                        new ScientificEffect(ScientificType.GEOMETRY),

                        new VictoryPointEffect(8),
                        new MilitaryEffect(3),
                        new MilitaryEffect(3),
                        new ScientificEffect(ScientificType.GEOGRAPHY),
                        new ScientificEffect(ScientificType.GEOMETRY),
                        null//TODO new EarnWithCardEffect(); //arene
                };

        Deck deckGot = this.cardFactory.AgeThreeCards(7);


        //sans les cartes Guildes (9 car 7joueurs)
        for (int i = 0; i < deckGot.getLength() - 9; i++)
        {
            assertEquals(deckGot.getCard(i).getCardEffect().getScore(), expected[i].getScore());

            if (deckGot.getCard(i).getCardEffect().getMaterials().length > 0)
            {
                for (int k = 0; k < deckGot.getCard(i).getCardEffect().getMaterials().length; k++)
                {
                    assertEquals(deckGot.getCard(i).getCardEffect().getMaterials()[k].getNumber(), expected[i].getMaterials()[k].getNumber());
                    assertEquals(deckGot.getCard(i).getCardEffect().getMaterials()[k].getType(), expected[i].getMaterials()[k].getType());
                }
            }

            if(deckGot.getCard(i).getCardEffect().getMaterials() != null)
            {
                for (int k = 0; k < deckGot.getCard(i).getCardEffect().getMaterials().length; k++)
                {
                    assertEquals(deckGot.getCard(i).getCardEffect().getMaterials()[k].getType(), expected[i].getMaterials()[k].getType());
                    assertEquals(deckGot.getCard(i).getCardEffect().getMaterials()[k].getNumber(), expected[i].getMaterials()[k].getNumber());
                }
            }
        }
    }

    @Test
    public void testNumberGuildCardByNumberOfPlayer()
    {
        for (int i=3; i <= 7; i++)
        {
            int numberOfGuildCard = 0;
            Deck deckGot = this.cardFactory.AgeThreeCards(i);
            for(int j = 0; j < deckGot.getLength(); j++)
            {
                if (deckGot.getCard(j).getType().equals(CardType.GUILD_BUILDINGS)) numberOfGuildCard++;
            }
            assertEquals(numberOfGuildCard, i+2); // 2 cartes de guildes de plus que le nombre de joueur
        }
    }
}