import java.util.ArrayList;
import java.util.Random;

public class LinearRegression {
	///hyper-parameters
	protected double learningRate=0.01;
	///
	protected int dim;
	protected ArrayList<Double> model;
	protected double bias=0;
	protected Random rand=new Random();
	
	/**
	 * @return rnd : a non zero random double between 0 and 1. 
	 */
	protected Double nextNonZeroRand() {
		double rnd=rand.nextDouble();
		while(rnd<=0) {
			rnd=rand.nextDouble();
		}
		return rnd;
	}
	/**
	 * Initializes the model with random weights and random bias at the beginning.
	 */
	protected void initModel() {
		model=new ArrayList<>(dim);
		for (int i=0;i<dim;++i) {
			model.add(nextNonZeroRand());
		}
		bias=nextNonZeroRand();
	}
	/**
	 * @param x :The normalized values the model should predict the price for. 
	 * @return res :  the predicted value with the current model.
	 * 
	 * This function multiplies the given normalized features with the the current weights in the model and add the bias.  
	 */
	protected double fitOne(ArrayList<Double> x) {
		assert(model.size()==x.size());
		double res=0;
		for (int i=0;i<dim;++i) {
			res+=model.get(i)*x.get(i);
		}
		res+=bias;
		return res;
	}
	/**
	 * @param inp
	 * @param output
	 */
	protected void fit(ArrayList<ArrayList<Double> >inp,ArrayList<Double> output) {
		for (int i=0;i<inp.size();++i) {
			output.set(i, fitOne(inp.get(i)));
		}
	}

	/**
	 * @param inp
	 * @param output
	 * @return err
	 * 
	 * This function calculate the error of our algorithm in the test data. 
	 * It does so by predicting the prices using model and comparing it with the actual price. 
	 * Then it normalizes the error and returns it.
	 */
	protected double calcError(ArrayList<ArrayList<Double> >inp,ArrayList<Double> output) {
		double err=0;
		//double mnDiff=1000;
		for (int i=0;i<inp.size();++i) {
			assert(inp.get(i).size()==model.size());
			double predict=fitOne(inp.get(i));
			double actual=output.get(i);
		//	mnDiff=Math.abs(Math.min(mnDiff, actual-predict));
			err+=(actual-predict)*(actual-predict);
		}
		return err/inp.size();
	}
	/**
	 * @param inp
	 * @param output
	 * 
	 * This function is used for training. It calculates the gradients for all the weights first. 
	 * Then it update the weights and the bias of the model based on their gradients.  
	 */
	protected void train(ArrayList<ArrayList<Double> >inp,ArrayList<Double> output) {
		assert(inp.size()==output.size());
		ArrayList<Double> grad=new ArrayList<>();
		double gradBias=0.0;
		for (int i=0;i<dim;++i) {
			grad.add(0.0);
		}
		int n=inp.size();
		for (int i=0;i<n;++i) {
			assert(inp.get(i).size()==model.size());
			double predict=fitOne(inp.get(i));
			double actual=output.get(i);
			double diff=predict-actual;
			for (int j=0;j<dim;++j) {
				grad.set(j,grad.get(j)+(2.0/n)*model.get(j)*diff);
			}
			gradBias+=(2.0/n)*diff;
		}
		for (int i=0;i<dim;++i) {
			model.set(i, model.get(i)-learningRate*grad.get(i));
		}
		bias=bias-learningRate*gradBias;
	}
	/**
	 * @param dimension
	 * This is the constructor of the class. It takes the dimension of our model
	 * (The number of weights wanted by the user.)
	 */
	public LinearRegression(int dimension){
		this.dim=dimension;
		initModel();
	}
	
	
	
}
