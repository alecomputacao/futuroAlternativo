-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: futurodb
-- ------------------------------------------------------
-- Server version	5.7.12-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `aluno`
--

DROP TABLE IF EXISTS `aluno`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `aluno` (
  `matricula` varchar(255) NOT NULL,
  `tipocotaingresso` int(11) NOT NULL,
  `dataingresso` date NOT NULL,
  `dataegresso` date DEFAULT NULL,
  `Turma_id` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` varchar(255) NOT NULL,
  `Pessoa_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Aluno_Turma1_idx` (`Turma_id`),
  KEY `fk_Aluno_Pessoa1_idx` (`id`),
  KEY `fk_Aluno_Pessoa1` (`Pessoa_id`),
  CONSTRAINT `fk_Aluno_Pessoa1` FOREIGN KEY (`Pessoa_id`) REFERENCES `pessoa` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Aluno_Turma1` FOREIGN KEY (`Turma_id`) REFERENCES `turma` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `disciplina`
--

DROP TABLE IF EXISTS `disciplina`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `disciplina` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  `cargahoraria` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `documento`
--

DROP TABLE IF EXISTS `documento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `documento` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tipo` varchar(255) NOT NULL,
  `numero` varchar(255) NOT NULL,
  `dataexpedicao` date NOT NULL,
  `datavalidade` date DEFAULT NULL,
  `orgaoemissor` varchar(255) NOT NULL,
  `uf` varchar(255) NOT NULL,
  `Pessoa_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Documento_Pessoa_idx` (`Pessoa_id`),
  CONSTRAINT `fk_Documento_Pessoa` FOREIGN KEY (`Pessoa_id`) REFERENCES `pessoa` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `endereco`
--

DROP TABLE IF EXISTS `endereco`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `endereco` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tipo` varchar(255) NOT NULL,
  `logradouro` varchar(255) NOT NULL,
  `numero` int(11) NOT NULL,
  `bairro` varchar(255) NOT NULL,
  `cidade` varchar(255) NOT NULL,
  `uf` varchar(255) NOT NULL,
  `cep` varchar(255) NOT NULL,
  `enderecocorrespondencia` varchar(255) NOT NULL,
  `telefone` varchar(255) NOT NULL,
  `Pessoa_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Endereco_Pessoa1_idx` (`Pessoa_id`),
  CONSTRAINT `fk_Endereco_Pessoa1` FOREIGN KEY (`Pessoa_id`) REFERENCES `pessoa` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `evento`
--

DROP TABLE IF EXISTS `evento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `evento` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `datahoraentrada` datetime NOT NULL,
  `datahorasaida` datetime DEFAULT NULL,
  `Pessoa_id` int(11) NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Evento_Pessoa1_idx` (`Pessoa_id`),
  CONSTRAINT `fk_Evento_Pessoa1` FOREIGN KEY (`Pessoa_id`) REFERENCES `pessoa` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pessoa`
--

DROP TABLE IF EXISTS `pessoa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pessoa` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  `datanasc` date NOT NULL,
  `sexo` varchar(255) NOT NULL,
  `naturalidade` varchar(255) NOT NULL,
  `uf` varchar(255) NOT NULL,
  `nomepai` varchar(255) NOT NULL,
  `nomemae` varchar(255) NOT NULL,
  `responsavellegal` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `numerocelular` varchar(255) NOT NULL,
  `necessidadesespeciais` varchar(255) NOT NULL,
  `etnia` varchar(255) NOT NULL,
  `nacionalidade` varchar(255) NOT NULL,
  `estadocivil` varchar(255) NOT NULL,
  `tipopessoa` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `plastico`
--

DROP TABLE IF EXISTS `plastico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `plastico` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `linhadigitavel` varchar(255) NOT NULL,
  `datacadastro` date NOT NULL,
  `status` varchar(255) NOT NULL,
  `Pessoa_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Plastico_Pessoa1_idx` (`Pessoa_id`),
  CONSTRAINT `fk_Plastico_Pessoa1` FOREIGN KEY (`Pessoa_id`) REFERENCES `pessoa` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `professor`
--

DROP TABLE IF EXISTS `professor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `professor` (
  `nivelformacao` varchar(255) NOT NULL,
  `formacao` varchar(255) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Pessoa_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Professor_Pessoa1_idx` (`id`),
  KEY `fk_Professor_Pessoa1` (`Pessoa_id`),
  CONSTRAINT `fk_Professor_Pessoa1` FOREIGN KEY (`Pessoa_id`) REFERENCES `pessoa` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `professor_has_disciplina`
--

DROP TABLE IF EXISTS `professor_has_disciplina`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `professor_has_disciplina` (
  `Professor_id` int(11) NOT NULL,
  `Disciplina_id` int(11) NOT NULL,
  PRIMARY KEY (`Professor_id`,`Disciplina_id`),
  KEY `fk_Professor_has_Disciplina_Disciplina1_idx` (`Disciplina_id`),
  KEY `fk_Professor_has_Disciplina_Professor1_idx` (`Professor_id`),
  CONSTRAINT `fk_Professor_has_Disciplina_Disciplina1` FOREIGN KEY (`Disciplina_id`) REFERENCES `disciplina` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Professor_has_Disciplina_Professor1` FOREIGN KEY (`Professor_id`) REFERENCES `professor` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `turma`
--

DROP TABLE IF EXISTS `turma`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `turma` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(255) NOT NULL,
  `Disciplina_id` int(11) NOT NULL,
  `Professor_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Turma_Disciplina1_idx` (`Disciplina_id`),
  KEY `fk_Turma_Professor1_idx` (`Professor_id`),
  CONSTRAINT `fk_Turma_Disciplina1` FOREIGN KEY (`Disciplina_id`) REFERENCES `disciplina` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Turma_Professor1` FOREIGN KEY (`Professor_id`) REFERENCES `professor` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping routines for database 'futurodb'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-06-03 19:29:24
