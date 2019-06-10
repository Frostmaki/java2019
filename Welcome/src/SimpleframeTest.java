import java.awt.*;
import javax.swing.*;

public class SimpleframeTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(()->
		{
			//SimpleFrame frame = new SimpleframeTest().new SimpleFrame();
			//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			//frame.setVisible(true);
			
			JFrame frame = new SimpleframeTest().new SizedFrame();
			frame.setTitle("SizedFrame");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
		});
	}
	
	class SimpleFrame extends JFrame
	{
		private static final int DEFAULT_WIDTH = 300;
		private static final int DEFAULT_HEIGHT = 200;
		
		public SimpleFrame()
		{
			setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
		}
	}
	
	class SizedFrame extends JFrame
	{
		public SizedFrame()
		{
			Toolkit kit = Toolkit.getDefaultToolkit();
			Dimension screenSize = kit.getScreenSize();
			int screenHeight = screenSize.height;
			int screenWidth = screenSize.width;
			
			setSize(screenWidth/2,screenHeight/2);
			setLocationByPlatform(true);
			
			Image img = new ImageIcon("MG_1300.JPG").getImage();
			setIconImage(img);
		}
		
		
	}

}
