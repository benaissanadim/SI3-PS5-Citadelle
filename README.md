# projet-ps5-citadelle 


    1- Les fonctionnalités réalisées  :
On a réalisé toutes les fonctionnalités du jeu et on a créé divers bots :
- Bot Basic et Bot Fast : Bot Basic veut construire les quartiers avec le prix le plus élevé pour avoir un score élevé contrairement au Fast qui veut construire
  celles avec le prix le plus bas pour terminer vite.
- Bot Color : construit des quartiers de couleurs différentes pour avoir le bonus de score à la fin et choisit le personnage qui lui donne le maximum de coin bonus.
- Bot ColorFast et Bot ColorBasic : ces 2 bots changent de stratégie color vers Fast (ou vers Basic) s'ils ont construit les 5 couleurs différentes.
- Bot Random : joue aléatoirement.

On a implémenté aussi la couronne et les pouvoirs des différents personnages avec des choix qui diffèrent selon la stratégie adaptée par le Bot.

On a respecté aussi le nombre total du coins dans le jeu, le nombre total des cartes, de plus, on a ajouté les cartes prestiges ainsi que leurs pouvoirs.



    2- Les Logs :

L'ajout de cette fonctionnalité a pu être réalisé simplement car on utilisait au préalable une classe ConsoleGuy qui nous permet d'afficher toutes nos informations.
Pour implémenter les logs, nous avons simplement modifié nous System.out.println pour utiliser des logs.
Nous utilisons deux niveaux de logs :
- les logs fine qui nous permettent d'afficher tout le déroulement d'une partie.
- les logs info quoi nous permettent d'afficher les statistiques lorsque l'on lance les 1000 parties.


    3- Les statistiques en CSV :

Pour commencer, nous avons créé la classe Statistics qui va permettre de lancer un certain nombre de partie en utilisant les logs info afin de ne pas afficher toutes les parties.
A la fin des parties, les statistiques de victoire et le score moyen sont calculé et afficher dans la console.
Ces stats sont ensuite envoyées vers un fichier CSV ou elles vont être stockées ou actualisées si cette configuration a déjà été joué.
La classe OpenCSV a la responsabilité de la lecture et l'écriture du fichier et la classe StatParagraphe représente les statistiques d'une batterie de partie jouée.

    4- Le Bot ajouté :
a) Ajout du Bot :

On a préféré ajouter un BotSuper qui adapte principalement la stratégie de Richard, car celle suggérée par Alphonse se ressemble aux stratégies de nos bots réalisés.
Pour réussir à le réaliser, on a redéfini plusieurs fonctions de la classe mère Strategy et on a détaillé les conditions du choix du personnage selon les conditions demandés par Richard.

b) Comparaison des résultats en ajoutant le nouveau BotSuper :

Notre BotColorFast reste le meilleur et le BotSuper est le troisième après le Bot ColorBasic.

En effet, notre BotColorFast est principalement bâtisseur, il se concentre au début par le choix des quartiers de couleurs différentes, et après avoir construit les 5 quartiers,
pour les 3 derniers, il change de stratégie et devient Fast en construisant celle qui a le moins de coût ce qui lui permet de terminer le plus vite possible.
De plus, pour son choix de personnage, il se concentre principalement à avoir celle qui lui donne le plus de bonus coins.

Alors que notre nouveau BotSuper suggéré par Richard essaye d'avoir un raisonnement humain qui n'est pas pratique lorsqu'il joue
contre des bots ayant des raisonnements différents non humains donc sa prédiction devient la plupart du temps fausse.
De plus, ce bot se concentre sur l'état des autres joueurs ce qui ne lui permet pas de bâtir rapidement ses quartiers.

