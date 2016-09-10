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

-- --------------------------------------------
-- removendo chave estrangeira disciplina
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
ALTER TABLE turma
DROP FOREIGN KEY fk_Turma_Disciplina1;
/*!40101 SET character_set_client = @saved_cs_client */;

-- removendo columna disciplina_id
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
ALTER TABLE turma
DROP COLUMN Disciplina_id;
/*!40101 SET character_set_client = @saved_cs_client */;
-- --------------------------------------------

-- --------------------------------------------
-- removendo chave estrangeira professor
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
ALTER TABLE turma
DROP FOREIGN KEY fk_Turma_Professor1;
/*!40101 SET character_set_client = @saved_cs_client */;

-- removendo columna professor_id
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
ALTER TABLE turma
DROP COLUMN Professor_id;
/*!40101 SET character_set_client = @saved_cs_client */;


-- ----------------------------------------------------------
-- create table tipo infracao
DROP TABLE IF EXISTS `tipo_infracao`;
-- /*!40101 SET @saved_cs_client     = @@character_set_client */;
-- /*!40101 SET character_set_client = utf8 */;
-- CREATE TABLE `tipo_infracao` (
--  `id` int(11) NOT NULL AUTO_INCREMENT,
--  `tipo` varchar(100) NOT NULL,
--  PRIMARY KEY (`id`))
-- ENGINE = InnoDB DEFAULT CHARSET=utf8;
-- /*!40101 SET character_set_client = @saved_cs_client */;

-- -------------------------------------------------------
-- create table infracao
DROP TABLE IF EXISTS `infracao`;
-- /*!40101 SET @saved_cs_client     = @@character_set_client */;
-- /*!40101 SET character_set_client = utf8 */;
-- CREATE TABLE `infracao` (
-- `id` int(11) NOT NULL AUTO_INCREMENT,
--  `descricao` varchar(255) NOT NULL,
--  `observacao` varchar(255) NULL,
--  `TipoInfracao_id` int(11) NOT NULL,
--  `Aluno_id` int(11) NOT NULL,
--  PRIMARY KEY (`id`),
--  INDEX `fk_Infracao_TipoInfracao_idx` (`TipoInfracao_id` ASC),
--  INDEX `fk_Infracao_Aluno1_idx` (`Aluno_id` ASC),
--  CONSTRAINT `fk_Infracao_TipoInfracao1`
--    FOREIGN KEY (`TipoInfracao_id`)
--    REFERENCES `tipo_infracao` (`id`)
--    ON DELETE NO ACTION
--    ON UPDATE NO ACTION,
--  CONSTRAINT `fk_Infracao_Aluno1`
--    FOREIGN KEY (`Aluno_id`)
--    REFERENCES `aluno` (`id`)
--    ON DELETE NO ACTION
--    ON UPDATE NO ACTION)
-- ENGINE = InnoDB DEFAULT CHARSET=utf8;
-- /*!40101 SET character_set_client = @saved_cs_client */;


-- -------------------------
-- tabela usuário
-- -------------------------
DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nomeusuario` varchar(100) NOT NULL,
  `senha` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
