using System;
using System.Collections.Generic;
using System.Text;
using System.Web.UI.WebControls;
using System.Web.UI;
using System.ComponentModel;
using System.Web.UI.HtmlControls;
using System.Globalization;
using System.Web.UI.Design;
using System.Collections.ObjectModel;
using System.Collections;

namespace VRK.Controls
{
	public class Cloud : CompositeDataBoundControl, IPostBackEventHandler
	{
		private Collection<CloudItem> _items = new Collection<CloudItem>();

		/// <summary>
		/// Collection of CloudItems. <see cref="CloudItem"/> 
		/// </summary>
		[Themeable(false), PersistenceMode(PersistenceMode.InnerProperty), MergableProperty(false)]
		public Collection<CloudItem> Items
		{
			get
			{
				return _items;
			}
		}

		private static int NormalizeWeight(double weight, double mean, double stdDev)
		{
			double factor = (weight - mean);

			if (factor != 0 && stdDev != 0) factor /= stdDev;

			return (factor > 2) ? 7 :
								(factor > 1) ? 6 :
								(factor > 0.5) ? 5 :
								(factor > -0.5) ? 4 :
								(factor > -1) ? 3 :
								(factor > -2) ? 2 :
												  1;
		}

		static string[] _fontSizes = new string[] { "xx-small", "x-small", "small", "medium", "large", "x-large", "xx-large" };

		protected override void Render(HtmlTextWriter writer)
		{
			base.Render(writer);
		}

		protected override HtmlTextWriterTag TagKey
		{
			get
			{
				return HtmlTextWriterTag.Div;
			}
		}

		/// <summary>
		/// Gets or sets the name of the data field that is bound to the Text property of an item.
		/// </summary>
		[Category("Data")]
		[TypeConverter(typeof(DataFieldConverter))]
		public string DataTextField
		{
			get
			{
				string val = ViewState["DataTextField"] as string;

				if (val != null)
				{
					return val;
				}

				return String.Empty;
			}
			set
			{
				ViewState["DataTextField"] = value;

				if (this.Initialized)
				{
					this.RequiresDataBinding = true;
				}
			}
		}


		/// <summary>
		/// Gets or sets the format string for the Text property.
		/// </summary>
		[Category("Data")]
		public string DataTextFormatString
		{
			get
			{
				string val = ViewState["DataTextFormatString"] as string;

				if (val != null)
				{
					return val;
				}

				return String.Empty;
			}
			set
			{
				ViewState["DataTextFormatString"] = value;

				if (this.Initialized)
				{
					this.RequiresDataBinding = true;
				}
			}
		}


		/// <summary>
		/// Gets or sets the data field which is bound to the Href property of an item.
		/// </summary>
		[Category("Data")]
		[TypeConverter(typeof(DataFieldConverter))]
		public string DataHrefField
		{
			get
			{
				string val = ViewState["DataHrefField"] as string;

				if (val != null)
				{
					return val;
				}

				return String.Empty;
			}
			set
			{
				ViewState["DataHrefField"] = value;

				if (this.Initialized)
				{
					this.RequiresDataBinding = true;
				}
			}
		}

		/// <summary>
		/// Gets or sets the format string to format the Href property value.
		/// </summary>
		[Category("Data")]
		public string DataHrefFormatString
		{
			get
			{
				string val = ViewState["DataHrefFormatString"] as string;

				if (val != null)
				{
					return val;
				}

				return String.Empty;
			}
			set
			{
				ViewState["DataHrefFormatString"] = value;

				if (this.Initialized)
				{
					this.RequiresDataBinding = true;
				}
			}
		}

		/// <summary>
		/// Gets or sets the data field which is bound to the Title property of an item.
		/// </summary>
		[Category("Data")]
		[TypeConverter(typeof(DataFieldConverter))]
		public string DataTitleField
		{
			get
			{
				string val = ViewState["DataTitleField"] as string;

				if (val != null)
				{
					return val;
				}

				return String.Empty;
			}
			set
			{
				ViewState["DataTitleField"] = value;

				if (this.Initialized)
				{
					this.RequiresDataBinding = true;
				}
			}
		}

		/// <summary>
		/// The format string for the title(tooltip) of an item. {0} in this string is replaced with the
		/// value of the field specified as the DataTitleField.
		/// </summary>
		[Category("Data")]
		public string DataTitleFormatString
		{
			get
			{
				string val = ViewState["DataTitleFormatString"] as string;

				if (val != null)
				{
					return val;
				}

				return String.Empty;
			}
			set
			{
				ViewState["DataTitleFormatString"] = value;

				if (this.Initialized)
				{
					this.RequiresDataBinding = true;
				}
			}
		}

		/// <summary>
		/// The field from the Data Source where the weight of an item is to be obtained.
		/// </summary>
		[Category("Data")]
		[TypeConverter(typeof(DataFieldConverter))]
		public string DataWeightField
		{
			get
			{
				string val = ViewState["DataWeightField"] as string;

				if (val != null)
				{
					return val;
				}

				return String.Empty;
			}
			set
			{
				ViewState["DataWeightField"] = value;

				if (this.Initialized)
				{
					this.RequiresDataBinding = true;
				}
			}
		}

