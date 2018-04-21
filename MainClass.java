public class MainClass{
 public static void main(String argv[]) throws Exception{
  Simulator ss = new Simulator();
  ss.FutureEventList = new EventList();
//  ss.Customers = new Queue();
  ss.stream=new Rand();
  ss.Clock=0.0;
  //ss.MeanInterArrivalTime=7.7;
  //ss.MeanServiceTime=6.21;   
  ss.Initialization();            
  //Loop until clock is greater than 10000
  while (ss.Clock<=10000){
   Event evt = ss.FutureEventList.getMin();  //get imminent event
   ss.FutureEventList.dequeue();             //delete the event
   ss.Clock=evt.getTime();                  //advance in time
   switch(evt.getType()){
      case 1: ss.ProcessCityDeparture(evt); 
      break;
      case 2: ss.ProcessStationArrival(evt);
      break;
      case 3: ss.ProcessOutletDeparture(evt);
      break;
//      case 4: ss.ProcessStationDeparture(evt);
//      break;
      case 5: ss.ProcessCityArrival(evt);
      break;
    }
//   if (evt.get_type()==Simulator.arrival) 
//    ss.ProcessArrival(evt);
//            else 
//             ss.ProcessDeparture(evt);
  }
  ss.ReportGeneration();
    }
}
