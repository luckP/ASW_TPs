package com.robocode.luc;

import java.util.LinkedList;

import robocode.AdvancedRobot;
import robocode.BattleEndedEvent;
import robocode.DeathEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;
import robocode.WinEvent;
import robocode.util.Utils;

public class SmartLucRobot extends AdvancedRobot{
	
	
	private static class Neuron{
		private double weights[];
		private int numInputs;
		private double threshold = 0.5;
		private final double MUTATION_RATE = 10; //10% de chance de haver mutacao
		
		public Neuron(int numInputs) {
			this.numInputs = numInputs;
			this.weights = new double[numInputs];
//			this.threshold = Math.random();
			
			for(int i=0; i< numInputs; i++)
				this.weights[i] = Math.random();
			
		}
		
		public Neuron(double[] w) {
			
			numInputs = w.length;
			for(int i=0; i<numInputs; i++) {
				weights[i] = w[i];
			}
		}
		
		public Neuron(Neuron n1, Neuron n2) {
			
			double w1[] = n1.getWeights();
			double w2[] = n2.getWeights(); 
			
			numInputs = n1.getNumInputs();
			weights = new double[n2.getWeights().length];
			for(int i=0; i<numInputs; i++) {
				double val = Math.random();
				if(val< MUTATION_RATE/100)
					weights[i] = Math.random();
				else if(val<(100-MUTATION_RATE)/2)
					weights[i] = w1[i];
				else 
					weights[i] = w2[i];
			}
		}
		
		public int getNumInputs() {
			return numInputs;
		}
		
		public double[] getWeights() {
			return weights;
		}
		
		public double calc(double[] inputs) {
			double sum = 0;
			for(int i=0; i<numInputs; i++) 
				sum+=weights[i]*inputs[i];
			
			sum/=numInputs;
			if(sum>=threshold)
				return sum;
			return 0;
		}
		
		@Override
		public String toString() {
			String str = "{";
			for(int i=0; i<numInputs; i++) {
				str+=Double.toString(weights[i])+ ", ";
			}
			return str.substring(0, str.length()-2)+"}";
		}
	}
	
	private static class Brain{
		private LinkedList<LinkedList<Neuron>> neuronsLayers = new LinkedList<LinkedList<Neuron>>();
		private double time;
		public Brain(int... neuronNumberLayer) {
			time = 0;
			for(int i=1; i<neuronNumberLayer.length; i++) {//				
				this.neuronsLayers.add(new LinkedList<Neuron>());
				for(int j=0; j<neuronNumberLayer[i-1]; j++) {
					this.neuronsLayers.get(i-1).addFirst(new Neuron(neuronNumberLayer[i-1]));
				}
			}
		}
		
		public Brain(Brain b1, Brain b2) {
			int len = b1.getNeuronsLayers().size();
			for(int i=0; i<len; i++) {
				neuronsLayers.add(new LinkedList<Neuron>());
				for(int j=0; j<b1.getNeuronsLayers().size(); j++) {
					Neuron n1 = b1.getNeuronsLayers().get(i).pop();
					Neuron n2 = b2.getNeuronsLayers().get(i).pop();
					neuronsLayers.get(i).addFirst(new Neuron(n1, n2));
				}
			}
			
		}
		public double getTime() {
			return time;
		}
		
		public void setTime(double t) {
			time = t;
		}
		
		@Override
		public String toString() {
			String str = "[";
			for(LinkedList<Neuron> nLayer: neuronsLayers) {
				for(Neuron n: nLayer)
					str+=n.toString();
				str+=", ";
			}
			return str.substring(0, str.length()-2)+"]";
		}
		
		public LinkedList<LinkedList<Neuron>> getNeuronsLayers(){
			return neuronsLayers;
		}
		
		public double normalizing(double d) {
			return d/(10*(d%10+1));
		}
		
		public double calc(double... val) {
			for(int i=0; i< val.length; i++) {
				val[i] = normalizing(val[i]);
			}
			double[] res = val;
			for(LinkedList<Neuron> nLayer: neuronsLayers) {
				double[] res2 = new double[nLayer.size()];
				int i=0;
				for(Neuron n: nLayer) {
					res2[i] = n.calc(res);
					i++;
				}
				res = new double[res2.length];
				res = res2;
			}
			return res[0];
		}
		
	}
	
	public static void main(String args[]) {
		Brain b1 = new Brain(3,5,3);
		Brain b2 = new Brain(3,5,3);
		Brain b3 = new Brain(b1, b2);
		
		System.out.println(b3.toString());
	}
	
	private Brain brainOnScannedRobotAhead = new Brain(3,3,3);
	private Brain brainOnScannedRobotTurnGunRight = new Brain(3,3,3);;
	private int dir = 1;
	
	private static int count = 1;
	
	
	private static Brain[] brainListWin = new Brain[10];
	private static Brain[] brainListDeath = new Brain[10];


	@Override
	public void run() {
		
		
		setAdjustRadarForGunTurn(true);
		setAdjustRadarForRobotTurn(true);
		setAdjustGunForRobotTurn(true);
		
		while(true) {
			turnRadarRightRadians(Double.POSITIVE_INFINITY);
		}
	}
	
	@Override
	public void onScannedRobot(ScannedRobotEvent e) {
		lockRadarOnTarget(e);
		setTurnGunRightRadians(brainOnScannedRobotTurnGunRight.calc(e.getBearingRadians(), e.getDistance(), e.getVelocity())*Math.PI);
		setTurnRight(brainOnScannedRobotAhead.calc(e.getBearingRadians(), e.getDistance(), e.getVelocity())*Math.PI);
		setAhead(Double.POSITIVE_INFINITY*dir);
		fire(1);
	}
	
	public void lockRadarOnTarget(ScannedRobotEvent e) {
		double radarTurn = getHeadingRadians() + e.getBearingRadians() - getRadarHeadingRadians();
		setTurnRadarRightRadians(Utils.normalRelativeAngle(radarTurn));
	}
	
	public void onHitWall(HitWallEvent e) {
		dir*=-1;
		setTurnRight(45);
	}
	
	public void onHitRobot(HitRobotEvent e) {
		dir*=-1;
	}
	
	public void onBattleEnded(BattleEndedEvent e) {
		
		
	}
	
	@Override
	public void onDeath(DeathEvent e){
		double time = e.getTime();
		for(int i=0; i<brainListDeath.length; i++)
				
		
		count++;
	}
	
	@Override
	public void onWin(WinEvent e) {
		
		double time = e.getTime();
		for(int i=0; i<brainListWin.length; i++)
			
		
		count++;
	}
	
	public void newGeneration() {
		int cWin = 0;
		int cDeath = 0;
//		for()
	}


}
