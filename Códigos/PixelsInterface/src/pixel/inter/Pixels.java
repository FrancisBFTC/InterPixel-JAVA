package pixel.inter;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.*;
import javax.imageio.ImageIO;

public class Pixels extends JFrame
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String getPath = new File("").getAbsolutePath();
	private JLabel label0, label1, label2;
	private JTextField capturate;
	private JTextField[] paramScreen = new JTextField[4];
	private JButton botao1, botao2;
	
	public Pixels()
	{
		this.setBounds(200, 200, 300, 160);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setTitle("InterPixel");
		
		label0 = new JLabel();
		label0.setText("<html><div style='width:300;height:160;'></div></html>");
		label0.setBounds(0, 0, 300, 160);
		this.add(label0);
		
		label1 = new JLabel("Get Image: ");
		label1.setBounds(10, 10, 100, 20);
		label1.setForeground(Color.BLACK);
		label0.add(label1);
		
		capturate = new JTextField();
		capturate.setBounds(10, 30, 170, 20);
		label0.add(capturate);
		
		botao1 = new JButton("Getting");
		botao1.setBounds(180, 30, 100, 20);
		botao1.setCursor(new Cursor(Cursor.HAND_CURSOR));
		label0.add(botao1);
		
		label2 = new JLabel("Set Image: ");
		label2.setBounds(10, 50, 100, 20);
		label2.setForeground(Color.BLACK);
		label0.add(label2);
		
		for(int i = 0, j = 10; i < 4; i++)
		{
			paramScreen[i] = new JTextField();
			paramScreen[i].setBounds(j, 70, 30, 20);
			label0.add(paramScreen[i]);
			j += 40;
		}
		paramScreen[0].setText("X");
		paramScreen[1].setText("Y");
		paramScreen[2].setText("W");
		paramScreen[3].setText("H");
		
		botao2 = new JButton("Setting");
		botao2.setBounds(180, 70, 100, 20);
		botao2.setCursor(new Cursor(Cursor.HAND_CURSOR));
		label0.add(botao2);
		
		ActionListener action1;
		action1 = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					CreateFile(capturate.getText());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		};
		
		ActionListener action2;
		action2 = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ScreenShot(capturate.getText());
				} catch (AWTException e1) {
					e1.printStackTrace();
				}
			}
		};
		
		botao1.addActionListener(action1);
		botao2.addActionListener(action2);
	}
	
	public void CreateFile(String nameFile) throws IOException
	{
		File file = new File(getPath+"\\"+nameFile);
		int c;
		int quant = 0;
		int number = 0;
		BufferedImage image = ImageIO.read(file);
		PrintWriter writer = new PrintWriter(getPath+"\\"+nameFile.substring(0, nameFile.lastIndexOf('.'))+".hex");
		for(int x=0;x < image.getWidth(); x++)
		{
			for(int y=0;y < image.getHeight(); y++)
			{
				number++;
				c = image.getRGB(x, y);
				String cl = Integer.toHexString(c).substring(2, Integer.toHexString(c).length());
				String code = cl+" ";
				
				if(number == 20){
					writer.println(code);
					number = 0;
				}else{
					writer.print(code);
				}
				
				quant++;
			}
		}
		writer.println();
		writer.println("Foi imprimido '"+quant+"' pixels");
		writer.close();
	}
	
	public void ScreenShot(String getText) throws AWTException
	{
		int a, b, c, d;

		Toolkit Tool = Toolkit.getDefaultToolkit();
		Dimension dimension = Tool.getScreenSize();
		
		
		if(paramScreen[0].getText().equals("X") || paramScreen[0].getText().isEmpty())
		{
			a = 0;
		}else{
			a = Integer.parseInt(paramScreen[0].getText());
		}
		
		if(paramScreen[1].getText().equals("Y") || paramScreen[1].getText().isEmpty())
		{
			b = 0;
		}else{
			b = Integer.parseInt(paramScreen[1].getText());
		}
		
		if(paramScreen[2].getText().equals("W") || paramScreen[2].getText().isEmpty())
		{
			c = dimension.width;
		}else{
			c = Integer.parseInt(paramScreen[2].getText());
		}
		
		if(paramScreen[3].getText().equals("H") || paramScreen[3].getText().isEmpty())
		{
			d = dimension.height;
		}else{
			d = Integer.parseInt(paramScreen[3].getText());
		}
		
		Robot robot;
		robot = new Robot();
		BufferedImage img = robot.createScreenCapture(new Rectangle(a, b, c, d));
		try {
			ImageIO.write(img, "jpg", new File(getPath+"\\"+getText.substring(0, getText.lastIndexOf('.'))+".jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException
	{
		new Pixels().setVisible(true);
	}
}
