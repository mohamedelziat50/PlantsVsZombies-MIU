- JavaFX is required in order to run this project, follow these steps:

1. Simply clone the project

2. File -> Project Structure(CTRL + ALT + SHIFT + S), Go to Modules, Under Project Name select dependencies

3. Press + sign, add path of lib folder for javafx's sdk(replace with appropiate path): E:\openjfx-23.0.1_windows-x64_bin-sdk\javafx-sdk-23.0.1\lib"

4. Go to Run -> Edit Configurations and Add Application: Select Main class and edit VM options with this line(replace with appropiate path): 
--module-path E:\openjfx-23.0.1_windows-x64_bin-sdk\javafx-sdk-23.0.1\lib
--add-modules javafx.controls,javafx.fxml,javafx.media 
--add-opens javafx.base/com.sun.javafx=ALL-UNNAMED

