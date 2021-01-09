import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
public class flaskChooser{
    public static int flaskChoice(int numOrders, int[] requirements, int flaskTypes, int totalMarks, int[][] markings){
        int[] excess = new int[flaskTypes];
        int counter = 0;
        for(int i = 0; i < flaskTypes; i++){
            //for each flask type, calculate the waste
            List<Integer> markingsPerFlask = new ArrayList<>();
            while(counter < markings.length && markings[counter][0] == i){
                markingsPerFlask.add(markings[counter][1]);
                counter++;
            }
            excess[i] = calculateWaste(numOrders, requirements, markingsPerFlask);
        }
        //find the flask in storage with the minimum waste
        int retMin = Integer.MAX_VALUE;
        int retMinIndex = -1;
        for(int i = 0; i < excess.length; i++){
            if(retMin > excess[i]){
                retMin = excess[i];
                retMinIndex = i;
            }
        }
        if(retMinIndex == -1){
            //no flask will work
            return -1;
        }
        return retMinIndex;
    }
    public static int calculateWaste(int numOrders, int[] requirements, List<Integer> markings){
        int sum = 0;
        for(int i = 0; i < numOrders; i++){
            //for each requirement, choose the optimal marking
            int optimalMarking = optimalMarking(requirements[i], markings);
            if(optimalMarking != -1){
                sum += optimalMarking - requirements[i]; 
            } else {
                return Integer.MAX_VALUE;
            }
        }
        return sum;
    }
    public static int optimalMarking(int requirement, List<Integer> markings){
        for(int i = 0; i < markings.size(); i++){
            if(markings.get(i) > requirement){
                //markings is sorted in ascending order
                return i;
            }
        }
        //requirement larger than all markings, this flask will not work
        return -1;
    }
    public static void main (String[] args){
        int numOrders = 4;
        int[] requirements = {4, 6, 6, 7};
        int flaskTypes = 3;
        int totalMarks = 9;
        int[][] markings = {{0,3},{0,5},{0,7},{1,6},{1,8},{1,9},{2,3},{2,5},{2,6}};
        System.out.println(flaskChoice(numOrders, requirements, flaskTypes, totalMarks, markings));
    }
}