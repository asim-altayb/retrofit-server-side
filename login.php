
<?php
 
//getting user values
$username=$_POST['username'];
$password=$_POST['password'];
 
//an array of response
$output = array();
 
//requires database connection
require_once('db.php');
 
//checking if email exit
$conn=$dbh->prepare("SELECT * FROM users WHERE username=? and password=?");
$pass=$password;
$conn->bindParam(1,$username);
$conn->bindParam(2,$pass);
$conn->execute();
if($conn->rowCount() == 0){
$output['isSuccess'] = 0;
$output['message'] = "Wrong username or Password";
}
 
//get the username
if($conn->rowCount() !==0){
$results=$conn->fetch(PDO::FETCH_OBJ);
//we get both the username and password
$username=$results->username;
 
 
$output['isSuccess'] = 1;
$output['message'] = "login sucessful";
$output['username'] = $username;
 
}
echo json_encode($output);
 
?>
