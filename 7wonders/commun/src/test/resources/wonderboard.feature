Feature: Features wonderboard

  Scenario: Je peux ajouter une carte
    Given Je crée un plateau de la merveille de nom "Babylone" qui rapporte une ressource bois
    When J'ajoute une carte de nom "Taverne" de l'age 1
    Then La carte peut être ajoutée au plateau de la merveille