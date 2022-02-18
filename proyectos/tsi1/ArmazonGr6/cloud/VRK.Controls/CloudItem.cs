using System;
using System.Collections;
using System.Collections.Generic;
using System.Text;

namespace VRK.Controls
{
    public class CloudItem : IEnumerable<CloudItem>
	{
		public CloudItem()
		{
		}

		public CloudItem(string text, double weight)
		{
			this._text = text;
			this._weight = weight;
		}

		public CloudItem(string text, double weight, string href)
			: this(text, weight)
		{
			this._href = href;
		}

		public CloudItem(string text, double weight, string href, string title)
			: this(text, weight, href)
		{
			this._title = title;
		}

		private string _text;

		/// <summary>
		/// Gets or sets the text for individual hyperlinks.
		/// </summary>
		public string Text
		{
			get
			{
				return _text;
			}
			set
			{
				_text = value;
			}
		}

		private string _href;

		/// <summary>
		/// Get or sets the address of the HTML anchor.
		/// </summary>
		public string Href
		{
			get
			{
				return _href;
			}
			set
			{
				_href = value;
			}
		}

		private string _title;

		/// <summary>
		/// Gets or sets the title (tooltip) of the HTML anchor.
		/// </summary>
		public string Title
		{
			get
			{
				return _title;
			}
			set { _title = value; }
		}

		private double _weight;

		/// <summary>
		/// Gets or sets the weight of the item.
		/// </summary>
		public double Weight
		{
			get { return _weight; }
			set { _weight = value; }
		}

		#region Serailization Support
		private bool ShouldSerializeWeight()
		{
			return _weight != 0;
		}

		private bool ShouldSerializeTitle()
		{
			return !String.IsNullOrEmpty(_title);
		}

		private bool ShouldSerializeText()
		{
			return !String.IsNullOrEmpty(_text);
		}

		private bool ShouldSerializeHref()
		{
			return !String.IsNullOrEmpty(_href);
		}
		#endregion

        public IEnumerator<CloudItem> GetEnumerator()
        {
            throw new System.NotImplementedException();
        }

        IEnumerator IEnumerable.GetEnumerator()
        {
            return GetEnumerator();
        }
	}
}
