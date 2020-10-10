Feature: Features cost

  Scenario: payer la carte bâtiment avec du bois
    Given j'ai selectionné une carte bâtiment dans le deck qui coûte 2 de bois
    When j'ai actuellement une carte qui produit 2 bois
    Then la construction doit être effectuée

  Scenario: je ne peux pas payer la carte bâtiment avec du bois
    Given j'ai selectionné une carte bâtiment dans le deck qui coûte 2 de bois
    When j'ai actuellement une carte qui produit 1 bois
    Then la construction ne doit pas être effectuée


  Scenario: payer la carte bâtiment plusieur carte
    Given j'ai selectionné une carte bâtiment dans le deck qui coûte 3 de bois
    When j'ai actuellement une carte qui produit 1 bois
    And j'ai actuellement une carte qui produit 1 bois
    And j'ai actuellement une carte qui produit 1 bois
    Then la construction doit être effectuée


  Scenario: je ne peux pas payer la carte bâtiment plusieures carte
    Given j'ai selectionné une carte bâtiment dans le deck qui coûte 3 de bois
    When j'ai actuellement une carte qui produit 1 bois
    And j'ai actuellement une carte qui produit 1 bois
    Then la construction ne doit pas être effectuée


  Scenario: je peux payer la carte bâtiment car j'ai pas toutes les ressource meme si il y a d'autre ressources
    Given j'ai selectionné une carte bâtiment dans le deck qui coûte 5 de bois
    When j'ai actuellement une carte qui produit 1 pierre
    And j'ai actuellement une carte qui produit 2 bois
    And j'ai actuellement une carte qui produit 1 argile
    And j'ai actuellement une carte qui produit 3 bois
    And j'ai actuellement une carte qui produit 1 minerai
    Then la construction doit être effectuée

  Scenario: je ne peux pas payer la carte bâtiment car j'ai pas toutes les ressource meme si j'ai d'autre ressources
    Given j'ai selectionné une carte bâtiment dans le deck qui coûte 3 de bois
    When j'ai actuellement une carte qui produit 1 bois
    And j'ai actuellement une carte qui produit 1 bois
    And j'ai actuellement une carte qui produit 1 pierre
    And j'ai actuellement une carte qui produit 1 argile
    And j'ai actuellement une carte qui produit 1 minerai
    Then la construction ne doit pas être effectuée

  Scenario: je peux payer une carte qui coute 2 materiaux
    Given j'ai selectionné une carte bâtiment dans le deck qui coûte 1 de bois
    And  1 de pierre
    When j'ai actuellement une carte qui produit 1 pierre
    And j'ai actuellement une carte qui produit 2 bois
    Then la construction doit être effectuée

  Scenario: je ne peux pas payer une carte qui coûte 3 materiaux
    Given j'ai selectionné une carte bâtiment dans le deck qui coûte 1 de bois
    And 1 de pierre
    And 1 de argile
    When j'ai actuellement une carte qui produit 1 pierre
    And j'ai actuellement une carte qui produit 2 bois
    Then la construction ne doit pas être effectuée

  Scenario: je ne peux pas payer une carte qui coûte 2 matériaux avec une carte à choix
    Given j'ai selectionné une carte bâtiment dans le deck qui coûte 1 de bois
    And  1 de pierre
    When j'ai actuellement une carte qui produit 1 pierre ou 1 bois
    Then la construction ne doit pas être effectuée

  Scenario: je peux payer une carte qui coûte 2 matériaux avec une carte à choix
    Given j'ai selectionné une carte bâtiment dans le deck qui coûte 1 de bois
    And 1 de pierre
    When j'ai actuellement une carte qui produit 1 pierre ou 1 bois
    And j'ai actuellement une carte qui produit 1 bois
    Then la construction doit être effectuée

  Scenario:je peux payer une carte qui coûte 7 matériaux différents
    Given j'ai selectionné une carte bâtiment dans le deck qui coûte 1 de bois
    And 1 de minerai
    And 1 de pierre
    And 1 de argile
    And 1 de verre
    And 1 de papyrus
    And 1 de tissu
    When j'ai actuellement une carte qui produit 1 bois
    And j'ai actuellement une carte qui produit 1 bois ou 1 pierre
    And j'ai actuellement une carte qui produit 1 minerai
    And j'ai actuellement une carte qui produit 1 pierre ou 1 argile
    And j'ai actuellement une carte qui produit 1 verre
    And j'ai actuellement une carte qui produit 1 papyrus
    And j'ai actuellement une carte qui produit 1 tissu
    Then la construction doit être effectuée

  Scenario:je ne peux pas payer une carte qui coûte 7 matériaux différents
    Given j'ai selectionné une carte bâtiment dans le deck qui coûte 1 de bois
    And 1 de minerai
    And 1 de pierre
    And 1 de argile
    And 1 de verre
    And 1 de papyrus
    And 1 de tissu
    When j'ai actuellement une carte qui produit 1 bois
    And j'ai actuellement une carte qui produit 1 minerai ou 1 bois
    And j'ai actuellement une carte qui produit 1 minerai
    And j'ai actuellement une carte qui produit 1 pierre ou 1 argile
    And j'ai actuellement une carte qui produit 1 verre
    And j'ai actuellement une carte qui produit 1 papyrus
    And j'ai actuellement une carte qui produit 1 tissu
    Then la construction ne doit pas être effectuée
