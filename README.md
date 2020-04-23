# SmartHomeJava
<h2>Install / Configure Apache</h2>
<h3>Linux</h3>
<ol>
  <li>Do <code>sudo apt-get install apache2 php</code> to install apache2 and php</li>
  <li>Go into <code>/etc/apache2/</code>: <code>cd /etc/apache2/</code></li>
  <li>Edit the file <code>apache2.conf</code>: <code>sudo nano apache2.conf</code></li>
  <ul>
    <li>Copy <br>
             &lt;Directory /var/www&gt;<br>
             Here is also some config <br>
             &lt;/Directory&gt<br>
             and paste it below. Change <code>/var/www</code> to <code>/Path/to/this/project/website</code></li>
  </ul>
  <ul>
    <li>Delete <code>Indexes</code> in the line <code>Options Indexes FollowSymLink</code> from the pasted part</li>
  </ul>
  <li> Go into <code>sites-enabled/</code>: <code>cd sites-enabled</code>
  <li> Edit the file <code>000-default.conf</code>: <code>sudo nano 000-default.conf</code>
  <ul>
    <li> Put a <code>#</code> before the line <code>DocumentRoot /var/www/html</code></li>
    <li> Write the new line <code>DocumentRoot /Path/to/this/project/website/website</code></li>
  </ul>
  <li>Go into your browser and go to <a href="http://localhost/">http://localhost/</a> or to the IP addresss from your linux device (get it with the cmd <code>ifconfig</code>)
</ol>

<h2>Install / Work with maven</h2>
<h3>Linux</h3>
<ol>
  <li>Install maven with <code>sudo apt-get install maven</code></li>
  <li>To compile the project go into the folder <code>code/java/</code> and type <code>mvn compile</code>. After that cmd         finished type<br> the cmd <code>mvn package</code></li>
  <li>Move the .jar file without <code>original</code> at the beginning to <code>code/compiledJava/</code> and execute it with <code>
      java -jar Filename.jar</code>
</ol>
  
