using System;
using System.Collections.Generic;
using System.Text;

namespace VRK.Controls
{
	public class CloudItemClickEventArgs : EventArgs
	{
		internal CloudItemClickEventArgs(CloudItem item)
		{
			this._item = item;
		}

		private CloudItem _item;

		/// <summary>
		/// Gets the item which is clicked.
		/// </summary>
		public CloudItem Item
		{
			get { return _item; }
		}
	}
}
