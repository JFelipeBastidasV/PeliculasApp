package bastidas.felipe.peliculasapp.modelos

class PeliculaRepositorio {

    fun getPeliculas(): List<Pelicula>{
        return listOf(
            Pelicula(
                1,
                "Gigantes de Acero",
                "Un luchador en apuros devenido promotor se reencuentra con su hijo para convertir a un robot de la vieja generación en un poderoso contrincante de box.",
                "Ciencia Ficcion",
                2011,
                "2h 7min"
            ),
            Pelicula(
                2,
                "Five Nights at Freddy's",
                "La película sigue a un guardia de seguridad cuando comienza a trabajar en Freddy Fazbear's Pizza. Mientras pasa su primera noche en el trabajo, se da cuenta de que el turno de noche en Freddy's no será tan fácil de superar.",
                "Horror",
                2023,
                "1h 50min"
            ),
            Pelicula(
                3,
                "Five Nights at Freddy's 2",
                "Un año después de la pesadilla supernatural en la Pizzería de Freddy Fazbear, comienza un nuevo episodio de terror cuando Abby, la hermana del ex guardia de seguridad Mike, se escapa para buscar a sus amigos animatrónicos: Freddy, Bonnie, Chica y Foxy. Mientras ocurren sucesos aterradores, salen a la luz secretos sobre el verdadero origen de Freddy's, desatando un horror olvidado durante décadas.",
                "Horror",
                2025,
                "1h 47min"
            ),
            Pelicula(
                4,
                "Chainsaw Man la película: Arco de Reze",
                "Por primera vez, Chainsaw Man llega a la gran pantalla en una épica aventura cargada de acción que continúa la exitosa serie de anime.\n" +
                        "\n" +
                        "Denji trabajaba como Cazador de Demonios para los yakuza, intentando saldar la deuda que heredó de sus padres, hasta que fue traicionado y asesinado por ellos. Al borde de la muerte, su querido perro-demonio con motosierra, Pochita, hizo un pacto con él y le salvó la vida. Esto los fusionó, dando origen al imparable Chainsaw Man.\n" +
                        "\n" +
                        "Ahora, en medio de una guerra brutal entre demonios, cazadores y enemigos ocultos, una misteriosa chica llamada Reze entra en su vida, y Denji se enfrenta a la batalla más peligrosa hasta ahora, impulsado por el amor en un mundo donde la supervivencia no tiene reglas.",
                "Accion",
                2025,
                "1h 41min"
            ),
            Pelicula(
                5,
                "One Piece Film: Red",
                "uffy y su tripulación viven una nueva aventura cuando la misteriosa superestrella pop Uta revela su identidad… y pone en marcha un plan para lograr la paz ...",
                "Accion",
                2022,
                "1h 55min"
            ),

        )
    }
}