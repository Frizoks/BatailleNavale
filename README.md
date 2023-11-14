# Bataille Navale en Java

## Description

Un jeu simple de Bataille Navale implémenté en Java. Le jeu permet à deux joueurs en local de s'affronter sur une grille virtuelle et de placer leurs navires, puis de s'attaquer mutuellement en essayant de couler les navires adverses.

un mode Solo est aussi implémenter, où l'on affronte une IA avec 4 niveaux de difficultés.

## Installation

Voici les etapes afin de pouvoir jouez à cette **Bataille Navale**
1. Rendez-vous sur la page Github de ce projet : https://github.com/Frizoks/BatailleNavale
2. Telecharger principalement le fichier "BatailleNavale.jar", et optionnellement le fichier "dataBN" afin de jouez les sons dans le jeu.
3. Si vous avez les deux fichier, il faut impérativement qu'il se trouve dans le même dossier (avec la même racine)
4. Afin de jouez a la **Bataille Navale**, il vous suffit maintenant d'executer le fichier "BatailleNavale.jar" pour cela :
    - Si vous êtes sur Windows, vous devez simplement faire un double-clic sur le fichier Jar
    - Si vous êtes sur Linux (cela marche également sur Windows), il vous suffit d'executer dans un terminal, dans le repertoire courant où se situe votre fichier Jar, la commande suivante  : java -jar BatailleNavale.jar
6. Vous avez maintenant lancez le jeu, a vous de jouez !!!

NB : Vous pouvez aussi téléchargez la documentation sur Github, et ouvrir index.html dans un navigateur internet, afin de découvrir le code de la **Bataille Navale**

## Utilisation

Une fois le la **Bataille Navale** lancez, vous arrivez donc sur la fenêtre d'acceuil, vous avez maintenant le choix entre 2 modes de jeux :

- Partie Solo (où vous affronterez une IA)

    - Choisissez maintenant une difficulté entre les 4 suivantes :
        - **Facile** : L'IA prend des décisions aléatoires.
        - **Moyen** : L'IA prend des décisions basées sur une logique simple.
        - **Difficile** : L'IA prend des décisions stratégiques pour maximiser ses chances de gagner.
        - **Impossible** : L'IA connaît le plateau adverse, et aléatoirment, trouve automatiquement la positions des navires.
     
    - Vous devez maintenant placez vos navires, vous avez ici aussi, deux choix :
        - Manuellement : En selectionnant un navire qui n'est pas déjà placer, ensuite les cases où vous voulez le placer sur votre plateau (celui de gauche), et finalement en appuyant sur le bouton                    **Valider** qui vérifiera si votre navire à le droit d'être placer ici, il faut placer les navires un par un (Vous ne pouvez pas placer 2 bateaux différents sur des cases adjacentes, voir les                 règles de la Bataille navale : https://fr.wikipedia.org/wiki/Bataille_navale_(jeu))
        - Automatiquement : en appuyant simplement sur le bouton **Auto**

  
- Partie Multijoueur (pour cela il faut être deux, connécté sur le même réseau internet) :

    - Un premier joueur doit **Créer une partie** (serveur), et attendre patiemment que quelqu'un rejoigne la partie en lui donnant l'adresse IP affiché en haut de la fenêtre.
 
    - Un deuxième joueur doit maintenant **Rejoindre une partie** (client), et renseigner l'adresse IP du serveur (celui qui à creer la partie), et ensuite cliquez sur **Validez**
 
    - Si tout se passe bien, vous devriez maintenant être connécté, il vous suffit de placez vos navires (voir la section corespondante sur la partie solo)

- Une fois vos navires placez (et ceux de l'adversaire aussi), vous allez pouvoir commencez à jouez :
    
    - Le plateau de gauche 


## Auteurs

- Luc LECARPENTIER
- Mathys PORET
