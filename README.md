# Domains

A command-line Kotlin application that reads email addresses from standard input and outputs
the top 10 most frequent email domains.

### Prerequisites

- Kotlin 2.0+
- JDK 17+
- Gradle

### Running the Application

You can run this application in any of the following ways:

#### 1. Run directly from your IDE

- Open the project in IntelliJ IDEA or your preferred IDE.
- Go to [Main.kt](src%2Fmain%2Fkotlin%2Fcom%2Fdomains%2FMain.kt)
- Click the **Run** (▶️) button next to the `main` function.
- The program will start and wait for your input in the console.

---

#### 2. Run with Gradle
From the terminal, you can run the program with Gradle:

```bash
./gradlew run --console=plain
```

#### 3. Run with Java

Build the JAR file:
```bash
./gradlew jar
```

Then run the JAR file using Java:
```bash
java -jar build/libs/app.jar
```
