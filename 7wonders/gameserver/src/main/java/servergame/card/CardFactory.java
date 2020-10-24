package servergame.card;

import commun.card.Card;
import commun.card.CardType;
import commun.card.Deck;
import commun.cost.CoinCost;
import commun.cost.MaterialCost;
import commun.effect.*;
import commun.effect.guild.ScientistsGuildEffect;
import commun.effect.guild.StrategistsGuild;
import commun.material.ChoiceMaterial;
import commun.material.Material;
import commun.material.MaterialType;
import commun.utils.SingletonRandom;

import java.util.Random;

/** CardFactory permet de generer les paquets de cartes selon l'Age */
public class CardFactory {
    /* Champs */
    Random r;

    /** Constructeur */
    public CardFactory(){
        r = SingletonRandom.getInstance();
    }

    /**
     * Créer le deck de l'age 1
     * @return Deck : le deck qui contient les cartes créées.
     */
    public Deck AgeOneCards(int nbPlayer){

        Deck deck1 = new Deck();

        if(nbPlayer>=3){
            //Cartes Bleues (Batiment civil)
            deck1.addCard(new Card("BAINS", CardType.CIVIL_BUILDING, new VictoryPointEffect(3),1,new MaterialCost(new Material(MaterialType.STONE,1)),"AQUEDUC"));//3+
            deck1.addCard(new Card("AUTEL", CardType.CIVIL_BUILDING, new VictoryPointEffect(2),1,null,"TEMPLE"));//3+
            deck1.addCard(new Card("THÉÂTRE", CardType.CIVIL_BUILDING, new VictoryPointEffect(2),1,null,"STATUE"));//3+


            //Cartes Marrons (Matiéres premières)
            deck1.addCard(new Card("CHANTIER", CardType.RAW_MATERIALS, new ChoiceMaterialEffect ( new ChoiceMaterial(new Material(MaterialType.WOOD,1 ))),1,null)); //3+
            deck1.addCard(new Card("CAVITÉ", CardType.RAW_MATERIALS, new ChoiceMaterialEffect ( new ChoiceMaterial(new Material(MaterialType.STONE,1 ))),1,null)); //3+
            deck1.addCard(new Card("BASSIN ARGILEUX", CardType.RAW_MATERIALS, new ChoiceMaterialEffect ( new ChoiceMaterial(new Material(MaterialType.CLAY,1 ))),1,null)); //3+
            deck1.addCard(new Card("FILON", CardType.RAW_MATERIALS, new ChoiceMaterialEffect ( new ChoiceMaterial(new Material(MaterialType.ORES,1 ))),1,null)); //3+
            deck1.addCard(new Card("FOSSE ARGILEUSE", CardType.RAW_MATERIALS, new ChoiceMaterialEffect(new ChoiceMaterial( new Material(MaterialType.CLAY,1) , new Material(MaterialType.ORES,1))),1,new CoinCost(1))); //3+
            deck1.addCard(new Card("EXPLOITATION FORESTIÉRE", CardType.RAW_MATERIALS, new ChoiceMaterialEffect(new ChoiceMaterial( new Material(MaterialType.STONE,1) , new Material(MaterialType.WOOD,1))),1,new CoinCost(1))); //3+



            //Cartes Grises (Produits manufacturés)
            deck1.addCard(new Card("VERRERIE", CardType.MANUFACTURED_PRODUCTS, new ChoiceMaterialEffect ( new ChoiceMaterial(new Material(MaterialType.GLASS, 1))), 1,null));//3+
            deck1.addCard(new Card("MÉTIER À TISSER", CardType.MANUFACTURED_PRODUCTS, new ChoiceMaterialEffect ( new ChoiceMaterial(new Material(MaterialType.FABRIC,1))),1,null));//3+
            deck1.addCard(new Card("PRESSE", CardType.MANUFACTURED_PRODUCTS, new ChoiceMaterialEffect ( new ChoiceMaterial(new Material(MaterialType.PAPYRUS, 1))), 1,null));//3+

            //Les carte qui permettent à un joueur d'acheter des materiaux à 1 coin chez les voisin
            // 0 = voisin de droite // 1 = voisin de gauche // 2 = les deux voisins
            deck1.addCard(new Card("COMPTOIR EST", CardType.COMMERCIAL_BUILDINGS , new OneCoinNeighborEffect(TargetType.RIGHT_NEIGHTBOUR, new Material(MaterialType.WOOD,1),new Material(MaterialType.CLAY,1), new Material(MaterialType.STONE,1), new Material(MaterialType.ORES,1)),1,null,"FORUM")); //+3

            deck1.addCard(new Card("COMPTOIR OUEST", CardType.COMMERCIAL_BUILDINGS , new OneCoinNeighborEffect(TargetType.LEFT_NEIGHTBOUR, new Material(MaterialType.WOOD,1),new Material(MaterialType.CLAY,1), new Material(MaterialType.STONE,1), new Material(MaterialType.ORES,1)),1,null,"FORUM")); //+3

            deck1.addCard(new Card("MARCHÉ", CardType.COMMERCIAL_BUILDINGS , new OneCoinNeighborEffect(TargetType.BOTH_NEIGHTBOUR,new Material(MaterialType.GLASS,1), new Material(MaterialType.PAPYRUS,1), new Material(MaterialType.FABRIC,1)),1,null,"CARAVANSÉRAIL")); //+3


            //Carte Rouge ( MILITARY BUILDINGS )
            deck1.addCard(new Card("PALISSADE", CardType.MILITARY_BUILDINGS , new MilitaryEffect(1),1,new MaterialCost(new Material(MaterialType.WOOD,1)))); //+3
            deck1.addCard(new Card("CASERNE", CardType.MILITARY_BUILDINGS , new MilitaryEffect(1),1,new MaterialCost(new Material(MaterialType.ORES,1)))); //+3
            deck1.addCard(new Card("TOUR DE GARDE", CardType.MILITARY_BUILDINGS , new MilitaryEffect(1),1,new MaterialCost(new Material(MaterialType.CLAY,1)))); //+3

            //Carte Verte (Bâtiments scientifique)
            deck1.addCard(new Card("OFFICINE", CardType.SCIENTIFIC_BUILDINGS , new ScientificEffect(ScientificType.GEOMETRY),1,new MaterialCost(new Material(MaterialType.FABRIC,1)),"ÉCURIES","DISPENSAIRE")); //+3
            deck1.addCard(new Card("ATELIER", CardType.SCIENTIFIC_BUILDINGS , new ScientificEffect(ScientificType.GEOGRAPHY),1,new MaterialCost(new Material(MaterialType.GLASS,1)),"CHAMPS DE TIR","LABORATOIRE")); //+3
            deck1.addCard(new Card("SCRIPTORIUM", CardType.SCIENTIFIC_BUILDINGS , new ScientificEffect(ScientificType.LITERATURE),1,new MaterialCost(new Material(MaterialType.PAPYRUS,1)),"TRIBUNAL","BIBLIOTHÈQUE")); //+3
        }
        if(nbPlayer>=4){
            //Cartes Bleues (Batiment civil)
            deck1.addCard(new Card("PRÊTEUR SUR GAGES", CardType.CIVIL_BUILDING, new VictoryPointEffect(3),1,null));//4+


            //Cartes Marrons (Matiéres premières)
            deck1.addCard(new Card("CHANTIER", CardType.RAW_MATERIALS, new ChoiceMaterialEffect ( new ChoiceMaterial(new Material(MaterialType.WOOD,1 ))),1,null)); //4+
            deck1.addCard(new Card("FILON", CardType.RAW_MATERIALS, new ChoiceMaterialEffect ( new ChoiceMaterial(new Material(MaterialType.ORES,1 ))),1,null)); //4+

            deck1.addCard(new Card("EXCAVATION", CardType.RAW_MATERIALS, new ChoiceMaterialEffect(new ChoiceMaterial( new Material(MaterialType.STONE,1) , new Material(MaterialType.CLAY,1))),1,new CoinCost(1))); //4+


            //Cartes Grises (Produits manufacturés)
            deck1.addCard(new Card("TAVERNE", CardType.COMMERCIAL_BUILDINGS , new CoinEffect(5),1,null)); //+4


            //Carte Rouge ( MILITARY BUILDINGS )
            deck1.addCard(new Card("TOUR DE GARDE", CardType.MILITARY_BUILDINGS , new MilitaryEffect(1),1,new MaterialCost(new Material(MaterialType.CLAY,1)))); //+4

            //Carte Verte (Bâtiments scientifique)
            deck1.addCard(new Card("SCRIPTORIUM", CardType.SCIENTIFIC_BUILDINGS , new ScientificEffect(ScientificType.LITERATURE),1,new MaterialCost(new Material(MaterialType.PAPYRUS,1)),"TRIBUNAL","BIBLIOTHÈQUE")); //+4
        }
        if(nbPlayer>=5){
            //Cartes Bleues (Batiment civil)
            deck1.addCard(new Card("AUTEL", CardType.CIVIL_BUILDING, new VictoryPointEffect(2),1,null,"TEMPLE"));//5+


            //Cartes Marrons (Matiéres premières)
            deck1.addCard(new Card("CAVITÉ", CardType.RAW_MATERIALS, new ChoiceMaterialEffect ( new ChoiceMaterial(new Material(MaterialType.STONE,1 ))),1,null)); //5+
            deck1.addCard(new Card("BASSIN ARGILEUX", CardType.RAW_MATERIALS, new ChoiceMaterialEffect ( new ChoiceMaterial(new Material(MaterialType.CLAY,1 ))),1,null)); //5+
            deck1.addCard(new Card("GISEMENT", CardType.RAW_MATERIALS, new ChoiceMaterialEffect(new ChoiceMaterial( new Material(MaterialType.WOOD,1) , new Material(MaterialType.ORES,1))),1,new CoinCost(1))); //5+

            //Carte Jaune (Bâtiments commerciaux )
            deck1.addCard(new Card("TAVERNE", CardType.COMMERCIAL_BUILDINGS , new CoinEffect(5),1,null)); //+5


            //Carte Rouge ( MILITARY BUILDINGS )
            deck1.addCard(new Card("CASERNE", CardType.MILITARY_BUILDINGS , new MilitaryEffect(1),1,new MaterialCost(new Material(MaterialType.ORES,1)))); //+5


            //Carte Verte (Bâtiments scientifique)
            deck1.addCard(new Card("OFFICINE", CardType.SCIENTIFIC_BUILDINGS , new ScientificEffect(ScientificType.GEOMETRY),1,new MaterialCost(new Material(MaterialType.FABRIC,1)),"ÉCURIES","DISPENSAIRE")); //+5

        }
        if(nbPlayer>=6){
            //Cartes Bleues (Batiment civil)
            deck1.addCard(new Card("THÉÂTRE", CardType.CIVIL_BUILDING, new VictoryPointEffect(2),1,null,"STATUE"));//6+


            //Cartes Marrons (Matiéres premières)
            deck1.addCard(new Card("FRICHE", CardType.RAW_MATERIALS, new ChoiceMaterialEffect(new ChoiceMaterial( new Material(MaterialType.WOOD,1) , new Material(MaterialType.CLAY,1))), 1,new CoinCost(1))); //6+
            deck1.addCard(new Card("MINE", CardType.RAW_MATERIALS, new ChoiceMaterialEffect(new ChoiceMaterial( new Material(MaterialType.STONE,1) , new Material(MaterialType.ORES,1))),1,new CoinCost(1))); //6+


            //Cartes Grises (Produits manufacturés)
            deck1.addCard(new Card("VERRERIE", CardType.MANUFACTURED_PRODUCTS, new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.GLASS, 1))), 1,null));//6+
            deck1.addCard(new Card("MÉTIER À TISSER", CardType.MANUFACTURED_PRODUCTS, new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.FABRIC,1))),1,null));//6+
            deck1.addCard(new Card("PRESSE", CardType.MANUFACTURED_PRODUCTS, new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.PAPYRUS, 1))), 1,null));//6+

            //Les carte qui permettent à un joueur d'acheter des materiaux à 1 coin chez les voisin
            // 0 = voisin de droite // 1 = voisin de gauche // 2 = les deux voisins
            deck1.addCard(new Card("MARCHÉ", CardType.COMMERCIAL_BUILDINGS , new OneCoinNeighborEffect(TargetType.BOTH_NEIGHTBOUR,new Material(MaterialType.GLASS,1), new Material(MaterialType.PAPYRUS,1), new Material(MaterialType.FABRIC,1)),1,null,"CARAVANSÉRAIL")); //+6

        }
        if(nbPlayer>=7){
            //Cartes Bleues (Batiment civil)
            deck1.addCard(new Card("PRÊTEUR SUR GAGES", CardType.CIVIL_BUILDING, new VictoryPointEffect(3),1,null));//7+
            deck1.addCard(new Card("BAINS", CardType.CIVIL_BUILDING, new VictoryPointEffect(3),1,new MaterialCost(new Material(MaterialType.STONE,1)),"AQUEDUC"));//7+

            //Carte Jaune (Bâtiments commerciaux )
            deck1.addCard(new Card("TAVERNE", CardType.COMMERCIAL_BUILDINGS , new CoinEffect(5),1,null)); //+7

            //Les carte qui permettent à un joueur d'acheter des materiaux à 1 coin chez les voisin
            // 0 = voisin de droite // 1 = voisin de gauche // 2 = les deux voisins
            deck1.addCard(new Card("COMPTOIR EST", CardType.COMMERCIAL_BUILDINGS , new OneCoinNeighborEffect(TargetType.RIGHT_NEIGHTBOUR,new Material(MaterialType.WOOD,1),new Material(MaterialType.CLAY,1), new Material(MaterialType.STONE,1), new Material(MaterialType.ORES,1)),1,null)); //+7
            deck1.addCard(new Card("COMPTOIR OUEST", CardType.COMMERCIAL_BUILDINGS , new OneCoinNeighborEffect(TargetType.LEFT_NEIGHTBOUR,new Material(MaterialType.WOOD,1),new Material(MaterialType.CLAY,1), new Material(MaterialType.STONE,1), new Material(MaterialType.ORES,1)),1,null)); //+7


            //Carte Rouge ( MILITARY BUILDINGS )
            deck1.addCard(new Card("PALISSADE", CardType.MILITARY_BUILDINGS , new MilitaryEffect(1),1,new MaterialCost(new Material(MaterialType.WOOD,1)))); //+7


            //Carte Verte (Bâtiments scientifique)
            deck1.addCard(new Card("ATELIER", CardType.SCIENTIFIC_BUILDINGS , new ScientificEffect(ScientificType.GEOGRAPHY),1,new MaterialCost(new Material(MaterialType.GLASS,1)),"CHAMPS DE TIR","LABORATOIRE")); //+7

        }



        return deck1;
    }

    /**
     * Créer le deck de l'age 2
     * @return Deck : le deck qui contient les cartes créées.
     */
    public Deck AgeTwoCards(int nbPlayers){

        Deck deck2 = new Deck();

        if (nbPlayers >= 3) {
            // Conflits militaires
            deck2.addCard(new Card("MURAILLE", CardType.MILITARY_BUILDINGS, new MilitaryEffect(2), 2, new MaterialCost(new Material(MaterialType.STONE, 3)),"FORTIFICATIONS")); //+3
            deck2.addCard(new Card("ÉCURIES", CardType.MILITARY_BUILDINGS, new MilitaryEffect(2), 2, new MaterialCost(new Material(MaterialType.WOOD, 1),new Material(MaterialType.ORES, 1),new Material(MaterialType.CLAY, 1)))); //+3
            deck2.addCard(new Card("CHAMPS DE TIR", CardType.MILITARY_BUILDINGS, new MilitaryEffect(2), 2, new MaterialCost(new Material(MaterialType.WOOD, 1)))); //+3

            // cartes verte (Bâtiments scientifique)
            deck2.addCard(new Card("DISPENSAIRE", CardType.SCIENTIFIC_BUILDINGS , new ScientificEffect(ScientificType.GEOMETRY),2,new MaterialCost(new Material(MaterialType.ORES,2) ,new Material(MaterialType.GLASS,1)),"ARÈNE","LOGE")); //+3
            deck2.addCard(new Card("LABORATOIRE", CardType.SCIENTIFIC_BUILDINGS , new ScientificEffect(ScientificType.GEOGRAPHY),2,new MaterialCost(new Material(MaterialType.CLAY,2) ,new Material(MaterialType.PAPYRUS,1)),"ATELIER DE SIEGE","OBSERVATOIRE")); //+3
            deck2.addCard(new Card("BIBLIOTHÈQUE", CardType.SCIENTIFIC_BUILDINGS , new ScientificEffect(ScientificType.LITERATURE),2,new MaterialCost(new Material(MaterialType.STONE,2) ,new Material(MaterialType.FABRIC,1)),"SÉNAT","UNIVERSITÉ")); //+3
            deck2.addCard(new Card("ÉCOLE", CardType.SCIENTIFIC_BUILDINGS , new ScientificEffect(ScientificType.LITERATURE),2,new MaterialCost(new Material(MaterialType.WOOD,1) ,new Material(MaterialType.PAPYRUS,1)),"ACADEMIE","ÉTUDE")); //+3

            //Carte Jaune (Bâtiments commerciaux )
            deck2.addCard(new Card("FORUM", CardType.COMMERCIAL_BUILDINGS , new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.PAPYRUS,1),new Material(MaterialType.FABRIC,1), new Material(MaterialType.GLASS,1))) ,2,new MaterialCost(new Material(MaterialType.CLAY,2)),"PORT" ));
            deck2.addCard(new Card("CARAVANSÉRAIL", CardType.COMMERCIAL_BUILDINGS , new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.WOOD,1),new Material(MaterialType.ORES,1),new Material(MaterialType.STONE,1),new Material(MaterialType.CLAY,1))) ,2,new MaterialCost(new Material(MaterialType.WOOD,2) ),"PHARE"));
            deck2.addCard(new Card("VIGNOBLE", CardType.COMMERCIAL_BUILDINGS , new EarnWithCardEffect(new EarnWithCard(1,0,TargetType.ME_AND_NEIGHTBOUR,CardType.RAW_MATERIALS)) ,2,null ));

            //Cartes Marrons (Matiéres premières)
            deck2.addCard(new Card("SCIERIE", CardType.RAW_MATERIALS , new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.WOOD,2))) ,2,new CoinCost(1) ));
            deck2.addCard(new Card("CARRIÈRE", CardType.RAW_MATERIALS , new ChoiceMaterialEffect ( new ChoiceMaterial(new Material(MaterialType.STONE,2))) ,2,new CoinCost(1) ));
            deck2.addCard(new Card("BRIQUETERIE", CardType.RAW_MATERIALS , new ChoiceMaterialEffect ( new ChoiceMaterial(new Material(MaterialType.CLAY,2))) ,2,new CoinCost(1) ));
            deck2.addCard(new Card("FONDERIE", CardType.RAW_MATERIALS , new ChoiceMaterialEffect ( new ChoiceMaterial(new Material(MaterialType.ORES,2))) ,2,new CoinCost(1) ));

            //Cartes Grises (Produits manufacturés)
            deck2.addCard(new Card("MÉTIER À TISSER", CardType.COMMERCIAL_BUILDINGS , new ChoiceMaterialEffect ( new ChoiceMaterial(new Material(MaterialType.FABRIC,1))),2,null));
            deck2.addCard(new Card("VERRERIE", CardType.COMMERCIAL_BUILDINGS , new ChoiceMaterialEffect ( new ChoiceMaterial(new Material(MaterialType.GLASS,1))),2,null));
            deck2.addCard(new Card("PRESSE", CardType.COMMERCIAL_BUILDINGS , new ChoiceMaterialEffect ( new ChoiceMaterial(new Material(MaterialType.PAPYRUS,1))),2,null));

            //Cartes Bleues (Batiment civil)
            deck2.addCard(new Card("AQUEDUC", CardType.CIVIL_BUILDING, new VictoryPointEffect(5),2,new MaterialCost(new Material(MaterialType.STONE,3))));
            deck2.addCard(new Card("TEMPLE", CardType.CIVIL_BUILDING, new VictoryPointEffect(3),2,new MaterialCost(new Material(MaterialType.CLAY,1),new Material(MaterialType.WOOD,1),new Material(MaterialType.GLASS,1)),"PANTHÉON"));
            deck2.addCard(new Card("STATUE", CardType.CIVIL_BUILDING, new VictoryPointEffect(4),2,new MaterialCost(new Material(MaterialType.ORES,2),new Material(MaterialType.WOOD,1)),"JARDINS"));
            deck2.addCard(new Card("TRIBUNAL",CardType.CIVIL_BUILDING, new VictoryPointEffect(4),2,new MaterialCost(new Material(MaterialType.CLAY,2), new Material(MaterialType.FABRIC,1))));
        }


        if (nbPlayers >= 4){
            // Conflits militaires
            deck2.addCard(new Card("PLACE D'ARMES", CardType.MILITARY_BUILDINGS, new MilitaryEffect(2), 2, new MaterialCost(new Material(MaterialType.WOOD, 1),new Material(MaterialType.ORES, 2)),"CIRQUE")); //+4
            // cartes verte (Bâtiments scientifique)
            deck2.addCard(new Card("DISPENSAIRE", CardType.SCIENTIFIC_BUILDINGS , new ScientificEffect(ScientificType.GEOMETRY),2,new MaterialCost(new Material(MaterialType.ORES,2) ,new Material(MaterialType.GLASS,1)),"ARÈNE","LOGE"));
            //Carte Jaune (Bâtiments commerciaux )
            deck2.addCard(new Card("BAZAR", CardType.COMMERCIAL_BUILDINGS , new EarnWithCardEffect(new EarnWithCard(2,0,TargetType.ME_AND_NEIGHTBOUR,CardType.MANUFACTURED_PRODUCTS)) ,2,null ));
            //Cartes Marrons (Matiéres premières)
            deck2.addCard(new Card("SCIERIE", CardType.RAW_MATERIALS , new ChoiceMaterialEffect ( new ChoiceMaterial(new Material(MaterialType.WOOD,2))) ,2,new CoinCost(1) ));
            deck2.addCard(new Card("CARRIÈRE", CardType.RAW_MATERIALS , new ChoiceMaterialEffect ( new ChoiceMaterial(new Material(MaterialType.STONE,2))) ,2,new CoinCost(1) ));
            deck2.addCard(new Card("BRIQUETERIE", CardType.RAW_MATERIALS , new ChoiceMaterialEffect ( new ChoiceMaterial(new Material(MaterialType.CLAY,2))) ,2,new CoinCost(1) ));
            deck2.addCard(new Card("FONDERIE", CardType.RAW_MATERIALS , new ChoiceMaterialEffect ( new ChoiceMaterial(new Material(MaterialType.ORES,2))) ,2,new CoinCost(1) ));

        }
        if (nbPlayers >= 5){
            // Conflits militaires
            deck2.addCard(new Card("ÉCURIES", CardType.MILITARY_BUILDINGS, new MilitaryEffect(2), 2, new MaterialCost(new Material(MaterialType.WOOD, 1),new Material(MaterialType.ORES, 1),new Material(MaterialType.CLAY, 1))));
            // cartes verte (Bâtiments scientifique)
            deck2.addCard(new Card("LABORATOIRE", CardType.SCIENTIFIC_BUILDINGS , new ScientificEffect(ScientificType.GEOGRAPHY),2,new MaterialCost(new Material(MaterialType.CLAY,2) ,new Material(MaterialType.PAPYRUS,1)),"ATELIER DE SIEGE","OBSERVATOIRE"));
            //Carte Jaune (Bâtiments commerciaux )
            deck2.addCard(new Card("CARAVANSÉRAIL", CardType.COMMERCIAL_BUILDINGS , new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.WOOD,1),new Material(MaterialType.ORES,1),new Material(MaterialType.STONE,1),new Material(MaterialType.CLAY,1))) ,2,new MaterialCost(new Material(MaterialType.WOOD,2) ),"PHARE"));
            //Cartes Grises (Produits manufacturés)
            deck2.addCard(new Card("MÉTIER À TISSER", CardType.COMMERCIAL_BUILDINGS , new ChoiceMaterialEffect ( new ChoiceMaterial(new Material(MaterialType.FABRIC,1))),2,null));
            deck2.addCard(new Card("VERRERIE", CardType.COMMERCIAL_BUILDINGS , new ChoiceMaterialEffect ( new ChoiceMaterial(new Material(MaterialType.GLASS,1))),2,null));
            deck2.addCard(new Card("PRESSE", CardType.COMMERCIAL_BUILDINGS , new ChoiceMaterialEffect ( new ChoiceMaterial(new Material(MaterialType.PAPYRUS,1))),2,null));
            //Cartes Bleues (Batiment civil)
            deck2.addCard(new Card("TRIBUNAL",CardType.CIVIL_BUILDING, new VictoryPointEffect(4),2,new MaterialCost(new Material(MaterialType.CLAY,2), new Material(MaterialType.FABRIC,1))));

        }
        if (nbPlayers >= 6){
            // Conflits militaires
            deck2.addCard(new Card("PLACE D'ARMES", CardType.MILITARY_BUILDINGS, new MilitaryEffect(2), 2, new MaterialCost(new Material(MaterialType.WOOD, 1),new Material(MaterialType.ORES, 2)),"CIRQUE"));
            deck2.addCard(new Card("CHAMPS DE TIRS", CardType.MILITARY_BUILDINGS, new MilitaryEffect(2), 2, new MaterialCost(new Material(MaterialType.WOOD, 2),new Material(MaterialType.ORES, 1))));
            // cartes verte (Bâtiments scientifique)
            deck2.addCard(new Card("BIBLIOTHÈQUE", CardType.SCIENTIFIC_BUILDINGS , new ScientificEffect(ScientificType.LITERATURE),2,new MaterialCost(new Material(MaterialType.STONE,2) ,new Material(MaterialType.FABRIC,1)),"SÉNAT","UNIVERSITÉ"));
            //Carte Jaune (Bâtiments commerciaux )
            deck2.addCard(new Card("FORUM", CardType.COMMERCIAL_BUILDINGS , new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.PAPYRUS,1),new Material(MaterialType.FABRIC,1), new Material(MaterialType.GLASS,1))) ,2,new MaterialCost(new Material(MaterialType.CLAY,2)),"PORT"));
            deck2.addCard(new Card("CARAVANSÉRAIL", CardType.COMMERCIAL_BUILDINGS , new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.WOOD,1),new Material(MaterialType.ORES,1),new Material(MaterialType.STONE,1),new Material(MaterialType.CLAY,1))) ,2,new MaterialCost(new Material(MaterialType.WOOD,2) ),"PHARE"));
            deck2.addCard(new Card("VIGNOBLE", CardType.COMMERCIAL_BUILDINGS , new EarnWithCardEffect(new EarnWithCard(1,0,TargetType.ME_AND_NEIGHTBOUR,CardType.RAW_MATERIALS)) ,2,null ));
            //Cartes Bleues (Batiment civil)
            deck2.addCard(new Card("TEMPLE", CardType.CIVIL_BUILDING, new VictoryPointEffect(3),2,new MaterialCost(new Material(MaterialType.CLAY,1),new Material(MaterialType.WOOD,1),new Material(MaterialType.GLASS,1)),"PANTHÉON"));

        }
        if (nbPlayers >= 7){
            // Conflits militaires
            deck2.addCard(new Card("MURAILLE", CardType.MILITARY_BUILDINGS, new MilitaryEffect(2), 2, new MaterialCost(new Material(MaterialType.STONE, 3)),"FORTIFICATIONS"));
            deck2.addCard(new Card("PLACE D'ARMES", CardType.MILITARY_BUILDINGS, new MilitaryEffect(2), 2, new MaterialCost(new Material(MaterialType.WOOD, 1),new Material(MaterialType.ORES, 2)),"CIRQUE"));
            // cartes verte (Bâtiments scientifique)
            deck2.addCard(new Card("ÉCOLE", CardType.SCIENTIFIC_BUILDINGS , new ScientificEffect(ScientificType.LITERATURE),2,new MaterialCost(new Material(MaterialType.WOOD,1) ,new Material(MaterialType.PAPYRUS,1)),"ACADEMIE","ÉTUDE"));
            //Carte Jaune (Bâtiments commerciaux )
            deck2.addCard(new Card("FORUM", CardType.COMMERCIAL_BUILDINGS , new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.PAPYRUS,1),new Material(MaterialType.FABRIC,1), new Material(MaterialType.GLASS,1))) ,2,new MaterialCost(new Material(MaterialType.CLAY,2)),"PORT"));
            deck2.addCard(new Card("BAZAR", CardType.COMMERCIAL_BUILDINGS , new EarnWithCardEffect(new EarnWithCard(2,0,TargetType.ME_AND_NEIGHTBOUR,CardType.MANUFACTURED_PRODUCTS)) ,2,null ));
            //Cartes Bleues (Batiment civil)
            deck2.addCard(new Card("AQUEDUC", CardType.CIVIL_BUILDING, new VictoryPointEffect(5),2,new MaterialCost(new Material(MaterialType.STONE,3))));
            deck2.addCard(new Card("STATUE", CardType.CIVIL_BUILDING, new VictoryPointEffect(4),2,new MaterialCost(new Material(MaterialType.ORES,2),new Material(MaterialType.WOOD,1)),"JARDINS"));

        }
        return deck2;
    }

     /**
     * Créer le deck de l'age 3
     * @return Deck : le deck qui contient les cartes créées.
     */
    public Deck AgeThreeCards (int nbPlayers)
    {
        Deck deck3 = new Deck();

        if (nbPlayers >= 3)
        {
            deck3.addCard(new Card("PANTHÉON", CardType.CIVIL_BUILDING, new VictoryPointEffect(7), 3, new MaterialCost(new Material(MaterialType.CLAY, 2), new Material(MaterialType.ORES, 1), new Material(MaterialType.PAPYRUS, 1), new Material(MaterialType.FABRIC, 1), new Material(MaterialType.GLASS, 1))));
            deck3.addCard(new Card("JARDINS", CardType.CIVIL_BUILDING, new VictoryPointEffect(5), 3, new MaterialCost(new Material(MaterialType.WOOD, 1), new Material(MaterialType.CLAY, 2))));
            deck3.addCard(new Card("HÔTEL DE VILLE", CardType.CIVIL_BUILDING, new VictoryPointEffect(6), 3, new MaterialCost(new Material(MaterialType.GLASS, 1), new Material(MaterialType.ORES, 1), new Material(MaterialType.STONE, 2))));
            deck3.addCard(new Card("PALACE", CardType.CIVIL_BUILDING, new VictoryPointEffect(8), 3, new MaterialCost(new Material(MaterialType.CLAY, 1), new Material(MaterialType.ORES, 1), new Material(MaterialType.PAPYRUS, 1), new Material(MaterialType.FABRIC, 1), new Material(MaterialType.GLASS, 1), new Material(MaterialType.WOOD, 1), new Material(MaterialType.STONE, 1))));
            deck3.addCard(new Card("PORT", CardType.COMMERCIAL_BUILDINGS , new EarnWithCardEffect(new EarnWithCard(1,1,TargetType.ME,CardType.RAW_MATERIALS)) ,3,new MaterialCost(new Material(MaterialType.WOOD, 1), new Material(MaterialType.ORES, 1), new Material(MaterialType.FABRIC, 1))));
            deck3.addCard(new Card("PHARE", CardType.COMMERCIAL_BUILDINGS , new EarnWithCardEffect(new EarnWithCard(1,1,TargetType.ME,CardType.COMMERCIAL_BUILDINGS)) ,3,new MaterialCost(new Material(MaterialType.GLASS, 1), new Material(MaterialType.STONE, 1))));
            deck3.addCard(new Card("FORTIFICATIONS", CardType.MILITARY_BUILDINGS, new MilitaryEffect(3), 3, new MaterialCost(new Material(MaterialType.STONE, 1), new Material(MaterialType.ORES, 3))));
            deck3.addCard(new Card("ARSENAL", CardType.MILITARY_BUILDINGS, new MilitaryEffect(3), 3, new MaterialCost(new Material(MaterialType.WOOD, 2), new Material(MaterialType.ORES, 1), new Material(MaterialType.FABRIC, 1))));
            deck3.addCard(new Card("ATELIER DE SIEGE", CardType.MILITARY_BUILDINGS, new MilitaryEffect(3), 3, new MaterialCost(new Material(MaterialType.WOOD, 1), new Material(MaterialType.CLAY, 3))));
            deck3.addCard(new Card("LOGE",CardType.SCIENTIFIC_BUILDINGS,new ScientificEffect(ScientificType.GEOMETRY),3,new MaterialCost(new Material(MaterialType.CLAY,2), new Material(MaterialType.FABRIC,1),new Material(MaterialType.PAPYRUS,1))));
            deck3.addCard(new Card("OBSERVATOIRE",CardType.SCIENTIFIC_BUILDINGS,new ScientificEffect(ScientificType.GEOGRAPHY),3,new MaterialCost(new Material(MaterialType.ORES,2), new Material(MaterialType.GLASS,1),new Material(MaterialType.FABRIC,1))));
            deck3.addCard(new Card("UNIVERSITE",CardType.SCIENTIFIC_BUILDINGS,new ScientificEffect(ScientificType.LITERATURE),3,new MaterialCost(new Material(MaterialType.WOOD,2), new Material(MaterialType.PAPYRUS,1),new Material(MaterialType.GLASS,1))));
            deck3.addCard(new Card("ACADEMIE",CardType.SCIENTIFIC_BUILDINGS,new ScientificEffect(ScientificType.GEOMETRY),3,new MaterialCost(new Material(MaterialType.STONE,3), new Material(MaterialType.GLASS,1))));
            deck3.addCard(new Card("ETUDE",CardType.SCIENTIFIC_BUILDINGS,new ScientificEffect(ScientificType.GEOGRAPHY),3,new MaterialCost(new Material(MaterialType.WOOD,1), new Material(MaterialType.PAPYRUS,1), new Material(MaterialType.FABRIC,1))));
            deck3.addCard(new Card("ARENE",CardType.COMMERCIAL_BUILDINGS, new EarnWithWonderEffect(new EarnWithWonder(TargetType.ME,3,1)),3,new MaterialCost(new Material(MaterialType.ORES,1), new Material(MaterialType.STONE,2))));//TODO
            deck3.addCard(new Card("SENAT", CardType.CIVIL_BUILDING, new VictoryPointEffect(6), 3, new MaterialCost(new Material(MaterialType.ORES, 1), new Material(MaterialType.STONE, 1), new Material(MaterialType.WOOD, 2))));
        }
        if (nbPlayers >= 4)
        {
            deck3.addCard(new Card("JARDINS", CardType.CIVIL_BUILDING, new VictoryPointEffect(5), 3, new MaterialCost(new Material(MaterialType.WOOD, 1), new Material(MaterialType.CLAY, 2))));
            deck3.addCard(new Card("PORT", CardType.COMMERCIAL_BUILDINGS , new EarnWithCardEffect(new EarnWithCard(1,1,TargetType.ME,CardType.RAW_MATERIALS)) ,3,new MaterialCost(new Material(MaterialType.WOOD, 1), new Material(MaterialType.ORES, 1), new Material(MaterialType.FABRIC, 1)) ));
            deck3.addCard(new Card("CHAMBRE DE COMMERCE", CardType.COMMERCIAL_BUILDINGS , new EarnWithCardEffect(new EarnWithCard(2,2,TargetType.ME,CardType.MANUFACTURED_PRODUCTS)) ,3,new MaterialCost(new Material(MaterialType.CLAY, 2), new Material(MaterialType.PAPYRUS, 1))));
            deck3.addCard(new Card("CIRQUE", CardType.MILITARY_BUILDINGS, new MilitaryEffect(3), 3, new MaterialCost(new Material(MaterialType.STONE, 3), new Material(MaterialType.ORES, 1))));
            deck3.addCard(new Card("ARSENAL", CardType.MILITARY_BUILDINGS, new MilitaryEffect(3), 3, new MaterialCost(new Material(MaterialType.WOOD, 2), new Material(MaterialType.ORES, 1), new Material(MaterialType.FABRIC, 1))));
            deck3.addCard(new Card("UNIVERSITE",CardType.SCIENTIFIC_BUILDINGS,new ScientificEffect(ScientificType.LITERATURE),3,new MaterialCost(new Material(MaterialType.WOOD,2), new Material(MaterialType.PAPYRUS,1),new Material(MaterialType.GLASS,1))));
        }
        if (nbPlayers >= 5)
        {
            deck3.addCard(new Card("HÔTEL DE VILLE", CardType.CIVIL_BUILDING, new VictoryPointEffect(6), 3, new MaterialCost(new Material(MaterialType.GLASS, 1), new Material(MaterialType.ORES, 1), new Material(MaterialType.STONE, 2))));
            deck3.addCard(new Card("CIRQUE", CardType.MILITARY_BUILDINGS, new MilitaryEffect(3), 3, new MaterialCost(new Material(MaterialType.STONE, 3), new Material(MaterialType.ORES, 1))));
            deck3.addCard(new Card("ETUDE",CardType.SCIENTIFIC_BUILDINGS,new ScientificEffect(ScientificType.GEOGRAPHY),3,new MaterialCost(new Material(MaterialType.WOOD,1), new Material(MaterialType.PAPYRUS,1), new Material(MaterialType.FABRIC,1))));
            deck3.addCard(new Card("ARENE",CardType.COMMERCIAL_BUILDINGS, new EarnWithWonderEffect(new EarnWithWonder(TargetType.ME,3,1)),3,new MaterialCost(new Material(MaterialType.ORES,1), new Material(MaterialType.STONE,2))));//TODO
            deck3.addCard(new Card("ATELIER DE SIEGE", CardType.MILITARY_BUILDINGS, new MilitaryEffect(3), 3, new MaterialCost(new Material(MaterialType.WOOD, 1), new Material(MaterialType.CLAY, 3))));
        }
        if (nbPlayers >= 6)
        {
            deck3.addCard(new Card("PANTHÉON", CardType.CIVIL_BUILDING, new VictoryPointEffect(7), 3, new MaterialCost(new Material(MaterialType.CLAY, 2), new Material(MaterialType.ORES, 1), new Material(MaterialType.PAPYRUS, 1), new Material(MaterialType.FABRIC, 1), new Material(MaterialType.GLASS, 1))));
            deck3.addCard(new Card("HÔTEL DE VILLE", CardType.CIVIL_BUILDING, new VictoryPointEffect(6), 3, new MaterialCost(new Material(MaterialType.GLASS, 1), new Material(MaterialType.ORES, 1), new Material(MaterialType.STONE, 2))));
            deck3.addCard(new Card("PHARE", CardType.COMMERCIAL_BUILDINGS , new EarnWithCardEffect(new EarnWithCard(1,1,TargetType.ME,CardType.COMMERCIAL_BUILDINGS)) ,3,new MaterialCost(new Material(MaterialType.GLASS, 1), new Material(MaterialType.STONE, 1))));
            deck3.addCard(new Card("CHAMBRE DE COMMERCE", CardType.COMMERCIAL_BUILDINGS , new EarnWithCardEffect(new EarnWithCard(2,2,TargetType.ME,CardType.MANUFACTURED_PRODUCTS)) ,3,new MaterialCost(new Material(MaterialType.CLAY, 2), new Material(MaterialType.PAPYRUS, 1))));
            deck3.addCard(new Card("CIRQUE", CardType.MILITARY_BUILDINGS, new MilitaryEffect(3), 3, new MaterialCost(new Material(MaterialType.STONE, 3), new Material(MaterialType.ORES, 1))));
            deck3.addCard(new Card("LOGE",CardType.SCIENTIFIC_BUILDINGS,new ScientificEffect(ScientificType.GEOMETRY),3,new MaterialCost(new Material(MaterialType.CLAY,2), new Material(MaterialType.FABRIC,1),new Material(MaterialType.PAPYRUS,1))));
        }
        if (nbPlayers >= 7)
        {
            deck3.addCard(new Card("PALACE", CardType.CIVIL_BUILDING, new VictoryPointEffect(8), 3, new MaterialCost(new Material(MaterialType.CLAY, 1), new Material(MaterialType.ORES, 1), new Material(MaterialType.PAPYRUS, 1), new Material(MaterialType.FABRIC, 1), new Material(MaterialType.GLASS, 1), new Material(MaterialType.WOOD, 1), new Material(MaterialType.STONE, 1))));
            deck3.addCard(new Card("FORTIFICATIONS", CardType.MILITARY_BUILDINGS, new MilitaryEffect(3), 3, new MaterialCost(new Material(MaterialType.STONE, 1), new Material(MaterialType.ORES, 3))));
            deck3.addCard(new Card("ARSENAL", CardType.MILITARY_BUILDINGS, new MilitaryEffect(3), 3, new MaterialCost(new Material(MaterialType.WOOD, 2), new Material(MaterialType.ORES, 1), new Material(MaterialType.FABRIC, 1))));
            deck3.addCard(new Card("OBSERVATOIRE",CardType.SCIENTIFIC_BUILDINGS,new ScientificEffect(ScientificType.GEOGRAPHY),3,new MaterialCost(new Material(MaterialType.ORES,2), new Material(MaterialType.GLASS,1),new Material(MaterialType.FABRIC,1))));
            deck3.addCard(new Card("ACADEMIE",CardType.SCIENTIFIC_BUILDINGS,new ScientificEffect(ScientificType.GEOMETRY),3,new MaterialCost(new Material(MaterialType.STONE,3), new Material(MaterialType.GLASS,1))));
            deck3.addCard(new Card("ARENE",CardType.COMMERCIAL_BUILDINGS, new EarnWithWonderEffect(new EarnWithWonder(TargetType.ME,3,1)),3,new MaterialCost(new Material(MaterialType.ORES,1), new Material(MaterialType.STONE,2))));//TODO

        }

        // AJOUTE DES CARTES DE GUILDES
        Deck guildCards = createGuildCard();
        int index;

        //Nombre carte guilde = nb de joueur +2
        for(int i = 0; i<nbPlayers+2;i++){
            index = r.nextInt(guildCards.getLength());
            deck3.addCard(guildCards.getCard(index));
            guildCards.removeCard(index);
        }
        
        return deck3;
    }

    /**
     * Créer toutes les cartes de guildes.
     * @return le deck des cartes de guildes.
     */
    private Deck createGuildCard(){
        Deck guildCard = new Deck();

        guildCard.addCard(new Card("GUILDE DES TRAVAILLEURS",CardType.GUILD_BUILDINGS,new EarnWithCardEffect(new EarnWithCard(0,1,TargetType.BOTH_NEIGHTBOUR,CardType.RAW_MATERIALS)),3,new MaterialCost(new Material(MaterialType.ORES,2),new Material(MaterialType.CLAY,1),new Material(MaterialType.STONE,1),new Material(MaterialType.WOOD,1))));
        guildCard.addCard(new Card("GUILDE DES ARTISANS",CardType.GUILD_BUILDINGS,new EarnWithCardEffect(new EarnWithCard(0,2,TargetType.BOTH_NEIGHTBOUR,CardType.MANUFACTURED_PRODUCTS)),3,new MaterialCost(new Material(MaterialType.ORES,2),new Material(MaterialType.STONE,2))));
        guildCard.addCard(new Card("GUILDE DES COMMERÇANTS",CardType.GUILD_BUILDINGS,new EarnWithCardEffect(new EarnWithCard(0,1,TargetType.BOTH_NEIGHTBOUR,CardType.COMMERCIAL_BUILDINGS)),3,new MaterialCost(new Material(MaterialType.FABRIC,2),new Material(MaterialType.PAPYRUS,1),new Material(MaterialType.GLASS,1))));
        guildCard.addCard(new Card("GUILDE DES PHILOSOPHES",CardType.GUILD_BUILDINGS,new EarnWithCardEffect(new EarnWithCard(0,1,TargetType.BOTH_NEIGHTBOUR,CardType.SCIENTIFIC_BUILDINGS)),3,new MaterialCost(new Material(MaterialType.CLAY,3),new Material(MaterialType.FABRIC,1),new Material(MaterialType.PAPYRUS,1))));
        guildCard.addCard(new Card("GUILDE DES ESPIONS",CardType.GUILD_BUILDINGS,new EarnWithCardEffect(new EarnWithCard(0,1,TargetType.BOTH_NEIGHTBOUR,CardType.MILITARY_BUILDINGS)),3,new MaterialCost(new Material(MaterialType.CLAY,3),new Material(MaterialType.GLASS,1))));
        guildCard.addCard(new Card("GUILDE DES STRATÈGES",CardType.GUILD_BUILDINGS,new StrategistsGuild(),3,new MaterialCost(new Material(MaterialType.ORES,2),new Material(MaterialType.STONE,1),new Material(MaterialType.FABRIC,1))));
        guildCard.addCard(new Card("GUILDE DES ARMATEURS",CardType.GUILD_BUILDINGS,new EarnWithCardEffect(new EarnWithCard(0,1,TargetType.ME,CardType.RAW_MATERIALS,CardType.MANUFACTURED_PRODUCTS,CardType.GUILD_BUILDINGS)),3,new MaterialCost(new Material(MaterialType.WOOD,3),new Material(MaterialType.PAPYRUS,1),new Material(MaterialType.GLASS,1))));
        guildCard.addCard(new Card("GUILDE DES SCIENTIFIQUES",CardType.GUILD_BUILDINGS,new ScientistsGuildEffect(),3,new MaterialCost(new Material(MaterialType.WOOD,2),new Material(MaterialType.ORES,1),new Material(MaterialType.PAPYRUS,1))));//TODO
        guildCard.addCard(new Card("GUILDE DES MAGISTRATS",CardType.GUILD_BUILDINGS,new EarnWithCardEffect(new EarnWithCard(0,1,TargetType.BOTH_NEIGHTBOUR,CardType.CIVIL_BUILDING)),3,new MaterialCost(new Material(MaterialType.WOOD,3),new Material(MaterialType.STONE,1),new Material(MaterialType.FABRIC,1))));
        guildCard.addCard(new Card("GUILDE DES BÂTISSEURS",CardType.GUILD_BUILDINGS,new EarnWithWonderEffect(new EarnWithWonder(TargetType.ME_AND_NEIGHTBOUR,0,1)),3,new MaterialCost(new Material(MaterialType.STONE,2),new Material(MaterialType.CLAY,2),new Material(MaterialType.GLASS,1))));

        return guildCard;
    }
}
