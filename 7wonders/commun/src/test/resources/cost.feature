Feature: Features cost

  Scenario: payer la carte bâtiment avec du bois
    Given j'ai selectionner une carte bâtiment dans le deck qui coûte 2 de bois
    When j'ai actuellement une carte qui produit 2 bois
    Then la construction dois être effectuée

  Scenario: je ne peut pas payer la carte bâtiment avec du bois
    Given j'ai selectionner une carte bâtiment dans le deck qui coûte 2 de bois
    When j'ai actuellement une carte qui produit 1 bois
    Then la construction ne dois pas être effectuée



  Scenario: payer la carte bâtiment plusieur carte
    Given j'ai selectionner une carte bâtiment dans le deck qui coûte 3 de bois
    When j'ai actuellement une carte qui produit 1 bois
    And j'ai actuellement une carte qui produit 1 bois
    And j'ai actuellement une carte qui produit 1 bois
    Then la construction dois être effectuée


  Scenario: je ne peut pas payer la carte bâtiment plusieur carte
    Given j'ai selectionner une carte bâtiment dans le deck qui coûte 3 de bois
    When j'ai actuellement une carte qui produit 1 bois
    And j'ai actuellement une carte qui produit 1 bois
    Then la construction ne dois pas être effectuée


  Scenario: je peut payer la carte bâtiment car j'ai pas toutes les ressource meme si il y a d'autre ressources
    Given j'ai selectionner une carte bâtiment dans le deck qui coûte 5 de bois
    When j'ai actuellement une carte qui produit 1 pierre
    And j'ai actuellement une carte qui produit 2 bois
    And j'ai actuellement une carte qui produit 1 argile
    And j'ai actuellement une carte qui produit 3 bois
    And j'ai actuellement une carte qui produit 1 minerai
    Then la construction dois être effectuée

  Scenario: je ne peut pas payer la carte bâtiment car j'ai pas toutes les ressource meme si j'ai d'autre ressources
    Given j'ai selectionner une carte bâtiment dans le deck qui coûte 3 de bois
    When j'ai actuellement une carte qui produit 1 bois
    And j'ai actuellement une carte qui produit 1 bois
    And j'ai actuellement une carte qui produit 1 pierre
    And j'ai actuellement une carte qui produit 1 argile
    And j'ai actuellement une carte qui produit 1 minerai
    Then la construction ne dois pas être effectuée
