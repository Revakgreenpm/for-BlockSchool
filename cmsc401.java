//Philip Revak Green
//An implementation of Dijkstra's algorithm
//designed to find the least expensive path
//from city (node) 1 to city 2
//along highways (edges).


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;

import org.w3c.dom.Node;


public class cmsc401 {
	
	
	
	//represent edges between two cities
	public static class highway{
		
		//gas represents price for traversing edge
		
		public double gas;
		public node src;
		public node dst;
		public highway(node src, node dst, double gas){
			this.src=src;
			this.dst=dst;
			this.gas=gas;
		}
		
		public void setSrc(node src){
			this.src=src;
		}
		
		public void setGas(double gas){
			this.gas=gas;
		}
		
		public double getGas(){
			return this.gas;
		}
		
		public node getSrc(){
			return this.src;
		}
		
		public void setDst(node dest){
			this.dst=dest;
		}
		
		public node getDst(){
			return this.dst;
		}
		
		//N is a city the highway is connected to.
		//Returns whatever city highway connects to N.
		public node getP(node N){
			if(N.equals(getSrc())){
				return getDst();
			}
			else if(N.equals(getDst())){
				return getSrc();
			}
			else{
				//System.out.println("This highway has no end");
				return N;
			}
		}
		
		
		
	}
	
	//Represents a graph of connected cities and highways
	public static class network{
		public ArrayList<node> cities;
		public ArrayList<highway> highs;
		public ArrayList<node> selected;
		
		public void addCity(int id, double motel){
			node n=new node(id,motel);
			//System.out.println("attempting to add city "+id);
			addC(n);
		}
		
		public void addHighway(int srcID, int dstID, double gas){
			
			node src = getId(srcID);
			node dst= getId(dstID);
			
			highway news = new highway(src,dst,gas);
			src.addNeighbor(news);
			dst.addNeighbor(news);
			
			highs.add(news);
			
		}
		
		public void addSelected(node n){
			ArrayList<node> l= getSelected();
			l.add(n);
			setSelected(l);
			
		}
		
		public ArrayList<node> getSelected(){
			return this.selected;
		}
		
		public void setSelected(ArrayList<node> list){
			this.selected=list;
		}
		
		public network(){
			cities=new ArrayList<node>();
			highs=new ArrayList<highway>();
			ArrayList<node> s=new ArrayList<node>();
			ArrayList<node> p = new ArrayList<node>();
			setSelected(s);
			addCity(0,0);
			addCity(1,0);
			addCity(2,0);
		}
		
		public void addC(node n){
			ArrayList<node> c=this.cities;
			c.add(n);
			this.cities=c;
			
		}
		
		public ArrayList<node> getCities(){
			return this.cities;
		}
		
		public node getId(int id){
			ArrayList<node> c=getCities();
			for(int i=0;i<c.size();i++){
				if(c.get(i).getId()==id){
				return cities.get(id);
				}
			}
			return cities.get(0);
		}
		
		public void initialize(){
			node s=getId(1);
			s.setCost(0);
			s.setMotel(0);
			LinkedList<node> path=new LinkedList<node>();
			path.add(s);
			s.path=path;
		}
		
		public void relax(node u){
			//System.out.println("Relaxing "+u.getId());
			for(int i=0;i<u.neighbors.size();i++){
				highway h=u.neighbors.get(i);
				node v=h.getP(u);
				if(u.cost>v.cost+h.gas+u.motel){
					//System.out.println(u.getId()+"'s cost was "+u.getCost()+" and is now "+(v.cost+h.gas+u.motel));
					u.setCost(v.getCost()+h.getGas()+u.getMotel());
				}
			}
		}
		
