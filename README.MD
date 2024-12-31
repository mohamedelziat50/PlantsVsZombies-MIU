# Plants vs Zombies - MIU

## Overview
This project is a JavaFX-based game developed as part of our **Object-Oriented Programming Project**. It showcases a strategic gameplay experience with custom-designed levels while utilizing core OOP concepts such as encapsulation, inheritance, abstraction, and additional features like threading, collision handling, etc.

---

## Features
- 🎮 **Engaging Gameplay:** Battle zombies using various plant abilities.
- 🎨 **Custom Levels:** Three progressively challenging levels:
  - **Level 1:** Start with 3 cards.
  - **Level 2:** Unlock 2 more cards (total 5 cards).
  - **Level 3:** Unlock 2 more cards (total 7 cards), themed for **Christmas**.
- 🔊 **Lovely Sounds:** Enjoy immersive sound effects throughout the game.
- 🔧 **JavaFX Implementation:** Built entirely using JavaFX for a dynamic user interface.

---

## How to Run
To run the project, JavaFX must be installed and configured. Follow these steps for a smooth experience:

### Prerequisites
- **JavaFX SDK** (e.g., `javafx-sdk-23.0.1`).
- **IntelliJ IDEA** (recommended for easy setup).

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

## Screenshots

### Main Menu
*(Insert main menu screenshot here)*

### Levels
- **Level 1:** Beginner Level *(Insert screenshot here)*
- **Level 2:** Intermediate Level *(Insert screenshot here)*
- **Level 3:** Advanced Level *(Insert screenshot here)*

---

## Video Previews
1. **Main Menu & Level 1 Gameplay:**
[Video Link](#) *(replace with actual link)*

2. **Level 3 Gameplay (Christmas Level):**
[Video Link](#) *(replace with actual link)*

---

## Common Issues and Fixes
- **Missing JavaFX SDK:** Ensure the correct path to the JavaFX SDK is set in the project configuration.
- **Main Class Not Found:** Verify that the correct Main class is selected in the run configurations.
- **Platform Compatibility:** The project works best with IntelliJ but can be set up on NetBeans or other IDEs by following similar configuration steps.

