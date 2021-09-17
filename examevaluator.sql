-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: localhost    Database: examevaluator
-- ------------------------------------------------------
-- Server version	8.0.23

-- ALUMNO: Franco Aguilar Lenin Eduardo
-- PROYECTO Aplicador y evaluador de examenes de opcion multiple
-- FECHA: 15/06/2021
-- GRUPO: 2CM13
-- MATERIA: PROGRAMACION ORIENTADA A OBJETOS
-- PROFESOR: TECLA PARRA ROBERTO

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `choice`
--

DROP TABLE IF EXISTS `choice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `choice` (
  `idp` int NOT NULL,
  `choice` varchar(70) NOT NULL,
  PRIMARY KEY (`idp`,`choice`),
  CONSTRAINT `fk2` FOREIGN KEY (`idp`) REFERENCES `question` (`idp`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `choice`
--

LOCK TABLES `choice` WRITE;
/*!40000 ALTER TABLE `choice` DISABLE KEYS */;
INSERT INTO `choice` VALUES (1,'Maybe they count wolves'),(1,'They count humans'),(1,'They count sheep too'),(2,'Because that is how someone made it up'),(2,'BECAUSE THAT IS HOW SOMEONE MADE IT UP in cappital letters'),(2,'Because the people who made it up are dyslexic'),(3,'Because it is funny'),(3,'Because we leave the beans on the stove'),(3,'Because we leave the children alone at home'),(4,'No, because they are fed up with their work'),(4,'No, they are robots'),(4,'Yes, because they are happy'),(5,'Because it was the only name they came up with'),(5,'Because missions are impossible until Tom Cruise appears'),(5,'Because the writer wanted it to be like that'),(6,'I do not know'),(6,'Maybe'),(6,'Yes'),(7,'I did not even know that dictionaries were still being sold'),(7,'I would ask OK Google'),(7,'What is a dictionary?'),(8,'Because Einstein never had a father'),(8,'I do not know'),(8,'I DO NOT KNOW in capital letters'),(9,'Because we are too lazy to give it a name'),(9,'I do not know'),(9,'Not even God knows'),(10,'I am not sure'),(10,'Maybe'),(10,'Quite so!'),(11,'III-III'),(11,'IV-IV'),(11,'V-V'),(12,'Because it is just a movie'),(12,'I do not know'),(12,'Not even God knows'),(13,'Maybe'),(13,'Usually, the light goes to breakfast'),(13,'Yes'),(14,'I do not know'),(14,'Yes'),(14,'YES in capital letters'),(15,'I do not know'),(15,'Throwing it away'),(15,'Using super powers'),(16,'Consult a specialist'),(16,'Nothing'),(16,'Run away from there or they will blame you'),(17,'No, they are only distant relatives'),(17,'Of course not, they are cousins'),(17,'Yes, of course'),(18,'Because usually stores close on leap years'),(18,'I do not know'),(18,'Not even God Knows'),(19,'I was lost in the war'),(19,'It was stolen and no one knows where it is'),(19,'Yes'),(20,'I do not know'),(20,'Yes'),(20,'YES in capital letters'),(21,'I do not know'),(21,'They last 30 minutes less'),(21,'They last about three hours'),(22,'Maybe'),(22,'Yes'),(22,'YES in capital letters'),(23,'I do not know'),(23,'No, the jack can eat it'),(23,'Of course'),(24,'I do not know'),(24,'It can be filled with water'),(24,'Not even God knows'),(25,'I do not know'),(25,'Not even God knows'),(25,'Yawn'),(26,'0044'),(26,'2044'),(26,'4004'),(27,'She measured the threads'),(27,'She spun the threads'),(27,'She worked alongside with Thanos in balancing the universe'),(28,'Hint: this option is not yours'),(28,'My lover'),(28,'Owning people is not correct, so it is not your lover'),(29,'59 seconds'),(29,'An hour'),(29,'Less than a minute'),(30,'1919'),(30,'2020'),(30,'2035'),(31,'If it is not Werevertumorro, it must be Yuya'),(31,'Peepeepoopoo'),(31,'Werevertumorro'),(32,'Bichu, Bichu'),(32,'D, o, g'),(32,'L, i, o, n'),(33,'Benito Juarez'),(33,'Bob Marley'),(33,'Freddy Mercury'),(34,'Gerald Ford'),(34,'Jymmy Carter'),(34,'Lyndon Jhonson'),(35,'Ricaleta'),(35,'Richard'),(35,'Ricon'),(36,'Cemitas'),(36,'Pastes'),(36,'Tacos'),(37,'Dark Vader'),(37,'Yanemba'),(37,'Yoda'),(38,'In 1980'),(38,'In 1999'),(38,'In 2000'),(39,'No'),(39,'This is not the answer'),(39,'Yes, no, maybe'),(40,'Cristiano Ronaldo'),(40,'Donald Trump'),(40,'Michael Jordan'),(41,'Any director of Bollywood'),(41,'Nobody'),(41,'The UK President'),(42,'Jupiter'),(42,'Kepler-438b'),(42,'Pluto'),(43,'2'),(43,'3'),(43,'88'),(44,'No'),(44,'The answer is in your heart'),(44,'This is not the answer'),(45,'01:00'),(45,'12:00'),(45,'12:30'),(46,'I am sure he is not'),(46,'No, they are siblings'),(46,'Of course, my horse'),(47,'No'),(47,'Nope'),(47,'Of course, no'),(48,'Ayiou, wasap bro'),(48,'Guau'),(48,'They do not make sounds'),(49,'My aunt'),(49,'My brother'),(49,'My dog'),(50,'One'),(50,'Two'),(50,'Zero'),(51,'Habbo'),(51,'Java'),(51,'Python'),(52,'Pastor Aleman'),(52,'Pikachu'),(52,'San Bernardo');
/*!40000 ALTER TABLE `choice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exam`
--

DROP TABLE IF EXISTS `exam`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exam` (
  `ide` int NOT NULL AUTO_INCREMENT,
  `idu` int NOT NULL,
  `title` varchar(100) NOT NULL,
  `grade` int DEFAULT NULL,
  `timeleft` int NOT NULL,
  PRIMARY KEY (`ide`,`idu`),
  KEY `fk1` (`idu`),
  CONSTRAINT `fk1` FOREIGN KEY (`idu`) REFERENCES `user` (`idu`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exam`
--

LOCK TABLES `exam` WRITE;
/*!40000 ALTER TABLE `exam` DISABLE KEYS */;
INSERT INTO `exam` VALUES (1,1,'Examen 1',4,270),(2,1,'Examen 2',3,270),(3,1,'Examen 3',3,272),(4,3,'Examen 4',2,262),(5,1,'Examen 5',4,0),(6,1,'Examen 6',0,0);
/*!40000 ALTER TABLE `exam` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `question` (
  `idp` int NOT NULL AUTO_INCREMENT,
  `question` varchar(1000) NOT NULL,
  `answer` varchar(70) NOT NULL,
  PRIMARY KEY (`idp`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES (1,'What do sheep count to be able to sleep?','Sheep cannot count'),(2,'Why is separate written all together and all together written separately?','Because it is just a spelling rule'),(3,'Why do we run when it rains?','Because we want to shelter from the rain'),(4,'When you take a picture next to Mickey Mouse, is the man in the costume smiling?','Maybe, but it is too creepy to think about it'),(5,'Why is the saga called Mission Impossible if they always managed to complete the missions?','Because missions are impossible, but everything is possible in life'),(6,'If the pool is Honda, is the sea Toyota?','Oh, of course not'),(7,'If a word were misspelled in the dictionary, how would we know it?','Commonly they cannot have errors because people review them'),(8,'If parents know more than children, why is Einstein famous and nobody knows anything about his dad?','Because he was more brilliant and knew how to relate more to people'),(9,'Why do the moons of other planets have names but ours is called the moon?','Because we are based on our beliefs'),(10,'If a lawyer goes crazy, does he lose his trial?','Of course, not'),(11,'How do you write zero in roman numbers?','There is no zero in Roman numerals'),(12,'Why do space battle films have such noisy explosions if the sound does not propagate in a vacuum?','Because it is more entertaining with explosions'),(13,'Where does the light go when it goes?','I do not know'),(14,'If there is a beyond, is there a less here?','Maybe'),(15,'If I want to buy a new boomerang, how do I get rid of the old one and keep it from coming back?','Giving it to someone else'),(16,'What should one do if they see an endangered animal eating an endangered plant?','Consult a specialist'),(17,'Is Plato the ladle brother?','Of course not'),(18,'If the 24-hour stores are open all day, why do the doors have a lock?','Because usually the stores close on holidays'),(19,'Where is the other half of the Middle East?','Not even God knows'),(20,'Is there any other word for synonym?','Maybe'),(21,'How long are the late hours of the night?','They last the same as the hours of the day'),(22,'Why is the boot called a boot and the ball called a ball, the ball being the one that bounces?','I do not know'),(23,'Can I keep my computer mouse in the trunk of the car with the jack?','Of course not'),(24,'What can an empty bucket be filled with?','Can be filled with holes'),(25,'What does everyone do when they wake up?','Open the eyes'),(26,'Which option has two zero and two fours?','None because the question is grammatically incorrect'),(27,'In Greek mythology, what  did Atropos, one of the three Moirai, do?','She cut the threads'),(28,'What is completely yours but everyone else uses it?','My name'),(29,'Complete the translated phrase: “We are about to get off the plane here in Oaxaca, we are landing in a minute, not a minute, less, I think in…”','5 minutes'),(30,'Choose the option that correctly expresses the year when COVID-19 was first observed in Wuhan, China','2019'),(31,'From the following youtubers, which one has the most-subscribed YouTube channel?','PewDiePie'),(32,'How do you spell cat?','C, a, t'),(33,'Who wrote the song “Imagine”?','Jhon Lenon'),(34,'Which of these U.S Presidents appeared on the television series “Laugh-In”?','Richard Nixon'),(35,'Complete the phrase: “Ricky Riquin…”','Canallin'),(36,'Which option is not Mexican food?','Pizza'),(37,'What is the cutest thing in the series “The Mandalorian”?','Baby Yoda'),(38,'When did United Kingdom finally cut its ties with the European Union?','In 2021'),(39,'Is the number 2^10019082982919109102829 even?','Yes'),(40,'Who created Minecraft?','Markus Persson'),(41,'Who is regarded as the head of the Indian State?','The Chief Minister'),(42,'Which planet orbits the closest to the sun?','Mercury'),(43,'What is the result of 2 + 2?','4'),(44,'Is the number 2^10019082982919109102829 + 1 even?','No'),(45,'What is exactly the time at midnight in a 24-hour format?','00:00'),(46,'Is Bad Bunny Bugs Bunny’s cousin?','Maybe'),(47,'Do drunk people and children always tell the truth?','Yessir'),(48,'What is the sound that cats make?','Meow'),(49,'If you did not have siblings, who would be your dad´s child?','Me'),(50,'What is the result of dividing by zero?','It is not possible to divide by zero'),(51,'Which of these options is not a programming language?	','Habbo	'),(52,'¿Cual de las siguientes opciones no es una raza de perro?','Chihuahua	');
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `response`
--

DROP TABLE IF EXISTS `response`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `response` (
  `ide` int NOT NULL,
  `idu` int NOT NULL,
  `idp` int NOT NULL,
  `useranswer` varchar(70) DEFAULT NULL,
  PRIMARY KEY (`ide`,`idu`,`idp`),
  KEY `fk4` (`idu`),
  KEY `fk5` (`idp`),
  CONSTRAINT `fk3` FOREIGN KEY (`ide`) REFERENCES `exam` (`ide`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk4` FOREIGN KEY (`idu`) REFERENCES `user` (`idu`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk5` FOREIGN KEY (`idp`) REFERENCES `question` (`idp`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `response`
--

LOCK TABLES `response` WRITE;
/*!40000 ALTER TABLE `response` DISABLE KEYS */;
INSERT INTO `response` VALUES (1,1,3,'Because we want to shelter from the rain'),(1,1,5,'Because the writer wanted it to be like that'),(1,1,12,'Because it is more entertaining with explosions'),(1,1,15,'Using super powers'),(1,1,16,'Consult a specialist'),(1,1,17,'Of course not'),(1,1,20,'YES in capital letters'),(1,1,21,'They last 30 minutes less'),(1,1,26,'0044'),(1,1,36,'Cemitas'),(2,1,5,'Because it was the only name they came up with'),(2,1,14,'YES in capital letters'),(2,1,15,'I do not know'),(2,1,22,'Maybe'),(2,1,23,'Of course not'),(2,1,24,'I do not know'),(2,1,30,'1919'),(2,1,32,'C, a, t'),(2,1,39,'This is not the answer'),(2,1,40,'Markus Persson'),(3,1,1,'Maybe they count wolves'),(3,1,6,'Oh, of course not'),(3,1,16,'Run away from there or they will blame you'),(3,1,19,'It was stolen and no one knows where it is'),(3,1,23,'I do not know'),(3,1,26,'None because the question is grammatically incorrect'),(3,1,38,'In 2021'),(3,1,42,'Jupiter'),(3,1,44,'This is not the answer'),(3,1,45,'01:00'),(4,3,3,'Because we leave the beans on the stove'),(4,3,5,'Because it was the only name they came up with'),(4,3,15,'Using super powers'),(4,3,19,'Yes'),(4,3,22,'I do not know'),(4,3,25,'I do not know'),(4,3,34,'Richard Nixon'),(4,3,35,'Richard'),(4,3,39,'No'),(4,3,46,'I am sure he is not'),(5,1,2,'Because it is just a spelling rule'),(5,1,5,'Because missions are impossible, but everything is possible in life'),(5,1,8,'Because he was more brilliant and knew how to relate more to people'),(5,1,18,'Not even God Knows'),(5,1,22,'I do not know'),(5,1,24,'Not even God knows'),(5,1,29,'Less than a minute'),(5,1,31,'Werevertumorro'),(5,1,46,'I am sure he is not'),(5,1,50,'0'),(6,1,6,'0'),(6,1,14,'0'),(6,1,17,'0'),(6,1,21,'0'),(6,1,28,'0'),(6,1,40,'0'),(6,1,43,'0'),(6,1,45,'0'),(6,1,46,'0'),(6,1,50,'0');
/*!40000 ALTER TABLE `response` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `idu` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `password` varchar(15) NOT NULL,
  `type` tinyint(1) NOT NULL,
  PRIMARY KEY (`idu`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'LeninFranco','P0L1m4s7er',0),(2,'Admin','admin1234',1),(3,'Marquitos','P0L1m4s7er',0),(4,'JuanitoPerez','P0L1m4s7er',0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-06-11 10:25:07
