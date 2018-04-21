import java.util.*;
public class RechargeStation extends Station{

  protected double position;
  protected ArrayList<Car> carQueue;
  protected ArrayList<Car> outletQueue;
  protected double getCosts;
  protected String name;
  protected int capacity;
  protected int outletsInUse;
  protected int queueLength;
  // Statistics
  protected double accumWaiting;
  protected double accumUtilization;
  protected double lastTime;
  protected int departures;
  public Simulator ss;
  
  public RechargeStation(){
    this.name = "Larry";
    this.position = -1;
    this.capacity = 0;
    this.carQueue = new ArrayList<Car>();
    this.outletQueue = new ArrayList<Car>();
    
  }
  public RechargeStation(String name, double position, int capacity, Simulator ss){
    this.name = name;
    this.position = position;
    this.capacity = capacity;
    this.carQueue = new ArrayList<Car>();
    this.outletQueue = new ArrayList<Car>();
    this.ss = ss;
  }
  
  public void queueCar(Car car){
    // First, collect statistics
    updateStats();
    // queue the car
    queueLength++;
    outletQueue.add(car);
  }
  
  public void dequeueCar(Car car){
    // First, collect statistics
    updateStats();
    // Dequeue the car
    queueLength--;
    outletQueue.remove(car);
  }
  
  // Puts a car in this station's outlets
  public void queueOutlet(Car car) throws Exception{
    // First, collect statistics
    updateStats();
    // queue the car
    outletsInUse++;
    if(outletsInUse > capacity){
      throw new Exception("You tried to put too many cars in this Station's outlets");
    }
    outletQueue.add(car);
  }
  
  // Remove a car from the queue of cars in the outlet
  public void dequeueOutlet(Car car){
    // First, collect statistics
    updateStats();
    // Dequeue the car
    outletsInUse--;
    outletQueue.remove(car);
    departures++;
  }
  
  // Updates the statistics associated with this station
  public void updateStats(){
    accumUtilization += (ss.Clock - lastTime)*outletsInUse;
    accumWaiting += (ss.Clock - lastTime)*queueLength;
    lastTime = ss.Clock;
  }
  
  // This is a profile of 
  public String report(){
    String wStr = 
      "STATION REPORT\n" +
      "Name: " + name + 
      "\nPosition: " + position + 
      "\nCars In Queue: " + queueLength +
      "\nOutlets in Use: " + outletsInUse +
      "\nDepartures: " + departures +     
      "\nMy WaitingTime: " + accumWaiting +
      "\nUtilization: " + accumUtilization;
    return wStr;
  }
  
  public double getPosition(){
    return position;
  }
  public ArrayList<Car> getCarqueue(){
    return carQueue;
  }
  public double getCosts(){
    return 1000000;
  }
  public int getCapacity(){
    return capacity;
  }
  public int getOutletsInUse(){
    return outletsInUse;
  }
  public int getQueueLength(){
    return queueLength;
  }
  public ArrayList<Car> getCarQueue(){
    return carQueue;
  }
}
