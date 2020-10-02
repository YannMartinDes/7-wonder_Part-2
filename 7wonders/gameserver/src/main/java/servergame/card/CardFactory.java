package servergame.card;

import commun.card.Card;
import commun.card.CardType;
import commun.card.Deck;
import commun.cost.CoinCost;
import commun.cost.MaterialCost;
import commun.effect.*;
import commun.material.ChoiceMaterial;
import commun.material.Material;
import commun.material.MaterialType;
import commun.material.NeighbourMaterials;

public class CardFactory {

    /**
     * Créer le deck de l'age 1
     * @return Deck : le deck qui contient les cartes créées.
     */
    public Deck AgeOneCards(int nbPlayer){

        Deck deck1 = new Deck();

        if(nbPlayer>=3){
            //Cartes Bleues (Batiment civil)
            deck1.addCard(new Card("BAINS", CardType.CIVIL_BUILDING, new VictoryPointEffect(3),1,new MaterialCost(new Material(MaterialType.STONE,1))));//3+
            deck1.addCard(new Card("AUTEL", CardType.CIVIL_BUILDING, new VictoryPointEffect(2),1,null));//3+
            deck1.addCard(new Card("THÉÂTRE", CardType.CIVIL_BUILDING, new VictoryPointEffect(2),1,null));//3+


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
            deck1.addCard(new Card("COMPTOIR EST", CardType.COMMERCIAL_BUILDINGS , new OneCoinNeighbor(0,new NeighbourMaterials(new Material(MaterialType.WOOD,1),new Material(MaterialType.CLAY,1), new Material(MaterialType.STONE,1), new Material(MaterialType.ORES,1))),1,null)); //+3

            deck1.addCard(new Card("COMPTOIR OUEST", CardType.COMMERCIAL_BUILDINGS , new OneCoinNeighbor(1,new NeighbourMaterials(new Material(MaterialType.WOOD,1),new Material(MaterialType.CLAY,1), new Material(MaterialType.STONE,1), new Material(MaterialType.ORES,1))),1,null)); //+3

            deck1.addCard(new Card("MARCHÉ", CardType.COMMERCIAL_BUILDINGS , new OneCoinNeighbor(2,new NeighbourMaterials(new Material(MaterialType.GLASS,1), new Material(MaterialType.PAPYRUS,1), new Material(MaterialType.FABRIC,1))),1,null)); //+3


            //Carte Rouge ( MILITARY BUILDINGS )
            deck1.addCard(new Card("PALISSADE", CardType.MILITARY_BUILDINGS , new MilitaryEffect(1),1,new MaterialCost(new Material(MaterialType.WOOD,1)))); //+3
            deck1.addCard(new Card("CASERNE", CardType.MILITARY_BUILDINGS , new MilitaryEffect(1),1,new MaterialCost(new Material(MaterialType.ORES,1)))); //+3
            deck1.addCard(new Card("TOUR DE GARDE", CardType.MILITARY_BUILDINGS , new MilitaryEffect(1),1,new MaterialCost(new Material(MaterialType.CLAY,1)))); //+3

            //Carte Verte (Bâtiments scientifique)
            deck1.addCard(new Card("OFFICINE", CardType.SCIENTIFIC_BUILDINGS , new ScientificEffect(ScientificType.GEOMETRY),1,new MaterialCost(new Material(MaterialType.FABRIC,1)))); //+3
            deck1.addCard(new Card("ATELIER", CardType.SCIENTIFIC_BUILDINGS , new ScientificEffect(ScientificType.GEOGRAPHY),1,new MaterialCost(new Material(MaterialType.GLASS,1)))); //+3
            deck1.addCard(new Card("SCRIPTORIUM", CardType.SCIENTIFIC_BUILDINGS , new ScientificEffect(ScientificType.LITERATURE),1,new MaterialCost(new Material(MaterialType.PAPYRUS,1)))); //+3
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
            deck1.addCard(new Card("SCRIPTORIUM", CardType.SCIENTIFIC_BUILDINGS , new ScientificEffect(ScientificType.LITERATURE),1,new MaterialCost(new Material(MaterialType.PAPYRUS,1)))); //+4
        }
        if(nbPlayer>=5){
            //Cartes Bleues (Batiment civil)
            deck1.addCard(new Card("AUTEL", CardType.CIVIL_BUILDING, new VictoryPointEffect(2),1,null));//5+


            //Cartes Marrons (Matiéres premières)
            deck1.addCard(new Card("CAVITÉ", CardType.RAW_MATERIALS, new ChoiceMaterialEffect ( new ChoiceMaterial(new Material(MaterialType.STONE,1 ))),1,null)); //5+
            deck1.addCard(new Card("BASSIN ARGILEUX", CardType.RAW_MATERIALS, new ChoiceMaterialEffect ( new ChoiceMaterial(new Material(MaterialType.CLAY,1 ))),1,null)); //5+
            deck1.addCard(new Card("GISEMENT", CardType.RAW_MATERIALS, new ChoiceMaterialEffect(new ChoiceMaterial( new Material(MaterialType.WOOD,1) , new Material(MaterialType.ORES,1))),1,new CoinCost(1))); //5+

            //Carte Jaune (Bâtiments commerciaux )
            deck1.addCard(new Card("TAVERNE", CardType.COMMERCIAL_BUILDINGS , new CoinEffect(5),1,null)); //+5


            //Carte Rouge ( MILITARY BUILDINGS )
            deck1.addCard(new Card("CASERNE", CardType.MILITARY_BUILDINGS , new MilitaryEffect(1),1,new MaterialCost(new Material(MaterialType.ORES,1)))); //+5


            //Carte Verte (Bâtiments scientifique)
            deck1.addCard(new Card("OFFICINE", CardType.SCIENTIFIC_BUILDINGS , new ScientificEffect(ScientificType.GEOMETRY),1,new MaterialCost(new Material(MaterialType.FABRIC,1)))); //+5

        }
        if(nbPlayer>=6){
            //Cartes Bleues (Batiment civil)
            deck1.addCard(new Card("THÉÂTRE", CardType.CIVIL_BUILDING, new VictoryPointEffect(2),1,null));//6+


            //Cartes Marrons (Matiéres premières)
            deck1.addCard(new Card("FRICHE", CardType.RAW_MATERIALS, new ChoiceMaterialEffect(new ChoiceMaterial( new Material(MaterialType.WOOD,1) , new Material(MaterialType.CLAY,1))), 1,new CoinCost(1))); //6+
            deck1.addCard(new Card("MINE", CardType.RAW_MATERIALS, new ChoiceMaterialEffect(new ChoiceMaterial( new Material(MaterialType.STONE,1) , new Material(MaterialType.ORES,1))),1,new CoinCost(1))); //6+


            //Cartes Grises (Produits manufacturés)
            deck1.addCard(new Card("VERRERIE", CardType.MANUFACTURED_PRODUCTS, new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.GLASS, 1))), 1,null));//6+
            deck1.addCard(new Card("MÉTIER À TISSER", CardType.MANUFACTURED_PRODUCTS, new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.FABRIC,1))),1,null));//6+
            deck1.addCard(new Card("PRESSE", CardType.MANUFACTURED_PRODUCTS, new ChoiceMaterialEffect(new ChoiceMaterial(new Material(MaterialType.PAPYRUS, 1))), 1,null));//6+

            //Les carte qui permettent à un joueur d'acheter des materiaux à 1 coin chez les voisin
            // 0 = voisin de droite // 1 = voisin de gauche // 2 = les deux voisins
            deck1.addCard(new Card("MARCHÉ", CardType.COMMERCIAL_BUILDINGS , new OneCoinNeighbor(2,new NeighbourMaterials(new Material(MaterialType.GLASS,1), new Material(MaterialType.PAPYRUS,1), new Material(MaterialType.FABRIC,1))),1,null)); //+6

        }
        if(nbPlayer>=7){
            //Cartes Bleues (Batiment civil)
            deck1.addCard(new Card("PRÊTEUR SUR GAGES", CardType.CIVIL_BUILDING, new VictoryPointEffect(3),1,null));//7+
            deck1.addCard(new Card("BAINS", CardType.CIVIL_BUILDING, new VictoryPointEffect(3),1,new MaterialCost(new Material(MaterialType.STONE,1))));//7+

            //Carte Jaune (Bâtiments commerciaux )
            deck1.addCard(new Card("TAVERNE", CardType.COMMERCIAL_BUILDINGS , new CoinEffect(5),1,null)); //+7

            //Les carte qui permettent à un joueur d'acheter des materiaux à 1 coin chez les voisin
            // 0 = voisin de droite // 1 = voisin de gauche // 2 = les deux voisins
            deck1.addCard(new Card("COMPTOIR EST", CardType.COMMERCIAL_BUILDINGS , new OneCoinNeighbor(0,new NeighbourMaterials(new Material(MaterialType.WOOD,1),new Material(MaterialType.CLAY,1), new Material(MaterialType.STONE,1), new Material(MaterialType.ORES,1))),1,null)); //+7
            deck1.addCard(new Card("COMPTOIR OUEST", CardType.COMMERCIAL_BUILDINGS , new OneCoinNeighbor(1,new NeighbourMaterials(new Material(MaterialType.WOOD,1),new Material(MaterialType.CLAY,1), new Material(MaterialType.STONE,1), new Material(MaterialType.ORES,1))),1,null)); //+7


            //Carte Rouge ( MILITARY BUILDINGS )
            deck1.addCard(new Card("PALISSADE", CardType.MILITARY_BUILDINGS , new MilitaryEffect(1),1,new MaterialCost(new Material(MaterialType.WOOD,1)))); //+7


            //Carte Verte (Bâtiments scientifique)
            deck1.addCard(new Card("ATELIER", CardType.SCIENTIFIC_BUILDINGS , new ScientificEffect(ScientificType.GEOGRAPHY),1,new MaterialCost(new Material(MaterialType.GLASS,1)))); //+7

        }



        return deck1;
    }

    public Deck AgeTwoCards(int nbPlayers){

        Deck deck2 = new Deck();

        if (nbPlayers >= 3) {
            // Conflits militaires
            deck2.addCard(new Card("MURAILLE", CardType.MILITARY_BUILDINGS, new MilitaryEffect(2), 2, new MaterialCost(new Material(MaterialType.STONE, 3)))); //+3
            deck2.addCard(new Card("ÉCURIES", CardType.MILITARY_BUILDINGS, new MilitaryEffect(2), 2, new MaterialCost(new Material(MaterialType.WOOD, 1),new Material(MaterialType.ORES, 1),new Material(MaterialType.CLAY, 1)))); //+3
            deck2.addCard(new Card("CHAMPS DE TIR", CardType.MILITARY_BUILDINGS, new MilitaryEffect(2), 2, new MaterialCost(new Material(MaterialType.WOOD, 1)))); //+3

            // cartes verte (Bâtiments scientifique)
            deck2.addCard(new Card("DISPENSAIRE", CardType.SCIENTIFIC_BUILDINGS , new ScientificEffect(ScientificType.GEOMETRY),2,new MaterialCost(new Material(MaterialType.ORES,2) ,new Material(MaterialType.GLASS,1)))); //+3
            deck2.addCard(new Card("LABORATOIRE", CardType.SCIENTIFIC_BUILDINGS , new ScientificEffect(ScientificType.GEOGRAPHY),2,new MaterialCost(new Material(MaterialType.CLAY,2) ,new Material(MaterialType.PAPYRUS,1)))); //+3
            deck2.addCard(new Card("BIBLIOTHÈQUE", CardType.SCIENTIFIC_BUILDINGS , new ScientificEffect(ScientificType.LITERATURE),2,new MaterialCost(new Material(MaterialType.STONE,2) ,new Material(MaterialType.FABRIC,1)))); //+3
            deck2.addCard(new Card("ÉCOLE", CardType.SCIENTIFIC_BUILDINGS , new ScientificEffect(ScientificType.LITERATURE),2,new MaterialCost(new Material(MaterialType.WOOD,1) ,new Material(MaterialType.PAPYRUS,1)))); //+3

            //Carte Jaune (Bâtiments commerciaux )
            deck2.addCard(new Card("FORUM", CardType.COMMERCIAL_BUILDINGS , null ,2,new MaterialCost(new Material(MaterialType.CLAY,2) ))); //TODO effect
            deck2.addCard(new Card("CARAVANSÉRAIL", CardType.COMMERCIAL_BUILDINGS , null ,2,new MaterialCost(new Material(MaterialType.WOOD,2) )));//TODO effect
            deck2.addCard(new Card("VIGNOBLE", CardType.COMMERCIAL_BUILDINGS , new EarnWithCardEffect(new EarnWithCard(CardType.RAW_MATERIALS,1,0,true)) ,2,null ));//TODO effect

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
            deck2.addCard(new Card("TEMPLE", CardType.CIVIL_BUILDING, new VictoryPointEffect(3),2,new MaterialCost(new Material(MaterialType.CLAY,1),new Material(MaterialType.WOOD,1),new Material(MaterialType.GLASS,1))));
            deck2.addCard(new Card("STATUE", CardType.CIVIL_BUILDING, new VictoryPointEffect(4),2,new MaterialCost(new Material(MaterialType.ORES,2),new Material(MaterialType.WOOD,1))));

        }


        if (nbPlayers >= 4){
            // Conflits militaires
            deck2.addCard(new Card("PLACE D'ARMES", CardType.MILITARY_BUILDINGS, new MilitaryEffect(2), 2, new MaterialCost(new Material(MaterialType.WOOD, 1),new Material(MaterialType.ORES, 2)))); //+4
            // cartes verte (Bâtiments scientifique)
            deck2.addCard(new Card("DISPENSAIRE", CardType.SCIENTIFIC_BUILDINGS , new ScientificEffect(ScientificType.GEOMETRY),2,new MaterialCost(new Material(MaterialType.ORES,2) ,new Material(MaterialType.GLASS,1))));
            //Carte Jaune (Bâtiments commerciaux )
            deck2.addCard(new Card("BAZAR", CardType.COMMERCIAL_BUILDINGS , new EarnWithCardEffect(new EarnWithCard(CardType.MANUFACTURED_PRODUCTS,2,0,true)) ,2,null ));//TODO effect
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
            deck2.addCard(new Card("LABORATOIRE", CardType.SCIENTIFIC_BUILDINGS , new ScientificEffect(ScientificType.GEOGRAPHY),2,new MaterialCost(new Material(MaterialType.CLAY,2) ,new Material(MaterialType.PAPYRUS,1))));
            //Carte Jaune (Bâtiments commerciaux )
            deck2.addCard(new Card("CARAVANSÉRAIL", CardType.COMMERCIAL_BUILDINGS , null ,2,new MaterialCost(new Material(MaterialType.WOOD,2) )));//TODO effect
            //Cartes Grises (Produits manufacturés)
            deck2.addCard(new Card("MÉTIER À TISSER", CardType.COMMERCIAL_BUILDINGS , new ChoiceMaterialEffect ( new ChoiceMaterial(new Material(MaterialType.FABRIC,1))),2,null));
            deck2.addCard(new Card("VERRERIE", CardType.COMMERCIAL_BUILDINGS , new ChoiceMaterialEffect ( new ChoiceMaterial(new Material(MaterialType.GLASS,1))),2,null));
            deck2.addCard(new Card("PRESSE", CardType.COMMERCIAL_BUILDINGS , new ChoiceMaterialEffect ( new ChoiceMaterial(new Material(MaterialType.PAPYRUS,1))),2,null));
        }
        if (nbPlayers >= 6){
            // Conflits militaires
            deck2.addCard(new Card("PLACE D'ARMES", CardType.MILITARY_BUILDINGS, new MilitaryEffect(2), 2, new MaterialCost(new Material(MaterialType.WOOD, 1),new Material(MaterialType.ORES, 2))));
            deck2.addCard(new Card("CHAMPS DE TIRS", CardType.MILITARY_BUILDINGS, new MilitaryEffect(2), 2, new MaterialCost(new Material(MaterialType.WOOD, 2),new Material(MaterialType.ORES, 1))));
            // cartes verte (Bâtiments scientifique)
            deck2.addCard(new Card("BIBLIOTHÈQUE", CardType.SCIENTIFIC_BUILDINGS , new ScientificEffect(ScientificType.LITERATURE),2,new MaterialCost(new Material(MaterialType.STONE,2) ,new Material(MaterialType.FABRIC,1))));
            //Carte Jaune (Bâtiments commerciaux )
            deck2.addCard(new Card("FORUM", CardType.COMMERCIAL_BUILDINGS , new EarnWithCardEffect(new EarnWithCard(CardType.MANUFACTURED_PRODUCTS,2,0,true)) ,2,new MaterialCost(new Material(MaterialType.CLAY,2) )));  //TODO effect
            deck2.addCard(new Card("CARAVANSÉRAIL", CardType.COMMERCIAL_BUILDINGS , null ,2,new MaterialCost(new Material(MaterialType.WOOD,2) )));//TODO effect
            deck2.addCard(new Card("VIGNOBLE", CardType.COMMERCIAL_BUILDINGS , new EarnWithCardEffect(new EarnWithCard(CardType.RAW_MATERIALS,1,0,true)) ,2,null ));//TODO effect
            //Cartes Bleues (Batiment civil)
            deck2.addCard(new Card("TEMPLE", CardType.CIVIL_BUILDING, new VictoryPointEffect(3),2,new MaterialCost(new Material(MaterialType.CLAY,1),new Material(MaterialType.WOOD,1),new Material(MaterialType.GLASS,1))));

        }
        if (nbPlayers >= 7){
            // Conflits militaires
            deck2.addCard(new Card("MURAILLE", CardType.MILITARY_BUILDINGS, new MilitaryEffect(2), 2, new MaterialCost(new Material(MaterialType.STONE, 3))));
            deck2.addCard(new Card("PLACE D'ARMES", CardType.MILITARY_BUILDINGS, new MilitaryEffect(2), 2, new MaterialCost(new Material(MaterialType.WOOD, 1),new Material(MaterialType.ORES, 2))));
            // cartes verte (Bâtiments scientifique)
            deck2.addCard(new Card("ÉCOLE", CardType.SCIENTIFIC_BUILDINGS , new ScientificEffect(ScientificType.LITERATURE),2,new MaterialCost(new Material(MaterialType.WOOD,1) ,new Material(MaterialType.PAPYRUS,1))));
            //Carte Jaune (Bâtiments commerciaux )
            deck2.addCard(new Card("FORUM", CardType.COMMERCIAL_BUILDINGS , null ,2,new MaterialCost(new Material(MaterialType.CLAY,2) )));//TODO effect
            deck2.addCard(new Card("BAZAR", CardType.COMMERCIAL_BUILDINGS , null ,2,null ));//TODO effect
            //Cartes Bleues (Batiment civil)
            deck2.addCard(new Card("AQUEDUC", CardType.CIVIL_BUILDING, new VictoryPointEffect(5),2,new MaterialCost(new Material(MaterialType.STONE,3))));
            deck2.addCard(new Card("STATUE", CardType.CIVIL_BUILDING, new VictoryPointEffect(4),2,new MaterialCost(new Material(MaterialType.ORES,2),new Material(MaterialType.WOOD,1))));

        }
        return deck2;
    }
}
