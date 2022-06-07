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
