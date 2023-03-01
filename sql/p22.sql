-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 01-03-2023 a las 17:31:02
-- Versión del servidor: 10.1.38-MariaDB
-- Versión de PHP: 5.6.40

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `p22`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `mail` varchar(255) COLLATE utf8_bin NOT NULL,
  `role` bit(1) NOT NULL,
  `lang` varchar(50) COLLATE utf8_bin NOT NULL,
  `name` varchar(50) COLLATE utf8_bin NOT NULL,
  `pass` varchar(255) COLLATE utf8_bin NOT NULL,
  `phone` varchar(50) COLLATE utf8_bin NOT NULL,
  `province` varchar(10) COLLATE utf8_bin NOT NULL,
  `surnames` varchar(255) COLLATE utf8_bin NOT NULL,
  `town` varchar(10) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Volcado de datos para la tabla `users`
--

INSERT INTO `users` (`id`, `mail`, `role`, `lang`, `name`, `pass`, `phone`, `province`, `surnames`, `town`) VALUES
(1, 'admin@mail.com', b'1', 'es', 'Admin', 'TpBOWO9Z9l0vw3b6ICMt2ha9vz/OoLp40gDxRs/LSPCWzEeWYehRhXQhInrjuHgt', '000000000', '01', 'The Admin', '01019'),
(2, 'adam@mail.com', b'0', 'en', 'Adam', 'ZoglyH60ViSdTsKMBvF8iZ1uOlYMSKuJ+wv90W8VrsMihjNmaKcfJ6eS5paIU3ph', '666554433', '42', 'Alda Almirante', '42093'),
(3, 'betty@mail.com', b'0', 'es', 'Betty', '+RkUhOfPBRrtVhw2ENAkxj+7gmCI/K2bQEa/nwHTm88605SnbwNs1xXijIEno0qS', '555443322', '42', 'Bueno Baños', '42162'),
(4, 'charlie@mail.com', b'0', 'es', 'Charlie', '4KiTMULo5Oqj6Ysz4U1Z2Xw5QMmzinZrL/60cH9N2PB5sHkm/TVV9T/GWN0bgJnz', '444332211', '42', 'Corral Casar', '42162'),
(5, 'cecil@mail.com', b'0', 'es', 'Cecil', '+5DAZC0gLmFuWMPf/1Fyyay8IktYj8x5gfUOvmX19ukNsIc0MLsATwcjfobiClDK', '420332811', '42', 'Cinta Coso', '42162'),
(6, 'diane@mail.com', b'0', 'en', 'Diane', 'Y6op3BH0iagkccymeVTibelnHmJBRU03m0FSQtgVAC7belL1bUJwPW/kBrqKEoCb', '333221100', '42', 'Dueñas Donoso', '42162'),
(7, 'eric@mail.com', b'0', 'es', 'Eric', '6+3SMSF0VZ5I1OcBaEjpaBih05Z/KarvUPbIe1Ugcy+xzO2FXzW4dJArBBvxepkr', '111223344', '42', 'Estepa España', '42162');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_jhck7kjdogc7yia7qamc89ypv` (`mail`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
