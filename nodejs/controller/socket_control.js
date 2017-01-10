//base on net module , net different with socket.io
//net base on server , only server side can use 傳輸層
//net create base on a new port, but socket.io base on a exist server
//var users = require('../model/user_model');
//var tic = require('../model/tic_tac_toe_model');
var async = require("async");
//var $ = require("jquery")(window);//require("jsdom").jsdom().parentWindow
/*require("jsdom").env("", function(err, window) {
    if (err) {
        console.error(err);
        return;
    }
    var $ = require("jquery")(window);
});*/
var net = require('net');
module.exports= function(port){
	var clients = [];
	//socket server
	var server = net.createServer(function(socket){
		socket.name = socket.remoteAddress + ":" + socket.remotePort;
		console.log(socket.name);
		clients.push(socket);
		//console.log(clients);
		//write data 
		socket.on("data",function(data){
			//console.log(data.toString());
			//socket.write("Hello "+data.toString()+" From nodejs \n");
			broadcast(data.toString(),socket,"");
			//socket.write("Hello From nodejs ");
			//socket.end("Hello "+data.toString()+" From nodejs ");
		});
		//when a client left
		socket.on("end",function(){
			clients.splice(clients.indexOf(socket), 1);
			console.log("disconnect");
		});
		//unexpected left error
		socket.on("error",function(err){
			console.log(err.toString());
			console.log("ERROR"+socket.remoteAddress+" "+socket.remotePort);
		});
		socket.on("close",function(data){
			console.log("Left "+socket.name);
		});
	});
	server.listen(port,function(){
		console.log("server on "+port);
	});

	function broadcast(message,sender,receiver){
		var clean = [];
		
		clients.forEach(function(client){
			if(receiver == ""){
				//if(client != sender){
					if(client.writable){
						// \n is needed , readLine() in java need \n to break, or it will not stop
						msg = processJson(message,"other");
						client.write(msg+"\n");
					}else{
						clean.push(client);
						client.destroy();
						
					}
				//}else{
					msg = processJson(message,"self");
					client.write(msg+"\n");
				//}
			}else{
				if(receiver == client.remoteAddress){
					msg = processJson(message,"self");
					client.write(msg+"\n");
				}
			}
		});
		
		for(i=0;i<clean.length;i+=1){
			clients.splice(clients.indexOf(clean[i]), 1);
		}
	}
	function processJson(message,target){
		var json = message;
		try{
			json = JSON.parse(json);
			/*if(json.RequestType == "Change Turn"){
				if(target == "self"){
					json.myturn = false;
				}else{
					json.myturn = true;
				}
			}*/
			json = JSON.stringify(json);
			return json;
		}catch(err){
			var er={
				"ERR":err,
			};
			return JSON.stringify(er);
		}
		
	}
}
