# WhatsApp-Analyzer
A JavaFX application which analyzes the textfile of a Whatsapp conversation, currently just working with the format of
Spanish Android exports (dd/MM/yyy), not sure it will work with any other export format.

## Features
- Multiple people chats
- Message count
- Messages per people
- Conversation percentage per people
- Most talked day
- Most talked month
- Total words
- Words per message average
- Daily messages' average
- Daily messages average including days not talking
- Days chart, also available to export as JPGs and SVGs
- Time of the day chart
- Participants chart
- Words Cloud

## Build
WhatsApp-Analyzer requires Java 8, JavaFx 8 and JFreeChart, build as normal. tested with IntelliJ IDEA.

## How to use
Email yourself the conversation you want to analyze   
- Open WhatsApp   
- Choose the conversation  
- Menu -> More   
- Send chat by email   

Download it and open the .txt file with the program.

## TODO
- Better building
- Emoticons per message
- Add Iphone format
- Add new input formats
- Most talked year
- Add integration to SQLite to make a traceable history
- Improve visuals
- Messages' heuristic and grammatical analysis

## Screenshots
### Main
![Principal](http://i.imgur.com/nBv5ldL.png)
### Days chart
![Days](http://i.imgur.com/m1Rwgyd.png?1)
### All days chart
![TotalDays](http://i.imgur.com/wJGPoms.png?1)
### Participants chart
![Participants](http://i.imgur.com/PA69xoJ.png?1)
### Time of the day chart
![Time](http://i.imgur.com/CAkRFWD.png?1)
### Cloud word

![cloud](http://i.imgur.com/1mvZDxz.png?1)
