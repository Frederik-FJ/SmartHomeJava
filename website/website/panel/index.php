<?php
  $darkmode = true;

?>

<!DOCTYPE html>
<html lang="de">
  <head>
    <meta charset="utf-8">

    <!--Entscheidet zwischen Darkmode und normalem mode-->
    <?php
      if($darkmode){
        echo "<link rel=\"stylesheet\" href=\"../css/dark.css\">";
      }else {
        echo "<link rel=\"stylesheet\" href=\"../css/bright.css\">";
      }
     ?>
    <link rel="stylesheet" href="../css/stylesheet.css">

    <title>Befehlspannel SmartHome</title>
  </head>
  <body>


      <!---->
      <ul class="nav">
        <li><a class="activ nav" href=".">Start</a></li>
        <li><a class="notActiv nav" href="sonos">Sonos</a></li>
        <li><a class="notActiv nav" href="fritzbox">Fritz!Box</a></li>
      </ul>


    <div class="divInhalt">
      <h1>Panel</h1>




    <p class="mittig">Dies ist die Startseite vom Panel</p>











    <?php

     ?>
     </div>
  </body>
</html>
