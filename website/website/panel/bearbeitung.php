<?php
  $darkmode = true;
  include("../phpclasses/sockets.php");


  /*
   * setVolume#int
   * louder#int
   * quieter#int
   *
   *
   */


  $cmd = $_GET["cmd"];


  $client = new ClientSocket("localhost", 1904);
  if($cmd){

    $cmd = $cmd."#".$_GET["data"];

    $type = $_GET["type"];
    if($type == "send"){
      $client->sendData($cmd);
    }elseif ($type == "request") {
      $client->requestData($cmd);
    }

    $from = $_GET["from"];


    if($from == "sonos"){
      header("Location: sonos/");
    }else {
      header("Location: .");
    }


}else {
  header("Location: ../");
}
