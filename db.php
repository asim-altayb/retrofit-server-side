<?php
 
try{
//remember to change larntech with your database name
 
$host="mysql:host=localhost;dbname=larntech";
$user_name="root";
$user_password="";
$dbh=new PDO($host,$user_name,$user_password);
}
 
catch(Exception $e){
exit("Connection Error".$e--->getMessage());
}
 
?>
