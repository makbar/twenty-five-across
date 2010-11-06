CREATE TABLE `UserInfo` (
	`UserName` varchar(10) NOT NULL,
	`Password` varchar(40) NOT NULL,
	`Rating` int(5),
	`Status` int(1),
	`DisplayName` varchar(40) NOT NULL,
	PRIMARY KEY(`UserName`));

CREATE TABLE `Games` (
	`GameId` int(5) NOT NULL,
	`Status` ENUM('Finished', 'Active'),
	`AccessType` ENUM('Public', 'Private'),
	PRIMARY KEY(`GameId`));

CREATE TABLE `ActiveUsers` (
	`UserName` varchar(10) NOT NULL,
	`GameId` int(5) NOT NULL,
	`level` ENUM('spectator', 'player'),
	`Score` int(5),
	primary key (`UserName`, `GameId`),
	FOREIGN KEY (`UserName`) REFERENCES UserInfo(`UserName`) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (`GameId`) REFERENCES Games (`GameId`) ON DELETE CASCADE ON UPDATE CASCADE);


CREATE TABLE `ClueList` (
	`GameId` int(5) NOT NULL,
	`Number` int(2) NOT NULL,
	`Orientation` ENUM ('across', 'word'),
	`Clue` varchar(100) NOT NULL,
	`RealSolution` varchar(50) NOT NULL,
	`PlayerSolution` varchar(50) NOT NULL,
	`PlayerName` varchar(10),
	PRIMARY KEY (`GameId`, `Number`, `Orientation`),
	FOREIGN KEY (`GameId`) REFERENCES Games (`GameId`) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (`PlayerName`) REFERENCES UserInfo(`UserName`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `SquareLocations` (
	`locationId` int(5) NOT NULL,
	`GameId` int(5) NOT NULL,
	`Number` int(2) NOT NULL,
	`Orientation` ENUM ('across', 'word'),
	PRIMARY KEY (`locationId`, `GameId`, `Number`, `Orientation`),
	FOREIGN KEY (`GameId`, `Number`, `Orientation`) REFERENCES ClueList(`GameId`, `Number`, `Orientation`) ON DELETE CASCADE ON UPDATE CASCADE
);
