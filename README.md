# Sound of World - Jeu éducatif IHM Java Swing

## Objectif
Ce projet est un jeu éducatif en Java utilisant Swing. Il permet aux utilisateurs d’associer visuellement et auditivement des **instruments de musique** à leurs **sons** respectifs.

## Architecture
Le projet est structuré selon le modèle **MVC (Modèle-Vue-Contrôleur)** :
- `model.Instrument` : représente un instrument avec nom, image et son.
- `view.MainWindow` et `view.InstrumentPanel` : interface graphique avec Swing.
- `controller.GameController` : logique de jeu et gestion des événements.

## Technologies
- Java (JDK 8+)
- Swing (IHM)
- javax.sound.sampled pour la lecture audio

## Ressources
https://stackoverflow.com
https://docs.oracle.com
Les sons https://freesound.org/

## Exécution
1. Compiler :
javac -d bin -sourcepath src src/main/Main.java
2. Executer :
java -cp bin main.Main
