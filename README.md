# <img width="400" alt="stratego" src="https://user-images.githubusercontent.com/26120940/32502146-37fad856-c397-11e7-80e1-a2edf3336774.png" />

> **Stratego** [/strəˈtiːɡoʊ/](https://en.wikipedia.org/wiki/Help:IPA/English) is a strategy board game for two players on a board of 10×10 squares. Each player controls 40 pieces representing individual officer ranks in an army. The objective of the game is to find and capture the opponent's *Flag*, or to capture so many enemy pieces that the opponent cannot make any further moves. *Stratego* has simple enough rules for young children to play, but a depth of strategy that is also appealing to adults. — Wikipedia

[@nuttywhal](https://github.com/nuttywhal) and [@david-henderson](https://github.com/david-henderson) implemented this board game as a final project for SER 215 (Software Enterprise II) in Fall 2014 during our third semester at Arizona State University. It was written as a distributed application using a client–server model. The server awaits socket connections from two different clients and then dispatches a thread to handle a game session between those clients. The server is responsible for information security and enforcing the game rules so that players may not modify the game client to gain an advantage.

## Building

```bash
# Clone the GitHub respository and `cd' into it.
git clone https://github.com/nuttywhal/stratego.git && cd stratego/

# Compile all of the *.java files into *.class files.
javac -cp src/edu/asu/stratego/**/*.java src/edu/asu/stratego/*.java -d temp/

# Copy all of the image assets.
cd src && cp --parents edu/asu/stratego/**/*.png ../temp && cd ..

# Create executable JAR files for the client and the server.
jar cvfm bin/client.jar src/manifest/client.mf -C temp/ .
jar cvfm bin/server.jar src/manifest/server.mf -C temp/ .

# Clean up.
rm -r temp/
```

… at this point in time, we did not know about build automation tools. :sob:

## Running

```bash
# Executing the client...
java -jar bin/client.jar &

# Executing the server...
java -jar bin/server.jar
```
## Screenshot

<img src="https://user-images.githubusercontent.com/26120940/32508089-705837d4-c3a6-11e7-9ca7-07c7a59778da.png" alt="stratego" align="middle" />