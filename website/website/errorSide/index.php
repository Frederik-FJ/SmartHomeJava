<?php

if(empty($_GET["error"])){
  header("Location: ../");
}
$darkmode = true;
$error = $_GET["error"];
?>

<!DOCTYPE html>
<html lang="de" dir="ltr">
  <head>
    <meta charset="utf-8">
    <title>Error</title>
    <?php
      if($darkmode){
        echo "<link rel=\"stylesheet\" href=\"../css/dark.css\">";
      }else {
        echo "<link rel=\"stylesheet\" href=\"../css/bright.css\">";
      }

      if($error == "test"){
        $errorName = "Test";
        $errorDescribtion = "This was just a test";
      }else {
        $errorName = "Test";
        $errorDescribtion = "This was just a test";
      }
    ?>

    <link rel="stylesheet" href="../css/stylesheet.css">
  </head>
  <body>
    <h1>There is an Error</h1>

    <table>
      <tr>
        <th>Error</th>
        <th>Describtion</th>
        <th>ErrorID</th>
      </tr>
      <tr>
        <td><?php echo $errorName; ?></td>
        <td><?php echo $errorDescribtion; ?></td>
        <td><?php echo $error; ?></td>
      </tr>
      <p>$error</p>

    </table>


  </body>
</html>
