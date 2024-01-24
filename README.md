# Bataille Navale en Java

## Description

Un jeu simple de Bataille Navale implémenté en Java. Le jeu permet à deux joueurs en local de s'affronter sur une grille virtuelle et de placer leurs navires, puis de s'attaquer mutuellement en essayant de couler les navires adverses.

Un mode Solo est aussi implémenté, où l'on affronte une IA avec 4 niveaux de difficultés.

## Installation

Voici les étapes afin de pouvoir jouez à cette **Bataille Navale**
1. Rendez-vous sur la page Github de ce projet : https://github.com/Frizoks/BatailleNavale
2. Télécharger le fichier "BatailleNavale.jar".
3. Afin de jouer a la **Bataille Navale**, il vous suffit maintenant d'exécuter le fichier "BatailleNavale.jar" pour cela :
    - Si vous êtes sur Windows, vous pouvez simplement faire un double-clic sur le fichier Jar
    - Si vous êtes sur Linux (cela marche également sur Windows), dans un terminal, dans le répertoire courant où se situe votre fichier Jar, tapez la commande suivante  : java -jar BatailleNavale.jar
4. Vous avez maintenant lancé le jeu, à vous de jouer !!!

NB : Vous pouvez aussi télécharger la documentation sur Github, et ouvrir index.html dans un navigateur internet, afin de découvrir le code de la **Bataille Navale**.

## Utilisation

Une fois la **Bataille Navale** lancée, vous arrivez donc sur la fenêtre d'accueil, vous avez maintenant le choix entre 2 modes de jeux :

- **Partie Solo** (où vous affronterez une IA)

    - Choisissez maintenant une difficulté entre les 4 suivantes :
        - **Facile** : L'IA prend des décisions aléatoires.
        - **Moyen** : L'IA prend des décisions basées sur une logique simple.
        - **Difficile** : L'IA prend des décisions stratégiques pour maximiser ses chances de gagner.
        - **Impossible** : L'IA connaît le plateau adverse, et aléatoirement, trouve automatiquement la position des navires.
     
    - Vous devez maintenant placer vos navires, vous avez ici aussi, deux choix :
        - **Manuellement** : En sélectionnant un navire qui n'est pas déjà placé, ensuite les cases où vous voulez le placer sur votre plateau (celui de gauche), et finalement en appuyant sur le bouton                    **Valider** qui vérifiera si votre navire à le droit d'être placé ici, il faut placer les navires un par un (Vous ne pouvez pas placer 2 bateaux différents sur des cases adjacentes, voir les                 règles de la Bataille navale : https://fr.wikipedia.org/wiki/Bataille_navale_(jeu))
        - **Automatiquement** : en appuyant simplement sur le bouton **Auto**

  
- **Partie Multijoueur** (pour cela, il faut être deux, connecté sur le même réseau internet) :

    - Un premier joueur doit **Créer une partie** (serveur), et attendre patiemment que quelqu'un rejoigne la partie en lui donnant l'adresse IP affiché en haut de la fenêtre.
 
    - Un deuxième joueur doit maintenant **Rejoindre une partie** (client), et renseigner l'adresse IP du serveur (celui qui a créé la partie), et ensuite cliquez sur **Validez**.
 
    - Si tout se passe bien, vous devez maintenant être connecté, il vous suffit de placer vos navires (voir la section correspondante sur la partie solo).


- Une fois vos navires placés (et ceux de l'adversaire aussi), vous allez pouvoir commencer à **jouer** :
    
    - Le **plateau de gauche** est le vôtre, vous verrez les coups que votre adversaire à effectuer, en dessous, vous avez aussi le résultat de ses 5 derniers coups, et encore en dessous, le nombre de navires que votre adversaire à coulé et sa précision (coups touchant un navire / coups tirés).
    - Le **plateau de droite** est celui de votre adversaire, c'est ici que vous devez cliquer pour jouer, quand c'est à votre tour, bien sûr. En dessous, vous avez les coordonnées de vos 5 derniers coups, ainsi que le nombre de navires que vous avez coulé et votre précision.
    - La partie se finit quand un des deux joueurs a coulé tous les navires de son adversaire si vous avez perdu, les navires restants vont s'afficher. Vous pourrez ensuite retourner à l'accueil, ou tout simplement fermer le message de fin pour regarder le rendu de votre partie (vous pouvez simplement décaler le message de fin sur le côté, regarder la fin de partie et relancer ensuite). Si vous avez cliqué sur **Ok** et que vous voulez relancer une partie, vous devez simplement re-éxecuter le fichier **BatailleNavale.jar**.
    - Vous pouvez retrouver les règles du jeu de la **Bataille Navale** sur la page suivante : https://fr.wikipedia.org/wiki/Bataille_navale_(jeu)
 

- Cette Bataille Navale est aussi composée de différents sons, et notamment une musique d'ascenseur dans les menus, afin de la désactiver ou de la réactiver, vous avec un bouton **Musique ON/OFF** sur la Fenêtre d'accueil.


## Auteurs

- Luc LECARPENTIER
- Mathys PORET
