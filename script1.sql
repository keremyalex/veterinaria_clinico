-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               PostgreSQL 15.14 (Debian 15.14-1.pgdg13+1) on x86_64-pc-linux-gnu, compiled by gcc (Debian 14.2.0-19) 14.2.0, 64-bit
-- Server OS:                    
-- HeidiSQL Version:             12.10.0.7000
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES  */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- Dumping structure for table public.bloque_horario
CREATE TABLE IF NOT EXISTS "bloque_horario" (
	"id" INTEGER NOT NULL,
	"activo" INTEGER NOT NULL,
	"diasemana" INTEGER NOT NULL,
	"horafinal" TIME NOT NULL,
	"horainicio" TIME NOT NULL,
	"doctor_id" INTEGER NOT NULL,
	PRIMARY KEY ("id"),
	CONSTRAINT "fkoi5vufal2bhtmkuqodvaob7ao" FOREIGN KEY ("doctor_id") REFERENCES "doctor" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Data exporting was unselected.

-- Dumping structure for table public.carnet_vacunacion
CREATE TABLE IF NOT EXISTS "carnet_vacunacion" (
	"id" INTEGER NOT NULL,
	"fechaemision" TIMESTAMP NOT NULL,
	"mascota_id" INTEGER NOT NULL,
	PRIMARY KEY ("id"),
	UNIQUE ("mascota_id"),
	CONSTRAINT "fkeeimi09uy2xfdmgspt20ahsm0" FOREIGN KEY ("mascota_id") REFERENCES "mascota" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Data exporting was unselected.

-- Dumping structure for table public.cita
CREATE TABLE IF NOT EXISTS "cita" (
	"id" INTEGER NOT NULL,
	"estado" INTEGER NOT NULL,
	"fechacreacion" TIMESTAMP NOT NULL,
	"fechareserva" TIMESTAMP NOT NULL,
	"motivo" VARCHAR(255) NOT NULL,
	"doctor_id" INTEGER NOT NULL,
	"mascota_id" INTEGER NOT NULL,
	PRIMARY KEY ("id"),
	CONSTRAINT "fkcgwfh1aixsj39j5a092qns4kx" FOREIGN KEY ("doctor_id") REFERENCES "doctor" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "fkjjr9rbirfalfxoq1rndrc8sqq" FOREIGN KEY ("mascota_id") REFERENCES "mascota" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Data exporting was unselected.

-- Dumping structure for table public.cliente
CREATE TABLE IF NOT EXISTS "cliente" (
	"id" INTEGER NOT NULL,
	"apellido" VARCHAR(255) NOT NULL,
	"ci" VARCHAR(255) NOT NULL,
	"fotourl" VARCHAR(255) NULL DEFAULT NULL,
	"nombre" VARCHAR(255) NOT NULL,
	"telefono" VARCHAR(255) NOT NULL,
	PRIMARY KEY ("id"),
	UNIQUE ("ci")
);

-- Data exporting was unselected.

-- Dumping structure for table public.detalle_vacunacion
CREATE TABLE IF NOT EXISTS "detalle_vacunacion" (
	"id" INTEGER NOT NULL,
	"fechavacunacion" DATE NOT NULL,
	"proximavacunacion" DATE NULL DEFAULT NULL,
	"carnet_vacunacion_id" INTEGER NOT NULL,
	"vacuna_id" INTEGER NOT NULL,
	PRIMARY KEY ("id"),
	CONSTRAINT "fk4juq31ps083ypfx56f3thxqj1" FOREIGN KEY ("carnet_vacunacion_id") REFERENCES "carnet_vacunacion" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "fkfidu078vq67q4auhc5cwv31dr" FOREIGN KEY ("vacuna_id") REFERENCES "vacuna" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Data exporting was unselected.

-- Dumping structure for table public.diagnostico
CREATE TABLE IF NOT EXISTS "diagnostico" (
	"id" INTEGER NOT NULL,
	"descripcion" VARCHAR(255) NOT NULL,
	"fecharegistro" TIMESTAMP NOT NULL,
	"observaciones" VARCHAR(255) NOT NULL,
	"cita_id" INTEGER NOT NULL,
	PRIMARY KEY ("id"),
	CONSTRAINT "fkfykmhdrsbp2x5r8svigpwmkxj" FOREIGN KEY ("cita_id") REFERENCES "cita" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Data exporting was unselected.

-- Dumping structure for table public.doctor
CREATE TABLE IF NOT EXISTS "doctor" (
	"id" INTEGER NOT NULL,
	"apellido" VARCHAR(255) NOT NULL,
	"ci" VARCHAR(255) NOT NULL,
	"email" VARCHAR(255) NOT NULL,
	"fotourl" VARCHAR(255) NULL DEFAULT NULL,
	"nombre" VARCHAR(255) NOT NULL,
	"telefono" VARCHAR(255) NOT NULL,
	PRIMARY KEY ("id"),
	UNIQUE ("ci")
);

-- Data exporting was unselected.

-- Dumping structure for table public.especie
CREATE TABLE IF NOT EXISTS "especie" (
	"id" INTEGER NOT NULL,
	"descripcion" VARCHAR(255) NOT NULL,
	PRIMARY KEY ("id")
);

-- Data exporting was unselected.

-- Dumping structure for table public.mascota
CREATE TABLE IF NOT EXISTS "mascota" (
	"id" INTEGER NOT NULL,
	"fechanacimiento" DATE NOT NULL,
	"fotourl" VARCHAR(255) NULL DEFAULT NULL,
	"nombre" VARCHAR(255) NOT NULL,
	"raza" VARCHAR(255) NOT NULL,
	"sexo" CHAR(1) NOT NULL,
	"cliente_id" INTEGER NOT NULL,
	"especie_id" INTEGER NOT NULL,
	PRIMARY KEY ("id"),
	CONSTRAINT "fk43ghhb7i1g4240ndbx7m7a6ji" FOREIGN KEY ("cliente_id") REFERENCES "cliente" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "fkdms4k4djfrpme91ctrx85cel4" FOREIGN KEY ("especie_id") REFERENCES "especie" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Data exporting was unselected.

-- Dumping structure for table public.tratamiento
CREATE TABLE IF NOT EXISTS "tratamiento" (
	"id" INTEGER NOT NULL,
	"descripcion" VARCHAR(255) NOT NULL,
	"nombre" VARCHAR(255) NOT NULL,
	"observaciones" VARCHAR(255) NOT NULL,
	"diagnostico_id" INTEGER NOT NULL,
	PRIMARY KEY ("id"),
	CONSTRAINT "fkir5qc0t4gn97dnf08ix7rxn9y" FOREIGN KEY ("diagnostico_id") REFERENCES "diagnostico" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Data exporting was unselected.

-- Dumping structure for table public.vacuna
CREATE TABLE IF NOT EXISTS "vacuna" (
	"id" INTEGER NOT NULL,
	"descripcion" VARCHAR(255) NOT NULL,
	PRIMARY KEY ("id")
);

-- Data exporting was unselected.

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
