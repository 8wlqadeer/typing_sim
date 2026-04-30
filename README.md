#typing_sim





## Project Structure
TypingRaceSimulator/
├── Part1/    # Textual simulation (Java, command-line)
└── Part2/    # GUI simulation (Java Swing)

## Part 1 — Textual Simulation

### How to compile
```bash
cd Part1
javac Typist.java TypingRace.java
```

### How to run
The race is started by calling `startRace()` on a `TypingRace` object.
A simple way to test this is via the `main` method already in `TypingRace`:

```java
public static void main(String[] args) {
    TypingRace race = new TypingRace(40, 3);
    race.addTypist(new Typist("①", "TURBOFINGERS", 0.85), 1);
    race.addTypist(new Typist("②", "QWERTY_QUEEN",  0.60), 2);
    race.addTypist(new Typist("③", "HUNT_N_PECK",   0.30), 3);
    race.startRace();
}
```

Then run:
```bash
java -cp . Part1.TypingRace
```

## Part 2 — GUI Simulation

### How to compile
```bash
cd TypingRaceGUI  (NOT INSIDE PART2)
javac Part2/Typist.java Part2/TypingRace.java Part2/TypingRaceGUI.java
```

### How to run
```bash
java -cp . Part2.TypingRaceGUI
```

The GUI is launched via the `main` method in `TypingRaceGUI`. This opens the home screen where you can configure and start the race.

### Features
- **Home Page** — navigate to all sections of the app
- **Set Players** — choose 2 to 6 typists using a slider
- **Modify Difficulty** — enable Autocorrect, Caffeine Mode or Night Shift before the race
- **Passage Selection** — choose Short, Medium, Long or Custom passage
- **Customise Typists** — for each player, choose typing style, keyboard, symbol, colour and accessories
- **Race Page** — watch typists race through the passage with animated progress bars
- **Stats Page** — view WPM, accuracy %, burnout count and mistypes per player after the race
- **Leaderboard** — ranked by personal best WPM, with titles awarded for milestones
- **Sponsors Page** — ranked by cumulative earnings, with sponsor bonuses for no burnouts

### How to play
1. Click How to Play and read full guide
2.  Set the number of players using SET PLAYERS
3. Optionally enable difficulty modifiers using MODIFY DIFFICULTY
4. Press START and select a passage
5. Customise each typist and press NEXT
6. Watch the race — progress bars fill as typists advance
7. When a winner is found, stats are displayed automatically
8. View the Leaderboard or Sponsors board, then return home to play again


## Dependencies
- Java Development Kit (JDK) 11 or higher
- No external libraries required
- Both parts use only standard Java libraries (Swing for Part 2)

## Notes
-All code compiles and runs using standard command line tools without any IDE configuration
-the starter code in part1 was originally incomplete and contained several bugs which have been fixed now
-part 2 contains modified versions of Typist and TypingRace to work with the GUI









