Feature: Features wonderboard

  Scenario: Je peux ajouter une carte
    Given Je crée un plateau de la merveille de nom "Babylone" qui rapporte une ressource bois
    When J'ajoute une carte de nom "Taverne" de l'age 1
    Then La carte peut être ajoutée au plateau de la merveille
    Then Le plateau de merveille possède 8 pièces

  Scenario: Je peux ajouter une carte conflit militaire
    Given Je crée un plateau de merveille de nom "Babylone"
    When J'ajoute la carte "PALISSADE" de l'âge 1 et la carte "MURAILLE" de l'âge 2 à "Babylone"
    Then Les cartes "PALISSADE" et "MURAILLE" peuvent être ajoutées au plateau de la merveille "Babylone"
    Then La merveille "Babylone" possède 3 de puissance militaire