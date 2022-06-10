
/**
 * Write a description of LargestQuakes here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;
public class LargestQuakes {
    public void findLargestQuakes(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("there are " + list.size() + " earquakes.");
        ArrayList<QuakeEntry> temp = getLargest(list,5);
        for(int i =0;i<temp.size();i++){
            System.out.println(temp.get(i));
        }
    }
    public int indexOfLargest(ArrayList<QuakeEntry> data){
        int max = 0;
        
        for(int i =1;i<data.size();i++){
            if(data.get(max).getMagnitude()<data.get(i).getMagnitude()){
            max = i;
            }
        }
        return max;
    }
    public ArrayList<QuakeEntry> getLargest(ArrayList<QuakeEntry> data, int howMany){
        ArrayList<QuakeEntry> copy = new ArrayList<QuakeEntry>(data);
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        int max = 0;
        for(int j =0;j<howMany;j++){
            for(int i =1; i< copy.size();i++){
            if(copy.get(max).getMagnitude()<copy.get(i).getMagnitude()){
            max = i;
            }
           }
            answer.add(copy.get(max));
            copy.remove(max);
        }
        return answer;
    }
}
