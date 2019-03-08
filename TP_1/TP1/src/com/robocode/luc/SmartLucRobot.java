package com.robocode.luc;

import java.util.LinkedList;

import robocode.AdvancedRobot;
import robocode.BattleEndedEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;

public class SmartLucRobot extends AdvancedRobot{
	
	
	private static class Neuron{
		private double weights[];
		private int numInputs;
		private double threshold = 0.5;
		private final double MUTATION_RATE = 10; //10% de chance de haver mutacao
		static int count = 0;
		
		public Neuron(int numInputs) {
			this.numInputs = numInputs;
			this.weights = new double[numInputs];
//			this.threshold = Math.random();
			
			for(int i=0; i< numInputs; i++)
				this.weights[i] = Math.random();
			
		}
		
		public Neuron(double[] w) {
			for(int i=0; i<numInputs; i++) {
				weights[i] = w[i];
			}
		}
		
		public Neuron(Neuron n1, Neuron n2) {
			
			double w1[] = n1.getWeights();
			double w2[] = n2.getWeights(); 
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
		private LinkedList<Neuron> neuronsLayers[];
		public Brain(int... neuronNumberLayer) {
			this.neuronsLayers = new LinkedList[neuronNumberLayer.length-1];
			for(int i=1; i<neuronNumberLayer.length; i++) {//				
				this.neuronsLayers[i-1] = new LinkedList<Neuron>();
				for(int j=0; j<neuronNumberLayer[i-1]; j++) {
					this.neuronsLayers[i-1].addFirst(new Neuron(neuronNumberLayer[i-1]));
				}
			}
		}
		
		public Brain(Brain b1, Brain b2) {
			int len = b1.getNeuronsLayers().length;
			neuronsLayers = new LinkedList[len];
			for(int i=0; i<len; i++) {
				neuronsLayers[i].addFirst(new Neuron(b1.getNeuronsLayers()[i].pop(), b2.getNeuronsLayers()[i].pop()));
			}
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
		
		public LinkedList<Neuron>[] getNeuronsLayers(){
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
	
//	public static void main(String args[]) {
//		Brain b = new Brain(3,3,1);
//		System.out.println(b.toString());
//		double[] d = new double[3];
////		d[0] = 1;
////		d[0] = 1;
////		d[0] = 1;
//		System.out.println(b.calc(1,2,3));
//	}
	
	Brain brainOnScannedRobotAhead = new Brain(3,3,3);
	Brain brainOnScannedRobotTurnGunRight = new Brain(3,3,3);
	int dir = 1;

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

}
