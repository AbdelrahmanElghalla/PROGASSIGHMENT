import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class LinearGUI extends JFrame{
	
	public LinearRegression reg;
	
    JTextField landSize=new JTextField();

    JTextField postcode=new JTextField();
    
    JLabel land_label = new JLabel();

    JLabel post_label = new JLabel();
    
    JButton b=new JButton("Click Here");  
    

    JLabel result = new JLabel();
    JLabel result_label = new JLabel();
	
	public LinearGUI() {
		// TODO Auto-generated constructor stub
		
	this.setLayout(new GridLayout(4,2));
	
	post_label.setText("Enter postcode here: ");
	land_label.setText("Enter land size here: ");
	result_label.setText("The approximate price of the house is: ");
	

	
	add(post_label);
	add(postcode);
	
	add(land_label);
	add(landSize);
	
	add(b);
	
	add(result_label);
	add(result);
	
    b.addActionListener(new ActionListener(){  
    	public void actionPerformed(ActionEvent e){  
            //tf.setText("Welcome to Javatpoint.");
			ArrayList<ArrayList<Double>> example = new ArrayList<>();
			ArrayList<Double> ex = new ArrayList<>();
			
			String price_text = landSize.getText();
			double price = Double.parseDouble(price_text);
			
			String post_text = postcode.getText();
			double post = Double.parseDouble(post_text);
			if(!SimpleCSVReader.postCodes.contains(post)) {
				result.setText("Enter a valid postcode");
			}else {
				
				ex.add(price);
				ex.add(post);
		
				example.add(ex);	
				for (int i=0;i<2;++i) {
					LinearRegressionTrainer.normalizeDataInput(example,i, LinearRegressionTrainer.values_input.get(i));
				}
	    		double price_house = reg.fitOne(example.get(0)) * LinearRegressionTrainer.values.get(1) + LinearRegressionTrainer.values.get(0);
	    		result.setText(new Double(price_house).toString());
    		}
    	}  
    });  
    
    
	}
}
