# <font color = "violet"> <u> Projet citadelle équipe N </font> </u>

## <font color = "#ADD8E6"> I - Architecture du projet </font>

### <font color = "#FFFFE0"> 1/ Comment est faites cette architecture : </font>

Le projet est composé de 6 packages

- Le package <b> app </b> qui contient uniquement la classe main pour executer le projet

- Le package <b> motor </b> qui contient les classes "arbitres" qui agissent sur l'état du jeu (changement des mains des joueurs, pose des cartes, ajout d'or, de score, etc...)
  - La classe Game est la classe maitresse, elle initialise, joue et met fin au jeu.
  - Les classes Bank et Score gèrent respectivement l'or et le score des joueurs.
  - La classe City est responsable du plateau du jeu, elle contient les cartes posées sur le jeu par chaque joueur.
  - La classe Hand est responsable des mains des joueurs, elle contient les cartes que chaque joueur possède, mais n'a pas posé.
  - La classe BoardPlayer contient des getters qui permettent aux joueurs d'avoir des infos sur les autres joueurs (ex le joeur le plus riche).
  - La classe GameMaster et celle qui gère toutes les demandes des joueurs, elle fait le lien entre les joueurs le score la banque, la main et les quartiers joués.

- Le package<b> cards </b> qui contient les cartes des personnages et les cartes quartiers il gère les decks de cartes ainsi que les mains et jeu des joueurs
  - La classe Districts est une enum qui contient toutes les cartes du jeu.
  - La classe Character contient le deck des personnages.
  - Les classes qui héritent de la classe Character sont responsables des pouvoirs de chaque personnage.
  
- Le package <b> bot </b> qui contient les players et qui leur associe un bot qui va suivre un strategy spécifique
  - La classe Player contient les attributs de chaque joueur (la main, le score, l'or ...).
  - Les classes Bots héritent de Player et choisissent la/les strategies adopté par le joueur.

- Le package <b> strategy </b> qui contient les 4 différentes strategies pouvant être adopté par les bots
  - La classe Strategy est une classe abstract qui contient toutes les méthodes nécessaires aux strategies.
  - Les classes qui héritent de Strategy redéfinissent les méthodes pour les adapter à different style de jeu.

- Le package <b> guy </b> qui contient uniquement les méthodes qui vont afficher des infos sur le terminal et les couleurs


### <font color = "#FFFFE0"> 2/ Les choix qui nous ont amené à réaliser cette architecture : </font>

** Cette Architecture n'était pas celle suivie au début du projet, suite à un premier refactor nous avons décidé de limiter les permissions des joueurs (ex : les joueurs se donnaient eux même leur score et leur or) 
et on a créé la classe GameMaster pour gérer ces demandes.

** Pour une raison de lisibilité on a séparé chaque aspect du jeu dans une classe différente toutes rattacher a la classe Game qui débute le jeu et qui y met fin.

** Pour les Cartes on a créé une Enum pour les Districts, car cela simplifie la gestion du deck de cartes

** Pour les personnages on a utilisé les héritages par simplicité d'utilisation du pouvoir de chaque personnage et par lisibilité.

** On a séparé les Bots et les strategies de ce fait, il est possible de faire changer de strategies un même bot au cours de la partie.

** Le ConsoleGuy nous permet de modifier plus simplement ce qui est affiché dans le terminal.

## <font color = "#ADD8E6"> II -Les infos de la doc : </font>

Chaque méthode possède une java doc qui précise les différents paramètres et l'utilité de la fonction.

## <font color = "#ADD8E6"> III - Qui est responsable de quoi : </font>
Chaque Milestone est divisée en plusieurs issues, chacune a été attribuée à un membre de l'équipe.
C'est ainsi chaque membre a touché toutes les fonctionnalités du projet.

## <font color = "#ADD8E6"> IV - Le process de l'équipe : </font>

### <font color = "#FFFFE0"> Organisation du travail : </font>

Au début de chaque séance on discute sur les fonctionnalités à ajouter, on crée un nouveau milestone et on le divise en petits slices à fin de livrer
un produit de valeur chaque semaine. Les différentes tâches du projet étaient bien réparties au sein de notre équipe et nous n'avons pas eu de problèmes de communication 
ce qui nous a permis de réaliser les fonctionnalités prévues du jeu dans les délais.


### <font color = "#FFFFE0"> Utilisation du git : </font>

Au début du projet chaque membre de l'équipe a fait git clone du projet.
Et chacun qui ajoute du contenu fait pull pour récupérer le travail des autres membres, commit son code puis push
son travail sur une issue particulière en précisant le "#".
Nous avons aussi créé puis merge des branches pour faciliter le refactor.

## <font color = "#ADD8E6"> V- Avancement sur les fonctionnalités : </font>

Concernant les slices, toutes celles qui étaient prévues ont été réaliser ; toutes les fonctionnalités du jeu sont présentes,
cependant il reste toujours des améliorations à faire (lisibilité, optimisation, perfection des stratégies).

## <font color = "#ADD8E6"> VI- Etat de la base de code : </font>

### <font color = "#FFFFE0"> 1/ Les parties à refactor : </font>
- Amélioration de la gestion des cartes merveilles qui ajoutent des conditions à vérifier pour chaque cas particulier.
- Perfection des strategies des bots, car certains choix des bots n'utilisent pas toutes les informations du jeu (exemple avancement du jeu des autres joueurs).
- Amélioration de la lisibilité de certaines méthodes pour les rendre facilement compréhensible.

### <font color = "#FFFFE0"> 2/ Les parties bien faites : </font>
- L'architecture du code est bien faites globalement.
- Le système de strategies adaptable à chaque bot ce qui nous permet de créer des bots
adaptant des stratégies différentes dans chaque tour.
- L'utilisation d'un gameMaster pour faire le lien entre le jeu et les joueurs.
- L'utilisation des héritages pour chaque personnage et chaque stratégie qui
nous a permis d'éviter au maximum la duplication du code.

