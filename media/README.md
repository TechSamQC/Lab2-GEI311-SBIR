# Dossier Media

Ce dossier contient tous les fichiers multimédias et exports du système de gestion de tickets.

## Structure

### `/images`
Contient toutes les captures d'écran et images attachées aux descriptions de tickets.
- Formats acceptés : `.jpg`, `.jpeg`, `.png`, `.gif`
- Maximum : 10 images par ticket

### `/videos`
Contient toutes les vidéos de démonstration de problèmes attachées aux tickets.
- Formats acceptés : `.mp4`, `.avi`, `.mov`
- Maximum : 5 vidéos par ticket

### `/exports`
Contient tous les exports PDF des tickets générés par le système.
- Format : `ticket_<ID>_<date>.pdf`

## Usage

Lors de l'ajout de médias à une description de ticket, utilisez les chemins relatifs :
- Images : `media/images/screenshot1.png`
- Vidéos : `media/videos/demo.mp4`
- Exports : `media/exports/ticket_1_report.pdf`

