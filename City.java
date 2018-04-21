public class City extends Station{

  protected String name;
  protected double position;
  protected Simulator ss;
  public City(String name, double position, Simulator ss){
    this.name = name;
    this.position = position;
    this.ss = ss;
    
  }

  public double getPosition(){
    return position;
  }

}
