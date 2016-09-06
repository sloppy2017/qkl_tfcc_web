<?php
	 $phones = array(13126567539,12121237397,13121897669);
 	 $phone = $_POST['phone'];
 	if(in_array($phone,$phones)){
 		echo "1";
 	}else{
 		echo "0";
 	}

 ?>