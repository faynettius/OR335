import java.util.*;
public class Station{
  int length;
  int implementingCost;
  int chargingCost;
  int overheadCost;
  double position;

  public double getPosition(){
    return 0;
  }
  public int getOutletsInUse(){
    return -1;
  }
  public int getCapacity(){
    return -1;
  }
//  public int getCarqueue(){
//    return 0;
//  }

  public double getCosts(){
    return 0;
  }

  public int getQueueLength(){
    return -1;
  }
  public void queueCar(Car car){}
  
  public void dequeueCar(Car car){}
  
  public void queueOutlet(Car car) throws Exception{}
  
  public void dequeueOutlet(Car car){}
  public ArrayList<Car> getCarQueue(){
    return new ArrayList<Car>();
  }
}
