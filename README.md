## Configuration
### Property Files
#### application.properties

**(Required)** The following configuration is recommended for MySQL database connectivity.

    spring.datasource.url=jdbc:mysql://<host>:<port>/<database>?serverTimezone=UTC
    spring.datasource.username=<username>
    spring.datasource.password=<password>
    spring.jpa.hibernate.ddl-auto=update
    spring.datasource.driver-class-id=com.mysql.jdbc.Driver
    
#### bot.properties

`bot.prefix=<str>` **(Required)** Command prefix. This is what all commands will be prefixed with.

`bot.token=<str>` Bot token. Obtained at <https://discordapp.com/developers/applications/me>. If not provided, you will be prompted at runtime.

`bot.logMessages=<bool> (Default: false)` Set this property to true if you wish all messages to be logged.

`bot.reddit.enabled=<bool> (Default: false)` Enable the Reddit module.

(The following four properties are required if `bot.reddit.enabled` is set to true)

	bot.reddit.username=<str>
	bot.reddit.password=<str>
	bot.reddit.clientId=<str>
	bot.reddit.clientSecret=<str>

(Bomb command, disabled by default)

	bot.bomb.enabled=<bool> (Default: false)
	bot.bomb.maxTimeMs=<int> (Default: 200_000)
	bot.bomb.minTimeMs=<int> (Default: 60_000)
	bot.bomb.minWires=<int> (Default: 2)
	bot.bomb.maxWires=<int> (Default: 6)

## Running

To run the bot, you can run it with Spring Boot. The below command will compile all of the source code
run the bot.

	mvn spring-boot:run
	
## License

This project is licensed under GPLv3. For more information, see LICENSE.