import java.util.*;
import edu.duke.*;

public class EarthQuakeClient {
    public EarthQuakeClient() {
        // TODO Auto-generated constructor stub
    }

    public ArrayList<QuakeEntry> filterByMagnitude(ArrayList<QuakeEntry> quakeData,
    double magMin) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        // TODO
        for(int i = 0;i<quakeData.size();i++){
            if(quakeData.get(i).getMagnitude()>magMin){
                answer.add(quakeData.get(i));
            }
        }    
        return answer;
    }

    public ArrayList<QuakeEntry> filterByDistanceFrom(ArrayList<QuakeEntry> quakeData, double distMax, Location from) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        // TODO
        for(int i =0; i <quakeData.size();i++){
            QuakeEntry quake = quakeData.get(i);            
            double distanceInMeters = from.distanceTo(quake.getLocation())/1000;
            if(distanceInMeters<distMax){
                answer.add(quakeData.get(i));
            }
        }
        return answer;
    }

    public void dumpCSV(ArrayList<QuakeEntry> list){
        System.out.println("Latitude,Longitude,Magnitude,Info");
        for(QuakeEntry qe : list){
            System.out.printf("%4.2f,%4.2f,%4.2f,%s\n",
                qe.getLocation().getLatitude(),
                qe.getLocation().getLongitude(),
                qe.getMagnitude(),
                qe.getInfo());
        }

    }

    public void bigQuakes() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        ArrayList<QuakeEntry> temp = filterByMagnitude(list,5.0);
        System.out.println("read data for "+list.size()+" quakes");
        for(int i =0; i < temp.size();i++){
            System.out.println(temp.get(i));
        }
        System.out.println("Found "+list.size()+" quakes that match that criteria");
    }

    public void closeToMe(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size()+" quakes");

        // This location is Durham, NC
        //Location city = new Location(35.988, -78.907);
        // This location is Bridgeport, CA
         Location city =  new Location(38.17, -118.82);
        ArrayList<QuakeEntry> temp  = filterByDistanceFrom(list,1000.00,city);
        
        for(int i =0;i<temp.size();i++){
            QuakeEntry entry = temp.get(i);
            double distanceInMeters = city.distanceTo(entry.getLocation())/1000;
            System.out.println(distanceInMeters + " " +entry.getInfo());
        }
        System.out.println("read data for "+temp.size()+" quakes" + " meet the criteria");

        // TODO
    }
    //depths of Earth Quakes
    public ArrayList<QuakeEntry> filterByDepth(ArrayList<QuakeEntry> quakeData, double minDepth, double maxDepth){
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for(QuakeEntry quake : quakeData){
            if(quake.getDepth()>minDepth && quake.getDepth()<maxDepth){
                answer.add(quake);
            }
        }
        return answer;
    }
    public void quakesOfDepth(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size()+" quakes");
        ArrayList<QuakeEntry> temp = filterByDepth(list, -10000.0,-5000.0);
        for(int i =0;i<temp.size();i++){
            QuakeEntry entry = temp.get(i);
            System.out.println(entry);
        }
        System.out.println("Found "+temp.size()+" quakes that match that criteria");
    }
    public ArrayList<QuakeEntry> filterByPhrase(ArrayList<QuakeEntry> quakeData, String where, String phrase){
         ArrayList<QuakeEntry> answer = new  ArrayList<QuakeEntry>();
         
         int temp=-1;
         for(QuakeEntry quake : quakeData){
            if(where.equals("start")){
                if(quake.getInfo().startsWith(phrase)){
                    answer.add(quake);
                }
            }
            else if(where.equals("end")){
                if(quake.getInfo().endsWith(phrase)){
                    answer.add(quake);
                }
            }
            else if(where.equals("any")){
                temp = quake.getInfo().indexOf(phrase);
                if(temp!=-1){
                    answer.add(quake);
                }
            }
         }
         return answer;
    }
    public void quakesByPhrase(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size()+" quakes");
        ArrayList<QuakeEntry> temp =filterByPhrase(list,"start","Explosion");
        for(int i =0;i<temp.size();i++){
            QuakeEntry entry = temp.get(i);
            System.out.println(entry);
        }
        System.out.println("Found "+temp.size()+" quakes that match that criteria");
    }
    public void createCSV(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedatasmall.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        dumpCSV(list);
        System.out.println("# quakes read: " + list.size());
        for (QuakeEntry qe : list) {
            System.out.println(qe);
        }
    }
    
}
