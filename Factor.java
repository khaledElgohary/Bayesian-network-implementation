/*---------------------------*/
/*Name: Khaled Elgohary*/
/*Student Number: 7924188*/
/*Course: Comp4190*/
/*Instructor: Cuneyt ackora */
/*Assignment:2*/

import java.util.*;
import java.text.DecimalFormat;

public class Factor {
    String[] variables;
    String[][]values;
    double[] probabilities;
    public Factor(String[] variables,String[][] values,double[] probabilities){
        this.variables=variables;
        this.values=values;
        this.probabilities=probabilities;
    }
    public double[] getProbabilities(){
        return probabilities;
    }

    public String[] getVariables(){
        return variables;
    }

    public String[][] getValues(){
        return values;
    }

    public String toTable(){
        String[][] values=getValues();
        String[] variables=getVariables();
        double[] probabilities=getProbabilities();
        String out="";
        for(int row=0;row<values.length;row++){
            for(int col=0;col<values[0].length;col++){
                out=out+values[row][col]+" ";
            }
            out=out+Double.parseDouble(""+probabilities[row])+"\n";
        }
        return out;
    }
    //The below function is tested, what the function does is it filters the columns to observe one of the values of a
    //given variable, the values are in the for of -r, or -t , or depending on the variable Character, for the sake of
    //testing we used the Variables R,T, hence the values are -r,+r,-t,+t
    public static Factor observe(Factor m,String variable, String value){
        ArrayList<Integer> indexes=new ArrayList<>(); //will hold the indexes of certain rows
        String[] variables=m.getVariables();
        String[][] values=m.getValues();
        double[] probabilities= m.getProbabilities();
        variable=variable.toUpperCase();
        int indexColumn=0;
        for(int i=0;i<variables.length;i++){
            if(variable.equals(variables[i])){
                indexColumn=i;
                break;
            }
        }
        for(int i=0;i<values.length;i++){
            if(values[i][indexColumn].equals(value)){
                indexes.add(i);
            }
        }
        double[] temp1=new double[indexes.size()];
        for(int i=0;i<indexes.size();i++){
            temp1[i]=probabilities[indexes.get(i)];
        }
        String[][] temp2=new String[indexes.size()][values[0].length];
        for(int i=0;i<indexes.size();i++){
            for(int c=0;c<values[0].length;c++){
                temp2[i][c]=values[indexes.get(i)][c];
            }
        }
        return removeVariable(new Factor(variables,temp2,temp1),variable);
    }

