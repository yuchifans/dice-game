# Dice game

# Description
This dice betting game allows users play dice game:
Each player will be granted 10 euros when registered. The betting limit is 2-5 euros each time. The betting limit can be configured.
When the player wins, the player win the same amount of money. Otherwise, the player loses all the bet amount.

# Endpoints:
1. POST/game: the player sets bet.

2. POST/customer: register player
3. POST/customer/login: login play (note: the play needs to be logged in to place a bet)
4. GET/customer/{ssn}: check player information and status
5. DELETE/customer/{ssn}: remove player

# Installation
Prerequisite: Docker needs to be installed.

To install the game:
Under dice-game repository
1. "docker pull mongo:latest"
2. "docker build -t dice-game:0.0.1 ."
3. "docker compose up"
After installation, visit http://localhost:8080/dice-game/swagger-ui.html to play the game.

# Uninstallation
To stop the game:
1. "docker stop dice-game"
2. "docker stop dicegamemongodb"

To uninstall the game:
1. "docker rm dice-game"
2. "docker rm dicegamemongodb"


