<?php
include 'conn.php';
$response=array();

if(isset($_FILES["dp"]["tmp_name"]))
{
	$tmp_file=$_FILES["dp"]["tmp_name"];
	$img_name=$_FILES["dp"]["name"];
	$upload_dir="images/".time().$img_name;

	$query="INSERT INTO `picture`( `dp`) VALUES ('$upload_dir')";
	$up=move_uploaded_file($tmp_file,$upload_dir);
	if($up){

		$res=mysqli_query($con,$query);
		if($res)
		{
			$response['msg']="Upload Success!";
			$response['url']=$upload_dir;
			$response['code']="1";
		}
		else{
			$response['msg']="Error Inserting!";
			$response['code']="0";
		}
	}
	else{
		$response['msg']="Could not upload!";
		$response['code']="0";
	}
}
else{
	$response['msg']="Incomplete Request!";
	$response['code']="0";
}

echo json_encode($response);

?>