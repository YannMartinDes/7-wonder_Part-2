Feature: Features coins

  Scenario: Revendre/defausser une carte
    Given j'ai 3 pieces
    When je defausse une carte
    Then j'ai 6 pieces au total

  Scenario: Remplacement par un nombre negatif
    Given j'ai 9 pieces
    When je dépense 5 pièces
    Then j'ai 4 pieces au total
