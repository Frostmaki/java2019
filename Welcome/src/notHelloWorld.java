import java.awt.*;
import javax.swing.*;

import org.omg.CORBA.PUBLIC_MEMBER;

public class notHelloWorld {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//�¼����ɽ��̣��߳̽�������Ͱ�������ת�Ƶ��û��ӿ����
		EventQueue.invokeLater(()->
				{
					JFrame frame = new notHelloWorld().new NotHelloWorldFrame();
					frame.setTitle("Not Hello world");
					//�����û��ر�ʱ�˳����򣬾��󲿷�Ӧ���û��ر�ֻ������Ӧ��
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setVisible(true);
				});
	}
	
	//���㴰�ڣ��������ô�С�����⣬ͼ��ȵ�
	class NotHelloWorldFrame extends JFrame
	{
		public NotHelloWorldFrame() 
		{
			//Toolkit����������뱾�ش���ϵͳ�򽻵��ķ���
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
			//����������Ϊǡ������������Ĵ�С
			//pack();
		}
	}
	
	//������Ļ�ͼ
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