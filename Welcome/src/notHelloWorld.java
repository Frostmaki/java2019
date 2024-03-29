import java.awt.*;
import javax.swing.*;

import org.omg.CORBA.PUBLIC_MEMBER;

public class notHelloWorld {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//事件分派进程，线程将鼠标点击和按键控制转移到用户接口组件
		EventQueue.invokeLater(()->
				{
					JFrame frame = new notHelloWorld().new NotHelloWorldFrame();
					frame.setTitle("Not Hello world");
					//设置用户关闭时退出程序，绝大部分应用用户关闭只是隐藏应用
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setVisible(true);
				});
	}
	
	//顶层窗口，可以设置大小，标题，图标等等
	class NotHelloWorldFrame extends JFrame
	{
		public NotHelloWorldFrame() 
		{
			//Toolkit类包含许多与本地窗口系统打交道的方法
			//
			Toolkit kit = Toolkit.getDefaultToolkit();
			Dimension screenSize = kit.getScreenSize();
			int screenHeight = screenSize.height;
			int screenWidth = screenSize.width;
			
			setSize(screenWidth/2,screenHeight/2);
			setLocationByPlatform(true);
			
			Image img = new ImageIcon("MG_1300.JPG").getImage();
			setIconImage(img);
			
			add(new NotHelloWorldComponent());
			//将窗口设置为恰好能容纳组件的大小
			//pack();
		}
	}
	
	//窗口里的绘图
	class NotHelloWorldComponent extends JComponent
	{
		public static final int DEFAULT_WIDTH=300;
		public static final int DEFAULT_HEIGHT=200;
		
		public static final int MESSAGE_X=75;
		public static final int MESSAGE_Y=100;
		
		public void paintComponent(Graphics g)
		{
			g.drawString("not a hello,world program", MESSAGE_X, MESSAGE_Y);
			
		}
		
		public Dimension getPreferredSize()
		{
			return new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT);
		}
	}

}
