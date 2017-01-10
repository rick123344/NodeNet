var express = require('express');
var app = express();

//var socket_server = require('./controller/socket_control')(app.get('port'));

app.set('port', (process.env.PORT || 5001));
app.use(express.static(__dirname + '/public'));

// views is directory for all template files
app.set('views', __dirname + '/views');
app.set('view engine', 'ejs');
app.use(function(req, res, next) {					//access ajax 
	res.header("Access-Control-Allow-Origin", "*");//*         "http://"+req.headers.host
	res.header("Access-Control-Allow-Headers", "X-Requested-With");
	res.header("Access-Control-Allow-Headers", "Content-Type");
	res.header("Access-Control-Allow-Methods", "PUT, GET, POST, DELETE, OPTIONS");
	next();
});
app.get('/', function(request, response) {
	response.render('pages/index');
});
var server=require('http').createServer(app);
server.listen(app.get('port'), function() {
	require('./controller/socket.io_control')(server,app.get('port'));
	console.log('Node app is running on port', app.get('port'));
});


//say you won't let go
//water under the bridge
//this town