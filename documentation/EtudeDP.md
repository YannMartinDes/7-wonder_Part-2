# Etude des Design Patterns (DP)

## DP Singleton
### GameLogger
`GameLogger est un Singleton pour une meilleure maintenabilité de code, en effet à la base cette classe contenait uniquement des attributs/méthodes statiques et avoir un Singleton permet de manipuler GameLogger d'une manière orientée objet`  
![GameLogger](/documentation/uml/GameLogger.png)  
### StatModule
`Le singleton StatModule repésente un StatObject, il permet d'éviter de mettre le StatObject en paramètre de fonction dès qu'on en a besoin, ainsi il contribue à une meilleure lisibilité de code ainsi qu'à éviter les duplications de StatObject`  
![StatModule](/documentation/uml/StatModule.PNG)  
## DP Factory
### CardFactory
`CardFactory est une Factory puisque cette classe sert à la creation des decks en fonction du nombre de joueurs`   
![CardFactory](/documentation/uml/CardFactory.png)  
### WonderBoardFactory
`WonderBoardFactory est une Factory puisque cette classe sert à la creation des merveilles en fonction de leur face`  
![WonderBoardFactory](/documentation/uml/WonderBoardFactory.PNG)  
## DP Template
### Statistiques
`Les statistiques sont représentées par un StatObject (commun/communication/StatObject.java), ce StatObject est le représentation de toutes les statistiques possibles. Chaque attribut de StatObject implémente une interface IStat et hérite de StatBase. C'est donc un Template DP. Cela nous a permis d'ajouter une fonction add générique qui permet d'additionner une statistique dans l'objet.`  
![Statistiques](/documentation/uml/Stats.png)  
### Dealers
`Les Dealers dans notre projet représentent les classes qui vont permettre de transformer un attribut de StatObject en donnée valide pour nos statistiques. Par exemple pour un attribut qui représente des entiers on va diviser par le nombre de parties et le transformer en ArrayList<String>. C'est donc un DP Template afin d'avoir une meilleure maintenance de code dans le cas où le type de la statistique changerait.`  
![Dealer](/documentation/uml/Dealer.png)  
## DP Strategy
### Intelligence Artificelle
`Les intelligences artificielles du jeu sont basées sur un DP Strategy, en effet cela permet une meilleure gestion des stratégies et une meilleure maintenabilité de code si l'on doit changer une stratégie à la volée, cela permet aussi de pouvoir comparer les stratégies entre elles.`  
![AI](/documentation/uml/AI.PNG)
### Coût
`Le coût en monnaie de jeu ou en ressource est réprésenté par une interface ICost qui est implémentée par les class qui permettent de gérer le coût et la possibilité d'achat. C'est un DP Strategy pour une meilleure maintenabilité de code.`    
![Cost](/documentation/uml/Cost.png)
### Effets
`Les effets sont une DP Strategy car cela permet de pouvoir mieux maintenir le code, avoir plusieurs classes qui ont chacun un effet différent.`    
![Effects](/documentation/uml/Effects.png)