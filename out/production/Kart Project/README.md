# Card Game
[ENG]

The purpose of this project is to demonstrate object-oriented programming principles and develop problem-solving skills by designing a simple card game consisting of 16 cards. The game, in which the player competes against the computer, will be played in two different categories: football and basketball. Each player has 8 cards (4 football players and 4 basketball players), and each category has 3 different match types (for football: free kick, penalty, one-on-one with the goalkeeper; for basketball: free throw, three-pointer, two-pointer). In each turn, a random match type from the active category will be selected, and the chosen players' attribute points will be compared. The score table will be updated accordingly, and the game will end when all cards have been used.

In our program, which is written in Java using the NetBeans IDE and the Java Swing UI library, past scores are recorded and displayed in a simple database structure. Each athlete has an image, with their attribute points displayed on it. The game flow and score table can be tracked through this interface, and the player selects cards via the UI. The computer's randomly selected card and match type are compared with the player's chosen card. Used cards cannot be reused, and the player cannot see the computer’s cards.

## Method
The Athlete (Sporcu) parent class is inherited by the Footballer (Futbolcu) and Basketball Player (Basketbolcu) classes. Similarly, the Player (Oyuncu) parent class is inherited by the User (Kullanıcı) and Computer (Bilgisayar) classes. Common attributes are defined in the parent classes, keeping the code clean and structured.The Text class is responsible for defining the athletes' attributes and names and contains the main method where the game flow is executed. The game logic is controlled and managed within the Text class.Player scores are defined as private variables and are accessed through getter and setter methods, ensuring encapsulation.



