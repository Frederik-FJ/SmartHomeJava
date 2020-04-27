<?php
  $darkmode = true;


?>


<!DOCTYPE html>
<html lang="de" dir="ltr">
  <head>
    <meta charset="utf-8">
    <!--Entscheidet zwischen Darkmode und normalem mode-->
    <?php
      if($darkmode){
        echo "<link rel=\"stylesheet\" href=\"../../css/dark.css\">";
      }else {
        echo "<link rel=\"stylesheet\" href=\"../../css/bright.css\">";
      }
     ?>
    <link rel="stylesheet" href="../../css/stylesheet.css">
    <title></title>
  </head>
  <body>

    <ul class="nav">
      <li><a class="notActiv nav" href="../">Start</a></li>
      <li><a class="activ nav" href="sonos">Sonos</a></li>
      <li><a class="notActiv nav" href="../fritzbox">Fritz!Box</a></li>
    </ul>


    <div class="divInhalt">
      <h1>Sonos</h1>
      <h2>Volume</h2>



      <!--Volume setzen-->
      <form action="../bearbeitung.php" method="get">
        <input class="notVisible" type="text" name="cmd" value="setVolume">
        <input class="notVisible" type="text" name="from" value="sonos">
        <input class="notVisible" type="text" name="type" value="send">
        <table>
          <td>Volume:</td>
          <td><input type="number" name="data"><br></td>
          <td><input type="submit" name="" value="Volume setzen"></td>
        </table>
      </form><br><br>

      <!-- -->
      <form action="../bearbeitung.php" method="get">
        <input class="notVisible" type="text" name="cmd" value="louder">
        <input class="notVisible" type="text" name="from" value="sonos">
        <input class="notVisible" type="text" name="type" value="send">
        <input class="notVisible" type="number" name="data" value="5">
        <table>
          <td><input type="submit" value="Lauter um 5"></td>
        </table>
      </form><br><br>


      <form action="../bearbeitung.php" method="get">
        <input class="notVisible" type="text" name="cmd" value="quieter">
        <input class="notVisible" type="text" name="from" value="sonos">
        <input class="notVisible" type="text" name="type" value="send">
        <input class="notVisible" type="number" name="data" value="5">
        <table>
          <td><input type="submit" name="" value="Leiser um 5"></td>
        </table>
      </form>

    </div>

  </body>
</html>