		/// <summary>
		/// Gets or sets the prefix for CSS class names for individual items.
		/// </summary>
		[Category("Appearance")]
		public string ItemCssClassPrefix
		{
			get
			{
				string val = ViewState["ItemCssClassPrefix"] as string;

				if (val != null)
				{
					return val;
				}

				return String.Empty;
			}
			set
			{
				ViewState["ItemCssClassPrefix"] = value;
			}
		}

		#region Serialization Support
		private bool ShouldSerializeItemCssClassPrefix()
		{
			return !String.IsNullOrEmpty(this.ItemCssClassPrefix);
		}

		private bool ShouldSerializeDataTitleFormatString()
		{
			return !String.IsNullOrEmpty(this.DataTitleFormatString);
		}

		private bool ShouldSerializeDataTextFormatString()
		{
			return !String.IsNullOrEmpty(this.DataTextFormatString);
		}

		private bool ShouldSerializeDataHrefField()
		{
			return !String.IsNullOrEmpty(this.DataHrefField);
		}

		private bool ShouldSerializeDataHrefFormatString()
		{
			return !String.IsNullOrEmpty(this.DataHrefFormatString);
		}

		private bool ShouldSerializeDataTitleField()
		{
			return !String.IsNullOrEmpty(this.DataTitleField);
		}

		private bool ShouldSerializeDataWeightField()
		{
			return !String.IsNullOrEmpty(this.DataWeightField);
		}
		#endregion

		private IEnumerable<double> ItemWeights
		{
			get
			{
				foreach (CloudItem item in this.Items)
				{
					yield return item.Weight;
				}
			}
		}

		protected override int CreateChildControls(System.Collections.IEnumerable dataSource, bool dataBinding)
		{
			if (dataBinding && !this.DesignMode)
				CreateItemsFromData(dataSource);

			double mean; 
			double stdDev = Statistics.StdDev(ItemWeights, out mean);

			bool hasCssClassPrefix = !String.IsNullOrEmpty(ItemCssClassPrefix);
			int index = 0;

			foreach (CloudItem item in Items)
			{
				HtmlAnchor a = new HtmlAnchor();
				a.HRef = String.IsNullOrEmpty(item.Href) ?
									 this.Page.ClientScript.GetPostBackClientHyperlink(this, index.ToString()) :
									 item.Href;
				a.InnerText = item.Text;
				a.Title = item.Title;
				int normalWeight = NormalizeWeight(item.Weight, mean, stdDev);

				if (hasCssClassPrefix)
				{
					a.Attributes["class"] = this.ItemCssClassPrefix + normalWeight.ToString();
				}
				else
				{
					a.Style.Add(HtmlTextWriterStyle.FontSize, _fontSizes[normalWeight - 1]);
				}

				this.Controls.Add(a);
				this.Controls.Add(new LiteralControl("&nbsp;"));
				index++;
			}

			if (this.DesignMode && Items.Count == 0)
			{

				HtmlAnchor a = new HtmlAnchor();
				a.HRef = "javascript:void(0)";
				a.InnerText = SR.Cloud;
				this.Controls.Add(a);
			}

			return Items.Count;
		}

		private void CreateItemsFromData(System.Collections.IEnumerable dataSource)
		{
			foreach (object data in dataSource)
			{
				CloudItem item = new CloudItem();

				if (String.IsNullOrEmpty(this.DataHrefField))
				{
					if (String.IsNullOrEmpty(this.DataHrefFormatString))
						item.Href = String.Empty;
					else
						String.Format(CultureInfo.CurrentCulture, this.DataHrefFormatString, new object[] { data });
				}
				else
				{
					item.Href = DataBinder.Eval(data, this.DataHrefField, this.DataHrefFormatString);
				}

				if (!String.IsNullOrEmpty(this.DataTextField))
				{
					item.Text = DataBinder.Eval(data, this.DataTextField, this.DataTextFormatString);
				}

				if (!String.IsNullOrEmpty(this.DataTitleField))
				{
					item.Title = DataBinder.Eval(data, this.DataTitleField, this.DataTitleFormatString);
				}

				if (!String.IsNullOrEmpty(this.DataWeightField))
				{
					item.Weight = Convert.ToDouble(DataBinder.GetPropertyValue(data, this.DataWeightField));
				}

				this.Items.Add(item);
			}
		}

		public event EventHandler<CloudItemClickEventArgs> ItemClick;

		protected void OnItemClick(CloudItemClickEventArgs e)
		{
			if (ItemClick != null)
				ItemClick(this, e);
		}

		#region IPostBackEventHandler Members

		public void RaisePostBackEvent(string eventArgument)
		{
			int selectedIndex = 0;
			if (Int32.TryParse(eventArgument, out selectedIndex))
			{
				this.RequiresDataBinding = true;
				this.EnsureDataBound();

				if (selectedIndex >= 0 && selectedIndex < this.Items.Count)
					OnItemClick(new CloudItemClickEventArgs(this.Items[selectedIndex]));
			}
		}

		#endregion
	}
}
