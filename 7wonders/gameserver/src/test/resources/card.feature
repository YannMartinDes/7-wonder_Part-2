Feature: Features cartes

  Scenario: Ajout de cartes
    Given j'ai 5 cartes avec ma merveille
    When j'ajoute 1 cartes
    Then je dois avoir 6 cartes avec ma merveille

  Scenario: Ajout d'une carte précise
    Given j'ai 2 cartes avec ma merveille
    When j'ai choisi une carte "BATIMENT CIVIL" de l'age 1 qui s'appelle "BAINS" qui rapporte 3 "POINTS VICTOIRE" et qui coûte 1 "PIERRE"
    Then la carte est bien ajoutée à ma merveille
