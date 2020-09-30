Feature: Features cost

  Scenario: payer la carte bâtiment avec du bois
    Given j'ai selectionner une carte bâtiment dans le deck qui coûte 2 de bois
    When j'ai actuellement une carte de 2 bois
    Then la construction dois être effectuée

  Scenario: je ne peut pas payer la carte bâtiment avec du bois
    Given j'ai selectionner une carte bâtiment dans le deck qui coûte 2 de bois
    When j'ai actuellement une carte de 1 bois
    Then la construction dois ne dois pas être effectuée

