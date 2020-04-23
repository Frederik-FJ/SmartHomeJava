<?php
if(true){
  header('Location: http://frederik-janssen.de');
}
$darkmode = false;
?>
<!DOCTYPE html>
<html lang="de">
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

    <title>reportBug SmartHome</title>
  </head>
  <body>
    <h1>Dieser Dienst funktioniert noch nicht</h1>

  </body>
</html>
