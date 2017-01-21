package _Tankem_Gestion_du_Compte;



import javax.swing.JPanel;

import java.awt.Component;
import java.awt.GridLayout;
import java.util.Vector;
import java.awt.Color;
import java.awt.image.ComponentSampleModel;

import javax.swing.JLabel;
import javax.swing.border.LineBorder;

public class MapThermique extends JPanel {

	/**
	 * Create the panel.
	 */
	private Color colDepart = Color.WHITE;
	private Color colA = Color.RED;
	private Color colB = Color.GREEN;
	private Color colAB = Color.MAGENTA;
	private double dataSetA[][];
	private double dataSetB[][];
	private int x;
	private int y;
	private JPanel mainPanel;
	private JLabel lblNewLabel_1;
	
	public MapThermique(int x,int y,double dataSetA[][],double dataSetB[][], int width, int height, int x2, int y2) {
		
		this.dataSetA = dataSetA;
		this.dataSetB = dataSetB;
		this.x = x;
		this.y = y;
		setLayout(null);
		mainPanel = new JPanel();
		mainPanel.setBounds(x2, y2, width, height);
		add(mainPanel);
		mainPanel.setLayout(new GridLayout(y, x, 0, 0));
		
		lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setBounds(363, 254, 46, 14);
		add(lblNewLabel_1);
		
		createMap();
	}
	
	
	public Color findColorForACase(double pourcentA, double pourcentB)
	{
		int red,blue,green;
		red = (int) ((colA.getRed()-colDepart.getRed())*pourcentA+colDepart.getRed());
		blue = (int) ((colA.getBlue()-colDepart.getBlue())*pourcentA+colDepart.getBlue());
		green = (int) ((colA.getGreen()-colDepart.getGreen())*pourcentA+colDepart.getGreen());
		
		Color temp1 = new Color(red,green,blue);
		
		red = (int) ((colAB.getRed()-colB.getRed())*pourcentA+colB.getRed());
		blue = (int) ((colAB.getBlue()-colB.getBlue())*pourcentA+colB.getBlue());
		green = (int) ((colAB.getGreen()-colB.getGreen())*pourcentA+colB.getGreen());
		
		Color temp2 = new Color(red,green,blue);
		
		red = (int) ((temp2.getRed()-temp1.getRed())*pourcentB+temp1.getRed());
		blue = (int) ((temp2.getBlue()-temp1.getBlue())*pourcentB+temp1.getBlue());
		green = (int) ((temp2.getGreen()-temp1.getGreen())*pourcentB+temp1.getGreen());
		System.out.println("PA: " + pourcentA + " PB: " + pourcentB);
		System.out.println("red: " +red+" blue: " + blue + " green: " + green);
		
		Color colFinal = new Color(red,green,blue);
		
		
		return colFinal;
	}
	
	public void updateDataSetA(double dataSetA[][])
	{
		this.dataSetA = dataSetA;
		Component[] lesComponents = mainPanel.getComponents();
		for(int i = 0; i < lesComponents.length; i++)
		{
			mainPanel.remove(lesComponents[i]);
		}
		mainPanel.revalidate();
		createMap();
	}
	
	public void updateDataSetB(double dataSetB[][])
	{
		this.dataSetB = dataSetB;
		Component[] lesComponents = mainPanel.getComponents();
		for(int i = 0; i < lesComponents.length; i++)
		{
			mainPanel.remove(lesComponents[i]);
		}
		mainPanel.revalidate();
		createMap();
	}
	
	public void createMap()
	{
		
		for(int i = 0; i < x; i++)
		{
			for(int j = 0; j < y; j++)
			{
				JLabel lblNewLabel;
				lblNewLabel = new JLabel("");
				lblNewLabel.setBorder(new LineBorder(new Color(0, 0, 0)));
				lblNewLabel.setOpaque(true);
				lblNewLabel.setBackground(findColorForACase(dataSetA[i][j],dataSetB[i][j]));
				mainPanel.add(lblNewLabel);
			}
		}
	}
}
