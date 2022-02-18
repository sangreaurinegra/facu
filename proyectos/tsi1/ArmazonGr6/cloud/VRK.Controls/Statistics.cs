using System;
using System.Collections.Generic;

namespace VRK
{
	/// <summary>
	/// Statistical functions
	/// </summary>
	public static class Statistics
	{
		public static double Mean(IEnumerable<double> values)
		{
			double sum = 0;
			int count = 0;

			foreach (double d in values)
			{
				sum += d;
				count++;
			}

			return sum / count;
		}
		
		public static double StdDev(IEnumerable<double> values, out double mean)
		{
			mean = Statistics.Mean(values);
			double sumOfDiffSquares = 0;
			int count = 0;

			foreach (double d in values)
			{
				double diff = (d - mean);
				sumOfDiffSquares += diff * diff;
				count++;
			}

			return Math.Sqrt(sumOfDiffSquares / count);
		}

		public static double StdDev(IEnumerable<double> values)
		{
			double mean;
			return StdDev(values, out mean);
		}	
	}
}