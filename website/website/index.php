<!--Cookies, Session etc-->
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
        echo "<link rel=\"stylesheet\" href=\"/css/dark.css\">";
      }else {
        echo "<link rel=\"stylesheet\" href=\"/css/bright.css\">";
      }
      ?>
    <link rel="stylesheet" href="/css/stylesheet.css">

    <title>Selbstentwickeltes SmartHome-Programm</title>
  </head>
  <body>
    <h1>Selbstentwickeltes SmartHome-Programm</h1>
    <p> Dieses Programm wurde entwickelt, damit man SmartHome auch in Java
      programmieren kann. Es können zukünftig noch andere Sprachen hinzukommen
      (am wahrscheinlichsten Python). Dabei unterstütz diese Programm bisher diese Dienste:</p>
    <ul>
      <li>FritzBox</li>
      <li>Sonos</li>
    </ul>
    <p>In naher Zukunft werden aber noch mehr Dienste unterstützt.</p>
    <p><a href="panel/">Hier</a> geht's zum Befehlspannel</p>
    <p>Es hilft sehr, wenn jeder, der einen Bug entdeckt, diesen reportet.<a href="report/reportBug/">Hier reportest du einen Bug</a></p><br/>
    <p>Das Programm nutzt diese Dienste:</p>
    <ul>
      <li><a href="https://pypi.org/project/fritzconnection/" target="_blank">fritzconnection</a>*</li>
      <li><a href="https://pypi.org/project/soco/" target="_blank">SoCo<a>*</li>
      <li><a href="https://httpd.apache.org/" target="_blank">Apache 2</a>*</li>
      <li><a href="https://mariadb.org/" target="_blank">MariaDB</a>*</li>
      <li><a href="https://maven.apache.org/" target="_blank">Maven</a>*</li>

      <li>Java*</li>
      <li>python 3*</li>
      <li>Php 7.3*</li><br/>
      <li>Eventuell kommt noch Node.js hinzu. Das wird sich noch entscheiden</li>
    </ul>
    <p>Programme mit einem * müssen zuvor installiert werden</p>
    <p>Bisher kann das Programm leider nur auf Linux laufen</p><br/><br/>


    <h3>Inofs zu Java:</h3>
    <p>Die Java Versionen 8 und 11 gehen auf jeden Fall, da ich mit diesen Arbeite.</p>
    <p>Das Programm kompilieren:</p>
    <ul>
      <li>In den Ordner code/java/ gehen</li>
      <li><code>mvn compile</code> und danach <code>mvn package</code> ausführen</li>
      <li>Die Jar-Datei aus dem code/java/target Ordner in den Ordner code/compiledJava legen</li>
      <li>Programm ist jetzt mit <code>java -jar Programmname.jar</code> ausführbar</li>
    </ul><br>
    <p>Javadoc: </p>
    <ul>
      <li><a href="javadoc/v0.0.1/public/">Version 0.0.1</a></li>
      <li><a href="javadoc/v0.0.2/public/">Version 0.0.2</a></li>
    </ul>
  </body>
</html>
