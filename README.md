# kdit - Text Editor written in Kotlin

A minimalistic text editor without fancy buttons, just with a tab pane
and fully controllable with shortkeys.

## Shortkeys

ESC - Switch between tab pane and current tab 
Ctrl+Alt+Left - Switch to left tab 
Ctrl+Alt+Right - Switch to right tab 
Ctrl+T - New tab 
Ctrl+W - Close current tab 
Ctrl+H - Open a help tab 

Ctrl+O - Open a file 
Ctrl+Shift+O - Open a project (Not supported yet) 
Ctrl+S - Save current file 
Ctrl+Shift+A - Save current file as .. 

Ctrl+K - Delete line 
Ctrl+D - Duplicate line 
Ctrl+Up - Navigate one paragraph up 
Ctrl+Down - Navigate one paragraph down 
Shift+Enter - Add newline not breaking current line 

## Build/Execution

Download jar from Bintray and run it with `java -jar kdit-xxx.jar`

or

Git clone this repository and run `gradle shadowJar` to create a 
executable jar. Again use `java -jar kdit-xxx.jar`.
