<?php



class ClientSocket{

  var $host;
  var $port;


  function __construct($host, $port){
    $this->host = $host;
    $this->port = $port;

  }


  function sendData($data){
    if(($socket = socket_create(AF_INET, SOCK_STREAM, SOL_TCP)) === FALSE){
      echo "socket_create() failed: reason: ".socket_strerror(socket_last_error());
    }else {
        if ( ($result = socket_connect($socket, $this->host, $this->port)) === FALSE )
            echo "socket_connect() failed. Reason: ($result) " .     socket_strerror(socket_last_error($socket));
        else {
            echo "Sending data...<br>";
            socket_write($socket, $data."\r\n", strlen($data."\r\n"));
            echo "OK<br>";

        }
        socket_close($socket);
    }
  }

  function requestData($data){
    if(($socket = socket_create(AF_INET, SOCK_STREAM, SOL_TCP)) === FALSE){
      echo "socket_create() failed: reason: ".socket_strerror(socket_last_error());
    }else {
        if ( ($result = socket_connect($socket, $this->host, $this->port)) === FALSE )
            echo "socket_connect() failed. Reason: ($result) " .     socket_strerror(socket_last_error($socket));
        else {
            echo "Sending data...<br>";
            socket_write($socket, $data."\r\n", strlen($data."\r\n"));
            echo "OK<br>";

            $out = socket_read($socket, 2048);
            echo $out;

        }
        socket_close($socket);
    }
  }

}

/*
if(empty($_POST["funktion"])){
  header ("Location: ../errorSide/?error=test");
}else {
  echo "test";
}

$host = '192.168.180.22';
$port = 1904;
$data = 'test';

if(($socket = socket_create(AF_INET, SOCK_STREAM, SOL_TCP)) === FALSE){
  echo "socket_create() failed: reason: ".socket_strerror(socket_last_error());
}else {
    if ( ($result = socket_connect($socket, $host, $port)) === FALSE )
        echo "socket_connect() failed. Reason: ($result) " .     socket_strerror(socket_last_error($socket));
    else {
        echo "Sending data...<br>";
        socket_write($socket, $data."\r\n", strlen($data."\r\n"));
        echo "OK<br>";

    }
    socket_close($socket);
}
 */
?>
