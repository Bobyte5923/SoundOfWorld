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
- Si elle est fausse : la case devient **rouge**.

---

## Architecture MVC

| Composant                            | Rôle                                                                 |
|-------------------------------------|----------------------------------------------------------------------|
| `Main.java`                         | Point d'entrée, lance la fenêtre principale                         |
| `model/Instrument.java`             | Représente un instrument (nom, image, son)                          |
| `view/MainWindow.java`              | Fenêtre principale Swing                                            |
| `view/InstrumentGuessPanel.java`    | Panneau interactif pour chaque instrument à deviner                 |
| `controller/GameController.java`    | Logique du jeu : lecture audio, validation, sélection aléatoire     |

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
```bash
java -cp bin main.Main
```

---

## Auteurs
Amandine & Ayman  
Encadré par : Professeur-e HES Madame Rizzotti-Kaddouri  

## Ressources
Code et questions https://stackoverflow.com
Informations fonction J https://docs.oracle.com
Explication et debug ultine https://chatgpt.com
Les sons https://freesound.org/
Les images https://images.google.com/