    public static Factor multiply(Factor factor1,Factor factor2){
        //below variable is used for rounding, and displaying the decimals
        DecimalFormat decfor=new DecimalFormat("0.000");
        if(factor1.getVariables().length==0){
            for(int i=0;i<factor2.getProbabilities().length;i++){
                factor2.getProbabilities()[i]=Double.parseDouble(decfor.format(factor2.getProbabilities()[i]*factor1.getProbabilities()[0]));
            }
            return factor2;
        }
        else if(factor2.getVariables().length==0){
            for(int i=0;i<factor1.getProbabilities().length;i++){
                factor1.getProbabilities()[i]=Double.parseDouble(decfor.format(factor1.getProbabilities()[i]*factor2.getProbabilities()[0]));
            }
            return factor1;
        }
        //we already have a function that returns the full outer join of 2 2d arrays
        //only thing remaining the multiplying the probabilities and returning a new factor, and variables
        String[] newVariables=fullOuterJoin(factor1.getVariables(),factor2.getVariables());
        //the variable index, indicates the column indexes in the values array
        int fact1Column=-1;
        int fact2Column=-1;
        ArrayList<Double> newProbabilities=new ArrayList<>();
        if(factor1.getVariables().length<factor2.getVariables().length) {
            for (int outer = 0; outer < factor1.getVariables().length; outer++) {
                for (int inner = 0; inner < factor2.getVariables().length; inner++) {
                    if (factor1.getVariables()[outer].equals(factor2.getVariables()[inner])) {
                        fact1Column = outer;
                        fact2Column = inner;
                        break;
                    }
                }
            }
        }
        else{
            for (int outer = 0; outer < factor2.getVariables().length; outer++) {
                for (int inner = 0; inner < factor1.getVariables().length; inner++) {
                    if (factor1.getVariables()[inner].equals(factor2.getVariables()[outer])) {
                        fact1Column = inner;
                        fact2Column = outer;
                        break;
                    }
                }
            }
        }

        if(fact1Column==-1){
            return null;
        }
        else{
            if(factor1.getVariables().length< factor2.getVariables().length && factor1.getVariables().length!=1){
                for(int r=0;r<factor1.getValues().length;r++){
                    for(int c=0;c<factor2.getValues().length;c++){
                        if(factor1.getValues()[r][fact1Column].equals(factor2.getValues()[c][fact2Column])){
                            double probability=Double.parseDouble(decfor.format(factor1.getProbabilities()[r]*factor2.getProbabilities()[c]));
                            newProbabilities.add(probability);
                        }
                    }
                }
            }
            else{
                if(factor1.getValues().length==1){
                        for(int c=0;c<factor2.getValues().length;c++){
                            if(factor1.getValues()[0][fact1Column].equals(factor2.getValues()[c][fact2Column])){
                                double probability=Double.parseDouble(decfor.format(factor1.getProbabilities()[0]*factor2.getProbabilities()[c]));
                                newProbabilities.add(probability);
                            }
                        }
                }
                else if(factor2.getValues().length==1){
                    for(int r=0;r<factor1.getValues().length;r++){
                        if(factor1.getValues()[r][fact1Column].equals(factor2.getValues()[0][fact2Column])){
                            double probability=Double.parseDouble(decfor.format(factor1.getProbabilities()[r]*factor2.getProbabilities()[0]));
                            newProbabilities.add(probability);
                        }
                    }
                }
                for(int r=0;r<factor1.getValues().length;r++){
                    for(int c=0;c<factor2.getValues().length;c++){
                        if(factor1.getValues()[r][fact1Column].equals(factor2.getValues()[c][fact2Column])){
                            double probability=Double.parseDouble(decfor.format(factor1.getProbabilities()[r]*factor2.getProbabilities()[c]));
                            newProbabilities.add(probability);
                        }
                    }
                }
            }
            double[] newProbabilities2=new double[newProbabilities.size()];
            for(int i=0;i<newProbabilities.size();i++){
                newProbabilities2[i]=newProbabilities.get(i);
            }
            return new Factor(newVariables,fullOuterJoin(factor1.getValues(), factor2.getValues()),newProbabilities2);
        }
    }
    public static String[][] fullOuterJoin(String[][] array1, String[][] array2) {
        // Check if the second column of array1 is nulls
        boolean switchArrays = true;
        for (int i = 0; i < array1.length; i++) {
            if (array1[i].length >= 2 && array1[i][1] != null) {
                switchArrays = false;
                break;
            }
        }

        // If the second column of array1 is nulls, swap the arrays
        if (switchArrays) {
            String[][] temp = array1;
            array1 = array2;
            array2 = temp;
        }

        // Determine the size of the resulting array
        int rows = Math.max(array1.length, array2.length);
        int cols = Math.max(array1[0].length, array2[0].length);

        // Initialize the resulting array
        String[][] result = new String[rows][cols];

        // Fill the resulting array with values from array1
        for (int i = 0; i < array1.length; i++) {
            for (int j = 0; j < array1[i].length; j++) {
                result[i][j] = array1[i][j];
            }
        }

        // Fill the resulting array with values from array2
        for (int i = 0; i < array2.length; i++) {
            for (int j = 0; j < array2[i].length; j++) {
                // Skip values that already exist in array1
                if (contains(result, array2[i][j])) {
                    continue;
                }
                // Find an empty cell in the resulting array to place the value
                boolean placed = false;
                for (int k = 0; k < rows; k++) {
                    for (int l = 0; l < cols; l++) {
                        if (result[k][l] == null) {
                            result[k][l] = array2[i][j];
                            placed = true;
                            break;
                        }
                    }
                    if (placed) {
                        break;
                    }
                }
            }
        }

        return result;
    }
    // Helper method to check if a value exists in a 2D array
    public static boolean contains(String[][] array, String value) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if (value.equals(array[i][j])) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String[] fullOuterJoin(String[] arr1, String[] arr2) {
        ArrayList<String> joinedList = new ArrayList<>();

        // Add elements from arr1 to the joined list
        for (String s : arr1) {
            if (!joinedList.contains(s)) {
                joinedList.add(s);
            }
        }

        // Add elements from arr2 to the joined list
        for (String s : arr2) {
            if (!joinedList.contains(s)) {
                joinedList.add(s);
            }
        }

        // Convert the joined list to an array
        String[] joinedArr = new String[joinedList.size()];
        for (int i = 0; i < joinedList.size(); i++) {
            joinedArr[i] = joinedList.get(i);
        }

        // Sort the array in ascending order

        return joinedArr;
    }

