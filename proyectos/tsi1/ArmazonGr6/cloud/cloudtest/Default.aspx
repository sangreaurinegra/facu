<%@ Page Language="C#" AutoEventWireup="true"  CodeFile="Default.aspx.cs" Inherits="_Default" %>
<%@ Register Namespace="VRK.Controls" TagPrefix="vrk" Assembly="VRK.Controls" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<head runat="server">
    <title>Cloud Test</title>
    <style type="text/css">
    body
    {
		font-family: tahoma;
    }
    #c1
    {
		text-align: justify;
    }
	#c1 a, #c1 a:visited
	{
		color: blue;
		text-decoration: none;
	}
        #form1
        {
            height: 349px;
            width: 196px;
        }
    </style>
</head>
<body>

   <div style = "float: right; height: 350px; width: 200px;">
    <form id="form1" runat="server">
		 <asp:ObjectDataSource ID="ItemsSource" runat="server" SelectMethod="GetItems" TypeName="CloudTest.ItemsSource" />
		
		<vrk:Cloud ID="c1" runat="server" DataSourceID="ItemsSource" 
             DataTextField="Name" DataTitleField="Weight" 
             DataTitleFormatString="{0} artilces on the subject" DataWeightField="Weight" 
             Height="19px" >
		<Items>
			<vrk:CloudItem Text="Item1" 
							Href="Default.aspx?tag=Item1" 
							Title="Test" Weight="4" />
			<vrk:CloudItem Text="Item2" 
							Href="Default.aspx?tag=Item2" 
							Title="Test" Weight="4" />
		</Items>
		</vrk:Cloud>
    </form>
    </div>
</body>
</html>

