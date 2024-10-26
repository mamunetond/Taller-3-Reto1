# Taller 3 - Reto 1: Login Book Store
Prueba automatizada en Java utilizando REST Assured y aplicando los principios FIRST

# Flujo completo Reto 1

# Página: https://demoqa.com/swagger/#/

# Paso 1:

![image](https://github.com/user-attachments/assets/80a29018-15d2-4a8e-9ff6-a09289d691c8)

Ir a la API /Account/v1/Authorized y ver la estructura del json, en este caso se tiene dos atributos UserName y Password, luego revisar los responses en este caso tenemos los siguientes:

### 200  -> Success y retorna un true
### 400  -> Error y retorna un código que es 0 y un mensaje de tipo string
### 404  -> Error y retorna un código que es 0 y un mensaje de tipo string


# Paso 2:

![image](https://github.com/user-attachments/assets/7377f650-e68d-4e34-9b79-c46f400521a6)

Ir a la API /Account/v1/GenerateToken y ver la estructura del json, en este caso se tiene dos atributos UserName y password, luego revisar los responses en este caso tenemos los siguientes:

### 200  -> Success y retorna los siguientes atributos 

{"token": "string",
  "expires": "2024-10-25T16:15:24.854Z",
  "status": "string",
  "result": "string"}
  
### 400  -> Error y retorna un código que es 0 y un mensaje de tipo string

# Paso 3:

![image](https://github.com/user-attachments/assets/91b043ec-8ee2-4ef5-9301-08087ba62d32)

Ir a la API /Account/v1/User y ver la estructura del json, en este caso se tiene dos atributos UserName y password, luego revisar los responses en este caso tenemos los siguientes:

### 201  -> Success y retorna los siguientes atributos
{
  "userId": "string",
  "username": "string",
  "books": [
    {
      "isbn": "string",
      "title": "string",
      "subTitle": "string",
      "author": "string",
      "publish_date": "2024-10-25T16:22:36.405Z",
      "publisher": "string",
      "pages": 0,
      "description": "string",
      "website": "string"
    }
  ]
}
### 404  -> Error y retorna un código que es 0 y un mensaje de tipo string
### 406  -> Error y retorna un código que es 0 y un mensaje de tipo string

# Paso 4:

![image](https://github.com/user-attachments/assets/8e79c4d1-c6c6-4af5-ad6d-24cf7001f552)

Ir al botón Try out e ingresar unos datos de prueba en este caso puse

{
  "userName": "mamunetond",
  "password": "Marioalejandro4*"
}

Luego le damos en el botón de ejecutar

# Paso 5:

![image](https://github.com/user-attachments/assets/5517ba41-6da7-4db5-be50-4ac5b034c40a)

Ver el Response de nuestra prueba, en este caso nos retorna un 201 es decir que el usuario fue creado exitosamente y tiene un "UserID" : "96282d51-d762-406e-925f-866c6645d1aa"

# Paso 6:

![image](https://github.com/user-attachments/assets/1c2b15d5-6bce-4ab5-a495-be6e4c975f38)

Ir a la API /Account/v1/User/{UUID}, y ver los parametros de esta API, el cual nos pide un UserId y también podemos ver sus responses:

### 200  -> Success y retorna un código 0 y un mensaje de tipo sring
### 204  -> Unauthorized y retorna un userId de tipo string y message de tipo string
### 401  -> Error y retorna un código que es 0 y un mensaje de tipo string

# Paso 7:

![image](https://github.com/user-attachments/assets/18fd5c0b-61ca-4736-9c63-324fd60b8053)

Le damos al botón Try it out e ingresamos el UserId que queramos eliminar este caso voy a eliminar el usuario con UserId: 96282d51-d762-406e-925f-866c6645d1aa y le doy en el botón ejecutar

# Paso 8:

![image](https://github.com/user-attachments/assets/44f57242-732e-4da1-b88d-f5f324643781)

Luego veo el response que me retorna

# Paso 9:

![image](https://github.com/user-attachments/assets/43fa82df-0da9-4ece-9b55-b357d0cb973d)

Ir a la página: https://demoqa.com/login ingresar el nombre de usuario y contraseña, esto nos redirige a esta página dónde podemos realizar las siguientes acciones:

1) Ir a la Book Store
2) Eliminar nuestro usuario
3) Eliminar todos los libros
4) Cerrar sesión

# Paso 10:

![image](https://github.com/user-attachments/assets/2de4d136-29c5-4637-9579-b360bb056768)

Le damos al botón de eliminar usuario y le damos en confirmar y nos aparece el siguiente mensaje: User Deleted

# Paso 11:

![image](https://github.com/user-attachments/assets/1fb781a0-3006-40c8-ae26-b915819c8262)

Si vuelvo a ingresar el usuario y la contraseña, nos arroja el siguiente mensaje "invalid username or password"


#Assertions

Esta prueba tiene dos assertions los cuales son:

## Assertion 1: Validación correcta de la creación del usuario

Se valida que se ingrese un usuario admitido y una contraseña que cumpla con los requisitos de longitud mínima de ocho caracteres, debe de incluir un número, una letra en mayúscula y un caracter especial

## Assertion 2: Mensaje "invalid username or password!"

Se valida que cuando se ingrese el usuario y la contraseña, aparezca el mensaje invalid username or password! esto quiere decir que el usuario no se enceuntra registrado, y de esta manera se termina la prueba.


# Principios FIRST

## Fast (Rápido):

El código debe ejecutarse rápidamente para que las pruebas se puedan realizar frecuentemente. Sin embargo, en tu código se están realizando solicitudes HTTP reales, lo que puede hacer que las pruebas sean más lentas. Para cumplir este principio, sería mejor usar mocks en lugar de realizar solicitudes HTTP reales en todas las pruebas. En tu caso, ya intentaste introducir un mock para la generación de token, pero debes aplicarlo en las demás llamadas HTTP también.

## Independent (Independiente):

Las pruebas no deben depender unas de otras. En tu código, la prueba de creación de usuario y la prueba de eliminación dependen de que el usuario sea creado correctamente en la prueba anterior, lo que rompe este principio. Para cumplir con este principio, debes asegurarte de que cada prueba sea capaz de ejecutarse por sí sola, sin importar los resultados de las otras pruebas.

## Repeatable (Repetible):

Las pruebas deben producir los mismos resultados cada vez que se ejecutan. Las pruebas que dependen de servicios externos o de un estado mutable (como la creación y eliminación de un usuario real) pueden fallar si el servicio externo cambia. Usar mocks en lugar de llamadas reales garantizaría que las pruebas sean repetibles y siempre se comporten de la misma manera.

## Self-validating (Auto-validables):

Las pruebas deben validar automáticamente los resultados sin intervención humana. En tu caso, las pruebas parecen cumplir con este principio ya que usan aserciones como assertEquals para validar los resultados esperados.

## Timely (Oportuno):

Las pruebas deben escribirse en el momento adecuado, idealmente antes o durante el desarrollo del código que se está probando. No puedo juzgar directamente cuándo se escribieron las pruebas, pero el hecho de que ya estén integradas en el código es un buen indicio.


## ¿Cómo se ven los principios FIRST reflejados en la prueba automatizada?

Usaa Mockito y MockMVC para simular las respuestas de los servicios externos, simulando las respuestas de la creación de usuario, inicio de sesión y eliminación, en lugar de realizar llamadas reales a las API. Esto mejorará la velocidad y permitirá que las pruebas sean independientes.










































