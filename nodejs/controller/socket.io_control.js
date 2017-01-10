//var users = require('../model/user_model');
//var async = require("async");
//var $ = require("jquery")(require("jsdom").jsdom().parentWindow);
var socket_io=require('socket.io');
module.exports= function(server,port){
	var io = socket_io.listen(server);
	io.set('log level',0);
	var allClient=[];
	var clientName=[];
	io.sockets.on('connection',function(socket){
		socket.emit('event',{'data':"hello client"});
		socket.GuessName="";
		var socketid=socket.id;
		var address = socket.handshake.address;
		var ip=address.address+":"+address.port;
		//console.log("New Connect "+ip);
		//When Message Come
		socket.on("NewGuess",function(data){
			var random = Math.floor((Math.random() * 10000000) + 1);
			var guess = "Guess"+random;
			socket.GuessName=guess;
			allClient.push(socket);
			clientName.push(guess);
			console.log("NewGuess : "+socket.GuessName);
			//emit only sender
			socket.emit('NewGuess',{'data':socket.GuessName});
			//broadcast.emit all socket except sender 
			socket.broadcast.emit('resetBar',{'data':JSON.stringify(clientName)});
			socket.emit('resetBar',{'data':JSON.stringify(clientName)});
		});
		socket.on('ReFresh',function(data){
			socket.broadcast.emit('resetBar',{'data':JSON.stringify(clientName)});
			socket.emit('resetBar',{'data':JSON.stringify(clientName)});
		})
		socket.on("MSG",function(data){
			socket.broadcast.emit('EachMessage',{'data':data});
			socket.emit('EachMessage',{'data':data});
		});
		//When Left
		socket.on('disconnect',function(){
			socket.broadcast.emit('leftPlayer',{'data':socket.GuessName+" Is Left..."});
			resetArray(socket);
		});
		//When Error
		socket.on('error',function(err){
			console.log("ERR "+err);
			resetArray(socket);
		});
	});
	
	function resetArray(socket){
		var i = allClient.indexOf(socket);
		var ii = clientName.indexOf(socket.GuessName);
		allClient.splice(i,1);
		clientName.splice(ii,1);
		socket.broadcast.emit('resetBar',{'data':JSON.stringify(clientName)});
		socket.emit('resetBar',{'data':JSON.stringify(clientName)});
	}
	
	console.log("IO WebSocket On "+port);
}