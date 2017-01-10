
Tic Tac Toe

##Cloud  : Heroku
##Server : Nodejs
##Client : Java
##Java Kits : Gottox SocketIo, Gson, JSONObject...

##Result In java_tic_tac_toe_new/jars/ticc.jar
##Notice: gson.jar and socketio.jar Must In The Same Dictionary with ticc.jar

##java source code in java_tic_tac_toe_new/(Main.java,board.java,config.java,MenuBar.java,socke.java)
##nodejs server code in nodejs/controller/socket.io_control.js

###This is Not a good design structure...
###Because Logics write on client side
###A few connection is tested...but not many connection test

#########################

You can also test in your localhost

1. open cmd
2. cd path/to/nodejs/
3. command line enter: $node index.js
4. you must see a info write => IO WebSocket On 5001
5. open new cmd and cd path/to/java_tic_tac_toe_new/
6. command line enter: $java Tic_Tac_Toe.Main
7. Done...