    public static Factor sumout(Factor factor,String variable){
        DecimalFormat decfor=new DecimalFormat("0.000");
        Factor initial=removeVariable(factor,variable);
        ArrayList<Integer> matchingRows=new ArrayList<>();
        ArrayList<String> holder=new ArrayList<>();
        ArrayList<String> holder2=new ArrayList<>();
        ArrayList<Double> newProbabilities=new ArrayList<>();
        for(int r=0;r<initial.getValues().length;r++){
            for(int c=0;c<initial.getValues()[0].length;c++){
                holder.add(initial.getValues()[r][c]);
            }
            //now we have all the values in the first row, hence we can compare it to the other rows and see if there's
            //a match
            if(r==initial.getValues().length-1){
                break;
                // why ? , since we already checked the whole array, no need to look back
            }else{
                for(int r2=r+1;r2<initial.getValues().length;r2++){
                    for(int c2=0;c2<initial.getValues()[0].length;c2++){
                        holder2.add(initial.getValues()[r2][c2]);
                    }
                    //now we have all the values in the subsequent Row
                    boolean logic=true;
                    for(int i=0;i<holder2.size();i++){
                        if(!holder.get(i).equals(holder2.get(i))){
                            logic=false;
                            break;
                        }
                    }
                    if(logic && !matchingRows.contains(r)){
                        matchingRows.add(r);
                        newProbabilities.add(initial.getProbabilities()[r]+initial.getProbabilities()[r2]);
                    }
                    holder2.clear();
                }
            }
            holder.clear();
        }
        String[][] newVals=new String[initial.getValues().length/2][initial.getValues()[0].length];
        double[] probabilities=new double[initial.getProbabilities().length/2];
        for(int i=0;i<newVals.length;i++){
            newVals[i]=initial.getValues()[matchingRows.get(i)];
            probabilities[i]=Double.parseDouble(decfor.format(newProbabilities.get(i)));
        }
        return new Factor(organizeVariables(initial),newVals,probabilities);
    }

    public static Factor removeVariable(Factor factor,String variable){
        int targetedColumn=-1;
        int newLength=factor.getValues()[0].length-1;
        for(int i=0;i<factor.getVariables().length;i++){
            if(factor.getVariables()[i].equals(variable)){
                targetedColumn=i;
            }
        }
        if(targetedColumn==-1){
            return factor;
        }
        String[][] newVals=new String[factor.getValues().length][newLength];
        String[] newVariables=new String[factor.getVariables().length-1];
        ArrayList<String> temp=new ArrayList<>();
        for(int i=0;i<factor.getValues().length;i++){
            for(int x=0;x<factor.getValues()[0].length;x++){
                if(x!=targetedColumn){
                    temp.add(factor.getValues()[i][x]);
                }
            }
        }
        int controller=0;
        for(int i=0;i<newVals.length;i++){
            for(int x=0;x<newVals[0].length;x++){
                newVals[i][x]=temp.get(controller);
                controller++;
            }
        }
        //time to remove the variable from the list of variable for the factor using the same approach
        int control2=0;
        for(int i=0;i<factor.getVariables().length;i++){
            if(i==targetedColumn && i==factor.getVariables().length-1){
                break;
            }
            else{
                if(i==targetedColumn){
                    i++;
                    newVariables[control2]=factor.getVariables()[i];
                }
                else{
                    newVariables[control2]=factor.getVariables()[i];
                }
            }
            control2++;
        }
        return new Factor(newVariables,newVals, factor.getProbabilities());
    }

