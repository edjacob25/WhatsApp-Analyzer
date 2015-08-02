# WhatsApp-Analyzer
A JavaFX application which analyzes the textfile of a Whatsapp conversation, currently just working with the format of
Spanish exports (dd/MM/yyy), not sure it will work with any other export format.

## Features
- Message count
- Messages per people
- Conversation percentage per people
- Most talked day
- Most talked month
- Daily messages' average
- Daily messages average including days not talking
- Day Graphs as JPGs and SVGs

## Build

WhatsApp-Analyzer requires Java 8, JavaFx 8 and JFreeChart, build as normal. tested with IntelliJ IDEA.

## How to use
Email yourself the conversation you want to analyze   
- Open WhatsApp   
- Choose the conversation  
- Menu -> More   
- Send chat by email   

Download it and open the .txt file with the program, the graphs are located at the program rute

## TODO
- Improve graphs
- Better building
- Words total
- Words per message average
- Emoticons per message
- Add new regionalizations
- Most talked year
- Finish transformation to regex
- Add integration to SQLite to make a traceable history
- Improve visuals
- Messages' heuristic and grammatical analysis