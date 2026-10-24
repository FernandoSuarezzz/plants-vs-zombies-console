# Plants vs Zombies (console)

A turn-based console version of *Plants vs Zombies*, written in Java. Place plants on a 4×8 board, collect suns to earn suncoins, and stop the zombies before they reach the left side of the board.

Developed as a project for the course *Tecnología de la Programación I* (Programming Technology I) at Universidad Complutense de Madrid. It is a non-commercial university project inspired by the original game, and it is not affiliated with PopCap or EA.

```
Number of cycles: 9
Sun coins: 50
Remaining zombies: 3
Generated suns: 3
Caught suns: 0
Score: 0
           0              1              2              3              4              5              6              7              8
     ────────────── ────────────── ────────────── ────────────── ────────────── ────────────── ────────────── ──────────────
  0 |     S[01]    |              |              |              |     *[07]    |              |     *[05]    |              |
     ────────────── ────────────── ────────────── ────────────── ────────────── ────────────── ────────────── ──────────────
  1 |              |     P[03]    |              |              | WN[10] *[08] |              |              |              |    Ez[05]
     ────────────── ────────────── ────────────── ────────────── ────────────── ────────────── ────────────── ──────────────
  2 |     P[03]    |              |              |              |              |              |              |              |
     ────────────── ────────────── ────────────── ────────────── ────────────── ────────────── ────────────── ──────────────
  3 |              |              |              |              |              |              |              |    Bz[08]    |
     ────────────── ────────────── ────────────── ────────────── ────────────── ────────────── ────────────── ──────────────

Command >
```

Each cell shows the objects in it as `symbol[life]`. Zombies enter the board from column 8.

## How to play

Every command that changes the board (`add`, `none`, …) advances the game one cycle. In each cycle:
- plants act (shoot, generate suns, explode);
- zombies move and attack;
- new zombies may appear;
- suns slowly disappear.

The player **wins** when every zombie of the level has been defeated. The **zombies win** if one of them reaches the left side of the board.

- You start with **50 suncoins**. Each sun you catch gives you **10 suncoins**.
- Suns appear randomly on the board and are also produced by sunflowers. They disappear after a few cycles.
- You can only catch **one sun per cycle**.
- Score: **10 points** per zombie killed, or **20** if it was killed by an explosion. The best score of each level is saved in `record.txt`.

### Commands

| Command | Shortcut | Description |
|---|---|---|
| `add <plant> <col> <row>` | `a` | Add a plant (costs suncoins) |
| `catch <col> <row>` | `c` | Catch a sun |
| `none` or empty line | `n` | Skip this cycle |
| `list` | `l` | List the available plants |
| `listZombies` | `lz` | List the available zombies |
| `record` | `o` | Show the record of the current level |
| `reset [<level> <seed>]` | `r` | Start a new game |
| `help` | `h` | Show the help |
| `exit` | `e` | Quit the game |
| `cheatPlant <plant> <col> <row>` | `cp` | Add a plant for free (debug) |
| `addZombie <idx> <col> <row>` | `az` | Add a zombie (debug) |

Plants can be written by name or by symbol, for example `add peashooter 2 1` or `a p 2 1`.

### Plants

| Plant | Symbol | Cost | Damage | Endurance | Behaviour |
|---|---|---|---|---|---|
| Sunflower | `S` | 20 | 0 | 1 | Generates a sun every 3 cycles |
| Peashooter | `P` | 50 | 1 | 3 | Shoots the first zombie in its row |
| Wall-Nut | `WN` | 50 | 0 | 10 | Blocks zombies |
| Cherry-Bomb | `c` (`C` just before exploding) | 50 | 10 | 2 | Explodes after 2 cycles, damaging the 3×3 area around it |

### Zombies

| Zombie | Symbol | Speed (cycles per move) | Endurance | Behaviour |
|---|---|---|---|---|
| Zombie | `Z` | 2 | 5 | — |
| BucketHead Zombie | `Bz` | 4 | 8 | Slow but tough |
| Sporty Zombie | `Sz` | 1 | 2 | Fast but weak |
| Explosive Zombie | `Ez` | 2 | 5 | Explodes when it dies, damaging nearby plants |

### Levels

| Level | Zombies | Spawn probability per cycle |
|---|---|---|
| `EASY` | 3 | 10% |
| `HARD` | 5 | 20% |
| `INSANE` | 10 | 30% |

## Build and run

Requirements: Java 17 or later.

```bash
javac -d bin $(find src -name "*.java")
java -cp bin tp1.p2.PlantsVsZombies EASY 42
```

The first argument is the level (`EASY`, `HARD` or `INSANE`). The second one is optional: it is the random seed, and the same seed always produces the same game.

On Windows (PowerShell):

```powershell
javac -d bin (Get-ChildItem -Recurse src -Filter *.java).FullName
java -cp bin tp1.p2.PlantsVsZombies EASY 42
```

If the board lines are not displayed correctly on Windows, run `chcp 65001` first so the console uses UTF-8.

## Design

```
src/tp1/
├── p2/
│   ├── PlantsVsZombies.java   Entry point (argument parsing)
│   ├── control/               Controller, Level, Command and its subclasses, exceptions
│   ├── logic/                 Game, GameObjectContainer, ZombiesManager, SunsManager, Record
│   │   ├── gameobjects/       Plants, zombies, suns and their factories
│   │   └── actions/           Delayed actions (explosions)
│   └── view/                  GamePrinter, Messages
└── utils/                     StringUtils
```

- **Command pattern**: each user command is a `Command` subclass that parses its own parameters (`create`) and runs its action (`execute`).
- **Factories with prototypes**: `PlantFactory` and `ZombieFactory` clone a prototype of each plant or zombie.
- **Interfaces to limit coupling**: game objects only see the game through `GameWorld`, and the view only reads it through `GameStatus`.
- **Polymorphism**: every plant and zombie overrides `update`, `onEnter` and `onExit`. There are no `instanceof` checks.
- **Action queue**: explosions are queued as `GameAction`s and run after the objects are updated. This allows chain reactions.
- **Custom exceptions**: errors are reported as a hierarchy of `GameException`s.