    public static Factor normalize(Factor factor){
        DecimalFormat decfor=new DecimalFormat("0.000");
        double total=0;
        double[] newProbabilities=new double[factor.getProbabilities().length];
        for(int i=0;i<factor.getProbabilities().length;i++){
            total=total+factor.getProbabilities()[i];
        }
        for(int i=0;i<factor.getProbabilities().length;i++){
            newProbabilities[i]=Double.parseDouble(decfor.format(factor.getProbabilities()[i]/total));
        }
        return new Factor(factor.getVariables(), factor.getValues(), newProbabilities);
    }

    public static Factor inference(Factor[] factorList,String[] queryVariables,String[] orderedListOfHiddenVariables,String[] evidenceList){
        //first we observe the values in the evidence
        System.out.println("---------Starting restrictions------------");
        for(int i=0;i<factorList.length;i++){
            for(int x=0;x<evidenceList.length;x++){
                String variable=""+evidenceList[x].charAt(1);
                if(containVar(factorList[i],variable.toUpperCase())){
                    factorList[i]=observe(factorList[i],variable,evidenceList[x]);
                    System.out.println(factorList[i].toTable());
                }
            }
        }
        System.out.println("-------------------Ending restrictions----------------");
        //then we eliminate the hidden variables
        System.out.println("--------------------Starting eliminations---------------");
        for(int i=0;i< factorList.length;i++){
            for(int x=0;x< orderedListOfHiddenVariables.length;x++){
                if(containVar(factorList[i],orderedListOfHiddenVariables[x])){
                    factorList[i]=sumout(factorList[i],orderedListOfHiddenVariables[x]);
                    System.out.println(factorList[i].toTable());
                }
            }
        }
        System.out.println("---------------------Ending eliminations-----------------------");
        //then we multiply all the factors
        System.out.println("------------------------Starting multiplications-------------------");
        Factor output=factorList[0];
        for(int i=1;i< factorList.length;i++){
            output=multiply(output,factorList[i]);
            System.out.println(output.toTable());

        }
        for(int i=0;i< queryVariables.length;i++){
            for(int x=0;x<output.getVariables().length;x++){
                if(!containVar(output,queryVariables[i])){
                    return null;
                }
            }
        }
        System.out.println("--------------------Ending multiplications------------------------");
        System.out.println("---------------------Last step Normalizing -------------------------");
        System.out.println(normalize(output).toTable());
        System.out.println("----------------------Halting------------------------");
        return normalize(output);
    }


    public static boolean containVar(Factor fact1,String variable){
        boolean logic=false;
        for(int i=0;i<fact1.getVariables().length;i++){
            if(fact1.getVariables()[i].equals(variable)){
                logic=true;
                break;
            }
        }
        return logic;
    }

    public static String[] organizeVariables(Factor fact){
        String[] newVariables=new String[fact.getVariables().length];
        for(int i=0;i< newVariables.length;i++){
            String var1=fact.getVariables()[i];
            if(fact.getValues()[0][i].toUpperCase().contains(var1)){
                newVariables[i]=var1;
            }
            else{
                for(int x=0;x< newVariables.length;x++){
                    if(x!=i){
                        var1=fact.getVariables()[x];
                        if(fact.getValues()[0][i].toUpperCase().contains(var1)){
                            newVariables[i]=var1;
                        }
                    }
                }
            }
        }
        return newVariables;
    }
}
