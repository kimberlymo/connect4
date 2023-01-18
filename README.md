# Connect Four Template

A start of a project implementing a variant of the game "Connect Four".

## Mission

1. Extend the existing code such that the GreedyPlayer works.
2. Implement a strong Opponent using MinMax/Negamax with fixed depth (like 10).
3. Implement a perfect Player and optimize it for speed.

## Resources

- https://de.wikipedia.org/wiki/Minimax-Algorithmus#Implementierung
- https://de.wikipedia.org/wiki/Alpha-Beta-Suche#Implementierung
- https://en.wikipedia.org/wiki/Zobrist_hashing

## Übersicht
Beim commit "perfectPlayer is working" funktioniert der alpha-beta einwandfrei. Der aktuelle Stand funktioniert auch gut mit Iterative Deepening, aber er spielt ein bisschen schlechter.
Der aktuelle Stand kann bewertet werden.

## Anforderungen
### min:
- ein State der läuft: MinMax, isWinning ist korrekt ✔
- viele Inlinekommentare ✔
- Selbstevaluation ✔
### weiteres:
- algorithmische Optimierungen, AlphaBeta, ... ✔
- MoveOrdering ✔
- Iterative Deepening ✔
- BitBoard
- OpeningBook
- Endziel: Perfect Solver ✔

## Selbstevaluation
In meiner Selbstevaluation möchte ich erwähnen, dass ich alle mindest Anforderungen des Projekts erfüllt habe. Ich war sogar in der Lage gewesen, zwei erweiterte Aufgaben erfolgreich zu lösen, eine davon war die Anwendung von MoveOrdering und die andere war die Anwendung von Iterative Deepening. Dazu konnte ich auch das Alpha-Beta-Pruning hinzufügen, um den Player performanter zu machen. Der Player, den ich entwickelt habe, funktioniert fast perfekt und konnte sowohl gegen den greedyPlayer als auch gegen eine Person gewinnen. Ich erwarte eine mindest Note von 5.25 für meine Leistungen.