# The Legendary Giggle Adventure

Legendary Giggle is an adventure game inspired by the TV series Rick and Morty, where the duo have an interstellar adventure. 

In this console game, you set out on an adventure from the edge of the universe. 
You will come across friendly planets, enemy planets that seek revenge, neutral planets that ignore your presence, 
and your home planet that you left a long time ago.

At the moment, the game does not have an end game goal. However, the idea is to gain lots of experience while staying alive.

- On your home planet, you gain full health, but gain no experience.
- On an ally planet, you gain some health, and some experience.
- On an enemy planet, you lose health, but also gain quite some experience.
- On a neutral planet, you gain no health nor experience.

Your spaceship has 4 windows, one in each direction North, East, West and South 
and that limits your vision to only 4 planets around you. 
So you explore one planet at a time and can move only in one of these four directions.
You cannot move beyond the edge of the universe. 

#### Main Menu options

1. Start a new adventure
2. Resume adventure
3. Exit

#### In game menu options:

- w => North
- a => West
- s => South
- d => East
- save => save Adventure
- exit => exit Adventure to game menu

![alt text](./wasdKeys.png)

### Game extension ideas

- Parallel universe: You enter a parallel universe where an ally planet in the current universe may be an enemy planet in the parallel universe.
- Portal Gun: On entering a planet, you are randomly relocated to another planet.
- Improve planet visuals

## Setting up the package

```
    mvn clean install
```    
    

## Starting the game

```
    mvn exec:java -Dexec.mainClass="com.game.legendarygiggle.GameApp"
```

## Running the tests

* To run the unit tests, call `mvn test`
* Code coverage reports are generated when `mvn verify` (or a full `mvn clean install`) is called.
  Point a browser at the output in `target/site/jacoco-both/index.html` to see the report.

## Project improvements

- Avoid delay within unit test by mocking miniDelay functionality to not sleep during tests 
- Command pattern on Adventure to undo previous move
- Include logging
- Integration tests

## Codebase layout

- `MainMenu object` orchestrates the Game
- `InGameMenu object` orchestrates the Adventure => it is composed of an `Adventure object`
- `Adventure object` => is composed of `Player object` + `UniverseMap object`

## Packages used

* Unit tests via [JUnit 5](https://junit.org/junit5/)
* Code coverage reports via [JaCoCo](https://www.jacoco.org/jacoco/)
* A Maven build that puts it all together
