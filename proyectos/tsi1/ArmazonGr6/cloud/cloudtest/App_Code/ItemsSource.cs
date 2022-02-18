using System;
using System.Data;
using System.Configuration;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;

namespace CloudTest
{
	public class Item
	{
		public Item(string name, int weight)
		{
			this._name = name;
			this._weight = weight;
		}

		private string _name;

		public string Name
		{
			get { return _name; }
		}

		private int _weight;

		public int Weight
		{
			get { return _weight; }
		}
	}

	/// <summary>
	/// ItemsSource supplies the items
	/// </summary>
	public class ItemsSource
	{
		public ItemsSource()
		{
		}

		public Item[] GetItems()
		{
			//Data taken from Ajaxian.com
			return new Item[]{
			new Item(".NET ",30) ,
			new Item("Accessibility ",26) ,
			new Item("Ajax ",218) ,
			new Item("Articles ",93) ,
			new Item("Book Reviews ",3) ,
			new Item("Books ",8) ,
			new Item("Browsers ",30) ,
			new Item("Builds ",3) ,
			new Item("Email ",2) ,
			new Item("Examples ",88) ,
			new Item("Firefox ",18) ,
			new Item("Flash ",20) ,
			new Item("Framework ",2) ,
			new Item("Fun ",11) ,
			new Item("Games ",13) ,
			new Item("Google ",33) ,
			new Item("HTML ",4) ,
			new Item("IE ",27) ,
			new Item("Interview ",6) ,
			new Item("Java ",69) ,
			new Item("Poronga ",230) ,
			new Item("Library ",113) ,
			new Item("LiveSearch ",12) ,
			new Item("Mapping ",16) ,
			new Item("Mobile ",7) ,
			new Item("Office ",5) ,
			new Item("Perl ",6) ,
			new Item("PHP ",40) ,
			new Item("Podcast ",17) ,
			new Item("Portal ",8) ,
			new Item("Presentation ",14) ,
			new Item("Programming ",101) ,
			new Item("Prototype ",65) ,
			new Item("Remoting ",12) ,
			new Item("Ruby ",37) ,
			new Item("Screencast ",15) ,
			new Item("Security ",12) ,
			new Item("Server ",7) ,
			new Item("Showcase ",263) ,
			new Item("Testing ",9) ,
			new Item("Tip ",13) ,
			new Item("Toolkit ",122) ,
			new Item("UI ",36) ,
			new Item("Usability ",41) ,
			new Item("Utility ",48) ,
			new Item("Web20 ",14) ,
			new Item("XmlHttpRequest ",28) ,
		};

		}
	}
}