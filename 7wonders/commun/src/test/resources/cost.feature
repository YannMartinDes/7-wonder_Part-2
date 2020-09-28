Feature: Features cartes

  Scenario: payer les carte batiment avec du bois
    Given J'ai selectionner une carte batiment dans le deck qui coute 2 de bois
    When j'ai actuellement une carte de 2 bois
    Then La construction dois etre 1 avec 0 refuser et 1 accepter

  Scenario: je peut pas payer les carte batiment avec du bois
    Given J'ai selectionner une carte batiment dans le deck qui coute 2 de bois
    When j'ai actuellement une carte de 1 bois
    Then La construction dois etre 0 avec 0 refuser et 1 accepter

