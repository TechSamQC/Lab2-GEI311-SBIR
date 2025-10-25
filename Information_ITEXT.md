# Installation d'iText pour l'export PDF

## Dépendance iText 7

Ce projet utilise **iText 7** pour générer des fichiers PDF à partir des tickets.

## Utilisation des JAR

**Compiler avec les JAR** :
   ```bash
   # Compilation
   javac -cp "lib/*" -d bin src/*.java
   
   # Exécution
   java -cp "bin;lib/*" Main
   ```
## Licence iText

⚠️ **Important** : iText 7 est sous licence **AGPL**. Pour un usage commercial, vous devrez acheter une licence.
Pour un usage académique/éducatif, la version AGPL est gratuite.