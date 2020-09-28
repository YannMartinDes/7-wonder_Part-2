Feature: Features cartes

  Scenario: Ajout de cartes
    Given j'ai 5 cartes avec ma merveille
    When j'ajoute 1 cartes
    Then je dois avoir 6 cartes avec ma merveille


