$(document).ready(function() 
{  
		$("#alertSuccess").hide();  
	    $("#alertError").hide(); 
}); 
 
 
// SAVE ============================================ 
$(document).on("click", "#btnSave", function(event) 
{  
	// Clear alerts---------------------  
	$("#alertSuccess").text("");  
	$("#alertSuccess").hide();  
	$("#alertError").text("");  
	$("#alertError").hide(); 
 
	// Form validation-------------------  
	var status = validateUserForm();  
	if (status != true)  
	{   
		$("#alertError").text(status);   
		$("#alertError").show();   
		return;  
	} 
 
	// If valid------------------------  
	var type = ($("#hidUserIDSave").val() == "") ? "POST" : "PUT"; 

	$.ajax( 
	{  
			url : "UserService",   
			type : type,  
			data : $("#formUser").serialize(),  
			dataType : "text",  
			complete : function(response, status)  
			{   
				onUserSaveComplete(response.responseText, status);  
			} 
	}); 
}); 


function onUserSaveComplete(response, status) 
{  
	if (status == "success")  
	{   
		var resultSet = JSON.parse(response); 

		if (resultSet.status.trim() == "success")   
		{    
			$("#alertSuccess").text("Successfully saved.");    
			$("#alertSuccess").show(); 

			$("#divUserGrid").html(resultSet.data);   
		} else if (resultSet.status.trim() == "error")   
		{    
			$("#alertError").text(resultSet.data);    
			$("#alertError").show();   
		} 

	} else if (status == "error")  
	{   
		$("#alertError").text("Error while saving.");   
		$("#alertError").show();  
	} else  
	{   
		$("#alertError").text("Unknown error while saving..");   
		$("#alertError").show();  
	} 

	$("#hidUserIDSave").val("");  
	$("#formUser")[0].reset(); 
} 

 
// UPDATE========================================== 
$(document).on("click", ".btnUpdate", function(event) 
{     
	$("#hidUserIDSave").val($(this).closest("tr").find('#hidUserIDUpdate').val());     
	$("#FirstName").val($(this).closest("tr").find('td:eq(0)').text());     
	$("#LastName").val($(this).closest("tr").find('td:eq(1)').text()); 
	$("#Address").val($(this).closest("tr").find('td:eq(2)').text());
	$("#AccountNo").val($(this).closest("tr").find('td:eq(3)').text());   
	$("#ContactNo").val($(this).closest("tr").find('td:eq(4)').text()); 
	$("#Email").val($(this).closest("tr").find('td:eq(3)').text());   

}); 




//REMOVE===========================================
$(document).on("click", ".btnRemove", function(event) 
{  
	$.ajax(  
	{   
		url : "UserService",   
		type : "DELETE",   
		data : "UserID=" + $(this).data("userid"),   
		dataType : "text",   
		complete : function(response, status)   
		{    
			onUserDeleteComplete(response.responseText, status);   
		}  
	}); 
}); 

function onUserDeleteComplete(response, status) 
{  
	if (status == "success")  
	{   
		var resultSet = JSON.parse(response); 

		if (resultSet.status.trim() == "success")   
		{    
			
			$("#alertSuccess").text("Successfully deleted.");    
			$("#alertSuccess").show(); 
		
			$("#divUserGrid").html(resultSet.data); 
			
		} else if (resultSet.status.trim() == "error")   
		{    
			$("#alertError").text(resultSet.data);    
			$("#alertError").show();   
		}
		

	} else if (status == "error")  
	{   
		$("#alertError").text("Error while deleting.");   
		$("#alertError").show();  
	} else  
	{   
		$("#alertError").text("Unknown error while deleting..");   
		$("#alertError").show();  
	}
}
 
// CLIENT-MODEL========================================================================= 
function validateUserForm() 
{  
	// NAME-----------------------
	if ($("#FirstName").val().trim() == "")  
	{   
		return "Insert FirstName.";  
	} 
	
	// LastName---------------------------  
	if ($("#LastName").val().trim() == "")  
	{   
		return "Insert LastName.";  
	}
	
	// Address------------------------------
	if ($("#Address").val().trim() == "")  
	{   
		return "Insert Address.";  
	}
	
	// AccountNo-------------------------------
	if ($("#AccountNo").val().trim() == "")  
	{   
		return "Insert AccountNo.";  
	}
	
	// ContactNo---------------------------  
	 var tmpMobile = $("#ContactNo").val().trim();
		if (!$.isNumeric(tmpMobile)) 
		{
		return "Insert ContactNo.";
		}
	// Email-----------------------
	if ($("#Email").val().trim() == "")  
	{   
		return "Insert Email.";  
	} 
		
	return true; 
}