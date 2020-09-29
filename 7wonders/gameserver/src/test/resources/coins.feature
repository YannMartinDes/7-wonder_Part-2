Feature: Features coins

  Scenario: Revendre/defausser une carte
    Given j'ai 3 pieces
    When je defausse une carte
    Then j'obtiens 3 pieces en plus, soit 6 pieces au total

  Scenario: Remplacement par un nombre negatif
    Given j'ai 3 pieces
    When je redefini les pieces du joueur par un nombre negatif, par exemple -1
    Then il est renvoye une erreur d'argument
