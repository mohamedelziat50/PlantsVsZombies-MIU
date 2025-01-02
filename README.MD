# Plants vs Zombies - MIU

## Overview
This project is a JavaFX-based game developed as part of our **Object-Oriented Programming Course Project**. It showcases a strategic gameplay experience with custom-designed levels while utilizing core OOP concepts such as encapsulation, inheritance, abstraction, and additional features like threading, collision handling, etc.

---

## Features
- 🎮 **Engaging Gameplay:** Battle zombies using various plant abilities.
- 🎨 **Custom Levels:** Three progressively challenging levels:
  - **Level 1:** Start with 3 cards, themed for the **Day**.
  - **Level 2:** Unlock 2 more cards (total 5 cards), themed for the **Night**.
  - **Level 3:** Unlock 2 more cards (total 7 cards),  specially themed for **Christmas**, inspired by our project's discussion on the last day of the year.
  - **Difficulty:** The game progressively gets harder as you advance through the levels, ensuring an exciting challenge for players.

- 🔊 **Sounds:** Enjoy immersive sound effects throughout the game. Only the **Main Menu soundtrack** is included in the preview to provide users with a novel feeling during gameplay. Gameplay soundtracks are unlocked during actual gameplay to enhance the **experience**. 😉
- ⏱️ **Game Duration:** Each level offers an immersive experience lasting approximately 4 minutes.

- 🔧 **JavaFX Implementation:** Built entirely using JavaFX for a dynamic user interface.

## Screenshots

### Main Menu
- **Authentication:**
![mainMenu1](https://github.com/user-attachments/assets/d7a9e0b2-61c9-49ed-920a-8999552296dc)
- **Adventure:**
![mainMenu2](https://github.com/user-attachments/assets/75988136-d2be-498b-9fde-d2683511863f)

### Levels
- **Level 1:** Beginner Level ![level1-picture](https://github.com/user-attachments/assets/6557c752-5823-4be3-b384-f16039b03b5c)
- **Level 2:** Intermediate Level ![level2-picture](https://github.com/user-attachments/assets/ce370e11-f187-4823-b46e-d8f6eaec6700)
- **Level 3:** Advanced Level ![level3-picture](https://github.com/user-attachments/assets/386e3e14-c452-4c80-95da-3d7df7b880f3)

---

## Video Previews
1. **Main Menu & Yard Preview:**

https://github.com/user-attachments/assets/f43abb73-d497-4d36-9c11-102bfdc5e608

2. **Level 1 Gameplay:**

https://github.com/user-attachments/assets/341b2e8d-24b0-46b7-9ff9-df0412788867

3. **Level 3 Gameplay (Christmas Level):**

https://github.com/user-attachments/assets/f9f44384-3bc2-4d2a-955b-ece9ccd564d7

---

---

## How to Run
To run the project, JavaFX must be installed and configured. Follow these steps for a smooth experience:

### Prerequisites
- **JavaFX SDK** (e.g., `javafx-sdk-23.0.1`).
- **IntelliJ IDEA** (recommended for easy setup, but other platforms, such as NetBeans, can also be used).

### Steps
1. **Clone the Repository:**
   ```bash
   git clone <repository_url>
   ```
2. **Configure JavaFX in IntelliJ:**
   - Go to `File -> Project Structure (CTRL + ALT + SHIFT + S)`.
   - Navigate to `Modules`.
   - Under your project, select `Dependencies` and click the `+` sign to add the JavaFX library.
     - Add the path to the `lib` folder of the JavaFX SDK, e.g., `E:\openjfx-23.0.1_windows-x64_bin-sdk\javafx-sdk-23.0.1\lib`.
3. **Edit VM Options:**
   - Go to `Run -> Edit Configurations`.
   - Add a new Application configuration and set the Main class to `MainGUI`.
   - In `VM Options`, add the following line (replace the path with your JavaFX SDK path):
     ```
     --module-path E:\openjfx-23.0.1_windows-x64_bin-sdk\javafx-sdk-23.0.1\lib --add-modules javafx.controls,javafx.fxml,javafx.media --add-opens javafx.base/com.sun.javafx=ALL-UNNAMED
     ```
4. **Run the Project:**
   Click `Run` to launch the game.

---

## Common Issues and Fixes
- **Missing JavaFX SDK:** Ensure the correct path to the JavaFX SDK is set in the project configuration.
- **Main Class Not Found:** Verify that the correct Main class is selected in the run configurations.
- **Platform Compatibility:** The project works best with IntelliJ but can be set up on NetBeans or other IDEs by following similar configuration steps.

