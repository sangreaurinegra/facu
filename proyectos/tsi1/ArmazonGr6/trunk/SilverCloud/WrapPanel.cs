using System;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Media.Animation;

namespace SilverCloud
{
    public class WrapPanel : Panel
    {
        public WrapPanel()
        {
            this.Orientation = Orientation.Horizontal;
        }

        public Orientation Orientation
        {
            get { return (Orientation)GetValue(OrientationProperty); }
            set { SetValue(OrientationProperty, value); }
        }

        public static readonly DependencyProperty OrientationProperty =
            DependencyProperty.Register("Orientation", typeof(Orientation), typeof(WrapPanel), null);

        protected override Size MeasureOverride(Size availableSize)
        {
            Size myAvailableSize = new Size(availableSize.Width, availableSize.Height);

            foreach (UIElement child in Children)
            {
                child.Measure(myAvailableSize);
            }

            return base.MeasureOverride(availableSize);
        }

        protected override Size ArrangeOverride(Size finalSize)
        {
            Point startPoint = new Point(0, 0);
            double largestHeight = 0.0;

            foreach (UIElement child in Children)
            {
                if (child.Visibility == Visibility.Visible)
                {
                    if (startPoint.X + child.DesiredSize.Width > finalSize.Width)
                    {
                        startPoint.X = 0;
                        startPoint.Y += largestHeight;
                        largestHeight = 0;
                    }

                    largestHeight = Math.Max(largestHeight, child.DesiredSize.Height);

                    child.Arrange(new Rect(startPoint, new Point(startPoint.X + child.DesiredSize.Width, startPoint.Y + child.DesiredSize.Height)));
                    //child.Arrange(new Rect(new Point(0,0), new Point(startPoint.X + child.DesiredSize.Width, startPoint.Y + child.DesiredSize.Height)));

                    // TODO: Seems to be very inefficient
                    ////instead of arranging the child, create an animation that moves the child to that position.
                    //Storyboard sb = new Storyboard();
                    //Storyboard.SetTarget(sb, child);
                    //DoubleAnimation xanim = new DoubleAnimation();
                    //Storyboard.SetTargetProperty(xanim, "(Canvas.Left)");
                    //xanim.To = startPoint.X;
                    //xanim.Duration = new Duration(TimeSpan.FromMilliseconds(500));
                    //DoubleAnimation yanim = new DoubleAnimation();
                    //Storyboard.SetTargetProperty(yanim, "(Canvas.Top)");
                    //yanim.To = startPoint.Y;
                    //yanim.Duration = new Duration(TimeSpan.FromMilliseconds(500));
                    //sb.Children.Add(xanim);
                    //sb.Children.Add(yanim);
                    //sb.Completed += new EventHandler(sb_Completed);

                    //this.Resources.Add(sb);
                    //sb.Begin();


                    startPoint.X += child.DesiredSize.Width;
                }
            }

            if (this.Height != startPoint.Y + largestHeight)
            {
                this.SetValue(HeightProperty, startPoint.Y + largestHeight);
            }

            return base.ArrangeOverride(new Size(finalSize.Width, startPoint.Y + largestHeight));

        }

        void sb_Completed(object sender, EventArgs e)
        {
            //this.Resources.Remove((Storyboard)sender);
        }
    }
}