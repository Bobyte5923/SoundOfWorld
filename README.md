# Sound of World - Jeu éducatif en Java Swing

## Objectif
Cette application interactive permet de reconnaître des instruments de musique uniquement par leur **son**.

Elle est développée avec Java Swing selon le modèle **MVC** (Modèle-Vue-Contrôleur).

---

## Règles du jeu

- À chaque lancement, **6 instruments sont choisis aléatoirement** parmi une liste.
- L'utilisateur voit **6 cases vides**, avec un bouton “Jouer le son” et un champ de réponse.
- Il peut cliquer pour écouter un instrument.
- Il doit deviner le nom de l'instrument (ex. `Piano`, `Guitare`, `Harpe`).
- Si la réponse est bonne : l'image de l'instrument s'affiche.
- Si elle est fausse : la case devient **rouge** et il perd 1 point.
- Lorsque les 6 instruments sont trouvés, l'image de Clippy apparait.

---

## Architecture MVC


Modèle (package model ) : contient la classe Instrument qui représente un instrument de
musique avec son nom et les chemins vers ses ressources (image et fichier son).

Contrôleur (package controller ) : contient la classe GameController qui gère la logique
du jeu (chargement des instruments, sélection aléatoire, lecture des sons, vérification des réponses) et fait le lien entre les données et l'interface graphique.

Vue / Interface graphique (package view ) : contient la classe MainWindow (fenêtre principale de l'application) et la classe InstrumentGuessPanel (panneau individuel pour deviner un instrument). InstrumentListPanel (Jliste de tout les instruments dans une barre latérale avec scroll dans un panneau titré "Instruments") et GameMenuBar (affiche le nombre de vies restantes et gère le bouton "Réinitialiser" pour relancer une partie). Ces classes s'occupent de la disposition à l’écran des composants, de la capture des actions de l'utilisateur et de l'affichage des résultats (images, messages).



Point d'entrée (package main ) : contient la classe Main avec la méthode main() qui lance
l'application.

---

## Technologies utilisées

- Java 8+
- Java Swing (GUI)
- `javax.sound.sampled` pour la lecture des sons
- Fichiers `.wav` (PCM 16-bit), `.jpg`

---

## ▶Compilation et exécution

### Compilation
```bash
javac -d bin -sourcepath src src/main/Main.java
```

### Exécution

Demarrer une console puis :

```$ ./build_and_run.sh
```

---

## Auteurs
Amandine & Ayman  
Encadré par : Professeure HES Madame Rizzotti-Kaddouri  

## Ressources
Code et questions https://stackoverflow.com
Informations fonction J https://docs.oracle.com
Les sons https://freesound.org/
Les images https://images.google.com/