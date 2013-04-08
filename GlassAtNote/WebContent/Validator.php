<?php
$usuario=$_GET[confirmationCode];
//echo "<script type=\"text/javascript\">window.alert(\"".$usuario."\")</script>";

$conexion = mysql_connect("147.96.80.89", "ilsaserver", "platano");

mysql_select_db("atnote", $conexion);

$querryAlter="UPDATE `atnote`.`user_app` SET `ISCONFIRMED`=1 WHERE `confirmationCode`='".$usuario."'";

mysql_query($querryAlter, $conexion) or die(mysql_error());

if (mysql_affected_rows()>0)
	{
		echo "<script type=\"text/javascript\">window.alert(\"Wellcome to @note, your registration was succesfully confirmed\")</script>";
		
		//header ("Location: http://a-note.fdi.ucm.es/");
		
		
		
	}
	else {
		echo "<script type=\"text/javascript\">window.alert(\"Your registration has failed, please chack your email instructions \")</script>";	
		
	}
	
	echo "<html>
	
<head>
	
<meta http-equiv=\"Refresh\" content=\"5;url=http://a-note.fdi.ucm.es/\">
	
</head>
	
<body>
	
<p>If you not be redirect in five seconds press this link: 
<a href=\"http://a-note.fdi.ucm.es/\">http://a-note.fdi.ucm.es/</a></p>
	
</body>
	
</html>";

?>
