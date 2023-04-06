# SBMBingo
This is a simple Minecraft Bingo Minigame designed for any amount of players, could be one or even one hundred!

## How to download
Head over to the [releases](https://github.com/ericlmao/SBMBingo/releases) page and download the latest version. You can also download the source code and compile and modify it yourself.

## Commands
The only commands are the administrative commands.


| Command              | Permission    | Description                           |
|----------------------|---------------|---------------------------------------|
| `/bingoadmin start`  | `bingo.admin` | Starts the game                       |
| `/bingoadmin stop`   | `bingo.admin` | Stops the game                        |
| `/bingoadmin reload` | `bingo.admin` | Reloads the configs                   |
| `/bingoadmin teams`  | `bingo.admin` | Monitor Teams and their game progress |


## How to configure
By default, the plugin is plug-and-play but you can also configure the cards and have different goals and objectives.

The configuration is divided into sub-sections that will be explained below.

### Teams
The teams section is a list of all available teams and their status, if you don't want `GREEN` team to be available to join, just make the value `false`
```yml
teams:
  RED: true
  BLUE: true
  GREEN: true
  YELLOW: true
  WHITE: true
  BLACK: true
  AQUA: true
  DARK_AQUA: true
  DARK_BLUE: true
  DARK_GRAY: true
  DARK_GREEN: true
  DARK_PURPLE: true
  DARK_RED: true
  GOLD: true
  GRAY: true
  LIGHT_PURPLE: true
```

### Game Duration
This is the maximum duration of the game, if the game is not finished by this time, it will automatically end.
```yml
max-game-duration: "1h" # Maximum duration of the game
```

### Bingo Card (Item)
This is the item that is used to open the Card Menu and see the current progress of the player.

Configuring the `material` of the item will be slightly difficult as Minecraft is inconsistent with their naming.

For material references, see [here](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Material.html)
```yml
bingo-card:
  name: "&aBingo Card &7(Right Click)"
  material: FILLED_MAP
  slot: 9
  locked: true # If true, the item will be locked in the inventory
```

### Bingo Team Selector (Item)
This is the item that is used to open the Team Selector Menu and select a team.

Configuring the `material` of the item will be slightly difficult as Minecraft is inconsistent with their naming.

For material references, see [here](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Material.html)
```yml
bingo-team-selector:
  name: "&aTeam Selector &7(Right Click)"
  material: NETHER_STAR
  slot: 1
  locked: true # If true, the item will be locked in the inventory
```

### Bingo Goals
This is the list of all the goals that are available to complete. You do not need to worry about the order of the goals as they are shuffled when the game begins.

This default configuration has an example of all the types of goals you can have at the moment.

The entire list of `goal-type` types are the following:
* `COLLECT` - Collect a certain amount of a certain material
* `KILL` - Kill a certain amount of a certain entity
* `POTION_EFFECT` - Get a certain potion effect
* `FIND_BIOME` - Find a certain biome
* `DIE` - Die a certain amount of times

Configuration Example:
```yml
goals:
  COLLECT_DIAMOND: # ID of the Goal
    name: "&fCollect a &bDiamond" # Display Name of the Goal (in the GUI)
    material: DIAMOND_ORE # Material of the Goal (in the GUI)

    goal-type: "COLLECT"
    goal-material: DIAMOND
    goal-amount: 1

  COLLECT_COAL:
    name: "&fCollect 32 &8Coal"
    material: COAL_ORE

    goal-type: "COLLECT"
    goal-material: COAL
    goal-amount: 32

  KILL_PIG:
    name: "&fKill a &dPig"
    material: PORKCHOP

    goal-type: "KILL"
    goal-entity: PIG
    goal-amount: 1

  GET_SPEED:
    name: "&fGet the &bSpeed &fPotion Effect"
    material: SUGAR

    goal-type: "POTION_EFFECT"
    goal-potion-effect: speed

  FIND_DESERT:
    name: "&fFind a &6Desert"
    material: SAND

    goal-type: "FIND_BIOME"
    goal-biome: DESERT

  DO_THE_DIE:
    name: "&fDie &c5 &ftimes"
    material: BARRIER

    goal-type: "DIE"
    goal-cause:
    goal-amount: 5
```