//Car Type
//Mileage?
//Battery type / Time to charge
//[Assume Fully Charged, most charging happens at home]
//Distance to Destination
//Distance to next charge
//Time to stay / leave time from destination
//Will the car need a station? If not, delete this object
//Stores stats.


// home and desitnation int meanings
// 1 = "Richmond"
// 2 = "DC"
// 3 = "Philly"
// 4 = "NY"
// 5 = "Boston"
import java.math.*;
import java.util.*;
public class Car{
  public double range;
  public double searchRange;
  public int carType;
  public int wealth;
  public int home;
  public double stayTime;
  public int destination;
  public String carTypeSTR[] = {"null", "BMW i3", "Tesla Model 3", "Chevy Bolt"};
  public int chargeTimeArr[] = {0,40,50,80}; // time to charge in minutes
  public double rangeArr[] = {0,114,270,238}; // range of car.
  public int homePointArr[] = {0,0,109,248,346,561}; // distances from richmond
  public double chargePer;
  public boolean destBound;
  public Station stationArray[];
  public int currentStation; // The current station within the stationArray that this car resides
  public Simulator ss;
  public int homePoint;
  public int destinationPoint;
  public int distance;
  public int chargeTime;

  /**
  * Creates Car for use in simulation; we will be considering several factors,
  * which will determine under what conditions the car will stop at a charging station.
  *
  * @param carType Int value that determines the type of electric car (1 = BMW i3, 2 = Tesla Model 3, 3 = Chevy Bolt)
  * @param wealth wealth status of driver (1 = lower, 2 = middle, 3 = upper)
  * @param home Starting City of vehicle (1 = Richmond, 2 = D.C., 3 = Philly, 4 = NYC, 5 = Boston)
  * @param stayTime Time staying at the destination city.
  * @param destination the city destination of the vehicle.
  */
  public Car(int carType,
             int wealth,
             int home,
             double stayTime,
             int destination,
             Simulator ss){

    chargePer = 100.0; // The % that the batery is charged.
    this.carType = carType;
    this.wealth = wealth;
    this.home = home;
    this.stayTime = stayTime;
    this.destination = destination;
    destBound = true;
    this.ss = ss;

    // Make the things that come with the attributes
    int chargeTime = chargeTimeArr[carType]; // Time it takes to charge a car, will differ, find
    range = rangeArr[carType]; // This is the range on a full battery, possible distance is calculated from range*%batterty
    searchRange = range/4; // This is the threshhold where it will look for more
    int homePoint = homePointArr[home]; //distance from richmond
    int destinationPoint = homePointArr[destination];
    int distance = destinationPoint - homePoint; // This is the distance the car needs to travel
  }

  /**
  * Deletes car (i.e., when car is finished, or if not applicable to simulation)
  * @param car Car to be deleted.
  */
  public static void deleteCar(Car car){
    car = null;
  }
  public Station getNextStation(){
    Station ans = stationArray[currentStation+1];
    return ans;
  }
  public Station getLastStation(){
    Station ans = stationArray[currentStation-1];
    return ans;
  }
  
  // Moves the car up 1 station
  public void moveUp() throws Exception{
    currentStation++;
    if (currentStation > stationArray.length){
      throw new Exception("You incremented the station when it was at its destination");
    }
  }
  
  // Helper function for the getStationList() function that checks forward
  public Station searchForward(double pos){
    Station target = new RechargeStation();
    for(int i=0;i<ss.StationArray.length;i++){
      if(ss.StationArray[i].position < pos){
        target = ss.StationArray[i];
      }
    }
    return target;
  }
  
  // Helper function for the getStationList() function that checks forward
  public Station searchBack(double pos){
    Station target = new RechargeStation();
    for(int i=ss.StationArray.length;i>0;i--){
      if(ss.StationArray[i].position > pos){
        target = ss.StationArray[i];
      }
    }
    return target;
  }
  
  // Function that returns a list of Stations that this car will go to along its journey
  public Station[] getStationList() throws Exception{
    boolean isDone = false;
    boolean returnTrip = false;
    double pos = (double)homePoint; // Car's starting position
    int direction = 0;
    if(distance > 0){
      direction = 1;
    }
    else if (distance < 0){
      direction = -1;
    }
    if (direction == 0){
      throw new Exception("This car doesn't have a direction");
    }
    ArrayList<Station> stations = new ArrayList<Station>();
    stations.add(ss.cityList.get(home)); // Adds the initial position to the array (the homePoint)
    while(!isDone){
      // If you're going left to right...
      if(direction==1){
        // Can i get to my destination?
        double target = destinationPoint*(!returnTrip ? 1:0) + homePoint*(returnTrip ? 1:0);
        if(range > (target - pos)){
          // If I'm not on my way back, I must be arriving at my destintaiton
          if(!returnTrip){
            stations.add(ss.cityList.get(destination));
            pos = ss.cityList.get(destination).position;
            direction=-1;
          }
          // If I am returning, I must be returning home
          else if (returnTrip){
            stations.add(ss.cityList.get(home));
            isDone = true;
          }
        }
        // If I can't get to my destination, then get me to the furthest station
        else{
          stations.add(searchForward(pos));
          pos = stations.get(stations.size()-1).position;
        }
      }
      // If you're going right to left...
      if(direction==-1){
        // Can I get to my destination?
        double target = destinationPoint*(!returnTrip ? 1:0) + homePoint*(returnTrip ? 1:0);
        if(range > (pos - target)){
          if(!returnTrip){
            stations.add(ss.cityList.get(destination));
            pos = ss.cityList.get(destination).position;
            direction=1;
          }
          // If I am returning, I must be returning home
          else if (returnTrip){
            stations.add(ss.cityList.get(home));
            isDone = true;
          }
        }
        // If I can't get to my destination, then get me to the furthest station
        else{
          stations.add(searchBack(pos));
          pos = stations.get(stations.size()-1).position;
        }
      }
    }
    return stations.toArray(new Station[stations.size()]);
  }
  
  public String reportStatistics(){
    return "";
  }
}
