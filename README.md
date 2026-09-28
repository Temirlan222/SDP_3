# Assignment 3: Bridge and Adapter

Match reports can be published through Telegram, Instagram, or a legacy newspaper press. `MatchReport` and its two variants form the Bridge abstraction hierarchy; `BroadcastChannel` is the implementation interface. The newspaper channel uses an Adapter.

Build and run JUnit 5 tests with JDK 17+ and Maven:

```sh
mvn clean package
```

In IntelliJ, run `Assik3.Main` and enter a channel and destination in the Run console. You can also pass them as command-line arguments:

```sh
java -jar target/software-design-patterns-1.0-SNAPSHOT.jar telegram "@match"
java -jar target/software-design-patterns-1.0-SNAPSHOT.jar instagram match-updates
java -jar target/software-design-patterns-1.0-SNAPSHOT.jar newspaper results
```

For the newspaper, destination is `news` or `results`. Output is simulated locally. The [design rationale](docs/assignment3-rationale.md) and [UML diagram](docs/assignment3-uml.svg) explain the design.
