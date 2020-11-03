<?php
 
//getting user values
$username=$_POST['username'];
$email=$_POST['email'];
$password=$_POST['password'];
 
//array of responses
$output=array();
 
//require database
require_once('db.php');
 
//checking if email exists
$conn=$dbh->prepare('SELECT email FROM users WHERE email=?');
$conn->bindParam(1,$email);
$conn->execute();
 
//results
if($conn->rowCount() !==0){
$output['isSuccess'] = 0;
$output['message'] = "Email Exists Please Login";
}else{
 
$conn=$dbh->prepare('INSERT INTO users(username,email,password) VALUES (?,?,?)');
//encrypting the password
$pass=$password;
$conn->bindParam(1,$username);
$conn->bindParam(2,$email);
$conn->bindParam(3,$pass);
 
$conn->execute();
if($conn->rowCount() == 0)
{
$output['isSuccess'] = 0;
$output['message'] = "Registration failed, Please try again";
}
elseif($conn->rowCount() !==0){
$output['isSuccess'] = 1;
$output['message'] = "Succefully Registered";
$output['username']=$username;
}
}
echo json_encode($output);
 
?&gt;
