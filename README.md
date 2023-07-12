# Dice game

# Discription
This dice betting game allows users play dice game:
Each player will be granted 10 euros when registered. The betting limit is 2-6 euros each time.
When the player wins, the player win the same amount of money. Otherwise, the player loses all the bet amount.

# Endpoints:
POST/game: the player sets bet.

POST/customer: register player
POST/customer/login: login play (note: the play needs to be logged in to place a bet)
GET/customer/{ssn}: check player information and status
DELETE/customer/{ssn}: remove player

# Install and Run
Docker is needed to install and run the game.
Under dice-game repository
Type 
1. "docker build -t dice-game:0.0.1 ."
2. "docker compose up"
Visit http://localhost:8080/dice-game/swagger-ui.html to play the game.

# Stop and Uninstall
Type
1. "docker stop dice-game"
2. "docker stop dicegamemongodb"


