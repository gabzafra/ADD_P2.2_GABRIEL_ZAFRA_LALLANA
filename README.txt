repo https://github.com/gabzafra/ADD_P2.2_GABRIEL_ZAFRA_LALLANA

Usuarios precargados

Administrador:
"admin@mail.com","admin"

Usuarios normales:
"adam@mail.com", "aaaa",
"betty@mail.com","bbbb",
"charlie@mail.com","cccc"
"cecil@mail.com","cece"
"diane@mail.com","dddd"
"eric@mail.com", "eeee"

He configurado Hibernate mediante anotaciones en la clase User y creado una implementación
de UsersDAO para poder elegir la persistencia en tres sabores jdbc, memoria e hibernate.

Al modelo user le he añadido un par de campos más para mantener los ids de provincia
y municipio, por lo que he retocado tanto UserService como los Controladores para
que puedan manejarlos.

Para obtener los datos desde el JSON remoto he añadido un GeoService que se encarga de
pedir las listas de lugares o los lugares por su id. Para mover esos datos he añadido
un objeto GeoItem para que actúe como "Record"

En las vistas he implementado unos selectores para que el usuario elija la provincia y
el municipio. Estos se manejan mediante JavaScript en el cliente, obteniendo los
datos para poblar las listas de un controlador Geo.java que recibe sus peticiones GET
y les devuelve los JSON con los datos para la actualización en cliente.