		public double getShortest(){
			
			boolean LA=false;
			PriorityQueue<node> p=new PriorityQueue<node>();
			LinkedList<node> shortest=new LinkedList<node>();
			initialize();
			node n=getId(1);
			p.add(n);
			ArrayList<node> s=getSelected();
			while(LA==false){
				n=p.poll();
				s.add(n);
				//System.out.println("Now examining #"+n.getId()+" which costs "+n.getCost());
				//System.out.println("It has "+n.getNeighbors().size()+" neighbors");
				if(n.getId()==2){
					LA=true;
					break;
				}
				for(int i=0;i<n.getNeighbors().size();i++){
					node d=n.getNeighbors().get(i).getP(n);
					//System.out.println(n.getId()+" borders with "+d.getId());
					relax(d);
					if(!p.contains(d)&&!s.contains(d)){
						//System.out.println(d.getId()+" discovered");
						p.add(d);
					}
					/*if(n.getNeighbors().get(i).src.equals(n)){
						relax(n.neighbors.get(i).dst);
						n.neighbors.get(i).dst.path=n.path;
						n.neighbors.get(i).dst.path.add(n);
						if(!p.contains(n.getNeighbors().get(i).dst)){
							System.out.println(n.getNeighbors().get(i).dst+" discovered");
							p.add(n.neighbors.get(i).dst);
						}
					}
					else{
						relax(n.neighbors.get(i).src);
						n.neighbors.get(i).src.path=n.path;
						n.neighbors.get(i).src.path.add(n);
						if(!p.contains(n.neighbors.get(i).src))
						{
							System.out.println(n.getNeighbors().get(i).src+" discovered");
							p.add(n.neighbors.get(i).src);
						}
					}*/
				}
				
			}
				
			return n.getCost();
			
		}		
	
	
	}
	
	public static class node implements Comparable<node>{
		
		public int id;
		public ArrayList<highway> neighbors;
		public LinkedList<node> path;
		public double motel;
		public double cost;
		
		public node(int ID,double motel){
			setId(ID);
			setNeighbors(new ArrayList<highway>());
			setPath(new LinkedList<node>());
			setMotel(motel);
			setCost(Double.POSITIVE_INFINITY);
		}
		
		public void setMotel(double cost){
			this.motel=cost;
		}

		public double getMotel(){
			return this.motel;
		}
		
		public void setCost(double cost){
			this.cost=cost;
			//System.out.println(this.getId()+"'s cost is now "+this.getCost());
		}
		
		public void setNeighbors(ArrayList<highway> list){
			this.neighbors=list;
		}
		
		public void addNeighbor(highway h){
			ArrayList<highway> l=getNeighbors();
			l.add(h);
			setNeighbors(l);
		}
		
		public ArrayList<highway> getNeighbors(){
			return this.neighbors;
		}
		
		
		public double getCost(){
			return this.cost;
		}
		
		public void setId(int id){
			this.id=id;
		}
		
		public int getId(){
			return this.id;
		}
		
		public LinkedList<node> getPath(){
			return this.path;
		}
		
		public void setPath(LinkedList<node> path){
			this.path=path;
		}
		
		public void expandPath(node n){
			LinkedList<node> l=getPath();
			l.add(n);
			setPath(l);
		}

		public int compareTo(node other) {
			return (int) (this.getCost() - other.getCost());
		}
	}

	
	
	
	
public static void main(String args[]){
		
	
	Scanner scan = new Scanner(System.in);
		
	//Feed input as follows:
	//Line 1 - # cities i.e: 5
	//Line 2 - # highways i.e: 7
	//line 3 to 3+(n-2) cost of each city i.e: 3 400 
	//										   4 725
	//subsequent lines - cost of highway
	// connecting cities i.e:  3 4 50
	//						   1 4 200
	
		
		network US = new network();
		int n=scan.nextInt();
		int m=scan.nextInt();
		for(int i=0;i<n-2;i++){
			int id=scan.nextInt();
			//System.out.println(id);
			int motel=scan.nextInt();
			//System.out.println(motel);
			US.addCity(id, motel);
		}
		//System.out.println("Now add highways");
		for(int i=0;i<m;i++){
			int srcID=scan.nextInt();
			int dstID=scan.nextInt();
			int gas=scan.nextInt();
			US.addHighway(srcID, dstID, gas);
		}
		double result=US.getShortest();
		System.out.println((int)result);

	}
	
	
}
