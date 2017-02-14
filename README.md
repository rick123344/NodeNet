
# **Tic Tac Toe**

1. Cloud :  [Heroku](https://devcenter.heroku.com/)
2. Server : [Nodejs](https://nodejs.org/en/about/)
3. Client : Java
4. Java Kits : Gottox SocketIo, Gson, JSONObject...

## Result Jar Path 

    => java_tic_tac_toe_new/jars/ticc.jar
    Notice: gson.jar and socketio.jar Must In The Same Directory with ticc.jar

## Java source code Path
    => java_tic_tac_toe_new/*.java
## Nodejs server code Path
    => nodejs/controller/socket.io_control.js

### You can also test in your localhost (two cmd)

*open one cmd
```sh
$ cd path/to/nodejs
$ npm install
$ node index.js
```
you must see a info write  => `IO WebSocket On 5001`

*open another cmd
```sh
$ cd path/to/java_tic_tac_toe_new/
$ java Tic_Tac_Toe.Main 
```
then you should see the result

#### Noted:
    This is Not a good design structure...
    Because Logics write on client side
    A few connection is tested...but not many connection test