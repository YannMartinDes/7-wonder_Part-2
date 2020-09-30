Feature: Features cartes

  Scenario: Ajout de cartes
    Given j'ai 5 cartes avec ma merveille
    When j'ajoute 1 cartes
    Then je dois avoir 6 cartes avec ma merveille

  Scenario: Rotation des mains des joueurs
    Given j'ai 3 joueurs avec chacun 5 cartes
    When chaque joueur passe ses cartes à son voisin lors de l'âge 1
    Then la rotation des mains s'est bien effectuée
